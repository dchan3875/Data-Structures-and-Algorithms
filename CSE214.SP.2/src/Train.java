/**
 * The Train class contains information such as train number, destination, arrival/transfer time, and next/previous train
 * for a train that is approaching the station.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #2 CSE214</dd>
 * </dl>
 */
public class Train
{
    private Train next; //next train
    private Train prev; //previous train
    private int trainNumber; //train no.
    private String destination; //where the train is going
    private int arrivalTime; //time of arrival
    private int transferTime; //total time for transferring

    /**
     * Default constructor for a new Train object.
     */
    public Train()
    {
        next = null;
        prev = null;
        destination = "";
    }

    /**
     * Constructor for a new Train object given the train number, destination, arrivla time, and tranfer time.
     *
     * @param trainNumber
     *      The number for the new Train object.
     * @param destination
     *      The destination of the new Train object.
     * @param arrivalTime
     *      The time of arrival of the new Train object in 24 hour format.
     * @param transferTime
     *      The time it takes to transfer for a new train object in minutes.
     */
    public Train(int trainNumber, String destination, int arrivalTime, int transferTime)
    {
        this.trainNumber = trainNumber;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
        this.transferTime = transferTime;
    }

    /**
     * Setter for nextTrain, which is the train scheduled to arrive after this one.
     *
     * @param nextTrain
     *      The Train object to be set as the next train.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Train has been instantiated</dd>
     */
    public void setNext(Train nextTrain)
    {
        this.next = nextTrain;
    }

    /**
     * Setter for prevTrain, which is the train scheduled prior to this one
     *
     * @param prevTrain
     *      The Train object to be set as the previous train.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Train has been instantiated</dd>
     */
    public void setPrev(Train prevTrain)
    {
        this.prev = prevTrain;
    }

    /**
     * Getter for nextTrain, which is the train scheduled to arrive after this one.
     *
     * @return
     *      The Train object that is scheduled next.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Train has been instantiated</dd>
     */
    public Train getNext()
    {
        return next;
    }

    /**
     * Getter for prevTrain, which is the train scheduled prior to this one.
     *
     * @return
     *      The Train object that is scheduled prior.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Train has been instantiated</dd>
     */
    public Train getPrev()
    {
        return prev;
    }

    /**
     * Getter for the number of this Train object.
     *
     * @return
     *      The number of the current Train object.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Train has been instantiated</dd>
     */
    public int getTrainNumber()
    {
        return trainNumber;
    }

    /**
     * Getter for the destination of this Train object.
     *
     * @return
     *      The destination of the current Train object.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Train has been instantiated</dd>
     */
    public String getDestination()
    {
        return destination;
    }

    /**
     * Getter for the arrival time of this Train object.
     *
     * @return
     *      An int representing the arrival time of this Train object.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Train has been instantiated</dd>
     */
    public int getArrivalTime()
    {
        return arrivalTime;
    }

    /**
     * Getter for the transfer time for this Train object.
     *
     * @return
     *      An int representing the transfer time for this Train object.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Train has been instantiated</dd>
     */
    public int getTransferTime()
    {
        return transferTime;
    }

    /**
     * Getter for the departure time of this Train object.
     *
     * @return
     *      An int representing the departure time of this Train object.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Train has been instantiated</dd>
     */
    public int getDepartureTime()
    {
        return arrivalTime + transferTime;
    }

    /**
     * Tests for equality between this Train object and another object.
     *
     * @param obj
     *      The object to be tested.
     *
     * @return
     *      True if obj is a Train object with the same train number, false otherwise.
     */
    public boolean equals(Object obj)
    {
        if(obj instanceof Train)
        {
            Train candidate = (Train)obj;
            if(candidate.getTrainNumber() == this.trainNumber)
                return true;
        }
        return false;
    }

    /**
     * Neatly formats the data for this Train object in a String.
     *
     * @return
     *      A neatly formatted string representing the Train object and its information.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Train has been instantiated</dd>
     */
    public String toString()
    {
        String arTime = String.valueOf(arrivalTime);
        String dpTime = String.valueOf(arrivalTime + transferTime);

        for(int i = arTime.length(); i < 4; i++)
            arTime = "0" + arTime;


        for(int i = dpTime.length(); i < 4; i++)
            dpTime = "0" + dpTime;

        String data = "";
        data += "Selected Train:\n";
        data = data + "    Train Number: " + trainNumber + "\n";
        data = data + "    Train Destination: " + destination + "\n";
        data = data + "    Train Arrival Time: " + arTime + "\n";
        data = data + "    Train Departure Time: " + dpTime + "\n";

        return data;
    }
}
