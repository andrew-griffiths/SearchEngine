package songpack;

import java.io.IOException;

public class program6 {

    public static void main(String[] args) throws IOException {
//        long start = System.currentTimeMillis();
//        MySearchEngine se = new MySearchEngine(MyDataReader.readFileToArrayList(args[0]));
//        long end = System.currentTimeMillis();
//        System.out.println((end-start)/1000 + " seconds to build the index\n");
//        search(se, "we are the champions");
//        search(se, "i will always love you");
//        search(se, "walking on sunshine");
//        search(se, "dancing in the rain");
//        search(se, "put your hands in the jupitersky");
        AVLTree rap1 = MyDataReader.readFileToAVL("song_lyrics.tsv", "rap");
        System.out.print(rap1.height(rap1.root));
    }
    
    /**
     * Sends query to search engine
     * @param se search engine
     * @param query to be passed
     */
    public static void search(MySearchEngine se, String query) {
        long start = System.currentTimeMillis();
        se.search(query);
        long end = System.currentTimeMillis();
        System.out.println((end-start) + " milliseconds to search for " + query + "\n");
    }
}
