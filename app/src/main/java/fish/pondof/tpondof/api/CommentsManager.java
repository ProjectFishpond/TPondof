package fish.pondof.tpondof.api;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fish.pondof.tpondof.api.model.Comment;
import fish.pondof.tpondof.api.model.Discussion;
import fish.pondof.tpondof.api.model.User;
import fish.pondof.tpondof.util.NetworkUtil;

import static fish.pondof.tpondof.BuildConfig.DEBUG;

/**
 * Created by Trumeet on 2017/2/27.
 * @author Trumeet
 */

public class CommentsManager {
    private static final String TAG = "CommentsManager";

    public static List<Comment> getCommentForDiscussion(Discussion discussion, boolean useCache) throws APIException {
        return getCommentForDiscussion(discussion.getID(),useCache);
    }

    public static List<Comment> getCommentForDiscussion(int discussionId, boolean useCache) throws APIException {
        if (DEBUG) Log.i(TAG, "-> start");
        try {
            String text = NetworkUtil.get(ApiManager.API_DISCUSSIONS + "/" + discussionId,
                    useCache);
            List<Comment> comments = new ArrayList<>();
            JSONObject root = JSON.parseObject(text);
            JSONArray included = root.getJSONArray("included");
            if (DEBUG) Log.i(TAG, "Total size: " + included.size());
            for (int i = 0; i < included.size(); i ++) {
                JSONObject object = included.getJSONObject(i);
                String type = object.getString("type");
                if (!type.equals("posts")) {
                    if (type.equals("users")) {
                        if (DEBUG) Log.i(TAG, "Get a user");
                        UserItemManager.getUserInfo(object.getInteger("id")
                                , object, false);
                        continue;
                    } else {
                        if (DEBUG) Log.w(TAG, "Not a post, just skip");
                        continue;
                    }
                }
                JSONObject attributes = object.getJSONObject("attributes");
                Comment comment = new Comment();
                comment.setId(object.getInteger("id"));
                comment.setNumber(attributes.getInteger("number"));
                comment.setTime(attributes.getString("time"));
                comment.setContentHtml(attributes.getString("contentHtml"));
                comment.setCanEdit(attributes.getBoolean("canEdit"));
                comment.setCanDelete(attributes.getBoolean("canDelete"));
                comment.setCanFlag(attributes.getBoolean("canFlag"));
                comment.setApproved(attributes.getBoolean("canApprove"));
                comment.setCanLike(attributes.getBoolean("canLike"));
                JSONObject relationships = object.getJSONObject("relationships");
                comment.setUser(UserItemManager.getUserInfo(relationships.getJSONObject("user")
                        .getJSONObject("data").getInteger("id"), useCache));
                JSONArray likes = relationships.getJSONObject("likes").getJSONArray("data");
                List<User> likesList = new ArrayList<>();
                for (int j = 0; j < likes.size(); j ++) {
                    likesList.add(UserItemManager.getUserInfo(likes.getJSONObject(i)
                            .getInteger("id"), useCache));
                }
                comment.setLikes(likesList);
                /*
                JSONArray mentionedBy = relationships.getJSONObject("mentionedBy")
                        .getJSONArray("data");
                List<User> mentionedByList = new ArrayList<>();
                for (int j = 0; j < mentionedBy.size(); j ++) {
                    mentionedByList.add(UserItemManager.getUserInfo(mentionedBy.getJSONObject(j).getInteger("id")));
                }
                comment.setMetionedBy(mentionedByList);
                */
                if (DEBUG) Log.i(TAG, "Adding " + comment);
                comments.add(comment);
            }
            // Sort by number
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment comment, Comment t1) {
                    if (comment.getNumber() > t1.getNumber()) {
                        return 1;
                    } else if (comment.getNumber() < t1.getNumber()) {
                        return -1;
                    }
                    return 0;
                }
            });
            return comments;
        } catch (IOException|JSONException|NullPointerException e) {
            throw new APIException(e);
        }
    }
}
