package ch.maystre.gilbert.nrgc.solvers;

import ch.maystre.gilbert.nrgc.io.Log;

public class NonRepetitiveColoring {
    private final int size;
    private final int[] coloring;

    public NonRepetitiveColoring(int size, int[] coloring){
        this.size = size;
        this.coloring = coloring;
    }

    public void prettyPrint(){
        Log.print("Color size: " + size);
        for(int i = 0; i < coloring.length; i++){
            Log.print("   Vertex " + i + ": " + coloring[i]);
        }
    }

}
