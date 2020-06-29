/**
 * The TextAnalyzer class obtains the directory from the user and compares the passages for similarity percentage.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #6 CSE214</dd>
 * </dl>
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextAnalyzer
{
    /**
     * Main method that prompts the user for the directory of a folder with text files.
     *
     * @param args
     *      A string argument array
     */
    public static void main(String[] args) {
        FrequencyTable frequencyTable;

        System.out.print("Enter the directory of a folder of text files: ");
        Scanner input = new Scanner(System.in);
        String fileName = input.nextLine();

        File[] filedir = new File(fileName).listFiles();

        if(filedir == null)
        {
            while(filedir == null)
            {
                System.out.println("Invalid directory.");
                System.out.print("Enter the directory of a folder of text files: ");
                fileName = input.nextLine();
                filedir = new File(fileName).listFiles();
            }
        }

        System.out.println("\n Reading files...\n");
        ArrayList<Passage> passageList = new ArrayList<>();

        for (File f : filedir) {
            if (!f.getName().equals("StopWords.txt")) {
                try
                {
                    passageList.add(new Passage(f.getName(), f, fileName + "\\StopWords.txt"));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        for (int i = 0; i < passageList.size(); i++) {
            for (int g = i + 1; g < passageList.size(); g++) {
              Passage.cosineSimilarity(passageList.get(i), passageList.get(g));
            }
        }

        for (Passage p : passageList) {
            System.out.println(p);
        }
        System.out.println("-----------------------");

        for (Passage p : passageList)
        {
            String out = p.getPossibleSameAuthors();

            if(!out.isBlank())
                System.out.println(out);
        }
    }
}
