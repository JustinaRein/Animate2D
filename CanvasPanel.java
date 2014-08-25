
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;
import java.lang.*;

public class CanvasPanel extends JPanel implements MouseListener,MouseMotionListener, Serializable
{
	protected final static int LINE=1,SQUARE=2,OVAL=3,POLYGON=4,BEZIER_KREIVE=5,FREE_HAND=6,
								SOLID_SQUARE=22, SOLID_OVAL=33, SOLID_POLYGON=44,
								SOLID_BEZIER_KREIVE=55;
								
	protected final static int PIESIMAS=1, PAZYMEJIMAS1=2, PAZYMEJIMAS2=3, JUDEJIMAS=1, TRANSFORM=2;	
	protected static Vector vFormos,xPolygon, yPolygon, vPazymeti, xBezier,yBezier, vHands,
							xHands, yHands, vFile;						 	
	
	private Stack atgalStack, pirmynStack;
	private Color foreGroundColor, backGroundColor; 
	
	private int x1,y1,x2,y2,linex1,linex2,liney1,liney2, drawMode=0, kadras=0, ikiKadro=0, 
				kaDarom, kelintas, uzpildModas=0;
				
	private boolean solidMode, polygonBuffer, bezierBuffer, yraGroteles;	
	private int pazymetas = -1;
	private int BezierTaskas=0;
	
	private int CanvasAukstis = 0, CanvasPlotis = 0;
	
	private double AtsX=0,AtsY=0,Sx=1,Sy=1,A=0,Tx=0,Ty=0;
	
	private File fileName;
	
	private String kelias;
	private boolean atidare=false;
	
