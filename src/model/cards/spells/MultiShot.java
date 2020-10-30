package model.cards.spells;

import java.util.ArrayList;
import java.util.Random;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class MultiShot extends Spell implements AOESpell {
	
	public MultiShot() {
		
		super("Multi-Shot",4,Rarity.BASIC);
		
	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {

		Random random = new Random();
		Minion minion;
		int first = 0;
		
		
		
		if(oppField.size()>=1) {
			
			int n = random.nextInt(oppField.size());
						
			minion = oppField.get(n);
			
			if(minion.isDivine())minion.setDivine(false);
			
			else {
				
				minion.setCurrentHP(minion.getCurrentHP()-3);
								
			}
			
			
			first = n;
			
			
		}
		
		
		while(true && oppField.size()>=2) {
			
			int n = random.nextInt(oppField.size());
			
			if(n==first) continue;
			
			minion = oppField.get(n);
			
			if(minion.isDivine())minion.setDivine(false);
			
			else {
				
				minion.setCurrentHP(minion.getCurrentHP()-3);
								
			}
			
			break;
			
		}
		
			
		
	}

}
