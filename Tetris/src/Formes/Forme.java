package Formes;
import Exceptions.BloqueException;
import Formes.States.*;
import Structure.Case;
import Structure.Grille;
import Structure.Position;

public abstract class Forme implements Runnable {
	protected int id;
	private static int cpt = 0;
	protected Case c1, c2, c3, c4;
	protected Grille g;
	protected Position posDepart;
	protected boolean stop = false;
	protected ISens sens = new Sens1();

	public Forme(Grille g){
		this.g = g;
		id = cpt++;	
		posDepart = new Position(0,g.getNbColonne()/2);
	}
	
	@Override
	public String toString() {
		return "Forme [id=" + id + ", c1=" + c1 + ", c2=" + c2 + ", c3=" + c3 + ", c4=" + c4 + ", g=" + g
				+ ", posDepart=" + posDepart + "]";
	}
	public Case getC1() {
		return c1;
	}
	public Case getC2() {
		return c2;
	}
	public Case getC3() {
		return c3;
	}
	public Case getC4() {
		return c4;
	}
	public Grille getG() {
		return g;
	}
	public void setC1(Case c1) {
		this.c1 = c1;
	}
	public void setC2(Case c2) {
		this.c2 = c2;
	}
	public void setC3(Case c3) {
		this.c3 = c3;
	}
	public void setC4(Case c4) {
		this.c4 = c4;
	}
	private boolean bloque() {
		return stop;
	}
	public void setValue(){
		c1.setValeur((char)(id+'0'));
		c2.setValeur((char)(id+'0'));
		c3.setValeur((char)(id+'0'));
		c4.setValeur((char)(id+'0'));	
	}
	private synchronized void chute() throws BloqueException{
		if(g.peutDescendre(c1) && g.peutDescendre(c2) && g.peutDescendre(c3) && g.peutDescendre(c4)){		
			Case ca1,ca2,ca3,ca4;
			ca1 = c1;
			ca2 = c2;
			ca3 = c3;
			ca4 = c4;
			c1 = g.getCase(new Position(ca1.getX()+1,ca1.getY()));
			c2 = g.getCase(new Position(ca2.getX()+1,ca2.getY()));
			c3 = g.getCase(new Position(ca3.getX()+1,ca3.getY()));
			c4 = g.getCase(new Position(ca4.getX()+1,ca4.getY()));
			ca1.liberer();
			ca2.liberer();
			ca3.liberer();
			ca4.liberer();
			setValue();
		}
		else{
			throw new BloqueException("ne peut plus tomber");
		}
		
	}
	
	public void rotate(){
		sens.rotation(this);
	}
	
	@Override
	public void run(){
		int i=0;
		while(!bloque()){
			try {
				Thread.sleep(300);
				if(i%2 == 0 && i>2) rotate();
				chute();
				g.affiche();
				i++;
			} catch (InterruptedException e){
				e.printStackTrace();
			}catch(BloqueException e){
				e.getMessage();
				stop=true;
			}
		}
	}

	public void nextSens(ISens s) {
		this.sens = s;
	}




}
