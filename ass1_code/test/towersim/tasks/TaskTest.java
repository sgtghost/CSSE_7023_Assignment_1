package towersim.tasks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TaskTest {
    private Task nonLoadTask;
    private Task loadTask;
    @Before
    public void setup() {
        this.nonLoadTask = new Task(TaskType.WAIT);
        this.loadTask = new Task(TaskType.LOAD, 30);
    }
    @Test
    public void getTypeTest() {
        Assert.assertEquals(TaskType.WAIT, this.nonLoadTask.getType());
        Assert.assertEquals(TaskType.LOAD, this.loadTask.getType());
    }
    @Test
    public void getLoadPercentTest() {
        Assert.assertEquals(30, this.loadTask.getLoadPercent());
    }
    @Test
    public void toStringTest() {
        Assert.assertEquals("LOAD at 30%", this.loadTask.toString());
        Assert.assertEquals("WAIT", this.nonLoadTask.toString());
    }
}
