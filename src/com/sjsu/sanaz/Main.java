package com.sjsu.sanaz;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static int[] getFrequencyOfWord(String filePath, String word){
        File novelFile = new File(filePath);
        //There are 23 chapters in my novel
        int[] res = new int[23];
        ArrayList<String> chapterString = new ArrayList<>();
        int chapterCount = 0;
        String searchWord = word.toLowerCase();
        String line;
        try (FileInputStream fileInputStream = new FileInputStream(novelFile)) {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null){
                if(line.equals("CHAPTER 1")){
                    continue;

                }else{
                    if (!line.contains("CHAPTER") && !line.contains("** end of file **")){
                        // \\s+ space delimiter
                        //Reading the file line by line
                        String tempLine = line.replaceAll("\\p{Punct}|\\d","");

                        String[] wordList = tempLine.split("\\s+");
                        for(int i = 0; i < wordList.length; i++){
                            //Casting to lower case in order to prevent counting the same words twice
                            chapterString.add(wordList[i].toLowerCase());
                        }
                    }
                    else{
                        HashMap<String, Integer> chapterDetail = new HashMap<>();
                        for(int i = 0; i < chapterString.size(); i++){
                            if(chapterString.get(i).equalsIgnoreCase(searchWord)){
                                    if(chapterDetail.containsKey(searchWord)){
                                        chapterDetail.put(searchWord, chapterDetail.get(searchWord)+1);
                                    }else{
                                        chapterDetail.put(searchWord, 1);
                                    }
                                }
                            }

                        if(chapterDetail.get(searchWord) == null){
                            res[chapterCount] = 0;

                        }else {
                            res[chapterCount] = chapterDetail.get(searchWord);

                        }
                            chapterCount++;
                            chapterString = new ArrayList<>();

                    }
                }

            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    //This method reads the whole file and return the result in a String
    public static String readFileAsString(String filePath)throws Exception
    {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(filePath)));
        return data;
    }

    public static int getChapterQuoteAppears(String filePath, String quote){
        int res = -1;

        try {
            String wholeBook = readFileAsString(filePath);
            String[] splittedText = wholeBook.split("CHAPTER [0-9]+");

          //  System.out.println("*********************" + splittedText.length);
            /* Uncomment the following for loop to get the text of each chapter, then you can choose a quote from it and search for it.
               Better to copy from the output because of remving \n and \r in my code.
             */
           /* for(int i =0; i < splittedText.length; i++){
                System.out.println("////////////////////////////////////////");
                splittedText[i] = splittedText[i].replaceAll("\n", " ");
                splittedText[i] = splittedText[i].replaceAll("\r", " ");
                System.out.println(splittedText[i]);
                System.out.println("#########################################");
            } */
            for(int i = 0; i < splittedText.length; i++){
                splittedText[i] = splittedText[i].replaceAll("\n", " ");
                splittedText[i] = splittedText[i].replaceAll("\r", " ");
                if(splittedText[i].contains(quote)){
                    return i;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }


    public static String generateSentence(String filePath) throws Exception {
        String result = "";
        ArrayList<String> temp; //To store the list of words come after a particular word
        StringBuilder mySentence = new StringBuilder();
        mySentence.append("The ");

        temp = afterWords("The", filePath);
        int tempSize = temp.size();


        int index = randomIndexGenerator(tempSize);
        mySentence.append(temp.get(index));

        for(int i = 0; i < 18; i++){
            String wordToChoose = temp.get(index);
            temp = afterWords(wordToChoose, filePath);
            tempSize = temp.size();
            index = randomIndexGenerator(tempSize);
            mySentence.append(" ");
            mySentence.append(temp.get(index));
        }

        result = mySentence.toString();


        return result;
    }

    //Creates a random index so I can choose a random word from my list of words at each step
    public static int randomIndexGenerator(int size){
        Random r = new Random();
        int index = r.nextInt((size));
        return index;
    }

    //It returns an array list of the words that come after a particular word
    public static ArrayList<String> afterWords(String word, String filePath){
        ArrayList<String> temp = new ArrayList<>();
        try {
            String firstString = readFileAsString(filePath);
            String[] tempString = firstString.split("\\s+");

            for(int i = 0; i < tempString.length; i++){
                if(tempString[i].equalsIgnoreCase(word) && i < tempString.length - 1){
                    temp.add(tempString[i+1]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return temp;
    }

    public static void main(String[] args) throws Exception {

        FirstPartMethods fm = new FirstPartMethods();

        String filePath = "/Users/sanazk/Downloads/42671-8.txt";
        System.out.println("The total number of words is: " + fm.getTotalNumberOfWords(filePath));
        System.out.println("The total number of unique words is: " + fm.getTotalUniqueWords(filePath));
        Object[] res = fm.get20MostFrequentWords(filePath);
        System.out.println("The list of 20 most frequent words: ");
        for (int i =0; i < 40; i = i + 2){
            System.out.print("[" + String.valueOf(res[i]) + ", "+ String.valueOf(res[i+1]) + "]");
        }
        System.out.println("\n");
        System.out.println("The list of 20 most interesting frequent words: ");
        Object[] res1 = fm.get20MostInterestingFrequentWords(filePath);
        for (int i =0; i < 40; i = i + 2){
            System.out.print("[" + String.valueOf(res1[i]) + ", "+ String.valueOf(res1[i+1]) + "]");
        }

        System.out.println("\n");
        System.out.println("The list of 20 most least frequent words: ");
        Object[] res2 = fm.get20LeastFrequentWords(filePath);
        for (int i =0; i < 40; i = i + 2){
            System.out.print("[" + String.valueOf(res2[i]) + ", "+ String.valueOf(res2[i+1]) + "]");
        }

        System.out.println("\n");
        System.out.println("The word frequency in each chapter is: ");
        int[] res3 = getFrequencyOfWord(filePath, "Elizabeth");
        for(int i = 0; i < res3.length; i++){
            System.out.print(res3[i] + " ");
        }
        System.out.println("\n");

        System.out.println("The chapter number is: "+getChapterQuoteAppears(filePath, "I am more obliged to you than I can express"));



        System.out.println(generateSentence(filePath));


    }
}