	public CanvasPanel()
	{		
		vFormos			= new Vector();	
		xPolygon		= new Vector();
		yPolygon		= new Vector();	
		xBezier			= new Vector();
		yBezier			= new Vector();
		vPazymeti		= new Vector();	
		
		xHands			= new Vector();
		yHands			= new Vector();
		vHands			= new Vector();	
		
		vFile			= new Vector();
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		solidMode 		= false;
		polygonBuffer 	= false; 
		bezierBuffer	= false;
		foreGroundColor = Color.BLACK;
		backGroundColor = Color.WHITE;
		setBackground(backGroundColor);
		
		atgalStack = new Stack();
		pirmynStack = new Stack();
		
		kaDarom = PIESIMAS;
		repaint();			
		
	}
/*----------------------------------------------------------------------------*/		
	public void mousePressed(MouseEvent event)
	{
		if ((drawMode==OVAL)||(drawMode==SQUARE)||(drawMode==LINE)||(drawMode==FREE_HAND))
		{
			vPazymeti.removeAllElements();	
		}
		
		if (drawMode == this.FREE_HAND) 
        {
            xHands.add(new Integer(event.getX()));
            yHands.add(new Integer(event.getY()));
         }
         
		x1 = linex1 = linex2 = event.getX();
        y1 = liney1 = liney2 = event.getY();
	}
/*----------------------------------------------------------------------------*/
	public void mouseClicked(MouseEvent event){}
	public void mouseMoved(MouseEvent event){}
/*----------------------------------------------------------------------------*/
	public void mouseReleased(MouseEvent event)
	{        		
		if (drawMode == this.LINE)
        {	
        	vPazymeti.removeAllElements();
           	vFormos.add(new Coordinate(x1,y1,event.getX(),event.getY(),foreGroundColor,LINE, kadras));
			kelintas = vFormos.size()-1;
			vPazymeti.add(new Coordinate(x1,y1,kelintas));
			vPazymeti.add(new Coordinate(event.getX(),event.getY(),kelintas));
			pazymetas = kelintas; 
			atgalStack.push(new StepInfo(new Coordinate(x1,y1,event.getX(),event.getY(),foreGroundColor,LINE, kadras)));
       
        }
        if (drawMode == this.SQUARE) 
        {
        	vPazymeti.removeAllElements();
            if(solidMode)
           	{
           		if(x1 > event.getX() && y1 > event.getY())
				{
           			vFormos.add(new Coordinate(event.getX(),event.getY(),x1,y1,foreGroundColor,SOLID_SQUARE, kadras));
           			kelintas = vFormos.size()-1;
           			pazymetas = kelintas;
           			vPazymeti.add(new Coordinate(event.getX()-5,event.getY()-5,kelintas));
        			vPazymeti.add(new Coordinate(event.getX()-5,y1,kelintas));
        			vPazymeti.add(new Coordinate(x1,y1,kelintas));
        			vPazymeti.add(new Coordinate(x1,event.getY()-5,kelintas));
           			atgalStack.push(new StepInfo(new Coordinate(event.getX(),event.getY(),x1,y1,foreGroundColor,SOLID_SQUARE, kadras)));
           		}
           		if(x1 < event.getX() && y1 < event.getY())
           		{
           			vFormos.add(new Coordinate(x1,y1,event.getX(),event.getY(),foreGroundColor,SOLID_SQUARE,kadras));
           			kelintas = vFormos.size()-1;
           			pazymetas = kelintas;
           			vPazymeti.add(new Coordinate(x1-5,y1-5,kelintas));
           			vPazymeti.add(new Coordinate(x1-5,event.getY(),kelintas));
        			vPazymeti.add(new Coordinate(event.getX(),y1-5,kelintas));
        			vPazymeti.add(new Coordinate(event.getX(),event.getY(),kelintas));        			
           			atgalStack.push(new StepInfo(new Coordinate(x1,y1,event.getX(),event.getY(),foreGroundColor,SOLID_SQUARE, kadras)));
           		}
           		if(x1 > event.getX() && y1 < event.getY())
           		{
           			vFormos.add(new Coordinate(event.getX(),y1,x1,event.getY(),foreGroundColor,SOLID_SQUARE,kadras));
           			kelintas = vFormos.size()-1;
           			pazymetas = kelintas;
           			vPazymeti.add(new Coordinate(event.getX()-5,y1-5,kelintas));
           			vPazymeti.add(new Coordinate(event.getX()-5,event.getY(),kelintas));
        			vPazymeti.add(new Coordinate(x1,y1-5,kelintas));
        			vPazymeti.add(new Coordinate(x1,event.getY(),kelintas));        			
           			atgalStack.push(new StepInfo(new Coordinate(event.getX(),y1,x1,event.getY(),foreGroundColor,SOLID_SQUARE, kadras)));

           		}
           		if(x1 < event.getX() && y1 > event.getY())
           		{
           			vFormos.add(new Coordinate(x1,event.getY(),event.getX(),y1,foreGroundColor,SOLID_SQUARE,kadras));
           			kelintas = vFormos.size()-1;
           			pazymetas = kelintas;
           			vPazymeti.add(new Coordinate(x1-5,event.getY()-5,kelintas));
           			vPazymeti.add(new Coordinate(x1-5,y1,kelintas));
        			vPazymeti.add(new Coordinate(event.getX(),y1,kelintas));
        			vPazymeti.add(new Coordinate(event.getX(),event.getY()-5,kelintas));
           			atgalStack.push(new StepInfo(new Coordinate(x1,event.getY(),event.getX(),y1,foreGroundColor,SOLID_SQUARE, kadras)));

           		}
           	}
            else
            {
           		if(x1 > event.getX() && y1 > event.getY())
				{
           			vFormos.add(new Coordinate(event.getX(),event.getY(),x1,y1,foreGroundColor,SQUARE,kadras));
           			kelintas = vFormos.size()-1;
           			pazymetas = kelintas;
           			vPazymeti.add(new Coordinate(event.getX()-5,event.getY()-5,kelintas));
        			vPazymeti.add(new Coordinate(event.getX()-5,y1,kelintas));
        			vPazymeti.add(new Coordinate(x1,y1,kelintas));
        			vPazymeti.add(new Coordinate(x1,event.getY()-5,kelintas));
           			atgalStack.push(new StepInfo(new Coordinate(event.getX(),event.getY(),x1,y1,foreGroundColor,SQUARE, kadras)));

           		}
           		if(x1 < event.getX() && y1 < event.getY())
           		{
           			vFormos.add(new Coordinate(x1,y1,event.getX(),event.getY(),foreGroundColor,SQUARE,kadras));
           			kelintas = vFormos.size()-1;
           			pazymetas = kelintas;
           			vPazymeti.add(new Coordinate(x1-5,y1-5,kelintas));
           			vPazymeti.add(new Coordinate(x1-5,event.getY(),kelintas));
        			vPazymeti.add(new Coordinate(event.getX(),y1-5,kelintas));
        			vPazymeti.add(new Coordinate(event.getX(),event.getY(),kelintas));   
           			atgalStack.push(new StepInfo(new Coordinate(x1,y1,event.getX(),event.getY(),foreGroundColor,SQUARE, kadras)));

           		}
           		if(x1 > event.getX() && y1 < event.getY())
           		{
           			vFormos.add(new Coordinate(event.getX(),y1,x1,event.getY(),foreGroundColor,SQUARE,kadras));
           			kelintas = vFormos.size()-1;
           			pazymetas = kelintas;
           			vPazymeti.add(new Coordinate(event.getX()-5,y1-5,kelintas));
           			vPazymeti.add(new Coordinate(event.getX()-5,event.getY(),kelintas));
        			vPazymeti.add(new Coordinate(x1,y1-5,kelintas));
        			vPazymeti.add(new Coordinate(x1,event.getY(),kelintas)); 
           			atgalStack.push(new StepInfo(new Coordinate(event.getX(),y1,x1,event.getY(),foreGroundColor,SQUARE, kadras)));

           		}
           		if(x1 < event.getX() && y1 > event.getY())
           		{
           			vFormos.add(new Coordinate(x1,event.getY(),event.getX(),y1,foreGroundColor,SQUARE,kadras));
           			kelintas = vFormos.size()-1;
           			pazymetas = kelintas;
           			vPazymeti.add(new Coordinate(x1-5,event.getY()-5,kelintas));
           			vPazymeti.add(new Coordinate(x1-5,y1,kelintas));
        			vPazymeti.add(new Coordinate(event.getX(),y1,kelintas));
        			vPazymeti.add(new Coordinate(event.getX(),event.getY()-5,kelintas));
           			atgalStack.push(new StepInfo(new Coordinate(x1,event.getY(),event.getX(),y1,foreGroundColor,SQUARE, kadras)));

           		}
           	}
        }
        if (drawMode == this.OVAL) 
        {
        	vPazymeti.removeAllElements();
          	if(solidMode)
          	{          	
          		if(x1 > event.getX() && y1 > event.getY())
				{
           			vFormos.add(new Coordinate(event.getX(),event.getY(),x1,y1,foreGroundColor,SOLID_OVAL,kadras));
           			kelintas = vFormos.size()-1;
           			pazymetas = kelintas;
           			vPazymeti.add(new Coordinate(event.getX()-5,y1-((y1-event.getY())/2),kelintas));
        			vPazymeti.add(new Coordinate(x1,y1-((y1-event.getY())/2),kelintas));
        			vPazymeti.add(new Coordinate(x1-((x1-event.getX())/2),y1,kelintas));
        			vPazymeti.add(new Coordinate(x1-((x1-event.getX())/2),event.getY()-5,kelintas));
           			atgalStack.push(new StepInfo(new Coordinate(event.getX(),event.getY(),x1,y1,foreGroundColor,SOLID_OVAL, kadras)));

           		}
           		if(x1 < event.getX() && y1 < event.getY())
           		{
           			vFormos.add(new Coordinate(x1,y1,event.getX(),event.getY(),foreGroundColor,SOLID_OVAL,kadras));
           			kelintas = vFormos.size()-1;
           			pazymetas = kelintas;
           			vPazymeti.add(new Coordinate(x1-5,event.getY()-((event.getY()-y1)/2),kelintas));
           			vPazymeti.add(new Coordinate(event.getX(),event.getY()-((event.getY()-y1)/2),kelintas));
        			vPazymeti.add(new Coordinate(event.getX()-((event.getX()-x1)/2),event.getY(),kelintas));
        			vPazymeti.add(new Coordinate(event.getX()-((event.getX()-x1)/2),y1-5,kelintas));
           			atgalStack.push(new StepInfo(new Coordinate(x1,y1,event.getX(),event.getY(),foreGroundColor,SOLID_OVAL, kadras)));

           		}
           		if(x1 > event.getX() && y1 < event.getY())
           		{
           			vFormos.add(new Coordinate(event.getX(),y1,x1,event.getY(),foreGroundColor,SOLID_OVAL,kadras));
           			kelintas = vFormos.size()-1;
           			pazymetas = kelintas;
           			vPazymeti.add(new Coordinate(event.getX()-5,event.getY()-((event.getY()-y1)/2),kelintas));
           			vPazymeti.add(new Coordinate(x1,event.getY()-((event.getY()-y1)/2),kelintas));
        			vPazymeti.add(new Coordinate(x1-((x1-event.getX())/2),event.getY(),kelintas));
        			vPazymeti.add(new Coordinate(x1-((x1-event.getX())/2),y1-5,kelintas));           		
           			atgalStack.push(new StepInfo(new Coordinate(event.getX(),y1,x1,event.getY(),foreGroundColor,SOLID_OVAL, kadras)));

           		}
           		if(x1 < event.getX() && y1 > event.getY())
           		{
           			vFormos.add(new Coordinate(x1,event.getY(),event.getX(),y1,foreGroundColor,SOLID_OVAL,kadras));
           			kelintas = vFormos.size()-1;
           			pazymetas = kelintas;
           			vPazymeti.add(new Coordinate(x1-5,y1-((y1-event.getY())/2),kelintas));
           			vPazymeti.add(new Coordinate(event.getX(),y1-((y1-event.getY())/2),kelintas));
        			vPazymeti.add(new Coordinate(event.getX()-((event.getX()-x1)/2),y1,kelintas));
        			vPazymeti.add(new Coordinate(event.getX()-((event.getX()-x1)/2),event.getY()-5,kelintas));
           			atgalStack.push(new StepInfo(new Coordinate(x1,event.getY(),event.getX(),y1,foreGroundColor,SOLID_OVAL, kadras)));

           		}
           	}
           	else
           	{
           		if(x1 > event.getX() && y1 > event.getY())
				{
           			vFormos.add(new Coordinate(event.getX(),event.getY(),x1,y1,foreGroundColor,OVAL,kadras));
           			kelintas = vFormos.size()-1;
           			pazymetas = kelintas;
           			vPazymeti.add(new Coordinate(event.getX()-5,y1-((y1-event.getY())/2),kelintas));
        			vPazymeti.add(new Coordinate(x1,y1-((y1-event.getY())/2),kelintas));
        			vPazymeti.add(new Coordinate(x1-((x1-event.getX())/2),y1,kelintas));
        			vPazymeti.add(new Coordinate(x1-((x1-event.getX())/2),event.getY()-5,kelintas));
           			atgalStack.push(new StepInfo(new Coordinate(event.getX(),event.getY(),x1,y1,foreGroundColor,OVAL, kadras)));

           		}
           		if(x1 < event.getX() && y1 < event.getY())
           		{
           			vFormos.add(new Coordinate(x1,y1,event.getX(),event.getY(),foreGroundColor,OVAL,kadras));
           			kelintas = vFormos.size()-1;
           			pazymetas = kelintas;
           			vPazymeti.add(new Coordinate(x1-5,event.getY()-((event.getY()-y1)/2),kelintas));
           			vPazymeti.add(new Coordinate(event.getX(),event.getY()-((event.getY()-y1)/2),kelintas));
        			vPazymeti.add(new Coordinate(event.getX()-((event.getX()-x1)/2),event.getY(),kelintas));
        			vPazymeti.add(new Coordinate(event.getX()-((event.getX()-x1)/2),y1-5,kelintas));  
           			atgalStack.push(new StepInfo(new Coordinate(x1,y1,event.getX(),event.getY(),foreGroundColor,OVAL, kadras)));

           		}
           		if(x1 > event.getX() && y1 < event.getY())
           		{
           			vFormos.add(new Coordinate(event.getX(),y1,x1,event.getY(),foreGroundColor,OVAL,kadras));
           			kelintas = vFormos.size()-1;
           			pazymetas = kelintas;
           			vPazymeti.add(new Coordinate(event.getX()-5,event.getY()-((event.getY()-y1)/2),kelintas));
           			vPazymeti.add(new Coordinate(x1,event.getY()-((event.getY()-y1)/2),kelintas));
        			vPazymeti.add(new Coordinate(x1-((x1-event.getX())/2),event.getY(),kelintas));
        			vPazymeti.add(new Coordinate(x1-((x1-event.getX())/2),y1-5,kelintas)); 
           			atgalStack.push(new StepInfo(new Coordinate(event.getX(),y1,x1,event.getY(),foreGroundColor,OVAL, kadras)));

           		}
           		if(x1 < event.getX() && y1 > event.getY())
           		{
           			vFormos.add(new Coordinate(x1,event.getY(),event.getX(),y1,foreGroundColor,OVAL,kadras));
           			kelintas = vFormos.size()-1;
           			pazymetas = kelintas;
           			vPazymeti.add(new Coordinate(x1-5,y1-((y1-event.getY())/2),kelintas));
           			vPazymeti.add(new Coordinate(event.getX(),y1-((y1-event.getY())/2),kelintas));
        			vPazymeti.add(new Coordinate(event.getX()-((event.getX()-x1)/2),y1,kelintas));
        			vPazymeti.add(new Coordinate(event.getX()-((event.getX()-x1)/2),event.getY()-5,kelintas));
           			atgalStack.push(new StepInfo(new Coordinate(x1,event.getY(),event.getX(),y1,foreGroundColor,OVAL, kadras)));

           		}
           	}
        }
        if (drawMode == this.POLYGON || drawMode == this.SOLID_POLYGON) 
        {
        	kelintas = vFormos.size()-1;
			vPazymeti.add(new Coordinate(event.getX(),event.getY(),kelintas));
        	xPolygon.add(new Integer(event.getX()));
        	yPolygon.add(new Integer(event.getY()));
        	polygonBuffer = true;
        	repaint();       	      
        }
        
        if ((drawMode == BEZIER_KREIVE)||(drawMode==SOLID_BEZIER_KREIVE)) 
		{		
			kelintas = vFormos.size()-1;
			vPazymeti.add(new Coordinate(event.getX(),event.getY(),kelintas));
			xBezier.add(new Integer(event.getX()));
        	yBezier.add(new Integer(event.getY()));
        	bezierBuffer = true;			
			repaint();
		}
		
		if (drawMode == this.FREE_HAND) 
        {           
            xHands.add(new Integer(event.getX()));
            yHands.add(new Integer(event.getY()));
			vFormos.add(new Coordinate(xHands, yHands, foreGroundColor,FREE_HAND,kadras));
           	atgalStack.push(new StepInfo(new Coordinate(xHands, yHands, foreGroundColor,FREE_HAND,kadras)));
			xHands.removeAllElements();
			yHands.removeAllElements();
			vHands.removeAllElements();
			repaint();
         }
        
        x1=linex1=x2=linex2=0;
        y1=liney1=y2=liney2=0;
        
        if (kaDarom==PAZYMEJIMAS2)
		{
			repaint();	
		}
        
	}
/*----------------------------------------------------------------------------*/
	public void mouseEntered(MouseEvent event)
	{
		if (kaDarom==PIESIMAS)
		{
			setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		}
		if (kaDarom==PAZYMEJIMAS2)
		{
			setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		}
		if (kaDarom==PAZYMEJIMAS1)
		{
			setCursor(new Cursor(Cursor.HAND_CURSOR));	
		}
	}
/*----------------------------------------------------------------------------*/
	public void mouseExited(MouseEvent event)
	{
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
/*----------------------------------------------------------------------------*/
	public void mouseDragged(MouseEvent event)
	{
        x2 = event.getX();
        y2 = event.getY();

        if (drawMode == this.FREE_HAND) 
        {
            linex1 = linex2;
            liney1 = liney2;           
            linex2 = x2;
            liney2 = y2;
            
            xHands.add(new Integer(event.getX()));
            yHands.add(new Integer(event.getY()));
            
            vHands.add(new Coordinate(linex1,liney1,linex2,liney2,foreGroundColor,FREE_HAND, kadras));

         }
         repaint();
	}
	
/*----------------------------------------------------------------------------*/
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		redrawVectorBuffer(g);
		Rectangle rr = getBounds();
		getCanvasPlotis(rr.width);
		getCanvasAukstis(rr.height);
		if (yraGroteles==true)   
		{  	  	
	  		Rectangle r = getBounds();
			int mast=25;
			int plotis = r.height /mast;
			int aukstis = r.width / mast;			
			Font f = new Font("monospaced", Font.BOLD, 10);
			g.setFont(f);
			for (int i = 0; i <= plotis; i++) 
			{
				g.setColor(Color.LIGHT_GRAY);
	    		g.drawLine(0,i * mast, r.width,i * mast);
	    		g.setColor(Color.red);
	    		g.drawLine(0,i * mast, 5,i * mast);
	    	
	    		if (i != 0){
	    			g.drawString(""+i, 10, i * mast+5);	    			
	    		}
	    		else {
	    			g.drawString("0", 10, i * mast+10);
	    		}
			}
			for (int i = 0; i <= aukstis; i++) 
			{
				g.setColor(Color.LIGHT_GRAY);			
	    		g.drawLine(i * mast, 0,i * mast, r.height);
	    		g.setColor(Color.red);
	    		g.drawLine(i * mast, 0,i * mast, 5);
	    	
	    		if (i != 0){
	    			g.drawString(""+i, i * mast, 15);	    			
	    		}
			}
			g.setColor(Color.red);
			g.drawLine(0, 0, r.width, 0);
			g.drawLine(0, 0, 0, r.height);
		
      		redrawVectorBuffer(g);
      	}
      	g.setColor(foreGroundColor);      	
      	
      	//*****************************************************************************      	    	
      	
      	if (drawMode == LINE) 
      	{        	        	
        	if ((x1!=0)&&(x2!=0)&&(y1!=0)&&(y2!=0))
        	{
        		g.drawLine(x1,y1,x2,y2);
        		g.setColor(Color.RED);
        		g.fillRect(x1,y1,5,5);
        		g.fillRect(x2,y2,5,5);
        		g.setColor(Color.BLACK);
        		g.drawRect(x1,y1,5,5);
        		g.drawRect(x2,y2,5,5);        	
        	}
      	}
      	if (drawMode == OVAL) 
      	{
      	 	if(solidMode)
      	 	{         		
         		if(x1 > x2 && y1 > y2)
         		{
         			g.fillOval(x2,y2,x1-x2,y1-y2);
         			g.setColor(Color.RED);
        			g.fillRect(x2-5,y1-((y1-y2)/2),5,5);
        			g.fillRect(x1,y1-((y1-y2)/2),5,5);
        			g.fillRect(x1-((x1-x2)/2),y1,5,5);
        			g.fillRect(x1-((x1-x2)/2),y2-5,5,5);
        			g.setColor(Color.BLACK);
        			g.drawRect(x2-5,y1-((y1-y2)/2),5,5);
        			g.drawRect(x1,y1-((y1-y2)/2),5,5); 
        			g.drawRect(x1-((x1-x2)/2),y1,5,5);
        			g.drawRect(x1-((x1-x2)/2),y2-5,5,5);
        		}
         		if(x1 < x2 && y1 < y2)
         		{
         			g.fillOval(x1,y1,x2-x1,y2-y1);
         			g.setColor(Color.RED);
        			g.fillRect(x1-5,y2-((y2-y1)/2),5,5);
        			g.fillRect(x2,y2-((y2-y1)/2),5,5);
        			g.fillRect(x2-((x2-x1)/2),y2,5,5);
        			g.fillRect(x2-((x2-x1)/2),y1-5,5,5);
        			g.setColor(Color.BLACK);
        			g.drawRect(x1-5,y2-((y2-y1)/2),5,5);
        			g.drawRect(x2,y2-((y2-y1)/2),5,5); 
        			g.drawRect(x2-((x2-x1)/2),y2,5,5);
        			g.drawRect(x2-((x2-x1)/2),y1-5,5,5);
         		}
         		if(x1 > x2 && y1 < y2)
         		{
         			g.fillOval(x2,y1,x1-x2,y2-y1);
         			g.setColor(Color.RED);
        			g.fillRect(x2-5,y2-((y2-y1)/2),5,5);
        			g.fillRect(x1,y2-((y2-y1)/2),5,5);
        			g.fillRect(x1-((x1-x2)/2),y2,5,5);
        			g.fillRect(x1-((x1-x2)/2),y1-5,5,5);
        			g.setColor(Color.BLACK);
        			g.drawRect(x2-5,y2-((y2-y1)/2),5,5);
        			g.drawRect(x1,y2-((y2-y1)/2),5,5); 
        			g.drawRect(x1-((x1-x2)/2),y2,5,5);
        			g.drawRect(x1-((x1-x2)/2),y1-5,5,5);	
         		}
         		if(x1 < x2 && y1 > y2)
         		{
         			g.fillOval(x1,y2,x2-x1,y1-y2); 
         			g.setColor(Color.RED);
        			g.fillRect(x1-5,y1-((y1-y2)/2),5,5);
        			g.fillRect(x2,y1-((y1-y2)/2),5,5);
        			g.fillRect(x2-((x2-x1)/2),y1,5,5);
        			g.fillRect(x2-((x2-x1)/2),y2-5,5,5);
        			g.setColor(Color.BLACK);
        			g.drawRect(x1-5,y1-((y1-y2)/2),5,5);
        			g.drawRect(x2,y1-((y1-y2)/2),5,5); 
        			g.drawRect(x2-((x2-x1)/2),y1,5,5);
        			g.drawRect(x2-((x2-x1)/2),y2-5,5,5);
         		}        			     
      	 	}
         	else
         	{
         		if(x1 > x2 && y1 > y2)
         		{
         			g.drawOval(x2,y2,x1-x2,y1-y2);
         			g.setColor(Color.RED);
        			g.fillRect(x2-5,y1-((y1-y2)/2),5,5);
        			g.fillRect(x1,y1-((y1-y2)/2),5,5);
        			g.fillRect(x1-((x1-x2)/2),y1,5,5);
        			g.fillRect(x1-((x1-x2)/2),y2-5,5,5);
        			g.setColor(Color.BLACK);
        			g.drawRect(x2-5,y1-((y1-y2)/2),5,5);
        			g.drawRect(x1,y1-((y1-y2)/2),5,5); 
        			g.drawRect(x1-((x1-x2)/2),y1,5,5);
        			g.drawRect(x1-((x1-x2)/2),y2-5,5,5);
        		}
         		if(x1 < x2 && y1 < y2)
         		{
         			g.drawOval(x1,y1,x2-x1,y2-y1);
         			g.setColor(Color.RED);
        			g.fillRect(x1-5,y2-((y2-y1)/2),5,5);
        			g.fillRect(x2,y2-((y2-y1)/2),5,5);
        			g.fillRect(x2-((x2-x1)/2),y2,5,5);
        			g.fillRect(x2-((x2-x1)/2),y1-5,5,5);
        			g.setColor(Color.BLACK);
        			g.drawRect(x1-5,y2-((y2-y1)/2),5,5);
        			g.drawRect(x2,y2-((y2-y1)/2),5,5); 
        			g.drawRect(x2-((x2-x1)/2),y2,5,5);
        			g.drawRect(x2-((x2-x1)/2),y1-5,5,5);
        		}
         		if(x1 > x2 && y1 < y2)
         		{
         			g.drawOval(x2,y1,x1-x2,y2-y1);
         			g.setColor(Color.RED);
        			g.fillRect(x2-5,y2-((y2-y1)/2),5,5);
        			g.fillRect(x1,y2-((y2-y1)/2),5,5);
        			g.fillRect(x1-((x1-x2)/2),y2,5,5);
        			g.fillRect(x1-((x1-x2)/2),y1-5,5,5);
        			g.setColor(Color.BLACK);
        			g.drawRect(x2-5,y2-((y2-y1)/2),5,5);
        			g.drawRect(x1,y2-((y2-y1)/2),5,5); 
        			g.drawRect(x1-((x1-x2)/2),y2,5,5);
        			g.drawRect(x1-((x1-x2)/2),y1-5,5,5);
        		}
         		if(x1 < x2 && y1 > y2)
         		{
         			g.drawOval(x1,y2,x2-x1,y1-y2);
         			g.setColor(Color.RED);
        			g.fillRect(x1-5,y1-((y1-y2)/2),5,5);
        			g.fillRect(x2,y1-((y1-y2)/2),5,5);
        			g.fillRect(x2-((x2-x1)/2),y1,5,5);
        			g.fillRect(x2-((x2-x1)/2),y2-5,5,5);
        			g.setColor(Color.BLACK);
        			g.drawRect(x1-5,y1-((y1-y2)/2),5,5);
        			g.drawRect(x2,y1-((y1-y2)/2),5,5); 
        			g.drawRect(x2-((x2-x1)/2),y1,5,5);
        			g.drawRect(x2-((x2-x1)/2),y2-5,5,5);
        		}         		
         	}
      	}
      	
      
      	if (drawMode == SQUARE) 
      	{
      	 	if(solidMode)
      	 	{
      	 		if(x1 > x2 && y1 > y2)
      	 		{
         			g.fillRect(x2,y2,x1-x2,y1-y2);
         			g.setColor(Color.RED);
        			g.fillRect(x2-5,y2-5,5,5);
        			g.fillRect(x2-5,y1,5,5);
        			g.fillRect(x1,y1,5,5);
        			g.fillRect(x1,y2-5,5,5);
        			g.setColor(Color.BLACK);
        			g.drawRect(x2-5,y2-5,5,5);
        			g.drawRect(x2-5,y1,5,5); 
        			g.drawRect(x1,y1,5,5); 
        			g.drawRect(x1,y2-5,5,5); 
        		}
         		if(x1 < x2 && y1 < y2)
         		{
         			g.fillRect(x1,y1,x2-x1,y2-y1);
         			g.setColor(Color.RED);
        			g.fillRect(x1-5,y1-5,5,5);
        			g.fillRect(x1-5,y2,5,5);
        			g.fillRect(x2,y1-5,5,5);
        			g.fillRect(x2,y2,5,5);
        			g.setColor(Color.BLACK);
        			g.drawRect(x1-5,y1-5,5,5);
        			g.drawRect(x1-5,y2,5,5); 
        			g.drawRect(x2,y1-5,5,5); 
        			g.drawRect(x2,y2,5,5); 
         		}	
         		if(x1 > x2 && y1 < y2)
         		{
         			g.fillRect(x2,y1,x1-x2,y2-y1);
         			g.setColor(Color.RED);
        			g.fillRect(x2-5,y1-5,5,5);
        			g.fillRect(x2-5,y2,5,5);
        			g.fillRect(x1,y1-5,5,5);
        			g.fillRect(x1,y2,5,5);
        			g.setColor(Color.BLACK);
        			g.drawRect(x2-5,y1-5,5,5);
        			g.drawRect(x2-5,y2,5,5); 
        			g.drawRect(x1,y1-5,5,5); 
        			g.drawRect(x1,y2,5,5); 
        		}
         		if(x1 < x2 && y1 > y2)
         		{
         			g.fillRect(x1,y2,x2-x1,y1-y2);
         			g.setColor(Color.RED);
        			g.fillRect(x1-5,y2-5,5,5);
        			g.fillRect(x1-5,y1,5,5);
        			g.fillRect(x2,y1,5,5);
        			g.fillRect(x2,y2-5,5,5);
        			g.setColor(Color.BLACK);
        			g.drawRect(x1-5,y2-5,5,5);
        			g.drawRect(x1-5,y1,5,5); 
        			g.drawRect(x2,y1,5,5); 
        			g.drawRect(x2,y2-5,5,5); 
        		}
  	 	
      	 	}
         	else
         	{
         		if(x1 > x2 && y1 > y2)
         		{
         			g.drawRect(x2,y2,x1-x2,y1-y2);
         			g.setColor(Color.RED);
        			g.fillRect(x2-5,y2-5,5,5);
        			g.fillRect(x2-5,y1,5,5);
        			g.fillRect(x1,y1,5,5);
        			g.fillRect(x1,y2-5,5,5);
        			g.setColor(Color.BLACK);
        			g.drawRect(x2-5,y2-5,5,5);
        			g.drawRect(x2-5,y1,5,5); 
        			g.drawRect(x1,y1,5,5); 
        			g.drawRect(x1,y2-5,5,5);
        		}
         		if(x1 < x2 && y1 < y2)
         		{
         			g.drawRect(x1,y1,x2-x1,y2-y1);
         			g.setColor(Color.RED);
        			g.fillRect(x1-5,y1-5,5,5);
        			g.fillRect(x1-5,y2,5,5);
        			g.fillRect(x2,y1-5,5,5);
        			g.fillRect(x2,y2,5,5);
        			g.setColor(Color.BLACK);
        			g.drawRect(x1-5,y1-5,5,5);
        			g.drawRect(x1-5,y2,5,5); 
        			g.drawRect(x2,y1-5,5,5); 
        			g.drawRect(x2,y2,5,5);
        		}
         		if(x1 > x2 && y1 < y2)
         		{
         			g.drawRect(x2,y1,x1-x2,y2-y1);
         			g.setColor(Color.RED);
        			g.fillRect(x2-5,y1-5,5,5);
        			g.fillRect(x2-5,y2,5,5);
        			g.fillRect(x1,y1-5,5,5);
        			g.fillRect(x1,y2,5,5);
        			g.setColor(Color.BLACK);
        			g.drawRect(x2-5,y1-5,5,5);
        			g.drawRect(x2-5,y2,5,5); 
        			g.drawRect(x1,y1-5,5,5); 
        			g.drawRect(x1,y2,5,5); 
        		}
         		if(x1 < x2 && y1 > y2)
         		{
         			g.drawRect(x1,y2,x2-x1,y1-y2);
         			g.setColor(Color.RED);
        			g.fillRect(x1-5,y2-5,5,5);
        			g.fillRect(x1-5,y1,5,5);
        			g.fillRect(x2,y1,5,5);
        			g.fillRect(x2,y2-5,5,5);
        			g.setColor(Color.BLACK);
        			g.drawRect(x1-5,y2-5,5,5);
        			g.drawRect(x1-5,y1,5,5); 
        			g.drawRect(x2,y1,5,5); 
        			g.drawRect(x2,y2-5,5,5); 
        		}
         	}
      	}
      	if (drawMode == POLYGON || drawMode == SOLID_POLYGON)
      	{
      		int xPos[] = new int[xPolygon.size()];
      	 	int yPos[] = new int[yPolygon.size()];
      	 
      	 	for(int count=0;count<xPos.length;count++)
      	 	{
      	 		xPos[count] = ((Integer)(xPolygon.elementAt(count))).intValue();
      	 		yPos[count] = ((Integer)(yPolygon.elementAt(count))).intValue();
      	 		
      	 		g.setColor(Color.RED);
        		g.fillRect(xPos[count],yPos[count],5,5);        		
        		g.setColor(Color.BLACK);
        		g.drawRect(xPos[count],yPos[count],5,5);
      	 	}
      	 	g.setColor(foreGroundColor);
      	 	g.drawPolyline(xPos,yPos,xPos.length);
      	 	polygonBuffer = true;
	  	}
	  	
	  	if ((drawMode == BEZIER_KREIVE)||(drawMode == SOLID_BEZIER_KREIVE)) 
      	{         	
        		
        	int xBez[] = new int[xBezier.size()];
      	 	int yBez[] = new int[yBezier.size()];
      	 	
      	 	Vector xBezierTaskai = new Vector();
      	 	Vector yBezierTaskai = new Vector();
      	 	int c1x=0;
        	int c1y=0;
        	int c23x=0;
        	int c23y=0;
        	int c4x=0;
        	int c4y=0;
        	int xx1=0;
        	int yy1=0;
        	int nuo=0;
        	int Bdydis=0;
      	 	for(int count=0;count<xBez.length;count++)
      	 	{
      	 		xBez[count] = ((Integer)(xBezier.elementAt(count))).intValue();
      	 		yBez[count] = ((Integer)(yBezier.elementAt(count))).intValue();
      	 		      	 		        			        			
         		g.setColor(Color.RED);
        		g.fillRect(xBez[count],yBez[count],5,5);        		
        		g.setColor(Color.BLACK);
        		g.drawRect(xBez[count],yBez[count],5,5);
        		if (count==1)
        		{
        			c1x=xBez[count-1];
        			c1y=yBez[count-1];
        			c4x=xBez[count];
        			c4y=yBez[count];
        			xx1=0;
        			yy1=0;
        			for (double ii=1;ii>=0;ii=ii-0.01) 
        			{  
        				xx1=(int)((double)((double)c4x+(double)(ii*(double)(c1x-c4x)))); 
        				yy1=(int)((double)((double)c4y+(double)(ii*(double)(c1y-c4y))));       				     			
        				xBezierTaskai.add(new Integer(xx1));
        				yBezierTaskai.add(new Integer(yy1));
        			}
        		} 
        		if (count>=2)
        		{
        			c1x=xBez[count-2];
        			c1y=yBez[count-2];
        			c23x=xBez[count-1];
        			c23y=yBez[count-1];
        			c4x=xBez[count];
        			c4y=yBez[count];
        			xx1=0;
        			yy1=0;
        			if (count-1==1)
        			{
        				nuo=50;
        			}
        			else
        			{
        				nuo=25;
        			}
        			Bdydis=xBezierTaskai.size();
        			for (double ii=0.75;ii>=0;ii=ii-0.01)
        			{
        				xx1=(int)((double)((double)(c1x*(ii*ii*ii))+
        					(double)(c23x*(3*ii*ii*(double)(1-ii)))+
        					(double)(c23x*(3*ii*(double)(1-ii)*(double)(1-ii)))+
        					(double)(c4x*((double)(1-ii)*(double)(1-ii)*(double)(1-ii)))));	
        					
        				yy1=(int)((double)((double)(c1y*(ii*ii*ii))+
        					(double)(c23y*(3*ii*ii*(double)(1-ii)))+
        					(double)(c23y*(3*ii*(double)(1-ii)*(double)(1-ii)))+
        					(double)(c4y*((double)(1-ii)*(double)(1-ii)*(double)(1-ii)))));
        				if (nuo>0)
        				{
        					xBezierTaskai.set(Bdydis-nuo, new Integer(xx1));
        					yBezierTaskai.set(Bdydis-nuo, new Integer(yy1));
        					nuo--;
        				}
        				else
        				{	
        					xBezierTaskai.add(new Integer(xx1));
        					yBezierTaskai.add(new Integer(yy1));
        				}
        			}		
        		}
        		
        	
      	 	}
      	 	g.setColor(foreGroundColor);
      	 	int xBezierM[] = new int[xBezierTaskai.size()];
      	 	int yBezierM[] = new int[yBezierTaskai.size()];
      	 	
      	 	for (int count=0;count<xBezierM.length;count++)
      	 	{
      	 		xBezierM[count] = ((Integer)(xBezierTaskai.elementAt(count))).intValue();
      	 		yBezierM[count] = ((Integer)(yBezierTaskai.elementAt(count))).intValue();
      	 	}      	 	
      	 	g.drawPolyline(xBezierM,yBezierM,xBezierM.length);
      	 	
      	 	g.setColor(Color.BLUE);
      	 	g.drawPolyline(xBez,yBez,xBez.length);     	 	
      	 	bezierBuffer = true;         
      	}
      	
      	if (drawMode == FREE_HAND) 
      	{
         	g.drawLine(linex1,liney1,linex2,liney2);
      	}
      	
      	if (kaDarom == PAZYMEJIMAS2) 
      	{
      	 	if(x1 > x2 && y1 > y2)
         		g.drawRect(x2,y2,x1-x2,y1-y2);
         	if(x1 < x2 && y1 < y2)
         		g.drawRect(x1,y1,x2-x1,y2-y1);
         	if(x1 > x2 && y1 < y2)
         		g.drawRect(x2,y1,x1-x2,y2-y1);
         	if(x1 < x2 && y1 > y2)
         		g.drawRect(x1,y2,x2-x1,y1-y2);  
         	//repaint();	 	
      	}
	}
	
/*-----------------------------------------------------------------------------*/
	private class Coordinate implements Serializable
	{
		private int x1,y1,x2,y2;
		private Color foreColor;
		private Vector xPoly, yPoly;
		private int tipas, kadras;
		private int pazymetas;
		
