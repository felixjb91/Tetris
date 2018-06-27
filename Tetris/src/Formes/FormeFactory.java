package Formes;
import java.util.Random;

import Structure.Grille;

public class FormeFactory {
	private static Random rand = new Random();
	public static Forme creatRandomForme(){
		int r = rand.nextInt(4);
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
	        }
		return null;
	}
}
