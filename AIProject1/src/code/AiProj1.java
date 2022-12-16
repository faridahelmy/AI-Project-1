package code;

import java.lang.Math;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class AiProj1 implements Comparator<State> {
	static Grid grid;
	public static boolean awelheuristic = true;
	public static boolean AS = false;
	static CoastGuard cgGlobal;
	
	static boolean canMoveUp=true;
	static boolean canMoveDown=true;
	static boolean canMoveRight=true;
	static boolean canMoveLeft=true;

	
	public static boolean stateExists(Queue<State> bfsQ, State state) throws CloneNotSupportedException {
		
		boolean exists=false;
		
		for (State elem: bfsQ) {
            
			if(sameState(elem,state)) {
				exists=true;
			}
        }
		
		return exists;
		
		
		
	}

	public static boolean stateExists(Stack<State> bfsQ, State state) throws CloneNotSupportedException {
		
		boolean exists=false;
		
		for (State elem: bfsQ) {
            
			if(sameState(elem,state)) {
				exists=true;
			}
        }
		
		return exists;
		
		
		
	}
	
	public static boolean stateExists(PriorityQueue<State> bfsQ, State state) throws CloneNotSupportedException {
		
		boolean exists=false;
		
		for (State elem: bfsQ) {
            
			if(sameState(elem,state)) {
				exists=true;
			}
        }
		
		return exists;
		
		
		
	}
	
	public static boolean sameState(State state1, State state2) throws CloneNotSupportedException {
		
		if(state1.cgI==state2.cgI && state1.cgJ==state2.cgJ && state1.cgPass==state2.cgPass && state1.rescued==state2.rescued && state1.retrieved==state2.retrieved && sameShips(state1.getShips(),state2.getShips()))
		{
			return true;
		}
		
		return false;
		
	}
	
	public static boolean sameShips(ArrayList<Ship> s1, ArrayList<Ship> s2) {
		
		boolean noDifference=true;
		for(int i=0; i<s1.size();i++) {
			if(s1.get(i).nbPass!=s2.get(i).nbPass || s1.get(i).damage!=s2.get(i).damage || s1.get(i).isBlackBox()!=s2.get(i).isBlackBox() || s1.get(i).passLeft!=s2.get(i).passLeft || s1.get(i).getI()!=s2.get(i).getI() || s1.get(i).isWreck()!=s2.get(i).isWreck()) {
				noDifference=false;
			}
		}
		
		return noDifference;
		
		
	}

	public static String[] Bfs(State root, CoastGuard cg) throws CloneNotSupportedException {
		cgGlobal=cg;
		Queue<State> bfsQ = new LinkedList<State>();
		bfsQ.add(root);
		String expansionSeq="";
		int death=0;
		int retrieval=0;
		int nodes =0;
		int rescued=0;

		while (!bfsQ.isEmpty())
		{
			//System.out.println("before removing = "+bfsQ.size());
			State currState = bfsQ.poll();

			//System.out.println("after removing = "+bfsQ.size());
			//System.out.println("this is the current state "+ currState);
			nodes++;
			boolean keepSearching= false;
			for(int i=0; i<currState.getShips().size();i++) {
				if(currState.getShips().get(i).passLeft!=0 || currState.getShips().get(i).isBlackBox() || currState.getCgPass()!=0)
				{keepSearching=true;
				break;}
				else {
					keepSearching=false;
				}
			}
//						if(!keepSearching) {
//							for(int i=0; i< currState.getActions().size();i++)
//								expansionSeq+= currState.getActions().get(i) + " ";
//				//			System.out.println("else");
//							break;
//						}
			if(keepSearching) {
			//	System.out.println("Searching");
				
				State pickState= (State)(cg.PickUp((State)currState.clone())).clone();
				//System.out.println("this is the pick state"+pickState);
				if(pickState.getActions().get(pickState.getActions().size()-1)!="null" && !stateExists(bfsQ,pickState) )
				{
					bfsQ.add(pickState);
//					System.out.println("pick state has entered the queue");
//					System.out.println("this is the pick state"+pickState);

				}

				State dropState=(State) (cg.Drop((State)currState.clone())).clone();
				//System.out.println("this is the drop state "+dropState);
				if(dropState.getActions().get(dropState.getActions().size()-1)!="null" && !stateExists(bfsQ,dropState))
				{
					bfsQ.add(dropState);
//					System.out.println("drop state has entered the queue");
//					System.out.println("this is the drop state "+dropState);
				}


				State retrieveState=(State)(cg.Retrieve((State)currState.clone())).clone();
				//System.out.println("this is the retrieve state "+retrieveState);
				if(retrieveState.getActions().get(retrieveState.getActions().size()-1)!="null" && !stateExists(bfsQ,retrieveState))
				{
					bfsQ.add(retrieveState);
//					System.out.println("retrieved state has entered the queue");
//					System.out.println("this is the retrieve state "+retrieveState);
				}



				State upState= (State)(cg.MoveUp((State)currState.clone())).clone();
				//System.out.println("this is the upstate "+upState);
				if(upState.getActions().get(upState.getActions().size()-1)!="null" && illegalPolice(currState, "up") && !stateExists(bfsQ,upState)) {
					bfsQ.add(upState);
//					System.out.println("added upstate to queue");
//					System.out.println("this is the upstate "+upState);

				}

				State downState=(State) (cg.MoveDown((State)currState.clone())).clone();
				//System.out.println("this is the downstate "+downState);
				if(downState.getActions().get(downState.getActions().size()-1)!="null" && illegalPolice(currState,"down") && !stateExists(bfsQ,downState)) {
					bfsQ.add(downState);
//					System.out.println("added downstate to queue");
//					System.out.println("this is the downstate "+downState);
				}


				State rightState=(State) (cg.MoveRight((State)currState.clone())).clone();
				//System.out.println("this is the right state "+rightState);
				if(rightState.getActions().get(rightState.getActions().size()-1)!="null" && illegalPolice(currState, "right") && !stateExists(bfsQ,rightState))
				{
					bfsQ.add(rightState);
//					System.out.println("added right state to queue");
//					System.out.println("this is the right state "+rightState);
				}

				State leftState= (State) (cg.MoveLeft((State)currState.clone())).clone();
				//System.out.println("this is the left state "+leftState);
				if(leftState.getActions().get(leftState.getActions().size()-1)!="null" && illegalPolice(currState, "left") && !stateExists(bfsQ,leftState)) {
					bfsQ.add(leftState);
//					System.out.println("added left state to queue");
//					System.out.println("this is the left state "+leftState);
				}


			}

			else {
				
				for(int i=0; i< currState.getActions().size();i++)
					expansionSeq+= currState.getActions().get(i) + ",";
				death=currState.getTotalPassengers()-currState.getRescued();
				rescued= currState.getRescued();
				retrieval=currState.getRetrieved();
				System.out.println(currState);
				break;
				
			}

			
		}
		String nodesStr = ""+ nodes;
		String[] res = new String[4];
		res[0]=expansionSeq;
		res[1]=death+"";
		res[2]=retrieval+"";
		res[3]=nodesStr;
		System.out.println("\n rescued are: " +rescued);
		return res;

	}

	public static String[] Dfs(State root, CoastGuard cg) throws CloneNotSupportedException {
		cgGlobal=cg;
		Stack<State> dfsS = new Stack<State>();
		dfsS.push(root);
		String expansionSeq="";
		int death=0;
		int retrieval=0;
		int nodes =0;
		int rescued=0;

		while (!dfsS.isEmpty())
		{
			//System.out.println("before removing = "+dfsS.size());
			State currState = dfsS.pop();

			//System.out.println("after removing = "+dfsS.size());
			//System.out.println("this is the current state "+ currState);
			nodes++;
			boolean keepSearching= false;
			for(int i=0; i<currState.getShips().size();i++) {
				if(currState.getShips().get(i).passLeft!=0 || currState.getShips().get(i).isBlackBox() || currState.getCgPass()!=0)
				{keepSearching=true;
				break;}
				else {
					keepSearching=false;
				}
			}

			if(keepSearching) {
				//System.out.println("Searching");

				State pickState= (State)(cg.PickUp((State)currState.clone())).clone();
				//System.out.println("this is the pick state"+pickState);
				if(pickState.getActions().get(pickState.getActions().size()-1)!="null" && !stateExists(dfsS,pickState))
				{
					dfsS.push(pickState);
					//System.out.println("pick state has entered the queue");

				}

				State dropState=(State) (cg.Drop((State)currState.clone())).clone();
				//System.out.println("this is the drop state "+dropState);
				if(dropState.getActions().get(dropState.getActions().size()-1)!="null" && !stateExists(dfsS,dropState))
				{
					dfsS.push(dropState);
					//System.out.println("drop state has entered the queue");
				}


				State retrieveState=(State)(cg.Retrieve((State)currState.clone())).clone();
				//System.out.println("this is the retrieve state "+retrieveState);
				if(retrieveState.getActions().get(retrieveState.getActions().size()-1)!="null" && !stateExists(dfsS,retrieveState))
				{
					dfsS.push(retrieveState);
					//System.out.println("retrieved state has entered the queue");
				}



				State upState= (State)(cg.MoveUp((State)currState.clone())).clone();
				//System.out.println("this is the upstate "+upState);
				if(upState.getActions().get(upState.getActions().size()-1)!="null" && illegalPolice(currState,"up") && !stateExists(dfsS,upState)) {
					dfsS.push(upState);
					//System.out.println("added upstate to queue");

				}

				State downState=(State) (cg.MoveDown((State)currState.clone())).clone();
				//System.out.println("this is the downstate "+downState);
				if(downState.getActions().get(downState.getActions().size()-1)!="null" && illegalPolice(currState,"down") && !stateExists(dfsS,downState)) {
					dfsS.push(downState);
					//System.out.println("added downstate to queue");
				}


				State rightState=(State) (cg.MoveRight((State)currState.clone())).clone();
				//System.out.println("this is the right state "+rightState);
				if(rightState.getActions().get(rightState.getActions().size()-1)!="null" && illegalPolice(currState,"right") && !stateExists(dfsS,rightState))
				{
					dfsS.push(rightState);
					//System.out.println("added right state to queue");
				}

				State leftState= (State) (cg.MoveLeft((State)currState.clone())).clone();
				//System.out.println("this is the left state "+leftState);
				if(leftState.getActions().get(leftState.getActions().size()-1)!="null" && illegalPolice(currState,"left") && !stateExists(dfsS,leftState)) {
					dfsS.push(leftState);
					//System.out.println("added left state to queue");
				}


			}

			else {
				for(int i=0; i< currState.getActions().size();i++)
					expansionSeq+= currState.getActions().get(i) + ",";
				
				death=currState.getTotalPassengers()-currState.getRescued();
				rescued= currState.getRescued();
				retrieval=currState.getRetrieved();
				//System.out.println("else");
				break;
			}


		}
		String nodesStr = ""+ nodes;
		String[] res = new String[4];
		res[0]=expansionSeq;
		res[1]=death+"";
		res[2]=retrieval+"";
		res[3]=nodesStr;
		//System.out.println("\n rescued are: " +rescued);
		return res;

	}


	public static String[] IterativeDeepening(State root, CoastGuard cg) throws CloneNotSupportedException {
		cgGlobal=cg;
		String expansionSeq="";
		int death=0;
		int retrieval=0;
		int nodes =0;
		int rescued=0;
		int max=0;
		boolean keepSearching= true;
		State currState= root;

		while(keepSearching) {

			for(int i=0; i<currState.getShips().size();i++) {
				if(currState.getShips().get(i).passLeft!=0 || currState.getShips().get(i).isBlackBox() || currState.getCgPass()!=0)
				{



//					System.out.println("keepsearching is true");
//					System.out.println("max depth is =" + max);
//					System.out.println(currState);
//					System.out.println("************");
					keepSearching=true;
				}
				else {

//					System.out.println("keepsearching is false");
//					System.out.println("max depth is =" + max);
//					System.out.println(currState);
//					System.out.println("************");
					keepSearching=false;
					break;
				}
			}

			currState=IterativeDeepening(root,cg,max);
			max=max+1;
			//System.out.println("********** MAX IS " + max + " ************");
		}

		for(int i=0; i< currState.getActions().size();i++)
			expansionSeq+= currState.getActions().get(i) + ",";

		death=currState.getTotalPassengers()-currState.getRescued();
		rescued= currState.getRescued();
		retrieval=currState.getRetrieved();

		//System.out.println("else");
		String nodesStr = ""+ nodes;
		String[] res = new String[4];
		res[0]=expansionSeq;
		res[1]=death+"";
		res[2]=retrieval+"";
		res[3]=nodesStr;
		//System.out.println("\n rescued are: " +rescued);
		return res;

	}


	public static State IterativeDeepening(State root, CoastGuard cg, int maxDepth) throws CloneNotSupportedException {
		cgGlobal=cg;
		Stack<State> dfsS = new Stack<State>();
		int currDepth=0;
		dfsS.push(root);
		//		String expansionSeq="";
		//		int death=0;
		//		int retrieval=0;
		//		int nodes =0;
		//		int rescued=0;

		//	while(currDepth<maxDepth) {
		while (!dfsS.isEmpty())
		{
			//System.out.println("before removing = "+dfsS.size());
			State currState = dfsS.pop();
			currDepth= currState.getActions().size()-1;

//			System.out.println("currDepth is = "+currDepth);
//			System.out.println("maxDepth is = "+maxDepth);
//			System.out.println("this is the current state "+ currState);
			//			nodes++;
			boolean keepSearching= false;
			for(int i=0; i<currState.getShips().size();i++) {
				if(currState.getShips().get(i).passLeft!=0 || currState.getShips().get(i).isBlackBox() || currState.getCgPass()!=0)
				{
					keepSearching=true;
					break;
				}
				else {
					keepSearching=false;
				}
			}
			if(currDepth>=maxDepth) {
				break;
			}
			if(keepSearching) {
				//System.out.println("Searching");



				State upState= (State)(cg.MoveUp((State)currState.clone())).clone();
				//System.out.println("this is the upstate "+upState);
				if(upState.getActions().get(upState.getActions().size()-1)!="null" && upState.getActions().get(upState.getActions().size()-2)!="down" && !stateExists(dfsS,upState)) {
					dfsS.push(upState);
					//System.out.println("added upstate to queue");

				}

				State leftState= (State) (cg.MoveLeft((State)currState.clone())).clone();
				//System.out.println("this is the left state "+leftState);
				if(leftState.getActions().get(leftState.getActions().size()-1)!="null" && leftState.getActions().get(leftState.getActions().size()-2)!="right" && !stateExists(dfsS,leftState)) {
					dfsS.push(leftState);
					//System.out.println("added left state to queue");
				}

				State downState=(State) (cg.MoveDown((State)currState.clone())).clone();
				//System.out.println("this is the downstate "+downState);
				if(downState.getActions().get(downState.getActions().size()-1)!="null" && downState.getActions().get(downState.getActions().size()-2)!="up" && !stateExists(dfsS,downState)) {
					dfsS.push(downState);
					//System.out.println("added downstate to queue");
				}


				State rightState=(State) (cg.MoveRight((State)currState.clone())).clone();
				//System.out.println("this is the right state "+rightState);
				if(rightState.getActions().get(rightState.getActions().size()-1)!="null" && rightState.getActions().get(rightState.getActions().size()-2)!="left" && !stateExists(dfsS,rightState))
				{
					dfsS.push(rightState);
					//System.out.println("added right state to queue");
				}


				State pickState= (State)(cg.PickUp((State)currState.clone())).clone();
				//System.out.println("this is the pick state"+pickState);
				if(pickState.getActions().get(pickState.getActions().size()-1)!="null" && !stateExists(dfsS,pickState))
				{
					dfsS.push(pickState);
					//System.out.println("pick state has entered the queue");

				}

				State dropState=(State) (cg.Drop((State)currState.clone())).clone();
				//System.out.println("this is the drop state "+dropState);
				if(dropState.getActions().get(dropState.getActions().size()-1)!="null" && !stateExists(dfsS,dropState))
				{
					dfsS.push(dropState);
					//System.out.println("drop state has entered the queue");
				}


				State retrieveState=(State)(cg.Retrieve((State)currState.clone())).clone();
				//System.out.println("this is the retrieve state "+retrieveState);
				if(retrieveState.getActions().get(retrieveState.getActions().size()-1)!="null" && !stateExists(dfsS,retrieveState))
				{
					dfsS.push(retrieveState);
					//System.out.println("retrieved state has entered the queue");
				}




			}

			else {

				return currState;
			}
		}
		//	}
		return root;

	}


	public static String[] Greedy1(State root, CoastGuard cg) throws CloneNotSupportedException {
		cgGlobal=cg;
		AS=false;
		awelheuristic=true;
		
		PriorityQueue<State> pq = new PriorityQueue<State>(10,StateComparator());
		pq.add(root);
		String expansionSeq="";
		int death=0;
		int retrieval=0;
		int nodes =0;
		int rescued=0;
		//int trial=0;
		while (!pq.isEmpty())
		{
			//trial++;
			//System.out.println("before removing = "+pq.size());
			State currState = pq.poll();
			//System.out.println(pq);
//			System.out.println("after removing = "+pq.size());
//			System.out.println("this is the current state "+ currState);
			nodes++;
			boolean keepSearching= false;
			for(int i=0; i<currState.getShips().size();i++) {
				if(currState.getShips().get(i).passLeft!=0 || currState.getShips().get(i).isBlackBox() || currState.getCgPass()!=0)
				{keepSearching=true;
				break;}
				else {
					keepSearching=false;
				}
			}
			//			if(!keepSearching) {
			//				for(int i=0; i< currState.getActions().size();i++)
			//					expansionSeq+= currState.getActions().get(i) + " ";
			//				System.out.println("else");
			//				break;
			//			}
			if(keepSearching) {
				//System.out.println("Searching");

				State pickState= (State)(cg.PickUp((State)currState.clone())).clone();
				//System.out.println("this is the pick state"+pickState);
				if(pickState.getActions().get(pickState.getActions().size()-1)!="null" && !stateExists(pq,pickState))
				{
					pq.add(pickState);
					//System.out.println("pick state has entered the queue");

				}

				State dropState=(State) (cg.Drop((State)currState.clone())).clone();
				//System.out.println("this is the drop state "+dropState);
				if(dropState.getActions().get(dropState.getActions().size()-1)!="null" && !stateExists(pq,dropState))
				{
					pq.add(dropState);
					//System.out.println("drop state has entered the queue");
				}


				State retrieveState=(State)(cg.Retrieve((State)currState.clone())).clone();
				//System.out.println("this is the retrieve state "+retrieveState);
				if(retrieveState.getActions().get(retrieveState.getActions().size()-1)!="null" && !stateExists(pq,retrieveState))
				{
					pq.add(retrieveState);
					//System.out.println("retrieved state has entered the queue");
				}



				State upState= (State)(cg.MoveUp((State)currState.clone())).clone();
				//System.out.println("this is the upstate "+upState);
				if(upState.getActions().get(upState.getActions().size()-1)!="null" && upState.getActions().get(upState.getActions().size()-2)!="down" && !stateExists(pq,upState)) {
					pq.add(upState);
					//System.out.println("added upstate to queue");

				}

				State downState=(State) (cg.MoveDown((State)currState.clone())).clone();
				//System.out.println("this is the downstate "+downState);
				if(downState.getActions().get(downState.getActions().size()-1)!="null" && downState.getActions().get(downState.getActions().size()-2)!="up" && !stateExists(pq,downState)) {
					pq.add(downState);
					//System.out.println("added downstate to queue");
				}


				State rightState=(State) (cg.MoveRight((State)currState.clone())).clone();
				//System.out.println("this is the right state "+rightState);
				if(rightState.getActions().get(rightState.getActions().size()-1)!="null" && rightState.getActions().get(rightState.getActions().size()-2)!="left" && !stateExists(pq,rightState))
				{
					pq.add(rightState);
					//System.out.println("added right state to queue");
				}

				State leftState= (State) (cg.MoveLeft((State)currState.clone())).clone();
				//System.out.println("this is the left state "+leftState);
				if(leftState.getActions().get(leftState.getActions().size()-1)!="null" && leftState.getActions().get(leftState.getActions().size()-2)!="right" && !stateExists(pq,leftState)) {
					pq.add(leftState);
					//System.out.println("added left state to queue");
				}


			}

			else {
				for(int i=0; i< currState.getActions().size();i++)
					expansionSeq+= currState.getActions().get(i) + ",";

				death=currState.getTotalPassengers()-currState.getRescued();
				rescued= currState.getRescued();
				retrieval=currState.getRetrieved();
				//System.out.println("else");
				break;
			}


		}
		String nodesStr = ""+ nodes;
		String[] res = new String[4];
		res[0]=expansionSeq;
		res[1]=death+"";
		res[2]=retrieval+"";
		res[3]=nodesStr;
		//System.out.println("\n rescued are: " +rescued);
		return res;
	}
	private static Comparator<State> StateComparator() {
		return new AiProj1();
	}

	public static String[] Greedy2(State root, CoastGuard cg) throws CloneNotSupportedException {
		cgGlobal=cg;
		AS=false;
		awelheuristic=false;
		
		PriorityQueue<State> pq = new PriorityQueue<State>(10,StateComparator());
		pq.add(root);
		String expansionSeq="";
		int death=0;
		int retrieval=0;
		int nodes =0;
		int rescued=0;

		while (!pq.isEmpty())
		{
			//System.out.println("before removing = "+pq.size());
			State currState = pq.poll();

//			System.out.println("after removing = "+pq.size());
//			System.out.println("this is the current state "+ currState);
			nodes++;
			boolean keepSearching= false;
			for(int i=0; i<currState.getShips().size();i++) {
				if(currState.getShips().get(i).passLeft!=0 || currState.getShips().get(i).isBlackBox() || currState.getCgPass()!=0)
				{keepSearching=true;
				break;}
				else {
					keepSearching=false;
				}
			}
			//			if(!keepSearching) {
			//				for(int i=0; i< currState.getActions().size();i++)
			//					expansionSeq+= currState.getActions().get(i) + " ";
			//				System.out.println("else");
			//				break;
			//			}
			if(keepSearching) {
				//System.out.println("Searching");

				State pickState= (State)(cg.PickUp((State)currState.clone())).clone();
				//System.out.println("this is the pick state"+pickState);
				if(pickState.getActions().get(pickState.getActions().size()-1)!="null" && !stateExists(pq,pickState))
				{
					pq.add(pickState);
					//System.out.println("pick state has entered the queue");

				}

				State dropState=(State) (cg.Drop((State)currState.clone())).clone();
				//System.out.println("this is the drop state "+dropState);
				if(dropState.getActions().get(dropState.getActions().size()-1)!="null" && !stateExists(pq,dropState))
				{
					pq.add(dropState);
					//System.out.println("drop state has entered the queue");
				}


				State retrieveState=(State)(cg.Retrieve((State)currState.clone())).clone();
				//System.out.println("this is the retrieve state "+retrieveState);
				if(retrieveState.getActions().get(retrieveState.getActions().size()-1)!="null" && !stateExists(pq,retrieveState))
				{
					pq.add(retrieveState);
					//System.out.println("retrieved state has entered the queue");
				}



				State upState= (State)(cg.MoveUp((State)currState.clone())).clone();
				//System.out.println("this is the upstate "+upState);
				if(upState.getActions().get(upState.getActions().size()-1)!="null" && upState.getActions().get(upState.getActions().size()-2)!="down" && !stateExists(pq,upState)) {
					pq.add(upState);
					//System.out.println("added upstate to queue");

				}

				State downState=(State) (cg.MoveDown((State)currState.clone())).clone();
				//System.out.println("this is the downstate "+downState);
				if(downState.getActions().get(downState.getActions().size()-1)!="null" && downState.getActions().get(downState.getActions().size()-2)!="up" && !stateExists(pq,downState)) {
					pq.add(downState);
					//System.out.println("added downstate to queue");
				}


				State rightState=(State) (cg.MoveRight((State)currState.clone())).clone();
				//System.out.println("this is the right state "+rightState);
				if(rightState.getActions().get(rightState.getActions().size()-1)!="null" && rightState.getActions().get(rightState.getActions().size()-2)!="left" && !stateExists(pq,rightState))
				{
					pq.add(rightState);
//					System.out.println("added right state to queue");
				}

				State leftState= (State) (cg.MoveLeft((State)currState.clone())).clone();
				//System.out.println("this is the left state "+leftState);
				if(leftState.getActions().get(leftState.getActions().size()-1)!="null" && leftState.getActions().get(leftState.getActions().size()-2)!="right" && !stateExists(pq,leftState)) {
					pq.add(leftState);
					//System.out.println("added left state to queue");
				}


			}

			else {
				for(int i=0; i< currState.getActions().size();i++)
					expansionSeq+= currState.getActions().get(i) + ",";

				death=currState.getTotalPassengers()-currState.getRescued();
				rescued= currState.getRescued();
				retrieval=currState.getRetrieved();
				//System.out.println("else");
				break;
			}


		}
		String nodesStr = ""+ nodes;
		String[] res = new String[4];
		res[0]=expansionSeq;
		res[1]=death+"";
		res[2]=retrieval+"";
		res[3]=nodesStr;
		//System.out.println("\n rescued are: " +rescued);
		return res;
	}
	public static String[] AStar1(State root, CoastGuard cg) throws CloneNotSupportedException {
		cgGlobal=cg;
		AS=true;
		awelheuristic=true;
		PriorityQueue<State> pq = new PriorityQueue<State>(10,StateComparator());
		pq.add(root);
		String expansionSeq="";
		int death=0;
		int retrieval=0;
		int nodes =0;
		int rescued=0;

		while (!pq.isEmpty())
		{
			//System.out.println("before removing = "+pq.size());
			State currState = pq.poll();

			//System.out.println("after removing = "+pq.size());
			//System.out.println("this is the current state "+ currState);
			nodes++;
			boolean keepSearching= false;
			for(int i=0; i<currState.getShips().size();i++) {
				if(currState.getShips().get(i).passLeft!=0 || currState.getShips().get(i).isBlackBox() || currState.getCgPass()!=0)
				{keepSearching=true;
				break;}
				else {
					keepSearching=false;
				}
			}
			//			if(!keepSearching) {
			//				for(int i=0; i< currState.getActions().size();i++)
			//					expansionSeq+= currState.getActions().get(i) + " ";
			//				System.out.println("else");
			//				break;
			//			}
			if(keepSearching) {
				//System.out.println("Searching");

				State pickState= (State)(cg.PickUp((State)currState.clone())).clone();
				//System.out.println("this is the pick state"+pickState);
				if(pickState.getActions().get(pickState.getActions().size()-1)!="null" && !stateExists(pq,pickState))
				{
					pq.add(pickState);
					//System.out.println("pick state has entered the queue");

				}

				State dropState=(State) (cg.Drop((State)currState.clone())).clone();
				//System.out.println("this is the drop state "+dropState);
				if(dropState.getActions().get(dropState.getActions().size()-1)!="null" && !stateExists(pq,dropState))
				{
					pq.add(dropState);
					//System.out.println("drop state has entered the queue");
				}


				State retrieveState=(State)(cg.Retrieve((State)currState.clone())).clone();
				//System.out.println("this is the retrieve state "+retrieveState);
				if(retrieveState.getActions().get(retrieveState.getActions().size()-1)!="null" && !stateExists(pq,retrieveState))
				{
					pq.add(retrieveState);
					//System.out.println("retrieved state has entered the queue");
				}



				State upState= (State)(cg.MoveUp((State)currState.clone())).clone();
				//System.out.println("this is the upstate "+upState);
				if(upState.getActions().get(upState.getActions().size()-1)!="null" && upState.getActions().get(upState.getActions().size()-2)!="down" && !stateExists(pq,upState)) {
					pq.add(upState);
					//System.out.println("added upstate to queue");

				}

				State downState=(State) (cg.MoveDown((State)currState.clone())).clone();
				//System.out.println("this is the downstate "+downState);
				if(downState.getActions().get(downState.getActions().size()-1)!="null" && downState.getActions().get(downState.getActions().size()-2)!="up" && !stateExists(pq,downState)) {
					pq.add(downState);
					//System.out.println("added downstate to queue");
				}


				State rightState=(State) (cg.MoveRight((State)currState.clone())).clone();
				//System.out.println("this is the right state "+rightState);
				if(rightState.getActions().get(rightState.getActions().size()-1)!="null" && rightState.getActions().get(rightState.getActions().size()-2)!="left" && !stateExists(pq,rightState))
				{
					pq.add(rightState);
				  //System.out.println("added right state to queue");
				}

				State leftState= (State) (cg.MoveLeft((State)currState.clone())).clone();
				//System.out.println("this is the left state "+leftState);
				if(leftState.getActions().get(leftState.getActions().size()-1)!="null" && leftState.getActions().get(leftState.getActions().size()-2)!="right" && !stateExists(pq,leftState)) {
					pq.add(leftState);
					//System.out.println("added left state to queue");
				}


			}

			else {
				for(int i=0; i< currState.getActions().size();i++)
					expansionSeq+= currState.getActions().get(i) + ",";

				death=currState.getTotalPassengers()-currState.getRescued();
				rescued= currState.getRescued();
				retrieval=currState.getRetrieved();
				//System.out.println("else");
				break;
			}


		}
		String nodesStr = ""+ nodes;
		String[] res = new String[4];
		res[0]=expansionSeq;
		res[1]=death+"";
		res[2]=retrieval+"";
		res[3]=nodesStr;
		//System.out.println("\n rescued are: " +rescued);
		return res;
		}
	public static String[] AStar2(State root, CoastGuard cg) throws CloneNotSupportedException {
		cgGlobal=cg;
		awelheuristic=false;
		AS=true;
		PriorityQueue<State> pq = new PriorityQueue<State>(10,StateComparator());
		pq.add(root);
		String expansionSeq="";
		int death=0;
		int retrieval=0;
		int nodes =0;
		int rescued=0;

		while (!pq.isEmpty())
		{
			//System.out.println("before removing = "+pq.size());
			State currState = pq.poll();

//			System.out.println("after removing = "+pq.size());
//			System.out.println("this is the current state "+ currState);
			nodes++;
			boolean keepSearching= false;
			for(int i=0; i<currState.getShips().size();i++) {
				if(currState.getShips().get(i).passLeft!=0 || currState.getShips().get(i).isBlackBox() || currState.getCgPass()!=0)
				{keepSearching=true;
				break;}
				else {
					keepSearching=false;
				}
			}
			//			if(!keepSearching) {
			//				for(int i=0; i< currState.getActions().size();i++)
			//					expansionSeq+= currState.getActions().get(i) + " ";
			//				System.out.println("else");
			//				break;
			//			}
			if(keepSearching) {
			//	System.out.println("Searching");

				State pickState= (State)(cg.PickUp((State)currState.clone())).clone();
				//System.out.println("this is the pick state"+pickState);
				if(pickState.getActions().get(pickState.getActions().size()-1)!="null" && !stateExists(pq,pickState))
				{
					pq.add(pickState);
					//System.out.println("pick state has entered the queue");

				}

				State dropState=(State) (cg.Drop((State)currState.clone())).clone();
				//System.out.println("this is the drop state "+dropState);
				if(dropState.getActions().get(dropState.getActions().size()-1)!="null" && !stateExists(pq,dropState))
				{
					pq.add(dropState);
					//System.out.println("drop state has entered the queue");
				}


				State retrieveState=(State)(cg.Retrieve((State)currState.clone())).clone();
				//System.out.println("this is the retrieve state "+retrieveState);
				if(retrieveState.getActions().get(retrieveState.getActions().size()-1)!="null" && !stateExists(pq,retrieveState))
				{
					pq.add(retrieveState);
					//System.out.println("retrieved state has entered the queue");
				}



				State upState= (State)(cg.MoveUp((State)currState.clone())).clone();
				//System.out.println("this is the upstate "+upState);
				if(upState.getActions().get(upState.getActions().size()-1)!="null" && upState.getActions().get(upState.getActions().size()-2)!="down" && !stateExists(pq,upState)) {
					pq.add(upState);
					//System.out.println("added upstate to queue");

				}

				State downState=(State) (cg.MoveDown((State)currState.clone())).clone();
				//System.out.println("this is the downstate "+downState);
				if(downState.getActions().get(downState.getActions().size()-1)!="null" && downState.getActions().get(downState.getActions().size()-2)!="up" && !stateExists(pq,downState)) {
					pq.add(downState);
					//System.out.println("added downstate to queue");
				}


				State rightState=(State) (cg.MoveRight((State)currState.clone())).clone();
				//System.out.println("this is the right state "+rightState);
				if(rightState.getActions().get(rightState.getActions().size()-1)!="null" && rightState.getActions().get(rightState.getActions().size()-2)!="left" && !stateExists(pq,rightState))
				{
					pq.add(rightState);
					//System.out.println("added right state to queue");
				}

				State leftState= (State) (cg.MoveLeft((State)currState.clone())).clone();
				//System.out.println("this is the left state "+leftState);
				if(leftState.getActions().get(leftState.getActions().size()-1)!="null" && leftState.getActions().get(leftState.getActions().size()-2)!="right" && !stateExists(pq,leftState)) {
					pq.add(leftState);
					//System.out.println("added left state to queue");
				}


			}

			else {
				for(int i=0; i< currState.getActions().size();i++)
					expansionSeq+= currState.getActions().get(i) + ",";

				death=currState.getTotalPassengers()-currState.getRescued();
				rescued= currState.getRescued();
				retrieval=currState.getRetrieved();
				//System.out.println("else");
				break;
			}


		}
		String nodesStr = ""+ nodes;
		String[] res = new String[4];
		res[0]=expansionSeq;
		res[1]=death+"";
		res[2]=retrieval+"";
		res[3]=nodesStr;
		//System.out.println("\n rescued are: " +rescued);
		return res;
	}


	public static boolean illegalPolice(State state, String action) throws CloneNotSupportedException {
		State currState= (State)state.clone();
		int index=0;
		
		for(int i=currState.getActions().size()-1; i>0 ;i--) {
			if(currState.getActions().get(i)=="pickup" || currState.getActions().get(i)=="drop" || currState.getActions().get(i)=="retrieve") {
				index=i;
				break;
			}
			
			
		}
		
		for(int j=index; j<currState.getActions().size();j++) {
			if(action=="down" && currState.getActions().get(j)=="up")
				return false;
			if( action=="up" && currState.getActions().get(j)=="down")
				return false;
			if(action=="left" && currState.getActions().get(j)=="right")
				return false;
			if(action=="right" && currState.getActions().get(j)=="left")
				return false;
		}
		return true;
		
		
	}
	
//	public static void illegalPolice(State state) throws CloneNotSupportedException {


	public static void main(String[] args) throws CloneNotSupportedException {
		//System.out.println(GenGrid());
		//String grid6 = "7,5;86;0,0;1,3,1,5,4,2;1,1,42,2,5,99,3,5,89;";
		//String grid0 = "5,6;50;0,1;0,4,3,3;1,1,90;";
		//String grid1 = "6,6;52;2,0;2,4,4,0,5,4;2,1,19,4,2,6,5,0,8;";
		//String grid2 = "7,5;40;2,3;3,6;1,1,10,4,5,90;";
		//String grid3 = "8,5;60;4,6;2,7;3,4,37,3,5,93,4,0,40;";
		String grid9 = "7,5;100;3,4;2,6,3,5;0,0,4,0,1,8,1,4,77,1,5,1,3,2,94,4,3,46;";
		ArrayList<Ship> ships= new ArrayList<Ship>();
		ships.add(new Ship(3,4,37));
		ships.add(new Ship(3,5,93));
		ships.add(new Ship(4,0,40));
		ArrayList<Station> stations= new ArrayList<Station>();
		stations.add(new Station(2,7));
		//stations.add(new Station(4,0));
		//stations.add(new Station(5,4));
		Grid grid = new Grid(5,8,ships,stations);
		//int[] loc=grid.emptyCell();
		CoastGuard cg=new CoastGuard(4, 6, 60,grid);
		ArrayList<String> empty=new ArrayList<String>();
		State root= new State(cg.getI(),cg.getJ(),cg.getPass(),0,0,grid.getShipList(),empty,"root");
		System.out.println(cg.getI()+","+ cg.getJ() + ","+cg.getCapacity());
		
		for(int i=0;i<ships.size();i++) {
			System.out.println(ships.get(i));
		}
		for(int i=0;i<stations.size();i++) {
			System.out.println(stations.get(i));
		}
		
//		System.out.println("Station coord: "+ grid.getStationList().get(0).getI() + "," + grid.getStationList().get(0).getJ());
//		System.out.println("Ship coord: "+ grid.getShipList().get(0).getI() + "," + grid.getShipList().get(0).getJ() + "," + grid.getShipList().get(0).getNbPass());

		
		String[] stuff=Greedy1(root,cg);
		System.out.println(grid.printCells(cg.getI(),cg.getJ()));
		//stuff[0]=stuff[0].substring(5,(stuff[0].length()-1));
		System.out.println("Expansion sequence: " +stuff[0]);
		System.out.println("Deaths: " +stuff[1]);
		System.out.println("Retrievals: " +stuff[2]);
		System.out.println("Nodes Expanded: " +stuff[3]);
		System.out.println("cg capacity: " + cg.getCapacity());
		
	}

	@Override
	public int compare(State o1, State o2) {
		int blacko1box=0;
		int blacko2box=0;
		int passo1left=0;
		int passo2left=0;
		int patho1cost=0;
		int patho2cost=0;
		if(AS) {
			patho1cost = o1.getActions().size();
			patho2cost = o2.getActions().size();
			
		}
			
		
		for(int i=0; i<o1.ships.size();i++) {
			if(o1.ships.get(i).isWreck() && o1.ships.get(i).isBlackBox() )
				blacko1box++;
			passo1left += o1.ships.get(i).getPassLeft();

		}
		passo1left = passo1left/cgGlobal.capacity;
		for(int i=0; i<o2.ships.size();i++) {
			if(o2.ships.get(i).isWreck() && o2.ships.get(i).isBlackBox() )
				blacko2box++;
			passo2left += o2.ships.get(i).getPassLeft();

		}
		passo2left = passo2left/cgGlobal.capacity;
		if(awelheuristic) {
			if ((blacko1box +patho1cost)< (blacko2box+patho2cost))
				return 1;
			else if ((blacko1box+patho1cost)> (blacko2box+patho2cost))
				return -1;
		}
		else {
			if ((blacko1box+passo1left+patho1cost) < (blacko2box+passo2left+patho2cost))
				return 1;
			else if ((blacko1box+passo1left+patho1cost) > (blacko2box+passo2left+patho2cost))
				return -1;

		}
		return 0;
	}
}
