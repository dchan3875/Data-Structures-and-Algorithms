/**
 * The KeyTable class represents the key to the playfair cipher by containing a two dimensional array of chars.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #4 CSE214</dd>
 * </dl>
 */
public class KeyTable
{
    private char[][] key; //2d array of chars that represent the keytable.

    /**
     * Default constructor with no parameters.
     *
     * Instantiates a new 5x5 KeyTable.
     */
    public KeyTable()
    {
        key = new char[5][5];
    }

    /**
     * Creates a KeyTable with a given keyphrase.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>keyphrase is not null.</dd>
     * </dl>
     *
     * @param keyphrase
     *     String that will be used to create the KeyTable.
     *
     * @return
     *     A KeyTable object.
     *
     * @throws IllegalArgumentException
     *     Indicates that the keyphrase passed in was null.
     */
    public static KeyTable buildFromString(String keyphrase) throws IllegalArgumentException
    {
        if(keyphrase == null)
            throw new IllegalArgumentException("The keyphrase entered was null.");

        KeyTable kt = new KeyTable();

        keyphrase = keyphrase.replaceAll(" ", "");
        keyphrase = keyphrase.replaceAll("[^A-Za-z]", "");
        keyphrase = keyphrase.toUpperCase();
        String replacedDupes = "";

        for(int i = 0; i < keyphrase.length(); i++)
        {
            if(!replacedDupes.contains(String.valueOf(keyphrase.charAt(i))))
            {
                replacedDupes += String.valueOf(keyphrase.charAt(i));
            }
        }

        keyphrase = replacedDupes;


        char[] keyCharArr = keyphrase.toCharArray();

        int idx = 0;

        for(int r = 0; r < kt.key.length; r++)
        {
            for(int c = 0; c < kt.key[0].length; c++)
            {
                if(idx == keyCharArr.length)
                    break;

                kt.key[r][c] = keyCharArr[idx];
                idx++;
            }
        }

        char alphabet = 65;
        System.out.println(Character.valueOf(kt.key[2][0]));

        for(int r = 0; r < kt.key.length; r++)
        {
            for(int c = 0; c < kt.key[0].length; c++)
            {
                if(kt.key[r][c] == 0) {
                    while (contains(kt.key, alphabet) || alphabet == 74) {
                        alphabet++;
                    }
                    kt.key[r][c] = alphabet;
                }
            }
        }
        return kt;
    }

    /**
     * Returns the current KeyTable's array of char variables.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>KeyTable has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The two dimensional char array.
     */
    public char[][] getKeyTable()
    {
        return key;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>KeyTable has been instantiated and c is a valid letter.</dd>
     * </dl>
     *
     * @param c
     *     The character to be looked for.
     *
     * @return
     *     The index of the row where c occurs.
     *
     * @throws IllegalArgumentException
     *     Indicates that the character c is not in the key matrix.
     */
    public int findRow(char c) throws IllegalArgumentException
    {
        for(int r = 0; r < key.length; r++)
        {
            for(int col = 0; col < key[0].length; col++)
            {
                if(key[r][col] == c)
                {
                    return r;
                }
            }
        }
        throw new IllegalArgumentException("The character '" + c + "' could not be found in the key matrix.");
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>KeyTable has been instantiated and c is a valid letter.</dd>
     * </dl>
     *
     * @param c
     *     The character to be looked for.
     *
     * @return
     *     The index of the column where c occurs.
     *
     * @throws IllegalArgumentException
     *     Indicates that the character c is not in the key matrix.
     */
    public int findCol(char c) throws IllegalArgumentException
    {
        for(int r = 0; r < key.length; r++)
        {
            for(int col = 0; col < key[0].length; col++)
            {
                if(key[r][col] == c)
                {
                    return col;
                }
            }
        }
        throw new IllegalArgumentException("The character '" + c + "' could not be found in the key matrix.");
    }

    /**
     * Helper method to check if a character is contained in a two dimensional array.
     *
     * @param arr
     *     The array to be searched through.
     * @param a
     *     The character to be looked for.
     *
     * @return
     *     True if the character a is contained in arr. False if it is not.
     */
    public static boolean contains(char[][] arr, char a)
    {
        for(int i = 0; i < arr.length; i++)
        {
            for(int b = 0; b < arr[0].length; b++)
            {
                if(arr[i][b] == a)
                    return true;
            }
        }
        return false;
    }

    /**
     * Returns the KeyTable in a neatly formatted string.
     *
     * @return
     *     A neatly formatted string of the KeyTable.
     */
    public String toString()
    {
        String output = "";

        for (int r = 0; r < key.length; r++) {
            for (int c = 0; c < key[0].length; c++) {
                output += (key[r][c] + " ");
            }
            output += "\n";
        }
        return output;
    }
}
