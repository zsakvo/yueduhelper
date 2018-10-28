package cc.zsakvo.yueduhelper.classes;

public class ChapterList {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getChapterCover() {
        return chapterCover;
    }

    public void setChapterCover(String chapterCover) {
        this.chapterCover = chapterCover;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getPartsize() {
        return partsize;
    }

    public void setPartsize(int partsize) {
        this.partsize = partsize;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public Boolean getUnreadble() {
        return unreadble;
    }

    public void setUnreadble(Boolean unreadble) {
        this.unreadble = unreadble;
    }

    public Boolean getVip() {
        return isVip;
    }

    public void setVip(Boolean vip) {
        isVip = vip;
    }

    private String title;
    private String link;
    private String chapterCover;
    private int totalpage;
    private int partsize;
    private int order;
    private int currency;
    private Boolean unreadble;
    private Boolean isVip;
}
