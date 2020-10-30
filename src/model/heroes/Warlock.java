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
import model.cards.spells.CurseOfWeakness;
import model.cards.spells.SiphonSoul;
import model.cards.spells.TwistingNether;

public class Warlock extends Hero{
	
	public Warlock() throws IOException, CloneNotSupportedException  {
		
		super("Gul'dan");
		
	}
	
	public void buildDeck() throws IOException, CloneNotSupportedException {
		
		ArrayList<Card>deck = new ArrayList<>();
		deck = getDeck();
		deck .addAll(getNeutralMinions(getAllNeutralMinions("neutral_minions.csv"), 13));
		
		CurseOfWeakness curseOfWeakness = new CurseOfWeakness();
		SiphonSoul siphonSoul = new SiphonSoul();
		TwistingNether twistingNether = new TwistingNether();
		Minion minion = new Minion("Wilfred Fizzlebang", 6, Rarity.LEGENDARY, 4, 4, false, false, false);

		
		deck.add(curseOfWeakness.clone());deck.add(curseOfWeakness.clone());
		deck.add(siphonSoul.clone());deck.add(siphonSoul.clone());
		deck.add(twistingNether.clone());deck.add(twistingNether.clone());
		deck.add(minion.clone());
		
		Collections.shuffle(deck);
		
		
		
	}
	
	@Override
	public void useHeroPower() throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException,
	FullHandException, FullFieldException, CloneNotSupportedException {
		
		super.useHeroPower();
		
		setCurrentHP(getCurrentHP()-2);
		
		Card card = drawCard();
		
		if (specialSearch("Wilfred Fizzlebang") && card instanceof Minion) {
			card.setManaCost(0);
			if (specialSearch("Chromaggus"))
				getHand().get(getHand().size() - 1).setManaCost(0);
		}
		

		
	}
	

}
