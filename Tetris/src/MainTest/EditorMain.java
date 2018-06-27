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
import javafx.stage.Stage;

public class EditorMain extends Application{
	
	public static final int TAILLE_TILE = 35;
    public static final int NB_COL = 15;
    public static final int NB_LIG = 20;
    private double time = 0.0;
    GraphicsContext g; 
    Forme current;
    private List<Forme> lesFormes = new ArrayList<>();
    private static Case[][] grille = new Case[NB_COL][NB_LIG];
    private boolean stop = false;


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
		});
		stage.show();
	}
	
	public static void main (String[] args){
		launch(args);
	}
	
	private Parent creatContent(){
		Pane root = new Pane();
		root.setPrefSize(NB_COL*TAILLE_TILE,NB_LIG*TAILLE_TILE);
		Canvas canvas = new Canvas(NB_COL*TAILLE_TILE,NB_LIG*TAILLE_TILE);
	    root.getChildren().add(canvas);
	    g = canvas.getGraphicsContext2D();
	    
	    spawn();
	    draw();
 
	    AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                time += 0.017;
                
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
	    current = FormeFactory.creatRandomForme();
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
		lesFormes.forEach(f -> f.chute());
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
		if(!stop) {
			for(Forme f: lesFormes)
				if(f.preChute())
					return false;
			return true;
		}
		return false;
	}
	
	public boolean finDuGame() {
		for(int i=0; i<NB_COL; i++)
			if( grille[i][2] != null) {
				stop = true;
				return true;
			}
		return false;
	}
	
	public void next() {
		if(canNext()) {
			if(finDuGame()) {
				System.out.println("Ton score est de "+ lesFormes.size());
				return;
			}
			spawn();
		}
	}

}