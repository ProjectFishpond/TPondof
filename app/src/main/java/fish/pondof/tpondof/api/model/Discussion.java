package fish.pondof.tpondof.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 * @author Trumeet
 */

public class Discussion implements Parcelable{
    public Discussion () {}
    protected Discussion(Parcel in) {
        mID = in.readInt();
        mTitle = in.readString();
        mAuthor = in.readParcelable(User.class.getClassLoader());
        slug = in.readString();
        commentsCount = in.readInt();
        participantsCount = in.readInt();
        startTime = in.readString();
        lastTime = in.readString();
        lastPostNumber = in.readInt();
        canReply = in.readByte() != 0;
        canRename = in.readByte() != 0;
        canDelete = in.readByte() != 0;
        canHide = in.readByte() != 0;
        readTime = in.readByte() != 0;
        readNumber = in.readByte() != 0;
        isApproved = in.readByte() != 0;
        isLocked = in.readByte() != 0;
        canLock = in.readByte() != 0;
        isSticky = in.readByte() != 0;
        canSticky = in.readByte() != 0;
        subscription = in.readString();
        canTag = in.readByte() != 0;
        vingleShareSocial = in.readString();
        contentHtml = in.readString();
        lastUser = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Discussion> CREATOR = new Creator<Discussion>() {
        @Override
        public Discussion createFromParcel(Parcel in) {
            return new Discussion(in);
        }

        @Override
        public Discussion[] newArray(int size) {
            return new Discussion[size];
        }
    };

    @Override
    public String toString () {
        return "Discussion[ID=" + mID + "]";
    }

    private int mID;
    private String mTitle;
    private User mAuthor;
    private String slug;
    private int commentsCount;
    private int participantsCount;
    private String startTime;
    private String lastTime;
    private int lastPostNumber;
    private boolean canReply;
    private boolean canRename;
    private boolean canDelete;
    private boolean canHide;
    private boolean readTime;
    private boolean readNumber;
    private boolean isApproved;
    private boolean isLocked;
    private boolean canLock;
    private boolean isSticky;
    private boolean canSticky;
    private String subscription;
    private boolean canTag;
    private String vingleShareSocial;
    private String contentHtml;
    private User lastUser;
    private List<Integer> tags = new ArrayList<>();

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public User getLastUser() {
        return lastUser;
    }

    public void setLastUser(User lastUser) {
        this.lastUser = lastUser;
    }

    public String getContentHtml () {
        return contentHtml;
    }

    public void setContentHtml (String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(int participantsCount) {
        this.participantsCount = participantsCount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public int getLastPostNumber() {
        return lastPostNumber;
    }

    public void setLastPostNumber(int lastPostNumber) {
        this.lastPostNumber = lastPostNumber;
    }

    public boolean isCanReply() {
        return canReply;
    }

    public void setCanReply(boolean canReply) {
        this.canReply = canReply;
    }

    public boolean isCanRename() {
        return canRename;
    }

    public void setCanRename(boolean canRename) {
        this.canRename = canRename;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public boolean isCanHide() {
        return canHide;
    }

    public void setCanHide(boolean canHide) {
        this.canHide = canHide;
    }

    public boolean isReadTime() {
        return readTime;
    }

    public void setReadTime(boolean readTime) {
        this.readTime = readTime;
    }

    public boolean isReadNumber() {
        return readNumber;
    }

    public void setReadNumber(boolean readNumber) {
        this.readNumber = readNumber;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isCanLock() {
        return canLock;
    }

    public void setCanLock(boolean canLock) {
        this.canLock = canLock;
    }

    public boolean isSticky() {
        return isSticky;
    }

    public void setSticky(boolean sticky) {
        isSticky = sticky;
    }

    public boolean isCanSticky() {
        return canSticky;
    }

    public void setCanSticky(boolean canSticky) {
        this.canSticky = canSticky;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public boolean isCanTag() {
        return canTag;
    }

    public void setCanTag(boolean canTag) {
        this.canTag = canTag;
    }

    public String getVingleShareSocial() {
        return vingleShareSocial;
    }

    public void setVingleShareSocial(String vingleShareSocial) {
        this.vingleShareSocial = vingleShareSocial;
    }

    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public User getAuthor() {
        return mAuthor;
    }

    public void setAuthor(User mAuthor) {
        this.mAuthor = mAuthor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mID);
        parcel.writeString(mTitle);
        parcel.writeParcelable(mAuthor, i);
        parcel.writeString(slug);
        parcel.writeInt(commentsCount);
        parcel.writeInt(participantsCount);
        parcel.writeString(startTime);
        parcel.writeString(lastTime);
        parcel.writeInt(lastPostNumber);
        parcel.writeByte((byte) (canReply ? 1 : 0));
        parcel.writeByte((byte) (canRename ? 1 : 0));
        parcel.writeByte((byte) (canDelete ? 1 : 0));
        parcel.writeByte((byte) (canHide ? 1 : 0));
        parcel.writeByte((byte) (readTime ? 1 : 0));
        parcel.writeByte((byte) (readNumber ? 1 : 0));
        parcel.writeByte((byte) (isApproved ? 1 : 0));
        parcel.writeByte((byte) (isLocked ? 1 : 0));
        parcel.writeByte((byte) (canLock ? 1 : 0));
        parcel.writeByte((byte) (isSticky ? 1 : 0));
        parcel.writeByte((byte) (canSticky ? 1 : 0));
        parcel.writeString(subscription);
        parcel.writeByte((byte) (canTag ? 1 : 0));
        parcel.writeString(vingleShareSocial);
        parcel.writeString(contentHtml);
        parcel.writeParcelable(lastUser, i);
    }
}
