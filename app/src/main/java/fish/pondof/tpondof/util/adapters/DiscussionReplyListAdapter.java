package fish.pondof.tpondof.util.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
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
import fish.pondof.tpondof.R;
import fish.pondof.tpondof.api.ApiManager;
import fish.pondof.tpondof.api.model.Commit;
import fish.pondof.tpondof.util.NetworkUtil;

import static fish.pondof.tpondof.BuildConfig.DEBUG;

/**
 * Created by Trumeet on 2017/2/27.
 * @author Trumeet
 */

public class DiscussionReplyListAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Commit> mCommitList;

    public DiscussionReplyListAdapter (Context context, List<Commit> commits) {
        super(context, 0, commits);
        mContext = context;
        mCommitList = commits;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_commit, parent, false);
        }
        // TODO
        Commit commit = mCommitList.get(position);

        TextView userNameText = (TextView) convertView.findViewById(android.R.id.text1);
        userNameText.setText(commit.getUser().getUsername());

        XRichText contentText = (XRichText) convertView.findViewById(android.R.id.text2);
        contentText.callback(new XRichText.Callback() {
            private static final String TAG = "RichText";
            @Override
            public void onImageClick(List<String> urlList, int position) {

            }

            @Override
            public boolean onLinkClick(String url) {
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
        }).text(commit.getContentHtml());
        return convertView;
    }
}
