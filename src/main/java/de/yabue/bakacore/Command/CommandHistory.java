package de.yabue.bakacore.Command;

import de.yabue.bakacore.Command.CommandExceptions.IllegalRedoException;
import de.yabue.bakacore.Command.CommandExceptions.IllegalUndoException;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;

/**
 * Implementiert eine Kommando Historie, die eine Vielzahl von Kommandos verwalten kann.
 * Sie dient dazu, dem Programmierer die Aufgabe abzunehmen, die Kommandos selbst verwalten zu müssen.
 */
public class CommandHistory {

    @Getter
    private final int size;

    private FXCommand[] commands;

    private int position;

    private final SimpleBooleanProperty canUndo;

    private final SimpleBooleanProperty canRedo;


    public CommandHistory(final int size){
        this.size = size;
        commands = new FXCommand[size];
        position = 0;
        canUndo = new SimpleBooleanProperty(false);
        canRedo = new SimpleBooleanProperty(false);
    }

    /**
     * Führt ein übergebenes Kommando aus und fügt es der Kommando Historie hinzu, wenn es
     * rückgängig gemacht werden kann.
     * @param command Das Kommando, das ausgeführt werden soll.
     */
    public void execute(final FXCommand command){
        if(command.isUndoable()){
            if(position + 1 == size){
                FXCommand[] temp = new FXCommand[size];
                for(int i = 0; i < size - 1;i++){
                    temp[i] = commands[(size - 1) - i];
                }
                commands = temp;
            }
            commands[position] = command;
            position += position + 1 == size ? 1 : 0;
            canUndo.setValue(true);
        }
        command.execute();
    }

    /**
     * Macht das letzte Kommando, welches ausgeführt wurde, rückgängig.
     * Wenn das Kommando unlegitim in der Historie verweilt, wird es
     * ohne weiteres entfernt und der trotzdem Index um 1 verschoben.
     */
    public void undo(){
        try {
            commands[position].undo();
            if(!commands[position].isRedoable()){
                commands[position] = null;
            }else{
                canRedo.setValue(true);
            }
        } catch (IllegalUndoException e) {
            e.printStackTrace();
            commands[position] = null;
        }
        position = position - 1 >= 0 ? position - 1 : 0;
        if(position == 0){
            canUndo.setValue(false);
        }
    }

    /**
     * Führt das zuletzt rückgängig gemachte Kommando wieder aus.
     * Vorausgesetzt, es existiert ein solches Kommando. Wenn das Kommando unlegitim
     * in der Historie verweilt, wird es
     * ohne weiteres entfernt und der trotzdem Index um 1 verschoben.
     */
    public void redo(){
        if(canRedo.getValue()){
            position++;
            try {
                commands[position].redo();
                if(!commands[position].isRedoable()){
                    commands[position] = null;
                    position--;
                }
            } catch (IllegalRedoException e) {
                e.printStackTrace();
                commands[position] = null;
                position--;
            }
            if(!(position + 1 == size)){
                if(commands[position + 1] != null && commands[position + 1].isRedoable()){
                    canRedo.setValue(true);
                }else{
                    canRedo.setValue(false);
                }
            }
        }
    }

    /**
     * Erlaubt es anderen Objekten zu wissen, ob Aktionen rückgängig gemacht werden können, oder nicht.
     * @return {@code true}, wenn etwas rückgängig gemacht werden kann.
     */
    public ReadOnlyBooleanProperty undoProperty(){
        return ReadOnlyBooleanProperty.readOnlyBooleanProperty(canUndo);
    }

    /**
     * Erlaubt es anderen Objekten zu wissen, ob Aktionen wiederholt werden können, oder nicht.
     * @return {@code true}, wenn etwas wiederholt werden kann.
     */
    public ReadOnlyBooleanProperty redoProperty(){
        return ReadOnlyBooleanProperty.readOnlyBooleanProperty(canRedo);
    }

}
