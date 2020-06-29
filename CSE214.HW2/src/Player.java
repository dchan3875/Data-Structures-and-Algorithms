/**
 * The Player class implements a SongLinkedList and allows users to create and navigate their playlist.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #2 CSE214</dd>
 * </dl>
 *
 */
import java.util.Scanner;

public class Player
{
    private static SongLinkedList playlist; // The SongLinkedList to be instantiated.

    /**
     * Sets up the application by instantiating the playlist and calls to display commands.
     * @param args
     *    String array argument
     */
    public static void main(String[] args)
    {
        playlist = new SongLinkedList();
        showCommands();
    }

    /**
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>A formatted command menu is printed and a scanner is created to record user input.</dd>
     * </dl>
     */
    public static void showCommands()
    {
        System.out.println(String.format("%-10s%-45s","(A)", "Add Song to Playlist"));
        System.out.println(String.format("%-10s%-45s","(F)", "Go to Next Song"));
        System.out.println(String.format("%-10s%-45s","(B)", "Go to Previous Song"));
        System.out.println(String.format("%-10s%-45s","(R)", "Remove Song from Playlist"));
        System.out.println(String.format("%-10s%-45s","(L)", "Play a Song"));
        System.out.println(String.format("%-10s%-45s","(C)", "Clear the Playlist"));
        System.out.println(String.format("%-10s%-45s","(S)", "Shuffle Playlist"));
        System.out.println(String.format("%-10s%-45s","(Z)", "Random Song"));
        System.out.println(String.format("%-10s%-45s","(P)", "Print Playlist"));
        System.out.println(String.format("%-10s%-45s","(T)", "Get the total amount of songs in the playlist"));
        System.out.println(String.format("%-10s%-45s","(Q)", "Exit the playlist"));

        Scanner inputScan = new Scanner(System.in);
        System.out.print("Please enter a command: ");
        enteredCommand(inputScan.nextLine().toUpperCase());
    }

    /**
     *
     * @param cmd
     *     The command entered by the user.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>SongLinkedList has been instantiated.</dd>
     * </dl>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The inputted command will be executed.</dd>
     * </dl>
     */
    public static void enteredCommand(String cmd)
    {
        if(cmd.isBlank()) {
            showCommands();
        }

        if(cmd.equals("A")) //Add Song
        {
            Song s = new Song();
            Scanner addScan = new Scanner(System.in);

            try {
                System.out.print("Enter song title: ");
                String songTitle = addScan.nextLine();
                s.setName(songTitle);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                showCommands();
            }

            try{
                System.out.print("Enter artist(s) of the song: ");
                String artist = addScan.nextLine();
                s.setArtist(artist);
            } catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
                showCommands();
            }

            try{
                System.out.print("Enter album: ");
                String album = addScan.nextLine();
                s.setAlbum(album);
            }
            catch(IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
                showCommands();
            }


            System.out.print("Enter length (in seconds): ");
            String lenstr = addScan.nextLine();
            try
            {
                if(lenstr.isBlank() || !(lenstr.matches("^[0-9]*$")))
                    throw new IllegalArgumentException("Length must be a positive integer. Song was not added.");
                else {
                    int lengthFromStr = Integer.parseInt(lenstr);
                    s.setLength(lengthFromStr);
                }
            }
            catch(IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
                showCommands();
            }

            try {
                playlist.insertAfterCursor(s);
            }
            catch(IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        if(cmd.equals("F")) // Go to next song
        {
            try{
                playlist.cursorForwards();
            }
            catch(IllegalStateException e){
                System.out.println(e.getMessage());
            }
        }
        if(cmd.equals("B")) // Go to previous song
        {
            try{
                playlist.cursorBackwards();;
            }
            catch(IllegalStateException e){
                System.out.println(e.getMessage());
            }
        }
        if(cmd.equals("R")) // Remove song
        {
            try{
                playlist.removeCursor();
            }
            catch(IllegalStateException e){
                System.out.println(e.getMessage());
            }
        }
        if(cmd.equals("L")) // Play a song
        {
            try {
                Scanner inputScan = new Scanner(System.in);
                System.out.print("Enter name of song to play: ");
                playlist.play(inputScan.nextLine());
            }
            catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }

        }
        if(cmd.equals("C")) //Clear playlist
        {
            playlist.deleteAll();
        }
        if(cmd.equals("S")) //Shuffle playlist
        {
            try{
                playlist.shuffle();
            }
            catch(IllegalStateException e)
            {
                System.out.println(e.getMessage());
            }
        }
        if(cmd.equals("Z")) // Random song
        {
            try{
                playlist.random();
            }
            catch (IllegalStateException | IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
        }
        if(cmd.equals("P")) // Print playlist
        {
            playlist.printPlaylist();
        }
        if(cmd.equals("T")) // Get size of playlist
        {
            int count = playlist.getSize();
            if(count == 0)
                System.out.println("Your playlist is currently empty.");
            else
                System.out.println("Your playlist currently has " + count + " songs.");
        }
        if(cmd.equals("Q")) // Quit
        {
            System.out.println("Program terminated.");
            System.exit(0);
        }
        showCommands();
    }

}
