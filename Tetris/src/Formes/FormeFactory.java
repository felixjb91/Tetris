package Formes;
import java.util.Random;

public class FormeFactory {
	private static Random rand = new Random();
	public static Forme creatRandomForme(){
		int r = rand.nextInt(7);
		 switch(r)
	        {
	            case 0:
	                return new FBarre();
	            case 1:
	            	return new FL();
	            case 2:
	            	return new FCarre();
	            case 3:
	            	return new FTrio();
	            case 4:
	            	return new FL2();
	            case 5:
	            	return new FZ();
	            case 6:
	            	return new FZ2();
	        }
		return null;
	}
}
