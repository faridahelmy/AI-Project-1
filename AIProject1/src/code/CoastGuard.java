package code;

import java.lang.Math;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
public class CoastGuard {

	public int i;
	public int j;
	public int capacity;
	public int pass;
	public static Grid gridGlobal;
	
	public CoastGuard(int i, int j, Grid grid) {
		this.i=i;
		this.j=j;
		this.capacity= (int)(Math.random() * 100-30+1)+30;
		this.pass=0;
		gridGlobal=grid;
		
	}
	public CoastGuard(int i, int j, int cap, Grid grid) {
		this.i=i;
		this.j=j;
		this.capacity= cap;
		this.pass=0;
		gridGlobal=grid;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

	public int getCapacity() {
		return capacity;
	}
	
	public State MoveUp(State state1) throws CloneNotSupportedException {
		State state=new State(state1.getCgI(),state1.getCgJ(),state1.getCgPass(),state1.getRescued(),state1.getRetrieved(),state1.getShips(),state1.getActions(),"");

		//	State state=(State) state1.clone();
		//ArrayList<String> stringtrial=new ArrayList<String>();
		//System.out.println("entered move up");
		int newI;
		if(state.getCgI()>0) {
			 newI=state.getCgI()-1;
		}
		else {
			 newI= state.getCgI();
			 //System.out.println("Move Up can not happen");
			 return new State(newI, state.getCgJ(),state.getCgPass(),state.getRescued(),state.getRetrieved(),state.getShips(), state.getActions(),"null");

		}
		for(int i=0;i<state.getShips().size();i++) {
			state.getShips().get(i).expire();
			state.getShips().get(i).damage();
		}
		
		State newState= new State(newI, state.getCgJ(),state.getCgPass(),state.getRescued(),state.getRetrieved(),state.getShips(), state.getActions(),"up");
		//System.out.println("Move up was successful");
		return newState;
		
		
	}
	
	public State MoveDown(State state1) throws CloneNotSupportedException {
		State state=new State(state1.getCgI(),state1.getCgJ(),state1.getCgPass(),state1.getRescued(),state1.getRetrieved(),state1.getShips(),state1.getActions(),"");

		//State state= (State) state1.clone();
		int newI;
		//System.out.println("entered move down");
		if(state.getCgI()<gridGlobal.getN()-1) {
			 newI=state.getCgI()+1;
		}
		else {
			 newI= state.getCgI();
			 //System.out.println("move down can't happen");
				return new State(newI, state.getCgJ(),state.getCgPass(),state.getRescued(),state.getRetrieved(),state.getShips(), state.getActions(),"null");

		}
		for(int i=0;i<state.getShips().size();i++) {
			state.getShips().get(i).expire();
			state.getShips().get(i).damage();
		}
		//System.out.println("move down was successful");
		return new State(newI, state.getCgJ(),state.getCgPass(),state.getRescued(),state.getRetrieved(),state.getShips(), state.getActions(),"down");
	}
	
	public State MoveLeft(State state1) throws CloneNotSupportedException {
		State state=new State(state1.getCgI(),state1.getCgJ(),state1.getCgPass(),state1.getRescued(),state1.getRetrieved(),state1.getShips(),state1.getActions(),"");

		//State state=(State) state1.clone();
		int newJ;
		if(state.getCgJ()>0) {
			 newJ=state.getCgJ()-1;
		}
		else {
			 newJ= state.getCgJ();
			 return new State(state.getCgI(), newJ,state.getCgPass(),state.getRescued(),state.getRetrieved(),state.getShips(), state.getActions(),"null");

		}
		for(int i=0;i<state.getShips().size();i++) {
			state.getShips().get(i).expire();
			state.getShips().get(i).damage();
		}
		return new State(state.getCgI(),newJ,state.getCgPass(),state.getRescued(),state.getRetrieved(),state.getShips(), state.getActions(),"left");
		
	}
	
	public State MoveRight(State state1) throws CloneNotSupportedException {
		State state=new State(state1.getCgI(),state1.getCgJ(),state1.getCgPass(),state1.getRescued(),state1.getRetrieved(),state1.getShips(),state1.getActions(),"");

		//State state=(State) state1.clone();
		int newJ;
		if(state.getCgJ()<gridGlobal.getM()-1) {
			 newJ=state.getCgJ()+1;
		}
		else {
			 newJ= state.getCgJ();
			 return new State(state.getCgI(), newJ,state.getCgPass(),state.getRescued(),state.getRetrieved(),state.getShips(), state.getActions(),"null");
		}
		for(int i=0;i<state.getShips().size();i++) {
			state.getShips().get(i).expire();
			state.getShips().get(i).damage();
		}
		return new State(state.getCgI(),newJ,state.getCgPass(),state.getRescued(),state.getRetrieved(),state.getShips(), state.getActions(),"right");
	}

	public State Retrieve(State state1) throws CloneNotSupportedException {
		State state=new State(state1.getCgI(),state1.getCgJ(),state1.getCgPass(),state1.getRescued(),state1.getRetrieved(),state1.getShips(),state1.getActions(),"");

		//State state=(State) state1.clone();
		int retrievals=state.getRetrieved() + 1;
		for(int k=0; k<state.getShips().size();k++) {
			if(state.getCgI()== state.getShips().get(k).getI() && state.getCgJ()== state.getShips().get(k).getJ()) {
				if(state.getShips().get(k).isWreck()) {
					state.getShips().get(k).setBlackBox(false);
					for(int i=0;i<state.getShips().size();i++) {
						//System.out.println(state.getShips().get(i).getPassLeft());
						state.getShips().get(i).expire();
						state.getShips().get(i).damage();
					}
					return new State(state.getCgI(), state.getCgJ(),state.getCgPass(),state.getRescued(),retrievals,state.getShips(), state.getActions(),"retrieve");
				}
			}
//			state.getShips().get(k).expire();
//			state.getShips().get(k).damage();
		}
		return new State(state.getCgI(), state.getCgJ(),state.getCgPass(),state.getRescued(),state.getRetrieved(),state.getShips(), state.getActions(),"null");
	}

	public State Drop(State state1) throws CloneNotSupportedException {
		State state=new State(state1.getCgI(),state1.getCgJ(),state1.getCgPass(),state1.getRescued(),state1.getRetrieved(),state1.getShips(),state1.getActions(),"");

		//	State state=(State) state1.clone();
		int passengers=state.getRescued()+state.getCgPass();
//		System.out.println("already rescued =" + state.getRescued());
//		System.out.println("pass on cg =" + state.getCgPass());
		for(int k=0; k<gridGlobal.getStationList().size();k++) {
			if(state.getCgI()== gridGlobal.getStationList().get(k).getI() && state.getCgJ()== gridGlobal.getStationList().get(k).getJ() && state.getCgPass()!=0) {
				for(int i=0;i<state.getShips().size();i++) {
					//System.out.println(state.getShips().get(i).getPassLeft());
					state.getShips().get(i).expire();
					state.getShips().get(i).damage();
				}
				//System.out.println("drop saved =" + passengers);
				return new State(state.getCgI(), state.getCgJ(),0,passengers,state.getRetrieved(),state.getShips(), state.getActions(),"drop");
			}
		}
		//System.out.println("drop state has failed");
		return new State(state.getCgI(), state.getCgJ(),state.getCgPass(),state.getRescued(),state.getRetrieved(),state.getShips(), state.getActions(),"null");

	}
	
	public State PickUp(State state1) throws CloneNotSupportedException {
		//State state=(State) state1.clone();
		State state=new State(state1.getCgI(),state1.getCgJ(),state1.getCgPass(),state1.getRescued(),state1.getRetrieved(),state1.getShips(),state1.getActions(),"");
		//System.out.println("entered pickup");
		for(int k=0; k<state.getShips().size();k++) {
			if(state.getCgI()== state.getShips().get(k).getI() && state.getCgJ()== state.getShips().get(k).getJ() && !state.getShips().get(k).isWreck() && state.getShips().get(k).getPassLeft()>0 && state.getCgPass()<capacity) {
				int currPass= state.getShips().get(k).getPassLeft(); //1
				int freeSpace= capacity- state.getCgPass(); //34
				int passToTake;
				if(freeSpace<currPass) {
					currPass-=freeSpace;
					passToTake=capacity;
				}
				else {
					passToTake=currPass+state.getCgPass(); //1
					currPass=0;
				}
				state.getShips().get(k).setPassLeft(currPass);
				//System.out.println("pickup was successful");
				for(int i=0;i<state.getShips().size();i++) {
					//System.out.println(state.getShips().get(i).getPassLeft());
					state.getShips().get(i).expire();
					state.getShips().get(i).damage();
				}
				return new State(state.getCgI(), state.getCgJ(),passToTake,state.getRescued(),state.getRetrieved(),state.getShips(), state.getActions(),"pickup");
			}
//			state.getShips().get(k).expire();
//			state.getShips().get(k).damage();
		}
		//System.out.println("pick up can't happen");
		return new State(state.getCgI(), state.getCgJ(),state.getCgPass(),state.getRescued(),state.getRetrieved(),state.getShips(), state.getActions(),"null");

	}
	
	public static String GenGrid() {
		//grid size
		int m= (int)(Math.random() * 15-5+1) + 5;
		int n= (int)(Math.random() * 15-5+1) + 5;
		System.out.println("m="+m+" n="+n);
		//nb of items
		int cellsNb= m*n;
		//random nb between 1 and nb of cells-2 (at least one cell for stations and one for agent)
		int ships= (int)Math.round((Math.random() *(cellsNb-2)-1+1) + 1);
		//remaining cells with no ships, -1 for agent
		int stations= (int)(Math.random() *(cellsNb-ships-1)-1+1) + 1;
		System.out.println("ships=" +ships+ " stations="+stations);

		//initialize grid
		gridGlobal= new Grid(m,n,ships,stations);
		//initialize coastGuard with its location in an empty grid cell;
		int [] coord = gridGlobal.emptyCell();
		CoastGuard cg= new CoastGuard(coord[0],coord[1],gridGlobal);
		System.out.println("cgx="+coord[0]+" cgy="+coord[1]+ " cg cap="+ cg.getCapacity());
		String output="";
		output= output +m+","+n+";"+cg.getCapacity()+";"+cg.getI()+","+cg.getJ()+";";
		for(int i=0; i<gridGlobal.stationList.size();i++)
			output+= gridGlobal.stationList.get(i).i+","+gridGlobal.stationList.get(i).j+",";
		output = output.substring(0, output.length() - 1);	
		output+= ";";
		for(int i=0; i<gridGlobal.shipList.size();i++)
			output+= gridGlobal.shipList.get(i).getI()+","+gridGlobal.shipList.get(i).getJ()+","+gridGlobal.shipList.get(i).getNbPass()+",";
		output = output.substring(0, output.length() - 1);	
		output+= ";";
		return output;
	}
	
	public static String solve(String grid, String strategy, boolean visualize) throws CloneNotSupportedException {
		StringTokenizer str = new StringTokenizer(grid, ";");
		StringTokenizer gridSize = new StringTokenizer(str.nextToken(), ",");
		int n = Integer.parseInt(gridSize.nextToken());
		int m = Integer.parseInt(gridSize.nextToken());
		int c = Integer.parseInt(str.nextToken());
		StringTokenizer cgPosition = new StringTokenizer(str.nextToken(), ",");
		int cgX = Integer.parseInt(cgPosition.nextToken());
		int cgY = Integer.parseInt(cgPosition.nextToken());
		StringTokenizer stationCoordinates = new StringTokenizer(str.nextToken(), ",");
		ArrayList<Station> stationList = new ArrayList<Station>();
		int i=0;
		int j=0;
		while(stationCoordinates.hasMoreTokens()) {
			i= Integer.parseInt(stationCoordinates.nextToken());
			j= Integer.parseInt(stationCoordinates.nextToken());
			Station station = new Station(i,j);
			stationList.add(station);
		}
		StringTokenizer shipCoordinates = new StringTokenizer(str.nextToken(), ",");
		ArrayList<Ship> shipList = new ArrayList<Ship>();
		int nbPass =0;
		while(shipCoordinates.hasMoreTokens()) {
			i= Integer.parseInt(shipCoordinates.nextToken());
			j= Integer.parseInt(shipCoordinates.nextToken());
			nbPass = Integer.parseInt(shipCoordinates.nextToken());
			Ship ship = new Ship(i,j,nbPass);
			shipList.add(ship);
		}
		gridGlobal= new Grid(m,n,shipList,stationList);
		CoastGuard cg = new CoastGuard(cgX,cgY,c,gridGlobal);
		
		State root = new State(cgX,cgY,0,0,0,shipList, new ArrayList<String>(),"root");
		String[] res = new String[4];
		if(strategy.equals("BF")){
			res = AiProj1.Bfs(root, cg);
			
		}
		if(strategy.equals("DF")){
			res = AiProj1.Dfs(root, cg);
			
		}
		if(strategy.equals("ID")){
			res = AiProj1.IterativeDeepening(root, cg);
			
		}
		if(strategy.equals("GR1")){
			res = AiProj1.Greedy1(root, cg);
			
		}
		if(strategy.equals("GR2")){
			res = AiProj1.Greedy2(root, cg);
			
		}
		if(strategy.equals("AS1")){
			res = AiProj1.AStar1(root, cg);
			
		}
		if(strategy.equals("AS2")){
			res = AiProj1.AStar2(root, cg);
			
		}
		
		res[0]=res[0].substring(5,res[0].length()-1);
		
		String result ="";
		for(int f=0;f<res.length;f++) {
			result+=res[f]+";";
//			if(i!=3) {
//				result+=";";
//			}
		}
		
		if(visualize) {
			System.out.println(gridGlobal.printCells(cg.i, cg.j));
		}

		return result;
	}
	
}
