import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class TransformMove extends JDialog
{
	private JPanel panelis1, panelis2, panelis3, panelis4;
	private JLabel laibelis1, laibelis2, laibelis3, laibelis4;
	private JTextField tekstas1, tekstas2, tekstas3, tekstas4;
	private JButton gerai, baigti;
	private CanvasPanel canvasPanel;
	private Container mainContainer;

	TransformMove(JFrame parent, CanvasPanel inCanvasPanel)
	{
		super(parent);
		this.setSize(200,150);
		this.setTitle("Postûmis");
		canvasPanel = inCanvasPanel;

		panelis2 = new JPanel();
		panelis2.setBorder(new TitledBorder("Parametrai:"));
		panelis2.setLayout(new FlowLayout());
		laibelis3 = new JLabel("Tx: ");
		laibelis4 = new JLabel("Ty: ");
		tekstas3 = new JTextField("0", 4);
		tekstas4 = new JTextField("0", 4);		
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
							
					canvasPanel.setTransformSeka1(1,1,0,
												  Double.parseDouble(tekstas3.getText().trim()),
												  Double.parseDouble(tekstas4.getText().trim()));		
												  
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
		mainContainer.setLayout(new GridLayout(2, 1));
		mainContainer.add(panelis2);
		mainContainer.add(panelis3);
		
	}
	
	public void RodytLanga()
	{
		this.setVisible(true);
	}
 
}