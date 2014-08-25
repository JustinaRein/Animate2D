import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class TransformToolPanel extends JMenuBar
{
	private JMenu Segtuvas, Koreguoti, Rodyti, Transform, Animacija, Pagalba;
	private JMenuItem menuItem;
	private CanvasPanel canvasPanel;
	private TransformScale transformMastelis;
	private TransformRotate transformPosukis;
	private TransformMove transformPostumis;
	private TransformCombine transformSeka;
	private AnimationShow rodomAnimacija;

	protected JFrame tevelis;
	
	public TransformToolPanel(CanvasPanel inCanvasPanel, JFrame inTevelis)
	{
		canvasPanel = inCanvasPanel;		
		tevelis = inTevelis;
		Segtuvas = new JMenu("Segtuvas");
		
		menuItem = new JMenuItem("Naujas");
		menuItem.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{	
					canvasPanel.setBaigiamPiestBP();
					canvasPanel.setFileTuscias();
					canvasPanel.clearCanvas();
				}	
			}	
		);
		Segtuvas.add(menuItem);	
		Segtuvas.addSeparator();		
		
		menuItem = new JMenuItem("Iðsaugoti");
		menuItem.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{	
					canvasPanel.setBaigiamPiestBP();
					canvasPanel.SaveCanvasToFile();
				}	
			}	
		);
		Segtuvas.add(menuItem);	
		menuItem = new JMenuItem("Iðsaugoti kaip...");
		menuItem.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{	
					canvasPanel.setBaigiamPiestBP();
					canvasPanel.setFileTuscias();
					canvasPanel.SaveAsCanvasToFile();
				}	
			}	
		);
		Segtuvas.add(menuItem);	
		Segtuvas.addSeparator();
		
		menuItem = new JMenuItem("Baigti");
		menuItem.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					System.exit(0);	
				}	
			}
		);				
		Segtuvas.add(menuItem);	
		
		this.add(Segtuvas);
		
		Koreguoti = new JMenu("Koreguoti");
		menuItem = new JMenuItem("Atgal");
		menuItem.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					canvasPanel.setBaigiamPiestBP();
					canvasPanel.undo();	
				}	
			}
		);
		Koreguoti.add(menuItem);
		
		menuItem = new JMenuItem("Pirmyn");
		menuItem.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					canvasPanel.setBaigiamPiestBP();
					canvasPanel.redo();	
				}	
			}
		);
		Koreguoti.add(menuItem);
		Koreguoti.addSeparator();
		menuItem = new JMenuItem("Iðvalyti viskà");
		menuItem.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					canvasPanel.setBaigiamPiestBP();
					canvasPanel.clearCanvas();	
				}	
			}
		);
		Koreguoti.add(menuItem);
		this.add(Koreguoti);
		
		Transform = new JMenu("Transformacijos");
		menuItem = new JMenuItem("Mastelis       ");
		menuItem.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					canvasPanel.setBaigiamPiestBP();	
					transformMastelis = new TransformScale(tevelis, canvasPanel);				
					transformMastelis.RodytLanga();
				}	
			}	
		);
		Transform.add(menuItem);
		menuItem = new JMenuItem("Posûkis        ");
		menuItem.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{	
					canvasPanel.setBaigiamPiestBP();
					transformPosukis  = new TransformRotate(tevelis, canvasPanel);				
					transformPosukis.RodytLanga();
				}	
			}	
		);
		Transform.add(menuItem);
		menuItem = new JMenuItem("Postumis       ");
		menuItem.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					canvasPanel.setBaigiamPiestBP();	
					transformPostumis = new TransformMove(tevelis, canvasPanel);				
					transformPostumis.RodytLanga();
				}	
			}	
		);
		Transform.add(menuItem);
		Transform.addSeparator();	
		menuItem = new JMenuItem("Transformacijø seka");	
		menuItem.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					canvasPanel.setBaigiamPiestBP();	
					transformSeka = new TransformCombine(tevelis, canvasPanel);				
					transformSeka.RodytLanga();
				}	
			}	
		);
		Transform.add(menuItem);
		
		this.add(Transform);
		
		Animacija = new JMenu("Animacija");
		menuItem = new JMenuItem("Rodyti   ");
		menuItem.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{	
					canvasPanel.OpenCanvasFile();
					boolean atidare = canvasPanel.arAtidare();
					String kelias = canvasPanel.yraKelias();
					if (atidare==true)
					{
						rodomAnimacija = new AnimationShow(tevelis, canvasPanel, kelias);
					}					
				}	
			}	
		);
		Animacija.add(menuItem);
		
		this.add(Animacija);
		
		Pagalba = new JMenu("Pagalba");
		menuItem = new JMenuItem("Pagalbos kontektas");
		Pagalba.add(menuItem);
		menuItem = new JMenuItem("Apie Programà");
		Pagalba.add(menuItem);
		//this.add(Pagalba);
	
	}	
}