package cc.zsakvo.yueduhelper.server.zssq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;


import cc.zsakvo.yueduhelper.classes.LastChapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetLastChapter {

    private String ids;

    public GetLastChapter(String ids){
       this.ids = ids;
    }

    public List<String> init(){
        try{
            List<String> lcs = new ArrayList<>();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://api05iye5.zhuishushenqi.com/book?view=updated&id="+ids)
                    .build();
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            JSONArray lpArray = JSONArray.parseArray(response.body().string());
            LastChapter lc;
            for (Object jsonOject:lpArray){
                lc = JSON.parseObject(jsonOject.toString(),LastChapter.class);
                lcs.add(lc.getLastChapter());
            }
            return lcs;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
