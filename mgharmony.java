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

	public Dot(double pradius, int protations)
	{
		angle = 0;
		radius = pradius;
		period = 10000;
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
	final int INITIAL_DOT_COUNT = 30;		
	final int INITIAL_STYLE = 4;		 	// 1 - 8
	final int WINDOW_WIDTH = 700;
	final int WINDOW_HEIGHT = 700;
	final int INITIAL_DOT_RADIUS = 3;

	Dot[] dots;
	Graphics g;
	int style;
	int dot_radius; 

	int num_dots_change_flag;
	int period_change_flag;

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

		initializeDots(INITIAL_DOT_COUNT);
		num_dots_change_flag = 0;

		style = INITIAL_STYLE;

		period_change_flag = 0;

		dot_radius = INITIAL_DOT_RADIUS;

	}

	public void initializeDots(int n) {
		dots = new Dot[n];
		for(int i = 0; i < dots.length; i++) {
			double rad = (int)(.95 * center_width()) /dots.length * (i+1);
			int rot = dots.length-i;
			dots[i] = new Dot(rad, rot);		
		}
	}

	public void tick() {

		//Check Change in Number of Dots
		if(num_dots_change_flag != 0) {
			if(num_dots_change_flag < 0)
				num_dots_change_flag *= -1;

			dots = null;

			initializeDots(num_dots_change_flag);

			num_dots_change_flag = 0;
		}

		// Check Change in Period
		if(period_change_flag != 0) {
			for(int i = 0; i<dots.length; i++)
				dots[i].setPeriod(period_change_flag);
			period_change_flag = 0;
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
			switch(style) {

			case 1:	//	Dot
				draw_dot(dots[i]);
				break;

			case 2:	//	Line To Center
				draw_line_to_center(dots[i]);
				break;
			
			case 3:	//	Line To Next Dot
				if(i == dots.length-1) break;
				draw_line_between_dots(dots[i], dots[i+1]);
				break;
			
			case 4:	//	Circle With Radius of the Distance to the Next Dot
				if(i == dots.length - 1) break;
				draw_circle_around_dot(dots[i], dist(dots[i], dots[i+1]));
				break;
			
			case 5:	//	Line to the Closest Dot
				draw_line_between_dots(dots[i], dots[closest_dot(i)]);			
				break;
			
			case 6:	// Circle, Touching Closest Dot
				int udist6 = dist(dots[i], dots[closest_dot(i)]);
				draw_circle_around_dot(dots[i], udist6);
				break;

			case 7:	// Circle, Touching Closest Dot, and Dot and Line to Closest Dot
				
				int udot = closest_dot(i);
				int udist7 = dist(dots[i], dots[udot]);
				draw_dot(dots[i]);
				draw_line_between_dots(dots[i], dots[udot]);
				draw_circle_around_dot(dots[i], udist7);
				break;
			
			case 8:	// Line to Closest Dot and to Next Dot
				if(i == dots.length-1) break;
				draw_line_between_dots(dots[i], dots[i+1]);
				draw_line_between_dots(dots[i], dots[closest_dot(i)]);
				break;
			}
		}
	}

	private void draw_dot(Dot d) {
		g.fillOval(	center_width() + d.getx() - dot_radius, 
					center_height() + d.gety() - dot_radius, 
					dot_radius * 2, 
					dot_radius * 2
		);
	}
	private void draw_line_to_center(Dot d) {
		g.drawLine(	center_width() + d.getx(), 
					center_width() + d.gety(), 
					center_width(), 
					center_height()
		);
	}
	private void draw_line_between_dots(Dot d1, Dot d2) {
		g.drawLine(	center_width() + d1.getx(),
					center_height() + d1.gety(), 
					center_width() + d2.getx(), 
					center_height() + d2.gety()
		);
	}
	private void draw_circle_around_dot(Dot d, int rad) {
		g.drawOval(	center_width() + d.getx() - rad, 
					center_height() + d.gety() - rad, 
					2 * rad, 
					2 * rad
		);
	}
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
					closest_index = -1;
				}
			}
		}
		if(closest_index < 0) return 0;
		return closest_index;
	}

	// simple wrapper functions
	
	public int width() { return getContentPane().getSize().width; }
	public int height() { return getContentPane().getSize().height; }
	public int center_width() { return width() / 2; }
	public int center_height() { return height() / 2; }
	public static void pause(long millisecs) {
		try 	{ 	Thread.sleep(millisecs); 	}
		catch	(InterruptedException e)		{ }
	}


	// Simulation Interface for the Menu

	public void changeNumDots(int n) 	{ num_dots_change_flag = n; }
	public int getNumDots() 			{ return dots.length; }

	public void changeStyle(int n) 		{ style = n; }
	public int getStyle() 				{ return style; }

	public void changePeriod(int n) 	{ period_change_flag = n; }
	public int getPeriod() 				{ return dots[0].getPeriod(); }



	// Main

	public static void main(String args[]) {
		mgharmony one = new mgharmony();
		(new Menu(one)).start();
		while(true) {	
			one.tick();
			pause(5);
		}
	}
}


class Menu extends Thread
{
	private BufferedReader buffer;
	mgharmony frame;

	public void run() 
	{
		while(true) {

			println("==============================\n\n\n");
			println("Current:");
			println("\n\tNum Dots " + frame.getNumDots());
			
			print("\tStyle: ");
			switch(frame.getStyle()) {
				case 1: println("Just Dots");	break;
				case 2: println("Line to the Center");	break;
				case 3: println("Line to the Next Dot");	break;
				case 4: println("Circle Touching the Next Dot");	break;
				case 5: println("Line to the Closest Dot");	break;
				case 6: println("Just Dots");	break;
				case 7: println("Just Dots");	break;
				case 8: println("Just Dots");	break;
				
				
				default: println("<error>");
			}

			println("\tPeriod: " + frame.getPeriod() + "\n");




			System.out.println("1: Set Number of Dots");
			System.out.println("2: Set Mode Number");
			System.out.println("3: Set New Speed");
			
			int choice = readInt("::> ");
			if(choice == 1) 
			{
				int n = readInt("Enter new number of dots: ");
				frame.changeNumDots(n);
			}
			else if(choice == 2) 
			{
				int n = readInt("Enter new Style [1-8]: ");
				frame.changeStyle(n);
			}
			else if(choice == 3)
			{
				int n = readInt("Enter New Speed: ");
				frame.changePeriod(n);
			}
		}
	}

	public int readInt(String prompt){
		
		print(prompt);
		
		for(int i = 0; i<10; i++)
		try {
			String s = (buffer.readLine()).trim();
			int value = (new Integer(s)).intValue();
			return value;
		}
		catch (Exception e){
			print("\nPlease Enter an Integer Value.\n\ttry again: ");
		}

		println("Well, you failed.");
		return 0;
	}
	
	public Menu(mgharmony _frame){
		InputStreamReader reader = new InputStreamReader (System.in);
		buffer = new BufferedReader (reader);
		frame = _frame;
	}   

	private void print(String str) { System.out.print(str); }
	private void println(String str) { print(str + "\n"); }

}




