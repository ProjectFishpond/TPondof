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
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.ImageHolder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fish.pondof.tpondof.R;
import fish.pondof.tpondof.api.APIException;
import fish.pondof.tpondof.api.DiscussionListManager;
import fish.pondof.tpondof.api.TagManager;
import fish.pondof.tpondof.api.model.Discussion;
import fish.pondof.tpondof.api.model.Tag;
import fish.pondof.tpondof.util.IconBuilder;
import fish.pondof.tpondof.util.adapters.DiscussionListAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static fish.pondof.tpondof.BuildConfig.DEBUG;

/**
 * Created by Administrator on 2017/2/22.
 * @author Trumeet
 */

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.list)
    ListView mListView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Discussion> mDiscussionList = new ArrayList<>();
    private DiscussionListAdapter mDiscussionListAdapter;
    private Drawer mNavigationDrawer;
    private List<Tag> mTags;
    private List<Tag> mParentTags;
    private List<Discussion> mQueriedDiscussionList = new ArrayList<>();
    private String mSelectedFilter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mTags == null || mTags.isEmpty() || mParentTags == null || mParentTags.isEmpty()) {
                    loadFilters();
                    return;
                }
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
        mNavigationDrawer = new DrawerBuilder()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        /**
                         * <strong>CAUTION!!!</strong>
                         * Now use <code>mParentTags</code> as list of drawer item list.
                         * Please use <code>mParentTags.get(position)</code> to get current item!
                         * And pos 0 is "All" filter.
                         */
                        if (position != 0) {
                            Tag tag = mParentTags.get(position);
                            setTitle(tag.getName());
                            mSelectedFilter = tag.toString();
                        } else {
                            mSelectedFilter = null;
                        }
                        query();
                        return false;
                    }
                })
                .build();

        mDiscussionListAdapter = new DiscussionListAdapter(this, mQueriedDiscussionList);
        mListView.setAdapter(mDiscussionListAdapter);

        loadFilters();
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
                    subscriber.onNext(DiscussionListManager.getList());
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
                Snackbar.make(mListView, R.string.toast_error
                        , Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onCompleted() {
                if (DEBUG) Log.i(TAG, "-> onCompleted");
                mSwipeRefreshLayout.setRefreshing(false);
                if (mList != null && mList.size() > 0) {
                    mDiscussionList.clear();

                    for (Discussion d : mList) {
                        mDiscussionList.add(d);
                    }

                    query();
                }
            }
        });
    }

    private void query () {
        final String TAG = "Query";
        if (DEBUG) Log.i(TAG, "-> start");
        if (DEBUG) Log.i(TAG, "Total Size:" + mDiscussionList.size());
        for (Discussion d : mDiscussionList) {
            if (mSelectedFilter != null && !mSelectedFilter.isEmpty()) {
                if (DEBUG) Log.i(TAG, "Querying...");
                /**
                 * TODO:Query.
                 * Query mDiscussionList -> mQueriedDiscussionList
                 * query tag by mSelectedFilter
                 */
            } else {
                if (DEBUG) Log.i(TAG, "Filter word not set, add" + d);
                mQueriedDiscussionList.add(d);
            }
            mDiscussionListAdapter.notifyDataSetChanged();
        }
    }

    private void loadFilters () {
        final String TAG = "LoadFilters";
        Observable<Object> loadFiltersObservable = Observable.create(new Observable.OnSubscribe<Object>() {
            public void call(Subscriber<? super Object> subscriber) {
                if (DEBUG) Log.i(TAG, "-> subscribe -> " + Thread.currentThread().getName());
                subscriber.onStart();
                try {
                    mTags = TagManager.getTags();
                    mParentTags = new ArrayList<>();
                    for (Tag tag : mTags) {
                        if (!tag.isChild()) {
                            mParentTags.add(tag);
                        }
                    }
                    subscriber.onCompleted();
                } catch (APIException e) {
                    subscriber.onError(e);
                }
            }
        });
        loadFiltersObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onStart() {
                        if (DEBUG) Log.d(TAG, "onSubscribe -> " + Thread.currentThread().getName());
                        mSwipeRefreshLayout.setRefreshing(true);
                    }

                    @Override
                    public void onNext(Object value) {
                        if (DEBUG) Log.i(TAG, "onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (DEBUG) Log.e(TAG, "-> onError");
                        e.printStackTrace();
                        mSwipeRefreshLayout.setRefreshing(false);
                        Snackbar.make(mListView, R.string.toast_error
                                , Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCompleted() {
                        if (DEBUG) Log.i(TAG, "-> onCompleted");
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (mTags != null) {
                            mNavigationDrawer.removeAllItems();
                            mNavigationDrawer.addItem(
                                    new PrimaryDrawerItem()
                                            .withName(R.string.filter_all)
                            );
                            for (Tag tag : mParentTags) {
                                PrimaryDrawerItem item = new PrimaryDrawerItem().withName(tag.getName());
                                if (tag.getIconUrl() == null ||
                                        tag.getIconUrl().isEmpty()) {
                                    item.withIcon(new IconBuilder(MainActivity.this, tag.getColor()).build());
                                } else {
                                    item.withIcon(new ImageHolder(tag.getIconUrl()));
                                }
                                mNavigationDrawer.addItem(item);
                            }
                        }
                        refresh();
                    }
                });
    }
}
