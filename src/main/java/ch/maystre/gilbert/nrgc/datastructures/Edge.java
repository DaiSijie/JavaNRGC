package ch.maystre.gilbert.nrgc.datastructures;

public class Edge extends Pair<Integer, Integer>{

    public Edge(Integer fst , Integer snd){
        super(fst, snd);
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Edge){
            Edge otherE = (Edge) other;
            return (otherE.fst.equals(fst) && otherE.snd.equals(snd)) || (otherE.snd.equals(fst) && otherE.fst.equals(snd));
        }
        return false;
    }

    @Override
    public int hashCode(){
        return fst + snd;
    }




}
