package Formes.States;

import Formes.FL;
import Formes.FTrio;
import Formes.Forme;
import Structure.Case;
import Structure.Grille;
import Structure.Position;

public class Sens3 implements ISens {

	@Override
	public synchronized void rotation(Forme f) {
		Case ca1,ca2,ca3,ca4;
		ca1 = f.getC1();
		ca2 = f.getC2();
		ca3 = f.getC3();
		ca4 = f.getC4();
		Position a,b,c,d;
		Grille g = f.getG();
		if(f instanceof FTrio){
			a = new Position(ca1.getX()-1,ca1.getY()+1);
			b = new Position(ca2.getX()-1,ca2.getY()-1);
			c = ca3.getP();
			d = new Position(ca4.getX()+1,ca4.getY()+1);
			
			if(g.peutAllerLa(ca1,a) && g.peutAllerLa(ca2,b) && g.peutAllerLa(ca3,c) && g.peutAllerLa(ca4,d)){
				f.setC1(g.getCase(a));
				f.setC2(g.getCase(b));
				f.setC3(g.getCase(c));
				f.setC4(g.getCase(d));
			}	
			ca1.liberer();
			ca2.liberer();
			ca3.liberer();
			ca4.liberer();
			f.setValue();
			f.nextSens(new Sens4());
		}
		else if(f instanceof FL){
			a = new Position(ca1.getX()-1,ca1.getY()+1);
			b = ca2.getP();
			c = new Position(ca3.getX()+1,ca3.getY()-1);
			d = new Position(ca4.getX()+2,ca4.getY());
			
			if(g.peutAllerLa(ca1,a) && g.peutAllerLa(ca2,b) && g.peutAllerLa(ca3,c) && g.peutAllerLa(ca4,d)){
				f.setC1(g.getCase(a));
				f.setC2(g.getCase(b));
				f.setC3(g.getCase(c));
				f.setC4(g.getCase(d));
			}	
			ca1.liberer();
			ca2.liberer();
			ca3.liberer();
			ca4.liberer();
			f.setValue();
			f.nextSens(new Sens4());
		}
	}

}
