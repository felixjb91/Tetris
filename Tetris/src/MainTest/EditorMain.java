package MainTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Formes.Case;
import Formes.Forme;
import Formes.FormeFactory;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class EditorMain extends Application {
	
	
	public static final int TAILLE_TILE = 25;
    public static final int NB_COL = 10;
    public static final int NB_LIG = 20;
    private final int TAILLE_SCORE = 8;
    private final int SEUIL = 2;
    private double time = 0.0;
    private double SPEED = 0.015;
    private int nblignesuppr = 0;
    private Scene scene ;
    private GraphicsContext g,g2; 
    private Forme current,nextCurrent=FormeFactory.creatRandomForme();
    boolean flag=true;
    private List<Forme> lesFormes = new ArrayList<>();
    private static Case[][] grille = new Case[NB_COL][NB_LIG];
    private boolean stop = false;
    private Line lineSeuil = new Line(5,SEUIL*TAILLE_TILE,(NB_COL*TAILLE_TILE)-5,SEUIL*TAILLE_TILE);
    

    @FXML
    private SplitPane sp;
    @FXML
    private AnchorPane leJeu;
    @FXML
    private AnchorPane leScore;
    @FXML
    private Pane nextForme;
    @FXML
    private Label nbLigne;
    @FXML
    private Label nbPiece;
    
	@Override
	public void start(Stage stage) throws Exception {
		//Scene scene = new Scene(creatContent());
		try {
			SplitPane rooot = FXMLLoader.load(this.getClass().getResource("/MainView.fxml"));
			scene = new Scene(rooot);	
			stage.setTitle("Tétris");
			stage.setScene(scene);
			
			initGame(rooot);
			controller();
	
			stage.show();
				
		}catch(IOException e) {
			e.getStackTrace();
		}
	
	}
	
	public static void main (String[] args){
		launch(args);
	}	
	
	private void initGame(SplitPane root) {
		root.setPrefSize((TAILLE_SCORE+NB_COL)*TAILLE_TILE, NB_LIG*TAILLE_TILE);
		root.setMinSize((TAILLE_SCORE+NB_COL)*TAILLE_TILE, NB_LIG*TAILLE_TILE);
		root.setMaxSize((TAILLE_SCORE+NB_COL)*TAILLE_TILE, NB_LIG*TAILLE_TILE);
		
		leJeu  = (AnchorPane) root.getItems().get(0);
		leJeu.setPrefSize(NB_COL*TAILLE_TILE, NB_LIG*TAILLE_TILE);
		leJeu.setMinSize(NB_COL*TAILLE_TILE, NB_LIG*TAILLE_TILE);
		leJeu.setMaxSize(NB_COL*TAILLE_TILE, NB_LIG*TAILLE_TILE);	
		
		leScore = (AnchorPane) root.getItems().get(1);		
		leScore.setPrefWidth(TAILLE_SCORE*TAILLE_TILE);
		leScore.setMinWidth(TAILLE_SCORE*TAILLE_TILE);
		leScore.setMaxWidth(TAILLE_SCORE*TAILLE_TILE);

		nextForme = (Pane) leScore.getChildren().get(1);
		Canvas c = (Canvas) nextForme.getChildren().get(0);
		c = (Canvas) nextForme.getChildren().get(0);
		g2 = c.getGraphicsContext2D();
		
		nbLigne = (Label) leScore.getChildren().get(2);
		nbLigne.setText(nblignesuppr+"");
		nbPiece = (Label) leScore.getChildren().get(5);
		nbPiece.setText(lesFormes.size()+"");
		
		leJeu.getChildren().add(creatContent());	
	}
	
	
	private void controller() {
		
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
	}
	
	private Parent creatContent(){
		Pane root = new Pane();
		root.setPrefSize((NB_COL+TAILLE_SCORE)*TAILLE_TILE,NB_LIG*TAILLE_TILE);
		Canvas canvas = new Canvas(NB_COL*TAILLE_TILE,NB_LIG*TAILLE_TILE);
	    root.getChildren().add(canvas);
	    g = canvas.getGraphicsContext2D();
	    root.getChildren().add(lineSeuil);
	    
	    boucleDeJeu();
  
	    return root;
	}
	
	private void boucleDeJeu() {
	    spawn();
	    draw();
	    AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                time += SPEED;

                if (time >= 0.5) {
                	chute();
                	draw();
                	next();       	
                    time = 0;
                }
            }
        };
        timer.start();
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
	    g2.clearRect(0, 0,141.0, 109.0);
	    
	    nextCurrent.getCases().forEach(c -> {
        	g2.setFill(Color.BLACK);
        	g2.fillRect((c.getX()-2) * TAILLE_TILE, (c.getY()) * TAILLE_TILE  +5, TAILLE_TILE, TAILLE_TILE);
        	g2.setFill(nextCurrent.getCouleur());
        	g2.fillRect(((c.getX()-2) * TAILLE_TILE)+2, ((c.getY()) * TAILLE_TILE)+2 +5, TAILLE_TILE-2, TAILLE_TILE-2);
        });
	    
	    lesFormes.add(current);
	    nbPiece.setText(lesFormes.size()+"");
	    addFtoGrille(current);
	}
	
	public static void addFtoGrille(Forme f) {
		f.getCases().forEach(c -> grille[c.getX()][c.getY()] = c);
	}
	public static void removeFtoGrille(Forme f) {
		f.getCases().forEach(c -> grille[c.getX()][c.getY()] = null);
	}

	public void chute() {
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
			e.printStackTrace();
		}
	}
	
	public void next() {
		if(canNext()) {
			//printGrille();
			flag = false;
			supprimer();
			if(finDuGame()) {
				System.out.println("Ton score est de "+ lesFormes.size());
				return;
			}
			flag = true;
			spawn();
			draw();
			//System.out.println("fin next");	
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
		//System.out.println("fin suppr");
	}

	private void suppLine(int i) {
		nblignesuppr++;
		nbLigne.setText(nblignesuppr+"");
		Case c;
		for(int j=0; j<NB_COL; j++) {
			c = grille[j][i];
			Forme parent = c.getParent();
			removeFtoGrille(parent);
			parent.getCases().remove(c);
			addFtoGrille(parent);
			//System.out.println("tour");
		}


		descentePostSuppr(i);
		//System.out.println("fin suppline");
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

			//System.out.println("ligne "+y+" descendu");
		}
		//System.out.println("fin descentePostSuppr");
		
	}




}