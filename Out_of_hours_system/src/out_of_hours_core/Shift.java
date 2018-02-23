/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package out_of_hours_core;

import java.util.*;
/**
 * A shift in a quarter.
 * @author Skenn
 */
public class Shift implements Comparable<Shift>
{
    // Instance Variables
    private Date startDate;
    private Engineer firstLineEngineer;
    private Engineer escalationEngineer;
    private Collection<Engineer> availableEscalationEngineers;
    private Collection<Engineer> availableFirstLineEngineers;
    
    /**
     * Constructs a new shift object with startDate set to aDate.
     * @param aDate 
     */
    protected Shift(Date aDate)
    {
        this.startDate = aDate;
        this.availableEscalationEngineers = new HashSet<>();
        this.availableFirstLineEngineers = new HashSet<>();
    }
    
    // Getters
    /**
     * Returns the start date of the receiver.
     * @return 
     */
    public Date getStartDate()
    {
        return this.startDate;
    }
    
    /**
     * Returns the first line engineer of the receiver.
     * @return 
     */
    public Engineer getFirstLineEngineer()
    {
        return this.firstLineEngineer;
    }
    
    /**
     * Returns the escalation engineer of the receiver.
     * @return 
     */
    public Engineer getEscalationEngineer()
    {
        return this.escalationEngineer;
    }
    
    /**
     * Returns the date that the shift will end.
     * @return 
     */
    public Date getEndDate()
    {
        // Clone the date so we don't overwrite the startDate.
        Date endDate = (Date) startDate.clone();
        
        // Create a Calander instance to perform the addition to the Date.
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        
        // If the shift starts on a weekday it ends on the friday.
        if (this.isWeekday())
        {
            cal.add(Calendar.DATE, 4);
        }
        else
        // The shift started on a Friday and will end on the Monday.
        {
            cal.add(Calendar.DATE, 3);
        }
        endDate = cal.getTime();
        
        return endDate;
    }
    
    // Setters
    /**
     * If the escalationEngineer has not already been set then
     * find the best possible engineer from the availableEscalationEngineers
     * Collection.
     * 
     * The best engineer is the one who has been assigned the least number
     * of shifts.
     * 
     * Then increment the number of shifts they've been assigned.
     */
    private void setEscalationEngineer()
    {
        if (this.escalationEngineer == null)
        {
            Engineer bestChoice = null;

            for(Engineer aEngineer : this.availableEscalationEngineers)
            {
                if (bestChoice == null)
                {
                    bestChoice = aEngineer;
                }    
                else if (aEngineer.getEscalationShiftsAssigned() < bestChoice.getEscalationShiftsAssigned())
                {
                    bestChoice = aEngineer;
                }
            }
            this.escalationEngineer = bestChoice;
            this.escalationEngineer.incrementEscalationShiftsAssigned();
        }
    }
    
        /**
     * If the firstLineEngineer has not already been set then
     * find the best possible engineer from the availableFirstLineEngineers
     * Collection.
     * 
     * The best engineer is the one who has been assigned the least number
     * of shifts.
     * 
     * Then increment the number of shifts they've been assigned.
     */
    private void setFirstLineEngineer()
    {
        if (this.firstLineEngineer == null)
        {
            Engineer bestChoice = null;

            for(Engineer aEngineer : this.availableFirstLineEngineers)
            {
                if (bestChoice == null)
                {
                    bestChoice = aEngineer;
                }    
                else if (aEngineer.getFirstLineShiftsAssigned() < bestChoice.getFirstLineShiftsAssigned())
                {
                    bestChoice = aEngineer;
                }
            }
            this.firstLineEngineer = bestChoice;
            this.firstLineEngineer.incrementFirstLineShiftsAssigned();
        }
    }
    
    
    // Methods
    /**
     * Returns true if the day between Mon - Friday.
     * @return 
     */
    protected boolean isWeekday()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        
        return (day == Calendar.MONDAY);
    }
    
    /**
     * Adds aEngineer to availableEscalationEngineers list
     * and increments aEngineers shifts available.
     * @param aEngineer 
     */
    protected void addToAvailableEscalationEngineers(Engineer aEngineer)
    {
        this.availableEscalationEngineers.add(aEngineer);
        aEngineer.incrementShiftsAvailable();
    }
    
    /**
     * Adds aEngineer to availableFirstLineEngineers list
     * and increments aEngineers shifts available.
     * @param aEngineer 
     */
    protected void addToFirstLineEngineers(Engineer aEngineer)
    {
        this.availableFirstLineEngineers.add(aEngineer);
        aEngineer.incrementShiftsAvailable();
    }
    
    /**
     * Removes aEngineer to availableEscalationEngineers list
     * and decrements aEngineers shifts available.
     * @param aEngineer 
     */
    protected void removeFromAvailableEscalationEngineers(Engineer aEngineer)
    {
        this.availableEscalationEngineers.remove(aEngineer);
        aEngineer.decrementShiftsAvailable();
    }
    
    /**
     * Removes aEngineer to availableFirstLineEngineers list
     * and decrements aEngineers shifts available.
     * @param aEngineer 
     */
    protected void removeFromFirstLineEngineers(Engineer aEngineer)
    {
        this.availableFirstLineEngineers.remove(aEngineer);
        aEngineer.decrementShiftsAvailable();
    }
    
    /**
     * If the currentEngineer is either the firstLineEngineer or the
     * escalationEngineer then they are replaced with newEngineer.
     * @param currentEngineer
     * @param newEngineer 
     */
    protected void changeEngineer(Engineer currentEngineer, Engineer newEngineer)
    {
        if (currentEngineer == this.firstLineEngineer)
        {
            this.firstLineEngineer = newEngineer;
        }
        else if (currentEngineer == this.escalationEngineer)
        {
            this.escalationEngineer = newEngineer;
        }
    }
    
    /**
     * Assigns the most suitable first line engineer and escalation
     * engineer to the shift.
     */
    public void assignEngineers()
    {
        this.setEscalationEngineer();
        this.setFirstLineEngineer();
    }
    
    /**
     * Returns true is o is a Shift object with startDate
     * that is equal to those of the receiver, otherwise return false.
     * 
     * @param o the object to be checked for equality with the receiver
     * @return true if the receiver and argument are equal, false otherwise
     */
    public boolean equals(Object o)
    {
        if (!(o instanceof Shift))
        {
            return false;
        }
        Shift s = (Shift) o;
        return (s.startDate.equals(this.startDate));
    }
    
        /**
     * Returns the hashcode of the shift.
     *
     * @return hashcode
     */
    @Override
    public int hashCode()
    {
        int code = 17;
        code = 37*code + this.startDate.hashCode();
        return code;
    }

    /**
     * Returns a negative integer if the receiver starts before aShift
     * a positive integer is the receiver starts after aShift
     * and zero otherwise.
     * 
     * @param aShift the shift to be compared with the receiver.
     * @return -1 if the Shift is before aShift, 1 if the shift is after aShift
     * and 0 otherwise.
     */
    @Override
    public int compareTo(Shift aShift) 
    {
        if (this.startDate.compareTo(aShift.startDate) < 0)
            return -1;
        if (this.startDate.compareTo(aShift.startDate) > 0)
            return 1;
        return 0;
    }
}
