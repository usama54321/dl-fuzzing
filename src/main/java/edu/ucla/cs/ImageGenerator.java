package edu.ucla.cs;

import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import com.pholser.junit.quickcheck.generator.GenerationStatus;

import java.util.Random;
import java.util.ArrayList;
import edu.ucla.cs.Img;

public class ImageGenerator extends Generator<Img> {
    public ImageGenerator() {
        super(Img.class); // Register the type of objects that we can create
    }


    @Override
    public Img generate(SourceOfRandomness random, GenerationStatus t) {
        int w = App.getWidth(), h = App.getHeight();
        w = 256;h = 256;
        int[] data = new int[w * h];

        /*
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 256; j++) {
                data[w * i + j] = random.nextInt();
            }
        }
        */
        int alpha = 0xFF << 24;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int val = random.nextInt();
                int red = ((val >> 16) & 0xFF);
                int green = ((val >> 8) & 0xFF);
                int blue = (val & 0xFF);

                int grey = (int)((float) red * 0.3 + (float)green * 0.59 + (float)blue * 0.11);

                grey = alpha | (grey << 16) | (grey << 8) | grey;
                data[w * i + j] = grey;
            }
        }

        Img im = new Img(data);
        return im;
    }
}