		public Coordinate(int inx1,int iny1,int inx2, int iny2, Color color, int inTipas, int inKadras) 
		{
        	x1 = inx1;
         	y1 = iny1;
         	x2 = inx2;
         	y2 = iny2;
         	foreColor = color;
         	tipas = inTipas;  
         	kadras = inKadras;       	
      	}
      	public Coordinate(Vector inXPolygon, Vector inYPolygon, Color color, int inTipas, int inKadras)
      	{
      		xPoly = (Vector)inXPolygon.clone();
      		yPoly = (Vector)inYPolygon.clone();
      		foreColor = color;
      		tipas = inTipas;
      		kadras = inKadras;
      	}
      	public Coordinate(int inx1,int iny1,int inPazymetas) 
		{
        	x1 = inx1;
         	y1 = iny1;         	
         	pazymetas = inPazymetas;       	
      	}
      
      	public int fPazymetas()
      	{
      		return pazymetas;	
      	}
      	public int fKadras()
      	{
      		return kadras;	
      	}
      	public int fTipas()
      	{
      		return tipas;	
      	}
      	public Color colour()
      	{
        	return foreColor;
      	}
      	public int getX1 () 
      	{
        	return x1;
      	}
      	public int getX2 () 
      	{
        	return x2;
      	}
      	public int getY1 () 
      	{
        	return y1;
      	}
      	public int getY2 () 
      	{
        	return y2;
      	}
      	public Vector getXPolygon()
      	{
      		return xPoly;
      	}
      	public Vector getYPolygon()
      	{
      		return yPoly;
      	}
	}
/*----------------------------------------------------------------------------*/	
	private class StepInfo implements Serializable
	{		
		private Coordinate stepCoordinate;
		
