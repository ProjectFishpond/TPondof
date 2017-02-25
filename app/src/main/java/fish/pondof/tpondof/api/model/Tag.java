package fish.pondof.tpondof.api.model;

/**
 * Created by Trumeet on 2017/2/25.
 * A Tag model.
 * @author Trumeet
 */

public class Tag {
    @Override
    public String toString () {
        return "Tag[ID=" + id + "]";
    }

    public static String toString (int id) {
        Tag tag = new Tag();
        tag.setId(id);
        return tag.toString();
    }

    private int id;
    private String name;
    private String description;
    private String slug;
    private int color;
    private String backgroundUrl;
    private String backgroundMode;
    private String iconUrl;
    private int discussionsCount;
    private int position;
    private String defaultSort;
    private boolean isChild;
    private boolean isHidden;
    private String lastTime;
    private boolean canStartDiscussion;
    private boolean canAddToDiscussion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getBackgroundMode() {
        return backgroundMode;
    }

    public void setBackgroundMode(String backgroundMode) {
        this.backgroundMode = backgroundMode;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getDiscussionsCount() {
        return discussionsCount;
    }

    public void setDiscussionsCount(int discussionsCount) {
        this.discussionsCount = discussionsCount;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDefaultSort() {
        return defaultSort;
    }

    public void setDefaultSort(String defaultSort) {
        this.defaultSort = defaultSort;
    }

    public boolean isChild() {
        return isChild;
    }

    public void setChild(boolean child) {
        isChild = child;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public boolean isCanStartDiscussion() {
        return canStartDiscussion;
    }

    public void setCanStartDiscussion(boolean canStartDiscussion) {
        this.canStartDiscussion = canStartDiscussion;
    }

    public boolean isCanAddToDiscussion() {
        return canAddToDiscussion;
    }

    public void setCanAddToDiscussion(boolean canAddToDiscussion) {
        this.canAddToDiscussion = canAddToDiscussion;
    }
}
