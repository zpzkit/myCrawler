package org.watson.crawler.platform.chinadaily;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import jdk.nashorn.internal.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.watson.crawler.bean.ArticleElement;
import org.watson.crawler.bean.OriginalInfo;
import org.watson.crawler.platform.HtmlParse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zp on 2016/12/26.
 */
public class DailyHtmlParse implements HtmlParse<OriginalInfo>{
    private static Logger logger = LoggerFactory.getLogger(DailyHtmlParse.class);

    @Override
    public List<String> getUrls(String url, String srcHtmlPage) {
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
        //System.out.println(pageUrlList.toString());
        return pageUrlList;
    }

    @Override
    public ArticleElement getAllElement(String url, String srcHtmlPage) {
        return null;
    }

    @Override
    public String getHtmlContent(String url, String srcHtmlPage) {

        return null;
    }

    @Override
    public String getAuther(String url, String srcHtmlPage) {
        Document parse = Jsoup.parse(srcHtmlPage);
        String author = parse.select("#Title_e > span.block.blueTxt.mb10").text();

        return author;
    }

    public boolean ifIncludeAuthor(String url, String srcHtmlPage){
        if (srcHtmlPage.toLowerCase().contains("zhou xingzuo".toLowerCase())){
            return true;
        }
        return false;
    }
}
