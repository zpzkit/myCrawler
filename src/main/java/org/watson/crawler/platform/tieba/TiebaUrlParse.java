package org.watson.crawler.platform.tieba;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.watson.crawler.bean.OrignalInfo;
import org.watson.crawler.utils.StaticUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by watson zhang on 16/9/28.
 */
public class TiebaUrlParse {
    private static Logger logger = LoggerFactory.getLogger(TiebaUrlParse.class);
    String hrefRge = "href=\"([^\":>]*)\"";
    String hostRge = "http://([^/]+)";

    public List<OrignalInfo> getHomeUrls(String url, String srcHtmlPage){

        Matcher m = Pattern.compile(hostRge).matcher(url);
        String host = null;
        if (m.find()){
            host = m.group(1);
        }

        if(host == null){
            logger.error("get host error host is null");
            return null;
        }
        m = Pattern.compile(hrefRge).matcher(srcHtmlPage);
        List<OrignalInfo> srcUrls = new ArrayList<>();
        while (m.find()){
            OrignalInfo srcUrl = new OrignalInfo();
            if (m.group(1).length() > 2){
                srcUrl.setUrl(host + "/"+m.group(1));
                srcUrl.setType(0);
                srcUrl.setRefer(url);
                srcUrls.add(srcUrl);
            }
        }
        return null;
    }

    public void urlClassify(OrignalInfo orignalInfo, String srcHtmlPage){
        Document document = Jsoup.parse(srcHtmlPage);
        String kwRge = "kw=([^/]+)";
        Elements select = document.select("head > meta:nth-child(2)");
        String furl = select.attr("furl");
        String fname = select.attr("fname");

        Matcher m = Pattern.compile(kwRge).matcher(furl);
        String kw = null;
        if (m.find()){
            kw = m.group(1);
        }

        if (StaticUtil.encodeUrl(fname, StaticUtil.UTF8).equals(kw) || StaticUtil.encodeUrl(fname, StaticUtil.UTF8).equals(kw)){
            orignalInfo.setType(CommonData.TOPICHEAD);
        }

        Elements select1 = document.select("head > script:nth-child(9)");
        String node = select1.get(0).childNodes().get(0).outerHtml();
        if (node.contains("PageData.forum")){
            orignalInfo.setType(CommonData.TIEBAHEAD);
        }
        System.out.println(furl + fname);
        return ;
    }
}
