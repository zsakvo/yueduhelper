package cc.zsakvo.yueduhelper.server.zssq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import cc.zsakvo.yueduhelper.classes.BookSource;
import cc.zsakvo.yueduhelper.classes.ChapterList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetChapterList {

    private String cid;

    public GetChapterList(String cid){
        this.cid = cid;
    }

    public String init(){
        try{
            StringBuilder html = new StringBuilder();
            html.append("<li id=\"list\">");
            OkHttpClient client = new OkHttpClient();

            Request urlrequest = new Request.Builder()
                    .url("http://api.zhuishushenqi.com/atoc?view=summary&book="+cid)
                    .build();
            Response urlresponse = client.newCall(urlrequest).execute();
            assert urlresponse.body() != null;
            JSONArray sourceArray = JSONArray.parseArray(urlresponse.body().string());
            String id = "";
            BookSource bs;
            for (Object jsonObject:sourceArray){
                bs = JSON.parseObject(jsonObject.toString(),BookSource.class);
                if(bs.getSource().equals("my176")){
                    id = bs.get_id();
                    break;
                }
            }
            Request request = new Request.Builder()
                    .url("http://api.zhuishushenqi.com/atoc/"+id+"?view=chapters")
                    .build();
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            JSONObject object = JSONObject.parseObject(response.body().string());
            JSONArray booksArray = object.getJSONArray("chapters");
            for (Object jsonObject:booksArray) {
                ChapterList cl = JSON.parseObject(jsonObject.toString(),ChapterList.class);
                String clink = cl.getLink().replace(" ", "%20").replace("?", "%3F").replace("=","*").replace("&",",");
                String cname = cl.getTitle();
                html.append("<dd><a href=\"chapter?link=")
                        .append(clink)
                        .append("\">")
                        .append(cname)
                        .append("</a></dd>");
            }
            html.append("</li>");
            return html.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
