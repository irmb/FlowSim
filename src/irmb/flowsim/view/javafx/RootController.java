package irmb.flowsim.view.javafx;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.GraphicView;
import irmb.flowsim.presentation.GraphicViewPresenter;
import irmb.flowsim.presentation.SimulationGraphicViewPresenter;
import irmb.flowsim.view.graphics.Paintable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Created by Sven on 22.12.2016.
 */
public class RootController implements GraphicView {

    @FXML
    private ButtonBar buttonBar;
    @FXML
    private Pane rootPane;
    @FXML
    private Button lineButton;
    @FXML
    private Button rectangleButton;
    @FXML
    private Button polyLineButton;
    @FXML
    private Canvas drawPanel;

    private SimulationGraphicViewPresenter presenter;
    private JavaFXPainter painter;
    private volatile CoordinateTransformer transformer;
    private GraphicsContext graphicsContext2D;
    private Runnable runnable;

    public RootController() {
    }

    public void setPresenter(SimulationGraphicViewPresenter presenter) {
        this.presenter = presenter;
    }

    @FXML
    public void initialize() {
        drawPanel.heightProperty().bind(rootPane.heightProperty());
        drawPanel.widthProperty().bind(rootPane.widthProperty());
        drawPanel.heightProperty().addListener(o -> {
            update();
        });
        drawPanel.widthProperty().addListener(o -> {
            update();
        });
        drawPanel.setOnScroll(event -> {
            presenter.handleScroll(event.getX(), event.getY(), (int) event.getDeltaY());
        });

        runnable = () -> {
            if (graphicsContext2D == null)
                graphicsContext2D = drawPanel.getGraphicsContext2D();
            if (painter == null)
                painter = new JavaFXPainter();
            painter.setGraphicsContext(graphicsContext2D);
            graphicsContext2D.clearRect(0, 0, drawPanel.getWidth(), drawPanel.getHeight());
//            graphicsContext2D.setFill(Color.STEELBLUE);
//            graphicsContext2D.fillRect(0, 0, drawPanel.getWidth(), drawPanel.getHeight());
            for (Paintable p : presenter.getPaintableList()) {
                p.paint(painter, transformer);
            }
        };
    }

    public void onLineButtonClick(ActionEvent event) {
        presenter.beginPaint("Line");
    }

    public void onRectangleButtonClick(ActionEvent event) {
        presenter.beginPaint("Rectangle");
    }

    public void onPolyLineButtonClick(ActionEvent event) {
        presenter.beginPaint("PolyLine");
    }

    public void onMousePressed(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY)
            presenter.handleLeftClick(event.getX(), event.getY());
        else if (event.getButton() == MouseButton.SECONDARY)
            presenter.handleRightClick(event.getX(), event.getY());
        else if (event.getButton() == MouseButton.MIDDLE)
            presenter.handleMiddleClick(event.getX(), event.getY());
    }

    public void onMouseMoved(MouseEvent event) {
        presenter.handleMouseMove(event.getX(), event.getY());
    }

    public void onMouseDragged(MouseEvent event) {
        presenter.handleMouseDrag(event.getX(), event.getY());
    }

    public void onMouseReleased(MouseEvent event) {
        presenter.handleMouseRelease();
    }

    public void onUndoButtonClick(ActionEvent event) {
        presenter.undo();
    }

    public void onRedoButtonClick(ActionEvent event) {
        presenter.redo();
    }

    public void onAddSimulationButtonClick(ActionEvent event) {
        presenter.addSimulation();
    }

    public void onRunSimulationClick(ActionEvent event) {
        presenter.runSimulation();
    }

    @Override
    public void update() {
        Platform.runLater(runnable);
    }

    @Override
    public void setCoordinateTransformer(CoordinateTransformer transformer) {
        this.transformer = transformer;
        transformer.setViewBounds(new Point(0, 0), new Point(800, 600));
    }
}
