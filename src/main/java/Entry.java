import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.watson.crawler.Dao.BasicNet;
import org.watson.crawler.platform.chinadaily.DailyHtmlParse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by watson zhang on 16/9/28.
 */
public class Entry {

    private static Logger logger = LoggerFactory.getLogger(BasicNet.class);

    private static String tiebaRedisHead = "u:s:t:";
    private static String chinaDailyRedisHead = "u:s:cd:";

    String charset = "utf8";
    DailyHtmlParse dailyHtmlParse;
    BasicNet basicNet;
    List<String> waitUrlList;
    List<String> catchUrlList;
    long startTime;
    long timeMinute;

    public Entry(){
        dailyHtmlParse = new DailyHtmlParse();
        basicNet = new BasicNet();
        waitUrlList = new ArrayList<>();
        catchUrlList = new ArrayList<>();
        startTime = System.currentTimeMillis();
    }


    public static void main(String[] args){

        BasicNet basicNet = new BasicNet();
        String url = "http://www.chinadaily.com.cn/";
        //String url = "http://www.chinadaily.com.cn/china/2016-04/15/content_24573534_4.htm";
        //String url = "http://www.chinadaily.com.cn/china/2016-12/26/content_27771941.htm";
        Entry entry = new Entry();
        entry.crawlerTask(url);


    }

    public void crawlerTask(String startUrl){
        if (startUrl == null || startUrl.isEmpty()){
            logger.error("start url is null");
            return;
        }
        String html = basicNet.getHtml(startUrl, charset);
        if (html == null){
            logger.error("get html error! url:{}", startUrl);
            return;
        }

        List<String> urls = dailyHtmlParse.getUrls(startUrl, html);
        waitUrlList.addAll(urls);
        if (dailyHtmlParse.ifIncludeAuthor(startUrl, html)){
            logger.info("zhouxingzuo_url_path:{}", startUrl);
        }

        while (!waitUrlList.isEmpty()){
            this.recursionCrawler(waitUrlList.get(0));

            if ((System.currentTimeMillis() - startTime)/60000  > timeMinute){
                timeMinute++;
                this.outputInfo();
            }
        }
    }

    public void recursionCrawler(String url){
        String html = basicNet.getHtml(url, charset);
        if (html == null){
            this.removeUrl(url);
            //logger.error("get html error! url:{}", url);
            return;
        }
        if (dailyHtmlParse.ifIncludeAuthor(url, html)){
            logger.info("zhouxingzuo_url_path:{}", url);
        }

        this.removeUrl(url);

        List<String> urls = dailyHtmlParse.getUrls(url, html);
        for (String s : urls) {
            if (!catchUrlList.contains(s) && !waitUrlList.contains(s)){
                waitUrlList.add(s);
            }
        }
    }

    public void removeUrl(String url){
        if (url == null || url.isEmpty()){
            return;
        }
        waitUrlList.remove(url);
        catchUrlList.add(url);
    }

    public void outputInfo(){
        logger.info("waitUrlList:{}", waitUrlList.size());
        logger.info("catchUrlList:{}", catchUrlList.size());
        logger.info("Time-consuming/m:{}", (System.currentTimeMillis() - startTime)/60000);
    }
}
