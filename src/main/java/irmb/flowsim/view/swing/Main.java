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
    }

    private static void setLookAndFeel() {
        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
                | IllegalAccessException ignored) {
        }
    }
}
