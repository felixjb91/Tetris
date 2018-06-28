package Formes;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class FBarre extends Forme {

	public FBarre() {
		cases = new ArrayList<Case>();
		cases.add(new Case(this, this.x, this.y));
		cases.add(new Case(this, this.x, this.y+1));
		cases.add(new Case(this, this.x, this.y+2));
		cases.add(new Case(this, this.x, this.y+3));
		couleur = Color.DARKORANGE;
	}
}
