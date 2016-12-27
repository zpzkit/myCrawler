package org.watson.crawler.Dao;

import org.jsoup.nodes.Document;

/**
 * Created by watson zhang on 16/9/28.
 */
public interface BasicCrawler<T> {

    public String getHtml(String url, String charSet);

    public Document getPageDocument(String url);

}
