package Formes;
import java.util.Random;

import Structure.Grille;

public class FormeFactory {
	private static Random rand = new Random();
	public static Forme creatRandomForme(Grille g){
		int r = rand.nextInt(4);
		 switch(r)
	        {
	            case 0:
	                return new FBarre(g);
	            case 1:
	            	return new FL(g);
	            case 2:
	            	return new FCarre(g);
	            case 3:
	            	return new FTrio(g);
	        }
		return null;
	}
}
