package edu.ucla.cs;

import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import com.pholser.junit.quickcheck.generator.GenerationStatus;

import edu.berkeley.cs.jqf.fuzz.tf.data.Image;

import java.awt.image.BufferedImage;


public class TfImageGenerator extends Generator<BufferedImage> {

    public TfImageGenerator() {
        super(BufferedImage.class);
    }

    public BufferedImage generate(SourceOfRandomness random, GenerationStatus t) {
        return Image.decode(random, BufferedImage.TYPE_3BYTE_BGR);
    }
}
