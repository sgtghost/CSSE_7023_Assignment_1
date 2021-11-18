package towersim.aircraft;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;

import java.util.ArrayList;
import java.util.List;

public class PassengerAircraftTest {
    private List<Task> tasks;
    private PassengerAircraft emptyPassengerAircraft;
    private PassengerAircraft fullPassengerAircraft;
    private PassengerAircraft randomPassengerAircraft;
    @Before
    public void setup() {
        this.tasks = new ArrayList<>();

        Task wait = new Task(TaskType.WAIT);
        Task load = new Task(TaskType.LOAD,70);
        Task takeoff = new Task(TaskType.TAKEOFF);
        Task away = new Task(TaskType.AWAY);
        Task land = new Task(TaskType.LAND);


        tasks.add(load);
        tasks.add(wait);
        tasks.add(takeoff);
        tasks.add(away);
        tasks.add(land);
        this.emptyPassengerAircraft = new PassengerAircraft("POG", AircraftCharacteristics.BOEING_787, new TaskList(tasks),100,0);
        this.fullPassengerAircraft = new PassengerAircraft("POG", AircraftCharacteristics.BOEING_787, new TaskList(tasks),100,242);
        this.randomPassengerAircraft = new PassengerAircraft("POG", AircraftCharacteristics.BOEING_787, new TaskList(tasks),100,100);
    }
    @Test
    public void invalidPassengerNumberTest() {
        try {
            PassengerAircraft testOverSize = new PassengerAircraft("POG", AircraftCharacteristics.BOEING_787, new TaskList(tasks),100,1000);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            PassengerAircraft testNegative = new PassengerAircraft("POG", AircraftCharacteristics.BOEING_787, new TaskList(tasks),100,-1);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void invalidFuelAmountTest() {
        try {
            PassengerAircraft testOverSize = new PassengerAircraft("POG", AircraftCharacteristics.BOEING_787, new TaskList(tasks),1262060,0);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            PassengerAircraft testNegative = new PassengerAircraft("POG", AircraftCharacteristics.BOEING_787, new TaskList(tasks),-126206,0);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void getTotalWeightTest() {
        Assert.assertTrue(Math.abs(this.emptyPassengerAircraft.getTotalWeight() - 120030.0) < 0.001);
        Assert.assertTrue(Math.abs(this.fullPassengerAircraft.getTotalWeight() - 141810.0) < 0.001);
        Assert.assertTrue(Math.abs(this.randomPassengerAircraft.getTotalWeight() - 129030.0) < 0.001);
    }
    @Test
    public void getLoadingTimeTest() {
        Assert.assertEquals((int) Math.round(Math.log10(242*0.7)),this.emptyPassengerAircraft.getLoadingTime());
    }

    @Test
    public void calculateOccupancyLevelTest() {
        Assert.assertEquals(0, this.emptyPassengerAircraft.calculateOccupancyLevel());
        Assert.assertEquals(100, this.fullPassengerAircraft.calculateOccupancyLevel());
        Assert.assertEquals(41, this.randomPassengerAircraft.calculateOccupancyLevel());
    }

    @Test
    public void tickTest() {
        //tick = 2
        this.emptyPassengerAircraft.tick();
        this.fullPassengerAircraft.tick();
        Assert.assertTrue(Math.abs(this.emptyPassengerAircraft.getFuelAmount() - 63203) < 0.001);
        Assert.assertEquals(35,this.emptyPassengerAircraft.calculateOccupancyLevel());
        Assert.assertTrue(Math.abs(this.fullPassengerAircraft.getFuelAmount() - 63203) < 0.001);
        Assert.assertEquals(100,this.fullPassengerAircraft.calculateOccupancyLevel());
    }

}
