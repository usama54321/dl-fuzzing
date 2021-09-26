package edu.ucla.cs;

import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import com.pholser.junit.quickcheck.generator.GenerationStatus;

public class ImageGenerator extends Generator<Img> {
    public ImageGenerator() {
        super(Img.class); // Register the type of objects that we can create
    }

    @Override
    public Img generate(SourceOfRandomness random, GenerationStatus t) {
        int w = App.getWidth(), h = App.getHeight();
        int[] data = Util.getRandomImage(random, w, h);
        Img im = new Img(data);
        return im;
    }
}

