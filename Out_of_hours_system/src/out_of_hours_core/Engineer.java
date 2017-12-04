/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package out_of_hours_core;

import java.util.*;

/**
 * The Engineer class represents support engineer that may function
 * in either a first line or escalation role.
 * 
 * @author Skenn
 */
public class Engineer {
    
    // Instance Variables
    private int shiftsAvailable;
    private int shiftsAssigned;
    private boolean isEscalation;
    private boolean isFirstLine;
    private User user;
    
    /**
     * Constructor for Engineer object.
     * @param isEscalation - If the engineer can operate as an escalation engineer
     * @param isFirstLine - If the engineer can operate as a first line engineer
     * @param aUser - The user object that owns the engineer object.
     */
    protected Engineer(boolean isEscalation, boolean isFirstLine, User aUser)
    {
        this.isEscalation = isEscalation;
        this.isFirstLine = isFirstLine;
        this.shiftsAssigned = 0;
        this.shiftsAvailable = 0;
        this.user = aUser;
    }
    
    // Getters
    /**
     * Returns true if the receiver is an Escalation Engineer.
     * @return True if the engineer is an escalation engineer.
     */
    public boolean isEscalation()
    {
        return this.isEscalation;
    }
    
    /**
     * Returns true is the receiver is a first line engineer.
     * @return True is the engineer is a first line engineer.
     */
    public boolean isFirstLine()
    {
        return this.isFirstLine;
    }
    
    /**
     * Returns the number of shifts the receiver has been marked as available for.
     * @return The number of shifts the receiver has been available for.
     */
    public int getShiftsAvailable()
    {
        return this.shiftsAvailable;
    }
    
    /**
     * Returns the number of shifts the receiver has been assigned.
     * @return number of shifts the receiver has been assigned.
     */
    protected int getShiftsAssigned()
    {
        return this.shiftsAssigned;
    }

    // Setters
    /**
     * Sets the isEscalation variable for the receiver.
     * @param aBoolean - Marks if the engineer can perform escalation shifts.
     */
    protected void setEscalation(boolean aBoolean)
    {
        this.isEscalation = aBoolean;
    }
    
    /**
     * Sets the isFirstLine variable for the receiver.
     * @param aBoolean - Marks if the engineer can perform first line shifts. 
     */
    protected void setFirstLine(boolean aBoolean)
    {
        this.isFirstLine = aBoolean;
    }
    
    // Methods
    /**
     * Increments the shiftAvailable variable for the receiver.
     */
    protected void incrementShiftsAvailable()
    {
        this.shiftsAvailable++;
    }
    
    /**
     * Decrements the shiftAvailable variable for the receiver.
     */
    protected void decrementShiftsAvailable()
    {
        this.shiftsAvailable--;
    }
    
    /**
     * Increments the shiftsAssigned variable for the receiver.
     */
    protected void incrementShiftsAssigned()
    {
        this.shiftsAssigned++;
    }
    
    /**
     * Decrements the shiftsAssigned variable for the receiver.
     */
    protected void decrementShiftsAssigned()
    {
        this.shiftsAssigned--;
    }     
    
    /**
     * Returns a string the represents the engineer
     * @return a string the represents the engineer.
     */
    public String toString()
    {
        return this.user.toString();
    }
}
