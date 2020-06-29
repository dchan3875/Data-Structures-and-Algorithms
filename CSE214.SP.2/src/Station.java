/**
 * The Station class contains a list of Track objects while also allowing he user to control different Trains
 * and Tracks with various commands.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #2 CSE214</dd>
 * </dl>
 */

import java.util.Scanner;

public class Station
{
    private Track head; //First Track object in the list
    private Track tail; //Last Track object in the list
    private Track cursor; //Currently selected Track object in the list
    private int totalTracks; //Total number of Tracks

    /**
     * Default constructor for a Station object.
     */
    public Station()
    {
        head = null;
        tail = null;
        cursor = null;
        totalTracks = 0;
    }

    /**
     * Adds a new Track object to this Station.
     *
     * @param newTrack
     *      The new Track to be added.
     *
     * @throws IllegalArgumentException
     *      Indicates that the Track was either null or the Track already exists.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Station has been instantiated</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>A new Track object is added to the list, and totalTracks has been updated.</dd>
     */
    public void addTrack(Track newTrack) throws IllegalArgumentException
    {
        if(newTrack == null)
            throw new IllegalArgumentException("Track not added: Track is null.\n");

        if(trackExists(newTrack.getTrackNumber()))
            throw new IllegalArgumentException("Track not added: Track " + newTrack.getTrackNumber() + " already exists.\n");

        if(cursor == null)
        {
            head = newTrack;
            tail = newTrack;
            cursor = newTrack;
        }
        else
        {
            Track pointer = head;

            while(pointer != null)
            {
                if(pointer.getTrackNumber() < newTrack.getTrackNumber())
                {
                    if(pointer == tail)
                    {
                        tail.setNext(newTrack);
                        newTrack.setPrev(tail);
                        tail = newTrack;
                        cursor = newTrack;
                        break;
                    }
                }
                else
                {
                    if(pointer == head)
                    {
                        newTrack.setNext(head);
                        head.setPrev(newTrack);
                        head = newTrack;
                        cursor = newTrack;
                        break;
                    }
                    else
                    {
                        newTrack.setNext(pointer);
                        newTrack.setPrev(pointer.getPrev());
                        pointer.getPrev().setNext(newTrack);
                        pointer.setPrev(newTrack);
                        cursor = newTrack;
                        break;
                    }

                }
                pointer = pointer.getNext();
            }
        }
        totalTracks++;
    }

    /**
     * Determines if a Track exists with the given number.
     *
     * @param trackNum
     *      The track number to be looked for.
     *
     * @return
     *      True if the track exists, false otherwise.
     *
     */
    public boolean trackExists(int trackNum)
    {
        if(cursor == null)
            return false;

        Track pointer = head;

        while(pointer != null)
        {
            if(pointer.getTrackNumber() == trackNum)
                return true;

            pointer = pointer.getNext();
        }
        return false;
    }

    /**
     * Removes the selected track referenced by the cursor.
     *
     * @return
     *      The removed Track object.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Station has been instantiated</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The Track referenced has been removed, and the totalTracks has been updated.</dd>
     */
    public Track removeSelectedTrack()
    {
        if(cursor == null)
            return null;

        Track t = cursor;

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
        totalTracks--;
        return t;
    }

    /**
     * Prints the information for the Track currently selected by Cursor.
     *
     * @throws IllegalStateException
     *      Indicates that there is currently no Track in the Station object.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Station has been instantiated</dd>
     */
    public void printSelectedTrack() throws IllegalStateException
    {
        if(cursor != null)
        {
            System.out.println("Track " + cursor.getTrackNumber() + " (" + cursor.getUtilizationRate() + "% Utilization Rate):");
            System.out.println(cursor + "\n");
        }
        else
        {
            throw new IllegalStateException("Cannot print: There is currently no track.\n");
        }
    }

