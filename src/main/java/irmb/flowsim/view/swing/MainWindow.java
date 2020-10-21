package irmb.flowsim.view.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.GraphicView;
import irmb.flowsim.presentation.SimulationGraphicViewPresenter;
import irmb.flowsim.simulation.visualization.PlotStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Sven on 15.12.2016.
 */
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
    private JPanel topPanel;
    private JToolBar mainToolBar;

    private JMenuItem undo;
    private JMenuItem redo;
    private JMenuItem clear;

    private SimulationGraphicViewPresenter presenter;
    private CoordinateTransformer transformer;

    public MainWindow(CoordinateTransformer transformer, String[] shapes) {
        this.transformer = transformer;
        $$$setupUI$$$();
        setupUI(shapes);
        setTitle("FlowSim 2.0");
        setSize(1250, 800);
        add(contentPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initListeners();
        scaleButtonIcons();
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem close = new JMenuItem("Close");
        close.addActionListener(e -> System.exit(1));
        fileMenu.add(close);
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");
        clear = new JMenuItem("Clear");
        editMenu.add(undo);
        editMenu.add(redo);
        editMenu.add(clear);
        menuBar.add(editMenu);

        JMenu visualizationMenu = new JMenu("Visualization");
        for (PlotStyle style : PlotStyle.values()) {
            JCheckBoxMenuItem item = new JCheckBoxMenuItem(style.toString());
            item.addActionListener(e -> presenter.togglePlotStyle(style));
            visualizationMenu.add(item);
        }
        menuBar.add(visualizationMenu);
        return menuBar;
    }

    private void scaleButtonIcons() {
        ImageIcon runIcon = (ImageIcon) runSimulationButton.getIcon();
        runSimulationButton.setIcon(new ImageIcon(runIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon addIcon = (ImageIcon) addSimulationButton.getIcon();
        addSimulationButton.setIcon(new ImageIcon(addIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon pauseIcon = (ImageIcon) pauseSimulationButton.getIcon();
        pauseSimulationButton
                .setIcon(new ImageIcon(pauseIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon removeIcon = (ImageIcon) removeSimulationButton.getIcon();
        removeSimulationButton
                .setIcon(new ImageIcon(removeIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon undoIcon = (ImageIcon) undoButton.getIcon();
        undoButton.setIcon(new ImageIcon(undoIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon redoIcon = (ImageIcon) redoButton.getIcon();
        redoButton.setIcon(new ImageIcon(redoIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon clearIcon = (ImageIcon) clearButton.getIcon();
        clearButton.setIcon(new ImageIcon(clearIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
    }

    private void initListeners() {
        ActionListener addSimulationListener = e -> presenter.addSimulation();
        ActionListener runSimulationListener = e -> presenter.runSimulation();
        ActionListener pauseSimulationListener = e -> presenter.pauseSimulation();
        ActionListener removeSimulationListener = e -> presenter.removeSimulation();
        ActionListener undoListener = e -> presenter.undo();
        ActionListener redoListener = e -> presenter.redo();
        ActionListener clearListener = e -> presenter.clearAll();

        addSimulationButton.addActionListener(addSimulationListener);
        runSimulationButton.addActionListener(runSimulationListener);
        pauseSimulationButton.addActionListener(pauseSimulationListener);
        removeSimulationButton.addActionListener(removeSimulationListener);

        undoButton.addActionListener(undoListener);
        undo.addActionListener(undoListener);
        redoButton.addActionListener(redoListener);
        redo.addActionListener(redoListener);
        clearButton.addActionListener(clearListener);
        clear.addActionListener(clearListener);
    }

    private void createUIComponents() {
        drawArea = new SwingGraphicView(transformer);
    }

    public GraphicView getGraphicView() {
        return (GraphicView) drawArea;
    }

    public void setPresenter(SimulationGraphicViewPresenter presenter) {
        this.presenter = presenter;
        ((SwingGraphicView) drawArea).setPresenter(presenter);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT
     * edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void setupUI(String[] shapes) {
        createUIComponents();
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 0));
        contentPanel.setMinimumSize(new Dimension(1600, 1024));
        contentPanel.setPreferredSize(new Dimension(1600, 1024));
        contentPanel.setRequestFocusEnabled(true);
        drawArea.setBackground(new Color(-1));
        contentPanel.add(drawArea, BorderLayout.CENTER);
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(0, 0));
        contentPanel.add(topPanel, BorderLayout.NORTH);
        mainToolBar = new JToolBar();
        mainToolBar.setOrientation(0);
        mainToolBar.putClientProperty("JToolBar.isRollover", Boolean.TRUE);
        topPanel.add(mainToolBar, BorderLayout.CENTER);

        JMenuBar menuBar = createMenuBar();
        topPanel.add(menuBar, BorderLayout.NORTH);

        JMenu shapesMenu = new JMenu("Shapes");
        menuBar.add(shapesMenu);

        for (String shape : shapes) {
            JButton button = new JButton();
//            button.setIcon(new ImageIcon(getClass().getResource("/" + shape + ".gif")));
            button.setText(shape);
            ActionListener listener = e -> presenter.beginPaint(shape);
            button.addActionListener(listener);
            mainToolBar.add(button);

//            ImageIcon buttonIcon = (ImageIcon) button.getIcon();
//            buttonIcon = new ImageIcon(buttonIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
//            button.setIcon(buttonIcon);

            JMenuItem item = new JMenuItem(shape);
//            item.setIcon(buttonIcon);
            item.addActionListener(listener);
            shapesMenu.add(item);
        }

        addSimulationButton = new JButton();
        addSimulationButton.setIcon(new ImageIcon(getClass().getResource("/add-simulation.png")));
        addSimulationButton.setText("Add Simulation");
        mainToolBar.add(addSimulationButton);
        final JToolBar.Separator toolBar$Separator1 = new JToolBar.Separator();
        mainToolBar.add(toolBar$Separator1);
        runSimulationButton = new JButton();
        runSimulationButton.setIcon(new ImageIcon(getClass().getResource("/continue.png")));
        runSimulationButton.setText("Run Simulation");
        mainToolBar.add(runSimulationButton);
        pauseSimulationButton = new JButton();
        pauseSimulationButton.setIcon(new ImageIcon(getClass().getResource("/pause.png")));
        pauseSimulationButton.setText("Pause Simulation");
        mainToolBar.add(pauseSimulationButton);
        removeSimulationButton = new JButton();
        removeSimulationButton.setIcon(new ImageIcon(getClass().getResource("/remove-simulation.png")));
        removeSimulationButton.setText("Remove Simulation");
        mainToolBar.add(removeSimulationButton);
        undoButton = new JButton();
        undoButton.setIcon(new ImageIcon(getClass().getResource("/edit-undo.png")));
        undoButton.setText("Undo");
        mainToolBar.add(undoButton);
        redoButton = new JButton();
        redoButton.setIcon(new ImageIcon(getClass().getResource("/edit-redo.png")));
        redoButton.setText("Redo");
        mainToolBar.add(redoButton);
        clearButton = new JButton();
        clearButton.setIcon(new ImageIcon(getClass().getResource("/edit-clear.png")));
        clearButton.setText("Clear");
        mainToolBar.add(clearButton);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 0));
        contentPanel.setMinimumSize(new Dimension(1600, 1024));
        contentPanel.setPreferredSize(new Dimension(1600, 1024));
        contentPanel.setRequestFocusEnabled(true);
        drawArea.setBackground(new Color(-1));
        contentPanel.add(drawArea, BorderLayout.CENTER);
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(0, 0));
        contentPanel.add(topPanel, BorderLayout.NORTH);
        mainToolBar = new JToolBar();
        mainToolBar.setOrientation(0);
        mainToolBar.putClientProperty("JToolBar.isRollover", Boolean.TRUE);
        topPanel.add(mainToolBar, BorderLayout.CENTER);
        addSimulationButton = new JButton();
        addSimulationButton.setIcon(new ImageIcon(getClass().getResource("/add-simulation.png")));
        addSimulationButton.setText("Add Simulation");
        mainToolBar.add(addSimulationButton);
        final JToolBar.Separator toolBar$Separator1 = new JToolBar.Separator();
        mainToolBar.add(toolBar$Separator1);
        runSimulationButton = new JButton();
        runSimulationButton.setIcon(new ImageIcon(getClass().getResource("/continue.png")));
        runSimulationButton.setText("Run Simulation");
        mainToolBar.add(runSimulationButton);
        pauseSimulationButton = new JButton();
        pauseSimulationButton.setIcon(new ImageIcon(getClass().getResource("/pause.png")));
        pauseSimulationButton.setText("Pause Simulation");
        mainToolBar.add(pauseSimulationButton);
        removeSimulationButton = new JButton();
        removeSimulationButton.setIcon(new ImageIcon(getClass().getResource("/remove-simulation.png")));
        removeSimulationButton.setText("Remove Simulation");
        mainToolBar.add(removeSimulationButton);
        undoButton = new JButton();
        undoButton.setIcon(new ImageIcon(getClass().getResource("/edit-undo.png")));
        undoButton.setText("Undo");
        mainToolBar.add(undoButton);
        redoButton = new JButton();
        redoButton.setIcon(new ImageIcon(getClass().getResource("/edit-redo.png")));
        redoButton.setText("Redo");
        mainToolBar.add(redoButton);
        clearButton = new JButton();
        clearButton.setIcon(new ImageIcon(getClass().getResource("/edit-clear.png")));
        clearButton.setText("Clear");
        mainToolBar.add(clearButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

}
