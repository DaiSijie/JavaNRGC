package ch.maystre.gilbert.nrgc.solvers;

import ch.maystre.gilbert.nrgc.datastructures.Graph;
import ch.maystre.gilbert.nrgc.pathfinders.TreePathsFinder;
import gurobi.*;

import java.util.HashMap;
import java.util.List;

public class ThueNumberSolver {


    public static NonRepetitiveColoring computeThueNumber(Graph g) throws GRBException {
        return computeThueNumber(g, g.getN(), TreePathsFinder.allPaths(g));
    }

    public static NonRepetitiveColoring computeThueNumber(Graph g, int maxNumberOfColors, List<List<Integer>> paths) throws GRBException {
        GRBEnv env = new GRBEnv();
        GRBModel model = new GRBModel(env);

        /*
         * Prepare variables
         */

        GRBVar[] y = new GRBVar[maxNumberOfColors]; // y[c] = 1 iff color c is used
        GRBVar[][] x = new GRBVar[maxNumberOfColors][g.getN()]; // x[c][j] = 1 iff vertex j uses color c
        GRBVar[][] z = new GRBVar[g.getN()][g.getN()]; // z[j][k] = 2 iff j and k have the same color

        for(int c = 0; c < maxNumberOfColors; c++){
            y[c] = model.addVar(0, 1, 0, GRB.BINARY, "y_" + c);
            for(int j = 0; j < g.getN(); j++){
                x[c][j] = model.addVar(0, 1, 0, GRB.BINARY, "x_" + c + "_" + j);
            }
        }

        for(int j = 0; j < g.getN(); j++){
            for(int k = j + 1; k < g.getN(); k++){
                z[j][k] = model.addVar(0, 2, 0, GRB.INTEGER, "z_" + j + "_" + k);
            }
        }

        /*
         * Prepare constraints
         */

        // each vertex has exactly one color
        for(int j = 0; j < g.getN(); j++){
            GRBLinExpr constraint = new GRBLinExpr();
            for(int c = 0; c < maxNumberOfColors; c++){
                constraint.addTerm(1, x[c][j]);
            }
            model.addConstr(constraint, GRB.EQUAL, 1, "vertex_" + j + "_has_exactly_one_color");
        }

        // each color is activated
        for(int c = 0; c < maxNumberOfColors; c++){
            for(int j = 0; j < g.getN(); j++){
                model.addConstr(y[c], GRB.GREATER_EQUAL, x[c][j], "color_" + c + "_is_activable_by_vertex_" + j);
            }
        }

        // each Z is set

        for(int j = 0; j < g.getN(); j++){
            for(int k = j + 1; k < g.getN(); k++){
                for(int c = 0; c < maxNumberOfColors; c++){
                    GRBLinExpr constraint = new GRBLinExpr();
                    constraint.addTerm(1, x[c][j]);
                    constraint.addTerm(1, x[c][k]);
                    model.addConstr(z[j][k], GRB.GREATER_EQUAL, constraint, "z_" + j + "_" + k + "_is_activable");
                }
                //model.addConstr(z[j][k], GRB.LESS_EQUAL, 2, "z_" + j + "_" + k + "_is_upper_bounded");
            }
        }

        // constraint each path
        int counter = 0;
        for(List<Integer> path : paths){
            GRBLinExpr constraint = new GRBLinExpr();
            for(int s = 0; s < path.size() / 2 ; s++){
                constraint.addTerm(1, getZFor(path.get(s), path.get(s + path.size()/2), z));
            }
            model.addConstr(constraint, GRB.LESS_EQUAL, path.size() - 1, "some_path_constraint_" + (counter++));
        }

        GRBLinExpr objective = new GRBLinExpr();
        for(int c = 0; c < maxNumberOfColors; c++){
            objective.addTerm(1, y[c]);
        }

        model.set(GRB.IntAttr.ModelSense, GRB.MINIMIZE);
        model.setObjective(objective);
        model.optimize();

        double[] yv = model.get(GRB.DoubleAttr.X, y);
        double[][] xv = model.get(GRB.DoubleAttr.X, x);

        model.dispose();
        env.dispose();

        return interpretResult(yv, xv, maxNumberOfColors, g.getN());
    }

    private static NonRepetitiveColoring interpretResult(double[] y, double[][] x, int maxNumberOfColor, int n){
        int counter = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int c = 0; c < maxNumberOfColor; c++){
            if(Math.round(y[c]) == 1){
                map.put(c, counter++);
            }
        }

        int[] ass = new int[n];
        for(int i = 0; i < n ; i++){
            for(int c = 0; c < maxNumberOfColor; c++){
                if(Math.round(x[c][i]) == 1){
                    ass[i] = map.get(c);
                }
            }
        }

        return new NonRepetitiveColoring(counter, ass);
    }

    private static GRBVar getZFor(int a, int b, GRBVar[][] z){
        return z[Math.min(a,b)][Math.max(a,b)];
    }

}
