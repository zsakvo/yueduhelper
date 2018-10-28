package cc.zsakvo.yueduhelper.server.jjcs;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;



public class GetContent_b {

    private String link;

    public GetContent_b(String link){
        this.link = link;
    }

    public String init(){
        String url = "http://www.99lib.net/book/"+link;
        StringBuilder html = new StringBuilder();
        try {
            Document document = Jsoup.connect(url).get();
            String client = document.select("meta[name=client]").attr("content");
            Element content = document.getElementById("content");
            content.select("strike,acronym,bdo,big,cite,site,code,dfn,kbd,q,s,samp,tt,u,var,cite,details,figure,footer").remove();
            String c = new Decode(content,client).init();
            html.append("<div id=\"content\">");
            html.append(c);
            html.append("</div>");
            return html.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
