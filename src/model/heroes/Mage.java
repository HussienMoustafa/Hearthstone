package model.heroes;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;

import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.NotEnoughManaException;
import exceptions.NotYourTurnException;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.Flamestrike;
import model.cards.spells.Polymorph;
import model.cards.spells.Pyroblast;

public class Mage extends Hero {
	
	public Mage() throws IOException, CloneNotSupportedException {
		
		super("Jaina Proudmoore");
		
	}
	
	public  void buildDeck() throws IOException, CloneNotSupportedException {
		
		ArrayList<Card>deck = new ArrayList<>();
		deck = getDeck();
		deck .addAll(getNeutralMinions(getAllNeutralMinions("neutral_minions.csv"), 13));
		
		Polymorph polymorph = new Polymorph();
		Flamestrike flamestrike = new Flamestrike();
		Pyroblast pyroblast = new Pyroblast();
		Minion minion = new Minion("Kalycgos", 10, Rarity.LEGENDARY, 4, 12, false, false, false);
		
		deck.add(polymorph.clone());deck.add(polymorph.clone());
		deck.add(flamestrike.clone());deck.add(flamestrike.clone());
		deck.add(pyroblast.clone());deck.add(pyroblast.clone());
		deck.add(minion.clone());
		
		Collections.shuffle(deck);

		
	}
	
	public void useHeroPower(Minion minion) throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException,
	FullHandException, FullFieldException, CloneNotSupportedException {
		
		super.useHeroPower();
		
		if(minion.isDivine()) minion.setDivine(false);
		else {
			minion.setCurrentHP(minion.getCurrentHP()-1);
		}
		
		
	}


	public void useHeroPower(Hero hero) throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException,
	FullHandException, FullFieldException, CloneNotSupportedException {

		super.useHeroPower();
		
		//getListener().damageOpponent(1);
		
		hero.setCurrentHP(hero.getCurrentHP()-1);
		


	}

}
