package fish.pondof.tpondof.api;

import android.graphics.Color;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fish.pondof.tpondof.api.model.Tag;
import fish.pondof.tpondof.util.NetworkUtil;

import static fish.pondof.tpondof.BuildConfig.DEBUG;

/**
 * Created by Trumeet on 2017/2/25.
 */

public class TagManager {
    private static final String TAG = "TagManager";
    public static List<Tag> getTags () throws APIException {
        if (DEBUG) Log.i(TAG, "-> getTags");
        try {
            String text = NetworkUtil.get(ApiManager.API_TAGS);
            if (DEBUG) Log.i(TAG, "-> get -> success");
            JSONObject root = JSON.parseObject(text);
            JSONArray data = root.getJSONArray("data");
            List<Tag> tags = new ArrayList<>();
            for (int i = 0; i < data.size(); i ++) {
                JSONObject object = data.getJSONObject(i);
                Tag tag = new Tag();
                tag.setId(object.getInteger("id"));
                JSONObject attributes = object.getJSONObject("attributes");
                tag.setName(attributes.getString("name"));
                tag.setDescription(attributes.getString("description"));
                tag.setSlug(attributes.getString("slug"));
                tag.setColor(Color.parseColor(attributes.getString("color")));
                tag.setBackgroundUrl(attributes.getString("backgroundUrl"));
                tag.setBackgroundMode(attributes.getString("backgroundMode"));
                tag.setIconUrl(attributes.getString("iconUrl"));
                tag.setDiscussionsCount(attributes.getInteger("discussionsCount"));
                //tag.setPosition(attributes.getInteger("position"));
                tag.setDefaultSort(attributes.getString("defaultSort"));
                tag.setChild(attributes.getBoolean("isChild"));
                tag.setHidden(attributes.getBoolean("isHidden"));
                tag.setLastTime(attributes.getString("lastTime"));
                tag.setCanStartDiscussion(attributes.getBoolean("canStartDiscussion"));
                tag.setCanAddToDiscussion(attributes.getBoolean("canAddToDiscussion"));
                if (DEBUG) Log.i(TAG, "Adding " + tag.toString());
                tags.add(tag);
            }
            return tags;
        } catch (IOException|JSONException e) {
            throw new APIException(e);
        }
    }
}
