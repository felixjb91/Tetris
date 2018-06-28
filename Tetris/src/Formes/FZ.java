package Formes;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class FZ extends Forme {

	public FZ() {
		cases = new ArrayList<Case>();
		cases.add(new Case(this, this.x, this.y));
		cases.add(new Case(this, this.x, this.y+1));
		cases.add(new Case(this, this.x-1, this.y+1));
		cases.add(new Case(this, this.x-1, this.y+2));
		couleur = Color.DARKMAGENTA;
	}
}
