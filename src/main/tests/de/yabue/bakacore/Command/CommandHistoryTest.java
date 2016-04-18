package de.yabue.bakacore.Command;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * DESCRIPTION HERE.
 *
 * @author baka
 * @version 0.0.1
 */
public class CommandHistoryTest {

    public static CommandHistory commandHistory;

    public static int i = 0;

    @BeforeMethod
    public void setUp() throws Exception {
        commandHistory = new CommandHistory(2);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        commandHistory = null;
        i = 0;
    }

    @Test
    public void testExecute() throws Exception {
        commandHistory.execute(new TestHistoryCommand());
        commandHistory.execute(new TestHistoryCommand());
        Assert.assertEquals(i, 2);
    }

    @Test
    public void testUndo() throws Exception {
        commandHistory.execute(new TestHistoryCommand());
        commandHistory.execute(new TestHistoryCommand());
        commandHistory.undo();
        Assert.assertEquals(i, 1);
        commandHistory.execute(new TestHistoryCommand());
        Assert.assertEquals(i, 2);
        commandHistory.execute(new TestHistoryCommand());
        commandHistory.execute(new TestHistoryCommand());
        Assert.assertEquals(i, 4);
        commandHistory.undo();
        commandHistory.undo();
        Assert.assertEquals(i, 2);
    }

    @Test
    public void testRedo() throws Exception {
        commandHistory.execute(new TestHistoryCommand());
        commandHistory.execute(new TestHistoryCommand());
        commandHistory.undo();
        commandHistory.redo();
        Assert.assertEquals(i, 2);
        commandHistory.undo();
        commandHistory.undo();
        commandHistory.redo();
        Assert.assertEquals(i, 1);
    }

    @Test
    public void testUndoProperty() throws Exception {
        testRedo();
        Assert.assertTrue(commandHistory.undoProperty().getValue());
        commandHistory.undo();
        Assert.assertFalse(commandHistory.undoProperty().getValue());
    }

    @Test
    public void testRedoProperty() throws Exception {
        testRedo();
        commandHistory.redo();
        Assert.assertFalse(commandHistory.redoProperty().getValue());
        commandHistory.undo();
        Assert.assertTrue(commandHistory.redoProperty().getValue());
    }

    @Test
    public void testGetSize() throws Exception {
        Assert.assertEquals(commandHistory.getSize(), 2);
    }

}

class TestHistoryCommand extends FXCommand {

    @Override
    protected void executeAction() {
        CommandHistoryTest.i += 1;
    }

    @Override
    protected void undoAction() {
        CommandHistoryTest.i -= 1;
    }

    @Override
    protected void redoAction() {
        executeAction();
    }
}