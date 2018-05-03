import com.env.pro.util.SocketServer;
import org.junit.Test;

/**
 Created by Zhikun on 12/01/2018. */
public class SocketServerTest {

    @Test
    public void test(){
        SocketServer socketServer = new SocketServer();
        socketServer.start(10502);
    }

}
