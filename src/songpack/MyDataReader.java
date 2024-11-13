package songpack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MyDataReader {
    
    /**
     * Process a line from the TSV file and returns the corresponding song object
     * @param inputLine TSV line
     * @return Song object
     */
    private static Song lineToReport(String inputLine)
    {
        String[] items = inputLine.split("\t");
        String title= items[0];
        String tag= items[1];
        String artist= items[2];
        int year= Integer.parseInt(items[3]);
        int views= Integer.parseInt(items[4]);
        String lyrics= items[5];
        
        Song s = new Song(title, tag, artist, year, views, lyrics);
        return s;
    }
    
    /**
     * This method takes in the tsv file path and returns the binary search tree of songs with the given tag
     * @param tsvFilePath tsv file path
     * @param tag one of the six tags: rap, rb, pop, rock, misc, and country
     * @return binary search tree of songs
     * @throws IOException
     */
    public static BinarySearchTree readFileToBST(String tsvFilePath, String tag) throws IOException
    {
        BinarySearchTree songsBST= new BinarySearchTree();
        BufferedReader TSVReader = new BufferedReader(new FileReader(tsvFilePath));
        String line = TSVReader.readLine();
        while ((line = TSVReader.readLine()) != null) {   
            Song song = MyDataReader.lineToReport(line);
            if(song.getTag().equals(tag))
              songsBST.insert(song);                                           
        }
        TSVReader.close();
            
        return songsBST;
    }
    
    /**
     * Returns an ArrayList of all songs contained in given file
     * @param tsvFilePath to be processed
     * @return ArrayList of songs
     */
    public static ArrayList<Song> readFileToArrayList(String tsvFilePath) {
        ArrayList<Song> songs = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(tsvFilePath))) {
            String line;
            br.readLine();
            
            while ((line = br.readLine())!=null) {
                songs.add(lineToReport(line));
            }
        }
        catch(IOException e) {
            System.out.println("Error while reading file.");
            e.printStackTrace();
        } 
        return songs;
    }
    

    public static AVLTree readFileToAVL(String tsvFilePath, String tag) throws IOException
    {
        int count = 0;
        AVLTree songsAVL = new AVLTree();
        BufferedReader TSVReader = new BufferedReader(new FileReader(tsvFilePath));
        String line = TSVReader.readLine();
        while ((line = TSVReader.readLine()) != null) {   
            Song song = MyDataReader.lineToReport(line);
            if(song.getTag().equals(tag)) {
                songsAVL.insert(song); 
                count++;     
            }
        }
        System.out.print(count);
        TSVReader.close();
        return songsAVL;
    }
}
