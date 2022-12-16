package code;
import java.lang.Math;
import java.util.ArrayList;

public class Grid {
	
	int m;
	int n; 
	int ships;
	int stations;
	int survivors; 
	int deaths;
	int blackBox;
	Cell[][] cells;
	ArrayList<Ship> shipList;
	ArrayList <Station>stationList;
	
	public Grid(int m, int n, int ships, int stations) {
		this.m=m;
		this.n=n;
		this.ships=ships;
		this.stations=stations;
		survivors=0;
		deaths=0;
		blackBox=0;	
		cells= new Cell[m][n];	
		shipList = new ArrayList<Ship>();
		stationList= new ArrayList<Station>();
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++)				
				cells[i][j]=new Cell(i,j);
		}
		
		int x;
		int y;
		int rand;
		int nbShips=ships;
		int nbStations=stations;
		while(nbShips>0 || nbStations>0) {
			x=(int)(Math.random() * m);
			y=(int)(Math.random() * n);
//			rand=1 --> ship , rand=0 --> station
			rand=(int) Math.round((Math.random()));
			
			if(!cells[x][y].isOccupied()) {
				if(rand==1 && nbShips>0) {
					cells[x][y].setShip(true);
					nbShips--;
					Ship ship=new Ship(x,y);
					shipList.add(ship);
					System.out.println("shipx="+x+" shipy="+y+" shipcap="+ship.nbPass);
					
					}
				else if(rand==0 && nbStations>0) {
					cells[x][y].setStation(true);	
					nbStations--;
					Station station = new Station(x,y);
					stationList.add(station);
					}
				
			}	
		}
	}
	
	public Grid(int m, int n, ArrayList<Ship> ships, ArrayList<Station> stations) {
		this.m=n;
		this.n=m;
		this.ships=ships.size();
		this.stations=stations.size();
		survivors=0;
		deaths=0;
		blackBox=0;	
		cells= new Cell[m][n];	
		shipList = ships;
		stationList= stations;
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++)				
				cells[i][j]=new Cell(i,j);
		}
		
		int x;
		int y;
		
		
		for(int i=0;i<ships.size();i++) {
			
			x=ships.get(i).getI();
			y=ships.get(i).getJ();
			cells[x][y].setShip(true);
			System.out.println("shipx="+x+" shipy="+y+" shipcap="+ships.get(i).nbPass);
			
		}
		for(int i=0;i<stations.size();i++) {
			
			x=stations.get(i).getI();
			y=stations.get(i).getJ();
			cells[x][y].setStation(true);
			System.out.println("stationx="+x+" stationy="+y);
			
		}
		
			
//			
			
			
			
				
			}	
		

	
	public ArrayList<Ship> getShipList() {
		return shipList;
	}

	public ArrayList<Station> getStationList() {
		return stationList;
	}

	public int getM() {
		return m;
	}

	public int getN() {
		return n;
	}

	public int[] emptyCell() {
		int [] coord= new int[2];
		
		coord[0]=(int)(Math.random() * m) ;
		coord[1]=(int)(Math.random() * n) ;
		
		while(cells[coord[0]][coord[1]].isOccupied()) {
			
			coord[0]=(int)(Math.random() * m) ;
			coord[1]=(int)(Math.random() * n) ;
			
		}
		
		
		
//		for(int i=0;i<m;i++){
//			for(int j=0;j<n;j++)				
//				if (!cells[i][j].isOccupied()) {
//					
//					coord[0]=i;
//					coord[1]=j;
//					return coord;
//				}
//		}
		
		return coord;
		
	}
	
	
	
	public String printCells(int cgi, int cgj) {
		String res="[";
		
		for(int i=0;i<cells.length;i++) {
			for(int j=0; j<cells[i].length;j++) {
				if(i==cgi && j==cgj) {
					
					res+="[";
					res+=cgi;
					res+=",";
					res+=cgj;
					res+=",agent";
					res+="]";
				}
				else {
					res+=cells[i][j].toString();
				}
				
			}
			if(!(i==cells.length-1))
			{res+="\n";}
			
			
		}
		
		res+="]";
		return res;
		
	}
	
	
	}


