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
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;
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
import fish.pondof.tpondof.util.Utils;
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
    /**
     * @see <a href="http://stackoverflow.com/questions/26495530/how-to-make-the-toolbar-title-clickable-exactly-like-on-actionbar-without-setti" />
     */
    @BindView(R.id.toolbar_title)
    TextView mTextTitle;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Discussion> mDiscussionList = new ArrayList<>();
    private DiscussionListAdapter mDiscussionListAdapter;
    private Drawer mNavigationDrawer;
    private List<Tag> mTags;
    private List<Tag> mParentTags;
    private List<Discussion> mQueriedDiscussionList = new ArrayList<>();
    private Tag mSelectedFilter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
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

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
                .withToolbar(mToolbar)
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
        mQueriedDiscussionList.clear();
        mDiscussionListAdapter.notifyDataSetChanged();
        for (Discussion d : mDiscussionList) {
            if (mSelectedFilter != null) {
                boolean finished = false;
                if (DEBUG) Log.i(TAG, "Querying...");
                /**
                 * TODO:这算法太tm垃圾了
                 */
                for (Integer integer : d.getTags()) {
                    if (finished)
                        continue;
                    if (mSelectedFilter.getId() == integer) {
                        finished = true;
                        mQueriedDiscussionList.add(d);
                    }
                }
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
                        if (e instanceof APIException && ((APIException)e).isIOException()) {
                            Snackbar.make(mListView, R.string.toast_error
                                    , Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(mListView, R.string.toast_error
                                    , Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (DEBUG) Log.i(TAG, "-> onCompleted");
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (mTags != null) {
                            mTextTitle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (mSwipeRefreshLayout.isRefreshing()) {
                                        if (DEBUG) Log.e(TAG, "Refreshing, not show sheet");
                                        return;
                                    }
                                    if (DEBUG) Log.i(TAG, "Showing bottom sheet");
                                    BottomSheetLayout bottomSheet = (BottomSheetLayout)findViewById(R.id.bottomsheet);
                                    Utils.pickTags(bottomSheet, new Utils.OnTagSelectedListener() {
                                        @Override
                                        public void onSelected(Tag tag) {
                                            mTextTitle.setText(tag.getName());
                                            mSelectedFilter = tag;
                                            query();
                                        }

                                        @Override
                                        public void onSpecialSelected(int id) {
                                            mTextTitle.setText(R.string.filter_all);
                                            mSelectedFilter = null;
                                            query();
                                        }
                                    }, mParentTags);
                                }
                            });
                            mTextTitle.setText(R.string.filter_all);
                        }
                        refresh();
                    }
                });
    }
}
