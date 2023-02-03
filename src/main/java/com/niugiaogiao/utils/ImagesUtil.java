package com.niugiaogiao.utils;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

@Component
public final class ImagesUtil {

    private static final int bgColor = 0x9f9f5f;
    private static final int imageWidth = 1024;
    private static final int imageHeight = 1300;

    private ImagesUtil() {
    }

    public void textToImage(List<String> texts, File file) {
        BufferedImage bufImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = getGraphics2D(bufImage);
        try {
            fillGraphics2D(graphics2D, texts);
            writeFile(bufImage, file);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            graphics2D.dispose();
        }
    }

    private Graphics2D getGraphics2D(BufferedImage bufferedImage) {
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(new Color(bgColor));
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.setColor(new Color(0x373737));
        graphics.drawRect(0, 0, imageWidth - 1, imageHeight - 1);
        graphics.setFont(new Font("宋体", Font.BOLD, 14));
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return graphics;
    }

    private void fillGraphics2D(Graphics2D graphics2D, List<String> texts) throws IOException {
        int lingHeight = 0;
        for (String item : texts) {
            int charNum;
            int textCount = 0;
            char[] readFun = new char[100];
            StringReader reader = new StringReader(item);
            while ((charNum = reader.read()) != -1) {
                readFun[textCount++] = (char) charNum;
                if (readFun[99] != 0) {
                    graphics2D.drawString(new String(readFun), 10, lingHeight); //20和i值是图片上的x,y坐标
                    lingHeight = lingHeight + 25; //换行，通过增加行高实现
                    textCount = 0;
                    Arrays.fill(readFun, '\0');
                }
            }
            lingHeight = lingHeight + 25;
            graphics2D.drawString(new String(readFun), 20, lingHeight);
        }
    }

    private void writeFile(BufferedImage bufferedImage, File fileName) throws IOException {
        if (!fileName.exists()) {
            if (!fileName.createNewFile()) {
                return;
            }
        }
        ImageIO.write(bufferedImage, "JPEG", fileName);
    }
}
