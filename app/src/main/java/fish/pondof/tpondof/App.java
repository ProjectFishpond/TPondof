package fish.pondof.tpondof;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/2/22.
 */

public class App extends Application {
    private static Context instance;
    @Override
    public void onCreate () {
        super.onCreate();
        instance = this;
    }
    public static Context getInstance () {
        return instance;
    }
}
