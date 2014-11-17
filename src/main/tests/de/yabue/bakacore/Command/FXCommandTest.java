package de.yabue.bakacore.Command;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FXCommandTest {

    public static int value = 4;

    private TestCommand testCommand;

    @BeforeTest
    public void init(){
        testCommand = new TestCommand();
    }

    @Test
    public void testCorrectUse() throws Exception {
        testCommand.execute();
        Assert.assertEquals(value, 10);
        testCommand.undo();
        Assert.assertEquals(value, 4);
        testCommand.redo();
        Assert.assertEquals(value, 10);
    }
}

class TestCommand extends FXCommand{

    public TestCommand(){
        super();
    }

    @Override
    public void executeAction() {
        FXCommandTest.value += 6;
    }

    @Override
    public void undoAction() {
        FXCommandTest.value = FXCommandTest.value - 6;
    }

    @Override
    public void redoAction() {
        executeAction();
    }
}