package edu.ucla.cs;

import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import com.pholser.junit.quickcheck.generator.GenerationStatus;

import java.awt.image.BufferedImage;

public class ImageGenerator extends Generator<BufferedImage> {
    public ImageGenerator() {
        super(BufferedImage.class); // Register the type of objects that we can create
    }

    @Override
    public BufferedImage generate(SourceOfRandomness random, GenerationStatus t) {
        int w = App.getWidth(), h = App.getHeight();
        return Util.getRandomBufferedImage(random, w, h);
    }
}

