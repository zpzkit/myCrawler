package Dao;

import bean.SrcUrl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by watson zhang on 16/9/27.
 */
public class BasicParse {

    public void parseUrl(SrcUrl srcUrl){
        if (srcUrl == null){
            return;
        }
        String tiebaUrlRge = "kw=[^&]+";

        Matcher matcher = Pattern.compile(tiebaUrlRge).matcher(srcUrl.getUrl());
        if (matcher.find()){
            srcUrl.setType(1);
        }else {
            srcUrl.setType(2);
        }
        return;
    }
}
