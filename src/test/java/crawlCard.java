import com.campus.etdao.impl.CrawlCardEtDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author C_hao
 * @date 2018/10/29 9:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring*.xml"})
public class crawlCard {

    @Autowired
    CrawlCardEtDaoImpl crawlCardEtDao;
    @Test
    public void test(){
        String cookie=crawlCardEtDao.loginCampus("161511334","056916");
        crawlCardEtDao.obtainCampus(cookie,"20171001","20180302");
    }
}
