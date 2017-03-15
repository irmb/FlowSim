package irmb.test.simulation;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import irmb.flowsim.model.Line;
import irmb.flowsim.model.Point;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.Color;
import irmb.flowsim.presentation.Painter;
import irmb.flowsim.presentation.factory.ColorFactory;
import irmb.flowsim.simulation.LBMChannelFlowSimulation;
import irmb.flowsim.simulation.UniformGrid;
import irmb.flowsim.util.Observer;
import irmb.flowsim.view.graphics.PaintableLine;
import irmb.flowsim.view.graphics.PaintableShape;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static irmb.test.util.TestUtil.makePoint;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

/**
 * Created by sven on 04.03.17.
 */
@RunWith(HierarchicalContextRunner.class)
public class LBMChannelFlowSimulationTest {

    private UniformGrid gridSpy;
    private LBMChannelFlowSimulation sut;
    private Painter painterSpy;
    private CoordinateTransformer transformer;
    private int horizontalNodes;
    private int verticalNodes;

    private double fMax;
    private double fMin;

    private double[][] gridValues;
    private SolverMock solverSpy;
    private ColorFactory colorFactory;

    @Before
    public void canCreateLBMChannelFlowSimulation() {
        fMax = Double.MIN_VALUE;
        fMin = Double.MAX_VALUE;
        solverSpy = spy(new SolverMock());
        painterSpy = mock(Painter.class);
        transformer = mock(CoordinateTransformer.class);
        colorFactory = mock(ColorFactory.class);
        setColorFactoryBehavior(colorFactory);
        when(transformer.transformToPointOnScreen(any(Point.class))).thenAnswer(invocationOnMock -> {
            Point argument = invocationOnMock.getArgument(0);
            Point p = makePoint(argument.getX() + 1, argument.getY() + 5);
            return p;
        });
        when(transformer.scaleToScreenLength(anyDouble())).thenAnswer(invocationOnMock -> (double) invocationOnMock.getArgument(0) * 7);
    }

    private void setColorFactoryBehavior(ColorFactory colorFactory) {
        when(colorFactory.makeColorForValue(anyDouble(), anyDouble(), anyDouble())).thenAnswer(invocationOnMock -> {
            int r = ((Double) invocationOnMock.getArgument(0)).intValue();
            int g = ((Double) invocationOnMock.getArgument(1)).intValue();
            int b = ((Double) invocationOnMock.getArgument(2)).intValue();
            return new Color(r, g, b);
        });
    }

    private void setGridBehavior(Point origin, double gridDelta) {
        initGridValues();
        when(gridSpy.getTopLeft()).thenReturn(origin);
        double width = (horizontalNodes - 1) * gridDelta;
        when(gridSpy.getWidth()).thenReturn(width);
        double height = (verticalNodes - 1) * gridDelta;
        when(gridSpy.getHeight()).thenReturn(height);
        when(gridSpy.getHorizontalNodes()).thenReturn(horizontalNodes);
        when(gridSpy.getVerticalNodes()).thenReturn(verticalNodes);
        when(gridSpy.getDelta()).thenReturn(gridDelta);
        when(gridSpy.getVelocityAt(anyInt(), anyInt())).thenAnswer(invocationOnMock -> {
            return gridValues[(int) invocationOnMock.getArgument(1)][(int) invocationOnMock.getArgument(0)];
        });
        when(gridSpy.isPointInside(any(Point.class))).thenAnswer(invocationOnMock -> {
            Point point = invocationOnMock.getArgument(0);
            return !(point.getX() < origin.getX() || point.getX() > origin.getX() + width || point.getY() < origin.getY() - height || point.getY() > origin.getY());
        });
    }

    private void initGridValues() {
        Random rnd = new Random();
        gridValues = new double[verticalNodes][horizontalNodes];
        for (int i = 0; i < verticalNodes; i++)
            for (int j = 0; j < horizontalNodes; j++) {
                int value = rnd.nextInt(1000);
                if (value > fMax)
                    fMax = value;
                if (value < fMin)
                    fMin = value;
                gridValues[i][j] = value;
            }
    }

    public class BasicGridContext {
        @Before
        public void setUp() {
            gridSpy = mock(UniformGrid.class);
            horizontalNodes = 4;
            verticalNodes = 3;
            setGridBehavior(makePoint(0, 2), 1.);

            sut = new LBMChannelFlowSimulation(gridSpy, solverSpy, colorFactory);
        }

