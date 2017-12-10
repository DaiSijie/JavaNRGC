package ch.maystre.gilbert.nrgc.datastructures;

public class Pair<S, T> {

    protected final S fst;
    protected final T snd;

    public Pair(S fst, T snd){
        this.fst = fst;
        this.snd = snd;
    }

    public boolean has(Integer other){
        return other.equals(fst) || other.equals(snd);
    }

    public S fst(){
        return fst;
    }

    public T snd(){
        return snd;
    }

}
