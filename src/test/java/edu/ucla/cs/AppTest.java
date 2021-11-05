package edu.ucla.cs;

import java.awt.image.BufferedImage;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.generator.Size;

import java.util.ArrayList;

import org.junit.Test;

import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.*;

/**
 * Unit test for simple App.
 */
@RunWith(JQF.class)
public class AppTest
{
    /**
     * Rigourous Test :-)
     */
    @Fuzz
    public void testProgramWithoutModel(@From(ImageGenerator.class) Img data) {
        int[] image = data.data;
        int laplacian = App.laplacian(image);
        if (laplacian > 1000) {
            org.junit.Assume.assumeTrue(laplacian > 1000);
        } else {
            org.junit.Assume.assumeTrue(laplacian <= 1000);
        }
    }

    @Fuzz
    public void testProgramWithModel(@From(ImageGeneratorWithModel.class) Img data) {

        int[] image = data.data;
        int laplacian = App.laplacian(image);
        if (laplacian > 1000) {
            //org.junit.Assume.assumeTrue(laplacian > 1000);
            //@TODO add better side effects so doesnt get optimized away?
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