        @Test
        public void whenCallingPaint_shouldSetCorrectColors() {
            sut.paint(painterSpy, transformer);
            ArgumentCaptor<Color> colorCaptor = ArgumentCaptor.forClass(Color.class);
            verify(painterSpy, times(verticalNodes * horizontalNodes)).setColor(colorCaptor.capture());

            assertCorrectGridColors(colorCaptor.getAllValues());
        }


        @Test
        public void whenPaintingSecondTimeAfterGridExtremesChanged_shouldSetCorrectColors() {
            sut.paint(painterSpy, transformer);
            clearInvocations(painterSpy);

            fMin = -50;
            fMax = 1001;
            gridValues[2][1] = fMin;
            gridValues[0][2] = fMax;

            sut.paint(painterSpy, transformer);
            ArgumentCaptor<Color> colorCaptor = ArgumentCaptor.forClass(Color.class);
            verify(painterSpy, times(verticalNodes * horizontalNodes)).setColor(colorCaptor.capture());
            assertCorrectGridColors(colorCaptor.getAllValues());
        }


        private void assertCorrectGridColors(List<Color> capturedValues) {
            int index = 0;
            for (int i = 0; i < verticalNodes; i++)
                for (int j = 0; j < horizontalNodes; j++) {
                    assertColorEquals(capturedValues.get(index), (int) fMin, (int) fMax, (int) gridValues[i][j]);
                    index++;
                }
        }

        private void assertColorEquals(Color c, int r, int g, int b) {
            assertEquals(r, c.r);
            assertEquals(g, c.g);
            assertEquals(b, c.b);

        }

        @Test
        public void whenCallingPaint_shouldPaintFilledRectanglesWithCorrectColor() {
            sut.paint(painterSpy, transformer);
            ArgumentCaptor<Color> colorCaptor = ArgumentCaptor.forClass(Color.class);
            ArgumentCaptor<Double> doubleCaptor = ArgumentCaptor.forClass(Double.class);
            int minNumberOfInvocations = horizontalNodes * verticalNodes;
            verify(painterSpy, atLeast(minNumberOfInvocations)).setColor(colorCaptor.capture());
            verify(painterSpy, atLeast(minNumberOfInvocations)).fillRectangle(
                    doubleCaptor.capture(),
                    doubleCaptor.capture(),
                    doubleCaptor.capture(),
                    doubleCaptor.capture()
            );
            assertCorrectGridColors(colorCaptor.getAllValues());
            assertCorrectRectangleValues(doubleCaptor.getAllValues());
        }

        private void assertCorrectRectangleValues(List<Double> capturedDoubles) {
            int index = 0;
            for (int i = 0; i < verticalNodes; i++)
                for (int j = 0; j < horizontalNodes; j++) {
                    assertEquals(1 + j * 7, Math.round(capturedDoubles.get(index++)));
                    assertEquals(5 + i * 7, Math.round(capturedDoubles.get(index++)));
                    assertEquals(7, Math.round(capturedDoubles.get(index++)));
                    assertEquals(7, Math.round(capturedDoubles.get(index++)));
                }
        }

        @Test
        public void whenCallingRun_shouldStartSolver() {
            sut.run();
            verify(solverSpy).solve();
        }

        @Test
        public void whenCallingPause_shouldPauseSolver() {
            sut.pause();
            verify(solverSpy).pause();
        }

        @Test
        public void whenSolverNotifiesSimulation_shouldNotifyObservers() {
            Observer<String> observerSpy = mock(Observer.class);
            sut.addObserver(observerSpy);

            solverSpy.notifyObservers("");

            verify(observerSpy).update(any());
        }

        @Test
        public void whenCallingSetShapesWithEmptyList_shouldOnlyResetGrid() {
            List<PaintableShape> shapeList = new ArrayList<>();
            sut.setShapes(shapeList);
            verify(gridSpy).resetSolidNodes();
            verifyNoMoreInteractions(gridSpy);
        }


