package fish.pondof.tpondof.util;

import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;

import java.util.List;

import fish.pondof.tpondof.R;
import fish.pondof.tpondof.api.model.Tag;

/**
 * Created by Trumeet on 2017/2/26.
 * @author Trumeet
 */

public class Utils {
    public static final int ID_ALL = -1;

    public interface OnTagSelectedListener {
        void onSelected (Tag tag);
        void onSpecialSelected (int id);
    }

    public static void pickTags (final BottomSheetLayout layout
            , final OnTagSelectedListener listener
            , final List<Tag> tags) {
        MenuSheetView view = new MenuSheetView(layout.getContext()
                , MenuSheetView.MenuType.GRID
                , R.string.title_tags
                , new MenuSheetView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == ID_ALL) {
                    listener.onSpecialSelected(item.getItemId());
                    layout.dismissSheet();
                    return true;
                }
                listener.onSelected(tags.get(item.getItemId()));
                layout.dismissSheet();
                return true;
            }
        });
        view.getMenu().add(0, ID_ALL, 0, R.string.filter_all)
        .setIcon(R.drawable.ic_apps_black_24dp);
        for (int i = 0; i < tags.size(); i ++) {
            Tag tag = tags.get(i);
            // TODO: Icon load
            view.getMenu().add(0, i, 0, tag.getName())
                    .setIcon(new IconBuilder(layout.getContext(), tag.getColor()).build());
        }
        view.updateMenu();
        layout.showWithSheetView(view);
    }

    /**
     * @see <a href="http://stackoverflow.com/questions/24811536/android-listview-get-item-view-by-position" >android - listview get item view by position
    </a>
     * @param pos
     * @param listView
     * @return
     */
    public static View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}
