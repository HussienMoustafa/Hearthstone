package model.heroes;

import java.io.BufferedReader;


import java.io.FileReader;
import java.io.IOException;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Random;

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
import model.cards.minions.Icehowl;
import model.cards.minions.Minion;
import model.cards.minions.MinionListener;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Spell;

public abstract class Hero implements MinionListener {
	
	private String name;
	private int currentHP;
	private boolean heroPowerUsed;
	private int totalManaCrystals;
	private int currentManaCrystals;
	private ArrayList<Card> deck;
	private ArrayList<Minion> field;
	private ArrayList<Card> hand;
	private int fatigueDamage;
	private HeroListener listener;
	private ActionValidator validator;
	
	
	public Hero(String name) throws IOException, CloneNotSupportedException  {

		this.name = name;
		currentHP = 30;
		deck = new ArrayList<Card>();
		field = new ArrayList<Minion>();
		hand = new ArrayList<Card>();
		buildDeck();
		assignListeners();
	}
	
	public abstract void buildDeck() throws IOException, CloneNotSupportedException ;


	public static final ArrayList<Minion> getAllNeutralMinions(String filePath) throws IOException{

		
		FileReader fileReader = new FileReader(filePath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String currentLine = "";
		ArrayList<Minion> minions = new ArrayList<>();
		
		while((currentLine = bufferedReader.readLine())!=null) {
			
			String[]minionInfo = currentLine.split(",");
			minions.add(getMinion(minionInfo));
		}
		
		bufferedReader.close();
		
		
		return minions;
		
		
	}
	
	private static final Minion getMinion(String[]minionInfo) {

		
		Rarity rarity ;
		boolean taunt,divine,charge;
		
		String name = minionInfo[0];
		
		int manaCost  = Integer.parseInt(minionInfo[1]);
		
		char c = minionInfo[2].charAt(0);
		if(c=='b')rarity = Rarity.BASIC;
		else if(c=='l')rarity = Rarity.LEGENDARY;
		else if(c=='e')rarity = Rarity.EPIC;
		else if(c=='c')rarity = Rarity.COMMON;
		else rarity = Rarity.RARE;
			
		int attack = Integer.parseInt(minionInfo[3]);
		
		int maxHP = Integer.parseInt(minionInfo[4]);
		
		char temp1 = minionInfo[5].toLowerCase().charAt(0);
		if(temp1=='t') taunt = true;
		else taunt = false;
		
		
		temp1 = minionInfo[6].toLowerCase().charAt(0);
		if(temp1=='t') divine = true;
		else divine = false;
		
		
		temp1 = minionInfo[7].toLowerCase().charAt(0);
		if(temp1=='t') charge = true;
		else charge = false;
		
		Minion minion;
		
		if(name.equals("Icehowl")) {
			minion = new Icehowl();
		}
		else {
			 minion = new Minion(name, manaCost, rarity, attack, maxHP, taunt, divine, charge);
		}
		
		return minion;
	}
	
	public static final ArrayList<Minion> getNeutralMinions(ArrayList<Minion> minions,int count) throws CloneNotSupportedException{



		
		int freq[] = new int[14];
        Random rand = new Random(); 
        ArrayList<Minion>choices = new ArrayList<>();

		
		for(int i=0;i<count;i++) {
			
			int num = rand.nextInt(14);
			
			if(freq[num]==2)i--;
			
			else if(freq[num]==1) {
				if(minions.get(num).getRarity()!=Rarity.LEGENDARY) {
					choices.add(minions.get(num).clone());
					freq[num]++;
				}
				
				else i--;
			}
			
			else {
				choices.add(minions.get(num).clone());
				freq[num]++;
			}
			
		}
		
		return choices;
	
	
	}
	
	public void useHeroPower() throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException,
	FullHandException,FullFieldException, CloneNotSupportedException{
		
		validator.validateUsingHeroPower(this);
		validator.validateTurn(this);
		setCurrentManaCrystals(getCurrentManaCrystals()-2);
		setHeroPowerUsed(true);

		
	}
	
	public void playMinion(Minion m) throws NotYourTurnException,NotEnoughManaException, FullFieldException{



		validator.validateTurn(this);

		validator.validatePlayingMinion(m);
		
		validator.validateManaCost(m);
		
		
		field.add(m);
		hand.remove(m);
		setCurrentManaCrystals(currentManaCrystals-m.getManaCost());
		
		
	}
	
	public void attackWithMinion(Minion attacker, Minion target) throws CannotAttackException, 
	NotYourTurnException, TauntBypassException,InvalidTargetException, NotSummonedException{
		
		validator.validateTurn(this);

		validator.validateAttack(attacker, target);
		
		attacker.attack(target);
		
	}
	
	public void attackWithMinion(Minion attacker, Hero target) throws CannotAttackException, 
	NotYourTurnException, TauntBypassException,NotSummonedException, InvalidTargetException{
		
		validator.validateTurn(this);
		validator.validateAttack(attacker, target);
		
		attacker.attack(target);
		
	}
	
	public void castSpell(FieldSpell s) throws NotYourTurnException,NotEnoughManaException{

		
		if(s instanceof Spell) {
			
			Spell spell = (Spell)s;
						
			validateSpell(spell);
			
			s.performAction(field);
			
			
		}
		
		
	}
	
	public void castSpell(AOESpell s, ArrayList<Minion >oppField) throws NotYourTurnException, NotEnoughManaException{
		
		if(s instanceof Spell) {
			
			Spell spell = (Spell)s;
			
			validateSpell(spell);
			
			s.performAction(oppField, field);
			
			
		}
		
	}
	
	public void castSpell(MinionTargetSpell s, Minion m) throws NotYourTurnException,
	NotEnoughManaException, InvalidTargetException{
		
		if(s instanceof Spell) {
			
			Spell spell = (Spell)s;
			
			validateSpell(spell);
			
			s.performAction(m);
			
			
		}
		
	}
	
	public void castSpell(HeroTargetSpell s, Hero h) throws NotYourTurnException,NotEnoughManaException{
		
		if(s instanceof Spell) {
			
			Spell spell = (Spell)s;
			
			validateSpell(spell);
			
			s.performAction(h);
			
			
		}
	}
	
	public void castSpell(LeechingSpell s, Minion m) throws NotYourTurnException,NotEnoughManaException{
	
		if(s instanceof Spell) {
			
			Spell spell = (Spell)s;
			
			validateSpell(spell);
			
			int health = s.performAction(m);
			
			setCurrentHP(currentHP+health);
			
			
		}

		
	}
	
	public void endTurn() throws FullHandException, CloneNotSupportedException{
		
		listener.endTurn();
		
	}
	
	
	public Card drawCard() throws FullHandException, CloneNotSupportedException{
		
		if(deck.size()==0) {
			
			
			setCurrentHP(currentHP-fatigueDamage);
			
			fatigueDamage++;
			
			return null ; 
			
		}
		
		else {
			
			Card card = deck.remove(0);
			
			if(deck.size()==0 && fatigueDamage==0) fatigueDamage=1;
				
			if(getHand().size()==10) throw new FullHandException("your hand is full of cards ", card);

			hand.add(card);
			
			if(specialSearch("Chromaggus")) {
				
				if(getHand().size()<10) hand.add(card.clone());
				
			}
			
			return card;
			
			
		}
		
		
		
	}
	
	public final boolean specialSearch(String name) {
		
		for(Minion minion : field) {
			if(minion.getName().equals(name)) return true;
		}
		
		return false;
		
	}
	
	
	public void onMinionDeath(Minion m) {
		
		this.field.remove(m);
		
	}
	
	public final void assignListeners() {
		
		for(Card card : deck) {
			
			if(card instanceof Minion) {
				((Minion) card).setListener(this);
			}
			
		}
		
	}
	
	public void validateSpell(Spell card) throws NotYourTurnException, NotEnoughManaException {


		validator.validateTurn(this);
		validateMagePower(card);
		validator.validateManaCost(card);
		
		setCurrentManaCrystals(currentManaCrystals-card.getManaCost());
		hand.remove(card);
		
	}
	
	public final void validateMagePower(Spell s)  {
		
		if(specialSearch("Kalycgos")) {
			
			//if(s.getManaCost()-4>getCurrentManaCrystals()) throw new NotEnoughManaException("you don't have enough mana crystals");
			
			s.setManaCost(s.getManaCost()-4);
		}
		
	}

	
	
	
	/////////////////////////////////// Getters and Setters ///////////////////////////////////////////

	
	public String getName() {
		return name;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {

		this.currentHP = Math.min(currentHP,30);
		this.currentHP = Math.max(0,this.currentHP);
		
		if(this.currentHP==0) listener.onHeroDeath();
		
	}

	public int getTotalManaCrystals() {
		return totalManaCrystals;
	}

	public void setTotalManaCrystals(int totalManaCrystals) {
		this.totalManaCrystals = Math.min(totalManaCrystals,10);
	}
	
	public int getCurrentManaCrystals() {
		return currentManaCrystals;
	}

	public void setCurrentManaCrystals(int currentManaCrystals) {
		this.currentManaCrystals = Math.min(currentManaCrystals,10);
	}

	public boolean isHeroPowerUsed() {
		return heroPowerUsed;
	}

	public void setHeroPowerUsed(boolean heroPowerUsed) {
		this.heroPowerUsed = heroPowerUsed;
	}

	public ArrayList<Card> getDeck() {

		return deck;
	}

	public ArrayList<Minion> getField() {
		return field;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public HeroListener getListener() {
		return listener;
	}

	public void setListener(HeroListener listener) {
		this.listener = listener;
	}

	public void setValidator(ActionValidator validator) {
		this.validator = validator;
	}

	
	
	


	

}
