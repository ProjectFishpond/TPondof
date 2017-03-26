package fish.pondof.tpondof.util.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import cn.droidlover.xrichtext.ImageLoader;
import cn.droidlover.xrichtext.XRichText;
import de.hdodenhof.circleimageview.CircleImageView;
import fish.pondof.tpondof.R;
import fish.pondof.tpondof.api.ApiManager;
import fish.pondof.tpondof.api.model.Comment;
import fish.pondof.tpondof.util.NetworkUtil;
import fish.pondof.tpondof.util.UrlRouterUtil;

import static fish.pondof.tpondof.BuildConfig.DEBUG;

/**
 * Created by Trumeet on 2017/2/27.
 *
 * @author Trumeet
 */

public class DiscussionReplyListAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Comment> mCommentList;
    private CommitListener mListener;
    private int mDiscussionId;

    public interface CommitListener {
        void onJump(Comment comment, int id);
    }

    public void setListener(@Nullable CommitListener listener) {
        mListener = listener;
    }

    public DiscussionReplyListAdapter(Context context, List<Comment> comments, int mDiscussionId) {
        super(context, 0, comments);
        mContext = context;
        mCommentList = comments;
        this.mDiscussionId = mDiscussionId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_commit, parent, false);
        }
        // TODO
        Comment comment = mCommentList.get(position);

        TextView userNameText = (TextView) convertView.findViewById(android.R.id.text1);
        userNameText.setText(comment.getUser().getUsername());

        XRichText contentText = (XRichText) convertView.findViewById(android.R.id.text2);
        contentText.callback(new XRichText.Callback() {
            private static final String TAG = "RichText";

            @Override
            public void onImageClick(List<String> urlList, int position) {

            }

            @Override
            public boolean onLinkClick(String url) {
                if (DEBUG) Log.i(TAG, "onLinkClick:" + url);
                // 帖子之间跳转
                if (UrlRouterUtil.isInnerJump(mContext, url, mDiscussionId)) {
                    return true;
                }
                // 评论之间跳转
                int commentId = UrlRouterUtil.isCommentJump(mContext, url, mDiscussionId);
                if (commentId != -1) {
                    if (mListener != null) {
                        for (int pos = 0; pos < mCommentList.size(); pos++) {
                            Comment tempComment = mCommentList.get(pos);
                            if (commentId == tempComment.getNumber()) {
                                mListener.onJump(tempComment, pos);
                                return true;
                            }
                        }
                    }
                }
                return false;
            }

            @Override
            public void onFix(XRichText.ImageHolder holder) {
            }
        }).imageDownloader(new ImageLoader() {
            private static final String TAG = "RichText-ImageLoader";

            @Override
            public Bitmap getBitmap(String url) throws IOException {
                if (DEBUG) Log.i(TAG, "-> getBitmap");
                if (DEBUG) Log.i(TAG, url);
                String urlNew = url;
                if (!urlNew.startsWith("http:") && !urlNew.startsWith("https:")) {
                    urlNew = ApiManager.URL + url;
                }
                if (DEBUG) Log.i(TAG, "Load url:" + urlNew);
                return NetworkUtil.buildPicasso(mContext, urlNew).get();
            }
        }).text(comment.getContentHtml());

        TextView posText = (TextView) convertView.findViewById(R.id.text_pos);
        posText.setText("#" + position);

        CircleImageView avatar = (CircleImageView) convertView.findViewById(R.id.avatar);
        NetworkUtil.loadImage(comment.getUser().getAvatarUrl(), avatar);

        return convertView;
    }
}
