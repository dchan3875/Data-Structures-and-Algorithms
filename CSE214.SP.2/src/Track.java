/**
 * The Track class contains references to Train nodes, keeping track of the utilization rate and times of arrival or
 * departure in order.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #2 CSE214</dd>
 * </dl>
 */
public class Track
{
    private Train head; //Beginning of the doubly linked list
    private Train tail; //End of doubly linked list
    private Train cursor; //Points to currently selected train
    private Track next; //Next Track
    private Track prev; //Previous Track
    private double utilizationRate; //Utilization Rate of the track (wait time/mins in day)
    private int trackNumber; //The current track number
    private int waitTime; //The total wait time for the track
    private int totalTrains; //Total number of trains
    private final int MINS_IN_DAY = 1440; //Constant for minutes in a day

    /**
     * Default constructor for a Track object.
     */
    public Track()
    {
        utilizationRate = 0.0;
        totalTrains = 0;
        waitTime = 0;
    }

    /**
     * Constructor for a Track object given the number for this track.
     *
     * @param trackNumber
     *      The number for this Track to be set as.
     */
    public Track(int trackNumber)
    {
        this.trackNumber = trackNumber;
        utilizationRate = 0.0;
        totalTrains = 0;
        waitTime = 0;
    }

    /**
     * Getter for the next Track object in this list.
     *
     * @return
     *      The next Track object.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Track has been instantiated</dd>
     */
    public Track getNext()
    {
        return next;
    }

    /**
     * Setter for the next Track object in this list.
     *
     * @param next
     *      The Track object to be set to.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Track has been instantiated</dd>
     */
    public void setNext(Track next)
    {
        this.next = next;
    }

    /**
     * Getter for the previous Track object in this list.
     *
     * @return
     *      The previous Track object.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Track has been instantiated</dd>
     */
    public Track getPrev()
    {
        return prev;
    }

    /**
     * Sets the previous Track object in this list to the one passed in.
     *
     * @param prev
     *      The Track to be set to.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Track has been instantiated</dd>
     */
    public void setPrev(Track prev)
    {
        this.prev = prev;
    }

    /**
     * Getter for the total number of trains scheduled for this Track.
     *
     * @return
     *      The number of trains scheduled for this Track.
     */
    public int getTotalTrains()
    {
        return totalTrains;
    }

    /**
     * Getter for the utilization rate of this track.
     *
     * @return
     *      The utilization rate of this Track.
     */
    public double getUtilizationRate()
    {
        return utilizationRate;
    }

    /**
     * Getter for the number for this Track.
     *
     * @return
     *      The number for this Track.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Track has been instantiated</dd>
     */
    public int getTrackNumber()
    {
        return trackNumber;
    }

    /**
     * Adds a new Train to the Track list.
     *
     * @param newTrain
     *      The new Train object to be added.
     * @throws IllegalArgumentException
     *      Indicates either some form of improper formatting of time, a null Train, or another Train exists with the same number.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Track has been instantiated</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>A new Train object is added to the list, utilization rate is updated, and the cursor is update to the new Train.</dd>
     */
     public void addTrain(Train newTrain) throws IllegalArgumentException
     {
        if(newTrain == null)
            throw new IllegalArgumentException("Train not added: Attempted to add a null train.");

        if(newTrain.getTransferTime() > 1399) //1 min before the next day, so train arriving at 12am and leaving 11:59pm is ok
        {
            throw new IllegalArgumentException("Train not added: Transfer time cannot exceed to the next day.");
        }

        if(newTrain.getArrivalTime() < 0 || newTrain.getDepartureTime() > 2359)
        {
            throw new IllegalArgumentException("Train not added: Train arrival/depart time out of bounds.");
        }

        if(trainExists(newTrain.getTrainNumber()))
        {
            throw new IllegalArgumentException("Train not added: Train already exists with that number on the current track.");
        }

        if(timeConflictExists(newTrain))
        {
            throw new IllegalArgumentException("Train not added: There is already a train on the track at the given time.");
        }

        if(cursor == null)
        {
            head = newTrain;
            cursor = newTrain;
            tail = newTrain;
        }
        else
        {
            Train pointer = head;
            while(pointer != null)
            {
                if(pointer.getArrivalTime() < newTrain.getArrivalTime())
                {
                    if(pointer == tail)
                    {
                        tail.setNext(newTrain);
                        newTrain.setPrev(tail);
                        tail = newTrain;
                        cursor = newTrain;
                        break;
                    }
                }
                else
                {
                    if(pointer == head)
                    {
                        newTrain.setNext(head);
                        head.setPrev(newTrain);
                        head = newTrain;
                        cursor = newTrain;
                        break;
                    }
                    else
                    {
                        newTrain.setNext(pointer);
                        newTrain.setPrev(pointer.getPrev());
                        pointer.getPrev().setNext(newTrain);
                        pointer.setPrev(newTrain);
                        cursor = newTrain;
                        break;
                    }
                }
                pointer = pointer.getNext();
            }
        }
         totalTrains++;
         waitTime += newTrain.getTransferTime();
         updateUtilization();
     }

