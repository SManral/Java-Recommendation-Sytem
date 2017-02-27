/**
 * Class Tuple, to represent tuples of form ⟨key,value⟩, where key 
 * is of type int and value is of type double. 
 * @author Smriti
 *
 */
public class Tuple {
	private int key;
	private float value;
	Tuple(int keyP, float valueP){
		key = keyP;
		value = valueP;
	}
	public int getKey(){		
		return key;
	}
	public double getValue(){
		return value;
	}
	public boolean equals(Tuple t){
		if(this == t){
			return true;
	}
		return false;
	}
	public static String toString(Tuple t){
		String p = t.getKey()+t.getValue()+"";
		return p;
	}
}
