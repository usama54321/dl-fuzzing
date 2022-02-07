package edu.ucla.cs;

import java.awt.image.BufferedImage;

import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.*;
import com.pholser.junit.quickcheck.From;

import net.tzolov.cv.pose.Pose;
import net.tzolov.cv.pose.PoseEstimation;
import net.tzolov.cv.pose.PoseResult;

import java.util.List;

@RunWith(JQF.class)
public class PoseTest {
    private static PoseEstimation poseService = null;

    public PoseTest() {
        if (poseService == null)
            poseService = new PoseEstimation();
    }
    @Fuzz
    public void testPoseEstimationRandom(@From(ImageGenerator.class) BufferedImage image) {
        PoseResult result = null;
        try {
            result = poseService.poseDetection(image);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        List<Pose> poses = result.getPoses();
        for(Pose pose: poses)
            pose.draw(image.getGraphics(), true, image.getHeight(), image.getWidth());
    }
    @Fuzz
    public void testPoseEstimation(@From(TfImageGenerator.class) BufferedImage image) {
        PoseResult result = null;
        try {
            result = poseService.poseDetection(image);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        List<Pose> poses = result.getPoses();
        for(Pose pose: poses)
            pose.draw(image.getGraphics(), true, image.getHeight(), image.getWidth());
    }
}
