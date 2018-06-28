package Formes;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class FL2 extends Forme {

	public FL2() {
		cases = new ArrayList<Case>();
		cases.add(new Case(this, this.x, this.y));
		cases.add(new Case(this, this.x, this.y+1));
		cases.add(new Case(this, this.x, this.y+2));
		cases.add(new Case(this, this.x-1, this.y+2));
		couleur = Color.GREENYELLOW;
	}
}
