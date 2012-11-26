package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class RandomImgGenerate {
    private static final int DEFAULT_WIDTH = 60;// 默认宽度
    private static final int DEFAULT_HEIGHT = 20;// 默认高度
    private static final String TAB_SPACE = "   ";
    private static final String EMPTY = "";
    private static final Random RANDOM = new Random();
    private static final String[] FONTS = {
                    "Ravie", "Antique Olive Compact", "Forte", "Wide Latin", "Gill Sans Ultra Bold"
    };

    public static void main(String[] args) throws IOException {
        FileOutputStream fout = new FileOutputStream("d:/a.jpeg");
        // System.err.println(getRandomStrImage(fout));
        System.err.println(getFormulaImage(fout, 60 + 40 * 1, 20));
        fout.flush();
        fout.close();
    }

    public static int getFormulaImage(OutputStream out) throws IOException {
        return getFormulaImage(out, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public static int getFormulaImage(OutputStream out, int width, int height) throws IOException {

        StringBuilder formula = new StringBuilder();
        int result = RANDOM.nextInt(10);

        formula.append(result);

        if (width < DEFAULT_WIDTH) {
            throw new IllegalArgumentException("width is to small");
        }

        for (int i = 0; i < 8; i++) {
            if (DEFAULT_WIDTH + i * 40 <= width) {
                int x = RANDOM.nextInt(10);
                result += x;

                formula.append(TAB_SPACE).append("+").append(TAB_SPACE).append(x);
            } else {
                break;
            }
        }
        generate(out, formula.toString(), width, height);

        return result;
    }

    public static String getRandomStrImage(OutputStream out) throws IOException {
        return getRandomStrImage(out, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public static String getRandomStrImage(OutputStream out, int width, int height) throws IOException {
        StringBuilder result = new StringBuilder();

        if (width < DEFAULT_WIDTH) {
            throw new IllegalArgumentException("width is to small");
        }
        for (int i = 0; i < width / 15; i++) {
            int r = RANDOM.nextInt(123);

            if ((r >= 65 && r <= 90) || (r >= 97 && r <= 122)) {
                result.append((char) r);
            } else {
                result.append(r % 10);
            }
            result.append(TAB_SPACE);
        }
        generate(out, result.toString(), width, height);

        return result.toString().toLowerCase().replace(TAB_SPACE, EMPTY);
    }

    private static void generate(OutputStream out, String str, int width, int height) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Random random = new Random();
        String[] chars = str.split(TAB_SPACE);
        Graphics graphics = image.getGraphics();

        graphics.setColor(getRandColor(160, 200));
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(getRandColor(160, 200));

        for (int i = 0; i < 155; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(12);
            int y2 = random.nextInt(12);

            graphics.drawLine(x1, y1, x1 + x2, y1 + y2);
        }

        int fontX = width / chars.length;
        int fontSize = height / 5 * 4;
        for (int i = 0; i < chars.length; i++) {
            graphics.setFont(getFont(fontSize));
            graphics.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            graphics.drawString(chars[i], fontX * i + 3, 15);
        }

        graphics.dispose();
        ImageIO.write(image, "JPEG", out);
    }

    private static Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private static Font getFont(int size) {
        return new Font(FONTS[RANDOM.nextInt(FONTS.length)], Font.PLAIN, size);
    }
}
