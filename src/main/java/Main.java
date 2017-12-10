import gurobi.GRBException;
import gurobi.GRBModel;

public class Main {

    public static void main(String[] args) throws GRBException {
        GRBModel y;
        System.out.println("Hello! arg count = " + args.length + " 1starg = " + (args.length > 0 ? args[0] : "none"));
    }
}
