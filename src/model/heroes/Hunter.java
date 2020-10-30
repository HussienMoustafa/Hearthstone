package model.heroes;

import static org.junit.Assert.fail;


import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import model.cards.spells.KillCommand;
import model.cards.spells.MultiShot;

public class Hunter extends Hero {

	public Hunter() throws IOException, CloneNotSupportedException {
		
		super("Rexxar");
		
	}
	
	public void buildDeck() throws IOException, CloneNotSupportedException  {
		
		ArrayList<Card>deck = new ArrayList<>();
		deck = getDeck();
		deck .addAll(getNeutralMinions(getAllNeutralMinions("neutral_minions.csv"), 15));
		
		KillCommand killCommand = new KillCommand();
		MultiShot multiShot = new MultiShot();
		Minion minion = new Minion("King Krush", 9, Rarity.LEGENDARY, 8, 8, false, false, true);
		
		deck.add(killCommand.clone());deck.add(killCommand.clone());
		deck.add(multiShot.clone());deck.add(multiShot.clone());
		deck.add(minion.clone());
		
		
		
		Collections.shuffle(deck);
		

		
	}
	
	
	public void useHeroPower() throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException,
	FullHandException, FullFieldException, CloneNotSupportedException {
		super.useHeroPower();
		getListener().damageOpponent(2);

	}
	
	
	

	
	
	

	
}
