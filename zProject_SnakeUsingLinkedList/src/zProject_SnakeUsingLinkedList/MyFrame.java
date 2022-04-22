package zProject_SnakeUsingLinkedList;

import javax.swing.JFrame;

public class MyFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MyFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new MyPanel());
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
}
