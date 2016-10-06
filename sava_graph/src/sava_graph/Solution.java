package sava_graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

public class Solution {
    
    private int n; 			//количество вершин в орграфе
    private int color[]; 			 	//массив для хранения цветов вершин
    private int prev[];				    //массив предков, необходимых для восстановления цикла, в случае его наличия в графе
    private boolean cyclic = false; 	//флаг, показывающий содержит орграф цикл или нет
    ArrayList<Integer> cycle; 			//цикл
    
    //процедура обхода в глубину
    private void dfsCycle(int v) { 
        //if node is black
        if (color[v] == 2) { 
            return;
        }
        if (cyclic) { 
            return;
        }
        //if node is grey it means graph is cyclic
        if (color[v] == 1) { 
            cyclic = true;
            cycle.add(v);		//save cycle 
            for (int ver = prev[v]; ver != v; ver = prev[ver]) {
                cycle.add(ver);
            }
            cycle.add(v);
            for (int i = 0; i < cycle.size() / 2; ++i) {
                int temp = cycle.get(i);
                cycle.set(i, cycle.get(cycle.size() - 1 - i));
                cycle.set(cycle.size() - 1 - i, temp);
            }
            return;
        }
        color[v] = 1;		//mark node grey
        //запускаем обход из всех вершин, смежных с вершиной v
        int w;
        for (int i = 0; i < Maths.matrix.length; ++i) { 
            if(Maths.matrix[v][i] != 0) {
            	//w = Maths.matrix[v][i];
            	w = i;
            	prev[w] = v; 			//вершина v - это предок вершины w при обходе
            	dfsCycle(w); 			//вызов обхода от вершины w, смежной с вершиной v
            }
            if (cyclic) {
                return;
            }
        }
        color[v] = 2; 		//mark black
    }
    
    private void readData() throws IOException {
    	n = Maths.matrix.length;	//number of nodes
    	//mark nodes as white
        color = new int[n];
        Arrays.fill(color, 0);
        //ancestors array
        prev = new int[n];
        Arrays.fill(prev, -1);
        
        cyclic = false;
        cycle = new ArrayList<Integer>();
    }
   
    private void printData() throws IOException {
        if (cyclic) {
        	String s = "";
            for (int i = 0; i < cycle.size(); ++i) {
            	s += (cycle.get(i) + 1) + " ";
            }
        	JOptionPane.showMessageDialog(UI.frame, "Graph has cycle: " + s, "ERROR", JOptionPane.ERROR_MESSAGE);
        	System.exit(0);
        } else {
        	JOptionPane.showMessageDialog(UI.frame, "Graph is acyclic");
        }
    }
    
    void run() throws IOException {
        readData();
        for (int v = 0; v < n; ++v) {
            dfsCycle(v);
        }
        printData();
    }
}