package fish.pondof.tpondof.util;

import android.content.Context;
import android.text.TextUtils;

import fish.pondof.tpondof.api.ApiManager;

/**
 * URL路由
 * Created by Pollex on 2017/3/25.
 */

public class UrlRouterUtil {

    /**
     * 是否是内部跳转
     * @return
     */
    public static boolean isInnerJump(Context context, String url, int mDiscussionId){
        if (!url.startsWith(ApiManager.URL)) {
            return false;
        }
//        if(isCommentJump(context,url)){
//            return true;
//        }
        if(isDiscussionJump(context,url, mDiscussionId)){
            return true;
        }

        return false;
    }

    /**
     * 是否是帖子跳转
     * @param context
     * @param url
     * @return
     * https://pondof.fish/d/13
     */
    public static boolean isDiscussionJump(Context context, String url,int mDiscussionId){
        if(null!= url && url.length() > ApiManager.URL.length() + 2) {
            String urlPath = url.substring(ApiManager.URL.length() + 2);
            String[] paths = urlPath.split("/");
            // 点击链接的帖子id和本帖子id不同
            if(TextUtils.isDigitsOnly(paths[0]) && TextUtils.isDigitsOnly(paths[1]) && mDiscussionId != Integer.parseInt(paths[0])){
                UIUtil.openDiscussionViewActivity(context,Integer.parseInt(paths[0]));
                return true;
            }
        }
        return false;
    }


    /**
     * 是否是评论跳转
     * @param context
     * @param url
     * @return
     * https://pondof.fish/d/10/2
     */
    public static int isCommentJump(Context context, String url, int mDiscussionId) {
        if(null!= url && url.length() > ApiManager.URL.length() + 2){
            String commentPath = url.substring(ApiManager.URL.length() + 2);
            String[] paths = commentPath.split("/");
            // 点击链接的帖子id和本帖子id相同
            if (TextUtils.isDigitsOnly(paths[0]) && TextUtils.isDigitsOnly(paths[1]) && mDiscussionId == Integer.parseInt(paths[0])) {
                return Integer.parseInt(paths[1]);
            }
        }
        return -1;
    }
}
