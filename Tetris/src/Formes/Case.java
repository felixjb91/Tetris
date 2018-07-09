package Formes;

public class Case{
	private int x,y;
	private Forme parent;
	
	public Case(Forme f, int x, int y){
		parent = f;
		this.x = x;
		this.y = y;	
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Forme getParent() {
		return parent;
	}

	public void move(int a, int b) {
		x+=a;
		y+=b;
		
	}
	public String toString() {
		return parent.getId()+"";
	}
	
}
