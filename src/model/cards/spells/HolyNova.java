package model.cards.spells;

import java.util.ArrayList;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class HolyNova extends Spell implements AOESpell {
	
	public HolyNova() {
		
		super("Holy Nova",5,Rarity.BASIC);
		
	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {

		ArrayList<Minion>minions  = (ArrayList<Minion>)oppField.clone();
		
		for(Minion minion : minions) {
			if(minion.isDivine())minion.setDivine(false);
			else minion.setCurrentHP(minion.getCurrentHP()-2);
		}
		
		for(Minion minion: curField) {
			minion.setCurrentHP(minion.getCurrentHP()+2);
		}
		
	}

}
