package test;


import java.awt.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.MatOfPoint;

import utils.NumericFileComparator;
import utils.bg_MOG2;

public class Detecte_Object {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Opencv 452
		
	}
	static bg_MOG2 bgs = new bg_MOG2();
	static Imgcodecs imageCodecs = new Imgcodecs();
	public static boolean deleteDirectory(File directory) {
	    if(directory.exists()){
	        File[] files = directory.listFiles();
	        if(null!=files){
	            for(int i=0; i<files.length; i++) {
	                if(files[i].isDirectory()) {
	                    deleteDirectory(files[i]);
	                }
	                else {
	                    files[i].delete();
	                }
	            }
	        }
	    }
	    return(directory.delete());
	}	
	
	public static void bg_sub(Mat original) {
		System.out.println("---bgsubstraction-----");
//		System.out.println(frame);
		Mat frame = original.clone();

		Imgproc.GaussianBlur(frame, frame, new Size(3, 3), 0);
		

		
		Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
		bgs.mog2.apply(frame, frame);
		
		Imgproc.erode(frame, frame, bgs.kernel_1);
		Imgproc.dilate(frame, frame, bgs.kernel_2);
		Imgproc.erode(frame, frame, bgs.kernel_1, new Point(-1, -1), 2);
		
		Mat herachy = new Mat();
		java.util.List<MatOfPoint> contours = new ArrayList<>();
		Imgproc.findContours(frame, contours, herachy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		double total_pixels = frame.rows()*frame.cols();
		double changeArea = 0;
		Scalar color = new Scalar(0, 0, 255);
//		Imgproc.drawContours(original, contours, -1, color, 2, Imgproc.LINE_8,herachy, 2, new Point() );
		
		java.util.List<Rect> contourRectList = new ArrayList<Rect>();
		for (int i=0;i<contours.size();i++)
		{
			Rect boundRect = Imgproc.boundingRect(contours.get(i));
			contourRectList.add(boundRect);
//			System.out.println(boundRect.height)
//			if (boundRect.width < 20 && boundRect.height < 20 )
//				continue;
			if (Imgproc.contourArea(contours.get(i))<200) {
				continue;
			}
			System.out.println("contours-area = "+Imgproc.contourArea(contours.get(i)));
			System.out.println("Rectangel-area = "+boundRect.area());
			changeArea += boundRect.area();
			
			Imgproc.rectangle(original, boundRect, color);
		}

	}
	public static void main(String[] args) {
		String inputDir = args[0];
		String outputDir = args[1];
		File inputDirs = new File(inputDir);
		File outputDirs = new File(outputDir);
		
		
		if (!outputDirs.exists()) {
			outputDirs.mkdirs();
		}else {
			deleteDirectory(outputDirs);
			outputDirs.mkdirs();
		}
		
		// TODO Auto-generated method stub
		System.out.println(inputDir);
		System.out.println(outputDir);
		File imgFolder = new File(inputDir);
		File[] inputImages = imgFolder.listFiles();
		Arrays.sort(inputImages,new NumericFileComparator());

		for(File file : inputImages) {

//			String file_name = String.valueOf(count)+".jpg";
			String file_name = file.toString();
			String x=file.getName();
			System.out.println("\n\n"+file_name);
			Mat frame = imageCodecs.imread(file_name);
			Imgproc.resize(frame, frame,new Size(320,320),Imgproc.INTER_LINEAR);
			bg_sub(frame);
			imageCodecs.imwrite(outputDir+"\\"+x,frame);
			
		}
//		
	}
}


