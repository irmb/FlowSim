package irmb.flowsim.presentation;

import irmb.flowsim.presentation.command.Command;
import irmb.flowsim.util.Observable;

public interface CommandStack extends Observable<String> {
    void add(Command command);

    void undo();

    void redo();
}
