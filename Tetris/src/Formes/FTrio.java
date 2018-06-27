package Formes;

import java.util.ArrayList;

public class FTrio extends Forme{
	public FTrio() {
		cases = new ArrayList<Case>();
		cases.add(new Case(this, this.x, this.y));
		cases.add(new Case(this, this.x-1, this.y+1));
		cases.add(new Case(this, this.x, this.y+1));
		cases.add(new Case(this, this.x+1, this.y+1));
	}
}
