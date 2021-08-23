
package simulationserver.system.gui;

import java.awt.Component;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author Ernesto
 */
public class SetpointHandler extends java.lang.Thread implements javax.swing.event.ChangeListener {
    // gui vars
    private final javax.swing.JFrame frame;
    private final javax.swing.JSlider slider;
    private final javax.swing.JLabel label;
    // numerica values
    private double maxValue = 100.0;
    private double minValue = 100.0;
    private double value = 0.0;
    // dynamic model
    private simulationserver.system.control.Controlador model;
    // constructor
    public SetpointHandler () {
        super();
        this.frame = new javax.swing.JFrame("Setpoint Handler");
        this.frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(400, 160);
        
        this.slider = new javax.swing.JSlider();
        this.slider.setOrientation(javax.swing.JSlider.HORIZONTAL);
        this.slider.setMaximum(100);
        this.slider.setMinimum(0);
        this.slider.setMajorTickSpacing(10);
        this.slider.setMinorTickSpacing(1);
        this.slider.setPaintTicks(true);
        this.slider.setPaintLabels(true);
        
        label = new javax.swing.JLabel("Valor de consigna %");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        final javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new java.awt.BorderLayout());
        panel.add(label, java.awt.BorderLayout.NORTH);
        panel.add(slider, java.awt.BorderLayout.CENTER);
        frame.add(panel);
    }
    
    public void setModel (simulationserver.system.control.Controlador model) {
        this.model = model;
    }
    
    public void setMaxValue (double maxValue) {
        this.maxValue = maxValue;
    }
    
    public void setMinValue (double minValue) {
        this.minValue = minValue;
    }
    
    public void setValue (double value) {
        this.value = value;
        if (this.value > this.maxValue) {
            this.value = this.maxValue;
        } if (this.value < this.minValue) {
            this.value = this.minValue;
        }
        // update the slider
        double delta = this.maxValue - this.minValue;
        delta = Math.abs(delta) / 100.0;
        final int intValue = (int)((this.value - this.minValue)/delta);
        try{
            this.slider.setValue(intValue);
            // update jlabel
            this.label.setText("Valor de consiga: "+intValue+" %, \""+value+"\"");
        }catch(Exception ex){}
    }
    
    @Override
    public void start () {
        // check if model
        if (this.model == null) {
            return;
        }
        // add the change listener
        this.slider.addChangeListener(this);
        // start the thread
        super.start();
    }
    
    @Override
    public void run() {
        this.frame.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (this.slider.getValueIsAdjusting()) {
            return;
        }
        
        final int valueTmp = this.slider.getValue();
        // map to range values
        double delta = this.maxValue - this.minValue;
        delta = Math.abs(delta) / 100.0;
        final double out = this.minValue + ((double)valueTmp)*delta;
        this.model.setSetpoint(out);
        // update jlabel
        this.label.setText("Valor de consiga: "+valueTmp+" %, \""+out+"\"");
    }
}
