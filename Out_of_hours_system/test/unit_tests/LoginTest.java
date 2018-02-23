/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit_tests;

import java.util.*;
import out_of_hours_core.*;

/**
 * Tests that the OohCoord.java login function works correctly.
 * @author Skenn
 */
public class LoginTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String correctPassword = "password";
        String incorrectPassword = "dfg456";
        
        // Test that login works correctly for a new user
        try
        {
            OohCoord coord = new OohCoord();
            Manager aManager = new Manager();
            
            SingleSignOn aSSO = new SingleSignOn(21222122);
            
            coord.createUser(aManager,"Dave","01506777888","aUser@aPlace.com", aSSO, true, true, true);
            
            User aUser = coord.login(aSSO, correctPassword);
            
            System.out.println("The users name is: " + aUser.getName());
        }
        catch (Exception anException)
        {
            System.out.println("Error: " + anException);
        }
        
        // Test that login works correctly for a new user
        try
        {
            OohCoord coord = new OohCoord();
            Manager aManager = new Manager();
            
            SingleSignOn aSSO = new SingleSignOn(21222122);
            
            coord.createUser(aManager,"Dave","01506777888","aUser@aPlace.com", aSSO, true, true, true);
            
            User aUser = coord.login(aSSO, incorrectPassword);
            
            System.out.println("The users name is: " + aUser.getName());
        }
        catch (Exception anException)
        {
            System.out.println("Error: " + anException);
        }
        
        try
        {
            OohCoord coord = new OohCoord();
            
            SingleSignOn aSSO = new SingleSignOn(10000000);
            
            User aUser = coord.login(aSSO, "password");
            
            System.out.println("The users name is: " + aUser.getName());
        }
        catch (Exception anException)
        {
            System.out.println("Error: " + anException);
        }
    }
    
}
