package fish.pondof.tpondof.util.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fish.pondof.tpondof.R;
import fish.pondof.tpondof.api.model.Discussion;
import fish.pondof.tpondof.util.NetworkUtil;

/**
 * Created by Administrator on 2017/2/22.
 */

public class DiscussionListAdapter extends ArrayAdapter<Discussion> {
    private List<Discussion> mList;
    private Context mContext;
    public DiscussionListAdapter (Context context, List<Discussion> list) {
        super(context, 0, list);
        mContext = context;
        mList = list;
    }
    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_discussion, parent, false);
        }
        Discussion discussion = mList.get(position);

        TextView title = (TextView) convertView.findViewById(android.R.id.text1);
        title.setText(discussion.getTitle());

        CircleImageView avatar = (CircleImageView) convertView.findViewById(R.id.avatar);
        NetworkUtil.loadImage(discussion.getAuthor().getAvatarUrl(), avatar);

        TextView context = (TextView) convertView.findViewById(android.R.id.text2);
        context.setText(mContext.getString(R.string.text_post_summary, discussion.getLastUser().getUsername(), discussion.getLastTime()));

        return convertView;
    }
}
