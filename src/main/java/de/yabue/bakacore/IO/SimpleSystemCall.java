package de.yabue.bakacore.IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Diese Klasse ermöglicht das Ausführen von einfachen Systemaufrufen in einer UNIX Umgebung.
 * Einfach nur die Statische Methode ausführen und das Ergebnis als String erhalten. Simple as that.
 */
public class SimpleSystemCall {

    private SimpleSystemCall() {}

    /**
     * Ermöglicht einen einfachen Systemaufruf.
     *
     * @param call      Das Kommando, welches ausgeführt werden soll.
     * @param output    In diesen String wird der Output/Fehler des Calls geschrieben.
     * @return          Der Wert, mit dem der Calls beendet wurde. 0 bedeutet, dass alles gut verlaufen ist.
     * @exception       IOException
     */
    public static int call(final String call, ArrayList<String> output) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(call);
        BufferedReader normal = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader error  = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        int returnValue = process.waitFor();
        if(output != null) {
            if (returnValue == 0) {
                normal.lines().forEach(output::add);
            } else {
                error.lines().forEach(output::add);
            }
        }
        return returnValue;
    }

    /**
     * Ermöglicht einen einfachen Systemaufruf. (Ignorieren der Rückgabe)
     *
     * @param call      Das Kommando, welches ausgeführt werden soll.
     * @return          Der Wert, mit dem der Calls beendet wurde. 0 bedeutet, dass alles gut verlaufen ist.
     * @exception       IOException
     */
    public static int call(final String call) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(call);
        return process.waitFor();
    }
}
