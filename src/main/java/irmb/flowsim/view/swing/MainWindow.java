package irmb.flowsim.view.swing;

import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.GraphicView;
import irmb.flowsim.presentation.SimulationGraphicViewPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

/** Created by Sven on 15.12.2016. */
public class MainWindow extends JFrame {

    private JPanel drawArea;
    private JPanel contentPanel;
    private JButton addSimulationButton;
    private JButton undoButton;
    private JButton redoButton;
    private JButton clearButton;
    private JButton pauseSimulationButton;
    private JButton runSimulationButton;
    private JButton removeSimulationButton;

    private JMenuItem undoMenuItem;
    private JMenuItem redoMenuItem;
    private JMenuItem clearMenuItem;

    private JMenuItem addSimulationMenuItem;
    private JMenuItem runSimulationMenuItem;
    private JMenuItem pauseSimulationMenuItem;
    private JMenuItem removeSimulationMenuItem;

    private SimulationGraphicViewPresenter presenter;
    private final CoordinateTransformer transformer;

    public MainWindow(CoordinateTransformer transformer, String[] shapes) {
        this.transformer = transformer;
        setTitle("FlowSim 2.0");
        setSize(1250, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setupUI(shapes);
        initListeners();
    }

    public GraphicView getGraphicView() {
        return (GraphicView) drawArea;
    }

    public void setPresenter(SimulationGraphicViewPresenter presenter) {
        this.presenter = presenter;
        ((SwingGraphicView) drawArea).setPresenter(presenter);
    }

    private void initListeners() {
        ActionListener undoListener = e -> presenter.undo();
        ActionListener redoListener = e -> presenter.redo();
        ActionListener clearListener = e -> presenter.clearAll();

        undoButton.addActionListener(undoListener);
        undoMenuItem.addActionListener(undoListener);
        redoButton.addActionListener(redoListener);
        redoMenuItem.addActionListener(redoListener);
        clearButton.addActionListener(clearListener);
        clearMenuItem.addActionListener(clearListener);
    }

    private void setupUI(String[] shapes) {
        createContentPanelWithDrawArea();
        JPanel topPanel = createTopPanel();
        JToolBar toolBar = createToolbar();
        contentPanel.add(topPanel, BorderLayout.NORTH);
        topPanel.add(toolBar, BorderLayout.CENTER);

        JMenuBar menuBar = createMenuBar(shapes);
        topPanel.add(menuBar, BorderLayout.NORTH);
        toolBar.add(new JToolBar.Separator());

        // addShapesToToolBar(shapes, toolBar);
        // addSimulationButton = addNewButtonToToolbar("Add Simulation", "add-simulation", toolBar);
        // runSimulationButton = addNewButtonToToolbar("Run Simulation", "continue", toolBar);
        // pauseSimulationButton = addNewButtonToToolbar("Pause Simulation", "pause", toolBar);
        // removeSimulationButton =
        //         addNewButtonToToolbar("Remove Simulation", "remove-simulation", toolBar);

        // toolBar.add(new JToolBar.Separator());

        undoButton = addNewButtonToToolbar("Undo", "edit-undo", toolBar);
        redoButton = addNewButtonToToolbar("Redo", "edit-redo", toolBar);
        clearButton = addNewButtonToToolbar("Clear", "edit-clear", toolBar);
    }

    private void createContentPanelWithDrawArea() {
        drawArea = new SwingGraphicView(transformer);
        drawArea.setBackground(new Color(-1));
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 0));
        contentPanel.setMinimumSize(new Dimension(1600, 1024));
        contentPanel.setPreferredSize(new Dimension(1600, 1024));
        contentPanel.setRequestFocusEnabled(true);
        contentPanel.add(drawArea, BorderLayout.CENTER);
        add(contentPanel);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(0, 0));
        return topPanel;
    }

    private JToolBar createToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setOrientation(0);
        toolBar.putClientProperty("JToolBar.isRollover", Boolean.TRUE);
        return toolBar;
    }

    private JMenuBar createMenuBar(String[] shapes) {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem close = new JMenuItem("Close");
        close.addActionListener(e -> System.exit(0));
        fileMenu.add(close);
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        undoMenuItem = addNewMenuItem(editMenu, "Undo", "edit-undo");
        redoMenuItem = addNewMenuItem(editMenu, "Redo", "edit-redo");
        clearMenuItem = addNewMenuItem(editMenu, "Clear", "edit-clear");
        menuBar.add(editMenu);

        JMenu shapesMenu = new JMenu("Shapes");
        menuBar.add(shapesMenu);
        addShapesToMenuBar(shapes, shapesMenu);

        JMenu simulationMenu = new JMenu("Simulation");
        // addSimulationMenuItem = addNewMenuItem(simulationMenu, "Add Simulation", "add-simulation");
        // runSimulationMenuItem = addNewMenuItem(simulationMenu, "Run Simulation", "continue");
        // pauseSimulationMenuItem = addNewMenuItem(simulationMenu, "Pause Simulation", "pause");
        // removeSimulationMenuItem =
        //         addNewMenuItem(simulationMenu, "Remove Simulation", "remove-simulation");
        menuBar.add(simulationMenu);

        JMenu visualizationMenu = new JMenu("Visualization");
        // addPlotStylesToMenu(visualizationMenu);
        menuBar.add(visualizationMenu);
        return menuBar;
    }

    private void addPlotStylesToMenu(JMenu visualizationMenu) {}

    private void addShapesToToolBar(String[] shapes, JToolBar toolBar)  {}

    private void addShapesToMenuBar(String[] shapes, JMenu shapesMenu) {}

    private JMenuItem addNewMenuItem(JMenu menu, String text, String imageName) {
        var item = new JMenuItem(text);
        item.setIcon(getScaledImageIcon(imageName));
        menu.add(item);
        return item;
    }

    private JButton addNewButtonToToolbar(String text, String iconName, JToolBar toolBar) {
        var button = new JButton(text);
        button.setIcon(getScaledImageIcon(iconName));
        toolBar.add(button);
        return button;
    }

    private ImageIcon getScaledImageIcon(String imageName) {
        return new ImageIcon(getImage(imageName).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    }

    private Image getImage(String imageName) {
        var clazz = getClass();
        URL resource = clazz.getResource("/" + imageName + ".gif");
        if (resource == null) resource = clazz.getResource("/" + imageName + ".png");
        return new ImageIcon(resource).getImage();
    }
}
