package edu.ucla.cs;

import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.Graphics2D;

public class Util {
    public static BufferedImage getRandomBufferedImage(SourceOfRandomness random, int width, int height) {
        int[] data = getRandomImage(random, width, height);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++)
                image.setRGB(x, y, data[x * height + y]);
        return image;
    }

    public static int[] getRandomImage(SourceOfRandomness random, int width, int height) {
        int[] data = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int c = random.nextInt();
                int grey = (int)((float) red * 0.3 + (float)green * 0.59 + (float)blue * 0.11);

                data[width * y + x] = c;
            }
        }
        return data;
    }

    public static BufferedImage resizeImage(BufferedImage img, int width, int height) {
        Image image = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return resized;
    }


    public static String readFile(String path) throws IOException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);

        //just read whole file at once
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return new String(data, "UTF-8");
    }
}
