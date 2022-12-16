package code;
import java.util.ArrayList;
public class State implements Cloneable{

	int cgI;
	int cgJ;
	int cgPass;
	int rescued;
	int retrieved;
	ArrayList<Ship> ships;
	ArrayList<String> actions;
	//int pathcost;
	//int estimated;
	
	@SuppressWarnings("unchecked")
	public State(int cgI, int cgJ, int cgPass, int rescued, int retrieved,ArrayList<Ship> ships, ArrayList<String> actions, String newAction) throws CloneNotSupportedException {
		
		this.cgI=cgI;
		this.cgJ=cgJ;
		this.cgPass=cgPass;
		
		ArrayList<Ship> newShips=new ArrayList<Ship>();
		
		for(int i=0;i<ships.size();i++) {
			newShips.add((Ship) ships.get(i).clone());
		}
		this.ships=newShips;

		this.rescued=rescued;
		this.retrieved=retrieved;
		this.actions=(ArrayList<String>) actions.clone();
		if(newAction!="") {
			this.actions.add(newAction);
		}
		
	}
	public int getTotalPassengers() {
		int total =0;
		for(int i=0;i<ships.size();i++) {
			total += ships.get(i).getNbPass();
			
		}
		return total;
	}

	public int getCgI() {
		return cgI;
	}

	public int getCgJ() {
		return cgJ;
	}

	public int getCgPass() {
		return cgPass;
	}

	public ArrayList<Ship> getShips() throws CloneNotSupportedException {
//		ArrayList<Ship> test = new ArrayList<Ship>();
//		for(int i=0;i<ships.size();i++) {
//			test.add((Ship) ships.get(i).clone());
//			
//		}
		return ships ;
	}

	public ArrayList<String> getActions() {
		return actions;
	}
	public int getRescued() {
		return rescued;
	}
	
	public int getRetrieved() {
		return retrieved;
	}
	@Override
	public String toString() {
		return "State [cgI=" + cgI + ", cgJ=" + cgJ + ", cgPass=" + cgPass + ", rescued=" + rescued + ", retrieved="
				+ retrieved + ", ships=" + ships + ", actions=" + actions + "]";
	}
	
	public Object clone() throws CloneNotSupportedException
    {
        State t = (State)super.clone();
 
   
        return t;
    }
	
//	public String toString() {
//		
//		String res="cgI=" + this.getCgI() + " cgJ=" + this.getCgJ() + " cgCap=" + this.getCgPass() + "\n";
//		res+="cgI=" + this.getCgI() + " cgJ=" + this.getCgJ() + " cgCap=" + this.getCgPass() + "\n";
//	}
//	
	
	
	
}