    /**
     * Checks if there are any time conflicts with arrival/departure times.
     *
     * @param newTrain
     *      The Train object to be tested for time conflicts.
     * @return
     *      True if newTrain interferes with the current schedule, false otherwise.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Track has been instantiated</dd>
     */
     public boolean timeConflictExists(Train newTrain)
     {
         Train pointer = head;

         while(pointer != null)
         {
            if(newTrain.getArrivalTime() <= pointer.getDepartureTime() && newTrain.getDepartureTime() >= pointer.getArrivalTime())
                return true;

            pointer = pointer.getNext();
         }
         return false;
     }

    /**
     * Checks the current Trains scheduled for the Track too determine if it exists given the number.
     *
     * @param trainNumber
     *      The number of the Train to be looked for.
     * @return
     *      True if the Train with the given number exists, false otherwise.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Track has been instantiated</dd>
     */
     public boolean trainExists(int trainNumber)
     {
         if(cursor == null)
             return false;

         Train pointer = head;

         while(pointer != null)
         {
             if(pointer.getTrainNumber() == trainNumber)
                 return true;

             pointer = pointer.getNext();
         }
         return false;
     }

    /**
     *  Updates the utilization rate and rounds it to the second decimal place.
     */
    public void updateUtilization()
     {
         utilizationRate = ((double)waitTime/MINS_IN_DAY) * 100.0;
         utilizationRate = Math.round(utilizationRate * 100.0)/100.0;
     }

    /**
     * Prints the information for the Train that is selected by the cursor.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Track has been instantiated, cursor cannot be null.</dd>
     */
    public void printSelectedTrain()
     {
         if(cursor != null)
             System.out.println(cursor);
         else
             System.out.println("Cannot print: No train was selected.");
     }

    /**
     * Removes the selected Train by the cursor.
     *
     * @return
     *      The Train object that was removed from the list.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Track has been instantiated</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The Train object has been removed, the cursor is now set to the one after the removed.</dd>
     */
     public Train removeSelectedTrain()
     {
        if(cursor == null)
            return null;


        Train t = cursor;
        waitTime -= cursor.getTransferTime();
        if(cursor == head)
        {
            head = null;
            cursor = null;
            tail = null;
        }
        else
        {
            if(cursor == tail)
            {
                tail = cursor.getPrev();
                tail.setNext(null);
                cursor = tail;
            }
            else
            {
                cursor.getNext().setPrev(cursor.getPrev());
                cursor.getPrev().setNext(cursor.getNext());
                cursor = cursor.getNext();
            }
        }
        totalTrains--;
        updateUtilization();
        return t;
     }

    /**
     * Moves the cursor forwards in the list.
     *
     * @return
     *      True if the cursor was successfully moved forwards, false if it is already at the tail.
     *
     * @throws IllegalStateException
     *      Indicates that the Track is currently empty.
     *
     */
     public boolean selectNextTrain() throws IllegalStateException
     {
        if(cursor == null)
            throw new IllegalStateException("Next train cannot be selected: Track is empty.");

        if(cursor.getNext() == null)
            return false;

        cursor = cursor.getNext();
        return true;
     }

    /**
     * Moves the cursor backwards in the list.
     *
     * @return
     *      True if the cursor was successfully moved backwards, false if it is already at the head.
     *
     * @throws IllegalStateException
     *      Indicates that the Track is currently empty.
     */
     public boolean selectPrevTrain() throws IllegalStateException
     {
         if(cursor == null)
             throw new IllegalStateException("Previous train cannot be selected: Track is empty.");

         if(cursor.getPrev() == null)
             return false;

         cursor = cursor.getPrev();
         return true;
     }

    /**
     * Checks if the cursor is on the current Train object.
     *
     * @param train
     *      The Train object to be tested.
     * @return
     *      A String with an asterisk indicating if it is selected, an empty one if it is not.
     */
     public String isSelected(Train train)
     {
         if(cursor == train)
             return "    *";
         else
             return "";
     }

    /**
     * Creates a neatly formatted String of the Train nodes on this Track, displaying its information.
     *
     * @return
     *      A neatly formatted String of the Train nodes scheduled for this Track.
     */
     public String toString()
     {
         String header = String.format("%-10s%-20s%-50s%-15s%-15s","Selected", "Train Number", "Train Destination","Arrival Time","Departure Time");
         String lines = "\n-------------------------------------------" +
                 "----------------------------------------------------------" +
                 "--------";

         Train pointer = head;
         String data = "";

         while(pointer != null)
         {
             String arTime = String.valueOf(pointer.getArrivalTime());
             String dpTime = String.valueOf(pointer.getArrivalTime() + pointer.getTransferTime());

             for(int i = arTime.length(); i < 4; i++)
                 arTime = "0" + arTime;


             for(int i = dpTime.length(); i < 4; i++)
                 dpTime = "0" + dpTime;

            data += String.format("\n%-10s%-20s%-50s%-15s%-15s", isSelected(pointer), pointer.getTrainNumber(),
            pointer.getDestination(), arTime, dpTime);

            pointer = pointer.getNext();
         }

         return header + lines + data;
     }
}
