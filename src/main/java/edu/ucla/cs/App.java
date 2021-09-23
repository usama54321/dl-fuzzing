package edu.ucla.cs;

import net.tzolov.cv.mtcnn.FaceAnnotation;
import net.tzolov.cv.mtcnn.MtcnnService;
import net.tzolov.cv.mtcnn.MtcnnUtil;
import net.tzolov.cv.mtcnn.FaceAnnotation.BoundingBox;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.WritableRaster;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

//import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    private static String ONET_SAMPLE_IMAGE = "/home/usama/ml_system/java-app/tf-java/img.jpeg";
    private static boolean DEBUG = true;
    public static int[] convertGreyImg(BufferedImage inp) {
        int w = inp.getWidth();
        int h = inp.getHeight();

        int[] pixels = inp.getRGB(0,0,256,256, null, 0, 256);//new int[h * w];
        //inp.getPixels(pixels, 0, w, 0, 0, w, h);

        //int[][] result = new int[h][w];
        int[] result = new int[w * h];
        int alpha = 0xFF << 24;
        for(int i = 0; i < h; i++)	{
            for(int j = 0; j < w; j++) {
                int val = pixels[w * i + j];


                int red = ((val >> 16) & 0xFF);
                int green = ((val >> 8) & 0xFF);
                int blue = (val & 0xFF);

                int grey = (int)((float) red * 0.3 + (float)green * 0.59 + (float)blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                result[w * i + j] = grey;
            }
        }
        return result;
    }

    public static int laplacian(BufferedImage inp) {
        //Bitmap bitmapScale = Bitmap.createScaledBitmap(bitmap, INPUT_IMAGE_SIZE, INPUT_IMAGE_SIZE, true);

        int[][] laplace = {{0, 1, 0}, {1, -4, 1}, {0, 1, 0}};
        int size = laplace.length;
        int LAPLACE_THRESHOLD = 50;
        int[] img = convertGreyImg(inp);

        if (DEBUG) {
            BufferedImage image = new BufferedImage(256,256, BufferedImage.TYPE_INT_ARGB);
            image.getRaster().setDataElements(0,0,256,256,img);
            try {
                ImageIO.write(image, "png", new File("./GrayScale.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int height = 256;
        int width = 256;

        int score = 0;
        for (int x = 0; x < height - size + 1; x++){
            for (int y = 0; y < width - size + 1; y++){
                int result = 0;
                // 对size*size区域进行卷积操作
                for (int i = 0; i < size; i++){
                    for (int j = 0; j < size; j++){
                        result += (img[(x + i) * width + (y + j)] & 0xFF) * laplace[i][j];
                    }
                }
                if (result > LAPLACE_THRESHOLD) {
                    score++;
                }
            }
        }
        return score;
    }

    public static void main(String[] args) throws Exception {
        //System.out.println("Hello TensorFlow " + TensorFlow.version());

        MtcnnService mtcnnService = new MtcnnService(30, 0.709, new double[] { 0.6, 0.7, 0.7 });

        BufferedImage croppedImage = null;
        try (InputStream imageInputStream = new FileInputStream(ONET_SAMPLE_IMAGE)) {
            // 2. Load the input image (you can use http:/, file:/ or classpath:/ URIs to resolve the input image
            BufferedImage inputImage = ImageIO.read(imageInputStream);
            // 3. Run face detection
            FaceAnnotation[] faceAnnotations = mtcnnService.faceDetection(inputImage);
            // 4. Augment the input image with the detected faces
            if (DEBUG) {
                BufferedImage annotatedImage = MtcnnUtil.drawFaceAnnotations(inputImage, faceAnnotations);
                // 5. Store face-annotated image
                ImageIO.write(annotatedImage, "png", new File("./AnnotatedImage.png"));
                // 6. Print the face annotations as JSON
                BoundingBox b = faceAnnotations[0].getBoundingBox();

                croppedImage = inputImage.getSubimage(b.getX(), b.getY(), b.getW(), b.getH());
                ImageIO.write(croppedImage, "png", new File("./CroppedImage.png"));
            }
        }

        if(croppedImage != null) {
            Image tmp = croppedImage.getScaledInstance(256,256,Image.SCALE_SMOOTH);
            BufferedImage resized = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = resized.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            int laplacian = laplacian(resized);

            if (laplacian > 1000) {
                System.out.println("true");
            } else {
                System.out.println("false");
            }
        }
    }
}
