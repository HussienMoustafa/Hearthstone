package model.cards.spells;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;

import model.cards.Rarity;
import model.cards.minions.Minion;
import model.heroes.Mage;

public class Flamestrike extends Spell implements AOESpell {
	
	public Flamestrike() {
		
		super("Flamestrike",7,Rarity.BASIC);
		
	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {

		
		ArrayList<Minion>minions  = (ArrayList<Minion>)oppField.clone();

		
		for(Minion minion : minions) {
			if(minion.isDivine())minion.setDivine(false);
			else minion.setCurrentHP(minion.getCurrentHP()-4);
		}
				
		
	}
	
	
}
