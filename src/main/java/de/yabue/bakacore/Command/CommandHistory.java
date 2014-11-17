package de.yabue.bakacore.Command;

import lombok.Getter;

/**
 * Implementiert eine Kommando Historie, die eine Vielzahl von Kommandos verwalten kann.
 * Sie dient dazu, dem Programmierer die Aufgabe abzunehmen, die Kommandos selbst verwalten zu müssen.
 */
public class CommandHistory {

    @Getter
    private final int size;

    private final FXCommand[] commands;

    private int position;

    public CommandHistory(final int size){
        this.size = size;
        commands = new FXCommand[size];
        position = 0;
    }

    /**
     * Führt ein übergebenes Kommando aus und fügt es der Kommano Historie hinzu, wenn es
     * rückgängig gemacht werden kann.
     * @param command Das Kommando, das ausgeführt werden soll.
     */
    public void execute(final FXCommand command){
        if(command.isUndoable()){

        }
        command.execute();
    }

}
