package edu.ucla.cs;

import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import java.util.ArrayList;

import net.tzolov.cv.mtcnn.FaceAnnotation;
import net.tzolov.cv.mtcnn.MtcnnService;
import net.tzolov.cv.mtcnn.MtcnnUtil;
import net.tzolov.cv.mtcnn.FaceAnnotation.BoundingBox;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferByte;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.awt.Image;

public class ImageGeneratorWithModel extends Generator<Img> {
    private static MtcnnService mtcnnService = null;
    private static String[] IMAGES = null;

    public ImageGeneratorWithModel() {
        super(Img.class); // Register the type of objects that we can create
    }

    private String[] initialize(String path) {
        String data = null;
        try {
            data = Util.readFile(path);
        } catch (IOException e) {
            assert(false);
            return null;
        }

        return data.split("\n");
    }

    @Override
    public Img generate(SourceOfRandomness random, GenerationStatus t) {
        String workDir = System.getProperty("user.dir");
        try {
        if (mtcnnService == null) {
            mtcnnService = new MtcnnService(30, 0.709, new double[] { 0.6, 0.7, 0.7 });
        }
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        int w = App.getWidth(), h = App.getHeight();
        int[] data = Util.getRandomImage(random, w, h);

        BufferedImage inputImage = null;

        inputImage = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        for (int i =0 ; i < h; i++) {
            for(int j = 0; j < w; j++) {
                inputImage.setRGB(i, j, data[h * i  + j]);
            }
        }

        FaceAnnotation[] faceAnnotations = null;

        try {
            faceAnnotations = mtcnnService.faceDetection(inputImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //@FIXIT only single annotations for now?
        BoundingBox b = faceAnnotations[0].getBoundingBox();
        BufferedImage croppedImage;
        try {
            croppedImage = inputImage.getSubimage(b.getX(), b.getY(), b.getW(), b.getH());
        } catch (Exception e) {
            e.printStackTrace();
            return new Img(data);
        }

        BufferedImage resized = Util.resizeImage(croppedImage, w, h);
        int[] pixels = resized.getRGB(0,0,256,256, null, 0, 256);//new int[h * w];

        Img im = new Img(pixels);

        return im;
    }
}
