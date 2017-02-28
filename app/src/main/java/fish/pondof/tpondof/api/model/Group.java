package fish.pondof.tpondof.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/2/22.
 */

public class Group implements Parcelable{
    private int id;

    protected Group(Parcel in) {
        id = in.readInt();
        nameSingular = in.readString();
        namePlural = in.readString();
        color = in.readString();
        icon = in.readString();
        vingleShareSocial = in.readString();
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSingular() {
        return nameSingular;
    }

    public void setNameSingular(String nameSingular) {
        this.nameSingular = nameSingular;
    }

    public String getNamePlural() {
        return namePlural;
    }

    public void setNamePlural(String namePlural) {
        this.namePlural = namePlural;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getVingleShareSocial() {
        return vingleShareSocial;
    }

    public void setVingleShareSocial(String vingleShareSocial) {
        this.vingleShareSocial = vingleShareSocial;
    }

    private String nameSingular;
    private String namePlural;
    private String color;
    private String icon;
    private String vingleShareSocial;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nameSingular);
        parcel.writeString(namePlural);
        parcel.writeString(color);
        parcel.writeString(icon);
        parcel.writeString(vingleShareSocial);
    }
}
