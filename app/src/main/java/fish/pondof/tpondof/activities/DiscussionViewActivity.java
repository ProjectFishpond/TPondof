package fish.pondof.tpondof.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xrichtext.XRichText;
import fish.pondof.tpondof.App;
import fish.pondof.tpondof.R;
import fish.pondof.tpondof.api.APIException;
import fish.pondof.tpondof.api.ApiManager;
import fish.pondof.tpondof.api.CommitsManager;
import fish.pondof.tpondof.api.model.Commit;
import fish.pondof.tpondof.api.model.Discussion;
import fish.pondof.tpondof.util.BarTransitions;
import fish.pondof.tpondof.util.Utils;
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
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListView.setAdapter(null);
                mAdapter = null;
                refresh(false);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refresh(true);
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

    private void refresh (final boolean useCache) {
        Observable<Object> objectObservable = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onStart();
                try {
                    mCommitList = CommitsManager.getCommitForDiscussion(mDiscussion, useCache);
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
                        mAdapter.setListener(new DiscussionReplyListAdapter.CommitListener() {
                            @Override
                            public void onJump(Commit commit, final int id) {
                                AlertDialog dialog = new AlertDialog.Builder(DiscussionViewActivity.this)
                                        .setTitle(commit.getUser().getUsername()
                                                + "#" + id)
                                        .setIcon(R.drawable.ic_reply_black_24dp)
                                        .setMessage(Html.fromHtml(commit.getContentHtml(), new Html.ImageGetter() {
                                            @Override
                                            public Drawable getDrawable(String s) {
                                                // Not show image
                                                return new ColorDrawable(Color.WHITE);
                                            }
                                        }, null))
                                        .setPositiveButton(R.string.action_goto, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                mListView.setSelection(id);
                                                changeColor(id);
                                            }
                                        })
                                        .create();
                                dialog.show();
                            }
                        });
                        mListView.setAdapter(mAdapter);
                    }
                });
    }
    private boolean mIsChangingColor = false;
    private synchronized void changeColor (int id) {
        if (mIsChangingColor)
            return;
        mIsChangingColor = true;
        // Change color
        final View view = Utils.getViewByPosition(id, mListView);
        final BarTransitions transitions =
                new BarTransitions(view, R.drawable.ic_reply_black_24dp);
        transitions.transitionTo(BarTransitions.MODE_WARNING, true);
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mListView.setOnTouchListener(null);
                transitions.transitionTo(BarTransitions.MODE_LIGHTS_OUT_TRANSPARENT, true);
                mIsChangingColor = false;
                return false;
            }
        });
    }
}
