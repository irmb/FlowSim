package irmb.flowsim.lbm.numerics.lbm.testcases.navierstokes;

import irmb.flowsim.lbm.numerics.BoundaryCondition;
import irmb.flowsim.lbm.numerics.UniformGrid;
import irmb.flowsim.lbm.numerics.lbm.LBMMovingWallBC;
import irmb.flowsim.lbm.numerics.lbm.LBMNoSlipBC;
import irmb.flowsim.lbm.numerics.lbm.navierstokes.LBMNavierStokesGrid;
import irmb.flowsim.lbm.numerics.lbm.testcases.TestCase;

public class DrivenCavityTestCase extends TestCase {

    public UniformGrid getGrid() {

        LBMNavierStokesGrid grid = new LBMNavierStokesGrid(0.5 /* length */, 0.5 /* width */, 0.005 /* dx */);

        grid.testcase = this.getClass().getSimpleName();

        grid.setGravity(0.0, 0.0 /* m/s^2 */);
        grid.setViscosity(0.005 /* m^2/s */);
        grid.setTimeStep(0.0001 /* s */);
        grid.updateParameters();

        double inflowVelo = 5.;

        grid.addBC(new LBMMovingWallBC(grid, BoundaryCondition.NORTH, inflowVelo, 0.0));
        //grid.addBC(new LBMVelocityBC(grid, BoundaryCondition.NORTH, inflowVelo, 0.0));
        grid.addBC(new LBMNoSlipBC(grid, BoundaryCondition.EAST));
        grid.addBC(new LBMNoSlipBC(grid, BoundaryCondition.WEST));
        grid.addBC(new LBMNoSlipBC(grid, BoundaryCondition.SOUTH));

        // Initial conditions
        for (int i = 0; i < grid.nx; i++) {
            for (int j = 0; j < grid.ny; j++) {
                grid.init(i, j, 1. / 3., 0.0, 0.0);
            }
        }
        return grid;
    }
}
