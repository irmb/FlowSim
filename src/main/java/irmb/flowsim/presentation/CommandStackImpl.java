package irmb.flowsim.presentation;

import irmb.flowsim.presentation.command.Command;
import irmb.flowsim.util.ObservableImpl;

import java.util.Stack;

public class CommandStackImpl extends ObservableImpl<String> implements CommandStack {

    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();

    public void add(Command command) {
        //TODO
    }

    public void undo() {
        //TODO
    }

    public void redo() {
        //TODO
    }
}
