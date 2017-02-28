package fish.pondof.tpondof.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Trumeet on 2017/2/25.
 * A Tag model.
 * @author Trumeet
 */

public class Tag implements Parcelable {
    public Tag () {}
    protected Tag(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        slug = in.readString();
        color = in.readInt();
        backgroundUrl = in.readString();
        backgroundMode = in.readString();
        iconUrl = in.readString();
        discussionsCount = in.readInt();
        position = in.readInt();
        defaultSort = in.readString();
        isChild = in.readByte() != 0;
        isHidden = in.readByte() != 0;
        lastTime = in.readString();
        canStartDiscussion = in.readByte() != 0;
        canAddToDiscussion = in.readByte() != 0;
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    @Override
    public String toString () {
        return "Tag[ID=" + id + "]";
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(slug);
        parcel.writeInt(color);
        parcel.writeString(backgroundUrl);
        parcel.writeString(backgroundMode);
        parcel.writeString(iconUrl);
        parcel.writeInt(discussionsCount);
        parcel.writeInt(position);
        parcel.writeString(defaultSort);
        parcel.writeByte((byte) (isChild ? 1 : 0));
        parcel.writeByte((byte) (isHidden ? 1 : 0));
        parcel.writeString(lastTime);
        parcel.writeByte((byte) (canStartDiscussion ? 1 : 0));
        parcel.writeByte((byte) (canAddToDiscussion ? 1 : 0));
    }
}
