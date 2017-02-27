import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * class named RecSys that operates on a mapped ratings matrix (where each user is mapped to a 1-Dimensional point). 
 * Rudimentary recommendation system.
 * @author Smriti
 *
 */
public class RecSys {
	//2D object array that holds movie ratings by users and points to which user has been mapped
	private float[][] ratingsMatrix;
	private ArrayList<Float> points;
	private NearestPoints nearestPoints;
/**
 * RecSys(String mrMatrix) The string mrMatrix is contains the absolute path name of the file that 
 * contains the mapped ratings matrix.	
 * @param mrMatrix
 */
	public RecSys(String mrMatrix){
		try {
            File mFile = new File(mrMatrix);
            Scanner scan = new Scanner(mFile);
            int row = scan.nextInt();
            int column = scan.nextInt();
            ratingsMatrix = new float[row][column];
            points = new ArrayList<Float>(row);
            	for(int i=0; i<row; i++){
            		for(int j=0; j<=column; j++){
            			if(j==0)
            				points.add(scan.nextFloat());
            			else
            				ratingsMatrix[i][j-1]=scan.nextFloat();
            		}
            	}
            	nearestPoints = new NearestPoints(points);
            scan.close();
		}
		catch(FileNotFoundException e) {
            System.out.println("Unable to open file '" + mrMatrix + "'");                
        }
		catch(IOException e) {
			e.printStackTrace();  
		}
	}
	
/**
 * If the user u has rated movie m, then it returns that rating; otherwise it will predict the rating 
 * based on the approach described above, and returns the predicted rating.	
 * @param u
 * @param m
 * @return
 */
	public float ratingOf(int u, int m){
		float predictedRating = 0;
		int similarRatingsCount = 0;
		float totalRating = 0;
		ArrayList<Float> nearestPointss = nearestPoints.npHashNearestPoints(points.get(u-1));
		System.out.println(nearestPointss);
		//int ratingInt = (int)(ratingsMatrix[u-1][m-1]);
		//if user has a rating for the movie then set rating to that rating
		if(ratingsMatrix[u-1][m-1]!=0)
			predictedRating = ratingsMatrix[u-1][m-1];
		//if user has no rating(rating = 0) for movie m then predict the rating of user u to movie m
		else{
			for (float nearestPoint : nearestPointss){
				//include the nearest users rating only if he has rated the movie; if he has not rated the movie -> rating = 0, don't count it.
				if(ratingsMatrix[points.indexOf(nearestPoint)][m-1]>0){
					totalRating += ratingsMatrix[points.indexOf(nearestPoint)][m-1];
					similarRatingsCount++;
				}
			}
			if(totalRating==0)
				predictedRating = 0;
			else
				predictedRating = totalRating/similarRatingsCount;
		}
		return predictedRating;
	}
}
