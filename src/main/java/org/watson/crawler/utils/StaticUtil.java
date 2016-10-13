package org.watson.crawler.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by watson zhang on 16/9/27.
 */
public class StaticUtil {

    public static int UTF8 = 1;
    public static int GB2312 = 2;
    public static String ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/536.36 (KHTML, like Gecko) Chrome/45.0.2452.101 Safari/536.36";

    public static String encodeUrl(String src, int type){
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

    public static String decodeUrl(String src, int type){
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


    public static String chineseEncode(String url){
        //String urlTmp = url;
        String Reg = "([\\u4E00-\\u9FEF]+)";
        Matcher matcher = Pattern.compile(Reg).matcher(url);
        while (matcher.find()){
            String matchString = matcher.group(1);
            String encodeChinese = StaticUtil.encodeUrl(matchString, StaticUtil.GB2312);
            url = url.replace(matchString, encodeChinese);
        }
        return url;
    }
}
