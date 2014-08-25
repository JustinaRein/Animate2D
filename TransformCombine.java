import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class TransformCombine extends JDialog
{
	private JPanel panelis1, panelis2, panelis3,panelis4, panelis5, panelis6;
	private JLabel laibelis1, laibelis2, laibelis3, laibelis4, laibelis5, laibelis6, laibelis7, laibelis8;
	private JTextField tekstas1, tekstas2, tekstas3, tekstas4, tekstas5, tekstas6, tekstas7;
	private JButton gerai, baigti;
	private JSpinner spinner;
	private CanvasPanel canvasPanel;
	private Container mainContainer;

	TransformCombine(JFrame parent, CanvasPanel inCanvasPanel)
	{
		super(parent);
		this.setSize(200,400);
		this.setTitle("Transformacijø seka");
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
		panelis2.setBorder(new TitledBorder("Mastelio keitimas:"));
		panelis2.setLayout(new FlowLayout());
		laibelis3 = new JLabel("SHx: ");
		laibelis4 = new JLabel("SHy: ");
		tekstas3 = new JTextField("1", 4);
		tekstas4 = new JTextField("1", 4);		
		panelis2.add(laibelis3);
		panelis2.add(tekstas3);
		panelis2.add(laibelis4);
		panelis2.add(tekstas4);
		
		panelis5 = new JPanel();
		panelis5.setBorder(new TitledBorder("Posûkis:"));
		panelis5.setLayout(new FlowLayout());
		laibelis5 = new JLabel("kampas: ");
		laibelis6 = new JLabel("laipsniai ");
		tekstas5 = new JTextField("0", 4);			
		panelis5.add(laibelis5);
		panelis5.add(tekstas5);
		panelis5.add(laibelis6);
		
		
		panelis6 = new JPanel();
		panelis6.setBorder(new TitledBorder("Postûmis:"));
		panelis6.setLayout(new FlowLayout());
		laibelis7 = new JLabel("Tx: ");
		laibelis8 = new JLabel("Ty: ");
		tekstas6 = new JTextField("0", 4);
		tekstas7 = new JTextField("0", 4);		
		panelis6.add(laibelis7);
		panelis6.add(tekstas6);
		panelis6.add(laibelis8);
		panelis6.add(tekstas7);
		
		panelis4 = new JPanel();
		panelis4.setBorder(new TitledBorder("Animuoti iki kadro:"));
		panelis4.setLayout(new FlowLayout());
    	spinner = new JSpinner();    	
    	spinner.setValue(new Integer(60));
    	panelis4.add(spinner);
		
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
											
					canvasPanel.setTransformSeka2(Double.parseDouble(tekstas3.getText().trim()),
									Double.parseDouble(tekstas4.getText().trim()),
									Double.parseDouble(tekstas5.getText().trim()),
									Double.parseDouble(tekstas6.getText().trim()),
									Double.parseDouble(tekstas7.getText().trim()),
									Integer.parseInt(spinner.getValue().toString()));
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
		mainContainer.setLayout(new GridLayout(6, 1));
		mainContainer.add(panelis1);
		mainContainer.add(panelis2);
		mainContainer.add(panelis5);
		mainContainer.add(panelis6);
		mainContainer.add(panelis4);
		mainContainer.add(panelis3);
		
	}
	
	public void RodytLanga()
	{
		this.setVisible(true);
	}
 
}