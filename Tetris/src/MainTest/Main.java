package MainTest;
import Formes.FormeFactory;
import Structure.Grille;

public class Main {
	public static void main(String [] args){
		try {
			Grille g = new Grille(20,8);
			Thread t;
			while(!g.finDuGame()){
				t = new Thread(FormeFactory.creatRandomForme(g));
				t.start();	
				t.join();
			}
			System.out.println("fin du game !");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
