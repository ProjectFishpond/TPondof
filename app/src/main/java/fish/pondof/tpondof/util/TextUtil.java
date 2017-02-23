package fish.pondof.tpondof.util;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/2/22.
 */

public class TextUtil {
    public static void applyHtmlToTextView (String html, TextView view) {
        if (html == null)
            return;
        view.setText(Html.fromHtml(html, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                return null;
            }
        }, null));
    }
}
