package model.cards;

public abstract class Card implements Cloneable {
	
	private String name;
	private Rarity rarity;
	private int manaCost;
	
	public Card(String name,int manaCost,Rarity rarity)  {
		
		this.name = name;
		this.rarity = rarity;
		setManaCost(manaCost);

		
		
	}
	
	@Override
	public Card clone() throws CloneNotSupportedException {
		return (Card)super.clone();
	}
	
	
	////////////////////////////////// Getters and Setters /////////////////////////////////////////////

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public int getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		
		this.manaCost = Math.max(0,manaCost);
		this.manaCost = Math.min(10,this.manaCost);

		
	}
	
	

}
