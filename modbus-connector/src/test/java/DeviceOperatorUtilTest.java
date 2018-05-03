import com.env.pro.util.DeviceOperatorUtil;
import com.env.pro.util.SocketServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 Created by Zhikun on 09/01/2018. */
public class DeviceOperatorUtilTest {


    @Test
    public void testCrc() {
        Assert.assertEquals("D5F5", DeviceOperatorUtil.crc("011700020190"));
        Assert.assertEquals("0DF8", DeviceOperatorUtil.crc("01050008FF00"));
        Assert.assertEquals("5C38", DeviceOperatorUtil.crc("01050009FF00"));
    }

    private String ip = "172.168.20.20";

    @Before
    public void before() throws InterruptedException {
        SocketServer.start(10502);
        Thread.sleep(2000);
    }

    @Test
    public void testQuery() throws IOException {
        System.out.println("begin send");
        DeviceOperatorUtil.query(ip);
    }

    @Test
    public void testBagOut() throws IOException {
        System.out.println("begin send");
        DeviceOperatorUtil.bagOut(ip);
    }

    @Test
    public void testBagIn() throws IOException {
        System.out.println("begin send");
        DeviceOperatorUtil.bagIn(ip);
    }

    @Test
    public void testGet() throws IOException {
        int second= DeviceOperatorUtil.getPaperTime(ip);
        System.out.println("time == "+ second);
    }

    @Test
    public void testSet() throws IOException, InterruptedException {
        Thread.sleep(2000);
        DeviceOperatorUtil.setPaperTime(ip,2);
    }

}
