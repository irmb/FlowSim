# FlowSim

The FlowSim application allows its user to draw shapes and map them onto an interactive fluid dynamics simulation.

FlowSim was developed as a Bachelor thesis to study test driven development.




## Exercise 1
The first exercise is based on fourth lecture about linear transformation. 
In this exercise we will implement the transformation of simple geometrical shapes. So far we can instantiate shapes in the main method (view/swing/Main.java) like the 2DLine. Notice that this object is put into the shapeList of type PaintableShape. However, while running the application we can only see parts of the line in the top left corner. Our task is now to implement the following class:

    model/util/CoordinateTransformerImpl.java

### Expected behavior:
the line is then visible in the middle of the window.



## Exercise 2
This exercise is based on the fifth lecture about the command pattern.
Compared with the last exercise the following classes are added to the project:


    presentation/command/AddPaintableShapeCommand.java
    presentation/command/ClearAllCommand.java
    presentation/command/Command.java
    presentation/command/MoveShapeCommand.java
    presentation/command/PanWindowCommand.java
    presentation/command/RemovePaintableCommand.java
    presentation/command/ZoomCommand.java

    presentation/CommandStack.java
    presentation/CommandStackImpl.java

    util/Observer.java
    util/ObserverImpl.java
    util/Observable.java


The task is to implement the behavior of the commands and the command stack.
At the bottom of the main method (view/swing/Main.java) several commands were instantiated. Between the execution of the commands is a 2 second delay. 

### Expected behavior:
The commands are executed correctly. After the execution we can undo and redo the commands.

## Exercise 3
This exercise is based on the sixth lecture about the factory and builder pattern.
Compared to the last exercise, we now have the two new folders filled with class stubs.

    presentation/builder/
    presentation/factory/

The task is implement the factory and builder classes. These classes were instantiated in the main.
### Expected behavior:
A Line and a Polyline is painted and visible in the main window.

## Exercise 4
This exercise is based on the seventh lecture about the strategy design pattern.
Compared to the last exercise, we now have the following folder filled with class stubs.

    presentation/strategy/

The task is implement BuildObjectMouseStrategy and MoveMouseStrategy.
### Expected behavior:
Shapes can be painted via the menu or the toolbar. Additionally, the shapes can be moved via the mouse, the window can be panned and zoomed. A right click should remove the shape.

## Exercuse 5
This exercise is based on the eighth lecture about the bezier interpolation method.
Compared to the last exercise, we now have the following folder filled with class stubs.

    model/BezierCurve.java
    view/graphics/PaintableBezierCurve.java

### Expected behavior:
Bezier Curve can be painted.
