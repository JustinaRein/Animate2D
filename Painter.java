import java.awt.*;
import java.awt.event.*;
import java.util.*;
//import java.io.*;
import javax.swing.*;

public class Painter extends JFrame
{
	private CanvasPanel 		canvasPanel;
	private ToolButtonPanel   	toolButtonPanel;
	private ColorButtonPanel	colorButtonPanel;
	private TransformMygtukai	transformMygtukai;	
	private TimeLine			laikmatis;
	
	private JLabel tarpas1,tarpas2;
	private JPanel panelis1, panelis2;

	private Container 			mainContainer;
	
	
	public Painter()
	{
		super("Transformacijø taikymai 2D animacijoje ");		
		
		
		canvasPanel 	  = new CanvasPanel();
		laikmatis		  = new TimeLine(canvasPanel);
		toolButtonPanel   = new ToolButtonPanel(canvasPanel);
		colorButtonPanel  = new ColorButtonPanel(canvasPanel);
		transformMygtukai = new TransformMygtukai(canvasPanel,this);
		

		panelis1 = new JPanel();
		panelis1.setLayout(new GridLayout(2,1));
		panelis1.add(toolButtonPanel);
		panelis1.add(colorButtonPanel);
			
		tarpas1 = new JLabel("      ");
		tarpas2 = new JLabel("  ");
	
		mainContainer = getContentPane();
		this.setJMenuBar(transformMygtukai);
		mainContainer.add(panelis1,BorderLayout.WEST);
		mainContainer.add(canvasPanel,BorderLayout.CENTER);
		mainContainer.add(laikmatis, BorderLayout.SOUTH);
		mainContainer.add(tarpas1, BorderLayout.EAST);
		mainContainer.add(tarpas2, BorderLayout.NORTH);
		
		setSize(800,600);
		setVisible(true);
		
		addWindowListener (
      		new WindowAdapter () 
      		{
      			public void windowClosing (WindowEvent e) 
      			{
      				System.exit(0);
      			}
      			public void windowDeiconified (WindowEvent e) 
      			{
      				canvasPanel.repaint();
      			}
      			public void windowActivated (WindowEvent e) 
      			{	 
      				canvasPanel.repaint();
      			}
      		}
      	);
	}

	
}

