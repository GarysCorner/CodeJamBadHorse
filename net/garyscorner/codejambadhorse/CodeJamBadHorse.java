//File:		CodeJamBadHorse.java
//Author:	Gary Bezet
//Date:		2016-07-27
//Desc:		Designed to solve google Code Jam Bad Horse Practice from Round A APAC test 2016
//Problem:	https://code.google.com/codejam/contest/6234486/dashboard
//Results:	A-small-practice-1.in:  23ms        A-small-practice-1.in:  41ms



package net.garyscorner.codejambadhorse;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class CodeJamBadHorse {

    
    //variables
    public long starttime;
    
    //file streams
    private BufferedReader infile;
    private PrintStream outfile;
    
    //filename variables
    private String infileopt, outfileopt;  
    
    private long linenum = 0; //keeps track of the current line number of the infile for error printing purposes    
    
    private int totalcases;  //total number of cases
    
    private TestCase[] testcases;  //holds all the test cases
    
       
    
    /*
        Functions
    */
    
    //program starts here
    public void run(String[] args) {
        this.loadoptions(args);  //load arguments
        
        this.openFiles();
        
        this.loadData();
        
        boolean[] solveable = new boolean[totalcases];  //store all the answers here
        long startsolve = System.currentTimeMillis();  //start the solution time for metrics
        //solve all cases
        for(int i=0; i<totalcases; i++) {
            solveable[i] = testcases[i].solutionexists();
        }
        System.err.printf("%1$d cases solved in %2$dms\n", totalcases, System.currentTimeMillis() - startsolve);
        
        long startoutput = System.currentTimeMillis(); //start time for output
        System.err.printf("Outputing %1$d cases...\n", totalcases );
        for(int i=0; i<totalcases; i++) {
            if(solveable[i]) {  //print yes if solveable no if not
                outfile.printf("Case #%1$d: Yes\r\n", i+1);
            } else {
                outfile.printf("Case #%1$d: No\r\n", i+1);
            }
            
        }
        System.err.printf("%1$d cases output to \"%2$s\" in %3$dms\n", totalcases, outfileopt, System.currentTimeMillis() - startoutput);
        
        
        this.closeFiles();  //close files
    }
    
    //load data from file into data structure
    private void loadData() {
        
        long loaddatastarttime = System.currentTimeMillis();  //the time we started loading data
        
        System.err.printf("\nLoading data from \"%1$s\"...\n", this.infileopt);
        
        String line = this.infileReadLine();
        
        //get case numbers
        try{
            totalcases = Integer.parseInt(line);
        } catch(NumberFormatException ex) {
            System.err.printf("Could not parse total number of cases (line #%1$d) from:  %2$s", this.linenum, this.infileopt);
            System.exit(4);
        }
        
        testcases = new TestCase[totalcases];  //initialize testcase array
        
        //load all test cases
        System.err.printf("Loading %1$d test cases...\n", totalcases);
        for( int i=0; i<totalcases; i++ ) {
            
            line = this.infileReadLine();
            
            int totalbeefs = 0;  //total members
            
            try {
                totalbeefs = Integer.parseInt(line);
            } catch(NumberFormatException ex) {  //catch bad totalbeefs number and panic
                System.err.printf("Error parsing number of beefs from line %1$d:  %2$s\n", this.linenum, ex);
                System.exit(5);
            }
            
            testcases[i] = new TestCase(i+1, totalbeefs);  //initilze the test case
            
            //load all the beefs from testcase
            for(int c=0; c<totalbeefs; c++) {
                line = this.infileReadLine();
                
                String[] beef = line.split(" ");  //get the two beefers
                
                if(beef.length != 2) {  //panic if there are more/less than two beefers
                    System.err.printf("Error on line %1$d, there are %2$d beefers instead of 2\n", this.linenum, beef.length);
                    System.exit(6);
                }
                
                testcases[i].addbeef(beef);
                
            }
            
            
            //System.err.println(testcases[i]);//for testing
            
        }
        
        System.err.printf("All data loaded in %1$dms\n", System.currentTimeMillis() - loaddatastarttime);
        
    }
    
    //open the files
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
    
    //reads a line panic if exception
    private String infileReadLine() {  
        
        linenum++;

        try {
                return infile.readLine();
        } catch (IOException e) {
                System.err.printf("Unable to read line #%3$d from \"%1$s\":  %2$s\n", infileopt, e.toString(), this.linenum);
                System.exit(3);
        }

        return null;  //get rid of netbeans error   
    }
    
    //close files
    private void closeFiles() {
        try {
            infile.close();
        } catch (Exception ex) {
            //do nothing the program is already finished
        }
        
        outfile.close();
    }
    
    //loads the command line options
    private void loadoptions(String[] args) {
        
		
        if(  2 < args.length || args.length == 0 ) {
                System.err.println("Program requires 1 or 2 arguments.  First arg is infile name, 2nd arg is outfile name StdOut by default.");
                System.exit(1);  //exit the system if arguments not correct
        }

        infileopt = args[0];

        if( args.length == 2 ) {  //set the outfile if exists
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
