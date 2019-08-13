package AHKT;

import java.awt.*;
import javax.swing.*;

public class PanelCoordinate extends JFrame{
    public PanelCoordinate() throws Exception{
        Surface tampilan = new Surface();
        add(tampilan); 
        setTitle("Koordinat awal");
        setSize(830, 630);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

class Surface extends JPanel{ 
    private int[] X;
    private int[] Y;
    private int Jumlah;
    
    public Surface (){
        int[][] x = Library.GetCoordinate();
        int n = x.length;
        Jumlah = n;
        X = new int[n];
        Y = new int[n];
        for(int i = 0; i < n; i++) {
            // ini diganti menyusuaian ukuran
            X[i] = (int)x[i][0];
            Y[i] = (int)x[i][1];
        }
        
        int min_x = Library.Min(X);
        int max_x = Library.Max(X);
        
        int min_y = Library.Min(Y);
        int max_y = Library.Max(Y);
        
        int rentang_x = max_x - min_x;
        int rentang_y = max_y - min_y;
        
        for(int i = 0; i < n; i++) {
            X[i] = X[i] - min_x;
            Y[i] = Y[i] - min_y;
        }
        
        for(int i = 0; i < n; i++) {
            X[i] = (int)((double)X[i] * 750 / (double)rentang_x) + 25;
            Y[i] = (int)((double)Y[i] * 550 / (double)rentang_y) + 25;
            Y[i] = 600 - Y[i];
        }
    }
    
    private void doDrawing(Graphics g){
        Graphics g2d = (Graphics2D) g;
        
        Color bl = Color.BLUE;
        Color bc = Color.BLACK;

        g2d.setColor(bc);
        for(int i = 0 ; i < Jumlah ; i++) {
            //ini besar lingkaran
            int z = 2;
            g2d.fillOval(X[i]-z,Y[i]-z,2*z+1,2*z+1);
            //g2d.drawString(i + "", X[i]+2, Y[i]-2);
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
}