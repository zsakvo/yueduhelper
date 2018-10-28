package cc.zsakvo.yueduhelper.server.zssq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cc.zsakvo.yueduhelper.classes.Chapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetContent {

    private String link;

    public GetContent(String link){
        this.link = link;
    }

    public String init(){
        try{
            StringBuilder html = new StringBuilder();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://chapter2.zhuishushenqi.com/chapter/"+link)
                    .build();
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            JSONObject object = JSONObject.parseObject(response.body().string());
            JSONObject chapterObject = object.getJSONObject("chapter");
            Chapter cp = JSON.parseObject(chapterObject.toString(),Chapter.class);
            html.append("<div id=\"content\">");
            html.append(cp.getBody().replaceAll("[\\n\\r]", "<br><br>"));
            html.append("</div>");
            return html.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
