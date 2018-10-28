package cc.zsakvo.yueduhelper.server.jjcs;



import android.util.Log;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static cc.zsakvo.yueduhelper.HelperService.TAG;

public class Decode {

    private Element element;
    private String client;

    public Decode(Element element, String client){
        this.element = element;
        this.client = client;
    }

    public String init(){
        int star = 0;
        Elements childNotes = element.children();
        int childNodesLength = childNotes.size();
        for (int i = 0; i < childNodesLength; i++) {
            if (childNotes.get(i).tagName().equals("h2")) {
                star = i + 1;
            }
            if (childNotes.get(i).tagName().equals("div") && !childNotes.get(i).className().equals("chapter")) {
                break;
            }
        }
        return load(childNotes,star);
    }

    private String base64(String a){
        String map = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        StringBuilder b = new StringBuilder();
        StringBuilder binary  = new StringBuilder();
        String[] sc = {"00000","0000","000","00","0",""};
        List<String> binarys = new ArrayList<>();
        for (int i = 0; i < a.length(); i++) {
            if (a.substring(i, i+1).equals("=")) {
                break;
            }
            String c = Integer.toBinaryString(map.indexOf(a.charAt(i)));
            binary.append(sc[c.length() - 1]).append(c);
        };
        for (int i=0;i<binary.length();i+=8){
            if (i+8>=binary.length()){
                binarys.add(binary.substring(i));
            }else {
                binarys.add(binary.substring(i, i + 8));
            }
        }
        for (int i = 0; i < binarys.size(); i++) {
            b.append("").append((char) (Integer.parseInt(binarys.get(i), 2)));
        };
        return b.toString();
    }

    private String load(Elements childNotes,int star){
        String t = base64(client).replaceAll("[^0-9%]","");
        String[] e = t.split("%");
        int k = 0;
        int size =0;
        for (String anE : e) {
            int tmp = 0;
            if (Integer.parseInt(anE) < 3) {
                k++;
                tmp = Integer.parseInt(anE);
            } else {
                tmp = Integer.parseInt(anE) - k;
                k+=2;
            }
            if (size < tmp) {
                size = tmp;
            }
        }
        StringBuilder content = new StringBuilder();
        Element[] childNode = new Element[size+1];
        int j = 0;
        for (int i = 0; i < e.length;i++) {
            int z= Integer.parseInt(e[i]);
            if (z < 3) {
                childNode[z] = childNotes.get(i+star);
                j++;
            } else {
                childNode[z-j] = childNotes.get(i+star);
                j+=2;
            }
        }
        for (int i =0;i<childNode.length;i++){
            if (childNode[i]!=null) {
                content.append(childNode[i].text()+"<br>");
                }
            }
        return content.toString();
        }
    }


