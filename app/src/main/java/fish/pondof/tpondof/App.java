package fish.pondof.tpondof;

import android.app.Application;
import android.content.Context;

import fish.pondof.tpondof.util.ACache;

/**
 * Created by Administrator on 2017/2/22.
 */

public class App extends Application {
    private static Context instance;
    private static ACache mCache;
    @Override
    public void onCreate () {
        super.onCreate();
        instance = this;
        mCache = ACache.get(this);
    }

    public static ACache getCache () {
        return mCache;
    }

    public static Context getInstance () {
        return instance;
    }
}
