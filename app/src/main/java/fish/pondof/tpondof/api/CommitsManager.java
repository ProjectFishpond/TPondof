package fish.pondof.tpondof.api;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fish.pondof.tpondof.api.model.Commit;
import fish.pondof.tpondof.api.model.Discussion;
import fish.pondof.tpondof.api.model.User;
import fish.pondof.tpondof.util.NetworkUtil;

import static fish.pondof.tpondof.BuildConfig.DEBUG;

/**
 * Created by Trumeet on 2017/2/27.
 * @author Trumeet
 */

public class CommitsManager {
    private static final String TAG = "CommitsManager";
    public static List<Commit> getCommitForDiscussion (Discussion discussion) throws APIException {
        if (DEBUG) Log.i(TAG, "-> start");
        try {
            String text = NetworkUtil.get(ApiManager.API_DISCUSSIONS + "/" + discussion.getID());
            List<Commit> commits = new ArrayList<>();
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
                Commit commit = new Commit();
                commit.setId(object.getInteger("id"));
                commit.setNumber(attributes.getInteger("number"));
                commit.setTime(attributes.getString("time"));
                commit.setContentHtml(attributes.getString("contentHtml"));
                commit.setCanEdit(attributes.getBoolean("canEdit"));
                commit.setCanDelete(attributes.getBoolean("canDelete"));
                commit.setCanFlag(attributes.getBoolean("canFlag"));
                commit.setApproved(attributes.getBoolean("canApprove"));
                commit.setCanLike(attributes.getBoolean("canLike"));
                JSONObject relationships = object.getJSONObject("relationships");
                commit.setUser(UserItemManager.getUserInfo(relationships.getJSONObject("user")
                        .getJSONObject("data").getInteger("id")));
                JSONArray likes = relationships.getJSONObject("likes").getJSONArray("data");
                List<User> likesList = new ArrayList<>();
                for (int j = 0; j < likes.size(); j ++) {
                    likesList.add(UserItemManager.getUserInfo(likes.getJSONObject(i)
                            .getInteger("id")));
                }
                commit.setLikes(likesList);
                /*
                JSONArray mentionedBy = relationships.getJSONObject("mentionedBy")
                        .getJSONArray("data");
                List<User> mentionedByList = new ArrayList<>();
                for (int j = 0; j < mentionedBy.size(); j ++) {
                    mentionedByList.add(UserItemManager.getUserInfo(mentionedBy.getJSONObject(j).getInteger("id")));
                }
                commit.setMetionedBy(mentionedByList);
                */
                if (DEBUG) Log.i(TAG, "Adding " + commit);
                commits.add(commit);
            }
            return commits;
        } catch (IOException|JSONException|NullPointerException e) {
            throw new APIException(e);
        }
    }
}
