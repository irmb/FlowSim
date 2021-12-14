package irmb.flowsim.view.swing;

import irmb.flowsim.model.Line;
import irmb.flowsim.model.Point;
import irmb.flowsim.model.Shape;
import irmb.flowsim.model.util.CoordinateTransformerImpl;
import irmb.flowsim.presentation.CommandStackImpl;
import irmb.flowsim.presentation.SimulationGraphicViewPresenter;
import irmb.flowsim.presentation.command.*;
import irmb.flowsim.view.graphics.PaintableLine;
import irmb.flowsim.view.graphics.PaintableShape;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/** Created by Sven on 15.12.2016. */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        setLookAndFeel();
        List<PaintableShape> shapeList = new LinkedList<>();

        var commandStack = new CommandStackImpl();

        var transformer = new CoordinateTransformerImpl();
        transformer.setWorldBounds(new Point(0, 0), new Point(1, 1));
        transformer.setViewBounds(new Point(0, 0), new Point(800, 600));

        var window = new MainWindow(transformer, null);
        var presenter = new SimulationGraphicViewPresenter(commandStack, shapeList);

        window.setPresenter(presenter);
        presenter.setGraphicView(window.getGraphicView());
        window.setVisible(true);

        // We're painting this line today and adding a few commands
        var line = new Line();
        line.setFirst(new Point(0, 0));
        line.setSecond(new Point(1, 0.5));
        var paintableLine = new PaintableLine(line);

        Thread.sleep(2000);

        var addPaintableShapeCommand = new AddPaintableShapeCommand(paintableLine, shapeList);
        addPaintableShapeCommand.execute();

        commandStack.add(addPaintableShapeCommand);
        window.repaint();

        Thread.sleep(2000);

        var zoomCommand = new ZoomCommand(transformer);
        zoomCommand.setZoomFactor(-0.5);
        zoomCommand.setZoomPoint(0, 0);
        zoomCommand.execute();

        commandStack.add(zoomCommand);
        window.repaint();
        Thread.sleep(2000);
        
        var panCommand = new PanWindowCommand(transformer);
        panCommand.setDelta(-80, 50);
        panCommand.execute();

        commandStack.add(panCommand);
        window.repaint();

        Thread.sleep(2000);

        var moveShapeCommand = new MoveShapeCommand(line);
        moveShapeCommand.setDelta(0.2, -0.2);
        moveShapeCommand.execute();

        commandStack.add(moveShapeCommand);
        window.repaint();
    }

    private static void setLookAndFeel() {
        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
                | IllegalAccessException ignored) {
        }
    }
}
