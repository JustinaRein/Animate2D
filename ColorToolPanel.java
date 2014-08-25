import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class ColorToolPanel extends JPanel
{
	private JButton foreGroundColorBtn,backGroundColorBtn, grotelesBtn, lapasBtn, rankaBtn, pazymetiBtn;
	private JLabel foreGroundColorLbl,backGroundColorLbl,foreColorLbl,backColorLbl;
	private JLabel figura, kordinates, dydis;
	private Color foreColor, backColor;
	private CanvasPanel canvasPanel;
	
	public ColorToolPanel(CanvasPanel inCanvasPanel)
	{
		canvasPanel = inCanvasPanel;
		
		
		foreGroundColorLbl = new JLabel("   ");
		foreGroundColorLbl.setOpaque(true);
		foreGroundColorLbl.setBackground(canvasPanel.getForeGroundColor());
		foreGroundColorLbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		backGroundColorLbl = new JLabel("   ");
		backGroundColorLbl.setOpaque(true);
		backGroundColorLbl.setBackground(canvasPanel.getBackGroundColor());
		backGroundColorLbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		foreGroundColorBtn = new JButton("P");
		foreGroundColorBtn.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					setForeGroundColor();
				}
			}
		);
		
		backGroundColorBtn = new JButton("F");
		backGroundColorBtn.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					setBackGroundColor();
				}
			}
		);	
		
		grotelesBtn = new JButton("",new ImageIcon("icons/groteles.gif"));
		grotelesBtn.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{				
					canvasPanel.setGroteles(Boolean.TRUE);
				}
			}
		);	
		
		lapasBtn = new JButton("",new ImageIcon("icons/lapas.gif"));
		lapasBtn.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					canvasPanel.setGroteles(Boolean.FALSE);
				}
			}
		);
		
		rankaBtn = new JButton("",new ImageIcon("icons/ranka.gif"));
		rankaBtn.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					canvasPanel.setBaigiamPiestBP();
					canvasPanel.setPazymejimas(1);
				}
			}
		);
		
		pazymetiBtn = new JButton("",new ImageIcon("icons/pazymeti.gif"));
		pazymetiBtn.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					canvasPanel.setBaigiamPiestBP();
					canvasPanel.setPazymejimas(2);
				}
			}
		);			
		
		JLabel tarpas1 = new JLabel("");
		JLabel tarpas2 = new JLabel("");
		JLabel tarpas3 = new JLabel("");
		JLabel tarpas4 = new JLabel("");
		JLabel tarpas5 = new JLabel("");
		JLabel tarpas6 = new JLabel("");
		
		this.setBorder(new TitledBorder(""));
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new GridLayout(6,2));
		this.add(tarpas1);
		this.add(tarpas2);
		this.add(tarpas3);
		this.add(tarpas4);
		//this.add(rankaBtn);
		//this.add(pazymetiBtn);
		this.add(tarpas5);
		this.add(tarpas6);		
		this.add(grotelesBtn);
		this.add(lapasBtn);		
		this.add(foreGroundColorBtn);
		this.add(foreGroundColorLbl);
		this.add(backGroundColorBtn);
		this.add(backGroundColorLbl);
		
		
	}

/*----------------------------------------------------------------------------*/	
	public void setForeGroundColor()
	{
		foreColor = JColorChooser.showDialog(null,"Piesimo spalva",foreColor);
		if(foreColor!=null)
		{
			foreGroundColorLbl.setBackground(foreColor);
			canvasPanel.setForeGroundColor(foreColor);
		}
	}
/*----------------------------------------------------------------------------*/
	public void setBackGroundColor()
	{
		backColor = JColorChooser.showDialog(null,"Fono spalva",backColor);
		if(backColor!=null)
		{
			backGroundColorLbl.setBackground(backColor);
			canvasPanel.setBackGroundColor(backColor);
		}
	}
}