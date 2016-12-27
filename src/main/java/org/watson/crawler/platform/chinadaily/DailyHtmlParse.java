package org.watson.crawler.platform.chinadaily;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.watson.crawler.bean.OriginalInfo;
import org.watson.crawler.platform.HtmlParse;

import java.util.List;

/**
 * Created by watson zhang on 2016/12/26.
 */
public class DailyHtmlParse implements HtmlParse<OriginalInfo>{

    public DailyHtmlParse(){

    }

    @Override
    public List<OriginalInfo> getUrls(String url, String srcHtmlPage) {
        //Document document = Jsoup.parse(srcHtmlPage).getElementsByTag("a").attr("href");
        //System.out.println(document.toString());
        return null;
    }

    @Override
    public String getHtmlContent(String url, String srcHtmlPage) {
        return null;
    }
}
