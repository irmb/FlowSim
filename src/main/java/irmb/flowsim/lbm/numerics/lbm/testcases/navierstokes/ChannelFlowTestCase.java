package irmb.flowsim.lbm.numerics.lbm.testcases.navierstokes;

import irmb.flowsim.lbm.numerics.BoundaryCondition;
import irmb.flowsim.lbm.numerics.UniformGrid;
import irmb.flowsim.lbm.numerics.lbm.LBMNoSlipBC;
import irmb.flowsim.lbm.numerics.lbm.LBMPressureBC;
import irmb.flowsim.lbm.numerics.lbm.LBMVelocityBC;
import irmb.flowsim.lbm.numerics.lbm.navierstokes.LBMNavierStokesGrid;
import irmb.flowsim.lbm.numerics.lbm.testcases.TestCase;

public class ChannelFlowTestCase extends TestCase {

    public UniformGrid getGrid() {

        LBMNavierStokesGrid grid = new LBMNavierStokesGrid(0.5 /* length */, 0.2 /* width */, 0.002 /* dx */);

        grid.testcase = this.getClass().getSimpleName();

        // 1. simulation parameters
        grid.setGravity(0.0, 0.0 /* m/s^2 */);
        grid.setViscosity(0.000001 /* m^2/s */);
        grid.setTimeStep(0.0001 /* s */);
        grid.updateParameters();

        // 2. boundary conditions
        double inflowVelo = 1.0; /* m/s */

        grid.addBC(new LBMPressureBC(grid, BoundaryCondition.EAST, 1.0));
        grid.addBC(new LBMVelocityBC(grid, BoundaryCondition.WEST, inflowVelo /* m/s */, 0.0));
        grid.addBC(new LBMNoSlipBC(grid, BoundaryCondition.NORTH));
        grid.addBC(new LBMNoSlipBC(grid, BoundaryCondition.SOUTH));

        // 3. initial conditions
        for (int i = 0; i < grid.nx; i++) {
            for (int j = 0; j < grid.ny; j++) {
                grid.init(i, j, 1. / 3., inflowVelo, 0.0);
            }
        }
        return grid;
    }
}
