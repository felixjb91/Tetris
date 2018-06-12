package Structure;

public class Case {
	private Position p;
	private char valeur = ' ';
	
	public Case(Position p){
		this.p = p;
	}

	public char getValeur() {
		return valeur;
	}

	public void setValeur(char valeur) {
		this.valeur = valeur;
	}

	public int getX() {
		return p.getX();
	}

	public int getY() {
		return p.getY();
	}

	@Override
	public String toString() {
		return "Case [p=" + p + ", valeur=" + valeur + "]";
	}
	
	public String affiche(){
		return valeur+"";
	}

	public void liberer(){
		this.valeur = ' ';
	}

	public Position getP() {
		return p;
	}
	
	
}
