package fish.pondof.tpondof.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fish.pondof.tpondof.R;
import fish.pondof.tpondof.api.APIException;
import fish.pondof.tpondof.api.CommitsManager;
import fish.pondof.tpondof.api.model.Commit;
import fish.pondof.tpondof.api.model.Discussion;
import fish.pondof.tpondof.util.adapters.DiscussionReplyListAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static fish.pondof.tpondof.BuildConfig.DEBUG;

/**
 * Created by Trumeet on 2017/2/27.
 * @author Trumeet
 */

public class DiscussionViewActivity extends AppCompatActivity {
    private static final String TAG = "DiscussionViewActivity";
    public static final String EXTRA_DISCUSSION = DiscussionViewActivity.class.getSimpleName()
             + ".EXTRA_DISCUSSION";
    private Discussion mDiscussion;

    @BindView(R.id.list)
    ListView mListView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mRefreshLayout;
    private DiscussionReplyListAdapter mAdapter;
    private List<Commit> mCommitList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) Log.i(TAG, "-> onCreate");
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        mDiscussion = intent.getParcelableExtra(EXTRA_DISCUSSION);
        if (mDiscussion == null) {
            finish();
            return;
        }
        setContentView(R.layout.activity_discussion_view);
        ButterKnife.bind(this);
        /**
         * Not support swipe refresh.
         */
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refresh();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refresh();
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh () {
        Observable<Object> objectObservable = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onStart();
                try {
                    mCommitList = CommitsManager.getCommitForDiscussion(mDiscussion);
                } catch (APIException e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
        objectObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onStart() {
                        if (DEBUG) Log.d(TAG, "onSubscribe -> " + Thread.currentThread().getName());
                        mRefreshLayout.setRefreshing(true);
                    }

                    @Override
                    @SuppressWarnings("unchecked")
                    public void onNext(Object value) {
                        if (DEBUG) Log.i(TAG, "onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (DEBUG) Log.e(TAG, "-> onError");
                        e.printStackTrace();
                        mRefreshLayout.setRefreshing(false);
                        Snackbar.make(mListView, R.string.toast_error
                                , Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCompleted() {
                        if (DEBUG) Log.i(TAG, "-> onCompleted");
                        mRefreshLayout.setRefreshing(false);
                        setTitle(mDiscussion.getTitle());
                        mAdapter = new DiscussionReplyListAdapter(DiscussionViewActivity.this
                                , mCommitList);
                        mListView.setAdapter(mAdapter);
                    }
                });
    }
}
