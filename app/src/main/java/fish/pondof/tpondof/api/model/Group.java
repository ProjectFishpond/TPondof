package fish.pondof.tpondof.api.model;

/**
 * Created by Administrator on 2017/2/22.
 */

public class Group {
    private int id;

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
}
