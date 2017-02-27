import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.Math.abs;
/**
 * Class to compute the nearest points to the given points and all the points, using the naive way and the neighbor preserving hash way  
 * @author Smriti
 *
 */
public class NearestPoints {
private ArrayList<Float> points = new ArrayList<Float>();
private HashTable hashTable;
/**
 * Constructor for the class NearestPoints. The variable dataFile holds the absolute path of the file that 
 * contains the set of points S.	
 * @param dataFile
 */
	public NearestPoints (String dataFile){
		try {
            File dFile = new File(dataFile);
            Scanner scan = new Scanner(dFile);
            
            while(scan.hasNextFloat()) {
            	points.add(scan.nextFloat());
            } 
            scan.close();
            buildDataStructure();
		}
		catch(FileNotFoundException e) {
            System.out.println("Unable to open file '" + dataFile + "'");                
        }
		catch(IOException e) {
			e.printStackTrace();  
		}
	}
/**
 * Constructor 2 for the class NearestPoints. The array list pointSet contains the set of points S.	
 * @param pointSet
 */
	public NearestPoints(ArrayList<Float> pointSet){
		points = pointSet;
		buildDataStructure();
	}
/**
 * Returns an array list of points (from the set S) that are close to p. This method must implement the naive 
 * approach. Note that the type of this method must be ArrayList<float>	
 * The expected run time of this method is O(n);
 * @param p
 * @return
 */
	public ArrayList<Float> naiveNearestPoints (float p){
		ArrayList<Float> nearestPointsList = new ArrayList<Float>();
		nearestPointsList.add(p);
		for(int i =0; i<points.size(); i++){
			if( abs(p - points.get(i)) <= 1){
				nearestPointsList.add(points.get(i));
			}
		}
		return nearestPointsList;
	}
/**
 * Builds the data structure that enables to quickly answer nearest point queries. Data
 * structure uses the notion of neighbor preserving hashing and along with the class HashTable. 
 */
	public void buildDataStructure(){
		int sizeHT = (int)(points.size()*1.5);
		hashTable = new HashTable(sizeHT);
		for(float point: points){
			int bucketIndex = (((int)(Math.floor(point)))%hashTable.size());	
			hashTable.add(new Tuple((int)(Math.floor(point)),point), bucketIndex);
		}				
	}
/**
 * Returns an array list of points (from the S) that are close to p. This method uses the data structure 
 * that was built. The expected run time of this method is O(N(p)); 
 * @param p
 * @return
 */
	public ArrayList<Float> npHashNearestPoints(float p){
		ArrayList<Float> nearestPointsList = new ArrayList<Float>();
		nearestPointsList.add(p);
		int bucketIndex = (((int)(Math.floor(p)))%hashTable.size());
		int key = (int)(Math.floor(p));
		//start search from bucketIndex-1 and key-1, if bucket index > 0
		if(bucketIndex > 0){
			bucketIndex -= 1;
			key -= 1; 
		//searches at bucket index at floor(p-1), floor(p), and floor(p+1) for the possible nearest points, when bucket index > 0
			for(int i=0; i<3 ; i++){
				for (float nearestPoint: hashTable.search(key, bucketIndex)){
					if(abs(p-nearestPoint) <= 1){
						nearestPointsList.add(nearestPoint);
					}
				}
				if(bucketIndex<hashTable.size()-1)
					bucketIndex++;
				else
					bucketIndex=0;
				key++;
			}
		}
		//searches at bucket index at floor(p), and floor(p+1) for the possible nearest points, when bucket index = 0
		else{
			for(int i=0; i<2; i++){
				for (float nearestPoint: hashTable.search(key, bucketIndex)){
					if(p-nearestPoint <= 1)
						nearestPointsList.add(nearestPoint);
				}
				if(bucketIndex<hashTable.size()-1)
					bucketIndex++;
				else
					bucketIndex=0;
				key++;
			}
		}
		return nearestPointsList;
	}
/**
 * For every point p ∈ S, compute the list of all points from S that are close to p by calling the method 
 * NaiveNearestPoints(p). Writes the results to a file named NaiveSolution.txt	
 * The expected run time of this method is O(n^2);  
 */
	public void allNearestPointsNaive(){
		try {
			PrintWriter writer = new PrintWriter("NaiveSolution.txt");			
			for(int i=0; i<points.size();i++){			
		    writer.println(naiveNearestPoints(points.get(i)));
			}
		    writer.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}		 
	}
/**
 * For every point p ∈ S, compute the list of all points from S that are close to p by calling the method 
 * NPHashNearestPoints(p). Write the results to a file named HashSolution.txt. The expected time of this method 
 * is O(n +  p∈S N (p)); 	
 */
	public void allNearestPointsHash(){
		try {
			PrintWriter writer = new PrintWriter("HashSolution.txt");			
			for(int i=0; i<points.size();i++){			
				writer.println(npHashNearestPoints(points.get(i)));
			}
		    writer.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

