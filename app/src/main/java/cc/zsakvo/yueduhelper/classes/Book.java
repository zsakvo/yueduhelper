package cc.zsakvo.yueduhelper.classes;

public class Book {
    private String _id;
    private Boolean hasCp;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Boolean getHasCp() {
        return hasCp;
    }

    public void setHasCp(Boolean hasCp) {
        this.hasCp = hasCp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public Integer getRetentionRatio() {
        return retentionRatio;
    }

    public void setRetentionRatio(Integer retentionRatio) {
        this.retentionRatio = retentionRatio;
    }

    public Integer getBanned() {
        return banned;
    }

    public void setBanned(Integer banned) {
        this.banned = banned;
    }

    public Boolean getAllowMonthly() {
        return allowMonthly;
    }

    public void setAllowMonthly(Boolean allowMonthly) {
        this.allowMonthly = allowMonthly;
    }

    public Integer getLatelyFollower() {
        return latelyFollower;
    }

    public void setLatelyFollower(Integer latelyFollower) {
        this.latelyFollower = latelyFollower;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getSuperscript() {
        return superscript;
    }

    public void setSuperscript(String superscript) {
        this.superscript = superscript;
    }

    public Integer getSizetype() {
        return sizetype;
    }

    public void setSizetype(Integer sizetype) {
        this.sizetype = sizetype;
    }

    public Object getHighlight() {
        return highlight;
    }

    public void setHighlight(Object highlight) {
        this.highlight = highlight;
    }

    private String title;
    private String aliases;
    private String cat;
    private String author;
    private String site;
    private String cover;
    private String shortIntro;
    private String lastChapter;
    private Integer retentionRatio;
    private Integer banned;
    private Boolean allowMonthly;
    private Integer latelyFollower;
    private Integer wordCount;
    private String contentType;
    private String superscript;
    private Integer sizetype;
    private Object highlight;
}
