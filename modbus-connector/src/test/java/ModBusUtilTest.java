import com.env.pro.util.ModbusUtil;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.sero.util.queue.ByteQueue;
import org.junit.Test;

/**
 Created by Zhikun on 27/12/2017. */
public class ModBusUtilTest {

    @Test
    public void test() throws ModbusTransportException, ErrorResponseException, ModbusInitException {

        short[] values = new short[]{0x08,0xff,0x00,0xD,0x8,0x08,0xff,0x00,0xD,0x8};
        ByteQueue result =ModbusUtil.modBusOnTCP("10.211.55.4", 502, 5, 0, values);
        System.out.println(result);

    }


    @Test
    public void test2() throws ModbusInitException, ErrorResponseException, ModbusTransportException {
        ByteQueue result =ModbusUtil.modbusTCP("10.211.55.4",502,1,0,3);
        System.out.println(result);
    }

    @Test
    public void test3() throws ModbusTransportException {




    }

}