		public StepInfo(Coordinate inStepCoordinate)
		{			
			stepCoordinate = inStepCoordinate;
		}
	
		public Coordinate getStepCoordinate()
		{
			return stepCoordinate;
		}
	}
/*----------------------------------------------------------------------------*/
	public void setDrawMode(int mode)
	{
		drawMode = mode;
		kaDarom = PIESIMAS;
		vPazymeti.removeAllElements();
		pazymetas=-1;
		BezierTaskas=0;
	}
	public int getDrawMode()
	{	
		return drawMode;	
	}
/*----------------------------------------------------------------------------*/	
	public void setPazymejimas(int inKaDarom)
	{		
		if (inKaDarom==1)
		{
			kaDarom = PAZYMEJIMAS1;
		}
		else
		{
			kaDarom = PAZYMEJIMAS2;	
		}
		drawMode=0;	
		vPazymeti.removeAllElements();
		pazymetas=-1;
		repaint();
	}
/*----------------------------------------------------------------------------*/	
	public void setGroteles(Boolean inGroteles)
	{
		yraGroteles = inGroteles.booleanValue();
		repaint();	
	}
	public Boolean getGroteles()
	{
		return Boolean.valueOf(yraGroteles);	
	}

/*----------------------------------------------------------------------------*/
	public void setAtsTaskas(double inAtsX, double inAtsY) 
	{
		AtsX=inAtsX;
		AtsY=inAtsY;		
	}
	
