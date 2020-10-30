package model.cards.minions;

import static org.junit.Assert.fail;

import java.io.IOException;

import exceptions.CannotAttackException;
import exceptions.InvalidTargetException;
import model.cards.Card;
import model.cards.Rarity;
import model.heroes.Hero;
import model.heroes.Mage;

public class Minion extends Card implements Cloneable {
	
	private int attack;
	private int maxHP;
	private int currentHP;
	private boolean taunt;
	private boolean divine;
	private boolean sleeping;
	private boolean attacked;
	private MinionListener listener;




	public Minion(String name,int manaCost,Rarity rarity,
			int attack,int maxHP,boolean taunt, boolean divine,boolean charge) {
		
		super(name,manaCost,rarity);
		
		setAttack(attack);
		this.maxHP = maxHP;
		this.taunt = taunt;
		this.divine = divine;
		
		currentHP = maxHP;
		
		if(!charge) {
			sleeping = true;
		}
		
		
	}
	
	
	public void attack(Minion target) {
		
		if(target.isDivine() && attack>0 ) target.setDivine(false);
		else target.setCurrentHP(target.getCurrentHP()-attack);
		
		if(divine && target.attack>0)setDivine(false);
		else setCurrentHP(currentHP-target.getAttack());
		
		attacked = true;
		
	}
	
	public void attack(Hero target) throws InvalidTargetException{
		
		
		if(getName().equals("Icehowl"))throw new InvalidTargetException("Icehowl cannot attack heroes");
		
		else {
			
			target.setCurrentHP(target.getCurrentHP()-attack);
			attacked = true;

		}
		

	}
	
	
	@Override
	public Minion clone() throws CloneNotSupportedException{
		
		return ((Minion)super.clone());
	}
	
	
	
	
	

	
	/////////////////////////////////// Getters and Setters ///////////////////////////////////////////


	public int getAttack() {
		return attack;
	}


	public void setAttack(int attack) {
		
		this.attack = Math.max(0,attack);
		
	}


	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		
		this.currentHP = Math.max(0,currentHP);
		this.currentHP = Math.min(this.currentHP,this.maxHP);
		
		if(this.currentHP==0) listener.onMinionDeath(this);
		
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public boolean isSleeping() {
		return sleeping;
	}

	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
	}

	public boolean isDivine() {
		return divine;
	}

	public void setDivine(boolean divine) {
		this.divine = divine;
	}

	public boolean isTaunt() {
		return taunt;
	}

	public void setTaunt(boolean taunt) {
		this.taunt = taunt;
	}
	
	public boolean isAttacked() {
		return attacked;
	}


	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}
	
	public void setListener(MinionListener listener) {
		this.listener = listener;
	}
	
	
	
	

}
