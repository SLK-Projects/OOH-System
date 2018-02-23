/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package out_of_hours_core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
/**
 * This is the coordinating class the will control interaction to objects in
 * the core system.
 * 
 * @author Skenn
 */
public class OohCoord implements Serializable
{
    // Links
    private static Collection<User> users  = new HashSet<>();;
    private static Collection<Quarter> quarters = new TreeSet<>();
    
    /**
     * Constructor: Creates a new OohCoord object. There can only be one
     * coordinating object in a system.
     */
    public OohCoord()
    {
        this.createDefaultUser();
        this.readOohDetails("ooh.csv");
    }
    
    //Getters
    
    /**
     * Returns a set of users linked to the receiver.
     * @return all users in the system.
     */
    public Collection<User> getUsers()
    {
        return this.users;
    }
    
    /**
     * Returns all the shifts for the quarter provided
     * as an argument.
     * @param aQuarter containing shifts.
     * @return The shifts in the quarter.
     */
    public Collection<Shift> getShifts(Quarter aQuarter)
    {
        return aQuarter.getShifts();
    }
    
    /**
     * Returns all quarters.
     * @return all quarters in the system
     */
    public Collection<Quarter> getQuarters()
    {
        return this.quarters;
    }
    
    /**
     * Returns the two types of engineer assigned to the shift.
     * @param aShift that has assigned engineers.
     * @return the engineers assigned to the shift.
     */
    public Map<Type, Engineer> getShiftEngineers(Shift aShift)
    {
        Map<Type, Engineer> aMap = new HashMap<>();
        
        aMap.put(Type.FIRST, aShift.getFirstLineEngineer());
        aMap.put(Type.ESCALATION, aShift.getEscalationEngineer());
        
        return aMap;
    }
    
   /**
    * Creates and returns a new OohCoord object.
    * Reads in the state of the object from the
    * file Ooh.data; if there is no such file,
    * or if it is not compatible, returns the object
    * in its initial state.
    *
    * @return a new OohCoord object
    */
   public static OohCoord getOohCoord()
   {
      OohCoord coord = null;
      FileInputStream fis = null;

      try
      {
         fis = new FileInputStream("ooh.data");
         ObjectInputStream ois = new ObjectInputStream(fis);
         coord = (OohCoord) ois.readObject();
      }
      catch (Exception ex)
      {
         // let user know that previous data file does not exist or is not compatible
         System.out.println("Data file does not exist or is incompatible with this version of the software.");
         System.out.println("Ooh will be initialised to default state");
         coord = new OohCoord(); // initialise ooh to default state.
         
         coord.save(); //and save it
      }
      finally // as we are not exiting make sure the fis stream is closed.
      {
         try
         {
            if (fis != null)
            {
               fis.close();
            }
         }
         catch (Exception ex)
         {
            System.out.println("Error closing file.");
         }
      }
      
      if (coord == null)
      {
          coord = new OohCoord();
      }
      
      if (coord.getUsers().isEmpty())
      {
          coord.createDefaultUser();
      }
      
      return coord;
   }
    
    //Setters
    /**
     * Adds an engineer to the available engineers list for a given Type
     * for the provided shift.
     * @param aShift
     * @param aType
     * @param aEngineer 
     */
    public void setAvailableForShift(Shift aShift, Type aType, Engineer aEngineer)
    {
        if (aType.equals(Type.FIRST))
        {
            aShift.addToFirstLineEngineers(aEngineer);
        }
        else if (aType.equals(Type.ESCALATION))
        {
            aShift.addToAvailableEscalationEngineers(aEngineer);
        }
    }
    
    /**
     * Remove aEngineer from available engineers collection
     * for a given type and shift.
     * @param aShift
     * @param aType
     * @param aEngineer 
     */
    public void setUnvailableForShift(Shift aShift, Type aType, Engineer aEngineer)
    {
        if (aType.equals(Type.FIRST))
        {
            aShift.removeFromFirstLineEngineers(aEngineer);
        }
        else if (aType.equals(Type.ESCALATION))
        {
            aShift.removeFromAvailableEscalationEngineers(aEngineer);
        }
    }
    
