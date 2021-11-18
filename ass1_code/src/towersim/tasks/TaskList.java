package towersim.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a circular list of tasks for an aircraft to cycle through.
 */
public class TaskList {
    //Fields

    /**
     * tasks - list of tasks
     */
    private List<Task> tasks = new ArrayList<>();

    /**
     * currentTask - current task
     */
    private Task currentTask;

    //Constructor

    /**
     * Creates a new TaskList with the given list of tasks.
     * Initially, the current task (as returned by getCurrentTask())
     * should be the first task in the given list.
     * @param tasks - list of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
        this.currentTask = tasks.get(0);
    }

    /**
     * Returns the current task in the list.
     * @return current task
     */
    public Task getCurrentTask() {
        return this.currentTask;
    }

    /**
     * Returns the task in the list that comes after the current task.
     * After calling this method,
     * the current task should still be the same as it was before calling the method.
     *
     * Note that the list is treated as circular,
     * so if the current task is the last in the list,
     * this method should return the first element of the list.
     * @return next task
     */
    public Task getNextTask() {
        Task nextTask;
        int currentIndex = this.tasks.indexOf(this.getCurrentTask());
        if (currentIndex + 1 == this.tasks.size()) {
            nextTask = this.tasks.get(0);
        } else {
            nextTask = this.tasks.get(currentIndex + 1);
        }
        return nextTask;
    }

    /**
     * Moves the reference to the current task forward by one in the circular task list.
     * After calling this method,
     * the current task should be the next task in the circular list after the "old" current task.
     *
     * Note that the list is treated as circular,
     * so if the current task is the last in the list,
     * the new current task should be the first element of the list.
     */
    public void moveToNextTask() {
        this.currentTask = this.getNextTask();
    }

    /**
     * Returns the human-readable string representation of this task list.
     * The format of the string to return is
     *
     * TaskList currently on currentTask [taskNum/totalNumTasks]
     *
     * where currentTask is the toString() representation of the current task
     * as returned by Task.toString(),
     * taskNum is the place the current task occurs in the task list,
     * and totalNumTasks is the number of tasks in the task list.
     *
     * For example, a task list with the list of tasks [AWAY, LAND, WAIT, LOAD, TAKEOFF]
     * which is currently on the WAIT task
     * would have a string representation of "TaskList currently on WAIT [3/5]".
     * @return string representation of this task list
     */
    @Override
    public String toString() {
        String taskString = this.getCurrentTask().toString();
        return "TaskList currently on " + taskString + " "
                + "[" + (1 + this.tasks.indexOf(this.getCurrentTask()))
                + "/" + this.tasks.size() + "]";
    }

}
