import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class will implement a hash table. The hash table will hold data items whose type is tuple. 
 * This class will have following public methods and constructor.
 * @author Smriti
 *
 */
public class HashTable {
	
private int sizeHT, numElements;
// A list of buckets to store the tuples in
private ArrayList<LinkedList<Tuple>> hashTable;
// Instance of Hash function to convert keys into index 
private HashFunction hf;

/**
 * HashTable(int size) Finds the smallest prime integer p whose value is at least size. Creates a 
 * hash table of size p where each cell initially is NULL. It will determine the hash function to 
 * be used in the hash table by creating the object new HashFunction(p).
 * @param size
 */
	public HashTable(int size){
		numElements=0;
		sizeHT = HashFunction.getFirstPrime(size);
		hf = new HashFunction(sizeHT); 
		hashTable = new ArrayList<LinkedList<Tuple>>(sizeHT);
		for(int i=0; i<sizeHT; i++){
			hashTable.add(i,(new LinkedList<Tuple>()));
		}
	}
	
/**
 * Returns the maximum load of the hash table
 * @return
 */
	public double maxLoad(){
		int maxCount = 0;
		for(int i=0; i<size(); i++){
			if(hashTable.get(i).size() > maxCount)
				maxCount = hashTable.get(i).size();
		}
		return maxCount;
	}
	
/**
 * Returns the average load of the hash table	
 * @return
 */
	public double averageLoad(){
		int countNonNullBuckets = 0;
		for(int i=0; i<size(); i++){
			if(hashTable.get(i).size() > 0)
				countNonNullBuckets++;
		}
		int averageLoad = numElements()/countNonNullBuckets;
		return averageLoad;
	}
	
/**
 * Returns the current size of the hash table.	
 * @return
 */
	public int size(){
		return sizeHT;
	}
	
/**
 * Returns the number of Tuples that are currently stored in the hash table.	
 * @return
 */
	public int numElements(){
		return numElements;
	}
	
/**
 * Returns the load factor which is numElements()/size()
 * @return
 */
	public double loadFactor(){
		return  numElements()/size();
	}
	
/**
 * Rehashing the hash table if load factor > 0.7
 */
	private void rehash()
    {
        ArrayList<LinkedList<Tuple>> oldElements = hashTable;
        // Create new double-sized, empty table
        int newSize = HashFunction.getFirstPrime(2*size()); 
		hf = new HashFunction(newSize);
		hashTable = new ArrayList<LinkedList<Tuple>>(newSize);
		for( int i = 0; i < newSize; i++ ){
			hashTable.add(i,(new LinkedList<Tuple>()));
        }
            // Copy table over
        for( int i = 0; i < size(); i++ ){
            for( Tuple item : oldElements.get(i)){
            	int bucketIndex = hf.hash(item.getKey());	
    			hashTable.get(bucketIndex).add(item);
            }
        }
        sizeHT = newSize;
    }
	
/**
 * Adds the tuple t to the hash table; places t in the list pointed by the cell h(t.getKey()) 
 * where h is the hash function that is being used. When the load factors becomes bigger than 0.7,
 * then it (approximately) doubles the size of the hash table and rehashes all the elements (tuples) 
 * to the new hash table. The size of the new hash table must be: Smallest prime integer whose value 
 * is at least twice the current size.	
 * best case = worst case O(1) 
 * @param t
 */
	public void add(Tuple t){
		//use the hash function to find index to store the tuple in using the key 
		int bucketIndex = hf.hash(t.getKey());	
		hashTable.get(bucketIndex).add(t);
		numElements++;
		//rehash if load factor exceeds 0.7
		if(loadFactor()>0.7){
			rehash();
		}
	}
	
/**
 * Overload method to use in NearestPoint class to add points to hasTable based on NearestPoint hash function 	
 * @param t
 * @param bucketIndex
 */
	public void add(Tuple t, int bucketIndex ){
		//use the hash function to find index to store the tuple in using the key 
		hashTable.get(bucketIndex).add(t);
		numElements++;
		//rehash if load factor exceeds 0.7
		if(loadFactor()>maxLoad()){
			rehash();
		}
	}	
	
/**
 *Returns an array list of Tuples (in the hash table) whose key equals k. If no such Tuples exist, returns an empty
 * list. Note that the type of this method must be ArrayList<Tuple>
 * O(1) best case 
 * @param k
 * @return
// */
	public ArrayList<Tuple> search(int k){
		ArrayList<Tuple> elementsFound = new ArrayList<Tuple>();
		int index = hf.hash(k);
		if (hashTable.get(index).isEmpty()){
            return elementsFound;
		}
		else{
			for(int i=0; i<hashTable.get(index).size(); i++){
			if(hashTable.get(index).get(i).getKey()==k)	
				elementsFound.add(hashTable.get(index).get(i));
			}
		}
		return elementsFound;
	}	
	
/**
 * Overload method to use in NearestPoint class to search for points to hasTable based on NearestPoint hash function 	
 * @param t
 * @param bucketIndex
 */	
	public ArrayList<Float> search(int k, int bucketIndex){
		ArrayList<Float> elementsFound = new ArrayList<Float>();
		if (hashTable.get(bucketIndex).isEmpty()){
            return elementsFound;
		}
		else{
			for(int i=0; i<hashTable.get(bucketIndex).size(); i++){
			if(hashTable.get(bucketIndex).get(i).getKey()==k)	
				elementsFound.add((float) hashTable.get(bucketIndex).get(i).getValue());
			}
		}
		return elementsFound;
	}	
	
/**
 * remove(Tuple t) Removes the first occurrence of Tuple t from the hash table.
 * O(1)
 * @param t
 */
	public void remove(Tuple t){	
		int index = hf.hash(t.getKey());
		for(int i=0; i<hashTable.get(index).size(); i++){
			if(hashTable.get(index).get(i).getKey()==t.getKey() && hashTable.get(index).get(i).getValue()==t.getValue()){	
				hashTable.get(index).remove(i);
				numElements--;
				//remove only first occurance of t
				break;
			}
		}
	}
}
