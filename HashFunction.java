import java.util.Random;
/**
 * This class will represent a random hash function that can be used in a hash table.
 * @author Smriti
 *
 */
public class HashFunction {
private int a,b,p;
/**
 * Hash code => Picks the first (positive) prime integer whose value is at least range, and sets the value of p to that 
 * prime integer.picks two random integers x and y from {0,1,··· ,p−1} and sets a as x and b as y.	
 * @param range
 */
	public HashFunction(int range){	    
	    p = getFirstPrime(range);
		Random rand = new Random();
		a = rand.nextInt(p);
		b = rand.nextInt(p);
	}
/**
 * This method will pick the first (positive) prime whose value is at least n and sets the value of p to that integer.	
 * @param n
 * @return
 */
	public static int getFirstPrime(int n){
		for(int i=2; i<n; i++){
	        if(n%i==0){
	        	i=1;
	            n=n+1; 
	        } 
		}
	    return n;
	}	
/**
 * Compressor => Returns the value of the hash function on x; i.e, returns (ax + b)%p.	
 * @param x
 * @return
 */
	public int hash(int x){
		return ((a*x) + b)%p;
	}
/**
 * returns a
 * @return
 */
	public int getA(){
		return a;
	}
/**
 * returns b	
 * @return
 */
	public int getB(){
		return b;
	}
/**
 * returns p	
 * @return
 */
	public int getC(){
		return p;
	}
/**
 * change the value of a (resp. b) to x%p (resp. y%p). Modifier method named setP(int x)	
 * @param x
 */
	public void setA(int x){
		a=x%p;
	}
/**
 * change the value of a (resp. b) to x%p (resp. y%p). Modifier method named setP(int x)	
 * @param y
 */
	public void setB(int y){
		b=y%p;
	}
/**
 * method will pick the first (positive) prime whose value is at least x and sets the value of p to that integer.	
 * @param x
 */
	public void setP(int x){	
	    p = getFirstPrime(x);
	}
}
