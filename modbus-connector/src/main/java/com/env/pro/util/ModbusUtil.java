package com.env.pro.util;


import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.msg.*;
import com.serotonin.modbus4j.sero.util.queue.ByteQueue;

/**
 Created by Zhikun on 27/12/2017. */
public class ModbusUtil {

    public static ByteQueue modBusOnTCP(String ip, int port, int slaveId, int start, short[] values) throws ModbusTransportException, ModbusInitException {
        ModbusFactory modbusFactory = new ModbusFactory();
        // 设备ModbusTCP的Ip与端口，如果不设定端口则默认为502
        IpParameters params = new IpParameters();
        params.setHost(ip);
        if (502 != port) {
            params.setPort(port);
        }// 设置端口，默认502
        ModbusMaster tcpMaster = null;
        // 参数1：IP和端口信息 参数2：保持连接激活
        tcpMaster = modbusFactory.createTcpMaster(params, true);
        tcpMaster.init();
        System.out.println("===============begin modbus tcp============");
        WriteRegistersRequest request = new WriteRegistersRequest(slaveId, start, values);
        WriteRegistersResponse response = (WriteRegistersResponse) tcpMaster.send(request);
        if (response.isException()) {
            System.out.println("Exception response: message=" + response.getExceptionMessage());
            throw new ModbusTransportException(response.getExceptionMessage());
        } else {
            System.out.println("Success");
            ByteQueue byteQueue = new ByteQueue(12);
            response.write(byteQueue);
            System.out.println("功能码:" + response.getFunctionCode());
            System.out.println("从站地址:" + response.getSlaveId());
            System.out.println("收到的响应信息大小:" + byteQueue.size());
            System.out.println("收到的响应信息值:" + byteQueue);
            return byteQueue;
        }

    }

    public static ByteQueue modbusTCP(String ip, int port, int slaveId, int start, int readLength) {
        ModbusFactory modbusFactory = new ModbusFactory();
        // 设备ModbusTCP的Ip与端口，如果不设定端口则默认为502
        IpParameters params = new IpParameters();
        params.setHost(ip);
        if (502 != port) {
            params.setPort(port);
        }//设置端口，默认502
        ModbusMaster tcpMaster = null;
        tcpMaster = modbusFactory.createTcpMaster(params, true);
        try {
            tcpMaster.init();
            System.out.println("===============" + 1111111);
        } catch (ModbusInitException e) {
            e.printStackTrace();
            return null;
        }
        ModbusRequest modbusRequest = null;
        try {
            modbusRequest = new ReadInputRegistersRequest(slaveId,start,readLength);
//            modbusRequest = new ReadHoldingRegistersRequest(slaveId, start, readLength);//功能码03
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        }
        ModbusResponse modbusResponse = null;
        try {
            modbusResponse = tcpMaster.send(modbusRequest);
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        }
        ByteQueue byteQueue = new ByteQueue(readLength);
        modbusResponse.write(byteQueue);
        System.out.println("功能码:" + modbusRequest.getFunctionCode());
        System.out.println("从站地址:" + modbusRequest.getSlaveId());
        System.out.println("收到的响应信息大小:" + byteQueue.size());
        System.out.println("收到的响应信息值:" + byteQueue);
        return byteQueue;
    }

}
