/*
 * Gilbert Maystre
 * 10.02.18
 */

package ch.maystre.gilbert.nrgc.conjectures;

import ch.maystre.gilbert.nrgc.datastructures.Graph;
import ch.maystre.gilbert.nrgc.datastructures.Pair;
import ch.maystre.gilbert.nrgc.io.Log;
import ch.maystre.gilbert.nrgc.io.Parser;
import ch.maystre.gilbert.nrgc.pathfinders.GeneralPathFinder;
import ch.maystre.gilbert.nrgc.pathfinders.TreePathsFinder;
import ch.maystre.gilbert.nrgc.solvers.EdgeNonRepetitiveColoringFinder;
import ch.maystre.gilbert.nrgc.solvers.VertexNonRepetitiveColoringFinder;
import gurobi.GRBException;

import java.io.*;
import java.util.*;

/**
 * Will test every trees within the root folder. Order then Diameter then Line
 */
public class TreeConjecture {

    private static final int MIN_ORDER = 4;

    private static final int MAX_ORDER = 22;

    private File root;

    // region tree iteration

    private List<Pair<Integer, Integer>> availableFiles;

    private int currentFile;

    private int currentLine;

    private BufferedReader currentFileReader;

    // endregion

    public TreeConjecture(File root){
        this(root, 0, 0, 0);
    }

    public TreeConjecture(File root, int startOrder, int startDiameter, int startLine){
        this.root = root;
        currentLine = startLine;

        availableFiles = new ArrayList<>();
        for(String s : root.list()){
            String[] splat = s.substring(4, s.length() -3).split("\\.");
            int order = new Integer(splat[0]);
            int diameter = new Integer(splat[1]);
            availableFiles.add(new Pair<>(order, diameter));
        }

        availableFiles.sort(new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                if(o1.fst().equals(o2.fst())){
                    return o1.snd().compareTo(o2.snd());
                }
                else{
                    return o1.fst().compareTo(o2.fst());
                }
            }
        });

        //find first file that has startOrder and startDiameter
        currentFile = 0;
        for(Pair<Integer, Integer> p : availableFiles){
            if(p.fst() > startOrder || (p.fst() == startOrder && p.snd() >= startDiameter)){
                break;
            }
            currentFile++;
        }

        //open the first bufferedReader at the correct position
        try {
            currentFileReader = new BufferedReader(new FileReader(toFileName(availableFiles.get(currentFile))));
            for(int i = 0; i < startLine; i++){
                currentFileReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("fail!");
        }

    }

    public void test(){
        String next;
        while((next = safeNextTree()) != null){
            //check for caterpillar here

            Graph tree = Parser.parseTree(next);
            EdgeNonRepetitiveColoringFinder edgeFinder = new EdgeNonRepetitiveColoringFinder(tree);
            edgeFinder.addPaths(GeneralPathFinder.findAllPaths(tree));

            Graph lt = tree.getLineGraph();
            VertexNonRepetitiveColoringFinder vertexFinder = new VertexNonRepetitiveColoringFinder(lt);
            vertexFinder.addSeparatePaths();
            vertexFinder.computeLargeClique();

            try {
                int index = edgeFinder.compute().getNumberOfColors();
                Log.print(vertexFinder.compute());
                int number = 0;
                printResult(index, number);
            } catch (GRBException e) {
                e.printStackTrace();
            }
        }
    }

    private void printResult(int index, int number){
        Pair<Integer, Integer> current = availableFiles.get(currentFile);
        if(index != number){
            System.out.println("=====================================================================");
        }
        System.out.println("tree" + current.fst() + "x" + current.snd() + "x" + currentLine + ": " + index + " vs " + number);
    }

    boolean given = false;

    private String safeNextTree(){
        if(!given){
            given = true;
            return "0 2  1 2  2 4  3 4  4 5  5 6  6 7";
        }
        else{
            return null;
        }

        /*
        try {
            return nextTree();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        */
    }

    // returns null if there is no next tree
    private String nextTree() throws IOException{
        //simply try to read a line
        currentLine++;
        String toReturn = currentFileReader.readLine();

        // need to open the next file
        if(toReturn == null && currentFile < availableFiles.size()){
            currentFile++;
            currentLine = 0;
            currentFileReader.close();
            currentFileReader = new BufferedReader(new FileReader(toFileName(availableFiles.get(currentFile))));
            toReturn = currentFileReader.readLine();
        }

        return toReturn;
    }

    private String toFileName(Pair<Integer, Integer> descriptor) {
        return root.getPath() + "/tree" + descriptor.fst() + "." + descriptor.snd() + ".txt";
    }

}
