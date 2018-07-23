package com.sjsu.sanaz;

import java.io.*;
import java.util.*;

/**
 * Created by sanazk on 7/23/18.
 */
public class FirstPartMethods {

    public static int getTotalNumberOfWords(String filePath){
        File novelFile = new File(filePath);
        int totalNumberOfWords = 0;
        String line;
        try (FileInputStream fileInputStream = new FileInputStream(novelFile)) {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null){
                if(!line.equals("")){
                    // \\s+ space delimiter
                    String[] wordList = line.split("\\s+");
                    totalNumberOfWords += wordList.length;
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalNumberOfWords;
    }

    public static int getTotalUniqueWords(String filePath){
        int uniqueWordsCountTotal = 0;

        HashSet<String> uniqueWords = new HashSet<>();
        File novelFile = new File(filePath);
        String line;

        try (FileInputStream fileInputStream = new FileInputStream(novelFile)) {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null){
                if(!line.equals("")){
                    // \\s+ space delimiter

                    String[] wordList = line.split("\\s+");

                    for(int i = 0; i < wordList.length; i++){
                        if(!uniqueWords.contains(wordList[i].toLowerCase())){
                            uniqueWords.add(wordList[i]);
                        }
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        uniqueWordsCountTotal = uniqueWords.size();

        return uniqueWordsCountTotal;
    }

    public static Object[] get20MostFrequentWords(String filePath){
        HashMap<String, Integer> wordCount = new HashMap<>();
        Object[] myArray = new Object[40];
        File novelFile = new File(filePath);
        String line;

        try (FileInputStream fileInputStream = new FileInputStream(novelFile)) {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null){
                if(!line.equals("")){
                    // \\s+ space delimiter
                    String[] wordList = line.split("\\s+");

                    for(int i = 0; i < wordList.length; i++){
                        if(wordCount.containsKey(wordList[i].toLowerCase())){
                            wordCount.put(wordList[i].toLowerCase(), wordCount.get(wordList[i].toLowerCase())+1);
                        }else{
                            wordCount.put(wordList[i].toLowerCase(), 1);
                        }
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Integer, String> map = sortByValuesDes(wordCount);
        Iterator iterator2 = map.entrySet().iterator();
        int i = 0;
        while(i < 40) {
            Map.Entry me2 = (Map.Entry)iterator2.next();
            myArray[i] = new String(me2.getKey().toString());
            myArray[i+1] = new Integer((Integer) me2.getValue());
            i = i + 2;
        }

        return myArray;
    }

    //Sorting hashmap in descending order
    public static HashMap sortByValuesDes(HashMap map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue())
                        .compareTo(((Map.Entry) (o1)).getValue());
            }
        });
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }


    //sorting hashmap in ascending order
    public static HashMap sortByValuesAsc(HashMap map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }


    //Adding the first 100 common words to a HashSet to filter them out later on
    public static HashSet<String> mostCommonWords(String filePath){
        HashSet<String> englishFrequent100Words = new HashSet<>();

        try {
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                englishFrequent100Words.add(line);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return englishFrequent100Words;

    }


    public static Object[] get20MostInterestingFrequentWords(String filePath){
        HashMap<String, Integer> wordCount = new HashMap<>();
        Object[] myArray = new Object[40];
        File novelFile = new File(filePath);
        String line;
        HashSet<String> filterSet = mostCommonWords("/Users/sanazk/Downloads/1-1000.txt");
        try (FileInputStream fileInputStream = new FileInputStream(novelFile)) {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null){
                if(!line.equals("")){
                    // \\s+ space delimiter
                    String[] wordList = line.split("\\s+");

                    for(int i = 0; i < wordList.length; i++){
                        if(!(filterSet.contains(wordList[i].toLowerCase()))){
                            if(wordCount.containsKey(wordList[i].toLowerCase())){
                                wordCount.put(wordList[i].toLowerCase(), wordCount.get(wordList[i].toLowerCase())+1);
                            }else{
                                wordCount.put(wordList[i].toLowerCase(), 1);
                            }
                        }

                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Integer, String> map = sortByValuesDes(wordCount);
        Iterator iterator2 = map.entrySet().iterator();
        int i = 0;
        while(i < 40) {
            Map.Entry me2 = (Map.Entry)iterator2.next();
            myArray[i] = new String(me2.getKey().toString());
            myArray[i+1] = new Integer((Integer) me2.getValue());
            i = i + 2;
        }

        return myArray;
    }

    public static Object[] get20LeastFrequentWords(String filePath){
        HashMap<String, Integer> wordCount = new HashMap<>();
        Object[] myArray = new Object[40];
        File novelFile = new File(filePath);
        String line;

        try (FileInputStream fileInputStream = new FileInputStream(novelFile)) {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null){
                if(!line.equals("")){
                    // \\s+ space delimiter
                    String[] wordList = line.split("\\s+");

                    for(int i = 0; i < wordList.length; i++) {
                        if (!wordList[i].matches("-?\\d+(\\.\\d+)?")) {
                            if (wordCount.containsKey(wordList[i].toLowerCase())) {
                                wordCount.put(wordList[i].toLowerCase(), wordCount.get(wordList[i].toLowerCase()) + 1);
                            } else {
                                wordCount.put(wordList[i].toLowerCase(), 1);
                            }
                        }
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Integer, String> map = sortByValuesAsc(wordCount);
        Iterator iterator2 = map.entrySet().iterator();
        int i = 0;
        while(i < 40) {
            Map.Entry me2 = (Map.Entry)iterator2.next();
            myArray[i] = new String(me2.getKey().toString());
            myArray[i+1] = new Integer((Integer) me2.getValue());
            i = i + 2;
        }

        return myArray;
    }

}
