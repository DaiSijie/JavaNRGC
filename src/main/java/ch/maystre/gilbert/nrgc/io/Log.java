package ch.maystre.gilbert.nrgc.io;

import java.util.List;

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

    public static void printPaths(List<List<Integer>> paths){
        for(List<Integer> path : paths){
            print(path.toString());
        }
    }

}
