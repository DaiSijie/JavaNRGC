package ch.maystre.gilbert.nrgc.solvers;

import ch.maystre.gilbert.nrgc.io.Log;
import gurobi.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class that solves an instance of something non-repetitively. Variables are integer from 0...n-1 the number is given
 * via the getNumberOfVars() method. The sequences that should not be repetitive are given via the
 * getNonRepetitiveConstraints() method.
 */
public class AbstractNonRepetitiveSolver {

    private int numberOfVars;
    private int maxNumberOfColors;
    private List<List<Integer>> nonRepetitiveConstraints;

    public AbstractNonRepetitiveSolver(int numberOfVars){
        this.numberOfVars = numberOfVars;
        this.maxNumberOfColors = numberOfVars;
        this.nonRepetitiveConstraints = new ArrayList<>();
    }

    public NonRepetitiveSolution compute() throws GRBException {
        GRBEnv env = new GRBEnv();
        GRBModel model = new GRBModel(env);

        /*
         * 1: Prepare variables
         */

        GRBVar[] y = new GRBVar[maxNumberOfColors]; // y[c] = 1 iff color c is used
        GRBVar[][] x = new GRBVar[maxNumberOfColors][numberOfVars]; // x[c][j] = 1 iff vertex j uses color c
        GRBVar[][] z = new GRBVar[numberOfVars][numberOfVars]; // z[j][k] = 2 iff j and k have the same color

        for(int c = 0; c < maxNumberOfColors; c++){
            y[c] = model.addVar(0, 1, 0, GRB.BINARY, "y_" + c);
            for(int j = 0; j < numberOfVars; j++){
                x[c][j] = model.addVar(0, 1, 0, GRB.BINARY, "x_" + c + "_" + j);
            }
        }

        for(int j = 0; j < numberOfVars; j++){
            for(int k = j + 1; k < numberOfVars; k++){
                z[j][k] = model.addVar(0, 2, 0, GRB.INTEGER, "z_" + j + "_" + k);
            }
        }

        /*
         * 2: Prepare consistency constraints
         */

        // each vertex has exactly one color
        for(int j = 0; j < numberOfVars; j++){
            GRBLinExpr constraint = new GRBLinExpr();
            for(int c = 0; c < maxNumberOfColors; c++){
                constraint.addTerm(1, x[c][j]);
            }
            model.addConstr(constraint, GRB.EQUAL, 1, "vertex_" + j + "_has_exactly_one_color");
        }

        // each color is activated
        for(int c = 0; c < maxNumberOfColors; c++){
            for(int j = 0; j < numberOfVars; j++){
                model.addConstr(y[c], GRB.GREATER_EQUAL, x[c][j], "color_" + c + "_is_activable_by_vertex_" + j);
            }
        }

        // each Z is set

        for(int j = 0; j < numberOfVars; j++){
            for(int k = j + 1; k < numberOfVars; k++){
                for(int c = 0; c < maxNumberOfColors; c++){
                    GRBLinExpr constraint = new GRBLinExpr();
                    constraint.addTerm(1, x[c][j]);
                    constraint.addTerm(1, x[c][k]);
                    model.addConstr(z[j][k], GRB.GREATER_EQUAL, constraint, "z_" + j + "_" + k + "_is_activable");
                }
            }
        }

        /*
         * 3: Prepare non-repetitiveness constraints
         */

        int counter = 0;
        for(List<Integer> constraint : nonRepetitiveConstraints){
            if(constraint.size() % 2 == 0){
                GRBLinExpr sum = new GRBLinExpr();
                for(int s = 0; s < constraint.size() / 2 ; s++){
                    sum.addTerm(1, getZFor(constraint.get(s), constraint.get(s + constraint.size()/2), z));
                }
                model.addConstr(sum, GRB.LESS_EQUAL, constraint.size() - 1, "some_path_constraint_" + (counter++));
            }
        }

        /*
         * 4: Solve
         */

        GRBLinExpr objective = new GRBLinExpr();
        for(int c = 0; c < maxNumberOfColors; c++){
            objective.addTerm(1, y[c]);
        }

        model.set(GRB.IntAttr.ModelSense, GRB.MINIMIZE);
        model.setObjective(objective);
        model.optimize();

        double[][] xv = model.get(GRB.DoubleAttr.X, x);

        /*
         * 5: End computation and return
         */

        model.dispose();
        env.dispose();

        return new NonRepetitiveSolution(xv);
    }


    public void setMaxNumberOfColors(int maxNumberOfColors){
        this.maxNumberOfColors = maxNumberOfColors;
    }

    /**
     * Sets the non-repetitive sequence of variable that must be non-repetitive
     * @param nonRepetitiveConstraints The sequences. All odd-length sequence will be removed
     */
    public void setNonRepetitiveConstraints(List<List<Integer>> nonRepetitiveConstraints){
        this.nonRepetitiveConstraints = nonRepetitiveConstraints;
    }

    private static GRBVar getZFor(int a, int b, GRBVar[][] z){
        return z[Math.min(a,b)][Math.max(a,b)];
    }

    public static class NonRepetitiveSolution {

        private final int numberOfColors;
        private final int numberOfVars;
        private final int[] colorOf;

        private NonRepetitiveSolution(double[][] x){
            this.numberOfVars = x.length == 0 ? 0 : x[0].length ;
            this.colorOf = new int[numberOfVars];

            // normalize coloring
            int counter = 0;
            for(int c = 0; c < x.length; c++){
                boolean colorUsed = false;
                for(int u = 0; u < numberOfVars; u++){
                    if(!equalsZero(x[c][u])){
                        colorOf[u] = counter;
                        colorUsed = true;
                    }
                }
                if(colorUsed){
                    counter++;
                }
            }

            this.numberOfColors = counter;
        }

        public int getNumberOfVars() {
            return numberOfVars;
        }

        public int getNumberOfColors() {
            return numberOfColors;
        }

        public int getColorForVar(int var){
            return colorOf[var];
        }

        private boolean equalsZero(double d){
            return d < 0.1d;
        }

    }

}
