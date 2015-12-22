package de.yabue.bakacore.IO;

import org.testng.annotations.Test;
import java.util.ArrayList;

import static org.testng.Assert.*;

public class SimpleSystemCallTest {

    @Test
    public void testCall() throws Exception {
        ArrayList<String> output = new ArrayList<>();
        int result = SimpleSystemCall.call("echo Hallo Welt", output);
        assertEquals(result, 0);
        assertEquals("[Hallo Welt]", output.toString());
    }

    @Test
    public void testCall1() throws Exception {
        int result = SimpleSystemCall.call("echo Hallo Welt");
        assertEquals(result, 0);
    }
}