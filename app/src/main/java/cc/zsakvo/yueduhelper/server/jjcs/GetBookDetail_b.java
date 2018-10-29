package cc.zsakvo.yueduhelper.server.jjcs;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import static cc.zsakvo.yueduhelper.HelperService.TAG;

public class GetBookDetail_b {
    private String id;

    public GetBookDetail_b(String id){
        this.id = id;
    }

    public String init(){
        StringBuilder html = new StringBuilder();
        try {
            Document document = Jsoup.connect("http://www.99lib.net/book/"+id+"/index.htm").get();

            Element book_info = document.getElementById("book_info");

            Element dir = document.getElementById("dir");

            html.append("<div>")
                    .append(book_info.selectFirst("h2"))
                    .append("</div>")
                    .append("<div>")
                    .append(book_info.selectFirst("img").attr("src"))
                    .append("</div>")
                    .append("<div>")
                    .append("</div>")
                    .append("<div>")
                    .append(book_info.getElementsByClass("intro").text())
                    .append("</div>")
                    .append("<div>")
                    .append(book_info.selectFirst("h4").selectFirst("a").text())
                    .append("</div>")
                    .append("<div>");
            for (Element e:book_info.select("h4").get(1).children()){
                html.append(e.text()).append(" ");
            }
            html.append("</div>");
            html.append("<div id=\"list\">");
            String[] urlParam;
            String volume ="";
            for (Element e:dir.children()){
                if (e.tagName().equals("dt")){
                    volume = e.text();
                }else {
                    urlParam = e.selectFirst("a").attr("href").split("/");
                    String url = "chapter?link="+urlParam[2]+"/"+urlParam[3];
                    html.append("<dd>")
                            .append("<a href=\"")
                            .append(url)
                            .append("\">")
                            .append(volume)
                            .append("  ")
                            .append(e.selectFirst("a").text())
                            .append("</a>")
                            .append("</dd>");
                }
            }
            html.append("</div>");
            return html.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
