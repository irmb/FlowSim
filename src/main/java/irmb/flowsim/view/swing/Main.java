package irmb.flowsim.view.swing;

import irmb.flowsim.model.Line;
import irmb.flowsim.model.Point;
import irmb.flowsim.model.Shape;
import irmb.flowsim.model.util.CoordinateTransformerImpl;
import irmb.flowsim.presentation.CommandStackImpl;
import irmb.flowsim.presentation.SimulationGraphicViewPresenter;
import irmb.flowsim.presentation.builder.TwoPointShapeBuilder;
import irmb.flowsim.presentation.command.AddPaintableShapeCommand;
import irmb.flowsim.presentation.command.MoveShapeCommand;
import irmb.flowsim.presentation.command.PanWindowCommand;
import irmb.flowsim.presentation.command.ZoomCommand;
import irmb.flowsim.presentation.factory.MouseStrategyFactoryImpl;
import irmb.flowsim.presentation.factory.PaintableShapeBuilderFactoryImpl;
import irmb.flowsim.presentation.factory.PaintableShapeFactoryImpl;
import irmb.flowsim.presentation.factory.ShapeFactoryImpl;
import irmb.flowsim.simulation.SimulationFactoryImpl;
import irmb.flowsim.simulation.visualization.GridNodeStyleFactory;
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
        transformer.setWorldBounds(new Point(0, 0.5), new Point(1, 0));
        transformer.setViewBounds(new Point(0, 0), new Point(800, 600));

        var shapeFactory = new ShapeFactoryImpl();
        var paintableShapeFactory = new PaintableShapeFactoryImpl(transformer);
        var builderFactory =
                new PaintableShapeBuilderFactoryImpl(shapeFactory, paintableShapeFactory);


        var window = new MainWindow(transformer, builderFactory.getShapeChoices());
        var mouseStrategyFactory =
                new MouseStrategyFactoryImpl(shapeList, builderFactory, transformer);
        var simulationFactory = new SimulationFactoryImpl();

        var gridNodeStyleFactory = new GridNodeStyleFactory(transformer);
        var presenter =
                new SimulationGraphicViewPresenter(
                        mouseStrategyFactory,
                        commandStack,
                        shapeList,
                        simulationFactory,
                        gridNodeStyleFactory);

        window.setPresenter(presenter);
        presenter.setGraphicView(window.getGraphicView());
        window.setVisible(true);
    }

    private static void setLookAndFeel() {
        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
                | IllegalAccessException ignored) {
        }
    }
}
