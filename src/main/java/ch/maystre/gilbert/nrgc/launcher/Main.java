package ch.maystre.gilbert.nrgc.launcher;

import ch.maystre.gilbert.nrgc.conjectures.TreeConjecture;
import ch.maystre.gilbert.nrgc.datastructures.Graph;
import ch.maystre.gilbert.nrgc.io.Log;
import ch.maystre.gilbert.nrgc.io.Parser;
import ch.maystre.gilbert.nrgc.pathfinders.GeneralPathFinder;
import ch.maystre.gilbert.nrgc.pathfinders.TreePathsFinder;
import ch.maystre.gilbert.nrgc.solvers.EdgeNonRepetitiveColoringFinder;
import ch.maystre.gilbert.nrgc.solvers.VertexNonRepetitiveColoringFinder;
import gurobi.GRBException;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        (new TreeConjecture(new File("/Users/gilbert/Documents/CLEAN all tree/UnZipped"))).test();


        //try {
            //String path = "/Users/gilbert/Desktop/Tree22/tree22.5.txt";
            //String fst = (new Reader(path)).readNextLines(6).get(5);
            //Graph tree = Parser.parseTree(fst);

        /*
            String graph = "5 9  9 8  8 7  7 6  6 5  0 5  4 9  3 8  2 7  1 6  0 3  0 2  1 4  1 3  2 4"; //petersen graph
            Graph g = Parser.parseTree(graph);
            GeneralPathFinder gpf = new GeneralPathFinder(g);
            Log.print(gpf.findAllPaths());

*/
            /*
            Graph lg = g.getLineGraph();
            VertexNonRepetitiveColoringFinder solver = new VertexNonRepetitiveColoringFinder(lg);
            solver.addSeparatePaths();
            Log.print(solver.compute());
            */




            /*
             * compute the Thue index
             */
            //List<List<Integer>> paths = TreePathsFinder.allPaths(tree);
            //EdgeNonRepetitiveColoringFinder edgeSolver = new EdgeNonRepetitiveColoringFinder(tree, paths);
            //Log.print(edgeSolver.compute());

            /*
             * compute the Thue number
             */
            //VertexNonRepetitiveColoringFinder vertexSolver = new VertexNonRepetitiveColoringFinder(tree);
            //Log.print(vertexSolver.addSeparatePaths().compute());

            /*
             * compute the Line graph
             */
            //Log.print("gap = " + treeGap(tree));



        /*} catch (GRBException e) {
            e.printStackTrace();
        }*/
    }



}
