/**
 * The Song class holds the information about the song, such as title and artist.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #2 CSE214</dd>
 * </dl>
 *
 */
public class Song
{
    private String name; // name of song
    private String artist; // name of the artist
    private String album; //name of the album
    private int length; // length of the song

    /**
     * Default constructor with no parameters.
     *
     * Instantiates a new Song with everything empty.
     */
    public Song()
    {
        name = "";
        artist = "";
        album = "";
        length = 0;
    }

    /**
     * Constructor with parameters.
     *
     * @param name
     *     Name of the song.
     * @param artist
     *     Name of the artist.
     * @param album
     *     Name of the album.
     * @param length
     *     Length of the song.
     */
    public Song(String name, String artist, String album, int length) throws IllegalArgumentException
    {
        if(name.isBlank() || artist.isBlank() || album.isBlank())
            throw new IllegalArgumentException("Song data cannot be blank.");
        if(length <= 0)
            throw new IllegalArgumentException("Length must be a positive integer");

        this.name = name;
        this.artist = artist;
        this.album = album;
        this.length = length;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Song has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The name of the song.
     */
    public String getName() {
        return name;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Song has been instantiated.</dd>
     * </dl>
     *
     * @param name
     *     The name of the song to be set to.
     *
     * @throws IllegalArgumentException
     *     Indicates that the song title was empty.
     */
    public void setName(String name) throws IllegalArgumentException{
        if(name.isBlank())
            throw new IllegalArgumentException("Song title cannot be empty. Song was not added.");

        this.name = name;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Song has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The artist of the song.
     */
    public String getArtist() {
        return artist;
    }

    /**
     *
     * @param artist
     *     The artist of the song to be set to.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Song has been instantiated.</dd>
     * </dl>
     *
     * @throws IllegalArgumentException
     *     Indicates that the artist string was empty.
     */
    public void setArtist(String artist) throws IllegalArgumentException{
        if(artist.isBlank())
            throw new IllegalArgumentException("Artist(s) cannot be empty. Song was not added.");

        this.artist = artist;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Song has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The name of the album.
     */
    public String getAlbum() {
        return album;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Song has been instantiated.</dd>
     * </dl>
     *
     * @param album
     *     The album to be set to.
     *
     * @throws IllegalArgumentException
     *     Indicates that the album string was empty.
     */
    public void setAlbum(String album) throws IllegalArgumentException{
        if(album.isBlank())
            throw new IllegalArgumentException("Album cannot be empty. Song was not added.");

        this.album = album;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Song has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The length of the song.
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length
     *     The length to be set to.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Song has been instantiated.</dd>
     * </dl>
     *
     * @throws IllegalArgumentException
     *     Indicates that the length passed in was either 0 or negative.
     *
     */
    public void setLength(int length) throws IllegalArgumentException{
        if(length <= 0)
            throw new IllegalArgumentException("Length must be a positive integer. Song was not added.");

        this.length = length;
    }



}

