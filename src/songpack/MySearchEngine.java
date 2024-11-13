package songpack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class MySearchEngine {
    ArrayList<Song> songList;
    TreeMap<Song, TreeMap<String, Double>> tf = new TreeMap<>();
    TreeMap<String, Double> idf = new TreeMap<>();
    TreeMap<Song, Double> avgLength = new TreeMap<>();
    
    /**
     * Constructor for MySearchEngine
     * @param songs to index
     */
    public MySearchEngine(ArrayList<Song> songs) {
        songList = songs;
        calculateTF(songs);
        calculateIDF(songs);
        calculateAvgLength(songs);
    }
    
    /**
     * Calculates term frequency of each word for all songs
     * @param songs
     */
    private void calculateTF(ArrayList<Song> songs) {
        for (Song s: songs) {
            String[] terms = s.getLyrics().toLowerCase().split("\\s+");
            int total = terms.length;
            tf.put(s, new TreeMap<String, Double>());
            for (String term: terms) {
                tf.get(s).put(term, (tf.get(s).getOrDefault(term, 0.0)+1)/total);
            }
        }
    }
    
    /**
     * Calculates inverse document frequency for all terms
     * @param songs
     */
    private void calculateIDF(ArrayList<Song> songs) {
        double N = songs.size();
        for (Song s: songs) {
            String[] terms = s.getLyrics().toLowerCase().split("\\s+");
            TreeSet<String> uniqueWords = new TreeSet<>(Arrays.asList(terms));
            for (String word: uniqueWords) {
                idf.put(word, idf.getOrDefault(word, 0.0) + 1);
            }
        }
        for (String s: idf.keySet()) {
            double nx = idf.get(s);
            double idfValue = (N-nx+0.5) / nx+0.5;
            idfValue = Math.log(idfValue+1);
            idf.put(s, idfValue);
        }
    }

    /**
     * Calculates ratio of song length to average length of all songs
     * @param songs
     */
    private void calculateAvgLength(ArrayList<Song> songs) {
        double n = songs.size();
        double sum = 0.0;
        for (Song s: songs) {
            String[] terms = s.getLyrics().toLowerCase().split("\\s+");
            sum += terms.length;
        }
        double average = sum / n;
        for (Song s: songs) {
            String[] terms = s.getLyrics().toLowerCase().split("\\s+");
            double ratio = terms.length / average;
            avgLength.put(s, ratio);
        }
    }
    
    /**
     * Calculates a relevance score for given song and query
     * @param s song
     * @param qTerms contents of query
     * @return double indicating relevance score
     */
    private double calculateRelevance(Song s, String[] qTerms) {
        double sum = 0.0;
        for (String t: qTerms) {
            double n = 2.2 * tf.get(s).getOrDefault(t, 0.0);
            double d = tf.get(s).getOrDefault(t, 0.0) + .3 + (.9 * avgLength.get(s));
            sum += (idf.getOrDefault(t, 0.0) * n) / d;
        }
        return sum;
    }
    
    /**
     * Searches index and prints search results
     * @param query passed
     */
    public void search(String query) {
        String[] qTerms = query.split("\\s+");
        TreeMap<Song,Double> scores = new TreeMap<>();
        for (Song s: songList) {
            scores.put(s, calculateRelevance(s, qTerms));
        }
        List<Map.Entry<Song,Double>> results = sortedByValue(scores, 5);
        printSearchResults(query, results);
    }
    
    /**
     * Sorts TreeMap of each song's relevance score and returns sorted map of top k scores
     * @param treeMap to be sorted
     * @param topK size of tree to be returned
     * @return sorted TreeMap of relevance scores
     */
    private List<Map.Entry<Song, Double>> sortedByValue(TreeMap<Song, Double> treeMap, int topK){
        List<Map.Entry<Song, Double>> list = new ArrayList<>(treeMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Song, Double>>() {
            @Override
            public int compare(Map.Entry<Song, Double> o1, Map.Entry<Song, Double> o2) {
            return o2.getValue().compareTo(o1.getValue());
            }
        });
        int counter = 0;
        List<Map.Entry<Song, Double>> results = new ArrayList<Map.Entry<Song, Double>>();
        while(counter<topK){
            results.add(Map.entry(list.get(counter).getKey(),list.get(counter).getValue()));
            counter+=1;
        }
        return results;
    }
    
    /**
     * Prints search results for given query
     * @param query passed
     * @param results top relevance scores
     */
    private void printSearchResults(String query, List<Map.Entry<Song, Double>> results){
        System.out.println("Results for "+ query + ":");
        int rank = 1;
        for(Map.Entry<Song, Double> entry: results){
            System.out.println("\t" + rank +": " + entry.getKey().getTitle() + " by " + entry.getKey().getArtist() + "\t"
                + entry.getValue());
            rank+=1;
        }
    }
}
