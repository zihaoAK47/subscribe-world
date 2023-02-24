package com.niugiaogiao.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import sun.font.FontDesignMetrics;

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

    private static final int bgColor = 0xFFFFFF;
    private static final int fontColor = 0x0181818;
    private static  int imageWidth = 2048;
    private static  int imageHeight = 1500;

    public static final String fontStyle = "XHei Intel";

    private ImagesUtil() {
    }

    public void textToImage(List<String> texts, File file) {
        confImage(texts);
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

    public void confImage(final List<String> texts) {
        if (ObjectUtils.isEmpty(texts) || texts.isEmpty()) {
            return;
        }

        FontMetrics fm = FontDesignMetrics.getMetrics(new Font(fontStyle, Font.BOLD, 20));
        int maxWidth = 0;
        int maxHeight = 50;
        for (String item : texts) {
            if (fm.stringWidth(item) > maxWidth) {
                maxWidth = fm.stringWidth(item);
            }
            maxHeight += 35;
        }
        imageWidth = maxWidth + 200;
        imageHeight = maxHeight;
    }

    private Graphics2D getGraphics2D(BufferedImage bufferedImage) {
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(new Color(fontColor, true));
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.setColor(new Color(bgColor));
        graphics.drawRect(0, 0, imageWidth - 1, imageHeight - 1);
        graphics.setFont(new Font(fontStyle, Font.BOLD, 20));
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return graphics;
    }

    private void fillGraphics2D(Graphics2D graphics2D, List<String> texts) throws IOException {
        int lingHeight = 40;
        for (String item : texts) {
            int charNum;
            int textCount = 0;
            char[] readFun = new char[imageWidth];
            StringReader reader = new StringReader(item);

            while ((charNum = reader.read()) != -1) {
                readFun[textCount++] = (char) charNum;
                if (readFun[imageWidth - 1] != 0) {
                    graphics2D.drawString(new String(readFun), 20, lingHeight); //20和i值是图片上的x,y坐标
                    lingHeight = lingHeight + 25; //换行，通过增加行高实现
                    textCount = 0;
                    Arrays.fill(readFun, '\0');
                }
            }
            graphics2D.drawString(new String(readFun), 20, lingHeight);
            lingHeight = lingHeight + 35;
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