	public void setTransformSeka1(double inSx,double inSy,double inA,double inTx,double inTy) 
	{
		Sx=inSx;
		Sy=inSy;
		A=inA;
		Tx=inTx;
		Ty=inTy;
		uzpildModas=TRANSFORM;
		uzpildomKadrus();
		vPazymeti.removeAllElements();
		pazymetas=-1;
		drawMode = -1;
		repaint();
		
	}
	
	public void setTransformSeka2(double inSx,double inSy,double inA,double inTx,double inTy,
								 int inIkiKadro)
	{		
		Sx=inSx;
		Sy=inSy;
		A=inA;
		Tx=inTx;
		Ty=inTy;
		ikiKadro=inIkiKadro;
		uzpildModas=JUDEJIMAS;
		uzpildomKadrus();
		drawMode = -1;
	}
/*----------------------------------------------------------------------------*/
	public void setSolidMode(Boolean inSolidMode)
	{
		solidMode = inSolidMode.booleanValue();
		repaint();
	}
	public Boolean getSolidMode()
	{
		return Boolean.valueOf(solidMode);
	}
/*----------------------------------------------------------------------------*/
	public void setForeGroundColor(Color inputColor)
	{
		foreGroundColor = inputColor;
	}
	public Color getForeGroundColor()
	{
		return foreGroundColor;
	}
/*----------------------------------------------------------------------------*/
	public void setBackGroundColor(Color inputColor)
	{
		backGroundColor = inputColor;
		this.setBackground(backGroundColor);
	}
	public Color getBackGroundColor()
	{
		return backGroundColor;
	}
/*----------------------------------------------------------------------------*/
	public void undo()
	{
		StepInfo tempInfo;
		
		vPazymeti.removeAllElements();
		pazymetas=-1;
		drawMode = -1;
		if(atgalStack.isEmpty())
			JOptionPane.showMessageDialog(null, "Nera þingsniø griðti atgal!","Piesimas", JOptionPane.INFORMATION_MESSAGE);
		else
		{
			tempInfo = (StepInfo)atgalStack.pop();
			vFormos.remove(vFormos.size()-1);			
			pirmynStack.push(tempInfo);
		}
		repaint();
	}
/*----------------------------------------------------------------------------*/
	public void redo()
	{
		StepInfo tempInfo;
		
		vPazymeti.removeAllElements();
		pazymetas=-1;
		drawMode = -1;
		if(pirmynStack.isEmpty())
			JOptionPane.showMessageDialog(null,"Nera þingniø eiti pirmyn!","Pieðimas",JOptionPane.INFORMATION_MESSAGE);
		else
		{
			tempInfo = (StepInfo)pirmynStack.pop();	
			vFormos.add(tempInfo.getStepCoordinate());	
			atgalStack.push(tempInfo);
		}
		repaint();
	}

/*----------------------------------------------------------------------------*/
	public void clearCanvas()
	{
		vFormos.removeAllElements();
		
		xPolygon.removeAllElements();
		yPolygon.removeAllElements();
		xBezier.removeAllElements();
		yBezier.removeAllElements();
		vPazymeti.removeAllElements();
		vHands.removeAllElements();	
		polygonBuffer = false;
		bezierBuffer = false;
		
		atgalStack.clear();
		pirmynStack.clear();
		
		repaint();
	}


/*----------------------------------------------------------------------------*/
	public boolean isExistPolygonBuffer()
	{
		return polygonBuffer;
	} 
	
	public boolean isExistBezierBuffer()
	{
		return bezierBuffer;
	} 
/*----------------------------------------------------------------------------*/
	public boolean arAtidare()
	{
		return atidare;	
	}
	
	public String yraKelias()
	{
		return kelias;	
	}
/*----------------------------------------------------------------------------*/	
	public void flushPolygonBuffer()
	{
		if(!solidMode)
		{
			vFormos.add(new Coordinate(xPolygon, yPolygon, foreGroundColor,POLYGON,kadras));
			atgalStack.push(new StepInfo(new Coordinate(xPolygon, yPolygon, foreGroundColor,POLYGON,kadras)));
		}
		else
		{
			vFormos.add(new Coordinate(xPolygon, yPolygon, foreGroundColor,SOLID_POLYGON,kadras));
			atgalStack.push(new StepInfo(new Coordinate(xPolygon, yPolygon, foreGroundColor,SOLID_POLYGON,kadras)));
		}
		
		pazymetas = vFormos.size()-1;
		xPolygon.removeAllElements();
		yPolygon.removeAllElements();
			
		polygonBuffer = false;
		repaint();
	}
	
	public void flushBezierBuffer()
	{
		if(!solidMode)
		{
			vFormos.add(new Coordinate(xBezier, yBezier, foreGroundColor,BEZIER_KREIVE,kadras));
			atgalStack.push(new StepInfo(new Coordinate(xBezier, yBezier, foreGroundColor,BEZIER_KREIVE,kadras)));
		}
		else
		{
			vFormos.add(new Coordinate(xBezier, yBezier, foreGroundColor,SOLID_BEZIER_KREIVE,kadras));
			atgalStack.push(new StepInfo(new Coordinate(xBezier, yBezier, foreGroundColor,SOLID_BEZIER_KREIVE,kadras)));
		}
		
		pazymetas = vFormos.size()-1;
		xBezier.removeAllElements();
		yBezier.removeAllElements();
			
		bezierBuffer = false;
		repaint();
	}
/*----------------------------------------------------------------------------*/
	public void getCanvasPlotis(int inPlotis)
	{
		CanvasPlotis = inPlotis;	
	}	
	
	public void getCanvasAukstis(int inAukstis)
	{
		CanvasAukstis = inAukstis;	
	}	
/*----------------------------------------------------------------------------*/
	public void setKadras(int inKadras)
	{
		
		if ((drawMode==POLYGON)||(drawMode==SOLID_POLYGON))
		{
			if(isExistPolygonBuffer()!= false)
			{
				flushPolygonBuffer();
			}		
		}
		
		if ((drawMode==BEZIER_KREIVE)||(drawMode==SOLID_BEZIER_KREIVE))
		{
			if(isExistBezierBuffer()!= false)
			{
				flushBezierBuffer();
			}	
		}
		vPazymeti.removeAllElements();
		pazymetas=-1;
		kadras = inKadras;
		repaint();		
	}
	public int getKadras()
	{
		return kadras;
	}
/*----------------------------------------------------------------------------*/
	public void setBaigiamPiestBP()
	{
		
		if ((drawMode==POLYGON)||(drawMode==SOLID_POLYGON))
		{
			if(isExistPolygonBuffer()!= false)
			{
				flushPolygonBuffer();
				repaint();
			}		
		}
		
		if ((drawMode==BEZIER_KREIVE)||(drawMode==SOLID_BEZIER_KREIVE))
		{
			if(isExistBezierBuffer()!= false)
			{
				flushBezierBuffer();
				repaint();	
			}	
		}
			
	}
/*----------------------------------------------------------------------------*/	
	public void setFileTuscias()
	{
		fileName = null;	
	}

/*----------------------------------------------------------------------------*/	
	public void SaveCanvasToFile()
	{
		vPazymeti.removeAllElements();
		pazymetas=-1;
		int pKadras = kadras;
		
		if(fileName != null)
		{
			vFile.removeAllElements();
			vFile.addElement(vFormos);
			
			try
			{
				FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(vFile);
			}catch(Exception exp){}
			
			for (int k=0;k<60;k++)
			{
				RenderedImage rendImage = myCreateImage(k);
				try 
				{	
					if (k<10)
					{			
						File file = new File(fileName.toString()+"0"+k+".jpg"); 
						ImageIO.write(rendImage, "jpg", file);
					}
					else
					{
						File file = new File(fileName.toString()+""+k+".jpg");
						ImageIO.write(rendImage, "jpg", file);
					}			
					
    			}
    			catch (IOException e) {}
			}
		}
		else
		{
			SaveAsCanvasToFile();
		}
		repaint();
		kadras = pKadras;
	}

/*----------------------------------------------------------------------------*/
	public void SaveAsCanvasToFile()
	{
			
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);	
		int result = fileChooser.showSaveDialog(null);
	
		if(result == JFileChooser.CANCEL_OPTION) return;
			
		fileName = fileChooser.getSelectedFile();

		if(fileName == null || fileName.getName().equals(""))
			JOptionPane.showMessageDialog(null,"Neteisingai ávestas failo pavadinimas","Pieðimas",JOptionPane.ERROR_MESSAGE);
		else
		{
			vFile.removeAllElements();			
			vFile.addElement(vFormos);	
						
			try
			{
				FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(vFile);
			}catch(Exception exp){}
			
			for (int k=0;k<60;k++)
			{
				RenderedImage rendImage = myCreateImage(k);
				try 
				{	
					if (k<10)
					{			
						File file = new File(fileName.toString()+"0"+k+".jpg"); 
						ImageIO.write(rendImage, "jpg", file);
					}
					else
					{
						File file = new File(fileName.toString()+""+k+".jpg");
						ImageIO.write(rendImage, "jpg", file);
					}
					
    			}
    			catch (IOException e) {}
			}
			
		}		    
		repaint();
	}
/*----------------------------------------------------------------------------*/
	public void OpenCanvasFile()
	{
		JFileChooser fileChooser;
		
		if (fileName!=null)
		{
			fileChooser = new JFileChooser(fileName.getAbsolutePath());
			//System.out.println(fileName.getPath());
		}
		else
		{
			fileChooser = new JFileChooser();	
		}
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
		int result = fileChooser.showOpenDialog(null);
		if(result == JFileChooser.CANCEL_OPTION) return;
			
		fileName = fileChooser.getSelectedFile();
		
		if(fileName != null)
		{
			try{
				FileInputStream fis = new FileInputStream(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);
				vFile = (Vector) ois.readObject();
				kelias = (String)(fileName.getAbsoluteFile()).toString();
				//System.out.println(kelias);
				atidare = true;
				
			}
			catch(Exception exp){
				atidare = false;
				JOptionPane.showMessageDialog(null,"Negali atidaryti failo!","Pieðimas",JOptionPane.INFORMATION_MESSAGE);
			}	
		}
		else{
			atidare=false;
			fileName = null;
		}
		repaint();
	}
