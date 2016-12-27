package org.watson.crawler.platform;

import org.watson.crawler.bean.ArticleElement;

import java.util.List;

/**
 * Created by zp on 2016/12/26.
 */
public interface HtmlParse<T> {

    public List<String> getUrls(String url, String srcHtmlPage);

    public ArticleElement getAllElement(String url, String srcHtmlPage);

    public String getHtmlContent(String url, String srcHtmlPage);
    public String getAuther(String url, String srcHtmlPage);
}
