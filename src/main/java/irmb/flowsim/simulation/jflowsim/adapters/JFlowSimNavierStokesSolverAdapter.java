package irmb.flowsim.simulation.jflowsim.adapters;

import java.util.Observable;
import java.util.Observer;

import irmb.flowsim.simulation.LBMSolver;
import irmb.flowsim.lbm.numerics.lbm.navierstokes.LBMNavierStokesGrid;
import irmb.flowsim.lbm.numerics.lbm.navierstokes.LBMNavierStokesSolver;

/**
 * Created by sven on 09.03.17.
 */
public class JFlowSimNavierStokesSolverAdapter extends LBMSolver implements Observer {

    private LBMNavierStokesSolver solver;
    private LBMNavierStokesGrid grid;

    public JFlowSimNavierStokesSolverAdapter(LBMNavierStokesSolver solver, LBMNavierStokesGrid grid) {
        this.solver = solver;
        this.grid = grid;
        solver.addObserver(this);
    }

    @Override
    public void solve() {
        if (solver == null) {
            solver = new LBMNavierStokesSolver(grid);
            solver.addObserver(this);
        }
        solver.startSimulation();
    }

    @Override
    public void pause() {
        solver.interrupt();
        solver = null;
    }

    @Override
    public void update(Observable o, Object arg) {
        notifyObservers("");
    }
}
