/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package out_of_hours_core;

import java.util.*;
/**
 * OOH System user, composed of a Engineer and/or a Manager
 * object.
 * 
 * @author Skenn
 */
public class User implements java.io.Serializable
{
    // Instance Variables
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private SingleSignOn sso;
    
    // Links
    private Engineer engineer;
    private Manager manager;
    
    /**
     * Constructor for the user
     * 
     * @param aName
     * @param aPhoneNumber
     * @param aEmail
     * @param aPassword
     * @param aSso
     * @param isFirstLine
     * @param isEscalation
     * @param isManager 
     */
    public User(String aName, String aPhoneNumber, String aEmail, String aPassword, 
            SingleSignOn aSso, boolean isFirstLine, boolean isEscalation, boolean isManager)
    {
        this.name = aName;
        this.phoneNumber = aPhoneNumber;
        this.email = aEmail;
        this.password = aPassword;
        this.sso = aSso;
        
        this.engineer = new Engineer(isFirstLine, isEscalation, this);
        
        if(isManager)
        {
            this.manager = new Manager();
        }
    }
    
    //Getters
    
    /**
     * Returns the users name.
     * @return the full name of the user.
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Returns the phone number of the receiver.
     * @return Phone number of the user.
     */
    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }
    
    /**
     * Returns the email of the receiver.
     * @return Email address of the user.
     */
    public String getEmail()
    {
        return this.email;
    }
    
    /**
     * Return the SSO of the receiver.
     * @return The single sign on identifier of the user
     */
    protected SingleSignOn getSSO()
    {
        return this.sso;
    }
    
    /**
     * Return the password of the receiver.
     * @return 
     */
    public String getPassword()
    {
        return this.password;
    }
    
    /**
     * Returns a engineer object linked to the User.
     * 
     * @return engineer object linked to the User
     */
    public Engineer getEngineer()
    {
        return this.engineer;
    }
    
    /**
     * Returns a manager object link to User or null if the user is not
     * a Manager.
     * 
     * @return manager object linked to a user.
     */
    public Manager getManager()
    {
        return this.manager;
    }
    
    // Setters
    /**
     * Sets users name to aName
     * @param aName 
     */
    protected void setName(String aName)
    {
        this.name = aName;
    }
    
    /**
     * Sets the email address of the receiver.
     * @param aEmail 
     */
    protected void setEmail(String aEmail)
    {
        this.email = aEmail;
    }
    
    /**
     * Sets the phone number of the receiver.
     * @param aPhoneNumber 
     */
    protected void setPhoneNumber(String aPhoneNumber)
    {
        this.phoneNumber = aPhoneNumber;
    }
    
    /**
     * Sets the password of the receiver.
     * @param aPassword 
     */
    protected void setPassword(String aPassword)
    {
        this.password = aPassword;
    }
    
    /**
     * Sets the SSO of the receiver.
     * @param aSSO 
     */
    protected void setSSO(SingleSignOn aSSO)
    {
        this.sso = aSSO;
    }    

    /**
     * Sets the receivers Engineers first line variable.
     * @param aBool - True if user can perform first line shifts.
     */
    protected void setFirstLine(boolean aBool) 
    {
        this.engineer.setFirstLine(aBool);
    }
    
    /**
     * Sets the receivers Engineers escalation variable.
     * @param aBool - True if user can perform escalation shifts.
     */
    protected void setEscalation(boolean aBool) 
    {
       this.engineer.setEscalation(aBool);
    }
    
    /**
     * Creates or removes a manager object for the user
     * If a Manager already exists and aBool is true nothing happens
     * If a Manager does not exist and aBool is false nothing happens.
     * @param aBool - True if user is an out of hours rota manager.
     */
    protected void setManager(boolean aBool)
    {
        if ( (this.manager != null) && (!aBool) )
        {
            this.manager = null;
        }
        else if ( ( this.manager == null ) && (aBool) )
        {
            this.manager = new Manager();
        }  
    }
    
    /**
     * Returns a string that represents the receiving object.
     * 
     * @return String representation of the receiver.
     */
    @Override
    public String toString()
    {
        return this.getName() + ", " + this.getSSO().toString();
    }
}
