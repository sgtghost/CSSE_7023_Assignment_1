package towersim.tasks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TaskTypeTest {
    private TaskType taskType;
    @Before
    public void setup() {
        this.taskType = TaskType.LOAD;
    }
    @Test
    public void getDescriptionTest() {
        Assert.assertEquals("Loading at gate",this.taskType.getDescription());
    }
}
