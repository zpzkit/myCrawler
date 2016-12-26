package org.watson.crawler.platform.chinadaily;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.watson.crawler.bean.OriginalInfo;
import org.watson.crawler.platform.HtmlParse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zp on 2016/12/26.
 */
public class DailyHtmlParse implements HtmlParse<OriginalInfo>{

    @Override
    public List<OriginalInfo> getUrls(String url, String srcHtmlPage) {
        Elements aTag = Jsoup.parse(srcHtmlPage).getElementsByTag("a");
        List<String> pageUrlList = new ArrayList<>();
        for (Element element:aTag){
            String href = element.attr("href");
            if (href.matches("http[s]?://www\\.chinadaily\\.com\\.cn/.*")){
                pageUrlList.add(href);
            }else if (href.contains("content")){
                String dailyHost = "http://www.chinadaily.com.cn/";
                if (!href.contains("http://")){
                    href = dailyHost.concat(href);
                    pageUrlList.add(href);
                }
            }
        }
        System.out.println(pageUrlList.toString());
        return null;
    }

    @Override
    public String getHtmlContent(String url, String srcHtmlPage) {
        return null;
    }
}
