package fish.pondof.tpondof.api.model;

/**
 * Created by Administrator on 2017/2/22.
 */

public class User {
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
}
