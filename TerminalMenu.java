
import java.io.*;

class StylesAndProperties {
    
    // These are checked for changes by the frame every tick 
    public int num_dots;
    public int period;
    public int dot_radius; 

    // These are checked every per dot, every tick, to decide what to draw
    public boolean bit_dot = true;
    public boolean bit_line_to_center = false;
    public boolean bit_line_to_next_dot = false;
    public boolean bit_line_to_closest_dot = false;
    public boolean bit_circle_to_next_dot = false;
    public boolean bit_circle_to_closest_dot = false;

    //Defaults and Initialization
    final int INITIAL_DOT_COUNT = 30;
    final int INITIAL_DOT_PERIOD = 10000;
    final int INITIAL_DOT_RADIUS = 3;
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


public class TerminalMenu extends Thread
{
    StylesAndProperties styles;
    private BufferedReader buffer;
    mgharmony frame;

    public TerminalMenu(mgharmony _frame){
        InputStreamReader reader = new InputStreamReader (System.in);
        buffer = new BufferedReader (reader);
        frame = _frame;
        styles = frame.getstyles();
    }   

    public void run() 
    {

        println("\n\n\n\n\n");
        println("##############################################");
        for(int i = 0; i<3; ++i)
            println("#                                            #");
        println("#                mgharmony                   #");
        for(int i = 0; i<3; ++i)
            println("#                                            #");
        println("##############################################\n");
        


        while(true) {

            println("\n\nMenu :\n\ncmd key\t\t\tcurrent \texplanation\n");
            println(" [ N : Number ]\t\t" + styles.num_dots + "\t\tNumber of dots");
            println(" [ P : Period ]\t\t" + (styles.period / 100)  + " (x100)\tPeriod (number of ticks in a full rotation)");
            println(" [ R : Radius ]\t\t" + styles.dot_radius + "\t\tDots radius (pixels)");
            println(" [ S : Styles ]\t\t...\t\tToggle Style Bits");
            
            String choice = readLine(">");
            
            if(choice.equalsIgnoreCase("n") || choice.equalsIgnoreCase("number")) 
            {
                int n = readInt("Enter new number of dots: ");
                if(n < 0)
                {
                    n = - n;
                    println("I'll assume you mean " + n + ", and not a negative amount.");
                }
                styles.num_dots = n;
                println("\nNumber of dots set to " + n + ".\n");
            }
            else if(choice.equalsIgnoreCase("p") || choice.equalsIgnoreCase("period"))
            {
                int n = readInt("Enter New Period (x100): ");
                if(n < 0) n *= -1;
                styles.period = n * 100;
            }
            else if(choice.equalsIgnoreCase("r") || choice.equalsIgnoreCase("radius")) 
            {
                int n = readInt("Enter a New Radius: ");
                if(n < 0) n *= -1;
                styles.dot_radius = n;

            }
            else if(choice.equalsIgnoreCase("s") || choice.equalsIgnoreCase("styles"))
            {

                String s_on = "\ton\t";
                String s_off = "\toff\t";
                int toggle_index = 1; 

                while(toggle_index > 0)
                {
                    println("\nCurrent Style Bits :\n");

                    print(" [ 1 ]" + (styles.bit_dot ? s_on : s_off));
                    println("Dot (radius " + styles.dot_radius + ")");

                    print(" [ 2 ]" + (styles.bit_line_to_center ? s_on : s_off));
                    println("Line To Center");

                    print(" [ 3 ]" + (styles.bit_line_to_next_dot ? s_on : s_off));
                    println("Line To Next Dot");

                    print(" [ 4 ]" + (styles.bit_line_to_closest_dot ? s_on : s_off));
                    println("Line To Closest Dot");

                    print(" [ 5 ]" + (styles.bit_circle_to_next_dot ? s_on : s_off));
                    println("Circle to Next Dot");

                    print(" [ 6 ]" + (styles.bit_circle_to_closest_dot ? s_on : s_off));
                    println("Circle To Closest Dot");


                    println("\n[ 0 ] to exit.\n");
                    toggle_index = readInt("Pick a Style Bit to toggle > ");

                    switch(toggle_index) {
                        case 1:
                            if(styles.bit_dot)
                                styles.bit_dot = false;
                            else
                                styles.bit_dot = true;
                            break;

                        case 2:
                            if(styles.bit_line_to_center)   
                                styles.bit_line_to_center = false;
                            else
                                styles.bit_line_to_center = true;
                            break;

                        case 3:
                            if(styles.bit_line_to_next_dot)  
                                styles.bit_line_to_next_dot = false;
                            else 
                                styles.bit_line_to_next_dot = true;
                            break;

                        case 4:
                            if(styles.bit_line_to_closest_dot)  
                                styles.bit_line_to_closest_dot = false;
                            else 
                                styles.bit_line_to_closest_dot = true;
                            break;

                        case 5:
                            if(styles.bit_circle_to_next_dot)  
                                styles.bit_circle_to_next_dot = false;
                            else 
                                styles.bit_circle_to_next_dot = true;
                            break;

                        case 6:
                            if(styles.bit_circle_to_closest_dot)  
                                styles.bit_circle_to_closest_dot = false;
                            else 
                                styles.bit_circle_to_closest_dot = true;
                            break;
                    }
                }   
            } 


            println("\n##############################################\n");
        }
    }

    public int readInt(String prompt){
        print(prompt);
        while(true)
        {
            String s = readLine("");
            try {
                int value = (new Integer(s)).intValue();
                return value;
            }
                catch (Exception e){
                println("\nThis needs to be an Integer Value.\n\ttry again: ");
            }
        }
    }
    public String readLine(String prompt){
        print(prompt);
        try {
            String value = buffer.readLine();
            return value;
        }
        catch (Exception e){
            readLine("Error on buffer.readLine. Exiting.");
            System.exit(0);
        }
        return "";
    }
    
    private void print(String str) { System.out.print(str); }
    private void println(String str) { System.out.println(str); }

}



