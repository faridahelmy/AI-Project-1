package code;
import java.lang.Math;

public class Ship implements Cloneable{

	int nbPass;
	int damage;
	boolean blackBox;
	int passLeft;
	int i;
	int j;
	
	public Ship(int i, int j) {
		this.i=i;
		this.j=j;
		this.nbPass= (int)(Math.random() * 100+1)+1;
		this.damage=0;
		this.passLeft= nbPass;
		this.blackBox= true;
	}
	public Ship(int i, int j, int nbPass) {
		this.i=i;
		this.j=j;
		this.nbPass= nbPass;
		this.damage=0;
		this.passLeft= nbPass;
		this.blackBox= true;
	}
	
	public boolean isBlackBox() {
		if(damage>=100 || blackBox==false){
			blackBox=false;
		}
		return blackBox;
	}

	public void setBlackBox(boolean blackBox) {
		this.blackBox = blackBox;
	}

	public void setPassLeft(int passLeft) {
		this.passLeft = passLeft;
	}

	public boolean isWreck() {
		if(passLeft==0 && isBlackBox()) {
			return true;
		}
		return false;
	}
	
	public void expire() {
		if(passLeft>0)
			passLeft-=1;
	}
	
	public void damage() {
		
		if(damage>=100) {
			return;
		}
		
		if(isWreck() && isBlackBox())
			damage++;
	}
	
	public int getNbPass() {
		return nbPass;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getPassLeft() {
		return passLeft;
	}
	
	public int getI() {
		return i;
	}
	
	public int getJ() {
		return j;
	}

	@Override
	public String toString() {
		return "Ship [nbPass=" + nbPass + ", damage=" + damage + ", blackBox=" + blackBox + ", passLeft=" + passLeft
				+ ", i=" + i + ", j=" + j + "]";
	}
	protected Object clone() throws CloneNotSupportedException {
		return (Ship)super.clone();
	}
	

	
}
