package fish.pondof.tpondof.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Trumeet on 2017/2/27.
 * @author Trumeet
 */

public class Comment implements Parcelable{
    private int id;
    private int number;
    private String time;
    private String contentHtml;
    private boolean canEdit;
    private boolean canDelete;
    private boolean isApproved;
    private boolean canFlag;
    private boolean canLike;
    private User user;
    private List<User> likes; // Likes user id list
    private List<User> metionedBy;

    public Comment() {}

    protected Comment(Parcel in) {
        id = in.readInt();
        number = in.readInt();
        time = in.readString();
        contentHtml = in.readString();
        canEdit = in.readByte() != 0;
        canDelete = in.readByte() != 0;
        isApproved = in.readByte() != 0;
        canFlag = in.readByte() != 0;
        canLike = in.readByte() != 0;
        user = in.readParcelable(User.class.getClassLoader());
        likes = in.createTypedArrayList(User.CREATOR);
        metionedBy = in.createTypedArrayList(User.CREATOR);
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
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

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean isCanFlag() {
        return canFlag;
    }

    public void setCanFlag(boolean canFlag) {
        this.canFlag = canFlag;
    }

    public boolean isCanLike() {
        return canLike;
    }

    public void setCanLike(boolean canLike) {
        this.canLike = canLike;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public List<User> getMetionedBy() {
        return metionedBy;
    }

    public void setMetionedBy(List<User> metionedBy) {
        this.metionedBy = metionedBy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(number);
        parcel.writeString(time);
        parcel.writeString(contentHtml);
        parcel.writeByte((byte) (canEdit ? 1 : 0));
        parcel.writeByte((byte) (canDelete ? 1 : 0));
        parcel.writeByte((byte) (isApproved ? 1 : 0));
        parcel.writeByte((byte) (canFlag ? 1 : 0));
        parcel.writeByte((byte) (canLike ? 1 : 0));
        parcel.writeParcelable(user, i);
        parcel.writeTypedList(likes);
        parcel.writeTypedList(metionedBy);
    }
}
