package Dao;

import utils.ExceptionUtil;
import bean.SrcUrl;
import utils.StaticUtil;
import org.apache.http.client.CookieStore;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by watson zhang on 16/9/19.
 */
public class BasicNet{
    private static Logger logger = LoggerFactory.getLogger(BasicNet.class);

    static Comparator<SrcUrl> com = new Comparator<SrcUrl>() {
        @Override
        public int compare(SrcUrl o1, SrcUrl o2) {
            return o1.getType() - o2.getType();
        }
    };

    public static Queue<SrcUrl> urlQueue = new PriorityQueue<>(10, com);
    String hrefRge = "href=\"([^\":>]*)\"";
    String hostRge = "http://([^/]+)";


    public void getContent(String url, String charset){
        CookieStore cookieStore = new BasicCookieStore();
        Cookie cookie = new BasicClientCookie("123", "123");
        cookieStore.addCookie(cookie);

        Matcher m = Pattern.compile(hostRge).matcher(url);
        String host = null;
        if (m.find()){
            host = m.group(1);
        }

        if(host == null){
            logger.error("get host error host is null");
            return;
        }
        try {
            String s = Executor.newInstance().execute(
                    Request.Get(url).userAgent(StaticUtil.ua).connectTimeout(2000))
                    .returnContent().asString(Charset.forName(charset));
            m = Pattern.compile(hrefRge).matcher(s);
            while (m.find()){
                SrcUrl srcUrl = new SrcUrl();
                if (m.group(1).length() > 2){
                    srcUrl.setUrl(host + "/"+m.group(1));
                    srcUrl.setType(0);
                    srcUrl.setRefer("");
                    urlQueue.add(srcUrl);
                    System.out.println(srcUrl.getUrl());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(ExceptionUtil.connectError.errorMessageFormat());
        }

    }

    public String encodeUrl(String src, int type){
        String gb2312 = null;
        try {
            if (type == StaticUtil.GB2312){
                gb2312 = URLEncoder.encode(src, "gb2312");
            }else if (type == StaticUtil.UTF8){
                gb2312 = URLEncoder.encode(src, "utf8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return gb2312;
    }

    public String decodeUrl(String src, int type){
        String gb2312 = null;
        try {
            if (type == StaticUtil.GB2312){
                gb2312 = URLDecoder.decode(src, "gb2312");
            }else if (type == StaticUtil.UTF8){
                gb2312 = URLDecoder.decode(src, "utf8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return gb2312;
    }

    public String chineseEncode(String url){
        //String urlTmp = url;
        String Reg = "([\\u4E00-\\u9FEF]+)";
        Matcher matcher = Pattern.compile(Reg).matcher(url);
        while (matcher.find()){
            String matchString = matcher.group(1);
            String encodeChinese = this.encodeUrl(matchString, StaticUtil.GB2312);
            url = url.replace(matchString, encodeChinese);
        }
        return url;
    }

    public static void main(String[] args){

        logger.error("test");

        String fileName = "./saveFile.txt";
        BasicNet basicNet = new BasicNet();
        String url = "http://tieba.baidu.com";
        String charset = "utf8";
        basicNet.getContent(url, charset);
        //String s = baseNet.encodeUrl(url, StaticUtil.GB2312);
        String urlEncode = basicNet.chineseEncode(url);

        FileWriter file = null;
        try {
            File fileExists =new File(fileName);
            if(!fileExists.exists()){
                System.out.println("不存在");
                System.out.println(fileExists.createNewFile());
                fileExists.createNewFile();
            }

            file = new FileWriter(fileName);

            for (SrcUrl srcUrl : urlQueue) {
                if(srcUrl.getType() == 0){
                    file.write(srcUrl.getUrl());
                    file.write("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(urlEncode);
    }
}
