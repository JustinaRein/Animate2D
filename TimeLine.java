import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.NumberFormatter;
import java.beans.*;


public class TimeLine extends JPanel implements ChangeListener, PropertyChangeListener
{
	
	private JLabel JLKoordinates1, JLKoordinates2;
	private JLabel laibelis1;
	private JFormattedTextField textField;
	private JSlider framesPerSecond;
	private JPanel panelis1;
	private float xx = 0, yy = 0;
	private CanvasPanel canvasPanel;
	
    static final int FPS_MIN = 0;
    static final int FPS_MAX = 60;
    static final int FPS_INIT = 0;    
    	
	int frameNumber = 0;
    
    
    
	public TimeLine(CanvasPanel inCanvasPanel)
	{
		canvasPanel = inCanvasPanel;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        framesPerSecond = new JSlider(JSlider.HORIZONTAL,
                                              FPS_MIN, FPS_MAX, FPS_INIT);
       	framesPerSecond.addChangeListener(this);
        framesPerSecond.setMajorTickSpacing(10);
        framesPerSecond.setMinorTickSpacing(1);
        framesPerSecond.setPaintTicks(true);
        framesPerSecond.setPaintLabels(true);       
		
        framesPerSecond.setBorder(
                BorderFactory.createEmptyBorder(0,140,0,10));
        
        laibelis1 = new JLabel("kadras");        
        		
		JLKoordinates1 = new JLabel("    ");
		JLKoordinates2 = new JLabel("           ");
		
        java.text.NumberFormat numberFormat =
        java.text.NumberFormat.getIntegerInstance();
        NumberFormatter formatter = new NumberFormatter(numberFormat);
        formatter.setMinimum(new Integer(FPS_MIN));
        formatter.setMaximum(new Integer(FPS_MAX));
        textField = new JFormattedTextField(formatter);
        textField.setValue(new Integer(FPS_INIT));
        textField.setColumns(5); 
        textField.addPropertyChangeListener(this);

       
        textField.getInputMap().put(KeyStroke.getKeyStroke(
                                        KeyEvent.VK_ENTER, 0),
                                        "check");
        textField.getActionMap().put("check", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!textField.isEditValid()) { 
                    Toolkit.getDefaultToolkit().beep();
                    textField.selectAll();
                } else try {                   
                    textField.commitEdit();     
                } catch (java.text.ParseException exc) { }
            }
        });
	
		panelis1 = new JPanel();
		panelis1.setBorder(BorderFactory.createLineBorder(Color.black));
		panelis1.add(laibelis1);
		panelis1.add(textField);
		panelis1.add(JLKoordinates2);		
		panelis1.add(JLKoordinates1);
		       
        add(framesPerSecond);                                  
    	add(panelis1);
	}	
	
	public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        int fps = (int)source.getValue();
        if (!source.getValueIsAdjusting()) { 
            textField.setValue(new Integer(fps)); 
            canvasPanel.setKadras(fps);
        } 
        else 
        { 
            textField.setText(String.valueOf(fps));
        }
    }
	
	public void propertyChange(PropertyChangeEvent e) {
        if ("value".equals(e.getPropertyName())) {
            Number value = (Number)e.getNewValue();
            if (framesPerSecond != null && value != null) {
                framesPerSecond.setValue(value.intValue());
            }
        }
    }
	
	public void GaunamKoord(int x, int y)
	{
		xx = x;
		yy = y;
		JLKoordinates1.setText("(" + xx + "," + yy + ")");	
		
	}

}