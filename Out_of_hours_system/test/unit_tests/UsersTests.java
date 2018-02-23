package unit_tests;


import java.util.*;
import out_of_hours_core.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Skenn
 */
public class UsersTests {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try
        {
            SingleSignOn aSSO = new SingleSignOn(12345678);
            
            User aUser = new User("Test", "00000", "Email", "abc", aSSO, 
                true, true, true);
            
            Manager aManager = aUser.getManager();
            
            Calendar cal = Calendar.getInstance();
            cal.set(2017, 10, 20);
            Date aDate = cal.getTime();
            
            OohCoord coord = new OohCoord();
        
            // user case M; Create Quarter.
            coord.createQuarter(aUser.getManager(), 1, true, aDate);
            
            for (Quarter aQuarter : coord.getQuarters())
            {
                System.out.println("Quarter number = " + aQuarter.getQuarter());
                System.out.println("Quarter isLong = " + aQuarter.getLong());
                System.out.println("Quarter startDate = " + aQuarter.getStartDate());
            
                // Use case O; Generate shifts for Quarter
                System.out.println("Generate Shifts");
                coord.generateShifts(aManager, aQuarter);
                System.out.println("Collect shifts from Quarter");
                Collection<Shift> shifts = coord.getShifts(aQuarter);
                
                int i = 0;
                for(Shift aShift : shifts)
                {
                    System.out.println("Iteration: " + ++i);
                    System.out.println("Shift StartDate = " + aShift.getStartDate());
                    System.out.println("Shift EndDate = " + aShift.getEndDate());
                }
            }   
            // Use case A: Create an engineer
            User user1 = aManager.CreateUser("Bob", "07777898898", "bob@ge.com", new SingleSignOn(20002000), true, true, false);
            User user2 = aManager.CreateUser("Bobette", "07745321321", "bobette@ge.com", new SingleSignOn(20002001), true, true, false);
            
            System.out.println("User1 details Name = " + user1.getName()
                    + " phone number = " + user1.getPhoneNumber()
                    + " email = " + user1.getEmail()
                    + " password = " + user1.getPassword());
            System.out.println("user2 details Name = " + user2.getName()
                    + " phone number = " + user2.getPhoneNumber()
                    + " email = " + user2.getEmail()
                    + " password = " + user2.getPassword());
                
            // Use case G; Make available for shift.
            SortedSet quarters = (SortedSet) coord.getQuarters();
                
            System.out.println("Get the first available Quarter");
            Quarter aQuarter = (Quarter) quarters.first();
            System.out.println("Get the shifts in the quarter");
            SortedSet shifts = (SortedSet) coord.getShifts(aQuarter);
            System.out.println("Get the first available Shift");
            Shift aShift = (Shift) shifts.first();
                
            System.out.println("Set the engineers as available for the shift.");
            coord.setAvailableForShift(aShift, Type.FIRST, user1.getEngineer());
            coord.setAvailableForShift(aShift, Type.ESCALATION, user2.getEngineer());
            
            aShift.assignEngineers();
            System.out.println("Engineers on shift are " + aShift.getFirstLineEngineer() 
                    + " and " + aShift.getEscalationEngineer());
            
        }
        catch (Exception anException)
        {
            System.out.println("Error: " + anException);
        }
        
    }
    
}
