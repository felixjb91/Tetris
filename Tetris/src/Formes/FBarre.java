package Formes;
import Structure.Grille;
import Structure.Position;

public class FBarre extends Forme {

	public FBarre(Grille g) {
		super(g);
		int x,y;
		x=posDepart.getX();
		y=posDepart.getY();
		this.c1 = g.getCase(posDepart);
		this.c2 = g.getCase(new Position(x+1,y));
		this.c3 = g.getCase(new Position(x+2,y));
		this.c4 = g.getCase(new Position(x+3,y));
		setValue();
		System.out.println(id+ " , " +c1 + "," + c2 + "," + c3 + "," + c4);
	}

	@Override
	public String toString() {
		return "FBarre [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
