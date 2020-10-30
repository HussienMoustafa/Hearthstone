package engine;

import java.util.Random;

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
import model.cards.minions.Minion;
import model.heroes.Hero;
import model.heroes.HeroListener;

public class Game implements ActionValidator,HeroListener {

	private Hero firstHero,secondHero,currentHero,opponent;
	private GameListener listener;
	
	public Game(Hero p1, Hero p2) throws FullHandException, CloneNotSupportedException {
		
		this.firstHero = p1;
		this.secondHero = p2;
		
		Random random = new Random();
		int player = random.nextInt(2);
		
		if(player==1) {
			currentHero = firstHero;
			opponent = secondHero;
		}
		
		else {
			currentHero = secondHero;
			opponent = firstHero;
		}
		
		currentHero.setTotalManaCrystals(1);
		currentHero.setCurrentManaCrystals(1);
		
		currentHero.setListener(this);
		currentHero.setValidator(this);
		
		opponent.setListener(this);
		opponent.setValidator(this);
		
		
		for(int i=0;i<3;i++)currentHero.drawCard();
		
		for(int i=0;i<4;i++)opponent.drawCard();
		
		
		
	}
	
	@Override
	public void validateTurn(Hero user) throws NotYourTurnException {

		if(!user.equals(currentHero)) throw new NotYourTurnException("This is not your turn !");
		
	}

	@Override
	public void validateAttack(Minion attacker, Minion target)
			throws CannotAttackException, NotSummonedException, TauntBypassException, InvalidTargetException {
		
		if(attacker.isSleeping()||attacker.isAttacked()||attacker.getAttack()==0)throw new CannotAttackException("this minion cannot attack");
		
		if(!currentHero.getField().contains(attacker)) throw new NotSummonedException("this minion is not found in the field");
		
		if(currentHero.getField().contains(target)) throw new InvalidTargetException("the target is one of the attacker's friends");
		
		if(!target.isTaunt()) {
			for(Minion minion: opponent.getField()) {
				if(minion.isTaunt()) throw new TauntBypassException("the target cannot be attacked while the opponet has a taunt in his field");
			}

		}
		
		if(!opponent.getField().contains(target)) throw new NotSummonedException("the target is not found in the opponent's field");
		
		
	}

	@Override
	public void validateAttack(Minion attacker, Hero target)
			throws CannotAttackException, NotSummonedException, TauntBypassException, InvalidTargetException {

		if(attacker.isSleeping()||attacker.isAttacked()||attacker.getAttack()==0)throw new CannotAttackException("this minion cannot attack");

		if(!currentHero.getField().contains(attacker)) throw new NotSummonedException("this minion is not found in the field");

		if(target.equals(currentHero)) throw new InvalidTargetException("you cannot attack yourself");
		
		for(Minion minion: opponent.getField()) {
			if(minion.isTaunt()) throw new TauntBypassException("the opponent cannot be attacked while he has a taunt in his field");
		}
		
		
	}

	@Override
	public void validateManaCost(Card card) throws NotEnoughManaException {

		if(currentHero.getCurrentManaCrystals()<card.getManaCost()) throw new NotEnoughManaException("you don't have enough mana crystals to use this card");
	}

	@Override
	public void validatePlayingMinion(Minion minion) throws FullFieldException {

		if(currentHero.getField().size()==7)throw new FullFieldException("The field is full !");
		
	}

	@Override
	public void validateUsingHeroPower(Hero hero) throws NotEnoughManaException, HeroPowerAlreadyUsedException {

		if(hero.isHeroPowerUsed()) throw new HeroPowerAlreadyUsedException("Hero power is already used !!");
		
		if(hero.getCurrentManaCrystals()<2) throw new NotEnoughManaException("you don't have enough mana crystals to use hero power !");
		
	}

	@Override
	public void onHeroDeath() {
		
		listener.onGameOver();
		
	}

	@Override
	public void damageOpponent(int amount) {

		opponent.setCurrentHP(opponent.getCurrentHP()-amount);
		
	}

	@Override
	public void endTurn() throws FullHandException, CloneNotSupportedException {
		
		if(currentHero.equals(firstHero)) {
			currentHero = secondHero;
			opponent = firstHero;
		}
		else {
			currentHero = firstHero;
			opponent = secondHero;
		}
		
		
		currentHero.setTotalManaCrystals(currentHero.getTotalManaCrystals()+1);
		currentHero.setCurrentManaCrystals(currentHero.getTotalManaCrystals());
		
		currentHero.setHeroPowerUsed(false);
		
		for(Minion minion : currentHero.getField()) {
			minion.setSleeping(false);
			minion.setAttacked(false);
		}
		
		currentHero.drawCard();
		
		
	}

	
	////////////////////////////////////  Getters and Setters  /////////////////////////////////////////
	

	public Hero getCurrentHero() {
		return currentHero;
	}


	public Hero getOpponent() {
		return opponent;
	}

	public void setListener(GameListener listener) {
		this.listener = listener;
	}
	
	

	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////

	
}
