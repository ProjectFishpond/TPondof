package fish.pondof.tpondof.util;

import android.content.Context;
import android.content.Intent;

import fish.pondof.tpondof.activities.DiscussionViewActivity;

/**
 * 页面跳转类
 * Created by Pollex on 2017/3/25.
 */

public class UIUtil {

    public static void openDiscussionViewActivity(Context context, int discussionId){
        Intent intent = new Intent(context, DiscussionViewActivity.class);
        intent.putExtra(DiscussionViewActivity.EXTRA_DISCUSSION_ID,discussionId);
        context.startActivity(intent);
//                .putExtra(
//                        , );
    }

}
