package com.env.pro.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 Created by Zhikun on 13/11/2017. */
@Component
public class QRUtil {

    private int DEFAULT_WIDTH = 200;
    private int DEFAULT_HEIGHT = 200;

    private String DEFAULT_FORMAT = "png";// 图像类型

    public void encodeQR(String content, OutputStream os, String format, int width, int height) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        MatrixToImageWriter.writeToStream(bitMatrix, format, os);
    }

    public void encodeQR(String content, OutputStream os) throws IOException, WriterException {
        encodeQR(content, os, DEFAULT_FORMAT, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public String decoderQRFromBase64(String base64Str) throws IOException, NotFoundException {
        byte[] bytes = this.base64Stream(base64Str);
        InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage bufImg = ImageIO.read(is);

        LuminanceSource source = new BufferedImageLuminanceSource(bufImg);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
        return result.getText();
    }

    public byte[] base64Stream(String imgStr) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = null;
        // Base64解码
        bytes = decoder.decodeBuffer(imgStr);
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {// 调整异常数据
                bytes[i] += 256;
            }
        }
        return bytes;
    }


}
