package Formes;
import Structure.Grille;
import Structure.Position;

public class FL extends Forme {

	public FL(Grille g) {
		super(g);
		int x,y;
		x=posDepart.getX();
		y=posDepart.getY();
		this.c1 = g.getCase(posDepart);
		this.c2 = g.getCase(new Position(x+1,y));
		this.c3 = g.getCase(new Position(x+2,y));
		this.c4 = g.getCase(new Position(x+2,y+1));
		setValue();
		System.out.println(id+ " , " +c1 + "," + c2 + "," + c3 + "," + c4);
	}

	@Override
	public String toString() {
		return "FL [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
