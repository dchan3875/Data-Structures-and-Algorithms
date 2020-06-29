/**
 * The FrequencyList class stores the frequency of each given word into a ArrayList with a Hashtable holding the indicies.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #6 CSE214</dd>
 * </dl>
 */

import java.util.ArrayList;
import java.util.Hashtable;

public class FrequencyList
{
    private String word;
    private ArrayList<Double> frequencies;
    private Hashtable<String, Integer> passageIndices; //stores title name as key (string), and its index in the arr list.

    /**
     * Constructor with a given word and a list of all the passages
     *
     * @param word
     *      The word that the FrequencyList will hold the frequencies for.
     * @param passages
     *      ArrayList containing all the passages to be checked.
     */
    public FrequencyList(String word, ArrayList<Passage> passages)
    {
        this.word = word;
        frequencies = new ArrayList<>();
        passageIndices = new Hashtable<>();

        for(Passage p : passages)
        {
            frequencies.add((p.getWordFrequency(word) / p.getWordCount()));
            passageIndices.put(p.getTitle(), frequencies.indexOf(p.getWordFrequency(word) / p.getWordCount()));
        }
    }

    /**
     * Adds a passage to the FrequencyList
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>passageIndicies contain p's title and the index is mapped to the next available slot in the ArrayList.</dd>
     *
     * @param p
     *      The passage to be added.
     */
    public void addPassage(Passage p)
    {
        frequencies.add(p.getWordFrequency(word) / p.getWordCount());
        passageIndices.put(p.getTitle(), frequencies.indexOf(p.getWordFrequency(word) / p.getWordCount()));
    }

    /**
     * Getter for the frequency of a given passage
     *
     * @param p
     *      The passage to be checked for the word frequency.
     *
     * @return
     *      The frequency of the word in the passage p as a double.
     */
    public double getFrequency(Passage p)
    {
      return frequencies.get(passageIndices.get(p.getTitle()));
    }

    /**
     * Getter for the indicies for the passages.
     *
     * @return
     *      The Hashtable containing the indicies for each passage's word frequency
     */
    public Hashtable<String, Integer> getPassageIndices()
    {
        return passageIndices;
    }
}
