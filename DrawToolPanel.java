import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class DrawToolPanel extends JPanel
{
	private JButton tiese, rtiese, tApskritimas, tStaciakampis, tApstaciakampis, tDaugiakampis;		
	private JButton pStaciakampis, pApskritimas, pDaugiakampis, pApstaciakampis;
	

	private CanvasPanel canvasPanel;
	
	public DrawToolPanel(CanvasPanel inCanvasPanel)
	{
		canvasPanel = inCanvasPanel;
/*----------------------------------------------------------------------------*/		 
		tiese			= new JButton("",new ImageIcon("icons/tiese.gif"));
		rtiese			= new JButton("",new ImageIcon("icons/rtiese.gif"));		
		
		tApskritimas	= new JButton("",new ImageIcon("icons/tapskritimas.gif"));
		pApskritimas	= new JButton("",new ImageIcon("icons/papskritimas.gif"));
		
		tStaciakampis	= new JButton("",new ImageIcon("icons/tstaciakampis.gif"));
		pStaciakampis	= new JButton("",new ImageIcon("icons/pstaciakampis.gif"));		
		
		tApstaciakampis	= new JButton("",new ImageIcon("icons/tapvalainis.gif"));
		pApstaciakampis	= new JButton("",new ImageIcon("icons/papvalainis.gif"));
		
		tDaugiakampis	= new JButton("",new ImageIcon("icons/tdaugiakampis.gif"));
		pDaugiakampis	= new JButton("",new ImageIcon("icons/pdaugiakampis.gif"));
		
		tiese.addActionListener(new ToolButtonListener());
		tiese.setToolTipText("Tiesë");		
		rtiese.addActionListener(new ToolButtonListener());
		rtiese.setToolTipText("Pieðtukas");
		
		tApskritimas.addActionListener(new ToolButtonListener());
		tApskritimas.setToolTipText("Tuðèiavidurë elipsë");
		pApskritimas.addActionListener(new ToolButtonListener());
		pApskritimas.setToolTipText("Pilnavidurë elipsë");
		
		tStaciakampis.addActionListener(new ToolButtonListener());
		tStaciakampis.setToolTipText("Tuðèiaviduris staèiakampis");
		pStaciakampis.addActionListener(new ToolButtonListener());
		pStaciakampis.setToolTipText("Pilnaviduris staèiakampis");
		
		tApstaciakampis.addActionListener(new ToolButtonListener());
		tApstaciakampis.setToolTipText("Bezier kreivinë");
		pApstaciakampis.addActionListener(new ToolButtonListener());
		pApstaciakampis.setToolTipText("Pilnavidurë kreivinë forma");
		
		tDaugiakampis.addActionListener(new ToolButtonListener());
		tDaugiakampis.setToolTipText("Tuðèiaviduris daugiakampis");
		pDaugiakampis.addActionListener(new ToolButtonListener());
		pDaugiakampis.setToolTipText("Pilnaviduris daugiakampis");
		
		
				
	
/*----------------------------------------------------------------------------*/		
		
		this.setBorder(new TitledBorder("Formos"));
		this.setLayout(new GridLayout(5,2)); 
		this.add(tiese);
		this.add(rtiese);
				
		this.add(tApskritimas);
		this.add(pApskritimas);
		
		this.add(tStaciakampis);
		this.add(pStaciakampis);
		
		this.add(tApstaciakampis);
		this.add(pApstaciakampis);
		
		this.add(tDaugiakampis);
		this.add(pDaugiakampis);
		
							
	}
/*----------------------------------------------------------------------------*/
	private class ToolButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{	
			if(canvasPanel.isExistPolygonBuffer()!= false)
			{
				canvasPanel.flushPolygonBuffer();
			}
			if(canvasPanel.isExistBezierBuffer()!= false)
			{
				canvasPanel.flushBezierBuffer();
			}
			if(event.getSource() == tiese)
			{
				canvasPanel.setDrawMode(canvasPanel.LINE);					
			}
			if(event.getSource() == rtiese)
			{
				canvasPanel.setDrawMode(canvasPanel.FREE_HAND);
			}
			
			if(event.getSource() == tApskritimas)
			{				
				canvasPanel.setSolidMode(Boolean.FALSE);
				canvasPanel.setDrawMode(canvasPanel.OVAL);
			}
			if(event.getSource() == pApskritimas)
			{				
				canvasPanel.setSolidMode(Boolean.TRUE);
				canvasPanel.setDrawMode(canvasPanel.OVAL);
			}
			
			
			if(event.getSource() == tStaciakampis)
			{
				canvasPanel.setSolidMode(Boolean.FALSE);
				canvasPanel.setDrawMode(canvasPanel.SQUARE);
			}
			if(event.getSource() == pStaciakampis)
			{
				canvasPanel.setSolidMode(Boolean.TRUE);
				canvasPanel.setDrawMode(canvasPanel.SQUARE);
			}
			
			
			if(event.getSource() == tApstaciakampis)
			{
				canvasPanel.setSolidMode(Boolean.FALSE);
				canvasPanel.setDrawMode(canvasPanel.BEZIER_KREIVE);
			}
			if(event.getSource() == pApstaciakampis)
			{
				canvasPanel.setSolidMode(Boolean.TRUE);
				canvasPanel.setDrawMode(canvasPanel.BEZIER_KREIVE);
			}
			
			if(event.getSource() == tDaugiakampis)
			{
				canvasPanel.setSolidMode(Boolean.FALSE);
				canvasPanel.setDrawMode(canvasPanel.POLYGON);
			}
			if(event.getSource() == pDaugiakampis)
			{
				canvasPanel.setSolidMode(Boolean.TRUE);
				canvasPanel.setDrawMode(canvasPanel.POLYGON);
			}			
		}
	}
}
