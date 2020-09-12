package resistor;


/*
*	Date: 13 Jun 2011
*	ElectronicColorCode.java
*	Resistor color code analyzer which generates resistance given color code on resistor
*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.plaf.basic.*;

public class resistorCalculator extends JFrame implements ActionListener{
    private JPanel[] panels = new JPanel[2];
    private JComboBox[] selections = new JComboBox[4];
    private ColorCode[] colorSets = {new ColorCode(Color.BLACK, "Black"), 
        new ColorCode(new Color(165, 42, 42), "Brown"),
        new ColorCode(Color.RED, "Red"), new ColorCode(Color.ORANGE, "Orange"),
        new ColorCode(Color.YELLOW, "Yellow"), new ColorCode(Color.GREEN, "Green"),
        new ColorCode(Color.BLUE, "Blue"), new ColorCode(Color.MAGENTA, "Violet"),
        new ColorCode(Color.GRAY, "Gray"), new ColorCode(Color.WHITE, "White")};
    private ColorCode[] tolColorSets = {new ColorCode(Color.GRAY, "Gray"), new ColorCode(Color.MAGENTA, "Violet"),
        new ColorCode(Color.BLUE, "Blue"), new ColorCode(Color.GREEN, "Green"),
        new ColorCode(new Color(165, 42, 42), "Brown"), new ColorCode(Color.RED, "Red"),
        new ColorCode(new Color(255, 215, 0), "Gold"), new ColorCode(new Color(192, 192, 192), "Silver"),
        new ColorCode(Color.WHITE, "No Color")};
    private double[] tolerance = {0.05, 0.1, 0.25, 0.5, 1, 2, 5, 10, 20};
    private String[] labelStrings = {"Resistance", "Tolerance", "Range"};
	private JTextField resisField, tolField, rangeField;
	private double resistance = 0.0, tol = 0.0, upLimit = 0.0, lowLimit = 0.0;
	private int i;
	
	public resistorCalculator(){
            
        this.setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Ax/pht.png")));
		// panel 0: simulate the resistor
        panels[0] = new JPanel();
		panels[0].setLayout(new GridLayout(2, selections.length));
		panels[0].setBorder(BorderFactory.createLineBorder(Color.BLACK));
		for(i=0; i<selections.length; i++)
			panels[0].add(new JLabel("Color Code " + (i+1), JLabel.CENTER));
		for(i=0; i<selections.length; i++){
			if(i==selections.length-1)
				selections[i] = new JComboBox(tolColorSets);
			else
				selections[i] = new JComboBox(colorSets);
			selections[i].setRenderer(new ItemRenderer());
			selections[i].setBorder(BorderFactory.createEmptyBorder(0,10,10,10));			
			selections[i].addActionListener(this);
			panels[0].add(selections[i]);
		}
		
		// panel 1: calculate resistance
		panels[1] = new JPanel();
		panels[1].setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		panels[1].setLayout(new GridLayout(2, labelStrings.length));
		for(i=0; i<labelStrings.length; i++)
			panels[1].add(new JLabel(labelStrings[i], JLabel.CENTER));
		resisField = new JTextField(10);
		resisField.setHorizontalAlignment(JTextField.CENTER);
		resisField.setEditable(false);
		panels[1].add(resisField);
		tolField = new JTextField(10);
		tolField.setHorizontalAlignment(JTextField.CENTER);
		tolField.setEditable(false);
		panels[1].add(tolField);
		rangeField = new JTextField(20);
		rangeField.setHorizontalAlignment(JTextField.CENTER);
		rangeField.setEditable(false);
		panels[1].add(rangeField);
		
		// JFrame properties
		Container container = getContentPane();
		container.add(panels[0], BorderLayout.NORTH);
		container.add(panels[1], BorderLayout.CENTER);
		setTitle("Resistor Calculator");
		setContentPane(container);
		setVisible(true);
		setSize(500,170);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ae){
		DecimalFormat oneDigit = new DecimalFormat("0.0");
		DecimalFormat threeDigits = new DecimalFormat("0.000");
		String output = "";
		
		resistance = selections[0].getSelectedIndex() * 10 + selections[1].getSelectedIndex();
		resistance *= Math.pow(10, selections[2].getSelectedIndex());
		if(resistance/1000000000>1.0)
			output = oneDigit.format(resistance/1000000000) + "B";
		else if(resistance/1000000>1.0)
			output = oneDigit.format(resistance/1000000) + "M";
		else if(resistance/1000.0>1.0)
			output = oneDigit.format(resistance/1000) + "k";
		else
			output = oneDigit.format(resistance);
		resisField.setText(output);
		tol = tolerance[selections[3].getSelectedIndex()];
		tolField.setText(threeDigits.format(tol));
		upLimit = resistance * (1 + tol/100);
		lowLimit = resistance * (1 - tol/100);
		if(lowLimit/1000000000>1.0)
			output = threeDigits.format(lowLimit/1000000000) + "B - ";
		else if(lowLimit/1000000>1.0)
			output = threeDigits.format(lowLimit/1000000) + "M - ";
		else if(lowLimit/1000.0>1.0)
			output = threeDigits.format(lowLimit/1000) + "k - ";
		else
			output = threeDigits.format(lowLimit) + " - ";
		if(upLimit/1000000000>1.0)
			output += threeDigits.format(upLimit/1000000000) + "B";
		else if(upLimit/1000000.0>1.0)
			output += threeDigits.format(upLimit/1000000) + "M";
		else if(upLimit/1000.0>1.0)
			output += threeDigits.format(upLimit/1000) + "k";
		else
			output += threeDigits.format(upLimit);
		rangeField.setText(output);
		upLimit = lowLimit = tol = resistance = 0.0;
	}
	
	public static void main(String args[]){
		//ElectronicColorCode app = new ElectronicColorCode();
	}
	
	class ItemRenderer extends BasicComboBoxRenderer{
		public Component getListCellRendererComponent(JList list, Object obj, int index, boolean isSelected, boolean hasFocus){
			super.getListCellRendererComponent(list, obj, index, isSelected, hasFocus);
			ColorCode colorCode = (ColorCode)obj;
			setText(colorCode.getAlias());
			Color bgColor = colorCode.getColor();
			setBackground(bgColor);
			if(!"Gray".equals(colorCode.getAlias()))
				setForeground(new Color(255-bgColor.getRed(), 255-bgColor.getGreen(), 255-bgColor.getBlue()));
			return this;
		}
	}
	
	class ColorCode{
		private Color color;
		private String alias;
		
		public ColorCode(Color color, String alias){
			this.color = color;
			this.alias = alias;
		}
		
		public Color getColor(){
			return color;
		}
		
		public String getAlias(){
			return alias;
		}
	}
}
