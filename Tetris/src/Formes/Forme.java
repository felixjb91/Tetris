package Formes;
import java.util.List;
import Formes.States.*;
import MainTest.EditorMain;

import static MainTest.EditorMain.NB_COL;
import static MainTest.EditorMain.TAILLE_TILE;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Forme{
	protected int id;
	private static int cpt = 0;
	protected Color couleur;
	protected List<Case> cases;
	protected int x = (NB_COL/2), y = 0;
	protected ISens sens = new Sens1();

	public Forme(){
		id = cpt++;	
	}
	
    public void draw(GraphicsContext g) {
        cases.forEach(c -> {
        	g.setFill(Color.BLACK);
        	g.fillRect(c.getX() * TAILLE_TILE, c.getY() * TAILLE_TILE, TAILLE_TILE, TAILLE_TILE);
        	g.setFill(couleur);
        	g.fillRect((c.getX() * TAILLE_TILE)+2, (c.getY() * TAILLE_TILE)+2, TAILLE_TILE-2, TAILLE_TILE-1.5);
        });
    }
    public void draw(GraphicsContext g,int x, int y) {
        cases.forEach(c -> {
        	g.setFill(Color.BLACK);
        	g.fillRect((c.getX()+x) * TAILLE_TILE, (c.getY()+y) * TAILLE_TILE, TAILLE_TILE, TAILLE_TILE);
        	g.setFill(couleur);
        	g.fillRect(((c.getX()+x) * TAILLE_TILE)+2, ((c.getY()+y) * TAILLE_TILE)+2, TAILLE_TILE-2, TAILLE_TILE-1.5);
        });
    }
	public List<Case> getCases() {
		return cases;
	}
	
	public void gauche() {
		if(preGauche()) {
			EditorMain.removeFtoGrille(this);
			cases.forEach(c -> c.move(-1, 0));
			EditorMain.addFtoGrille(this);
		}		
	}
	public boolean preGauche() {
		for(Case c: cases) 
			if(!EditorMain.peutAller(c, -1, 0)) 
				return false;
		return true;
	}
	
	public void droite() {
		if(preDroite()) {
			EditorMain.removeFtoGrille(this);
			cases.forEach(c -> c.move(1, 0));
			EditorMain.addFtoGrille(this);
		}		
	}
	public boolean preDroite() {
		for(Case c: cases) 
			if(!EditorMain.peutAller(c, 1, 0)) 
				return false;
		return true;
	}

	public void chute(){
		if(preChute()) {
			EditorMain.removeFtoGrille(this);
			cases.forEach(c -> c.move(0, 1));
			EditorMain.addFtoGrille(this);
		}
	}
	
	public boolean preChute() {
		for(Case c: cases) {
			if(!EditorMain.peuDescendre(c)) {
				//System.out.println(c.getY() + " ne peut pas décendre");
				return false;
			}
		}
		return true;
	}
	
	public void rotate(){
		sens.rotation(this);
	}

	public void nextSens(ISens s) {
		this.sens = s;
	}
	
	public int getId() {
		return id;
	}
}
