package cc.zsakvo.yueduhelper.server;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cc.zsakvo.yueduhelper.server.jjcs.GetBookDetail_b;
import cc.zsakvo.yueduhelper.server.jjcs.GetContent_b;
import cc.zsakvo.yueduhelper.server.jjcs.SearchBook_b;
import cc.zsakvo.yueduhelper.server.zssq.GetBookDetail;
import cc.zsakvo.yueduhelper.server.zssq.GetChapterList;
import cc.zsakvo.yueduhelper.server.zssq.GetContent;
import cc.zsakvo.yueduhelper.server.zssq.GetLastChapter;
import cc.zsakvo.yueduhelper.server.zssq.SearchBook;
import fi.iki.elonen.NanoHTTPD;

import static cc.zsakvo.yueduhelper.HelperService.TAG;

public class HelperServer extends NanoHTTPD  {

    private String ids;


    public enum Status implements Response.IStatus{
        REQUEST_ERROR(500, "请求失败"),
        REQUEST_ERROR_API(501, "无效的请求接口"),
        REQUEST_ERROR_CMD(502, "无效命令");

        private final int requestStatus;
        private final String description;

        Status(int requestStatus, String description) {
            this.requestStatus = requestStatus;
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public int getRequestStatus() {
            return requestStatus;
        }
    }

    public HelperServer(int port) {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session){
        String uri = session.getUri();
        Map<String,String> headers = session.getHeaders();
        try{
            session.parseBody(new HashMap<String, String>());
        }catch (IOException | ResponseException e){
            e.printStackTrace();
        }

        String html,bookName,page,bid,cid,link;
        String[] sbr;
        int cp;
        List<String> lcs;
        Log.e(TAG, "serve: "+uri );
        switch (uri){
            case "/zhuishushenqi/search":
                bookName = getParam(session,"bookname");
                sbr = new SearchBook(bookName).init();
                html = sbr[0];
                ids = sbr[1];
                cp = 0;
                lcs = new GetLastChapter(ids).init();
                for (String lc:lcs){
                    html = html.replace("lastchapter"+cp,lc);
                    cp++;
                }
                break;
            case "/zhuishushenqi/bookdetail":
                bid = getParam(session,"bid");
                html = new GetBookDetail(bid).init();
                break;
            case "/zhuishushenqi/chapterlist":
                cid = getParam(session,"cid");
                html = new GetChapterList(cid).init();
                break;
            case  "/zhuishushenqi/chapter":
                link = Objects.requireNonNull(getParam(session, "link"))
                        .replace("*","=")
                        .replace(",","&")
                        .replace(":","%3a")
                        .replace("/","%2f")
                        .replace(" ", "%20")
                        .replace("?", "%3F");
                html = new GetContent(link).init();
                break;
            case "/jiujiucangshu/search":
                bookName = getParam(session,"bookname");
                page = getParam(session,"page");
                html = new SearchBook_b(bookName,page).init();
                break;
            case "/jiujiucangshu/bookdetail":
                bid = getParam(session,"bid");
                html = new GetBookDetail_b(bid).init();
                break;
            case "/jiujiucangshu/chapterlist":
                cid = getParam(session,"cid");
                html = new GetChapterList(cid).init();
                break;
            case  "/jiujiucangshu/chapter":
                link = Objects.requireNonNull(getParam(session, "link"));
                html = new GetContent_b(link).init();
                break;
                default:
                    html ="欢迎使用阅读助手 By zsakvo";
                    break;
        }
        return newFixedLengthResponse(html);
    }

    private String getParam(IHTTPSession session,String key){
        try{
            Map<String, List<String>> params = session.getParameters();
            return Objects.requireNonNull(params.get(key)).get(0);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
