//File:		CodeJamBadHorse.java
//Author:	Gary Bezet
//Date:		2016-07-27
//Desc:		Designed to solve google Code Jam Bad Horse Practice from Round A APAC test 2016
//Problem:	https://code.google.com/codejam/contest/6234486/dashboard
//Results:	A-small-practice-1.in:  23ms        A-small-practice-1.in:  41ms
//License:      GNU GPLv3


package net.garyscorner.codejambadhorse;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class CodeJamBadHorse {

    
    //Variables
    public long starttime;
    
    //File streams
    private BufferedReader infile;
    private PrintStream outfile;
    
    private String infileopt, outfileopt; //Filename variables  
    
    private long linenum = 0; //Keeps track of the current line number of the infile for error printing purposes    
    
    private int totalcases;  //Total number of cases
    
    private TestCase[] testcases;  //Holds all the test cases
    
       
    
    /*
        Functions
    */
    
    //Program starts here
    public void run(String[] args) {
        this.loadoptions(args);  //Load arguments
        
        this.openFiles();
        
        this.loadData();
        
        boolean[] solveable = new boolean[totalcases];  //Store all the answers here
        long startsolve = System.currentTimeMillis();  //Start the solution time for metrics
        //Solve all cases
        for(int i=0; i<totalcases; i++) {
            solveable[i] = testcases[i].solutionexists();
        }
        System.err.printf("%1$d cases solved in %2$dms\n", totalcases, System.currentTimeMillis() - startsolve);
        
        long startoutput = System.currentTimeMillis(); //Start time for output
        System.err.printf("Outputting %1$d cases...\n", totalcases );
        for(int i=0; i<totalcases; i++) {
            if(solveable[i]) {  //Print yes if solvable no if not
                outfile.printf("Case #%1$d: Yes\r\n", i+1);
            } else {
                outfile.printf("Case #%1$d: No\r\n", i+1);
            }
            
        }
        System.err.printf("%1$d cases output to \"%2$s\" in %3$dms\n", totalcases, outfileopt, System.currentTimeMillis() - startoutput);
        
        
        this.closeFiles();  //Close files
    }
    
    //Load data from file into data structure
    private void loadData() {
        
        long loaddatastarttime = System.currentTimeMillis();  //The time data loading was started.
        
        System.err.printf("\nLoading data from \"%1$s\"...\n", this.infileopt);
        
        String line = this.infileReadLine();
        
        //Get case numbers
        try{
            totalcases = Integer.parseInt(line);
        } catch(NumberFormatException ex) {
            System.err.printf("Could not parse total number of cases (line #%1$d) from:  %2$s", this.linenum, this.infileopt);
            System.exit(4);
        }
        
        testcases = new TestCase[totalcases];  //Initialize testcase array
        
        //Load all test cases
        System.err.printf("Loading %1$d test cases...\n", totalcases);
        for( int i=0; i<totalcases; i++ ) {
            
            line = this.infileReadLine();
            
            int totalbeefs = 0;  //Total members
            
            try {
                totalbeefs = Integer.parseInt(line);
            } catch(NumberFormatException ex) {  //Catch bad totalbeefs number and panic
                System.err.printf("Error parsing number of beefs from line %1$d:  %2$s\n", this.linenum, ex);
                System.exit(5);
            }
            
            testcases[i] = new TestCase(i+1, totalbeefs);  //Initialize the test case
            
            //Load all the beefs from testcase
            for(int c=0; c<totalbeefs; c++) {
                line = this.infileReadLine();
                
                String[] beef = line.split(" ");  //get the two beefers
                
                if(beef.length != 2) {  //Panic if there are more/less than two beefers
                    System.err.printf("Error on line %1$d, there are %2$d beefers instead of 2\n", this.linenum, beef.length);
                    System.exit(6);
                }
                
                testcases[i].addbeef(beef);
                
            }
            
            
            //System.err.println(testcases[i]);//for testing
            
        }
        
        System.err.printf("All data loaded in %1$dms\n", System.currentTimeMillis() - loaddatastarttime);
        
    }
    
    //Open the files
    public void openFiles() {
        try {
            infile = new BufferedReader(new FileReader(infileopt));
        } catch (FileNotFoundException ex) {
            System.err.printf("Error could not open infile \"%1$s\":  $2%s\n", infileopt, ex.toString());
            System.exit(2);
        }
        
        if(outfileopt == null) {
            outfileopt = "Stdout";
            outfile = System.out;
        } else {
            try {
                outfile = new PrintStream(new File(outfileopt));
            } catch (FileNotFoundException ex) {
                System.err.printf("Error could not open outfile \"%1$s\":  $2%s\n", outfileopt, ex.toString());
                System.exit(2);
            }
        }
        
        System.err.printf("Infile:  %1$s\nOutfile:  %2$s\n", infileopt, outfileopt);
        
        
                
    }
    
    //Reads a line panic if exception
    private String infileReadLine() {  
        
        linenum++;

        try {
                return infile.readLine();
        } catch (IOException e) {
                System.err.printf("Unable to read line #%3$d from \"%1$s\":  %2$s\n", infileopt, e.toString(), this.linenum);
                System.exit(3);
        }

        return null;  //Get rid of netbeans error   
    }
    
    //Close files
    private void closeFiles() {
        try {
            infile.close();
        } catch (Exception ex) {
            //Do nothing the program is already finished
        }
        
        outfile.close();
    }
    
    //Loads the command line options
    private void loadoptions(String[] args) {
        
		
        if(  2 < args.length || args.length == 0 ) {
                System.err.println("Program requires 1 or 2 arguments.  First argument is infile name, 2nd argument is outfile name StdOut by default.");
                System.exit(1);  //Exit the system if arguments not correct
        }

        infileopt = args[0];

        if( args.length == 2 ) {  //Set the outfile if exists
                outfileopt = args[1];
        }

    }
    
    public static void main(String[] args) {
        //load class and call run()
        CodeJamBadHorse prog = new CodeJamBadHorse();
        prog.starttime = System.currentTimeMillis();
        prog.run(args);
        System.err.printf("Program finished in %1$dms\n", System.currentTimeMillis() - prog.starttime);
        
    }
    
}
