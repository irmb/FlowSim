package irmb.test.presentation;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


import static irmb.mockito.verification.AtLeastThenForget.atLeastThenForget;
import static irmb.mockito.verification.AtLeastThenForgetAll.atLeastThenForgetAll;
import static org.mockito.Mockito.*;

/**
 * Created by Sven on 15.12.2016.
 */
@RunWith(HierarchicalContextRunner.class)
public class GraphicViewPresenterAcceptanceTests extends GraphicViewPresenterTest {

    public class SimplePaintingContext {
        @Test
        public void buildLineAcceptanceTest() {
            sut.beginPaint("Line");
            sut.handleLeftClick(13, 15);
            sut.handleLeftClick(18, 19);
            verify(painterSpy, times(1)).paintLine(13, 15, 18, 19);

            sut.beginPaint("Line");
            sut.handleLeftClick(36, 12);
            sut.handleLeftClick(25, 57);
            verify(painterSpy, times(1)).paintLine(36, 12, 25, 57);

            verify(painterSpy, times(2)).paintLine(13, 15, 18, 19);
        }

        @Test
        public void buildRectangleAcceptanceTest() {
            sut.beginPaint("Rectangle");
            sut.handleLeftClick(13, 15);
            sut.handleLeftClick(18, 19);
            verify(painterSpy, times(1)).paintRectangle(13, 15, 5, 4);

            sut.beginPaint("Rectangle");
            sut.handleLeftClick(36, 12);
            sut.handleLeftClick(25, 57);
            verify(painterSpy, times(1)).paintRectangle(25, 12, 11, 45);

            verify(painterSpy, times(2)).paintRectangle(13, 15, 5, 4);
        }

        @Test
        public void buildPolyLineAcceptanceTest() {
            sut.beginPaint("PolyLine");
            sut.handleLeftClick(13, 15);
            sut.handleLeftClick(18, 19);
            verify(painterSpy, times(1)).paintLine(13, 15, 18, 19);

            sut.handleLeftClick(36, 12);
            verify(painterSpy, times(2)).paintLine(13, 15, 18, 19);
            verify(painterSpy, times(1)).paintLine(18, 19, 36, 12);
        }
    }

    public class LivePaintingContext {
        @Test
        public void livePaintingLineAcceptanceTest() {
            sut.beginPaint("Line");

            sut.handleLeftClick(13, 15);
            sut.handleMouseMove(18, 19);
            verify(painterSpy, times(1)).paintLine(13, 15, 18, 19);

            sut.handleMouseMove(36, 12);
            verify(painterSpy, times(1)).paintLine(13, 15, 36, 12);

            sut.handleLeftClick(36, 12);
            verify(painterSpy, times(2)).paintLine(13, 15, 36, 12);

            sut.handleMouseMove(25, 57);
            verifyNoMoreInteractions(painterSpy);
        }

        @Test
        public void livePaintingRectangleAcceptanceTest() {
            sut.beginPaint("Rectangle");

            sut.handleLeftClick(13, 15);
            sut.handleMouseMove(18, 19);
            verify(painterSpy).paintRectangle(13, 15, 5, 4);

            sut.handleMouseMove(36, 12);
            verify(painterSpy).paintRectangle(13, 12, 23, 3);

            sut.handleLeftClick(36, 12);
            verify(painterSpy, times(2)).paintRectangle(13, 12, 23, 3);

            sut.handleMouseMove(25, 57);
            verifyNoMoreInteractions(painterSpy);
        }

        @Test
        public void livePaintingPolyLineAcceptanceTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            sut.beginPaint("PolyLine");

            sut.handleLeftClick(13, 15);
            sut.handleMouseMove(18, 19);
            verify(painterSpy, times(1)).paintLine(13, 15, 18, 19);


            sut.handleMouseMove(36, 12);
            verify(painterSpy, times(1)).paintLine(13, 15, 36, 12);

            sut.handleLeftClick(36, 12);
            verify(painterSpy, times(2)).paintLine(13, 15, 36, 12);

            sut.handleMouseMove(24, 20);
            verify(painterSpy, times(3)).paintLine(13, 15, 36, 12);
            verify(painterSpy, times(1)).paintLine(36, 12, 24, 20);

            sut.handleLeftClick(24, 20);
            verify(painterSpy, times(4)).paintLine(13, 15, 36, 12);
            verify(painterSpy, times(2)).paintLine(36, 12, 24, 20);

            sut.handleMouseMove(43, 22);
            verify(painterSpy, times(5)).paintLine(13, 15, 36, 12);
            verify(painterSpy, times(3)).paintLine(36, 12, 24, 20);
            verify(painterSpy, times(1)).paintLine(24, 20, 43, 22);