    /**
     * Prints the information for all Tracks in this Station.
     *
     * @throws IllegalStateException
     *      Indicates that there are currently no Tracks in the Station.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Station has been instantiated</dd>
     */
    public void printAllTracks() throws IllegalStateException
    {
        if(cursor == null)
            throw new IllegalStateException("Cannot print: There are currently no tracks.\n");

        Track pointer = head;

        while(pointer != null)
        {
            System.out.println("Track " + pointer.getTrackNumber() + " (" + pointer.getUtilizationRate() + "% Utilization Rate):");
            System.out.println(pointer);
            System.out.println();
            pointer = pointer.getNext();
        }

    }

    /**
     * Switches selection of the Track to the number that is passed in.
     *
     * @param trackToSelect
     *      The number of the Track to be switched to.
     *
     * @return
     *      True if it was successfully selected, false otherwise.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Station has been instantiated</dd>
     */
    public boolean selectTrack(int trackToSelect)
    {
        Track pointer = head;

        while(pointer != null)
        {
            if(pointer.getTrackNumber() == trackToSelect)
            {
                cursor = pointer;
                return true;
            }
            pointer = pointer.getNext();
        }
        return false;
    }

    /**
     * Creates a neatly formatted representation of the Station object.
     *
     * @return
     *      A neatly formatted representation of the Station object as a String.
     */
    public String toString()
    {
        String data = ("Station (" + totalTracks + " tracks):");

        Track pointer = head;

        while(pointer != null)
        {
            data += "\n    Track " + pointer.getTrackNumber() + ": " + pointer.getTotalTrains() + " trains arriving (" +
            pointer.getUtilizationRate() + "% Utilization Rate)\n";

            pointer = pointer.getNext();
        }
        return data;
    }

