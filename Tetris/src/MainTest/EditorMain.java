package MainTest;

import java.util.ArrayList;
import java.util.List;

import Formes.Case;
import Formes.Forme;
import Formes.FormeFactory;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class EditorMain extends Application{
	
	
	public static final int TAILLE_TILE = 25;
    public static final int NB_COL = 10;
    public static final int NB_LIG = 20;
    private final int LARG_JEU = NB_COL * TAILLE_TILE;
    private final int LARG_Fenetre = NB_COL+7;
    private final int SEUIL = 2;
    private double time = 0.0;
    private final double SPEED = 0.015;
    GraphicsContext g,g2; 
    Forme current,nextCurrent=FormeFactory.creatRandomForme();
    boolean flag=true;
    private List<Forme> lesFormes = new ArrayList<>();
    private static Case[][] grille = new Case[NB_COL][NB_LIG];
    private boolean stop = false;
    private Line lineSeuil = new Line(5,SEUIL*TAILLE_TILE,(NB_COL*TAILLE_TILE)-5,SEUIL*TAILLE_TILE);
    private Line separator = new Line(NB_COL*TAILLE_TILE, 5,NB_COL*TAILLE_TILE,NB_LIG*TAILLE_TILE);
	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(creatContent());
		stage.setScene(scene);
		
		
		scene.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.UP)
				current.rotate();
			if(e.getCode() == KeyCode.LEFT)
				current.gauche();
			if(e.getCode() == KeyCode.RIGHT)
				current.droite();
			if(e.getCode() == KeyCode.DOWN)
				current.chute();
			draw();
        	next();
        	draw();
		});
		stage.show();
	}
	
	public static void main (String[] args){
		launch(args);
	}
	
	private Parent creatContent(){
		Pane root = new Pane();
		root.setPrefSize((LARG_Fenetre)*TAILLE_TILE,NB_LIG*TAILLE_TILE);
		
		Canvas canvas = new Canvas(NB_COL*TAILLE_TILE,NB_LIG*TAILLE_TILE);
		Canvas canvasMENU = new Canvas((LARG_Fenetre)*TAILLE_TILE,NB_LIG*TAILLE_TILE);
		
	    root.getChildren().add(canvas);
	    root.getChildren().add(canvasMENU);
	    
	    g = canvas.getGraphicsContext2D();
	    g2 = canvasMENU.getGraphicsContext2D();
	    
	    root.getChildren().add(lineSeuil);
	    root.getChildren().add(separator);
	   
	    
	    spawn();
	    draw();
 
	    AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                time += SPEED;
                
                if (time >= 0.5) {
                	//printGrille();
                	chute();
                	draw();
                	next();       	
                    time = 0;
                }
            }
        };
        timer.start();   
	    return root;
	}
	
	public static Case getCase(int x, int y) {
		if(x<0 || x>=NB_COL || y<0 || y>=NB_LIG) {
			System.out.println("Out of grille !");
			return null;
		}else
			return grille[x][y];	
	}
	
	public void spawn() {
		current = nextCurrent;
	    nextCurrent = FormeFactory.creatRandomForme();
	    
	    g2.clearRect(0, 0, LARG_Fenetre*TAILLE_TILE, NB_LIG*TAILLE_TILE);
	    nextCurrent.draw(g2, NB_COL/2+3, 2);    
	    
	    lesFormes.add(current);
	    addFtoGrille(current);
	}
	
	public static void addFtoGrille(Forme f) {
		f.getCases().forEach(c -> grille[c.getX()][c.getY()] = c);
	}
	public static void removeFtoGrille(Forme f) {
		f.getCases().forEach(c -> grille[c.getX()][c.getY()] = null);
	}

	public void chute() {
		//lesFormes.forEach(f -> f.chute());
		if(flag) current.chute();
	}

	public static boolean peuDescendre(Case c) {
		//System.out.println(c.getY() + ", " + NB_LIG);
		return c.getY()+1 < NB_LIG && (getCase(c.getX(),c.getY()+1) == null ||  getCase(c.getX(),c.getY()+1).getParent() == c.getParent());
	}
	
	public static boolean peutAller(Case c, int x, int y) {
		return c.getX()+x >= 0 && c.getX()+x < NB_COL && c.getY()+y > 0 && c.getY()+y < NB_LIG && (getCase(c.getX()+x,c.getY()+y) == null || getCase(c.getX()+x,c.getY()+y).getParent() == c.getParent());
	}
	
	public void draw() {
		g.clearRect(0, 0, NB_COL*TAILLE_TILE, NB_LIG*TAILLE_TILE);
		lesFormes.forEach(f -> f.draw(g));
	}
	
	public void printGrille() {
		for(int i=0; i<NB_LIG; i++) {
			for(int j=0; j<NB_COL; j++)
				System.out.print("[" + grille[j][i] + "]");
			System.out.println();
		}
		System.out.println();
		System.out.println();	
	}
	
	public boolean canNext() {
		if(!stop) return !current.preChute();
		return false;
	}
	
	public boolean finDuGame() {
		for(int i=0; i<NB_COL; i++)
			if(grille[i][SEUIL]!=null) {
				stop = true;
				return true;
			}
		return false;
	}
	
	public void pause(long temps) {
		try {
			Thread.sleep(temps);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void next() {
		if(canNext()) {
			printGrille();
			flag = false;
			supprimer();
			if(finDuGame()) {
				System.out.println("Ton score est de "+ lesFormes.size());
				return;
			}
			flag = true;
			//draw();
			spawn();
			draw();
			System.out.println("fin next");	
		}
		
	}
	
	public void supprimer() {
		int cpt;
		for(int i=NB_LIG-1; i>=0; i--) {
			cpt = 0;
			for(int j=0; j<NB_COL; j++) {
				if(grille[j][i]!=null) {
					cpt++;
				}
			}
			if(cpt==NB_COL) {
				suppLine(i);
				i=NB_LIG-1;
			}
		}
		System.out.println("fin suppr");
	}

	private void suppLine(int i) {
		Case c;
		for(int j=0; j<NB_COL; j++) {
			c = grille[j][i];
			Forme parent = c.getParent();
			removeFtoGrille(parent);
			parent.getCases().remove(c);
			addFtoGrille(parent);
			System.out.println("tour");
		}


		descentePostSuppr(i);
		System.out.println("fin suppline");
	}

	private void descentePostSuppr(int i) {
		Case c;
		for(int y=i-1; y>=0; y--) {
			for(int x=0; x<NB_COL; x++) {
				c = grille[x][y];
				if(c != null) {
					removeFtoGrille(c.getParent());
					c.move(0, 1);
					addFtoGrille(c.getParent());
				}
			}

			System.out.println("ligne "+y+" descendu");
		}
		System.out.println("fin descentePostSuppr");
		
	}

}