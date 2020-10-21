package irmb.flowsim.lbm.numerics.lbm.testcases.navierstokes;

import irmb.flowsim.lbm.numerics.BoundaryCondition;
import irmb.flowsim.lbm.numerics.UniformGrid;
import irmb.flowsim.lbm.numerics.lbm.LBMNoSlipBC;
import irmb.flowsim.lbm.numerics.lbm.LBMPressureBC;
import irmb.flowsim.lbm.numerics.lbm.LBMVelocityBC;
import irmb.flowsim.lbm.numerics.lbm.navierstokes.LBMNavierStokesGrid;
import irmb.flowsim.lbm.numerics.lbm.testcases.TestCase;

public class FlatPlateTestCase extends TestCase {

    public UniformGrid getGrid() {

        LBMNavierStokesGrid grid = new LBMNavierStokesGrid(0.5 /* length */, 0.2 /* width */, 0.002 /* dx */);

        grid.testcase = this.getClass().getSimpleName();

        // 0. Scaling considerations
        double Re = 100;
        double inflowVelo = 0.01; /* m/s */
        double Ma = 0.05;
        double vLB = Ma * 1. / Math.sqrt(3.);
        double dt = grid.dx / inflowVelo * vLB;

        // 1. Parameters
        grid.setGravity(0.0, 0.0 /* m/s^2 */);
        grid.setViscosity(0.0001 /* m^2/s */);
        grid.setTimeStep(dt /* s */);
        grid.updateParameters();

        // boundary layer thickness
        double deltaLB = Math.sqrt(grid.nue_lbm * grid.nx / vLB);
        double deltaRW = Math.sqrt(grid.viscosity * grid.getLength() / inflowVelo);
        System.out.println("Boundary layer thickness at the end of the domain: " + deltaLB);
        System.out.println("Boundary layer thickness at the end of the domain: " + deltaRW);

        // 2. Boundary conditions
        grid.addBC(new LBMPressureBC(grid, BoundaryCondition.EAST, 1.0));
        grid.addBC(new LBMVelocityBC(grid, BoundaryCondition.WEST, inflowVelo, 0.0));
        grid.addBC(new LBMVelocityBC(grid, BoundaryCondition.NORTH, inflowVelo, 0.0));
        grid.addBC(new LBMNoSlipBC(grid, BoundaryCondition.SOUTH));

        // 3. Initial conditions
        for (int i = 0; i < grid.nx; i++) {
            for (int j = 0; j < grid.ny; j++) {
                grid.init(i, j, 1. / 3., inflowVelo, 0.0);
            }
        }
        return grid;
    }
}