    /**
     * Generates a new Station object for the user, and prompts the user to select commands to utilize the program.
     *
     * @param args
     *      String array of arguments.
     */
    public static void main(String[] args)
    {
        Station station = new Station();
        Scanner input = new Scanner(System.in);
        String cmd;

        while(true)
        {
            System.out.println("|-----------------------------------------------------------------------------|\n" +
                    "| Train Options                       | Track Options                         |\n" +
                    "|    A. Add new Train                 |    TA. Add Track                      |\n" +
                    "|    N. Select next Train             |    TR. Remove selected Track          |\n" +
                    "|    V. Select previous Train         |    TS. Switch Track                   |\n" +
                    "|    R. Remove selected Train         |   TPS. Print selected Track           |\n" +
                    "|    P. Print selected Train          |   TPA. Print all Tracks               |\n" +
                    "|-----------------------------------------------------------------------------|\n" +
                    "| Station Options                                                             |\n" +
                    "|   SI. Print Station Information                                             |\n" +
                    "|    Q. Quit                                                                  |\n" +
                    "|-----------------------------------------------------------------------------|\n");

            System.out.print("Choose an operation: ");
            cmd = input.nextLine().toUpperCase().replaceAll(" ", "");

            if(cmd.equals("A"))
            {
                if(station.cursor == null)
                {
                    System.out.println("Train not added: There is no Track to add the Train to!\n");
                }
                else
                {
                    try
                    {
                        int trainNum; int arvTime; int transTime;

                        System.out.print("Enter train number: ");
                        String trainNumStr = input.nextLine();

                        System.out.print("Enter train destination: ");
                        String destination = input.nextLine();

                        System.out.print("Enter train arrival time: ");
                        String arrivalTime = input.nextLine();

                        System.out.print("Enter train transfer time: ");
                        String transferTime = input.nextLine();

                        if(!trainNumStr.matches("[0-9]+"))
                            throw new IllegalArgumentException("Train not added: Train number must be a number.\n");
                        else
                            trainNum = Integer.parseInt(trainNumStr);

                        if(!arrivalTime.matches("[0-9]+") || arrivalTime.length() != 4 ||
                                Integer.parseInt(arrivalTime.split("")[0] + arrivalTime.split("")[1]) > 23
                                || Integer.parseInt(arrivalTime.split("")[2] + arrivalTime.split("")[3]) > 59)
                        {
                            throw new IllegalArgumentException("Train not added: Arrival time must be a number in 24 hour format.\n");
                        }
                        else
                        {
                            arvTime = Integer.parseInt(arrivalTime);
                        }

                        if(!transferTime.matches("[0-9]+"))
                        {
                            throw new IllegalArgumentException("Train not added: Transfer time must be a number in minutes.\n");
                        }
                        else
                        {
                            transTime = Integer.parseInt(transferTime);
                        }

                        station.cursor.addTrain(new Train(trainNum,destination, arvTime, transTime));
                        System.out.println("\nTrain No. " + trainNum + " to " + destination + " added to Track " + station.cursor.getTrackNumber() + ". \n");
                    }
                    catch(IllegalArgumentException e)
                    {
                        System.out.println(e.getMessage() + "\n");
                    }
                }
            }

            if(cmd.equals("N"))
            {
                if(station.cursor == null)
                {
                    System.out.println("Train not selected: There is no next train to be selected!\n");
                }
                else
                {
                    try
                    {
                        if(station.cursor.selectNextTrain())
                            System.out.println("Cursor has been moved to next train.\n");
                        else
                            System.out.println("Selected train not updated: Already at end of Track List.\n");
                    }
                    catch(IllegalStateException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
            }

            if(cmd.equals("V"))
            {
                if(station.cursor == null)
                    System.out.println("There are currently no trains.\n");
                else
                {
                    try
                    {
                        if(station.cursor.selectPrevTrain())
                            System.out.println("Cursor has been moved to previous train.\n");
                        else
                            System.out.println("Selected train not updated: Already at front of Track List.\n");
                    }
                    catch(IllegalStateException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
            }

            if(cmd.equals("R"))
            {
                if(station.cursor == null)
                    System.out.println("There are currently no trains.\n");
                else
                {
                    Train t = station.cursor.removeSelectedTrain();

                    if(t != null)
                        System.out.println("Train No. " + t.getTrainNumber() + " to " +
                                t.getDestination() + " has been removed from Track " + station.cursor.getTrackNumber() + "\n");
                    else
                        System.out.println("There is no selected train to remove.\n");
                }
            }

            if(cmd.equals("P"))
            {
                if(station.cursor == null)
                    System.out.println("There are currently no trains.\n");
                else
                    station.cursor.printSelectedTrain();
            }

            if(cmd.equals("TA"))
            {
                System.out.print("Enter track number: ");
                String numText = input.nextLine();
                if(!numText.matches("[0-9]+"))
                {
                    System.out.println("Track number must be a number.\n");
                }
                else
                {
                    try
                    {
                        int num = Integer.parseInt(numText);
                        station.addTrack(new Track(num));
                        System.out.println("Track " + num + " added to the station.");
                    }
                    catch(IllegalArgumentException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
            }

            if(cmd.equals("TR"))
            {
                Track tr = station.removeSelectedTrack();
                if(tr != null)
                    System.out.println("Closed Track " + tr.getTrackNumber());
                else
                    System.out.println("There is no selected track to be removed.");
            }

            if(cmd.equals("TS"))
            {
                System.out.print("Enter the Track Number: ");
                String trackNum = input.nextLine();
                if(!trackNum.matches("[0-9]+"))
                    System.out.println("Track number must be a number.\n");
                else
                {
                    int num = Integer.parseInt(trackNum);
                    if(station.selectTrack(num))
                        System.out.println("Switched to Track " + num + ". \n");
                    else
                        System.out.println("Could not switch to Track " + num + ": Track " + num + " does not exist.\n");
                }
            }

            if(cmd.equals("TPS"))
            {
                try
                {
                    station.printSelectedTrack();
                }
                catch(IllegalStateException e)
                {
                    System.out.println(e.getMessage() + "\n");
                }
            }

            if(cmd.equals("TPA"))
            {
                try
                {
                    station.printAllTracks();
                }
                catch(IllegalStateException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("SI"))
            {
                System.out.println(station);
            }

            if(cmd.equals("Q"))
            {
                System.out.println("Program terminating normally...");
                System.exit(0);
            }
        }
    }
}
