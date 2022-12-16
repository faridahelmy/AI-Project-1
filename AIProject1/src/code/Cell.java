package code;


public class Cell {
	
	int i;
	int j;
	boolean isShip;
	boolean isStation;
	
	public Cell(int i, int j) {
		this.i=i;
		this.j=j;
		this.isShip=false;
		this.isStation=false;
	}
public boolean isOccupied() {
	if(isShip==true || isStation==true)
		return true;
	return false;
}
public boolean isShip() {
	return isShip;
}
public void setShip(boolean isShip) {
	this.isShip = isShip;
}
public boolean isStation() {
	return isStation;
}
public void setStation(boolean isStation) {
	this.isStation = isStation;
}

public String toString() {
	
	String res="";
	res+="[";
	res+=this.i;
	res+=",";
	res+=this.j;
	
	
	if(this.isShip()) {	
		res+=",ship";	
	}
	else if(this.isStation()) {
		res+=",station";
	}
	else {
		res+=",empty";
	}
	
	res+="]";
	
	return res;
}
	
}
