
package graphingcalculator;

/*@author Augusto Batista
  Version 2.0   11/7/2018*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public final class GraphingCalculator implements ActionListener {
    
    public static void main (String[] args){
        GraphingCalculator app = new GraphingCalculator();
    }
       
    private final JFrame frame; 
    private final JComboBox menu;
    private final JPanel topPanel;
    private final JPanel inputPanel;
    private final JPanel controlPanel;
    private final JPanel displayPanel;
    private final JLabel selectionLabel;
    private final JLabel infoLabel;
    private int height =0;
    
    private final JLabel[] coefficientLabel = new JLabel[6]; 
    private final JTextField[] textField = new JTextField[6];
 
    private final JButton clearButton;
    private final JButton zoomButton;
    private final JButton zoomButton2;
   
    private final JPanel colorPanel;
    private final ButtonGroup colorButtons;
    private final JRadioButton redButton;
    private final JRadioButton blueButton;
    private final JRadioButton greenButton;
    private final JRadioButton orangeButton;
    
    private final DecimalFormat df1;
    private final DecimalFormat df2;
    private final DecimalFormat df3;
    
    private final JButton calcButton;
    
    private final DrawingPanel plane;
    private final Graphics g;
    
    
    public GraphingCalculator(){
    Font style = new Font("Geneva",Font.PLAIN,14);
    Font style2 = new Font("Georgia",Font.PLAIN,15);
    
        //Frame configuration 
        frame = new JFrame("Graphing Calculator");
            frame.setSize(320, 260);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocation(50,150);
            frame.setLayout(new BorderLayout());
        
        // Top section of  the BorderLayout
        topPanel = new JPanel(new GridLayout(3,1));
        JPanel menuPanel = new JPanel(new FlowLayout());
            topPanel.add(menuPanel);
        selectionLabel = new JLabel("Selecione uma função:", SwingConstants.CENTER );
            selectionLabel.setFont(style);
            menuPanel.add(selectionLabel);
        frame.add(topPanel, BorderLayout.NORTH);
        
        // Setup function selection menu
        String[] functions = {"1ºGrau","2ºGrau","3ºGrau","Exponencial","Sin(x)","Cos(x)","Tan(x)"};
            menu = new JComboBox(functions);  
            menu.setFont(new Font("Monaco",Font.PLAIN,13));
            menu.addActionListener(this);
            menuPanel.add(menu);
        infoLabel = new JLabel("", SwingConstants.CENTER);
            infoLabel.setFont(new Font("Georgia",Font.PLAIN,16));
            topPanel.add(infoLabel);
         inputPanel = new JPanel(new FlowLayout()); 
            topPanel.add(inputPanel);
            
        /* BorderLayout Location: TOP
        This part of the code sets the input section of the program by crating a 
        loop to instantiate an Label and a TextField defined by a vector of that 
        class for each coefficient used in the program while adding it to the panel 
        added to the top section of the frame */
                
        for(int i=0;i<6;i++){
            String[] labelText = {"a =","b =","c =","d =","h =","k ="};
            coefficientLabel[i] = new JLabel(labelText[i]); 
            coefficientLabel[i].setFont(style2);
            coefficientLabel[i].setVisible(false);
            textField[i]= new JTextField(3);
            textField[i].setVisible(false);
            inputPanel.add(coefficientLabel[i]);
            inputPanel.add(textField[i]);
        }
                
       
        //Center
        displayPanel = new JPanel(new GridLayout(2,1));
        frame.add(displayPanel, BorderLayout.CENTER);
        controlPanel = new JPanel( new FlowLayout());
        displayPanel.add(controlPanel);
        
        /*Setup control buttons that will be used to zoom 
        and clear the drawing panel*/
        colorPanel = new JPanel( new FlowLayout());
        displayPanel.add(colorPanel);
        clearButton = new JButton("Clear");
            clearButton.setFont(style);
            clearButton.addActionListener(this);
            controlPanel.add(clearButton);
        zoomButton = new JButton("+Zoom");
            zoomButton.setFont(style);
            zoomButton.addActionListener(this);
            controlPanel.add(zoomButton);
        zoomButton2 = new JButton("-Zoom");
            zoomButton2.setFont(style);
            zoomButton2.addActionListener(this);
            controlPanel.add(zoomButton2);
        
        /* Create the JButtons for the colors that are added to a JPanel in the lower section of 
        the "display" JPanel added to the central part of the border layout of the JFrame with a
        Grid layout of (2,1) those buttons are grouped in a ButtonGroup called colorButtons which 
        is used to erase the selection using clearSelection() method and are added to an actionlistener
        that tracks the seletion of a certain button to set the correct color
        */
        colorButtons = new ButtonGroup();
        
        blueButton = new JRadioButton("Azul",false);
        redButton = new JRadioButton("Vermelho",false);
        greenButton = new JRadioButton("Verde",false);     
        orangeButton = new JRadioButton("Laranja",false); 
        
        blueButton.addActionListener(this);
        redButton.addActionListener(this);
        greenButton.addActionListener(this);
        orangeButton.addActionListener(this);
        
        blueButton.setFont(style);
        redButton.setFont(style);
        greenButton.setFont(style);
        orangeButton.setFont(style);
        
        colorButtons.add(redButton);
        colorButtons.add(blueButton);
        colorButtons.add(greenButton);
        colorButtons.add(orangeButton);
        
        colorPanel.add(blueButton);
        colorPanel.add(redButton);
        colorPanel.add(greenButton);
        colorPanel.add(orangeButton);
        
        //Setup the number formats used to print the results
        df1 = new DecimalFormat("0.##");
        df2 = new DecimalFormat("+0.##;-0.##");
        df3 = new DecimalFormat("0.###");
        
        //Bottom
        calcButton = new JButton("Desenhar Gráfico");
        calcButton.addActionListener(this);
        frame.add(calcButton, BorderLayout.SOUTH);
        calcButton.setFont(style);
        
        //Drawing Plane setup
        plane = new DrawingPanel(600,600);
        g = plane.getGraphics();
        g.setFont(new Font("Futura",Font.PLAIN,14)); 
        drawPlane();
        g.setFont(new Font ("Times",Font.ITALIC,14));
        frame.setVisible(true);
        
    }
    @Override
    public void actionPerformed(ActionEvent e){
        
        if(colorButtons.getSelection()== null){
            g.setColor(Color.black);
        }
        if(blueButton.isSelected()){
            g.setColor(Color.blue);       
        }
        if(redButton.isSelected()){
            g.setColor(Color.red);        
        }
        if(orangeButton.isSelected()){
            g.setColor(Color.orange);
        }
        if(greenButton.isSelected()){
            g.setColor(Color.green);
        }
        
        String function = (String)this.menu.getSelectedItem();
        switch(function){
            case "1ºGrau":
                this.infoLabel.setText("f(x)= ax+b");
                setTextFieldVisibility();
                if(e.getSource()== this.calcButton){
                    
                    String tx1 = textField[0].getText();
                    double a = Double.parseDouble(tx1);
                
                    String tx2 = textField[1].getText();
                    double b = Double.parseDouble(tx2);
        
                    drawPolinomialFunction(1,0,0,a,b);
                    colorButtons.clearSelection();
                }
                break;
            case "2ºGrau":
                this.infoLabel.setText("f(x)= ax^2+bx+c");
                setTextFieldVisibility();
                if(e.getSource()== this.calcButton){
                    
                    String tx1 = textField[0].getText();
                    double a = Double.parseDouble(tx1);
                
                    String tx2 = textField[1].getText();
                    double b = Double.parseDouble(tx2);
        
                    String tx3 = textField[2].getText();
                    double c = Double.parseDouble(tx3);
                    
                    drawPolinomialFunction(2,0,a,b,c);
                    colorButtons.clearSelection();
                }
                break;
            case"3ºGrau":
                this.infoLabel.setText("f(x)= ax^3+bx^2+cx+d");
                setTextFieldVisibility();
                if(e.getSource()==this.calcButton){
                    
                    String tx1 = textField[0].getText();
                    Double a = Double.parseDouble(tx1);
                
                    String tx2 = textField[1].getText();
                    double b = Double.parseDouble(tx2);
                    
                    String tx3 = textField[2].getText();
                    Double c = Double.parseDouble(tx3);
                
                    String tx4 = textField[3].getText();
                    double d = Double.parseDouble(tx4);
                    
                    drawPolinomialFunction(3,a,b,c,d);
                    colorButtons.clearSelection();    
                }
                break;
            case"Exponencial":
                this.infoLabel.setText("f(x)= a^(x-h)+k");
                setTextFieldVisibility();
                
                if(e.getSource()==this.calcButton){
                    String tx1 = textField[0].getText();
                    Double a = Double.parseDouble(tx1);
                    
                    String tx3 = textField[4].getText();
                    Double h = Double.parseDouble(tx3);
                
                    String tx4 = textField[5].getText();
                    double k = Double.parseDouble(tx4);
                    
                    drawFunctionEq(0,a,0,h,k);
                    drawExp(a,h,k);
                    colorButtons.clearSelection();    
                }
                break;
            case"Sin(x)":
                this.infoLabel.setText("f(x)= a*Sin(b*(x-h))+k");
                setTextFieldVisibility();
                
                if(e.getSource()== this.calcButton){
                        
                        String tx1 = textField[0].getText();
                        double a = Double.parseDouble(tx1);
                
                        String tx2 = textField[1].getText();
                        double b = Double.parseDouble(tx2);
        
                        String tx3 = textField[4].getText();
                        double h = Double.parseDouble(tx3);
                        
                        String tx4 = textField[5].getText();
                        double k = Double.parseDouble(tx4);
                        
                        drawFunctionEq(1,a,b,h,k);
                        drawSinx(a,b,h,k);
                        colorButtons.clearSelection();
                }
                
                break;
            case"Cos(x)":
                this.infoLabel.setText("f(x)= a*Cos(b*(x-h))+k");
                setTextFieldVisibility();
                 
                if(e.getSource()== this.calcButton){
                        
                        String tx1 = textField[0].getText();
                        double a = Double.parseDouble(tx1);
                
                        String tx2 = textField[1].getText();
                        double b = Double.parseDouble(tx2);
        
                        String tx3 = textField[4].getText();
                        double h = Double.parseDouble(tx3);
                        
                        String tx4 = textField[5].getText();
                        double k = Double.parseDouble(tx4);
                        
                        drawFunctionEq(2,a,b,h,k);
                        drawCosx(a,b,h,k);    
                        colorButtons.clearSelection();
                }
                
                break;
            case"Tan(x)":
                this.infoLabel.setText("f(x)= a*Tan(b*(x-h))+k");
                setTextFieldVisibility();
                if(e.getSource()== this.calcButton){
                        
                        String tx1 = textField[0].getText();
                        double a = Double.parseDouble(tx1);
                
                        String tx2 = textField[1].getText();
                        double b = Double.parseDouble(tx2);
        
                        String tx3 = textField[4].getText();
                        double h = Double.parseDouble(tx3);
                        
                        String tx4 = textField[5].getText();
                        double k = Double.parseDouble(tx4);
                        
                        drawFunctionEq(3,a,b,h,k);
                        drawTanx(a,b,h,k);
                        
                        colorButtons.clearSelection();
                }
                break;
        }
        
        if(e.getSource()== this.clearButton ){
            plane.clear();
            height =0;
            drawPlane();
        }
        if(e.getSource()== this.zoomButton){
            this.plane.zoom(2);    
        }
        if(e.getSource()== this.zoomButton2){  
            this.plane.zoom(1);     
        }
       
    }
    
    public void drawPlane(){
       
        g.setColor(Color.black);
        g.drawLine(0, 300, 600, 300);
        g.drawLine(300,0,300,600);
        
        for(int i =0;i<31;i++){
            g.drawLine(i*20, 296, i*20, 304);
        }
        for(int i=0;i<30;i++){
            g.drawLine(296, i*20, 304, i*20);
        }
        
        for(int i=0;i<15;i++){
            g.drawString(String.valueOf(i-15),20*i-6,322);
        }
        for(int i=1;i<15;i++){
            g.drawString(String.valueOf(i),298+20*i,322);
        }    
        int k=14;
        for(int i=0;i<14;i++){
            g.drawString(String.valueOf(k),280,23+20*i);
            k = k-1;
        }
        for(int i=2;i<15;i++){
            g.drawString(String.valueOf(-i), 274, 305+20*i);
        }
    }    
        
    public void setTextFieldVisibility(){
       
        String function = this.infoLabel.getText();
        boolean ast = false;
        boolean bst= false;
        boolean cst = false;
        boolean dst = false;
        boolean hst = false;
        boolean kst = false;
        
        for(int i=0;i<function.length();i++){
            switch(function.charAt(i)){
                case'a': ast = true; 
                break;
                case'b': bst = true;
                break;
                case'c': cst = true;
                break;
                case'd': dst = true;
                break;
                case'h': hst = true;
                break;
                case'k': kst = true;
                break;  
            }
        }
        coefficientLabel[0].setVisible(ast);
        textField[0].setVisible(ast); 
        coefficientLabel[1].setVisible(bst);
        textField[1].setVisible(bst); 
        coefficientLabel[2].setVisible(cst);
        textField[2].setVisible(cst);
        coefficientLabel[3].setVisible(dst);
        textField[3].setVisible(dst);
        coefficientLabel[4].setVisible(hst);
        textField[4].setVisible(hst); 
        coefficientLabel[5].setVisible(kst);               
        textField[5].setVisible(kst); 
    }    
    
    public void drawFunctionEq(int type,double a,double b,double h,double k){
        String stra = df1.format(a);
        String trgf = "";
        String strb = df1.format(b);
        String strh = df2.format(h);
        String strk = df2.format(k);
        String function = "";
        
        switch(type){
            case 1: trgf = "Sin";
                break;
            case 2: trgf = "Cos";
                break;
            case 3: trgf = "Tan";
                break;
        }
        
        if(a==0){
            stra = "";
        }
        if(b==0){
            strb = "";
        }
        if(h==0){
            strh = "";
        }
        if(k==0){
            strk = "";
        }
        
        if(type == 0){
            function = "f(x)= "+stra+"^(x"+strh+")"+strk;
            g.drawString(function, 10, 20 + height);
        }
        else{
            function = "f(x)= "+stra+trgf+"("+strb+"(x"+strh+")"+")"+strk;
            g.drawString(function, 10, 20 + height);
        }
    }
    
    public void drawPolinomialFunction(int type,double a,double b,double c,double d) {
        
        for(double x=-300;x<=300;x++){
            double y1 = (a*((x-1)*(x-1)*(x-1))/400+b*((x-1)*(x-1))/20+c*(x-1)+d*20); 
            double y2 = (a*(x*x*x)/400+b*(x*x)/20+c*x+d*20); 
            this.g.drawLine(300+(int)(x-1), 300-(int)y1, 300+(int)x, 300-(int)y2);
        }
        
        String stra = df1.format(a)+"x³ ";
        String strb= df2.format(b)+"x² ";
        String strc= df2.format(c)+"x ";
        String strd= df2.format(d);
                
            if(a==0){
                stra = "";
            }
            if(b==0){
                strb = "";
            }
            if(c==0){
                strc = "";
            }
            if(d==0){
                strd = "";
            }
        
        switch(type){
            case 1:
                double x = ((d*-1)/c);
                g.drawString("f(x)= "+df1.format(c)+"x"+strd, 10, 20+height);
                g.drawString("{R}  x'= "+df3.format(x), 10, 590-height);
                
                break;
            case 2:
                g.drawString("f(x)= "+df1.format(b)+"x² "+strc+strd, 10, 20+height);
                        
                double delta = (c*c)-(4*b*d);
                double rDelta = Math.sqrt(delta);
                
                double x1 = (-c+rDelta)/(2*b);
                double x2 = (-c-rDelta)/(2*b);
                
                if(delta == 0){
                    g.drawString("{R}  x'= "+df3.format(x1), 10, 590-height);
                }
                if(delta>0){
                    g.drawString("{R}  x'= "+df3.format(x1)+" x''= "+df3.format(x2), 10, 590-height);
                }
                if(delta<0){
                    double xr = (-c)/(2*b);
                    double xi = (Math.sqrt(delta*-1))/(2*b);
                    g.drawString("{Im}  x'= "+df1.format(xr)+df2.format(xi)+"i "+" x''= "+df1.format(xr)+df2.format((xi*-1))+"i", 10, 590-height);
                }
                
                break;
            case 3:
                Cubic cubic = new Cubic();
                cubic.solve(a, b, c, d);
               
                g.drawString("f(x)= "+df1.format(a)+"x³ "+strb+strc+strd, 10, 20+height);
                g.drawString("{R}  x'= "+df3.format(cubic.x1)+" x''= "+df3.format(cubic.x2)+" x'''= "+df3.format(cubic.x3), 10, 590-height);
                                    
                break;
        }
        
        height = height + 20;
    } 
    
    public void drawExp(double a,double h,double k){
        
        for(double x=-300;x<300;x++){
            int y1 = 300-(int)(20*Math.pow((a),((x-1)-h*20)/20)+k*20);
            int y2 = 300-(int)(20*Math.pow((a),(x-h*20)/20)+k*20);
            g.drawLine(300+(int)(x-1),y1, 300+(int)x,y2);
        }
        
        double logk = Math.log10(k*-1);
        double loga = Math.log10(a);
        double x = (logk/loga) + (h); 
       
        g.drawString("{R}  x'= "+df3.format(x), 10, 590-height);
        height = height + 20;
    }
    
    public void drawSinx(double a,double b,double h,double k){
        for(int x=-300;x<300;x++){         
            int y1 = 300 - (int)Math.round(20*a* Math.sin(b*( ((x-1)/20.0)* 2 * Math.PI)- 90*Math.toRadians(h))+k*20);     
            int y2 = 300 - (int)Math.round(20*a* Math.sin(b*((x / 20.0) * 2 * Math.PI)- 90*Math.toRadians(h))+k*20);
            g.drawLine(300+(x-1),y1,300+x,y2);
        }
        height = height +20;
    }
    
    public void drawCosx(double a, double b,double h,double k){
        for(int x=-300;x<300;x++){            
            int y1 = 300 - (int)Math.round(20*a* Math.cos(b*( ((x-1)/20.0)* 2 * Math.PI)- 90*Math.toRadians(h))+k*20);       
            int y2 = 300 - (int)Math.round(20*a* Math.cos(b*((x / 20.0) * 2 * Math.PI)- 90*Math.toRadians(h))+k*20);
            g.drawLine(300+(x-1),y1,300+x,y2);
        }
        height = height +20;
    }
    
    public void drawTanx(double a, double b,double h,double k){
        for(int x=-300;x<300;x++){
            int y1 = 300 - (int)Math.round(20*a* Math.tan(b*( ((x-1)/20.0)* 2 * Math.PI)- 90*Math.toRadians(h))+k*20);      
            int y2 = 300 - (int)Math.round(20*a* Math.tan(b*((x / 20.0) * 2 * Math.PI)- 90*Math.toRadians(h))+k*20);
            g.drawLine(300+(x-1),y1,300+x,y2);
        }
        height = height +20;
    } 

}
    

