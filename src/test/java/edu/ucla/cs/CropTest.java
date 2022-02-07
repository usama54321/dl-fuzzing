package edu.ucla.cs;

import java.awt.image.BufferedImage;

import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.*;
import com.pholser.junit.quickcheck.From;

import net.tzolov.cv.cropper.SmartCropper;



@RunWith(JQF.class)
public class CropTest {
    @Fuzz
    public void testSmartCropper(@From(TfImageGenerator.class) BufferedImage image) {
        try {
            SmartCropper.buildImageDetector("");
            BufferedImage img = SmartCropper.cropImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Fuzz
    public void testSmartCropperRandom(@From(ImageGenerator.class) BufferedImage image) {
        try {
            SmartCropper.buildImageDetector("");
            BufferedImage img = SmartCropper.cropImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
