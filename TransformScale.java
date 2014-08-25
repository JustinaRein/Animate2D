import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class TransformScale extends JDialog
{
	private JPanel panelis1, panelis2, panelis3, panelis4;
	private JLabel laibelis1, laibelis2, laibelis3, laibelis4;
	private JTextField tekstas1, tekstas2, tekstas3, tekstas4;
	private JButton gerai, baigti;
	private CanvasPanel canvasPanel;
	private Container mainContainer;
	
	TransformScale(JFrame parent, CanvasPanel inCanvasPanel)
	{
		super(parent);
		this.setSize(200,200);
		this.setTitle("Mastelis");
		canvasPanel = inCanvasPanel;
		
		panelis1 = new JPanel();
		panelis1.setBorder(new TitledBorder("Atskaitos taðkas"));
		panelis1.setLayout(new FlowLayout());
		laibelis1 = new JLabel("X: ");
		laibelis2 = new JLabel("Y: ");
		tekstas1 = new JTextField("0", 4);
		tekstas2 = new JTextField("0", 4);
		panelis1.add(laibelis1);
		panelis1.add(tekstas1);
		panelis1.add(laibelis2);
		panelis1.add(tekstas2);
		
		
		panelis2 = new JPanel();
		panelis2.setBorder(new TitledBorder("Parametrai:"));
		panelis2.setLayout(new FlowLayout());
		laibelis3 = new JLabel("Sx: ");
		laibelis4 = new JLabel("Sy: ");
		tekstas3 = new JTextField("1", 4);
		tekstas4 = new JTextField("1", 4);
		panelis2.add(laibelis3);
		panelis2.add(tekstas3);
		panelis2.add(laibelis4);
		panelis2.add(tekstas4);
		
		panelis3 = new JPanel();
		panelis3.setLayout(new FlowLayout());
		gerai = new JButton("Gerai");
		gerai.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					canvasPanel.setAtsTaskas(Double.parseDouble(tekstas1.getText().trim()),
											Double.parseDouble(tekstas2.getText().trim()));
											
					canvasPanel.setTransformSeka1(Double.parseDouble(tekstas3.getText().trim()),
												  Double.parseDouble(tekstas4.getText().trim()),		
												  0,0,0);
					setVisible(false);
				}
			}
		);
		
		baigti = new JButton("Baigti");
		baigti.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					setVisible(false);
				}
			}			
		);
		
		panelis3.add(gerai);
		panelis3.add(baigti);
		
   	
		mainContainer = getContentPane();
		mainContainer.setLayout(new GridLayout(3, 1));
		mainContainer.add(panelis1);
		mainContainer.add(panelis2);
		mainContainer.add(panelis3);
	}
	
	public void RodytLanga()
	{
		this.setVisible(true);
	}
 
}
