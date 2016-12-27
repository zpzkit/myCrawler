package org.watson.crawler.platform;

import java.util.List;

/**
 * Created by zp on 2016/12/26.
 */
public interface HtmlParse<T> {

    public List<T> getUrls(String url, String srcHtmlPage);
    public String getHtmlContent(String url, String srcHtmlPage);
}
