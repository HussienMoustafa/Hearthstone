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
import model.cards.spells.LevelUp;
import model.cards.spells.SealOfChampions;

public class Paladin extends Hero {
	
	public Paladin() throws IOException, CloneNotSupportedException {
		
		super("Uther Lightbringer");
		
	}
	
	public  void buildDeck() throws IOException, CloneNotSupportedException {
		
		ArrayList<Card>deck = new ArrayList<>();
		deck = getDeck();
		deck .addAll(getNeutralMinions(getAllNeutralMinions("neutral_minions.csv"), 15));
		
		SealOfChampions sealOfChampions = new SealOfChampions();
		LevelUp levelUp = new LevelUp();
		Minion minion = new Minion("Tirion Fordring", 4, Rarity.LEGENDARY, 6, 6,true, true, false);
		
		
		deck.add(sealOfChampions.clone());deck.add(sealOfChampions.clone());
		deck.add(levelUp.clone());deck.add(levelUp.clone());
		deck.add(minion.clone());
		
		Collections.shuffle(deck);
		
		
		
	}
	
	@Override
	
	public void playMinion(Minion m) throws NotYourTurnException,NotEnoughManaException, FullFieldException{
		
		super.playMinion(m);
		
		
		
		
		
		
	}
	
	
	
	
	
	@Override
	public void useHeroPower() throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException, FullFieldException, CloneNotSupportedException {
		
		if(getField().size()==7) throw new FullFieldException();
		super.useHeroPower();
		Minion minion = new Minion("Silver Hand Recruit", 1, Rarity.BASIC, 1, 1, false, false, false);
		getField().add(minion);
		
		
	}

	/*public void useHeroPower(Minion minion) throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException,
	FullHandException, FullFieldException, CloneNotSupportedException {
		useHeroPower();

}


	public void useHeroPower(Hero hero) throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException,
	FullHandException, FullFieldException, CloneNotSupportedException {

		useHeroPower();

}*/

	

}
