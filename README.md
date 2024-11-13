author: andrew-griffiths
Search Engine

This program accepts a single argument, which is a text file containing song information including title, artist, tag, year, view count, and lyrics. 
The file is used to implement a basic search engine for song lyrics using the Okapi BM25 model.

The search engine constructs three TreeMaps that store term frequency values for each song, inverse document frequency values for each word, and 
the ratio of each song's length to the average length of all the songs. These values are used to calculate a relevance score for a given query.

The search engine contains methods to calculate values for each of the three TreeMaps, as well as methods to calculate the relevance score for a given query, sort 
the songs based on their relevance score, and print the results of the search. Finally, a public search method is provided to allow the user to enter a query.

To run the program, add the java files to a java project and execute the main class by passing the text file as a single argument.
