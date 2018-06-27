
package Structure;
public class Grille {
	private int nbLigne, nbColonne;
	private Case [][] laGrille;
	
	public Grille(int ligne, int colonne){
		nbLigne = ligne;
		nbColonne = colonne;
		laGrille = new Case[nbLigne][nbColonne];
		
		for(int i=0; i<nbLigne; i++)
			for(int j=0; j<nbColonne; j++)
				laGrille[i][j] = new Case(new Position(i,j));
	}

	public int getNbLigne() {
		return nbLigne;
	}

	public int getNbColonne() {
		return nbColonne;
	}

	public Case[][] getLaGrille() {
		return laGrille;
	}

	public void affiche(){
		System.out.println();
		for(int i=0; i<nbLigne; i++){
			System.out.print("|");
			for(int j=0; j<nbColonne; j++)
				System.out.print(" "+laGrille[i][j].affiche()+ " |");
			System.out.println();
			for(int j=0; j<nbColonne; j++)
				System.out.print(" - -");
			System.out.println();
		}
	}
	
	public Case getCase(Position p){
		return laGrille[p.getX()][p.getY()];
	}
	
	public boolean peutDescendre(Case c){
		if(c.getX() == nbLigne-1) return false;
		if( (laGrille[c.getX()+1][c.getY()].getValeur() == c.getValeur()) || (laGrille[c.getX()+1][c.getY()].getValeur() == ' ')) 
			return true;
		return false;	
	}
	
	public boolean peutAllerLa(Case c, Position p){
		if(p.getX() >= nbLigne || p.getY() >= nbColonne)
			return false;
		if( (laGrille[p.getX()][p.getY()].getValeur() == c.getValeur()) || (laGrille[p.getX()][p.getY()].getValeur() == ' ')) 
			return true;
		return false;
		
	}
	
	public boolean finDuGame(){
		for(int i=0; i<nbColonne; i++)
			if(laGrille[5][i].getValeur() != ' ')
				return true;
		return false;
	}
	
	
	
}
