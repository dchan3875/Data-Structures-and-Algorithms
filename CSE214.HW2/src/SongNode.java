/**
 * The SongNode class are the nodes for the linked list.
 * Carries Song data and links to other nodes.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #2 CSE214</dd>
 * </dl>
 *
 */
public class SongNode
{
    private SongNode prev; // The previous node in the linked list.
    private SongNode next; // The next song in the linked list
    private Song data; // The current song in the linked list.

    /**
     * Default constructor with no parameters.
     *
     * Instantiates a new SongNode with everything as null.
     */
    public SongNode()
    {
        prev = null;
        next = null;
        data = null;
    }

    /**
     * Constructor with parameters.
     *
     * @param prev
     *     The SongNode preceding the current one.
     * @param next
     *     The SongNode succeeding the current one.
     * @param data
     *     The Song data that the node holds.
     */
    public SongNode(SongNode prev, SongNode next, Song data)
    {
        this.prev = prev;
        this.next = next;
        this.data = data;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>SongNode has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The preceding SongNode.
     */
    public SongNode getPrev() {
        return prev;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>SongNode has been instantiated.</dd>
     * </dl>
     *
     * @param prev
     *     The SongNode to be set to the previous.
     */
    public void setPrev(SongNode prev) {
        this.prev = prev;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>SongNode has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The succeeding SongNode.
     */
    public SongNode getNext() {
        return next;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>SongNode has been instantiated.</dd>
     * </dl>
     *
     * @param next
     *     The SongNode to be set to the next.
     */
    public void setNext(SongNode next) {
        this.next = next;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>SongNode has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The Song contained in the SongNode.
     */
    public Song getData() {
        return data;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>SongNode has been instantiated.</dd>
     * </dl>
     *
     *  @param data
     *      The Song data to be stored by the SongNode.
     */
    public void setData(Song data) {
        this.data = data;
    }



}
