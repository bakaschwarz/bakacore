package de.yabue.bakacore.Command.CommandExceptions;

/**
 * Wird in der Regel geworfen, wenn versucht wird, ein Kommando
 * rückgängig zu machen, welches nicht rückgängig gemacht werden darf.
 */
public class IllegalUndoException extends Exception {
    public IllegalUndoException(String message){
        super(message);
    }
}