/*------------------------------------------------------------------------------*/	
	private RenderedImage myCreateImage(int inK) 
	{
        BufferedImage bufferedImage = new BufferedImage(CanvasPlotis,CanvasAukstis, BufferedImage.TYPE_INT_RGB);

        Graphics g = bufferedImage.createGraphics();
        g.setColor(backGroundColor);
        g.fillRect(0,0,CanvasPlotis,CanvasAukstis);
        kadras = inK;
    	redrawVectorBuffer(g);

      	g.dispose();
      	return bufferedImage;
    }
/*----------------------------------------------------------------------------*/	
	//public void deleteFile()
	//{
	//	for (int k=0; k<60; k++)
	//	{
	//		boolean success = (new File("images/kadras"+k+".jpg")).delete();
	//		repaint();
	//	}
//	}

/*----------------------------------------------------------------------------*/	
	
    private void redrawVectorBuffer(Graphics g)
    {
    	int drawmode1 = 0;
    	int kadrai = 0;

      	for (int i=0;i<vFormos.size();i++)
      	{
      		kadrai = ((Coordinate)vFormos.elementAt(i)).fKadras();
      		if (kadrai == kadras)
      		{
         		g.setColor(((Coordinate)vFormos.elementAt(i)).colour());
         		drawmode1 = ((Coordinate)vFormos.elementAt(i)).fTipas();
         	
         		if (drawmode1 == this.LINE)
         		{
         			g.drawLine(((Coordinate)vFormos.elementAt(i)).getX1(),((Coordinate)vFormos.elementAt(i)).getY1(),((Coordinate)vFormos.elementAt(i)).getX2(),((Coordinate)vFormos.elementAt(i)).getY2());     		
      			}
      		
      			if (drawmode1 == this.OVAL)
      			{
	         		g.drawOval(((Coordinate)vFormos.elementAt(i)).getX1(),((Coordinate)vFormos.elementAt(i)).getY1(),((Coordinate)vFormos.elementAt(i)).getX2()-((Coordinate)vFormos.elementAt(i)).getX1(),((Coordinate)vFormos.elementAt(i)).getY2()-((Coordinate)vFormos.elementAt(i)).getY1());
      			}
      		
      			if (drawmode1 == this.SOLID_OVAL)
      			{
	         		g.fillOval(((Coordinate)vFormos.elementAt(i)).getX1(),((Coordinate)vFormos.elementAt(i)).getY1(),((Coordinate)vFormos.elementAt(i)).getX2()-((Coordinate)vFormos.elementAt(i)).getX1(),((Coordinate)vFormos.elementAt(i)).getY2()-((Coordinate)vFormos.elementAt(i)).getY1());
      			}
      		
      			if (drawmode1 == this.SQUARE)
      			{
	         		g.drawRect(((Coordinate)vFormos.elementAt(i)).getX1(),((Coordinate)vFormos.elementAt(i)).getY1(),((Coordinate)vFormos.elementAt(i)).getX2()-((Coordinate)vFormos.elementAt(i)).getX1(),((Coordinate)vFormos.elementAt(i)).getY2()-((Coordinate)vFormos.elementAt(i)).getY1());
      			}
      		
      			if (drawmode1 == this.SOLID_SQUARE)
      			{
	         		g.fillRect(((Coordinate)vFormos.elementAt(i)).getX1(),((Coordinate)vFormos.elementAt(i)).getY1(),((Coordinate)vFormos.elementAt(i)).getX2()-((Coordinate)vFormos.elementAt(i)).getX1(),((Coordinate)vFormos.elementAt(i)).getY2()-((Coordinate)vFormos.elementAt(i)).getY1());
      			} 
      			
      			if (drawmode1 == this.FREE_HAND)
      			{
      	 			int xHan[] = new int[((Coordinate)vFormos.elementAt(i)).getXPolygon().size()];
      	 			int yHan[] = new int[((Coordinate)vFormos.elementAt(i)).getYPolygon().size()];
      	 
      	 			for(int count=0;count<xHan.length;count++)
      	 			{
      	 				xHan[count] = ((Integer)((Coordinate)vFormos.elementAt(i)).getXPolygon().elementAt(count)).intValue();
      	 				yHan[count] = ((Integer)((Coordinate)vFormos.elementAt(i)).getYPolygon().elementAt(count)).intValue();
      	 				if (count != 0)
      	 				{
      	 					g.drawLine(xHan[count-1], yHan[count-1], xHan[count],yHan[count]);	
      	 				}
      	 			}     	 
	  			}     			
      		
      			if (drawmode1 == this.POLYGON)
      			{
      	 			int xPos[] = new int[((Coordinate)vFormos.elementAt(i)).getXPolygon().size()];
      	 			int yPos[] = new int[((Coordinate)vFormos.elementAt(i)).getYPolygon().size()];
      	 
      	 			for(int count=0;count<xPos.length;count++)
      	 			{
      	 				xPos[count] = ((Integer)((Coordinate)vFormos.elementAt(i)).getXPolygon().elementAt(count)).intValue();
      	 				yPos[count] = ((Integer)((Coordinate)vFormos.elementAt(i)).getYPolygon().elementAt(count)).intValue();
      	 			}     	 
      	 			g.drawPolygon(xPos,yPos,xPos.length);
	  			}
	  			if (drawmode1 == this.SOLID_POLYGON)
	  			{
      	 			int xPos[] = new int[((Coordinate)vFormos.elementAt(i)).getXPolygon().size()];
      	 			int yPos[] = new int[((Coordinate)vFormos.elementAt(i)).getYPolygon().size()];
      	 
      	 			for(int count=0;count<xPos.length;count++)
      	 			{
      	 				xPos[count] = ((Integer)((Coordinate)vFormos.elementAt(i)).getXPolygon().elementAt(count)).intValue();
      	 				yPos[count] = ((Integer)((Coordinate)vFormos.elementAt(i)).getYPolygon().elementAt(count)).intValue();
      	 			}
      	 			g.fillPolygon(xPos,yPos,xPos.length);
      			}
      			
      			if (drawmode1 == this.BEZIER_KREIVE)
	  			{
      	 			int xBez[] = new int[((Coordinate)vFormos.elementAt(i)).getXPolygon().size()];
      	 			int yBez[] = new int[((Coordinate)vFormos.elementAt(i)).getYPolygon().size()];
      	 			
      	 			
      	 			Vector xBezierTaskai = new Vector();
      	 			Vector yBezierTaskai = new Vector();
      	 			int c1x=0;
        			int c1y=0;
        			int c23x=0;
        			int c23y=0;
        			int c4x=0;
        			int c4y=0;
        			int xx1=0;
        			int yy1=0;
        			int nuo=0;
        			int Bdydis=0;
      	 			for(int count=0;count<xBez.length;count++)
      	 			{
      	 				xBez[count] = ((Integer)((Coordinate)vFormos.elementAt(i)).getXPolygon().elementAt(count)).intValue();
      	 				yBez[count] = ((Integer)((Coordinate)vFormos.elementAt(i)).getYPolygon().elementAt(count)).intValue();
      	 				if (count==1)
        				{
        					c1x=xBez[count-1];
        					c1y=yBez[count-1];
        					c4x=xBez[count];
        					c4y=yBez[count];
        					xx1=0;
        					yy1=0;
        					for (double ii=1;ii>=0;ii=ii-0.01) 
        					{  
        						xx1=(int)((double)((double)c4x+(double)(ii*(double)(c1x-c4x)))); 
        						yy1=(int)((double)((double)c4y+(double)(ii*(double)(c1y-c4y))));       				     			
        						xBezierTaskai.add(new Integer(xx1));
        						yBezierTaskai.add(new Integer(yy1));
        					}
        				} 
        				if (count>=2)
        				{
        					c1x=xBez[count-2];
        					c1y=yBez[count-2];
        					c23x=xBez[count-1];
        					c23y=yBez[count-1];
        					c4x=xBez[count];
        					c4y=yBez[count];
        					xx1=0;
        					yy1=0;
        					if (count-1==1)
        					{
        						nuo=50;
        					}
        					else
        					{
        						nuo=25;
        					}
        					Bdydis=xBezierTaskai.size();
        					for (double ii=0.75;ii>=0;ii=ii-0.01)
        					{
        						xx1=(int)((double)((double)(c1x*(ii*ii*ii))+
        							(double)(c23x*(3*ii*ii*(double)(1-ii)))+
        							(double)(c23x*(3*ii*(double)(1-ii)*(double)(1-ii)))+
        							(double)(c4x*((double)(1-ii)*(double)(1-ii)*(double)(1-ii)))));	
        					
        						yy1=(int)((double)((double)(c1y*(ii*ii*ii))+
        							(double)(c23y*(3*ii*ii*(double)(1-ii)))+
        							(double)(c23y*(3*ii*(double)(1-ii)*(double)(1-ii)))+
        							(double)(c4y*((double)(1-ii)*(double)(1-ii)*(double)(1-ii)))));
        						if (nuo>0)
        						{
        							xBezierTaskai.set(Bdydis-nuo, new Integer(xx1));
        							yBezierTaskai.set(Bdydis-nuo, new Integer(yy1));
        							nuo--;
        						}
        						else
        						{	
        							xBezierTaskai.add(new Integer(xx1));
        							yBezierTaskai.add(new Integer(yy1));
        						}
        					}		
        				}
        		
        	
      	 			}
      	 			int xBezierM[] = new int[xBezierTaskai.size()];
      	 			int yBezierM[] = new int[yBezierTaskai.size()];
      	 	
      	 			for (int count=0;count<xBezierM.length;count++)
      	 			{
      	 				xBezierM[count] = ((Integer)(xBezierTaskai.elementAt(count))).intValue();
      	 				yBezierM[count] = ((Integer)(yBezierTaskai.elementAt(count))).intValue();
      	 			}      	 	
      	 			g.drawPolyline(xBezierM,yBezierM,xBezierM.length);
      			}
      			
      			if (drawmode1 == this.SOLID_BEZIER_KREIVE)
	  			{
	  			//	System.out.println(""+drawmode1);
      	 			int xBez[] = new int[((Coordinate)vFormos.elementAt(i)).getXPolygon().size()];
      	 			int yBez[] = new int[((Coordinate)vFormos.elementAt(i)).getYPolygon().size()];
      	 
      	 				Vector xBezierTaskai = new Vector();
      	 				Vector yBezierTaskai = new Vector();
      	 				int c1x=0;
        				int c1y=0;
        				int c23x=0;
        				int c23y=0;
        				int c4x=0;
        				int c4y=0;
        				int xx1=0;
        				int yy1=0;
        				int nuo=0;
        				int Bdydis=0;
      	 				for(int count=0;count<xBez.length;count++)
      	 				{
      	 					xBez[count] = ((Integer)((Coordinate)vFormos.elementAt(i)).getXPolygon().elementAt(count)).intValue();
      	 					yBez[count] = ((Integer)((Coordinate)vFormos.elementAt(i)).getYPolygon().elementAt(count)).intValue();
      	 					if (count==1)
        					{
        						c1x=xBez[count-1];
        						c1y=yBez[count-1];
        						c4x=xBez[count];
        						c4y=yBez[count];
        						xx1=0;
        						yy1=0;
        						for (double ii=1;ii>=0;ii=ii-0.01) 
        						{  
        							xx1=(int)((double)((double)c4x+(double)(ii*(double)(c1x-c4x)))); 
        							yy1=(int)((double)((double)c4y+(double)(ii*(double)(c1y-c4y))));       				     			
        							xBezierTaskai.add(new Integer(xx1));
        							yBezierTaskai.add(new Integer(yy1));
        						}
        					} 
        					if (count>=2)
        					{
        						c1x=xBez[count-2];
        						c1y=yBez[count-2];
        						c23x=xBez[count-1];
        						c23y=yBez[count-1];
        						c4x=xBez[count];
        						c4y=yBez[count];
        						xx1=0;
        						yy1=0;
        						if (count-1==1)
        						{
        							nuo=50;
        						}
        						else
        						{
        							nuo=25;
        						}
        						Bdydis=xBezierTaskai.size();
        						for (double ii=0.75;ii>=0;ii=ii-0.01)
        						{
        							xx1=(int)((double)((double)(c1x*(ii*ii*ii))+
        								(double)(c23x*(3*ii*ii*(double)(1-ii)))+
        								(double)(c23x*(3*ii*(double)(1-ii)*(double)(1-ii)))+
        								(double)(c4x*((double)(1-ii)*(double)(1-ii)*(double)(1-ii)))));	
        					
        							yy1=(int)((double)((double)(c1y*(ii*ii*ii))+
        								(double)(c23y*(3*ii*ii*(double)(1-ii)))+
        								(double)(c23y*(3*ii*(double)(1-ii)*(double)(1-ii)))+
        								(double)(c4y*((double)(1-ii)*(double)(1-ii)*(double)(1-ii)))));
        							if (nuo>0)
        							{
        								xBezierTaskai.set(Bdydis-nuo, new Integer(xx1));
        								yBezierTaskai.set(Bdydis-nuo, new Integer(yy1));
        								nuo--;
        							}
        							else
        							{	
        								xBezierTaskai.add(new Integer(xx1));
        								yBezierTaskai.add(new Integer(yy1));
        							}
        						}		
        					}
        		
        	
      	 				}
      	 				if (xBez.length>=2)
      	 				{
      	 					c1x=xBez[xBez.length-2];
        					c1y=yBez[yBez.length-2];
        					c23x=xBez[xBez.length-1];
        					c23y=yBez[xBez.length-1];
        					c4x=xBez[0];
        					c4y=yBez[0];
        					xx1=0;
        					yy1=0;
        					nuo=25;
        				
        					Bdydis=xBezierTaskai.size();
        					for (double ii=0.75;ii>=0.25;ii=ii-0.01)
        					{
        						xx1=(int)((double)((double)(c1x*(ii*ii*ii))+
        								(double)(c23x*(3*ii*ii*(double)(1-ii)))+
        								(double)(c23x*(3*ii*(double)(1-ii)*(double)(1-ii)))+
        								(double)(c4x*((double)(1-ii)*(double)(1-ii)*(double)(1-ii)))));	
        					
        						yy1=(int)((double)((double)(c1y*(ii*ii*ii))+
        								(double)(c23y*(3*ii*ii*(double)(1-ii)))+
        								(double)(c23y*(3*ii*(double)(1-ii)*(double)(1-ii)))+
        								(double)(c4y*((double)(1-ii)*(double)(1-ii)*(double)(1-ii)))));
        						if (nuo>0)
        						{
        							xBezierTaskai.set(Bdydis-nuo, new Integer(xx1));
        							yBezierTaskai.set(Bdydis-nuo, new Integer(yy1));
        							nuo--;
        						}
        						else
        						{	
        							xBezierTaskai.add(new Integer(xx1));
        							yBezierTaskai.add(new Integer(yy1));
        						}
        					}
        				
        					c1x=xBez[xBez.length-1];
        					c1y=yBez[yBez.length-1];
        					c23x=xBez[0];
        					c23y=yBez[0];
        					c4x=xBez[1];
        					c4y=yBez[1];
        					xx1=0;
        					yy1=0;
        					nuo=0;
        					Bdydis=xBezierTaskai.size();
        					for (double ii=0.75;ii>=0.25;ii=ii-0.01)
        					{
        						xx1=(int)((double)((double)(c1x*(ii*ii*ii))+
        								(double)(c23x*(3*ii*ii*(double)(1-ii)))+
        								(double)(c23x*(3*ii*(double)(1-ii)*(double)(1-ii)))+
        								(double)(c4x*((double)(1-ii)*(double)(1-ii)*(double)(1-ii)))));	
        					
        						yy1=(int)((double)((double)(c1y*(ii*ii*ii))+
        								(double)(c23y*(3*ii*ii*(double)(1-ii)))+
        								(double)(c23y*(3*ii*(double)(1-ii)*(double)(1-ii)))+
        								(double)(c4y*((double)(1-ii)*(double)(1-ii)*(double)(1-ii)))));
        				
        						xBezierTaskai.set(nuo, new Integer(xx1));
        						yBezierTaskai.set(nuo, new Integer(yy1));
        						nuo++;
        					}	
      	 				}
      	 				int xBezierM[] = new int[xBezierTaskai.size()];
      	 				int yBezierM[] = new int[yBezierTaskai.size()];
      	 	
      	 				for (int count=0;count<xBezierM.length;count++)
      	 				{
      	 					xBezierM[count] = ((Integer)(xBezierTaskai.elementAt(count))).intValue();
      	 					yBezierM[count] = ((Integer)(yBezierTaskai.elementAt(count))).intValue();
      	 				}      	 
      	 				g.fillPolygon(xBezierM,yBezierM,xBezierM.length);
      				}
      		}	     		
      	} 
      	
      	if (drawMode == FREE_HAND)
      	{
      		for (int i=0;i<vHands.size();i++)
      		{
      			kadrai = ((Coordinate)vHands.elementAt(i)).fKadras();        		
         	
      			if (kadrai==kadras)
      			{
      				g.setColor(((Coordinate)vHands.elementAt(i)).colour());
      				g.drawLine(((Coordinate)vHands.elementAt(i)).getX1(),((Coordinate)vHands.elementAt(i)).getY1(),((Coordinate)vHands.elementAt(i)).getX2(),((Coordinate)vHands.elementAt(i)).getY2());
      			
      			}	
      		}
      	}
      	else
      	{
      		vHands.removeAllElements();	
      	}
      	
      	for (int i=0;i<vPazymeti.size();i++)
      	{      		
      		g.setColor(Color.RED);
      		g.fillRect(((Coordinate)vPazymeti.elementAt(i)).getX1(), ((Coordinate)vPazymeti.elementAt(i)).getY1(),5,5);
      		g.setColor(Color.BLACK);
      		g.drawRect(((Coordinate)vPazymeti.elementAt(i)).getX1(), ((Coordinate)vPazymeti.elementAt(i)).getY1(),5,5);
      	}     	
    }
