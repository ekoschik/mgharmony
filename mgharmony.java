import java.io.*;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.*;

/*
    An interesting animation created using the Java Swing Library,
    originally written by Joey Haig, as a side priject in Mr. McKeens
    AP Comp Sci class.

    I just found it after many years, and re-worked it a bit for fun.
    In High School I thought it was really cool, and I still do.

    Evan Koschik

*/


class Dot
{
    double angle;
    double radius;
    int period;
    int rotations;

    public Dot(double pradius, int protations, int pperiod)
    {
        angle = 0;
        radius = pradius;
        period = pperiod;
        rotations = protations;
    }
    
    public void tick() {
        angle += (rotations*360.0) / period;
        if(angle > 360) angle -= 360;
    }
    
    public int getx() {
        return (int)(Math.cos(Math.toRadians(angle))*radius);   
    }
    
    public int gety() {
        return (int)(Math.sin(Math.toRadians(angle))*radius);
    }

    public void setPeriod(int n) {
        if(n < 0) return;
        period = n;
    }
    public int getPeriod() {
        return period;
    }
}



public class mgharmony extends JFrame
{
    static int g_pause_count = 5;
    
    final int WINDOW_WIDTH = 700;
    final int WINDOW_HEIGHT = 700;

    Dot[] dots;
    Graphics g;
    StylesAndProperties styles;

    public StylesAndProperties getstyles() { return styles; }

    public mgharmony() {

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("MGharmony");
        setResizable(false);
        setVisible(true);
        setBackground(Color.white);
        setFont(new Font("dialog", Font.PLAIN, 60));

        createBufferStrategy(2);
        BufferStrategy buffer = getBufferStrategy();
        g = buffer.getDrawGraphics();

        
        styles = new StylesAndProperties();

        resetDots();

    }

    public void resetDots() {
        dots = new Dot[styles.num_dots];
        for(int i = 0; i < dots.length; i++) {
            double rad = (int)(.95 * center_width()) /dots.length * (i+1);
            int rot = dots.length-i;
            dots[i] = new Dot(rad, rot, styles.period);        
        }
    }

    public void tick() {

        // Check For Change in Number of Dots
        if(dots.length != styles.num_dots) {
            resetDots();
        }

        // Check Change in Period     
        if(dots[0].getPeriod() != styles.period) {
            for(int i = 0; i<dots.length; i++)
                dots[i].setPeriod(styles.period);
        }


        // Tick() All the Dots
        for(int i = 0; i < dots.length; i++) {
            dots[i].tick();
        }

        // Paint The World
        g.setColor(Color.white);
        g.fillRect(0, 0, width()*2, height()*2);
        paint_dots();
        getBufferStrategy().show();

    }

    public void paint_dots() 
    {
        g.setColor(Color.black);

        for(int i = 0; i < dots.length; i++) {
            if(styles.bit_dot)                      draw_dot(i);
            if(styles.bit_line_to_center)           draw_line_to_center(i);
            if(styles.bit_line_to_next_dot)         draw_line_to_next_dot(i);
            if(styles.bit_line_to_closest_dot)      draw_line_to_closest_dot(i);
            if(styles.bit_circle_to_next_dot)       draw_circle_to_next_dot(i);
            if(styles.bit_circle_to_closest_dot)    draw_circle_to_closest_dot(i);
        }
    }

    //Private Helper Functions to Draw All Different Styles

    public int width() { return getContentPane().getSize().width; }
    public int height() { return getContentPane().getSize().height; }
    public int center_width() { return width() / 2; }
    public int center_height() { return height() / 2 + 30; }

    private int dist(Dot d1, Dot d2) {
        return (int) Math.sqrt(
            Math.pow(d1.getx() - d2.getx(), 2) + 
            Math.pow(d1.gety() - d2.gety(), 2)
        );
    }
    private int closest_dot(int index) {
        int closest_index = -1;
        int closest_val = -1;
        for(int i = 0; i<dots.length; i++) {
            if(i != index) {
                int val = dist(dots[i], dots[index]);

                if(closest_index < 0 || val < closest_val) {
                    closest_val = val;
                    closest_index = i;
                }
            }
        }
        if(closest_index < 0) return 0;
        return closest_index;
    }
    private void draw_dot(Dot d) {
        g.fillOval( center_width() + d.getx() - styles.dot_radius, 
                    center_height() + d.gety() - styles.dot_radius, 
                    styles.dot_radius * 2, 
                    styles.dot_radius * 2
        );
    }
    private void draw_line_to_center(Dot d) {
        g.drawLine( center_width() + d.getx(), 
                    center_width() + d.gety(), 
                    center_width(), 
                    center_height()
        );
    }
    private void draw_line_between_dots(Dot d1, Dot d2) {
        g.drawLine( center_width() + d1.getx(),
                    center_height() + d1.gety(), 
                    center_width() + d2.getx(), 
                    center_height() + d2.gety()
        );
    }
    private void draw_circle_around_dot(Dot d, int rad) {
        g.drawOval( center_width() + d.getx() - rad, 
                    center_height() + d.gety() - rad, 
                    2 * rad, 
                    2 * rad
        );
    }
    private void draw_dot(int i) {
        draw_dot(dots[i]);
    }
    private void draw_line_to_center(int i) {
        draw_line_to_center(dots[i]);
    }
    private void draw_line_to_next_dot(int i) {
        if(i == dots.length-1) return;
        draw_line_between_dots(dots[i], dots[i+1]);
    }
    private void draw_line_to_closest_dot(int i) {
        draw_line_between_dots(dots[i], dots[closest_dot(i)]);
    }
    private void draw_circle_to_next_dot(int i) {
        if(i == dots.length-1) return;
        int dist = dist(dots[i], dots[i+1]);
        draw_circle_around_dot(dots[i], dist);
    }
    private void draw_circle_to_closest_dot(int i) {
        if(i == dots.length-1) return;
        int dist = dist(dots[i], dots[closest_dot(i)]);
        draw_circle_around_dot(dots[i], dist);
    }


        
    public static void pause(long millisecs) {
        try     {   Thread.sleep(millisecs);    }
        catch   (InterruptedException e)        { }
    }


    // Main

    public static void main(String args[]) {
        mgharmony one = new mgharmony();
        (new TerminalMenu(one)).start();
        while(true) {   
            one.tick();
            pause(g_pause_count);
        }
    }
}


