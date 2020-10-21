package irmb.flowsim.presentation;

import irmb.flowsim.presentation.CommandStack;
import irmb.flowsim.presentation.command.Command;
import irmb.flowsim.util.ObservableImpl;

import java.util.Stack;

public class CommandStackImpl extends ObservableImpl<String> implements CommandStack {

    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();

    public void add(Command command) {
        redoStack.clear();
        undoStack.push(command);
    }

    public void undo() {
        if (undoStack.isEmpty()) return;
        var command = undoStack.pop();
        redoStack.push(command);
        command.undo();
        notifyObservers("undo");
    }

    public void redo() {
        if (redoStack.isEmpty()) return;
        var command = redoStack.pop();
        undoStack.push(command);
        command.redo();
        notifyObservers("redo");
    }
}
