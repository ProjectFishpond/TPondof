package fish.pondof.tpondof.api;

import android.util.ArrayMap;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.Map;

import fish.pondof.tpondof.api.model.User;
import fish.pondof.tpondof.util.NetworkUtil;

/**
 * Created by Administrator on 2017/2/22.
 * @author Trumeet
 */

public class UserItemManager {
    private static Map<Integer, User> mCachedUsers = new ArrayMap<>();

    public static User getUserInfo (int uid, JSONObject object, boolean loadFull) throws APIException {
        if (mCachedUsers.containsKey(uid)) {
            // TODO: 这里会缓存 简短的用户信息 ，当获取完整信息时也会取出缓存。
            return mCachedUsers.get(uid);
        }
        try {
            JSONObject data = object.getJSONObject("data");
            User user = new User();
            user.setId(uid);
            JSONObject attributes;
            if (loadFull) {
                attributes = data.getJSONObject("attributes");
            } else {
                attributes = object.getJSONObject("attributes");
            }
            user.setUsername(attributes.getString("username"));
            user.setAvatarUrl(attributes.getString("avatarUrl"));
            if (loadFull) {
                user.setBio(attributes.getString("bio"));
                user.setJoinTime(attributes.getString("joinTime"));
                user.setDiscussionsCount(attributes.getInteger("discussionsCount"));
                user.setCommentsCount(attributes.getInteger("commentsCount"));
                user.setCanEdit(attributes.getBoolean("canEdit"));
                user.setCanDelete(attributes.getBoolean("canDelete"));
                user.setCanSuspend(attributes.getBoolean("canSuspend"));
                user.setVingleShareSocial(attributes.getString("vingle.share.social"));
                //TODO:Group
            }
            mCachedUsers.put(uid, user);
            return user;
        } catch (JSONException e) {
            throw new APIException(e);
        }
    }
    public static User getUserInfo (int uid) throws APIException {
        if (mCachedUsers.containsKey(uid)) {
            return mCachedUsers.get(uid);
        }
        try {
            return getUserInfo(uid
                    , JSONObject
                            .parseObject(NetworkUtil
                                    .get(ApiManager.API_USER_BY_ID + uid)), true);
        } catch (IOException|APIException e) {
            throw new APIException(e);
        }
    }
}
