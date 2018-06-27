package Formes;

import java.util.ArrayList;

public class FCarre extends Forme  {
	public FCarre() {
		cases = new ArrayList<Case>();
		cases.add(new Case(this, this.x, this.y));
		cases.add(new Case(this, this.x, this.y+1));
		cases.add(new Case(this, this.x+1, this.y+1));
		cases.add(new Case(this, this.x+1, this.y));
	}
}
