package edu.ucla.cs;

import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Util {
    public static int[] getRandomImage(SourceOfRandomness random, int height, int width) {
        int alpha = 0xFF << 24;
        int[] data = new int[width * height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int val = random.nextInt();
                int red = ((val >> 16) & 0xFF);
                int green = ((val >> 8) & 0xFF);
                int blue = (val & 0xFF);

                int grey = (int)((float) red * 0.3 + (float)green * 0.59 + (float)blue * 0.11);

                grey = alpha | (grey << 16) | (grey << 8) | grey;
                data[width * i + j] = grey;
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
}
