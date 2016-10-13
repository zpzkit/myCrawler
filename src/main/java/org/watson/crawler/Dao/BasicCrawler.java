package org.watson.crawler.Dao;

/**
 * Created by watson zhang on 16/9/28.
 */
public interface BasicCrawler<T> {

    public String getHtml(String url, String charSet);


}
