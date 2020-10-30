package model.heroes;

import static org.junit.Assert.fail;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;

import engine.ActionValidator;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.DivineSpirit;
import model.cards.spells.HolyNova;
import model.cards.spells.ShadowWordDeath;

public class Priest extends Hero {
	
	public Priest() throws IOException, CloneNotSupportedException {
		
		super("Anduin Wrynn");
		
	}
	
	public void buildDeck() throws IOException, CloneNotSupportedException {
		
		ArrayList<Card>deck = new ArrayList<>();
		deck = getDeck();
		deck .addAll(getNeutralMinions(getAllNeutralMinions("neutral_minions.csv"), 13));
		
		DivineSpirit divineSpirit = new DivineSpirit();
		HolyNova holyNova = new HolyNova();
		ShadowWordDeath shadowWordDeath = new ShadowWordDeath();
		Minion minion = new Minion("Prophet Velen", 7, Rarity.LEGENDARY, 7, 7, false, false, false);
		
		
		deck.add(divineSpirit.clone());deck.add(divineSpirit.clone());
		deck.add(holyNova.clone());deck.add(holyNova.clone());
		deck.add(shadowWordDeath.clone());deck.add(shadowWordDeath.clone());
		deck.add(minion.clone());
		
		Collections.shuffle(deck);
		

		
	}
	
	public void useHeroPower(Minion minion) throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException,
	FullHandException, FullFieldException, CloneNotSupportedException {
		
		super.useHeroPower();
		
		if(super.specialSearch("Prophet Velen")) {
			
			minion.setCurrentHP(minion.getCurrentHP()+8);

		}
		
		else {
			
			minion.setCurrentHP(minion.getCurrentHP()+2);

		}
		
				
	}


	public void useHeroPower(Hero hero) throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException,
	FullHandException, FullFieldException, CloneNotSupportedException {

		super.useHeroPower();
		
		if(super.specialSearch("Prophet Velen")) {
			
			hero.setCurrentHP(hero.getCurrentHP()+8);

		}
		
		else {
			
			hero.setCurrentHP(hero.getCurrentHP()+2);

		}
		
	

	}
	

}
