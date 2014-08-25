import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;


public class AnimationShow extends JDialog 
{
	private JPanel panelis1;
	private CanvasPanel canvasPanel; 
	private String kelias;
	
	static final int FPS_MIN = 0;
    static final int FPS_MAX = 30;
    static final int FPS_INIT = 15;   
    private int frameNumber = 0;
    private int NUM_FRAMES = 60;
    private int delay;
    private Timer timer;
    private boolean frozen = false;
    private JLabel picture;

	AnimationShow(JFrame parent, CanvasPanel inCanvasPanel, String inKelias)
	{
		super(parent);
		this.setTitle("Filmukas");
		
		canvasPanel=inCanvasPanel;
		kelias = inKelias;
		
		panelis1 = new JPanel();		
		panelis1.setLayout(new BoxLayout(panelis1, BoxLayout.PAGE_AXIS));

        delay = 1000 / FPS_INIT;

        JLabel sliderLabel = new JLabel("Kadrai per sekunæ", JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL,
                                              FPS_MIN, FPS_MAX, FPS_INIT);
       
       	framesPerSecond.addChangeListener(
        	new ChangeListener()
        	{
        		public void stateChanged(ChangeEvent e) 
        		{
        			JSlider source = (JSlider)e.getSource();
        			if (!source.getValueIsAdjusting()) 
        			{
            			int fps = (int)source.getValue();
            			if (fps == 0) 
            			{
                			if (!frozen) stopAnimation();
            			} 
            			else 
            			{
                			delay = 1000 / fps;
                			timer.setDelay(delay);
                			if (frozen) startAnimation();
            			}
        			}	
        		} 	
        	}
        );

        framesPerSecond.setMajorTickSpacing(10);
        framesPerSecond.setMinorTickSpacing(1);
        framesPerSecond.setPaintTicks(true);
        framesPerSecond.setPaintLabels(true);
        framesPerSecond.setBorder(
                BorderFactory.createEmptyBorder(0,0,10,0));

        picture = new JLabel();
        picture.setHorizontalAlignment(JLabel.CENTER);
        picture.setAlignmentX(Component.CENTER_ALIGNMENT);
        picture.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(10,10,10,10)));
        updatePicture(0); 

        panelis1.add(sliderLabel);
        panelis1.add(framesPerSecond);
        panelis1.add(picture);
        panelis1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        timer = new Timer(delay, 
        						new ActionListener()
        						{
        							public void actionPerformed(ActionEvent e) 
        							{
        								if (frameNumber == (NUM_FRAMES - 1)) 
        								{
            								frameNumber = 0;
        								} 
        								else 
        								{
            								frameNumber++;
        								}
		
        								updatePicture(frameNumber); 
										repaint();
        								if ( frameNumber==(NUM_FRAMES - 1)
          									|| frameNumber==(NUM_FRAMES/2 - 1) ) 
          								{
           									timer.restart();
        								}
    								}	
        						}
        );
		
		
		startAnimation();
        this.setContentPane(panelis1);
        pack();
        this.show();
        addWindowListener (
      		new WindowAdapter () 
      		{
      			public void windowClosing (WindowEvent e) 
      			{
      				stopAnimation();
      				removeAll();
      			}
      			
      		}
      	);
       
	}
	
	public void startAnimation() 
	{
    	repaint();
        timer.start();
        frozen = false;
    }

    public void stopAnimation() 
    {
    	repaint();
        timer.stop();
        frozen = true;
    }
	
	protected void updatePicture(int frameNum) {
     	
     	int numeris = frameNum;
     	ImageIcon images;
        
        if (numeris<10)
        {
        	images = createImageIcon(kelias+"0"+numeris+ ".jpg"); 
        }
        else
        {
        	images = createImageIcon(kelias+""+numeris+ ".jpg");	
        }                                                 
        if (images != null) {
            picture.setIcon(images);
        } else { 
            picture.setText("paveikslelis " + numeris + " nerastas!");
        }
    }
    

    private ImageIcon createImageIcon(String path) 
    {
            return new ImageIcon(path);
    }	
		
}


