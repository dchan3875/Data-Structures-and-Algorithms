/**
 * The FrequencyTable class extends an ArrayList to hold FrequencyLists, containing the frequency for words of passages.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #6 CSE214</dd>
 * </dl>
 */

import java.util.ArrayList;
import java.util.Set;

public class FrequencyTable extends ArrayList<FrequencyList>
{
    /**
     * Iterates through all the passages in the given ArrayList, creating and story word frequency for all words.
     *
     * @param passages
     *      The list of passages to be iterated through
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>A new FrequencyTable is constructed, containing a list of FrequencyLists.</dd>
     *
     * @return
     *      The constructed FrequencyTable
     */
    public static FrequencyTable buildTable(ArrayList<Passage> passages)
    {
        FrequencyTable ft = new FrequencyTable();
        //ft.add(new FrequencyList("", passages));
        for(Passage p : passages)
        {
            Set<String> set = p.getWords();
            for(String s : set)
            {
                ft.add(new FrequencyList(s, passages));
            }
        }
        return ft;
    }

    /**
     * Adds a Passage into the FrequencyTable and updates the FrequencyLists.
     *
     * @param p
     *      The passage to be added
     * @throws IllegalArgumentException
     *      Indicates that the passage is null or empty.
     */
    public void addPassage(Passage p) throws IllegalArgumentException
    {
        if(p.isEmpty() || p == null)
        {
            throw new IllegalArgumentException("The passage is empty or null!");
        }

        Set<String> set = p.getWords();
        for(String s : set)
        {

        }
    }

    /**
     * Getter for the frequency of the given word.
     *
     * @param word
     *      The word's frequency to be found.
     * @param p
     *      The passage to be looked at for the frequency of the word.
     * @return
     *      The frequency of the word in the passage
     * @throws IllegalArgumentException
     */
    public int getFrequency(String word, Passage p) {
        for (FrequencyList fl : this) {
            if (fl.getPassageIndices().contains(word)) {
                return fl.getPassageIndices().get(p.getTitle());
            }
        }
        return 0;
    }
}
