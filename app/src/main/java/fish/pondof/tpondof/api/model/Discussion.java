package fish.pondof.tpondof.api.model;

/**
 * Created by Administrator on 2017/2/22.
 */

public class Discussion {
    @Override
    public String toString () {
        return "Discussion[ID=" + mID + "][TITLE=" + mTitle + "][AUTHOR=" + mAuthor + "]";
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
}
