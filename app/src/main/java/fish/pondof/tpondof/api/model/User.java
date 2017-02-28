package fish.pondof.tpondof.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/2/22.
 * @author Trumeet
 */

public class User implements Parcelable {
    private int id;
    private String username;
    private String avatarUrl;
    private String bio;
    private String joinTime;
    private int discussionsCount;
    private int commentsCount;
    private boolean canEdit;
    private boolean canDelete;
    private boolean canSuspend;
    private String vingleShareSocial;
    private Group group;

    public User () {}

    protected User(Parcel in) {
        id = in.readInt();
        username = in.readString();
        avatarUrl = in.readString();
        bio = in.readString();
        joinTime = in.readString();
        discussionsCount = in.readInt();
        commentsCount = in.readInt();
        canEdit = in.readByte() != 0;
        canDelete = in.readByte() != 0;
        canSuspend = in.readByte() != 0;
        vingleShareSocial = in.readString();
        group = in.readParcelable(Group.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public int getDiscussionsCount() {
        return discussionsCount;
    }

    public void setDiscussionsCount(int discussionsCount) {
        this.discussionsCount = discussionsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public boolean isCanSuspend() {
        return canSuspend;
    }

    public void setCanSuspend(boolean canSuspend) {
        this.canSuspend = canSuspend;
    }

    public String getVingleShareSocial() {
        return vingleShareSocial;
    }

    public void setVingleShareSocial(String vingleShareSocial) {
        this.vingleShareSocial = vingleShareSocial;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(username);
        parcel.writeString(avatarUrl);
        parcel.writeString(bio);
        parcel.writeString(joinTime);
        parcel.writeInt(discussionsCount);
        parcel.writeInt(commentsCount);
        parcel.writeByte((byte) (canEdit ? 1 : 0));
        parcel.writeByte((byte) (canDelete ? 1 : 0));
        parcel.writeByte((byte) (canSuspend ? 1 : 0));
        parcel.writeString(vingleShareSocial);
        parcel.writeParcelable(group, i);
    }
}
