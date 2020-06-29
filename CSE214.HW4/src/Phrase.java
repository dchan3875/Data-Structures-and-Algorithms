/**
 * The Phrase class acts as a wrapper for queue, being implemented by extending a LinkedList.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #4 CSE214</dd>
 * </dl>
 */
import java.util.LinkedList;

public class Phrase extends LinkedList<Bigram>
{
    /**
     * Builds a new Phrase object with the given String using a queue of Bigrams using a LinkedList.
     *
     * @param s
     *     The string the Phrase will be built from.
     *
     * @return
     *     A new Phrase object containing a queue of Bigram objects.
     */
    public static Phrase buildPhraseFromString(String s)
    {
        Phrase ph = new Phrase();
        s = s.toUpperCase();
        s = s.replaceAll("[^a-zA-Z]", "");
        s = s.replaceAll("J", "I");

        for(int i = 0; i <= s.length() - 2; i+=2) {
            if (s.charAt(i) == s.charAt(i + 1))
            {
                s = s.substring(0,i + 1) + "X" + s.substring(i+1);
            }
        }

        String[] arr = s.split("(?<=\\G.{2})");
        for(String str: arr)
        {
            if(str.length() == 2)
            {
                ph.enqueue(new Bigram(str.charAt(0), str.charAt(1)));
            }
            else
            {
                ph.enqueue(new Bigram(str.charAt(0), (char)88));
            }
        }
        return ph;
    }

    /**
     * Encrypts the Phrase, returning a new Phrase with the encrypted Bigrams.
     *
     * @param key
     *     The KeyTable to be used to encrypt the Phrase.
     *
     * @return
     *     The new Phrase object with a queue of encrypted Bigrams.
     *
     * @throws IllegalArgumentException
     *     Indicates that the given key is null.
     */
    public Phrase encrypt(KeyTable key) throws IllegalArgumentException
    {
        if(key == null)
            throw new IllegalArgumentException("The given key is null!");

        Phrase encryptph = new Phrase();

        while(!this.isEmpty())
        {
            Bigram bg = this.dequeue();
            try
            {
                int c1Row = key.findRow(bg.getFirst());
                int c2Row = key.findRow(bg.getSecond());

                int c1Col = key.findCol(bg.getFirst());
                int c2Col = key.findCol(bg.getSecond());

                if (c1Row == c2Row)
                {
                    bg.setFirst(key.getKeyTable()[c1Row][(c1Col + 1) % key.getKeyTable()[0].length]);
                    bg.setSecond(key.getKeyTable()[c2Row][(c2Col + 1) % key.getKeyTable()[0].length]);
                }
                else
                {
                    if (c1Col == c2Col)
                    {
                        bg.setFirst(key.getKeyTable()[(c1Row + 1) % key.getKeyTable().length][c1Col]);
                        bg.setSecond(key.getKeyTable()[(c2Row + 1) % key.getKeyTable().length][c1Col]);
                    }
                    else
                     {
                        bg.setFirst(key.getKeyTable()[c1Row][c2Col]);
                        bg.setSecond(key.getKeyTable()[c2Row][c1Col]);
                     }
                }
            }
            catch(IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
            encryptph.enqueue(bg);
        }
        return encryptph;
    }

    /**
     * Decrypts the Phrase, returning a new Phrase with the decrypted Bigrams.
     *
     * @param key
     *     The KeyTable to be used to decrypt the Phrase.
     *
     * @return
     *     The new Phrase object with a queue of decrypted Bigrams.
     *
     * @throws IllegalArgumentException
     *     Indicates that the given key is null.
     */
    public Phrase decrypt(KeyTable key) throws IllegalArgumentException
    {
        if(key == null)
            throw new IllegalArgumentException("The given key is null!");

        Phrase decryptph = new Phrase();

        try {
            while (!this.isEmpty()) {
                Bigram bg = this.dequeue();
                int c1Row = key.findRow(bg.getFirst());
                int c2Row = key.findRow(bg.getSecond());

                int c1Col = key.findCol(bg.getFirst());
                int c2Col = key.findCol(bg.getSecond());

                if (c1Row == c2Row)
                {
                    if (c1Col - 1 >= 0)
                    {
                        bg.setFirst(key.getKeyTable()[c1Row][Math.abs(c1Col - 1) % key.getKeyTable()[0].length]);
                    }
                    else
                    {
                        bg.setFirst(key.getKeyTable()[c1Row][key.getKeyTable()[0].length - 1]);
                    }

                    if (c2Col - 1 >= 0)
                    {
                        bg.setSecond(key.getKeyTable()[c2Row][Math.abs(c2Col - 1) % key.getKeyTable()[0].length]);
                    }
                    else
                    {
                        bg.setSecond(key.getKeyTable()[c2Row][key.getKeyTable()[0].length - 1]);
                    }
                }
                else
                {
                    if (c1Col == c2Col)
                    {
                        if(c1Row - 1 >= 0)
                            bg.setFirst(key.getKeyTable()[(c1Row - 1) % key.getKeyTable().length][c1Col]);
                        else
                            bg.setFirst(key.getKeyTable()[key.getKeyTable().length - 1][c1Col]);

                        if(c2Row - 1 >= 0)
                            bg.setSecond(key.getKeyTable()[(c2Row - 1) % key.getKeyTable().length][c2Col]);
                        else
                            bg.setFirst(key.getKeyTable()[key.getKeyTable().length - 1][c2Col]);
                    }
                    else
                    {
                        bg.setFirst(key.getKeyTable()[c1Row][c2Col]);
                        bg.setSecond(key.getKeyTable()[c2Row][c1Col]);
                    }
                }
                decryptph.enqueue(bg);
            }
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
        return decryptph;
    }

    /**
     * Adds the given Bigram into the end of the Phrase.
     *
     * @param b
     *     The Bigram to be added to the Phrase.
     */
    public void enqueue(Bigram b)
    {
        this.add(b);
    }

    /**
     * Removes the first Bigram in the Phrase.
     *
     * @return
     *     The Bigram that was removed.
     */
    public Bigram dequeue()
    {
        return this.remove();
    }

    /**
     * Returns the first Bigram in the Phrase.
     *
     * @return
     *     The first Bigram in the Phrase.
     */
    public Bigram peek()
    {
        return this.peek();
    }

    /**
     * Returns a String representing the Phrase by a collection of Bigrams.
     *
     * @return
     *     A formatted String representing the Phrase as Bigrams.
     */
    public String toString()
    {
        String output = "";
        for(int i = 0; i < this.size(); i++)
           output += String.valueOf(this.get(i).getFirst()) + this.get(i).getSecond();

        return output;
    }


}