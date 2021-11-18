package towersim.tasks;

// add any required imports here

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TaskListTest {

    // add unit tests here
    private TaskList taskList;
    private List<Task> tasks;
    @Before
    public void setup() {
        this.tasks = new ArrayList<>();

        Task wait = new Task(TaskType.WAIT);
        Task load = new Task(TaskType.LOAD,30);
        Task takeoff = new Task(TaskType.TAKEOFF);
        Task away = new Task(TaskType.AWAY);
        Task land = new Task(TaskType.LAND);

        tasks.add(wait);
        tasks.add(load);
        tasks.add(takeoff);
        tasks.add(away);
        tasks.add(land);


        taskList = new TaskList(tasks);
    }

    @Test
    public void getCurrentTaskTest() {
        Assert.assertEquals(TaskType.WAIT,taskList.getCurrentTask().getType());
    }

    @Test
    public void getNextTaskTest() {
        Assert.assertEquals(this.tasks.get(1),taskList.getNextTask());
        Assert.assertEquals(this.tasks.get(0),taskList.getCurrentTask());
        taskList.moveToNextTask();
        Assert.assertEquals(this.tasks.get(2),taskList.getNextTask());
        Assert.assertEquals(this.tasks.get(1),taskList.getCurrentTask());
        taskList.moveToNextTask();
        Assert.assertEquals(this.tasks.get(3),taskList.getNextTask());
        Assert.assertEquals(this.tasks.get(2),taskList.getCurrentTask());
        taskList.moveToNextTask();
        Assert.assertEquals(this.tasks.get(4),taskList.getNextTask());
        Assert.assertEquals(this.tasks.get(3),taskList.getCurrentTask());
        taskList.moveToNextTask();
        Assert.assertEquals(this.tasks.get(0),taskList.getNextTask());
        Assert.assertEquals(this.tasks.get(4),taskList.getCurrentTask());
        taskList.moveToNextTask();
        Assert.assertEquals(this.tasks.get(1),taskList.getNextTask());
        Assert.assertEquals(this.tasks.get(0),taskList.getCurrentTask());
    }

    @Test
    public void moveToNextTaskTest() {
        Assert.assertEquals(this.tasks.get(0),taskList.getCurrentTask());
        taskList.moveToNextTask();
        Assert.assertEquals(this.tasks.get(1),taskList.getCurrentTask());
        taskList.moveToNextTask();
        Assert.assertEquals(this.tasks.get(2),taskList.getCurrentTask());
        taskList.moveToNextTask();
        Assert.assertEquals(this.tasks.get(3),taskList.getCurrentTask());
        taskList.moveToNextTask();
        Assert.assertEquals(this.tasks.get(4),taskList.getCurrentTask());
        taskList.moveToNextTask();
        Assert.assertEquals(this.tasks.get(0),taskList.getCurrentTask());
    }

    @Test
    public void toStringTest() {
        Assert.assertEquals("TaskList currently on WAIT [1/5]",taskList.toString());
        taskList.moveToNextTask();
        Assert.assertEquals("TaskList currently on LOAD at 30% [2/5]",taskList.toString());
        taskList.moveToNextTask();
        Assert.assertEquals("TaskList currently on TAKEOFF [3/5]",taskList.toString());
        taskList.moveToNextTask();
        Assert.assertEquals("TaskList currently on AWAY [4/5]",taskList.toString());
        taskList.moveToNextTask();
        Assert.assertEquals("TaskList currently on LAND [5/5]",taskList.toString());
        taskList.moveToNextTask();
        Assert.assertEquals("TaskList currently on WAIT [1/5]",taskList.toString());

    }

}
