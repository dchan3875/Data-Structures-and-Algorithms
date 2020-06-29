/**
 * The SongLinkedList class implements a Doubly Linked List.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #2 CSE214</dd>
 * </dl>
 *
 */
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SongLinkedList
{
    private SongNode head; // Beginning of the linked list.
    private SongNode tail; // End of the linked list.
    private SongNode cursor; // SongNode reference
    private int size; // Size of linked list.
    private Clip currentPlaying; // Current song clip playing.

    /**
     * Default constructor.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>Instantiates an empty SongLinkedList with head, tail, and cursor set to null.</dd>
     * </dl>
     *
     */
    public SongLinkedList()
    {
        head = null;
        tail = null;
        cursor = null;
        size = 0;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The name of the song must be in the playlist in addition to having a file being associated with it.</dd>
     * </dl>
     *
     * @param name
     *     The name of the song to be played.
     *
     * @throws IllegalArgumentException
     *     Indicates that either the song was not found in the playlist or the file does not exist.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The song is now playing.</dd>
     * </dl>
     */
    public void play(String name) throws IllegalArgumentException
    {
        String fileName = name + ".wav";
        File audioFile = new File(fileName);

        if(!isInPlaylist(name))
            throw new IllegalArgumentException("'" + name + "'" + " not found in playlist.");
        if(!audioFile.isFile())
            throw new IllegalArgumentException("The .wav file could not be found for " + "'" + name + "'");

        try {
            currentPlaying.stop();
        }
        catch(NullPointerException e){}

        try {
            AudioInputStream AIS = AudioSystem.getAudioInputStream(
                    audioFile);

            Clip c = AudioSystem.getClip();
            c.open(AIS);
            c.start();
            currentPlaying = c;
            System.out.println("'" + name + "' is now playing.");
        }
        catch (Exception ex)
        {
            System.out.println("Error playing song.");
        }
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The SongLinkedList has been instantiated.</dd>
     * </dl>
     *
     * Checks to see if a song is in the playlist.
     *
     * @param name
     *     Name of the song.
     * @return
     *     True if the song is in the playlist, false if it is not.
     */
    public boolean isInPlaylist(String name)
    {
        if(head == null)
            return false;

        SongNode pointer = head;

        for(int i = 0; i < size; i++)
        {
            if(pointer.getData().getName().equals(name))
                return true;
            else
                pointer = pointer.getNext();
        }
        return false;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The list is not empty. </dd>
     * </dl>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The cursor has been moved to the next SongNode.</dd>
     * </dl>
     *
     * @throws IllegalStateException
     *     Indicates that the playlist is empty or the cursor is already at the end of the list.
     */
    public void cursorForwards() throws IllegalStateException
    {
        if(cursor == null)
            throw new IllegalStateException("Playlist is currently empty, cursor cannot be moved forward.");

        if(cursor.getNext() == null)
            throw new IllegalStateException("Cursor is already at the end of the list.");

        if(cursor != tail && cursor != null) {
            cursor = cursor.getNext();
            System.out.println("Cursor moved to next song.");
        }
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The list is not empty. </dd>
     * </dl>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The cursor has been moved to the previous SongNode.</dd>
     * </dl>
     *
     * @throws IllegalStateException
     *     Indicates that the playlist is empty or the cursor is already at the beginning of the list.
     */
    public void cursorBackwards() throws IllegalStateException
    {
        if(cursor == null)
            throw new IllegalStateException("Playlist is currently empty, cursor cannot be moved backwards.");

        if(cursor == head && cursor.getPrev() == null)
            throw new IllegalStateException("Cursor is already at the beginning of the list.");

        if(cursor != head && cursor != null) {
            cursor = cursor.getPrev();
            System.out.println("Cursor moved to previous song.");
        }
    }

    /**
     * Inserts a song after the cursor position.
     *
     * @param newSong
     *     Song to be inserted in the playlist.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The newSong object has been instantiated.</dd>
     * </dl>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The new song is inserted into the playlist after the cursor. The order of the playlist is preserved
     * and the cursor now points to the new node.</dd>
     * </dl>
     *
     * @throws IllegalArgumentException
     *     Indicates that the newSong is null.
     */
    public void insertAfterCursor(Song newSong) throws IllegalArgumentException
    {
        if(newSong == null)
            throw new IllegalArgumentException("The Song to be added was null.");

        if(head == null)
        {
            SongNode beginNode = new SongNode();
            beginNode.setData(newSong);
            head = beginNode;
            tail = beginNode;
            cursor = beginNode;
        }
        else
        {
            SongNode sn = new SongNode();
            if(cursor.getNext() != null)
            {
                sn.setNext(cursor.getNext());
                sn.setPrev(cursor);
                cursor.getNext().setPrev(sn);
                cursor.setNext(sn);
                cursor = sn;
            }
            else
            {
                cursor.setNext(sn);
                sn.setPrev(cursor);
                tail = sn;
                cursor = sn;
            }
            sn.setData(newSong);
        }
        System.out.println("'" + newSong.getName() + "' " + "by " + newSong.getArtist() + " is added to your playlist.");
        size++;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The cursor is not null.</dd>
     * </dl>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The SongNode referenced by the cursor is removed and now the cursor references the next node or the previous
     * if the next does not exist.</dd>
     * </dl>
     *
     * @return
     *     the Song that was removed.
     *
     * @throws IllegalStateException
     *     Indicates that the playlist is empty.
     */
    public Song removeCursor() throws IllegalStateException
    {
        if(cursor == null)
            throw new IllegalStateException("Playlist is currently empty, there is nothing to remove.");

        Song s = cursor.getData();

        if(cursor == head && size == 1){
            head = null;
            tail = null;
            cursor = null;
        }
        else{
            if(cursor == head){
                head = cursor.getNext();
                head.setPrev(null);
                cursor = head;
            }
            else{
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
        }
        size--;
        System.out.println("'" + s.getName() + "'" + " was removed from the playlist.");
        return s;
    }

    /**
     * Selects a random song and plays it.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The randomly chosen song is now playing.</dd>
     * </dl>
     *
     * @return
     *     The song which was randomly selected.
     *
     * @throws IllegalStateException
     *     Indicates that the playlist is empty.
     */
    public Song random() throws IllegalStateException
    {
        if(head == null)
            throw new IllegalStateException("The playlist is currently empty.");

        System.out.println("Playing a random song...");
        play(getRandomSongNode().getData().getName());
        return cursor.getData();
    }

    /**
     * Chooses a random song from the playlist.
     *
     * @return
     *     A random song node.
     */
    public SongNode getRandomSongNode()
    {
        SongNode pointer = head;
        int rdm = (int) Math.floor(Math.random() * size);
        for(int i = 0; i < rdm; i++)
        {
            pointer = pointer.getNext();
        }
        return pointer;
    }

    /**
     * Randomly reorders the songs in the playlist.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The cursor references the same SongNode prior to the shuffle. The playlist is randomly reordered.</dd>
     * </dl>
     *
     * @throws IllegalStateException
     *     Indicates that the playlist is empty.
     */
    public void shuffle() throws IllegalStateException
    {
        if(size == 0)
            throw new IllegalStateException("Playlist is currently empty.");
        else if(size == 1)
            return;

        for(int i = 0; i < size; i++)
        {
            insertAtEnd(randomRemove());
        }
        System.out.println("Playlist shuffled.");
    }

    /**
     * Removes a song node randomly and returns the removed node.
     *
     * @return
     *     The SongNode that was removed.
     */
    public SongNode randomRemove()
    {
        SongNode pointer = head;
        int rdm = (int) Math.floor(Math.random() * size);
        for(int i = 0; i < rdm; i++)
        {
            pointer = pointer.getNext();
        }
        SongNode removed = pointer;

        if(pointer == head && size == 1){
            head = null;
            tail = null;
        }
        else {
            if (pointer == head) {
                head = pointer.getNext();
                head.setPrev(null);
            } else {
                if (pointer == tail) {
                    tail = pointer.getPrev();
                    tail.setNext(null);
                } else {
                    pointer.getNext().setPrev(pointer.getPrev());
                    pointer.getPrev().setNext(pointer.getNext());
                }
            }
        }
        return removed;
    }

    /**
     * Inserts a SongNode at the end of the playlist.
     *
     * @param s
     *     The SongNode to be inserted.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The SongNode has been inserted at the end of the playlist.</dd>
     * </dl>
     */
    public void insertAtEnd(SongNode s)
    {
        if(head == null) {
            head = s;
            tail = s;
        }
        else
        {
            tail.setNext(s);
            s.setPrev(tail);
            tail = s;
        }
    }

    /**
     * Prints the playlist in a neatly formatted table.
     */
    public void printPlaylist()
    {

        System.out.printf("%-25s%-25s%-25s%-25s\n","Song", "| Artist", "| Album", "| Length (s)");
        System.out.println("------------------------------"+
                "----------------------------------------" +
                "--------------------------------"
                +"--------------");

        System.out.println(this.toString());
    }

    /**
     * Deletes all the songs in the playlist.
     */
    public void deleteAll()
    {
        if(head == null) {
            System.out.println("Playlist is already empty.");
        }
        else
        {
            head.setNext(null);
            head = null;
            tail = null;
            cursor = null;
            size = 0;
            System.out.println("Playlist cleared.");
        }

    }

    /**
     * Returns a formatted string of the playlist.
     *
     * @return
     *     A neatly formatted string in tabular form.
     */
    public String toString()
    {
        SongNode currNode = head;
        String playlistStr = "";

        for(int i = 0; i < size; i++)
        {
            playlistStr += String.format("%-26s%-26s%-26s%-26s\n", currNode.getData().getName(), currNode.getData().getArtist(),
                    currNode.getData().getAlbum(), currNode.getData().getLength() + isCursorHere(currNode));

            currNode = currNode.getNext();
        }
        return playlistStr;
    }

    /**
     * Checks if a cursor is at the given node.
     *
     * @param currNode
     *     The node to be checked.
     *
     * @return
     *     A string with an arrow if the node is there, if not, it returns a blank string.
     */
    public String isCursorHere(SongNode currNode)
    {
        if(currNode == cursor)
            return "        <--";
        else
            return "";
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The SongLinkedList has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The SongNode of the head.
     */
    public SongNode getHead() {
        return head;
    }

    /**
     * Sets the head to the SongNode passed in.
     * @param head
     *    The SongNode to be set to the head.
     */
    public void setHead(SongNode head) {
        this.head = head;
    }

    /**
     * Returns the SongNode that the tail is pointing to.
     *
     * @return
     *     The tail SongNode.
     */
    public SongNode getTail() {
        return tail;
    }

    /**
     * Sets the tail to the given SongNode.
     * @param tail
     *     the SongNode the tail will be set to.
     */
    public void setTail(SongNode tail) {
        this.tail = tail;
    }

    /**
     * Returns the SongNode the cursor is pointing to.
     * @return
     *     the SongNode the cursor is pointing to.
     */
    public SongNode getCursor() {
        return cursor;
    }

    /**
     * Sets the cursor to the given SongNode.
     * @param cursor
     *     The SongNode the cursor will be set to.
     */
    public void setCursor(SongNode cursor)  {
        this.cursor = cursor;
    }

    /**
     * Returns the size of the playlist.
     *
     * @return
     *     The size of the playlist.
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the playlist.
     * @param size
     *     The new size of the playlist.
     */
    public void setSize(int size) {
        this.size = size;
    }
}
