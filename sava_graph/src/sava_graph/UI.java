package sava_graph;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class UI extends JPanel{
    final static int maxGap = 20;
    static GridLayout layout = new GridLayout(0,1);
    static JMenuBar menu=new JMenuBar();
    static JTabbedPane tabbedPane = new JTabbedPane();
    static JComponent panel = new JPanel(false), panel2 = new JPanel(false);
    public static int x;
	public static int y;
	static JMenuItem save = new JMenuItem("Save");
    static JMenuItem load = new JMenuItem("Load");
    static JMenuItem clear = new JMenuItem("Clear");
    static JMenuItem exit = new JMenuItem("Exit");
    static JButton finish = new JButton("Apply");
	static JButton cycle = new JButton("Check for cycles");
	static JFrame frame = new JFrame("Rusanova1");
	static JTable table, sysTable;
	static JScrollPane scrollPane;
	static JSplitPane split;
	static JButton system = new JButton("Create system structure");
	static JButton addRow = new JButton("+");
	static JButton delRow = new JButton("-");
	static JButton sort2 = new JButton("sort2");
	static JButton sort3 = new JButton("sort3");
	static JButton sort4 = new JButton("sort4");
	static JButton generate = new JButton("Generate graph");
	static DefaultTableModel dtm, sdtm;
	static JTextArea text = new JTextArea();
	
	static void paintUI() {
		Dimension dim = new Dimension(580,580);
	    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    makeMenu();
	    
	    frame.setJMenuBar(menu);
	    frame.setPreferredSize(dim);
	    split = new JSplitPane();
	    split.setOrientation(JSplitPane.VERTICAL_SPLIT);
	    split.setTopComponent(new DrawingPane());
	    
	    createTable();
	    frame.add(split);
	    
	    frame.pack();
	    frame.setVisible(true);
	}
	
	protected static void makeMenu() {
		JMenu file=new JMenu("File");
		JToolBar tb = new JToolBar();
		menu.add(file);
	    tb.add(finish);
	    tb.add(cycle);
	    tb.add(generate);
	    menu.add(tb);
	    
	    file.add(save);
	    file.add(load);
	    file.add(clear);
	    file.add(exit);
	    menu.setSize(maxGap, 50);
	    
	    setActions();
	}
	
	public static void createTable(){
		String[] columnNames = {"1", "2", "3", "4", "5"};
		Object[][] data = {{0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}};
		dtm = new DefaultTableModel(data, columnNames);
		sdtm = new DefaultTableModel(data, columnNames);
		table = new JTable(dtm);
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		JPanel p=new JPanel();
		//p.setLayout(new BorderLayout());
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		JLabel separator0 = new JLabel("Task graph matrix");
		p.add(separator0);
		p.add(table.getTableHeader());
		p.add(table);
		JLabel separator = new JLabel("System structure matrix");
		p.add(separator);
		sysTable = new JTable(sdtm);
		sysTable.setFillsViewportHeight(true);
		p.add(sysTable.getTableHeader());
		p.add(sysTable);
		
		JToolBar tb = new JToolBar();
		tb.add(system);
		tb.add(addRow);
		tb.add(delRow);
		tb.add(sort2);
		tb.add(sort3);
		tb.add(sort4);
		p.add(tb);
		p.add(text);
		split.setBottomComponent(p);     //хз на счет контент пейна, лучше его гдето раньше сделать. сам сплитпейн. и добавить две панели
		frame.add(split);
	}
	
	public static void setActions(){
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				java.io.PrintStream ps=null;
				try {
					ps = new java.io.PrintStream("/home/katerynasavina/Documents/java/5.2/pzks1.txt" );
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				for(int i=0; i<Maths.nodes.size(); i++){
						ps.println(Maths.nodes.get(i).id+" "+Maths.nodes.get(i).weight);
				}
				ps.println("link");
				for(int i=0; i<Maths.advLinks.size(); i++){
					ps.print(Maths.advLinks.get(i).from.id+" "+Maths.advLinks.get(i).to.id+" "+Maths.advLinks.get(i).weight);
					ps.println();
				}
		        ps.close();
		        text.setText(UI.text.getText()+"\nSaved");
			}
	    });
	    
	    cycle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Solution s = new Solution();
				try {
					s.run();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	    });
	    
	    finish.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Maths.createMatrix();
				for(int i=0; i<Maths.advLinks.size(); i++){
					text.setText(UI.text.getText()+"\n"+Maths.advLinks.get(i).from.id+" --> "+Maths.advLinks.get(i).to.id+" : "+Maths.advLinks.get(i).weight);
				}
				//add changing rows number dynamically
				for(int i=0; i<Maths.matrix.length; i++){
					for(int j=0; j<Maths.matrix.length; j++){
						table.setValueAt(Maths.matrix[i][j], i, j);
					}
				}
			}
	    });
	    
	    exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);				
			}
	    });
	    
	    load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String s=null;
				File file=new File("/home/katerynasavina/Documents/java/5.2/pzks1.txt");
				if(file.exists()){
					Scanner inFile=null;
					try {
						inFile = new Scanner(file);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					
					Maths.nodes.clear();
					while(inFile.hasNext()){
 						s=inFile.nextLine();
				        if(s.equals("link"))
				        	break;
				        String[] splitted = s.split("\\s+");
				        Maths.nodes.add(new Node(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])));
				    }
					
					setConnection(inFile);
					
					inFile.close();
				}
				text.setText(text.getText()+"\nLoaded");
			}
	    });
	    
	    system.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Maths.createSystemStructure();
				Maths.isCohesive();
			}
	    });
	    
	    addRow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
			}
	    });
	    
	    delRow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				sdtm.removeRow(sysTable.getRowCount()-1);
			}
	    });
	    
	    sort2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Maths.sort2();
			}
	    });
	    
	    sort3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Maths.sort3();
			}
	    });
	    
	    sort4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Maths.sort4();
			}
	    });
	    
	    generate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GraphGenerator.run();
				String s=null;
				File file=new File("/home/katerynasavina/Documents/java/5.2/pzks1.txt");
				if(file.exists()){
					Scanner inFile=null;
					try {
						inFile = new Scanner(file);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					
					Maths.nodes.clear();
					while(inFile.hasNext()){
							s=inFile.nextLine();
				        if(s.equals("link"))
				        	break;
				        String[] splitted = s.split("\\s+");
				        Maths.nodes.add(new Node(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])));
				    }
					
					setConnection(inFile);
					
					inFile.close();
					text.setText(text.getText()+"\nGraph generated");
				}
			}
	    });
	}
	
	public static void setConnection(Scanner inFile){
		String s=null;
		while(inFile.hasNext()){
			s=inFile.nextLine();
	        String[] splitted = s.split("\\s+");
	        Connection c = new Connection();
	        c.from = Maths.nodes.get(Integer.parseInt(splitted[0])-1);
	        c.to = Maths.nodes.get(Integer.parseInt(splitted[1])-1);
	        c.weight=Integer.parseInt(splitted[2]);
	        Maths.nodes.get(Integer.parseInt(splitted[0])-1).setNext(Maths.nodes.get(Integer.parseInt(splitted[1])-1));
	        Maths.nodes.get(Integer.parseInt(splitted[1])-1).setPrev(Maths.nodes.get(Integer.parseInt(splitted[0])-1));
	        
	        Maths.advLinks.add(c);
		}
	}
}