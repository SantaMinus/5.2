package sava_graph;

import java.io.FileNotFoundException;

public class GraphGenerator {
	
	public String generate(int numberOfVertex,int minVertexWeight,int maxVertexWeight,double correlation,int minConnection, int maxConnection){
		String res="";
		double cor=0;
		
		do{
			res="";
			int totalVertexWeight=0;
			int totalLinksWeight=0;
			//generate weight for each vertex
			for(int i=0;i<numberOfVertex;i++){
				int weight=(int)(Math.random()*(maxVertexWeight-minVertexWeight)+minVertexWeight);
				totalVertexWeight+=weight;
				res=res+(i+1)+" "+weight+"\n";
			}
			//generate links
			res+="link\n";
			for(int i=0;i<numberOfVertex;i++){
				int firstNode=(int)(Math.random()*(numberOfVertex-1)+1);
				int secondNode=(int)(Math.random()*(numberOfVertex-firstNode)+firstNode)+1;
				int weight=(int)(Math.random()*(maxConnection-minConnection)+minConnection);
				totalLinksWeight+=weight;
				res+=firstNode+" "+secondNode+" "+weight+"\n";
			}
			cor=Math.floor(((double)totalVertexWeight/(totalVertexWeight+totalLinksWeight))*100)/100;
			//System.out.println(cor);
		}while(cor!=correlation);
		return res;
	}
	
	
	public static void run(){
		GraphGenerator g=new GraphGenerator();
		String s=g.generate(5, 1, 3, 0.5, 1, 3);
		java.io.PrintStream ps=null;
		try {
			ps = new java.io.PrintStream("/home/katerynasavina/Documents/java/5.2/pzks1_gen.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ps.print(s);
	}

}
