/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package out_of_hours_core;

import java.util.*;
/**
 * The Manager class represents an out of hours rota manager.
 * @author Skenn
 */
public class Manager implements java.io.Serializable
{
    // Instance variables
    private final String DEFAULT_PASSWORD = "password";
    /**
     * Construct a manager object.
     */
    public Manager()
    {}
    
    // Methods
    /**
     * Creates and returns a new user object.
     * @param aName - Name of the new user
     * @param aPhoneNumber - Phone number of the new user.
     * @param aEmail - Email address of the new user.
     * @param aSSO - Unique identifier for the new user.
     * @param isEscalation - If the engineer can perform escalation shifts.
     * @param isFirstLine - If the engineer can perform first line shifts.
     * @param isManager - If the new user is a out of hours rota manager.
     * @return a new User object.
     */
    public User CreateUser(String aName, String aPhoneNumber, String aEmail, SingleSignOn aSSO,
                               boolean isEscalation, boolean isFirstLine, boolean isManager)
    {
        User aUser = new User(aName, aPhoneNumber, aEmail, DEFAULT_PASSWORD, 
            aSSO, isFirstLine, isEscalation, isManager);
        
        return aUser;
    }
    
    /**
     * Sets password for aUser to default password "password".
     * @param aUser - User that has to be reset.
     */
    protected void resetPassword(User aUser)
    {
        aUser.setPassword(DEFAULT_PASSWORD);
    }
    
    /**
     * Sets the SSO for aUser to aSSO.
     * @param aUser - User who's SSO has to be reset
     * @param aSSO - New unique identifier for the user.
     */
    protected void setSSO(User aUser, SingleSignOn aSSO)
    {
        aUser.setSSO(aSSO);
    }
    
    /**
     * Sets aUser firstLine and escalation to be false.
     * The user will no longer be able to perform any shifts.
     * @param aUser - The user to be disabled.
     */
    protected void disableEngineer(User aUser)
    {
        aUser.setFirstLine(false);
        aUser.setEscalation(false);
    }
    
    /**
     * Sets aUser engineer isFirstLine to be aBool.
     * @param aUser - User to be changed.
     * @param aBool - True if user can perform first line shifts.
     */
    protected void setFirstLine(User aUser, boolean aBool)
    {
        aUser.setFirstLine(aBool);
    }
    
    /**
     * Sets aUser isEscalation to be aBool.
     * @param aUser - User to be changed.
     * @param aBool - True if user can perform escalation shifts.
     */
    protected void setEscalation(User aUser, boolean aBool)
    {
        aUser.setEscalation(aBool);
    }

    /**
     * Creates or removes a Manager object for aUser.
     * @param aUser - User to be changed.
     * @param aBool - True is user is a manager.
     */
    protected void setManager(User aUser, boolean aBool)
    {
        aUser.setManager(aBool);
    }
    
    /**
     * Changes Current engineer to new engineer a given shift.
     * @param aShift - Shift that needs altered.
     * @param currentEngineer - Engineer currently assigned to the shift.
     * @param newEngineer - Engineer that will be taking over the shift.
     */
    protected void changeShift(Shift aShift, Engineer currentEngineer, 
                                Engineer newEngineer)
    {
        aShift.changeEngineer(currentEngineer, newEngineer);
    }
    
    /**
     * Creates a Quarter object
     * @param quarterNum - The number the represents the quarter (1-4)
     * @param isLong - If the quarter is long, 14 weeks rather than 12 weeks.
     * @param startDate - The date that the quarter will start on.
     * @return A empty quarter object.
     */
    public Quarter createQuarter(int quarterNum, boolean isLong, Date startDate)
    {
        try
        {
            Quarter aQuarter = new Quarter(quarterNum, isLong, startDate);
            return aQuarter;
        }
        catch(Exception aException)
        {
            System.out.println("Error: Unable to create quarter: " + aException);
        }
        return null;
    }
    
    /**
     * Generates shifts for a given quarter.
     * @param aQuarter - The requires shifts.
     */
    public void generateShifts(Quarter aQuarter)
    {
        aQuarter.generateShifts();
    }
    
    /**
     * Assigns first line and escalation engineer to a shift.
     * @param aQuarter with populated shifts that will be assigned engineers.
     */
    public void assignShifts(Quarter aQuarter)
    {
        aQuarter.assignShifts();
    }
}
