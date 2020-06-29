/**
 * The Passage class extends a HashTable with the key being a word in the passage and the integer being the number of times it appears.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #6 CSE214</dd>
 * </dl>
 */
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class Passage extends Hashtable<String, Integer>
{
    private String title;
    private int wordCount;
    private Hashtable<String, Double> similarTitles;
    private ArrayList<String> stopWords;

    /**
     * Default Constructor with title and file parameters
     *
     * @param title
     *      The title of the passage
     * @param file
     *      The passage as a File object
     */
    public Passage(String title, File file)
    {
        this.title = title;
        try {
            parseFile(file);
        }
        catch(IOException e)
        {
            System.out.println("error");
        }
    }

    /**
     * Constructor with title, file, and the location of the StopWords.txt. Holds the stop words to help with parsing.
     *
     * @param title
     *      The title of the passage
     * @param file
     *      The passage as a file object
     * @param stopdir
     *      The location of the stoptext.txt
     * @throws IOException
     *      Indicates that the file may be null
     */
    public Passage(String title, File file, String stopdir) throws IOException
    {
        this.title = title;
        stopWords = new ArrayList<>();
        similarTitles = new Hashtable<>();

        FileInputStream fis = new FileInputStream(stopdir);
        InputStreamReader inStream = new InputStreamReader(fis);
        BufferedReader stdin = new BufferedReader(inStream);

        String word;
        while(((word = stdin.readLine()) != null))
        {
            stopWords.add(word);
        }

        try
        {
            parseFile(file);
        }
        catch(IOException e)
        {
            System.out.println("The file given was null");
        }
    }

    /**
     * Removes punctuation and stop words from the passage. Words are also inserted into the hashtable.
     *
     * @param file
     *      The file to be parsed
     * @throws IOException
     *      Indicates that the file may be null
     */
    public void parseFile(File file) throws IOException
    {
        BufferedReader stdin = new BufferedReader(new FileReader(file));

        String data = "";
        String s;
        ArrayList<String> finalized = new ArrayList<>();

        while((s = stdin.readLine()) != null) {
            String[] arr = s.split(" ");
            for(String str : arr)
            {
                str = str.replaceAll("[^a-zA-Z]", " ");
                if(!str.isBlank())
                    wordCount++;
                if(!str.isBlank() && !stopWords.contains(str))
                {
                    finalized.add(str.toLowerCase().replaceAll("\\s", ""));
                }
            }
        }

        for(String words : finalized)
        {
            if(this.containsKey(words))
            {
                this.put(words, this.get(words) + 1);
            }
            else
            {
                this.put(words, 1);
            }
        }

    }

    /**
     * Calculates the similarity between two passages with the Cosine Similarity algorithm.
     *
     * @param passage1
     *      The first passage to be compared
     * @param passage2
     *      The second passage to be compared
     * @return
     *      The similiarity between the two texts.
     */
    public static double cosineSimilarity(Passage passage1, Passage passage2)
    {
        Set<String> setOne = passage1.getWords();
        Set<String> setTwo = passage2.getWords();
        Set<String> union = new HashSet<String>(setOne);
        union.retainAll(setTwo);
        for(String s : union)
        {
            System.out.println(s);
        }
        double product = 0;
        double u = 0;
        double v = 0;

        for(String word : union)
        {
            product = product + (passage1.get(word).doubleValue() / passage1.getWordCount()) * (passage2.get(word).doubleValue() / passage2.getWordCount());
            u = u + Math.pow((passage1.get(word).doubleValue() / passage1.getWordCount()), 2);
            v = v + Math.pow((passage2.get(word).doubleValue() / passage2.getWordCount()), 2);
        }

        double value = product / ((Math.sqrt(u)) * Math.sqrt(v));
        passage1.similarTitles.put(passage2.title, value);
        passage2.similarTitles.put(passage1.title, value);
        return value;
    }

    /**
     * Returns the word frequency for the given word.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Passage has been instantiated.</dd>
     *
     * @param word
     *      The word to be counted
     * @return
     *      The word frequency for the given word.
     *
     *
     */
    public double getWordFrequency(String word)
    {
        return this.get(word).doubleValue();
    }

    /**
     * Getter for all the words as a set.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Passage has been instantiated.</dd>
     *
     * @return
     *      A set of String with all the keys of the passage.
     *
     */
    public Set<String> getWords()
    {
        return this.keySet();
    }

    /**
     * Getter for the title of the passage.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Passage has been instantiated.</dd>
     *
     * @return
     *      The title of the passage.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for the title of the passage.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Passage has been instantiated.</dd>
     *
     * @param title
     *      The title to be set to.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for the word count of the passage
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Passage has been instantiated.</dd>
     *
     * @return
     *      The number of words in the passage
     */
    public int getWordCount() {
        return wordCount;
    }

    /**
     * Setter for the word count of the passage
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Passage has been instantiated.</dd>
     *
     * @param wordCount
     *      The word count to be set to.
     */
    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    /**
     * Getter for the Hashtable of all other passages and their similarities
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Passage has been instantiated.</dd>
     *
     * @return
     *      The Hashtable with similar passages from the current passage
     */
    public Hashtable<String, Double> getSimilarTitles() {
        return similarTitles;
    }

    /**
     * Setter for the Hashtable of all other passages and their similarities
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Passage has been instantiated.</dd>
     *
     * @param similarTitles
     *      The Hashtable to be set to.
     */
    public void setSimilarTitles(Hashtable<String, Double> similarTitles) {
        this.similarTitles = similarTitles;
    }

    public String getPossibleSameAuthors()
    {
        String output = "";
        Set<String> similars = this.similarTitles.keySet();
        for(String s : similars)
        {
            if((int)(similarTitles.get(s) * 100)  >= 60)
            {
                output += "'" + title + "'" + " and " + "'" + s + "'" + " may have the same author (" + (int)(similarTitles.get(s) * 100)+ "% similar).\n";
            }
        }
        return output;
    }

    /**
     * Formats a string of the similarTitles Hashtable.
     *
     * @return
     *      A formatted string listing the data from the similarTitles Hashtable.
     */
    public String toString()
    {
        String output = "";
        String names = "";
        Set<String> similars = this.similarTitles.keySet();
        for(String s: similars)
        {
            names += s + " (" + (int)(similarTitles.get(s) * 100) + "%), ";
        }

        output += String.format("%-30s%-5s%-200s\n", this.title, "|", names);
        return output;
    }
}
