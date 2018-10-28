package cc.zsakvo.yueduhelper.classes;

public class BookSourceText {
    public String getBookSourceGroup() {
        return "provide_by_zsakvo";
    }

    public String getCheckUrl() {
        return "";
    }

    public Boolean getEnable() {
        return true;
    }

    public String getRuleBookAuthor() {
        return "tag.div.4@text";
    }

    public String getRuleBookContent() {
        return "id.content@textNodes";
    }

    public String getRuleBookName() {
        return "tag.div.0@text";
    }

    public String getRuleChapterList() {
        return "id.list@tag.dd";
    }

    public String getRuleChapterName() {
        return "tag.a.0@text";
    }

    public String getRuleChapterUrl() {
        return "tag.div.2@text";
    }

    public String getRuleChapterUrlNext() {
        return "";
    }

    public String getRuleContentUrl() {
        return "tag.a.0@href";
    }

    public String getRuleContentUrlNext() {
        return "";
    }

    public String getRuleCoverUrl() {
        return "tag.div.1@text";
    }

    public String getRuleFindUrl() {
        return "";
    }

    public String getRuleIntroduce() {
        return "tag.div.3@text";
    }

    public String getRuleSearchAuthor() {
        return "tag.div.3@text";
    }

    public String getRuleSearchCoverUrl() {
        return "tag.div.2@text";
    }

    public String getRuleSearchKind() {
        return "tag.div.4@text";
    }

    public String getRuleSearchLastChapter() {
        return "tag.div.5@text";
    }

    public String getRuleSearchList() {
        return "tag.li";
    }

    public String getRuleSearchName() {
        return "tag.div.0@text";
    }

    public String getRuleSearchNoteUrl() {
        return "tag.div.1@text";
    }

    public int getSerialNumber() {
        return 1;
    }

    public int getWeight() {
        return 0;
    }

    private String bookSourceName;
    private String bookSourceUrl;
    private String ruleSearchUrl;

    public String getBookSourceName() {
        return bookSourceName;
    }

    public void setBookSourceName(String bookSourceName) {
        this.bookSourceName = bookSourceName;
    }

    public String getBookSourceUrl() {
        return bookSourceUrl;
    }

    public void setBookSourceUrl(String bookSourceUrl) {
        this.bookSourceUrl = bookSourceUrl;
    }

    public String getRuleSearchUrl() {
        return ruleSearchUrl;
    }

    public void setRuleSearchUrl(String ruleSearchUrl) {
        this.ruleSearchUrl = ruleSearchUrl;
    }
}
