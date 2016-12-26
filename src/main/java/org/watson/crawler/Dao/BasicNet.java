package org.watson.crawler.Dao;

import org.apache.http.client.CookieStore;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.watson.crawler.bean.OriginalInfo;
import org.watson.crawler.utils.ExceptionUtil;
import org.watson.crawler.utils.StaticUtil;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by watson zhang on 16/9/19.
 */
public class BasicNet implements BasicCrawler<OriginalInfo>{
    private static Logger logger = LoggerFactory.getLogger(BasicNet.class);

    @Override
    public String getHtml(String url, String charSet) {
        CookieStore cookieStore = new BasicCookieStore();
        Cookie cookie = new BasicClientCookie("123", "123");
        cookieStore.addCookie(cookie);

        try {
            String s = Executor.newInstance().execute(
                    Request.Get(url).userAgent(StaticUtil.ua).connectTimeout(2000))
                    .returnContent().asString(Charset.forName(charSet));
           return s;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(ExceptionUtil.connectError.errorMessageFormat());
            return null;
        }
    }

}
