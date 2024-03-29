package irmb.flowsim.simulation.jflowsim.adapters;

import irmb.flowsim.lbm.numerics.lbm.navierstokes.LBMNavierStokesGrid;
import irmb.flowsim.lbm.numerics.utilities.GridNodeType;
import irmb.flowsim.lbm.numerics.utilities.Scalar;
import irmb.flowsim.model.Point;
import irmb.flowsim.simulation.UniformGrid;

/**
 * Created by sven on 09.03.17.
 */
public class JFlowSimNavierStokesGridAdapter implements UniformGrid {
    private LBMNavierStokesGrid grid;
    private Point topLeft;

    public JFlowSimNavierStokesGridAdapter(LBMNavierStokesGrid grid) {
        this.grid = grid;
        topLeft = new Point(grid.getMinX(), grid.getMaxY());
    }

    @Override
    public void setHeight(double length) {
        grid.setWidth(length);
    }

    @Override
    public double getHeight() {
        return grid.getWidth();
    }

    @Override
    public void setWidth(double width) {
        grid.setLength(width);
    }

    @Override
    public double getWidth() {
        return grid.getLength();
    }

    @Override
    public void setHorizontalNodes(int horizontalNodes) {
        grid.nx = horizontalNodes;
    }

    @Override
    public int getHorizontalNodes() {
        return grid.nx;
    }

    @Override
    public void setVerticalNodes(int verticalNodes) {
        grid.ny = verticalNodes;
    }

    @Override
    public int getVerticalNodes() {
        return grid.ny;
    }

    @Override
    public double getVelocityAt(int x, int y) {
        return grid.getScalar(x, y, Scalar.V);
    }

    public void setTopLeft(Point point) {
        this.topLeft = point;
    }

    @Override
    public Point getTopLeft() {
        return topLeft;
    }

    @Override
    public void setDelta(double delta) {
        grid.dx = delta;
    }

    @Override
    public double getDelta() {
        return grid.dx;
    }

    @Override
    public void resetSolidNodes() {
        for (int x = 0; x < grid.nx; x++) {
            for (int y = 0; y < grid.ny; y++) {
                if (grid.getType(x, y) == GridNodeType.SOLID) {
                    grid.setType(x, y, GridNodeType.FLUID);
                }
            }
        }
    }

    @Override
    public void setSolid(int x, int y) {
        grid.setType(x, y, GridNodeType.SOLID);
    }

    @Override
    public boolean isPointInside(Point point) {
        return !(point.getX() < topLeft.getX() || point.getX() > topLeft.getX() + grid.getLength() || point.getY() < topLeft.getY() - grid.getWidth() || point.getY() > topLeft.getY());
    }

    @Override
    public boolean isSolid(int x, int y) {
        return grid.getType(x, y) == GridNodeType.SOLID;
    }

    @Override
    public double getHorizontalVelocityAt(int x, int y) {
        return grid.getScalar(x, y, Scalar.V_X);
    }

    @Override
    public double getVerticalVelocityAt(int x, int y) {
        return grid.getScalar(x, y, Scalar.V_Y);
    }

    @Override
    public double getMNUPS() {
        return grid.mnups;
    }

    @Override
    public double getRealTime() {
        return grid.real_time;
    }

    @Override
    public double getViscosity() {
        return grid.viscosity;
    }

    @Override
    public double getVerticalGravity() {
        return grid.gravityY;
    }

    @Override
    public double getHorizontalGravity() {
        return grid.gravityX;
    }

    @Override
    public int getTimeStep() {
        return grid.timestep;
    }

    public LBMNavierStokesGrid getJFlowSimGrid() {
        return grid;
    }

    public int getGridCoordinatesX(double x) {
        return (int) Math.floor((x - getTopLeft().getX()) / getDelta());
    }

    public int getGridCoordinatesY(double y) {
        return (int) Math.floor((getTopLeft().getY() - y) / getDelta());
    }

    public double getWorldCoordinatesX(int x) {
        return getTopLeft().getX() + getDelta() * x;
    }

    public double getWorldCoordinatesY(int y) {
        return getTopLeft().getY() - getDelta() * y;
    }
}