            sut.handleRightClick();
            verify(painterSpy, times(6)).paintLine(13, 15, 36, 12);
            verify(painterSpy, times(4)).paintLine(36, 12, 24, 20);
            verify(painterSpy, times(1)).paintLine(24, 20, 43, 22);

            sut.handleMouseMove(35, 84);
            sut.handleLeftClick(35, 84);
            verify(painterSpy, never()).paintLine(43, 22, 35, 84);
        }
    }

    @Test
    public void moveShapeAcceptanceTest() {
        sut.beginPaint("Line");

        buildLine(13, 15, 18, 19);
        verify(painterSpy, atLeastOnce()).paintLine(13, 15, 18, 19);

        sut.handleLeftClick(15, 18);
        sut.handleMouseDrag(20, 24);
        verify(painterSpy, atLeastOnce()).paintLine(18, 21, 23, 25);

        sut.handleMouseDrag(3, 10);
        verify(painterSpy, atLeastOnce()).paintLine(1, 7, 6, 11);

        sut.handleMouseRelease();

        sut.handleLeftClick(0, 0);
        sut.handleMouseDrag(15, 18);
        verifyNoMoreInteractions(painterSpy);
    }

    @Test
    public void commandQueueAcceptanceTest() {

        buildLine(13, 15, 18, 19);
        verify(painterSpy, atLeastThenForget(1)).paintLine(13, 15, 18, 19);

        performMove(15, 18, 20, 24);
        verify(painterSpy, atLeastThenForget(1)).paintLine(18, 21, 23, 25);

        sut.undo();
        verify(painterSpy, atLeastThenForget(1)).paintLine(13, 15, 18, 19);

        sut.undo();
        verifyNoMoreInteractions(painterSpy);

        sut.undo();
        verifyNoMoreInteractions(painterSpy);

        sut.redo();
        verify(painterSpy, atLeastThenForget(1)).paintLine(13, 15, 18, 19);

        sut.redo();
        verify(painterSpy, atLeastThenForget(1)).paintLine(18, 21, 23, 25);

        sut.redo();
        verifyNoMoreInteractions(painterSpy);

        sut.undo();
        verify(painterSpy, atLeastThenForget(1)).paintLine(13, 15, 18, 19);

        List<Double> coordinates = makePolyLineCoordinates();
        buildPolyLine(coordinates);
        verify(painterSpy, atLeastThenForget(1)).paintLine(13, 15, 18, 19);
        verify(painterSpy, atLeastThenForget(1)).paintLine(35, 40, 10, 54);
        verify(painterSpy, atLeastThenForget(1)).paintLine(10, 54, 65, 74);

        sut.undo();
        verify(painterSpy, atLeastThenForget(1)).paintLine(13, 15, 18, 19);
        verify(painterSpy, never()).paintLine(35, 40, 10, 54);
        verify(painterSpy, never()).paintLine(10, 54, 65, 74);

        sut.redo();
        verify(painterSpy, atLeastThenForget(1)).paintLine(13, 15, 18, 19);
        verify(painterSpy, atLeastThenForget(1)).paintLine(35, 40, 10, 54);
        verify(painterSpy, atLeastThenForgetAll(1)).paintLine(10, 54, 65, 74);

        sut.redo();
        verifyNoMoreInteractions(painterSpy);
    }

    private List<Double> makePolyLineCoordinates() {
        List<Double> coordinates = new ArrayList<>();
        coordinates.add(35.);
        coordinates.add(40.);
        coordinates.add(10.);
        coordinates.add(54.);
        coordinates.add(65.);
        coordinates.add(74.);
        return coordinates;
    }

    @Test
    public void moveViewWindowAcceptanceTest() {
        buildLine(13, 15, 18, 19);
        verify(painterSpy, atLeastThenForget(1)).paintLine(13, 15, 18, 19);

        List<Double> coordinates = makePolyLineCoordinates();
        buildPolyLine(coordinates);
        verify(painterSpy, atLeastThenForget(1)).paintLine(13, 15, 18, 19);
        verify(painterSpy, atLeastThenForget(1)).paintLine(35, 40, 10, 54);
        verify(painterSpy, atLeastThenForget(1)).paintLine(10, 54, 65, 74);

        performMove(4, 5, 10, 10);
        verify(painterSpy, atLeastThenForget(1)).paintLine(19, 20, 24, 24);
        verify(painterSpy, atLeastThenForget(1)).paintLine(41, 45, 16, 59);
        verify(painterSpy, atLeastThenForget(1)).paintLine(16, 59, 71, 79);

        performMove(10, 10, 3, 2);
        verify(painterSpy, atLeastThenForget(1)).paintLine(12, 12, 17, 16);
        verify(painterSpy, atLeastThenForget(1)).paintLine(34, 37, 9, 51);
        verify(painterSpy, atLeastThenForget(1)).paintLine(9, 51, 64, 71);
    }

}
