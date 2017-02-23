package fish.pondof.tpondof.api;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import fish.pondof.tpondof.api.model.User;
import fish.pondof.tpondof.util.NetworkUtil;

/**
 * Created by Administrator on 2017/2/22.
 */

public class UserItemManager {
    public static User getUserInfo (int uid) throws APIException {
        try {
            JSONObject object = JSONObject.parseObject(NetworkUtil.get(ApiManager.API_USER_BY_ID + uid));
            JSONObject data = object.getJSONObject("data");
            User user = new User();
            user.setId(uid);
            JSONObject attributes = data.getJSONObject("attributes");
            user.setUsername(attributes.getString("username"));
            user.setAvatarUrl(attributes.getString("avatarUrl"));
            user.setBio(attributes.getString("bio"));
            user.setJoinTime(attributes.getString("joinTime"));
            user.setDiscussionsCount(attributes.getInteger("discussionsCount"));
            user.setCommentsCount(attributes.getInteger("commentsCount"));
            user.setCanEdit(attributes.getBoolean("canEdit"));
            user.setCanDelete(attributes.getBoolean("canDelete"));
            user.setCanSuspend(attributes.getBoolean("canSuspend"));
            user.setVingleShareSocial(attributes.getString("vingle.share.social"));
            //TODO:Group
            return user;
        } catch (IOException|JSONException e) {
            throw new APIException(e);
        }
    }
}
