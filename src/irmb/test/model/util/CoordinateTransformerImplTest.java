package irmb.test.model.util;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.util.CoordinateTransformerImpl;
import org.junit.Before;
import org.junit.Test;

import static irmb.test.util.TestUtil.DELTA;
import static irmb.test.util.TestUtil.assertExpectedPointEqualsActual;
import static irmb.test.util.TestUtil.makePoint;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Sven on 10.01.2017.
 */
public class CoordinateTransformerImplTest extends CoordinateTransformerImpl {

    private CoordinateTransformerImplTest sut;

    @Before
    public void setUp() throws Exception {
        sut = new CoordinateTransformerImplTest();
        sut.setWorldBounds(makePoint(-15, 15), makePoint(10, -25));
        sut.setViewBounds(makePoint(0, 0), makePoint(800, 600));
    }

    @Test
    public void testTranslation() {
        sut.setWorldBounds(makePoint(0, 25), makePoint(10, 0));
        sut.setViewBounds(makePoint(0, 5), makePoint(10, 30));

        Point p = makePoint(0, 0);
        Point result = sut.transformToPointOnScreen(p);
        assertExpectedPointEqualsActual(makePoint(0, 30), result, 1);

    }

    @Test
    public void testScaling() {
        sut.setWorldBounds(makePoint(-25, 25), makePoint(25, -25));
        sut.setViewBounds(makePoint(-25, -25), makePoint(25, 25));

        Point origin = makePoint(0, 0);
        Point result = sut.transformToPointOnScreen(origin);
        assertExpectedPointEqualsActual(makePoint(0, 0), result, 1);

        Point topLeft = makePoint(-25, 25);
        Point bottomRight = makePoint(25, -25);

        double distance = getDistance(topLeft, bottomRight);

        sut.setViewBounds(makePoint(-50, -50), makePoint(50, 50));
        Point topLeftView = sut.transformToPointOnScreen(topLeft);
        Point bottomRightView = sut.transformToPointOnScreen(bottomRight);
        assertEquals(distance * 2, getDistance(topLeftView, bottomRightView));
    }

    private double getDistance(Point first, Point second) {
        double dx = first.getX() - second.getX();
        double dy = first.getY() - second.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Test
    public void testTransformToPointOnScreen() {
        Point p = makePoint(0, 0);

        Point result = sut.transformToPointOnScreen(p);
        assertExpectedPointEqualsActual(makePoint(437.5, 225), result);

        p = makePoint(-3, 12);

        result = sut.transformToPointOnScreen(p);
        assertExpectedPointEqualsActual(makePoint(392.5, 45), result);

        p = makePoint(-18, 20);

        result = sut.transformToPointOnScreen(p);
        assertExpectedPointEqualsActual(makePoint(167.5, -75), result);
    }

    @Test
    public void testTransformToWorldPoint() {
        Point p = makePoint(437.5, 225);

        Point result = sut.transformToWorldPoint(p);
        assertExpectedPointEqualsActual(makePoint(0, 0), result);

        p = makePoint(392.5, 45);

        result = sut.transformToWorldPoint(p);
        assertExpectedPointEqualsActual(makePoint(-3, 12), result);

        p = makePoint(167.5, -75);

        result = sut.transformToWorldPoint(p);
        assertExpectedPointEqualsActual(makePoint(-18, 20), result);
    }

    @Test
    public void testMoveViewWindow() {
        sut.moveViewWindow(5, 23);
        assertExpectedPointEqualsActual(makePoint(5, 23), sut.viewTopLeft);
        assertExpectedPointEqualsActual(makePoint(805, 623), sut.viewBottomRight);
    }

    @Test
    public void testScaleToScreenLength() {
        double screenLength = sut.scaleToScreenLength(5);
        assertEquals(5 * 15, screenLength, DELTA);

        screenLength = sut.scaleToScreenLength(34);
        assertEquals(34 * 15, screenLength, DELTA);
    }

    @Test
    public void testScaleToWorldLength() {
        double worldLength = sut.scaleToWorldLength(15);
        assertEquals(1, worldLength, DELTA);

        worldLength = sut.scaleToWorldLength(75);
        assertEquals(75 / 15, worldLength, DELTA);

        sut.setWorldBounds(makePoint(0, 0), makePoint(800, 600));
        sut.setViewBounds(makePoint(-15, 15), makePoint(10, -25));

        double expectedScaleFactor = 0.03125;
        worldLength = sut.scaleToWorldLength(15);
        assertEquals(15 / expectedScaleFactor, worldLength);
    }


}