        @Test
        public void whenCallingSetShapesWithLine_shouldMapToGrid() {
            List<PaintableShape> shapeList = getPaintableShapeListWithLine(makePoint(0, 0), makePoint(3, 2));

            sut.setShapes(shapeList);

            verify(gridSpy).resetSolidNodes();
            verify(gridSpy).setSolid(0, 2);
            verify(gridSpy).setSolid(1, 1);
            verify(gridSpy).setSolid(2, 1);
            verify(gridSpy).setSolid(3, 0);

            clearInvocations(gridSpy);

            shapeList = getPaintableShapeListWithLine(makePoint(1, 0), makePoint(3, 2));

            sut.setShapes(shapeList);

            verify(gridSpy).resetSolidNodes();
            verify(gridSpy).setSolid(1, 2);
            verify(gridSpy).setSolid(2, 1);
            verify(gridSpy).setSolid(3, 0);
        }

    }


    public class GridWithOffsetContext {

        @Before
        public void setUp() {
            gridSpy = mock(UniformGrid.class);
            horizontalNodes = 5;
            verticalNodes = 8;
            setGridBehavior(makePoint(-1, 10), 1.);

            sut = new LBMChannelFlowSimulation(gridSpy, solverSpy, colorFactory);
        }


        @Test
        public void whenCallingSetShapesWithLineOnAltSetup_shouldMapToGrid() {

            List<PaintableShape> shapeList = getPaintableShapeListWithLine(makePoint(0, 3), makePoint(3, 5));

            sut.setShapes(shapeList);

            verify(gridSpy).resetSolidNodes();
            verify(gridSpy).setSolid(1, 7);
            verify(gridSpy).setSolid(2, 6);
            verify(gridSpy).setSolid(3, 6);
            verify(gridSpy).setSolid(4, 5);
        }
    }


    public class RealisticGridContext {

        @Before
        public void setUp() {
            gridSpy = mock(UniformGrid.class);
            horizontalNodes = 10;
            verticalNodes = 16;
            setGridBehavior(makePoint(-1, 10), .5);

            sut = new LBMChannelFlowSimulation(gridSpy, solverSpy, colorFactory);
        }

        @Test
        public void whenCallingSetShapesWithLineOnGridWithNonIntDelta_shouldMapToGrid() {
            List<PaintableShape> shapeList = getPaintableShapeListWithLine(makePoint(0, 3), makePoint(3, 5));

            sut.setShapes(shapeList);

            verify(gridSpy).resetSolidNodes();
            verify(gridSpy).setSolid(2, 14);
            verify(gridSpy).setSolid(3, 13);
            verify(gridSpy).setSolid(4, 13);
            verify(gridSpy).setSolid(5, 12);
            verify(gridSpy).setSolid(6, 11);
            verify(gridSpy).setSolid(7, 11);
            verify(gridSpy).setSolid(8, 10);
        }

        @Test
        public void whenCallingSetShapesWithLineOutsideGridBounds_shouldNotMapToGrid() {
            List<PaintableShape> shapeList = getPaintableShapeListWithLine(makePoint(-1.5, 4), makePoint(3, 5));

            sut.setShapes(shapeList);

            verify(gridSpy).resetSolidNodes();
            verify(gridSpy, never()).setSolid(anyInt(), anyInt());

            clearInvocations(gridSpy);

            shapeList = getPaintableShapeListWithLine(makePoint(3.7, 5), makePoint(0, 3));

            sut.setShapes(shapeList);

            verify(gridSpy).resetSolidNodes();
            verify(gridSpy, never()).setSolid(anyInt(), anyInt());

            clearInvocations(gridSpy);

            shapeList = getPaintableShapeListWithLine(makePoint(2, 2), makePoint(0, 3));

            sut.setShapes(shapeList);

            verify(gridSpy).resetSolidNodes();
            verify(gridSpy, never()).setSolid(anyInt(), anyInt());

            clearInvocations(gridSpy);

            shapeList = getPaintableShapeListWithLine(makePoint(2, 4), makePoint(0, 11));

            sut.setShapes(shapeList);

            verify(gridSpy).resetSolidNodes();
            verify(gridSpy, never()).setSolid(anyInt(), anyInt());
        }
    }

    private List<PaintableShape> getPaintableShapeListWithLine(Point first, Point second) {
        Line line = new Line();
        line.setFirst(first);
        line.setSecond(second);
        PaintableLine paintableLine = new PaintableLine(line);
        List<PaintableShape> shapeList = new ArrayList<>();
        shapeList.add(paintableLine);
        return shapeList;
    }


}


