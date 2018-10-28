package cc.zsakvo.yueduhelper.server.zssq;

import com.alibaba.fastjson.JSON;


import cc.zsakvo.yueduhelper.classes.BookDetail;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetBookDetail {

    private String id;

    public GetBookDetail(String id){
        this.id = id;
    }

    public String init(){
       try{
           StringBuilder html = new StringBuilder();
           OkHttpClient client = new OkHttpClient();
           Request request = new Request.Builder()
                   .url("http://api.zhuishushenqi.com/book/"+id)
                   .build();
           Response response = client.newCall(request).execute();
           assert response.body() != null;
           BookDetail bd = JSON.parseObject(response.body().string(),BookDetail.class);
           html.append("<div>")
                   .append(bd.getTitle())
                   .append("</div>")
                   .append("<div>")
                   .append(bd.getCover().replace("/agent/","").replace("%3A",":").replace("%2F","/"))
                   .append("</div>")
                   .append("<div>")
                   .append("chapterlist?cid=")
                   .append(id)
                   .append("</div>")
                   .append("<div>")
                   .append(bd.getLongIntro())
                   .append("</div>")
                   .append("<div>")
                   .append(bd.getAuthor())
                   .append("</div>")
                   .append("<div>")
                   .append(bd.getCat())
                   .append("</div>");
           return html.toString();

       }catch (Exception e){
           e.printStackTrace();
           return null;
       }
    }
}
