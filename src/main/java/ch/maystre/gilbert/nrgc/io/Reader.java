package ch.maystre.gilbert.nrgc.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    private final BufferedReader br;

    public Reader(String path) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(new File(path)));
    }

    /**
     * Read the n next lines in the file
     *
     * @param nLines the number of lines to read
     * @return at most n lines (less if file has ended
     */
    public List<String> readNextLines(int nLines) throws IOException {
        ArrayList<String> toReturn = new ArrayList<>();
        String next;
        while(toReturn.size() < nLines && (next = br.readLine()) != null){
            toReturn.add(next);
        }
        return toReturn;
    }

}
