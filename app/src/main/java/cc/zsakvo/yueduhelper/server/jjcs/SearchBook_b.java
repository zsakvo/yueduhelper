package cc.zsakvo.yueduhelper.server.jjcs;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static cc.zsakvo.yueduhelper.HelperService.TAG;

public class SearchBook_b {

    private String bookName;
    private String page;

    public SearchBook_b(String bookName, String page){
        this.bookName = bookName;
        this.page = page;
    }

    public String init(){
        StringBuilder html = new StringBuilder();
        try {
            Document document = Jsoup.connect("http://www.99lib.net/book/search.php?type=all&keyword="+bookName+"&page=" +page).get();
            Element list_box = document.getElementsByClass("list_box").get(0);
            for (Element e:list_box.children()){
                html.append("<li>")
                        .append("<div>")
                        .append(e.select("h2").get(0).text())
                        .append("</div>")
                        .append("<div>")
                        .append("bookdetail?bid=")
                        .append(e.select("a").get(0).attr("href").split("/")[2])
                        .append("</div>")
                        .append("<div>")
                        .append(e.select("img").get(0).attr("src"))
                        .append("</div>")
                        .append("<div>")
                        .append(e.select("h4").get(0).select("a").get(0).text())
                        .append("</div>")
                        .append("<div>")
                        .append(e.select("h4").get(1).select("a").get(0).text())
                        .append("</div>")
                        .append("<div>")
                        .append("   ")
                        .append("</div>")
                        .append("<div>")
                        .append(e.getElementsByClass("intro").get(0).text())
                        .append("</div>")
                        .append("</li>");
            }
            return html.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
