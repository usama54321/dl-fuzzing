package edu.ucla.cs;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import net.tzolov.cv.pose.Pose;
import net.tzolov.cv.pose.PoseResult;
import net.tzolov.cv.pose.PoseEstimation;

import edu.berkeley.cs.jqf.fuzz.tf.data.Image;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class PoseGenerator extends Generator<PoseData> {
    private static PoseEstimation poseService;

    public PoseGenerator() {
        super(PoseData.class); // Register the type of objects that we can create
        poseService = new PoseEstimation();
    }

    public PoseData generate(SourceOfRandomness random, GenerationStatus t) {
        BufferedImage inputImage = Image.decode(random, BufferedImage.TYPE_INT_RGB);

        try {
            PoseResult result = poseService.poseDetection(inputImage);

            PoseData data = new PoseData();
            data.poseResult = result;
            data.image = inputImage;

            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
