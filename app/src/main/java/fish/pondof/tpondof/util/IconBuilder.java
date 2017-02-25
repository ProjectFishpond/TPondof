package fish.pondof.tpondof.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import fish.pondof.tpondof.R;

/**
 * Created by Trumeet on 2017/2/25.
 * Generate Tag icon
 * @author Trumeet
 */

public class IconBuilder {
    private int mColor;
    private Drawable mDrawable;
    public IconBuilder (Context context, int color) {
        mColor = color;
        mDrawable = context.getResources().getDrawable(R.drawable.ic_round_color);
    }

    public Drawable build () {
        mDrawable.setTint(mColor);
        return mDrawable;
    }
}
