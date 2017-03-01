package fish.pondof.tpondof.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by Trumeet on 2017/3/1.
 * @author Trumeet
 */

public class PhotoViewActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGES = PhotoViewActivity.class.getSimpleName()
             + ".EXTRA_IMAGES";
    private ArrayList<String> mImageUrlString;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
