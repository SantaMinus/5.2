package sava_graph;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Maths {
	static int INF = Integer.MAX_VALUE/2;
	public static ArrayList<Node> nodes = new ArrayList <Node>();
	public static ArrayList<Connection> advLinks = new ArrayList <Connection>();
	public static ArrayList<Unit> units = new ArrayList <Unit>();
	static int [][]matrix, dist, sysMatr;
	static int []color;

	public static void createMatrix() {
		matrix = new int[nodes.size()][nodes.size()];
		for(int i=0; i<advLinks.size(); i++) {
			matrix[advLinks.get(i).from.id-1][advLinks.get(i).to.id-1]=1;
		}
		printMatrix(matrix);
	}
	
	static void printMatrix(int[][] m) {
		for(int i=0; i<m.length; i++) {
			for(int j=0; j<m.length; j++)
				System.out.print(matrix[i][j]);
			System.out.println();
		}
	}
	
	static void floydWarshall(){
		int n=matrix.length;
		for (int i=0; i<n; i++) 
			System.arraycopy(matrix[i], 0, dist[i], 0, n);
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				if(dist[i][j]==0 && i!=j)
					dist[i][j]=INF;
			}
		}
		for (int k=0; k<n; k++)
			for (int i=0; i<n; i++)
				for (int j=0; j<n; j++)
					dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j]);
	}
	
	static int min(int a, int b){
		if(a<b) return a;
		else return b;
	}
	
	public static void createSystemStructure() {
		units.clear();
		int n=UI.sysTable.getRowCount();
		sysMatr = new int[n][n];
		for (int i=0; i<n; i++){
			Unit u = new Unit(i+1);
			units.add(u);
			for (int j=i; j<n; j++){
				sysMatr[i][j] = Integer.parseInt(UI.sysTable.getValueAt(i, j).toString());
				sysMatr[j][i] = sysMatr[i][j];
				UI.sysTable.setValueAt(sysMatr[i][j], j, i);
			}
		}
		for(int i=0; i<units.size(); i++){
			for (int j=0; j<n; j++){ 
				if(sysMatr[i][j]!=0){
					units.get(i).addNeighbour(units.get(j));
				}
			}
		}
	}
	
	public static void isCohesive(){
		int n = units.size();
		color = new int[n];
		
		for(int i=0; i<n; i++){
			color[i]=0;
		}
		color[0]=1;
		for(int i=0; i<n; i++){
			if(color[i]==1){
				for(int j=0; j<n; j++){
					if(sysMatr[i][j]==1 && color[j]!=2) color[j]=1;
				}
				color[i]=2;
			}
		}
		for(int i=0; i<n; i++){
			if(color[i]==0){
				JOptionPane.showMessageDialog(UI.frame, "The system is not connected!", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		JOptionPane.showMessageDialog(UI.frame, "The system is well connected");
	}
	
	public static void sort2(){
		int n=nodes.size();
		Node tmp;
		Node []sorted2 = new Node[n];
		for(int i=0; i<n; i++){
			sorted2[i]=nodes.get(i);
		}
		for(int k=0; k<n; k++)
			for(int i=0; i<n-1; i++){
				if(sorted2[i].weight < sorted2[i+1].weight){
					tmp=sorted2[i];
					sorted2[i]=sorted2[i+1];
					sorted2[i+1]=tmp;
				}
			}
		//System.out.println("Sorted2 (id:weight):  ");
		UI.text.setText(UI.text.getText()+"\nSorted2 (id:weight):  ");
		for(int i=0; i<n; i++){
			//System.out.print(sorted2[i].id+":"+sorted2[i].weight+"    ");
			UI.text.setText(UI.text.getText()+"\n"+sorted2[i].id+":"+sorted2[i].weight+"    ");
		}
	}
	
	public static void sort3(){
		int n=nodes.size();
		Node []sorted3 = new Node[n];
		Node tmp;
		
		for(int i=0; i<n; i++){
			nodes.get(i).countAdj();
			sorted3[i]=nodes.get(i);
			//System.out.println(sorted3[i].id+" adj="+sorted3[i].adjacency);
		}
		int [][]dist3;
		int []path = new int[n];
		dist3=floydWarshallMod();
		//printMatrix(dist3);
		for(int i=0; i<n; i++){
			path[i]=0;
			for (int j=0; j<n; j++){ 
				if(dist3[j][i]>path[i] && dist3[j][i]!=-INF)
					path[i]=dist3[j][i];
			}
		}
		for(int k=0; k<n; k++){
			for(int i=0; i<n-1; i++){
				if(sorted3[i].adjacency < sorted3[i+1].adjacency){
					tmp=sorted3[i];
					sorted3[i]=sorted3[i+1];
					sorted3[i+1]=tmp;
				} else if(sorted3[i].adjacency == sorted3[i+1].adjacency){
					if(path[sorted3[i].id-1] > path[sorted3[i].id]){
						tmp=sorted3[i];
						sorted3[i]=sorted3[i+1];
						sorted3[i+1]=tmp;
					}
				}
			}
		}
		//System.out.println("\nSorted3 (id:adjacency:crit.path):  ");
		UI.text.setText(UI.text.getText()+"\nSorted3 (id:adjacency:crit.path):  ");
		for(int i=0; i<n; i++){
			//System.out.print(sorted3[i].id+":"+sorted3[i].adjacency+":"+path[sorted3[i].id-1]+"   ");
			UI.text.setText(UI.text.getText()+"\n"+sorted3[i].id+":"+sorted3[i].adjacency+":"+path[sorted3[i].id-1]+"   ");
		}
	}
	
	public static void sort4(){
		int n=nodes.size(), s=0, f=0, max=0;
		Node []sorted4 = new Node[n];
		Node tmp;
		ArrayList<Node> ends = new ArrayList<Node>();

		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				s+=matrix[i][j];
			}
			if(s==0){
				ends.add(Maths.nodes.get(i));
			}
			s=0;
		}
		for(int i=0; i<ends.size(); i++){
			ends.get(i).setCritPath(0);
		}
		for(int m=0; m<n; m++){
			for(int i=0; i<n; i++){
				for(int k=0; k<nodes.get(i).next.size(); k++){
					if(nodes.get(i).next.get(k).critTimePath!=0){
						f++;
					}
				}
				if(f==nodes.get(i).next.size() && !ends.contains(nodes.get(i)) && nodes.get(i).critTimePath==0){
					for(int k=0; k<nodes.get(i).next.size(); k++){
						if(max < nodes.get(i).next.get(k).critTimePath){
							max = nodes.get(i).next.get(k).critTimePath;
						}
					}
					nodes.get(i).setCritPath(max);
				}
				f=0;
				max=0;
			}
		}
		for(int i=0; i<n; i++)
			sorted4[i]=nodes.get(i);
		for(int k=0; k<n; k++)
			for(int i=0; i<n-1; i++){
				if(sorted4[i].critTimePath < sorted4[i+1].critTimePath){
					tmp=sorted4[i];
					sorted4[i]=sorted4[i+1];
					sorted4[i+1]=tmp;
				}
			}
		UI.text.setText(UI.text.getText()+"\nSorted4 (id:crit.time.path):  ");
		for(int i=0; i<n; i++){
			UI.text.setText(UI.text.getText()+"\n"+sorted4[i].id+":"+sorted4[i].critTimePath);
		}
	}
	
	static int[][] floydWarshallMod(){
		int n=matrix.length;
		int[][]arr = new int[n][n];
		for (int i=0; i<n; i++) 
			System.arraycopy(matrix[i], 0, arr[i], 0, n);
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				if(arr[i][j]==0 && i!=j)
					arr[i][j]=-INF;
			}
		}
		for (int k=0; k<n; k++)
			for (int i=0; i<n; i++)
				for (int j=0; j<n; j++)
					arr[i][j] = max(arr[i][j], arr[i][k] + arr[k][j]);
		return arr;
	}
	
	static int max(int a, int b){
		if(a>b) return a;
		else return b;
	}
}
