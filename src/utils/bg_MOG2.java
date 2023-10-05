package utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;
public class bg_MOG2 {
	public BackgroundSubtractorMOG2 mog2 = Video.createBackgroundSubtractorMOG2();
	public static Mat kernel_1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
	public static Mat kernel_2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(7,7));
	public static Mat get() {
		return kernel_1;
	}
}