    //Methods
    
    public void changeShift(Manager aManager, Shift aShift, 
                            Engineer currentEngineer, Engineer newEngineer)
    {
        aManager.changeShift(aShift, currentEngineer, newEngineer);
    }
    
    /**
     * Returns a User object is aSSO matches a users SSO and aPassword
     * matches that Users Password.
     * @param aSSO
     * @param aPassword
     * @return the user object with the specified unique identifier.
     * @throws Exception if the username or password is incorrect.
     */
    public User login(SingleSignOn aSSO, String aPassword) throws Exception
    {        
        for (User aUser : this.getUsers())
        { 
            if ( aUser.getSSO().toString().equals(aSSO.toString() ) && 
                 aUser.getPassword().equals(aPassword) )
            {
                return aUser;
            }
        }
        throw new Exception("No such user or password incorrect");
    }
    
    /**
     * Create a quarter and adds the quarters to the quarters collection.
     * @param aManager
     * @param quarterNum
     * @param isLong
     * @param startDate 
     */
    public void createQuarter(Manager aManager, int quarterNum, Boolean isLong, 
                            Date startDate)
    {
        Quarter aQuarter = aManager.createQuarter(quarterNum, isLong, startDate);
        this.quarters.add(aQuarter);
    }
    
    /**
     * Generates the shifts for a quarter.
     * @param aManager
     * @param aQuarter 
     */
    public void generateShifts(Manager aManager, Quarter aQuarter)
    {
        aManager.generateShifts(aQuarter);
    }
    
    /**
     * Assigns engineers for all the shifts in a given quarter.
     * @param aManager
     * @param aQuarter 
     */
    public void assignShifts(Manager aManager, Quarter aQuarter)
    {
        aManager.assignShifts(aQuarter);
    }

    public void getCurrentQuarter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Creates a new user and adds it to the users collection.
     * @param aManager
     * @param aName
     * @param PhoneNumber
     * @param email
     * @param aSSO
     * @param isEscalation
     * @param isFirstLine
     * @param isManager 
     */
    public void createUser(Manager aManager, String aName, String PhoneNumber,
                            String email, SingleSignOn aSSO, boolean isEscalation,
                            boolean isFirstLine, boolean isManager)
    {
        // Create the new users
        User aUser = aManager.CreateUser(aName, PhoneNumber, email, aSSO, 
                            isEscalation, isFirstLine, isManager);
        
        // Preserves the new user in the users list.
        this.getUsers().add(aUser);
    }
    
   /**
    * Saves the state of the receiver
    * to the file ooh.data.
    */
   public void save()
   {
      try
      {
         FileOutputStream fos = new FileOutputStream("ooh.data");
         ObjectOutputStream oos = new ObjectOutputStream(fos);
         oos.writeObject(this);
      }
      catch (Exception ex)
      {
         System.out.println("Problem storing state of oohCoord " + ex + "/n Message:" + ex.getMessage());
         System.exit(1);
      }
   }
   
   /**
    * Sets up a ooh system with engineers, quarters and shifts detailed in setupFile. 
    * This should be invoked only by the constructor.
    */
   private void readOohDetails(String setupFile)
   {
      System.out.println("Function not implemented yet");
   }

   /**
    * Creates the default system user.
    */ 
   private void createDefaultUser() 
    {
        try
        {
            Manager aManager = new Manager();
            SingleSignOn aSSO = new SingleSignOn(10000000);
            this.createUser(aManager, "root", "0", "root@root.com", aSSO, true, true, true);
        }
        catch (Exception anException)
        {
            System.out.println("Error: Something has gone horrible wrong. " + anException);
        }
    }

}
