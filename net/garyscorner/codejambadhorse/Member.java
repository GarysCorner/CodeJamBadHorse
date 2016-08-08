//File:		Member.java
//Author:	Gary Bezet
//Date:		2016-07-27
//Desc:		Designed to solve google Code Jam Bad Horse Practice from Round A APAC test 2016
//Problem:	https://code.google.com/codejam/contest/6234486/dashboard
//Results:	None yet

package net.garyscorner.codejambadhorse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class Member {
    
    //variables
    public String name;
    public List<String> beefs; //a list of all beefs this member has
    
    public enum Team{ Red, Blue, None };
    public Team team = Team.None;
    
    //functions
    public Member(String name) {
        this.name = name;
        beefs = new ArrayList<String>();
    }
    
    //add a beef to the members beef list
    public void addbeef(String beefname) {
        if(!beefs.contains(beefname)) {
            beefs.add(beefname);
        }
    }
    
    
    //return true if we can place on this team
    public boolean canplaceon(ArrayList<String> team) {
        for(String beef : beefs) {
            if(team.contains(beef)) {
                return false;  //return false if there are beefs on this team
            }
        }
        return true;  //return true if we dont have beefs on this team
    }
    
    public boolean placeinteam(ArrayList<String> Red, ArrayList<String> Blue, Team teampref, Map<String, Member> members) {
        
        //this member is already on team
        if(this.team != Team.None) {
            if(teampref == this.team || teampref == Team.None) {
                return true;
            } else {
                return false;
            }
            
        } else {
            //try to place on red team
            if(teampref == Team.None || teampref == Team.Red) {
                if(this.canplaceon(Red)) {
                    //place on red team
                    this.team = Team.Red;
                    //place beefs in the other team
                    for(String beef : beefs) {
                        if(!members.get(beef).placeinteam(Red, Blue, Team.Blue, members)) {
                            return false;
                        }
                    }
                    return true;
                } 
                
            }
            
            //try to place in blue
            if( teampref == Team.None || teampref == Team.Blue ) {
                if(this.canplaceon(Blue)) {
                    //place on blue team
                    this.team = Team.Blue;
                    //place beefs in the other team
                    for(String beef : beefs) {
                        if(!members.get(beef).placeinteam(Red, Blue, Team.Red, members)) {
                            return false;
                        }
                    }
                    return true;
                }
                
            } 
        }
        //couldnt be placed on team
        return false;
    }
    
   
    
    @Override
    public String toString() {
        String object = "\tMember:  " + this.name + System.lineSeparator();
        object += "\t\t\tBeefs:" + System.lineSeparator();
        for(String beef : beefs) {
            object += "\t\t\t\t" + beef + System.lineSeparator();
        }
        
        object += System.lineSeparator();
        
        return object;
        
    }
}
