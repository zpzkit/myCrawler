import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.watson.crawler.Dao.BasicNet;
import org.watson.crawler.bean.OriginalInfo;
import org.watson.crawler.platform.chinadaily.DailyHtmlParse;
import org.watson.crawler.utils.RedisUtil;
import org.watson.crawler.utils.StaticUtil;

/**
 * Created by watson zhang on 16/9/28.
 */
public class Entry {

    private static Logger logger = LoggerFactory.getLogger(BasicNet.class);

    private static String tiebaRedisHead = "u:s:t:";

    public static void main(String[] args){
        RedisUtil redisUtil = RedisUtil.getInstance();
        logger.error("test");

        BasicNet basicNet = new BasicNet();
        String url = "http://www.chinadaily.com.cn/";
        String charset = "utf8";
        String html = basicNet.getHtml(url, charset);
        //String s = baseNet.encodeUrl(url, StaticUtil.GB2312);
        String urlEncode = StaticUtil.chineseEncode(url);
        OriginalInfo orignalInfo = new OriginalInfo();
        orignalInfo.setType(0);
        orignalInfo.setUrl(url);
        orignalInfo.setRefer(null);
        DailyHtmlParse dailyHtmlParse = new DailyHtmlParse();
        dailyHtmlParse.getUrls(url, html);

        //TiebaUrlParse parse = new TiebaUrlParse();
        //parse.urlClassify(orignalInfo, html);

/*
        List<OrignalInfo> homeUrls = parse.getHomeUrls(url, html);
        for (OrignalInfo homeUrl : homeUrls) {
            redisUtil.redis.hset(homeUrl.getUrl(), "type", String.valueOf(homeUrl.getType()));
            if (homeUrl.getRefer() != null){
                redisUtil.redis.hset(homeUrl.getUrl(), "refer", homeUrl.getRefer());
            }
        }
*/

        System.out.println(urlEncode);
    }
}
