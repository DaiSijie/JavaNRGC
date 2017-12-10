package ch.maystre.gilbert.nrgc.io;

import ch.maystre.gilbert.nrgc.datastructures.Edge;
import ch.maystre.gilbert.nrgc.solvers.AbstractNonRepetitiveSolver;
import ch.maystre.gilbert.nrgc.solvers.ThueIndexSolver;

import java.util.List;
import java.util.Map;

public class Log {

    /*
     * TODO logging to file
     */

    private static Log instance;

    private Log(){}

    public static Log getInstance(){
        if(instance == null){
            instance = new Log();
        }
        return instance;
    }

    public static void print(String str){
        System.out.println(System.currentTimeMillis() + ": " + str);
    }

    public static void print(List<List<Integer>> paths){
        for(List<Integer> path : paths){
            print(path.toString());
        }
    }

    public static void print(AbstractNonRepetitiveSolver.NonRepetitiveSolution solution){
        print("=================");
        print("Coloring solution");
        print("=================");
        print("With " + solution.getNumberOfColors() + " color(s)");
        for(int i = 0; i < solution.getNumberOfVars(); i++){
            print(" - vertex " + i + ": " + solution.getColorForVar(i));
        }
    }

    public static void print(ThueIndexSolver.EdgeNonRepetitiveColoring solution){
        print("=================");
        print("Coloring solution");
        print("=================");
        print("With " + solution.getNumberOfColors() + " color(s)");
        for(Map.Entry<Edge, Integer> e : solution.getColoring().entrySet()){
            print(" - edge (" + e.getKey().fst() + ", " + e.getKey().snd() + "): " + e.getValue());
        }
    }

}
