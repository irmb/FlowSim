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
import irmb.flowsim.presentation.factory.PaintableShapeBuilderFactoryImpl;
import irmb.flowsim.presentation.factory.PaintableShapeFactoryImpl;
import irmb.flowsim.presentation.factory.ShapeFactoryImpl;

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


        var shapeFactory = new ShapeFactoryImpl();
        var paintableShapeFactory = new PaintableShapeFactoryImpl(transformer);
        var builderFactory =
                new PaintableShapeBuilderFactoryImpl(shapeFactory, paintableShapeFactory);

        var window = new MainWindow(transformer, builderFactory.getShapeChoices());
        var presenter = new SimulationGraphicViewPresenter(commandStack, shapeList);

        window.setPresenter(presenter);
        presenter.setGraphicView(window.getGraphicView());
        window.setVisible(true);


        // Exercise 3:
        var builder = builderFactory.makeShapeBuilder("Line");
        builder.addPoint(new Point(0, 0));
        builder.addPoint(new Point(1, 0.5));
        PaintableShape paintableShape = builder.getShape();

        var addShapeCommand = new AddPaintableShapeCommand(paintableShape, shapeList);
        addShapeCommand.execute();
        commandStack.add(addShapeCommand);

        builder = builderFactory.makeShapeBuilder("PolyLine");
        builder.addPoint(new Point(0.2, 0));
        builder.addPoint(new Point(0.6, 0.2));
        builder.addPoint(new Point(0.8, 0));
        builder.addPoint(new Point(1, 0.5));
        paintableShape = builder.getShape();

        addShapeCommand = new AddPaintableShapeCommand(paintableShape, shapeList);
        addShapeCommand.execute();
        commandStack.add(addShapeCommand);

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
