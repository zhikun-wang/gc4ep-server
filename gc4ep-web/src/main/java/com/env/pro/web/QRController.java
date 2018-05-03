package com.env.pro.web;

import com.env.pro.util.QRUtil;
import com.env.pro.web.vo.RestResponse;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 Created by Zhikun on 13/11/2017. */
@Controller
@RequestMapping("qr")
public class QRController {

    @Autowired
    QRUtil qrTool;

    @RequestMapping(value = "encode", method = RequestMethod.POST)
    public void encode(@RequestBody String body, HttpServletRequest request, HttpServletResponse response) {
        try {
            qrTool.encodeQR(body, response.getOutputStream());
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "encode/{id}",method = RequestMethod.GET)
    public void encodeId(@PathVariable String id, HttpServletResponse response) {
        try {
            if("apkDownload".equalsIgnoreCase(id)){
                id="http://172.168.20.2:8011/apk/app-debug.apk";
//                id="http://192.168.10.186:10000/temp/temp/app-debug.apk";
            }

            qrTool.encodeQR(id, response.getOutputStream());
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "decode",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse decode(@RequestBody Map body) {
        try {
            String content = qrTool.decoderQRFromBase64(body.get("img").toString());
            RestResponse rest = RestResponse.success(content);
            return rest;
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
            return RestResponse.failure(e.getMessage(), "10001");
        }
    }


}
