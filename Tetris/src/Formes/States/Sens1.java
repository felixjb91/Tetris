package Formes.States;

import Formes.FBarre;
import Formes.FL;
import Formes.FL2;
import Formes.FTrio;
import Formes.FZ;
import Formes.FZ2;
import Formes.Forme;
import MainTest.EditorMain;
public class Sens1 implements ISens {

	@Override
	public synchronized void rotation(Forme f) {
		if(f instanceof FTrio){
			if(EditorMain.peutAller(f.getCases().get(0),-1, 1) && EditorMain.peutAller(f.getCases().get(1),1, 1) && EditorMain.peutAller(f.getCases().get(3),-1, -1))
			{
				EditorMain.removeFtoGrille(f);
				f.getCases().get(0).move(-1, 1);
				f.getCases().get(1).move(1, 1);
				f.getCases().get(3).move(-1, -1);
				EditorMain.addFtoGrille(f);
				f.nextSens(new Sens2());
			}
		}
		else if(f instanceof FBarre){
			if(EditorMain.peutAller(f.getCases().get(0),-1, 1) && EditorMain.peutAller(f.getCases().get(2),1, -1) && EditorMain.peutAller(f.getCases().get(3),2, -2))
			{
				EditorMain.removeFtoGrille(f);
				f.getCases().get(0).move(-1, 1);
				f.getCases().get(2).move(1, -1);
				f.getCases().get(3).move(2, -2);
				EditorMain.addFtoGrille(f);	
				f.nextSens(new Sens2());
			}
		}
		else if(f instanceof FL){
			if(EditorMain.peutAller(f.getCases().get(0),-1, 1) && EditorMain.peutAller(f.getCases().get(2),1, -1) && EditorMain.peutAller(f.getCases().get(3),0, -2))
			{
				EditorMain.removeFtoGrille(f);
				f.getCases().get(0).move(-1, 1);
				f.getCases().get(2).move(1, -1);
				f.getCases().get(3).move(0, -2);
				EditorMain.addFtoGrille(f);
				f.nextSens(new Sens2());
			}
		}
		else if(f instanceof FL2){
			if(EditorMain.peutAller(f.getCases().get(0),1, 1) && EditorMain.peutAller(f.getCases().get(2),-1, -1) && EditorMain.peutAller(f.getCases().get(3),0, -2))
			{
				EditorMain.removeFtoGrille(f);
				f.getCases().get(0).move(1, 1);
				f.getCases().get(2).move(-1, -1);
				f.getCases().get(3).move(0, -2);
				EditorMain.addFtoGrille(f);
				f.nextSens(new Sens2());
			}
		}
		else if(f instanceof FZ){
			if(EditorMain.peutAller(f.getCases().get(0),-1, 1) && EditorMain.peutAller(f.getCases().get(2),1, 1) && EditorMain.peutAller(f.getCases().get(3),2, 0))
			{
				EditorMain.removeFtoGrille(f);
				f.getCases().get(0).move(-1, 1);
				f.getCases().get(2).move(1, 1);
				f.getCases().get(3).move(2, 0);
				EditorMain.addFtoGrille(f);
				f.nextSens(new Sens2());
			}
		}
		else if(f instanceof FZ2){
			if(EditorMain.peutAller(f.getCases().get(0),-1, 1) && EditorMain.peutAller(f.getCases().get(2),-1, -1) && EditorMain.peutAller(f.getCases().get(3),0, -2))
			{
				EditorMain.removeFtoGrille(f);
				f.getCases().get(0).move(-1, 1);
				f.getCases().get(2).move(-1, -1);
				f.getCases().get(3).move(0, -2);
				EditorMain.addFtoGrille(f);
				f.nextSens(new Sens2());
			}
		}
	}
}
