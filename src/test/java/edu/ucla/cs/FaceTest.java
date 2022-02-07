package edu.ucla.cs;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.runner.RunWith;
import com.pholser.junit.quickcheck.From;
import edu.berkeley.cs.jqf.fuzz.*;

import net.tzolov.cv.mtcnn.MtcnnService;
import net.tzolov.cv.mtcnn.FaceAnnotation;
import net.tzolov.cv.mtcnn.FaceAnnotation.BoundingBox;

import java.awt.Image;
import java.awt.Graphics2D;

@RunWith(JQF.class)
public class FaceTest {
    private static MtcnnService mtcnnService = null;

    public FaceTest() {
        if (mtcnnService == null) {
            try {
                mtcnnService = new MtcnnService(30, 0.709, new double[] { 0.6, 0.7, 0.7 });
            }catch(Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    @Fuzz
    public void testFaceDetectionRandom(@From(ImageGenerator.class) BufferedImage faceOne, @From(ImageGenerator.class) BufferedImage faceTwo) {
        faceApp(faceOne, faceTwo);
    }

    public static BufferedImage resizeImage(BufferedImage img, int width, int height) {
        Image image = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return resized;
    }

    @Fuzz
    public void testFaceDetection(@From(TfImageGenerator.class) BufferedImage faceOne) throws Exception {
        BufferedImage faceTwo = resizeImage(faceOne, faceOne.getWidth(), faceOne.getHeight());

        faceApp(faceOne, faceTwo);
    }

    public void faceApp(BufferedImage faceOne, BufferedImage faceTwo) {
        FaceAnnotation[] faceOneAnnotations = null;
        FaceAnnotation[] faceTwoAnnotations = null;

        try {
            faceOneAnnotations = mtcnnService.faceDetection(faceOne);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            faceTwoAnnotations = mtcnnService.faceDetection(faceTwo);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (faceOneAnnotations.length == 0 || faceTwoAnnotations.length == 0) {
            System.out.println("no face detected");
            return;
        }


        BoundingBox b1 = faceOneAnnotations[0].getBoundingBox();
        BoundingBox b2 = faceTwoAnnotations[0].getBoundingBox();

        BufferedImage croppedFaceOne = faceOne.getSubimage(b1.getX(), b1.getY(), b1.getW(), b1.getH());
        BufferedImage croppedFaceTwo = faceTwo.getSubimage(b2.getX(), b2.getY(), b2.getW(), b2.getH());

        croppedFaceOne = resizeImage(croppedFaceOne, 256, 256);
        croppedFaceTwo = resizeImage(croppedFaceTwo, 256, 256);

        int[] pixelsOne = new int[256 * 256];
        int[] pixelsTwo = new int[256 * 256];

        croppedFaceOne.getRGB(0, 0, 256, 256, pixelsOne, 0, 256);
        croppedFaceTwo.getRGB(0, 0, 256, 256, pixelsTwo, 0, 256);

        int lapOne = App.laplacian(pixelsOne);

        if (lapOne < 1000) {
            System.out.println("less than threshold");
        } else {
            System.out.println("more than threshold");
            float score = 0.5f; //@TODO fas.antiSpoofing(croppedFaceOne)

            if (score < 0.2f) {
                //some code
                System.out.println();
            } else {
                //some code
                System.out.println(" ");
            }
        }

        int lapTwo = App.laplacian(pixelsTwo);
        if (lapTwo < 1000) {
            System.out.println("less than expected");
        } else {
            float score = 0.5f;

            if (score < 0.2f) {
                //some code
                System.out.println();
            } else {
                //some code
                System.out.println(" ");
            }
        }
    }
}
