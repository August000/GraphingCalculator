
package graphingcalculator;

/*@author Augusto Batista
  Version 2.0   11/7/2018
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;

public class GraphingCalculator implements ActionListener {
    
    public static void main (String[] args){
        GraphingCalculator app = new GraphingCalculator();
    }
       
    private final JFrame frame; 
    private final JPanel topPanel;
    private final JComboBox menu;
    private final JPanel inputPanel;
    private final JLabel selectionLabel;
    private final JLabel infoLabel;
    
    private final JLabel alabel;
    private final JTextField atx;
    private final JLabel blabel;
    private final JTextField btx;
    private final JLabel clabel;
    private final JTextField ctx;
    private final JLabel dlabel;
    private final JTextField dtx;
    private final JLabel hlabel;
    private final JTextField htx;
    private final JLabel klabel;
    private final JTextField ktx;
   
    private final JButton calcButton;
    private final JButton clearButton;
    private final JButton zoomButton;
    private final JButton zoomButton2;
   
    /*
    private final JRadioButton blackButton;
    private final JRadioButton redButton;
    private final JRadioButton blueButton;
    private final JRadioButton yellowButton;
    private final JRadioButton greenButton;
    private final JRadioButton orangeButton;
    private final JRadioButton magentaButton;
    private final JRadioButton cyanButton;
    */
    public int zoom = 0; 
    private final DrawingPanel plane;
    private final Graphics g;
    
    
    public GraphingCalculator(){
    Font style = new Font("Geneva",Font.PLAIN,14);
    Font style2 = new Font("Georgia",Font.PLAIN,15);
    
        // Components
        JFrame frame = new JFrame("Raízes Equação 2ºGrau");
            frame.setSize(300, 250);
            frame.setResizable(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocation(50,150);
            this.frame = frame;
            // General Border Layout
            frame.setLayout(new BorderLayout());
        
        // Top
        JPanel topPanel = new JPanel(new GridLayout(3,1));
        this.topPanel = topPanel;
        frame.add(topPanel, BorderLayout.NORTH);
        JPanel menuPanel = new JPanel(new FlowLayout());
        topPanel.add(menuPanel);
        JLabel selectionLabel = new JLabel("Selecione uma função:", SwingConstants.CENTER );
        selectionLabel.setFont(style);
        this.selectionLabel = selectionLabel;
        menuPanel.add(selectionLabel);
            String[] functions = {"1ºGrau","2ºGrau","3ºGrau","Exponencial","Sin(x)","Cos(x)","Tan(x)"};
            JComboBox menu = new JComboBox(functions);  
            menu.setFont(new Font("Monaco",Font.PLAIN,13));
            menu.addActionListener(this);
            menuPanel.add(menu);
            this.menu = menu;
        JLabel infoLabel = new JLabel("", SwingConstants.CENTER);
            infoLabel.setFont(new Font("Georgia",Font.PLAIN,16));
            this.infoLabel = infoLabel;
            topPanel.add(infoLabel);
        JPanel inputPanel = new JPanel(new FlowLayout()); 
            topPanel.add(inputPanel);
            this.inputPanel = inputPanel;
        
            
        // Top - DataInput
                
                JLabel alabel = new JLabel("a =");
                alabel.setFont(style2);
                JTextField atx = new JTextField(3);
                JLabel blabel = new JLabel("b =");
                blabel.setFont(style2);
                JTextField btx = new JTextField(3);
                JLabel clabel = new JLabel("c =");
                clabel.setFont(style2);
                JTextField ctx = new JTextField(3);
                JLabel dlabel = new JLabel("d =");
                dlabel.setFont(style2);
                JTextField dtx = new JTextField(3);
                JLabel hlabel = new JLabel("h =");
                hlabel.setFont(style2);
                JTextField htx = new JTextField(3);
                JLabel klabel = new JLabel("k =");
                klabel.setFont(style2);
                JTextField ktx = new JTextField(3);
                
                inputPanel.add(alabel);
                inputPanel.add(atx);
                inputPanel.add(blabel);
                inputPanel.add(btx);
                inputPanel.add(clabel);
                inputPanel.add(ctx);
                inputPanel.add(dlabel);
                inputPanel.add(dtx);
                inputPanel.add(hlabel);
                inputPanel.add(htx);
                inputPanel.add(klabel);
                inputPanel.add(ktx);
                
                this.alabel = alabel;
                this.atx = atx;
                this.blabel = blabel;
                this.btx = btx;
                this.clabel = clabel;
                this.ctx = ctx;
                this.dlabel = dlabel;
                this.dtx = dtx;
                this.hlabel = hlabel;
                this.htx = htx;
                this.klabel = klabel;               
                this.ktx = ktx;
                
                alabel.setVisible(false);
                atx.setVisible(false); 
                blabel.setVisible(false);
                btx.setVisible(false); 
                clabel.setVisible(false);
                ctx.setVisible(false);
                dlabel.setVisible(false);
                dtx.setVisible(false);
                hlabel.setVisible(false);
                htx.setVisible(false); 
                klabel.setVisible(false);               
                ktx.setVisible(false); 
                
       
        //Center
        JPanel displayPanel = new JPanel(new GridLayout(2,1));
        frame.add(displayPanel, BorderLayout.CENTER);
        JPanel controlPanel = new JPanel( new FlowLayout());
        displayPanel.add(controlPanel);
        JPanel colorPanel = new JPanel( new FlowLayout());
        displayPanel.add(colorPanel);
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(style);
        JButton zoomButton = new JButton("+Zoom");
        JButton zoomButton2 = new JButton("-Zoom");
        zoomButton.addActionListener(this);
        zoomButton.setFont(style);
        zoomButton2.addActionListener(this);
        zoomButton2.setFont(style);
        clearButton.addActionListener(this);
        this.clearButton = clearButton;
        this.zoomButton = zoomButton;
        this.zoomButton2 = zoomButton2;
        controlPanel.add(clearButton);
        controlPanel.add(zoomButton);
        controlPanel.add(zoomButton2);
        /*
        JRadioButton blackButton = new JRadioButton("Black",false);
        blackButton.addActionListener(this);
        this.blackButton = blackButton;
        colorPanel.add(blackButton);
        JRadioButton redButton = new JRadioButton("Red",false);
        redButton.addActionListener(this);
        this.redButton = redButton;
        colorPanel.add(redButton);
        JRadioButton blueButton = new JRadioButton("Blue",false);
        blueButton.addActionListener(this);
        this.blueButton = blueButton;
        colorPanel.add(blueButton);
        JRadioButton yellowButton = new JRadioButton("Yellow",false);
        yellowButton.addActionListener(this);
        colorPanel.add(yellowButton);
        JRadioButton greenButton = new JRadioButton("Green",false);
        greenButton.addActionListener(this);
        colorPanel.add(greenButton);
        JRadioButton orangeButton = new JRadioButton("Orange",false);
        orangeButton.addActionListener(this);
        colorPanel.add(orangeButton);
        */
        
        // Botton
        JButton calcButton = new JButton("Desenhar Gráfico");
        calcButton.addActionListener(this);
        this.calcButton = calcButton;
        frame.add(calcButton, BorderLayout.SOUTH);
        calcButton.setFont(style);
        
        //Drawing Plane setup
        DrawingPanel plane = new DrawingPanel(600,600);
        this.plane = plane;
        Graphics g = plane.getGraphics();
        this.g = g;
        g.setFont(new Font("Futura",Font.PLAIN,14));
        
        drawPlane();
        
        
        
        
        frame.setVisible(true);
        
        
        
    }
    
    public void actionPerformed(ActionEvent e){
        
        
        
        String function = (String)this.menu.getSelectedItem();
        switch(function){
            case "1ºGrau":
                this.infoLabel.setText("f(x)= ax+b");
                this.alabel.setVisible(true);
                setVisibility();
                break;
            case "2ºGrau":
                this.infoLabel.setText("f(x)= ax^2+bx+c");
                setVisibility();
                this.g.setColor(Color.blue);
                if(e.getSource()== this.calcButton){
                    
                    String tx1 = atx.getText();
                    double a = Double.parseDouble(tx1);
                
                    String tx2 = btx.getText();
                    double b = Double.parseDouble(tx2);
        
                    String tx3 = ctx.getText();
                    double c = Double.parseDouble(tx3);
                    
                    Double delta = (b*b)-4*a*c;
                    Double rDelta = Math.sqrt(delta);
        
                    Double x1 = (-b + rDelta)/2*a;
                    Double x2 = (-b - rDelta)/2*a;
              
                    for(int x=-300;x<=300;x++){
                        int y2 = ((int)a*(x*x)/20+(int)b*x+(int)c*20); 
                        int y1 = ((int)a*((x-1)*(x-1))/20+(int)b*(x-1)+(int)c*20); 
                        g.drawLine((x-1)+300,300-y1,x+300, 300-y2);
                        System.out.println("x="+(x)+" y= "+((int)a*(x*x)+(int)b*x+(int)c));
                    }
            
                    g.drawString("x'= "+x1+"  "+"x''="+x2, 10, 590);
                    g.drawString("f(x)= "+a+"x", 0, 0);
             
                }
                break;
            case"3ºGrau":
                this.infoLabel.setText("f(x)= ax^3+bx^2+cx+d");
                setVisibility();
                break;
            case"Exponencial":
                this.infoLabel.setText("f(x)= ab^x");
                setVisibility();
                
                if(e.getSource()==this.calcButton){
                    String tx1 = atx.getText();
                    Double a = Double.parseDouble(tx1);
                
                    String tx2 = btx.getText();
                    double b = Double.parseDouble(tx2);
                    
                    for(int x=-300;x<300;x++){
                        g.drawLine(300+(x-1),300-((int)Math.pow((a*b),x-1)), 300+x, 300-((int) Math.pow(a*b,x/20)));
                    }
                }
                break;
            case"Sin(x)":
                this.infoLabel.setText("f(x)= a*Sin(b*(x-h))+k");
                setVisibility();
                this.g.setColor(Color.red);
                if(e.getSource()== this.calcButton){
                        
                        String tx1 = atx.getText();
                        double a = Double.parseDouble(tx1);
                
                        String tx2 = btx.getText();
                        double b = Double.parseDouble(tx2);
        
                        String tx3 = htx.getText();
                        double h = Double.parseDouble(tx3);
                        
                        String tx4 = ktx.getText();
                        double k = Double.parseDouble(tx4);
                        
                        drawSinx(a,b,h,k);
                        
                    
                }
                break;
            case"Cos(x)":
                this.infoLabel.setText("f(x)= a*Cos(b*(x-h))+k");
                setVisibility();
                break;
            case"Tan(x)":
                this.infoLabel.setText("f(x)= a*Sin(b*(x-h))+k");
                setVisibility();
                break;
                        
        }
        
        
        
        
        if(e.getSource()== this.clearButton ){
            this.plane.clear();
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
    public void setVisibility(){
       
        String function = this.infoLabel.getText();
        boolean ast = false;
        boolean bst= false;
        boolean cst = false;
        boolean dst = false;
        boolean hst = false;
        boolean kst = false;
        for(int i=0;i<function.length();i++){
            if(function.charAt(i)=='a'){
            ast = true;
            }
            if(function.charAt(i)=='b'){
            bst = true;
            }
            if(function.charAt(i)=='c'){
            cst= true;
            }
            if(function.charAt(i)=='d'){
            dst = true;
            }
            if(function.charAt(i)=='h'){
            hst = true;
            }
            if(function.charAt(i)=='k'){
            kst = true;
            }
        }
        alabel.setVisible(ast);
        atx.setVisible(ast); 
        blabel.setVisible(bst);
        btx.setVisible(bst); 
        clabel.setVisible(cst);
        ctx.setVisible(cst);
        dlabel.setVisible(dst);
        dtx.setVisible(dst);
        hlabel.setVisible(hst);
        htx.setVisible(hst); 
        klabel.setVisible(kst);               
        ktx.setVisible(kst); 
    }    
    public void drawSinx(double a,double b,double h,double k){
        for(int i=-6000;i<6000;i++){
            double rad1 = Math.toRadians(i-1);
            double rad2 = Math.toRadians(i);
            g.drawLine(300+i/18, 300-(int)(a*20*Math.sin(b*(rad1-(h*20)))+k*20), 300+i/18,300-(int)(a*20*Math.sin(b*(rad2-(h*20)))+k*20));
        }    
    }

}
    

