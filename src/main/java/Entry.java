import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.watson.crawler.Dao.BasicNet;
import org.watson.crawler.bean.OrignalInfo;
import org.watson.crawler.platform.tieba.TiebaUrlParse;
import org.watson.crawler.utils.RedisUtil;
import org.watson.crawler.utils.StaticUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
        String url = "http://tieba.baidu.com/f?kw=nba";
        String charset = "utf8";
        String html = basicNet.getHtml(url, charset);
        //String s = baseNet.encodeUrl(url, StaticUtil.GB2312);
        String urlEncode = StaticUtil.chineseEncode(url);
        TiebaUrlParse parse = new TiebaUrlParse();
        OrignalInfo orignalInfo = new OrignalInfo();
        orignalInfo.setType(0);
        orignalInfo.setUrl(url);
        orignalInfo.setRefer(null);
        parse.urlClassify(orignalInfo, html);

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