/*----------------------------------------------------------------------------*/	
	public void uzpildomKadrus()
	{
		if (pazymetas != -1)
		{
			
			if (uzpildModas==TRANSFORM)
			{
				int drawmode1 = ((Coordinate)vFormos.elementAt(pazymetas)).fTipas();
				Color spalva = ((Coordinate)vFormos.elementAt(pazymetas)).colour();
				
				double Sx1=1,Sy1=1;
				double xMastelis = Sx-1;
				double yMastelis = Sy-1;				
				
				double A1=0;
				double aKampas = (A*Math.PI)/180;
				
				double Tx1=0,Ty1=0;
				double xStumiam = Tx*25;
				double yStumiam = Ty*25;
				
				double isimX=0, isimY=0;
				
				AtsX=AtsX*25;
				AtsY=AtsY*25;
				
				Vector xx1 = new Vector();
				Vector yy1 = new Vector();
				
				double xxM[]=new double[1];
				double yyM[]=new double[1];
				int MasDydis=0;
				
				if (drawmode1==LINE)
				{
					xxM = new double[2];
					yyM = new double[2];
					
					xxM[0]=(double)((Coordinate)vFormos.elementAt(pazymetas)).getX1();
					yyM[0]=(double)((Coordinate)vFormos.elementAt(pazymetas)).getY1();
					xxM[1]=(double)((Coordinate)vFormos.elementAt(pazymetas)).getX2();
					yyM[1]=(double)((Coordinate)vFormos.elementAt(pazymetas)).getY2();
					
					MasDydis=2;	
				}
				
				if ((drawmode1==OVAL)||(drawmode1==SOLID_OVAL))
				{
					double spindulysA =(double)(((Coordinate)vFormos.elementAt(pazymetas)).getX2()-((Coordinate)vFormos.elementAt(pazymetas)).getX1())/2;	
					double spindulysB =(double)(((Coordinate)vFormos.elementAt(pazymetas)).getY2()-((Coordinate)vFormos.elementAt(pazymetas)).getY1())/2;
					
					double Cx=(double)(((Coordinate)vFormos.elementAt(pazymetas)).getX2()-spindulysA);
					double Cy=(double)(((Coordinate)vFormos.elementAt(pazymetas)).getY2()-spindulysB);
					
					double taskasx=0;
					double taskasy=0;
					Vector apskritx =new Vector();
					Vector apskrity =new Vector();
					for (double t=0;t<=1;t=t+0.01)
					{
						taskasx= spindulysA*Math.cos(2*Math.PI*t)+Cx;
						taskasy= spindulysB*Math.sin(2*Math.PI*t)+Cy;
						
						apskritx.add(new Double(taskasx));
						apskrity.add(new Double(taskasy));						
					}
					xxM = new double[apskritx.size()];
					yyM = new double[apskrity.size()];
					for (int i=0;i<apskritx.size();i++)
					{
						xxM[i]= ((Double)(apskritx.elementAt(i))).doubleValue();
						yyM[i]= ((Double)(apskrity.elementAt(i))).doubleValue();	
					}
					
					MasDydis = xxM.length;
				}
				
				if ((drawmode1==SQUARE)||(drawmode1==SOLID_SQUARE))
				{
					xxM = new double[4];
					yyM = new double[4];
					
					xxM[0]= xxM[3] = (double)((Coordinate)vFormos.elementAt(pazymetas)).getX1();
					xxM[1]= xxM[2] = (double)((Coordinate)vFormos.elementAt(pazymetas)).getX2();
					yyM[0]= yyM[1] = (double)((Coordinate)vFormos.elementAt(pazymetas)).getY1();
					yyM[2]= yyM[3] = (double)((Coordinate)vFormos.elementAt(pazymetas)).getY2();
										
					MasDydis=xxM.length;
				}
				
				if ((drawmode1==POLYGON)||(drawmode1==SOLID_POLYGON)||(drawmode1==BEZIER_KREIVE)||(drawmode1==SOLID_BEZIER_KREIVE))
				{					
					xxM = new double[((Coordinate)vFormos.elementAt(pazymetas)).getXPolygon().size()];
					yyM = new double[((Coordinate)vFormos.elementAt(pazymetas)).getYPolygon().size()];
					
					for (int i=0;i<xxM.length;i++)
					{
						xxM[i] = (double)((Integer)((Coordinate)vFormos.elementAt(pazymetas)).getXPolygon().elementAt(i)).intValue();	
						yyM[i] = (double)((Integer)((Coordinate)vFormos.elementAt(pazymetas)).getYPolygon().elementAt(i)).intValue();	
					}
					MasDydis=xxM.length;
				}
				
				double xxPag[] = new double[xxM.length];
				double yyPag[] = new double[yyM.length];
					
				
					Sx1=Sx1+xMastelis;
					Sy1=Sy1+yMastelis;
						
					A1=A1+aKampas;
						
					Tx1=Tx1+xStumiam;
					Ty1=Ty1+yStumiam;
						
					for (int ii=0;ii<MasDydis;ii++)
					{
						xxPag[ii]=AtsX+Sx1*(xxM[ii]-AtsX);
						yyPag[ii]=AtsY+Sy1*(yyM[ii]-AtsY);
							
						isimX=xxPag[ii];
						isimY=yyPag[ii];
							
						xxPag[ii]=AtsX+Math.cos(A1)*(isimX-AtsX)-Math.sin(A1)*(isimY-AtsY);
						yyPag[ii]=AtsY+Math.sin(A1)*(isimX-AtsX)+Math.cos(A1)*(isimY-AtsY);
							
						xxPag[ii]=xxPag[ii]+Tx1;
						yyPag[ii]=yyPag[ii]+Ty1;
							
						xx1.add(new Integer((int)xxPag[ii]));
						yy1.add(new Integer((int)yyPag[ii]));	
					}
											
					if ((drawmode1==SQUARE)||(drawmode1==POLYGON)||(drawmode1==OVAL)||(drawmode1==LINE))
					{			
						vFormos.set(pazymetas, new Coordinate(xx1,yy1,spalva,POLYGON, kadras));	
						atgalStack.set(pazymetas, new StepInfo(new Coordinate(xx1,yy1,spalva,POLYGON, kadras)));
					}
					if ((drawmode1==SOLID_SQUARE)||(drawmode1==SOLID_POLYGON)||(drawmode1==SOLID_OVAL))
					{
						vFormos.set(pazymetas, new Coordinate(xx1,yy1,spalva,SOLID_POLYGON, kadras));
						atgalStack.set(pazymetas, new StepInfo(new Coordinate(xx1,yy1,spalva,SOLID_POLYGON, kadras)));
					}
					if (drawmode1==BEZIER_KREIVE)
					{
						vFormos.set(pazymetas, new Coordinate(xx1,yy1,spalva,BEZIER_KREIVE, kadras));
						atgalStack.set(pazymetas, new StepInfo(new Coordinate(xx1,yy1,spalva,BEZIER_KREIVE, kadras)));
					}
					if (drawmode1==SOLID_BEZIER_KREIVE)
					{
						vFormos.set(pazymetas, new Coordinate(xx1,yy1,spalva,SOLID_BEZIER_KREIVE, kadras));
						atgalStack.set(pazymetas, new StepInfo(new Coordinate(xx1,yy1,spalva,SOLID_BEZIER_KREIVE, kadras)));
					}
					System.out.println(""+pazymetas);
					xx1.removeAllElements();
					yy1.removeAllElements();		
							
				
			
			}
			if (uzpildModas==JUDEJIMAS)
			{
				int drawmode1 = ((Coordinate)vFormos.elementAt(pazymetas)).fTipas();
				Color spalva = ((Coordinate)vFormos.elementAt(pazymetas)).colour();
				
				double Sx1=1,Sy1=1;
				double xMastelis = ((Sx-1)/(ikiKadro-kadras));
				double yMastelis = ((Sy-1)/(ikiKadro-kadras));				
				
				double A1=0;
				double aKampas = ((A*Math.PI)/180)/(double)(ikiKadro-kadras);
				
				double Tx1=0,Ty1=0;
				double xStumiam = (Tx/(ikiKadro-kadras))*25;
				double yStumiam = (Ty/(ikiKadro-kadras))*25;
				
				double isimX=0, isimY=0;
				
				AtsX=AtsX*25;
				AtsY=AtsY*25;
				
				Vector xx1 = new Vector();
				Vector yy1 = new Vector();
				
				double xxM[]=new double[1];
				double yyM[]=new double[1];
				int MasDydis=0;
				
				if (drawmode1==LINE)
				{
					xxM = new double[2];
					yyM = new double[2];
					
					xxM[0]=(double)((Coordinate)vFormos.elementAt(pazymetas)).getX1();
					yyM[0]=(double)((Coordinate)vFormos.elementAt(pazymetas)).getY1();
					xxM[1]=(double)((Coordinate)vFormos.elementAt(pazymetas)).getX2();
					yyM[1]=(double)((Coordinate)vFormos.elementAt(pazymetas)).getY2();
					
					MasDydis=2;	
				}
				
				if ((drawmode1==OVAL)||(drawmode1==SOLID_OVAL))
				{
					double spindulysA =(double)(((Coordinate)vFormos.elementAt(pazymetas)).getX2()-((Coordinate)vFormos.elementAt(pazymetas)).getX1())/2;	
					double spindulysB =(double)(((Coordinate)vFormos.elementAt(pazymetas)).getY2()-((Coordinate)vFormos.elementAt(pazymetas)).getY1())/2;
					
					double Cx=(double)(((Coordinate)vFormos.elementAt(pazymetas)).getX2()-spindulysA);
					double Cy=(double)(((Coordinate)vFormos.elementAt(pazymetas)).getY2()-spindulysB);
					
					double taskasx=0;
					double taskasy=0;
					Vector apskritx =new Vector();
					Vector apskrity =new Vector();
					for (double t=0;t<=1;t=t+0.01)
					{
						taskasx= spindulysA*Math.cos(2*Math.PI*t)+Cx;
						taskasy= spindulysB*Math.sin(2*Math.PI*t)+Cy;
						
						apskritx.add(new Double(taskasx));
						apskrity.add(new Double(taskasy));						
					}
					xxM = new double[apskritx.size()];
					yyM = new double[apskrity.size()];
					for (int i=0;i<apskritx.size();i++)
					{
						xxM[i]= ((Double)(apskritx.elementAt(i))).doubleValue();
						yyM[i]= ((Double)(apskrity.elementAt(i))).doubleValue();	
					}
					
					MasDydis = xxM.length;
				}
				
				if ((drawmode1==SQUARE)||(drawmode1==SOLID_SQUARE))
				{
					xxM = new double[4];
					yyM = new double[4];
					
					xxM[0]= xxM[3] = (double)((Coordinate)vFormos.elementAt(pazymetas)).getX1();
					xxM[1]= xxM[2] = (double)((Coordinate)vFormos.elementAt(pazymetas)).getX2();
					yyM[0]= yyM[1] = (double)((Coordinate)vFormos.elementAt(pazymetas)).getY1();
					yyM[2]= yyM[3] = (double)((Coordinate)vFormos.elementAt(pazymetas)).getY2();
										
					MasDydis=xxM.length;
				}
				
				if ((drawmode1==POLYGON)||(drawmode1==SOLID_POLYGON)||(drawmode1==BEZIER_KREIVE)||(drawmode1==SOLID_BEZIER_KREIVE))
				{					
					xxM = new double[((Coordinate)vFormos.elementAt(pazymetas)).getXPolygon().size()];
					yyM = new double[((Coordinate)vFormos.elementAt(pazymetas)).getYPolygon().size()];
					
					for (int i=0;i<xxM.length;i++)
					{
						xxM[i] = (double)((Integer)((Coordinate)vFormos.elementAt(pazymetas)).getXPolygon().elementAt(i)).intValue();	
						yyM[i] = (double)((Integer)((Coordinate)vFormos.elementAt(pazymetas)).getYPolygon().elementAt(i)).intValue();	
					}
					MasDydis=xxM.length;
				}
				
				double xxPag[] = new double[xxM.length];
				double yyPag[] = new double[yyM.length];
					
				for (int i=kadras+1;i<=ikiKadro;i++)
				{
					Sx1=Sx1+xMastelis;
					Sy1=Sy1+yMastelis;
						
					A1=A1+aKampas;
						
					Tx1=Tx1+xStumiam;
					Ty1=Ty1+yStumiam;
						
					for (int ii=0;ii<MasDydis;ii++)
					{
						xxPag[ii]=AtsX+Sx1*(xxM[ii]-AtsX);
						yyPag[ii]=AtsY+Sy1*(yyM[ii]-AtsY);
							
						isimX=xxPag[ii];
						isimY=yyPag[ii];
							
						xxPag[ii]=AtsX+Math.cos(A1)*(isimX-AtsX)-Math.sin(A1)*(isimY-AtsY);
						yyPag[ii]=AtsY+Math.sin(A1)*(isimX-AtsX)+Math.cos(A1)*(isimY-AtsY);
							
						xxPag[ii]=xxPag[ii]+Tx1;
						yyPag[ii]=yyPag[ii]+Ty1;
							
						xx1.add(new Integer((int)xxPag[ii]));
						yy1.add(new Integer((int)yyPag[ii]));	
					}
											
					if ((drawmode1==SQUARE)||(drawmode1==POLYGON)||(drawmode1==OVAL)||(drawmode1==LINE))
					{			
						vFormos.add(new Coordinate(xx1,yy1,spalva,POLYGON, i));	
						atgalStack.push(new StepInfo(new Coordinate(xx1,yy1,spalva,POLYGON, kadras)));

					}
					if ((drawmode1==SOLID_SQUARE)||(drawmode1==SOLID_POLYGON)||(drawmode1==SOLID_OVAL))
					{
						vFormos.add(new Coordinate(xx1,yy1,spalva,SOLID_POLYGON, i));
						atgalStack.push(new StepInfo(new Coordinate(xx1,yy1,spalva,SOLID_POLYGON, kadras)));

					}
					if (drawmode1==BEZIER_KREIVE)
					{
						vFormos.add(new Coordinate(xx1,yy1,spalva,BEZIER_KREIVE, i));
						atgalStack.push(new StepInfo(new Coordinate(xx1,yy1,spalva,BEZIER_KREIVE, kadras)));

					}
					if (drawmode1==SOLID_BEZIER_KREIVE)
					{
						vFormos.add(new Coordinate(xx1,yy1,spalva,SOLID_BEZIER_KREIVE, i));
						atgalStack.push(new StepInfo(new Coordinate(xx1,yy1,spalva,SOLID_BEZIER_KREIVE, kadras)));

					}
					xx1.removeAllElements();
					yy1.removeAllElements();		
				}				
				
			}
		}
	}
/*----------------------------------------------------------------------------*/	

}