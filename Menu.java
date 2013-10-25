
import java.io.*;

class StylesAndProperties {
    
    final int INITIAL_DOT_COUNT = 30;
    final int INITIAL_DOT_PERIOD = 10000;
    final int INITIAL_DOT_RADIUS = 3;

    public int num_dots;
    public int period;
    public int dot_radius; 

    public boolean bit_dot = true;
    public boolean bit_line_to_center = false;
    public boolean bit_line_to_next_dot = false;
    public boolean bit_line_to_closest_dot = false;
    public boolean bit_circle_to_next_dot = false;
    public boolean bit_circle_to_closest_dot = false;

    public StylesAndProperties() {

        num_dots = INITIAL_DOT_COUNT;
        period = INITIAL_DOT_PERIOD;
        dot_radius = INITIAL_DOT_RADIUS;

        bit_dot = true;
        bit_line_to_center = false;
        bit_line_to_next_dot = false;
        bit_line_to_closest_dot = false;
        bit_circle_to_next_dot = false;
        bit_circle_to_closest_dot = false;
    }

}


public class Menu extends Thread
{
    StylesAndProperties styles;
    private BufferedReader buffer;
    mgharmony frame;

    public void run() 
    {

        println("\n\n\n\n\n");
        println("\t**mgharmony**\n\n\n");



        while(true) {

            print_styles();

            System.out.println("1: Set Number of Dots");
            System.out.println("2: Set New Period");
            System.out.println("3: Change Style Bits");
            
            int choice = readInt("please enter a choice ::> ");

            if(choice == 1) 
            {
                int n = readInt("Enter new number of dots: ");
                if(n > 0)
                   styles.num_dots = n;
            }
            else if(choice == 2)
            {
                int n = readInt("Enter New Speed: ");
                if(n > 0)
                    styles.period = n;
            }
            else if(choice == 3) 
            {
                int style_choice = 1;
                while(style_choice > 0) {

                    print_styles();

                    style_choice = readInt("Enter a Style to turn on/off [1-6]: ");

                    switch(style_choice) {
                        case 1:
                            if(styles.bit_dot)  
                                styles.bit_dot = false;
                            else styles.bit_dot = true;
                            break;

                        case 2:
                            if(styles.bit_line_to_center)  
                                styles.bit_line_to_center = false;
                            else styles.bit_line_to_center = true;
                            break;

                        case 3:
                            if(styles.bit_line_to_next_dot)  
                                styles.bit_line_to_next_dot = false;
                            else styles.bit_line_to_next_dot = true;
                            break;

                        case 4:
                            if(styles.bit_line_to_closest_dot)  
                                styles.bit_line_to_closest_dot = false;
                            else styles.bit_line_to_closest_dot = true;
                            break;

                        case 5:
                            if(styles.bit_circle_to_next_dot)  
                                styles.bit_circle_to_next_dot = false;
                            else styles.bit_circle_to_next_dot = true;
                            break;

                        case 6:
                            if(styles.bit_circle_to_closest_dot)  
                                styles.bit_circle_to_closest_dot = false;
                            else styles.bit_circle_to_closest_dot = true;
                            break;
                    }


                }

            }
        }
    }

    private void print_on_off(boolean b) {
        if(b) print("*ON* ----> ");
        else  print(" off       ");
    }
    public void print_styles() {
        println("==============================\n\n\n");

        println("Number of dots:\t\t" + styles.num_dots);
        println("Period (ticks per circle:\t" + styles.period);
        println("Dot Radius\t\t" + styles.dot_radius);
        println("\n\tStyles Turned On:\n");

        print_on_off(styles.bit_dot);
        println("1\tDot (radius " + styles.dot_radius + ")");

        print_on_off(styles.bit_line_to_center);
        println("2\tLine To Center");

        print_on_off(styles.bit_line_to_next_dot);
        println("3\tLine To Next Dot");

        print_on_off(styles.bit_line_to_closest_dot);
        println("4\tLine To Closest Dot");

        print_on_off(styles.bit_circle_to_next_dot);
        println("5\tCircle to Next Dot");

        print_on_off(styles.bit_circle_to_closest_dot);
        println("6\tCircle To Closest Dot");

        println("==============================\n\n\n");
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
        styles = frame.getstyles();
    }   

    private void print(String str) { System.out.print(str); }
    private void println(String str) { print(str + "\n"); }

}



