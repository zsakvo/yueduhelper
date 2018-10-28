package cc.zsakvo.yueduhelper.server.zssq;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.zsakvo.yueduhelper.classes.Book;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static cc.zsakvo.yueduhelper.HelperService.TAG;

public class SearchBook {

    private String bookName;

    public SearchBook(String bookName){
        this.bookName = bookName;
    }

    public String[] init(){
        try {
            StringBuilder ids = new StringBuilder();
            StringBuilder html = new StringBuilder();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://api.zhuishushenqi.com/book/fuzzy-search?query="+bookName)
                    .build();
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            JSONObject object = JSONObject.parseObject(response.body().string());
            JSONArray booksArray = object.getJSONArray("books");
            ids = new StringBuilder();
            html = new StringBuilder();
            int cp = 0;
            Book book;
            for (Object jsonObject:booksArray){
                book = JSON.parseObject(jsonObject.toString(),Book.class);
                if (!book.getContentType().equals("txt")){
                    continue;
                }
                ids.append(book.get_id()).append(",");
                html.append("<li>")
                        .append("<div>")
                        .append(book.getTitle())
                        .append("</div>")
                        .append("<div>")
                        .append("bookdetail?bid=")
                        .append(book.get_id())
                        .append("</div>")
                        .append("<div>")
                        .append(book.getCover().replace("/agent/","").replace("%3A",":").replace("%2F","/"))
                        .append("</div>")
                        .append("<div>")
                        .append(book.getAuthor())
                        .append("</div>")
                        .append("<div>")
                        .append(book.getCat())
                        .append("</div>")
                        .append("<div>")
                        .append("lastchapter")
                        .append(cp)
                        .append("</div>")
                        .append("<div>")
                        .append(book.getShortIntro())
                        .append("</div>")
                        .append("</li>");
                cp++;
            }
            ids.deleteCharAt(ids.length()-1);
            return new String[]{html.toString(),ids.toString()};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{null};
        }
    }
}
