package fish.pondof.tpondof.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fish.pondof.tpondof.R;
import fish.pondof.tpondof.api.APIException;
import fish.pondof.tpondof.api.DiscussionListManager;
import fish.pondof.tpondof.api.model.Discussion;
import fish.pondof.tpondof.util.adapters.DiscussionListAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static fish.pondof.tpondof.BuildConfig.DEBUG;

/**
 * Created by Administrator on 2017/2/22.
 */

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.list)
    ListView mListView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Discussion> mDiscussionList;
    private DiscussionListAdapter mDiscussionListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        new ProfileDrawerItem()
                                /*
                                .withName("Mike Penz")
                                .withEmail("mikepenz@gmail.com")
                                */
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        new DrawerBuilder()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .build();
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void refresh () {
        final String TAG = "Refresh Discussion List";
        Observable<Object> refreshObservable = Observable.create(new Observable.OnSubscribe<Object>() {
            public void call(Subscriber<? super Object> subscriber) {
                if (DEBUG) Log.i(TAG, "-> subscribe -> " + Thread.currentThread().getName());
                subscriber.onStart();
                try {
                    subscriber.onNext(DiscussionListManager.getList(null));
                    subscriber.onCompleted();
                } catch (APIException e) {
                    subscriber.onError(e);
                }
            }
        });
        refreshObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
            private List<Discussion> mList;
            @Override
            public void onStart() {
                if (DEBUG) Log.d(TAG, "onSubscribe -> " + Thread.currentThread().getName());
                mSwipeRefreshLayout.setRefreshing(true);
            }

            @Override
            @SuppressWarnings("unchecked")
            public void onNext(Object value) {
                if (DEBUG) Log.i(TAG, "onNext");
                if (value instanceof List) {
                    List list = (List) value;
                    if (list.size() > 0 && list.get(0) instanceof Discussion) {
                        mList = (List<Discussion>) list;
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (DEBUG) Log.e(TAG, "-> onError");
                e.printStackTrace();
                mSwipeRefreshLayout.setRefreshing(false);
                Snackbar.make(mListView, R.string.toast_error, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onCompleted() {
                if (DEBUG) Log.i(TAG, "-> onCompleted");
                mSwipeRefreshLayout.setRefreshing(false);
                if (mList != null && mList.size() > 0) {
                    if (mDiscussionList != null)
                    mDiscussionList.clear();
                    else
                    mDiscussionList = new ArrayList<>();
                    if (mDiscussionListAdapter != null) {
                        mDiscussionListAdapter.notifyDataSetChanged();
                    } else {
                        mDiscussionListAdapter = new DiscussionListAdapter(MainActivity.this, mDiscussionList);
                        mListView.setAdapter(mDiscussionListAdapter);
                    }
                    for (Discussion d : mList) {
                        mDiscussionList.add(d);
                        mDiscussionListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
