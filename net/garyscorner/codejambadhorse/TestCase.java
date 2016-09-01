//File:		TestCase.java
//Author:	Gary Bezet
//Date:		2016-07-27
//Desc:		Designed to solve google Code Jam Bad Horse Practice from Round A APAC test 2016
//Problem:	https://code.google.com/codejam/contest/6234486/dashboard
//Results:	A-small-practice-1.in:  23ms        A-small-practice-1.in:  41ms
//License:      GNU GPLv3



package net.garyscorner.codejambadhorse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



public class TestCase {

    //Variables
    private int casenum;
    private int totalbeefs;
    private Map<String, Member> members;
    private String[][] beefs; //all the beefs
    int beefspos = 0;  //Current position in beefs
    
    //Function
    public TestCase(int casenum, int totalbeefs) {
        this.casenum = casenum;
        this.totalbeefs = totalbeefs;
        
        //Initialize members hashmap
        this.members = new HashMap<String, Member>();
        this.beefs = new String[totalbeefs][2];
    }
    
    //Adds a beef
    public void addbeef(String[] beef) {
        
        //Load the beefs into local beef String[][]
        this.beefs[beefspos][0] = beef[0];
        this.beefs[beefspos][1] = beef[1];
        this.beefspos++;  //increment beefs position after the loop
        
        //Do he same for both beefers
        for(int i=0; i<2; i++) {
            
            //Add member if not already added
            if(!members.containsKey(beef[i])) {
                members.put(beef[i], new Member(beef[i]));
            }
            
            if(i==0) {
                members.get(beef[0]).addbeef(beef[1]);
            } else {
                members.get(beef[1]).addbeef(beef[0]);
            }
            
            
        }
        
        
    }
    
    //Returns true if there is a solution otherwise false
    public boolean solutionexists() {
       
        ArrayList<String> Red = new ArrayList<String>();
        ArrayList<String> Blue = new ArrayList<String>();
        
        for( Entry<String, Member> memberentry : members.entrySet() ) {
            Member member = memberentry.getValue();
               if(!member.placeinteam(Red, Blue, Member.Team.None, members)) {
                    return false;
                } 
        }
        
        return true;  //Return true if the solution hasn't already failed
        
    }
    
    @Override
    public String toString() {
        String object = "Case #" + Integer.toString(casenum) + System.lineSeparator()
                + "Beefs:  " + Integer.toString(this.totalbeefs) + System.lineSeparator();
        
        
        
        for(Entry<String, Member>member : members.entrySet()) {
            object += member.getValue().toString();
        }
        
        return object;
    }
    
}
