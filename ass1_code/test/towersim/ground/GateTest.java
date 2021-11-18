package towersim.ground;

// add any required imports here

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import towersim.aircraft.Aircraft;
import towersim.aircraft.AircraftCharacteristics;
import towersim.aircraft.FreightAircraft;
import towersim.aircraft.PassengerAircraft;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.NoSpaceException;

import java.util.ArrayList;
import java.util.List;


public class GateTest {
    private Gate gate;
    private Aircraft aircraft1;
    private Aircraft aircraft2;

    @Before
    public void setup() {
        gate = new Gate(1);

        List<Task> tasks = new ArrayList<>();

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

        aircraft1 = new FreightAircraft("Yeet", AircraftCharacteristics.BOEING_747_8F, new TaskList(tasks),0,0);
        aircraft2 = new PassengerAircraft("POG", AircraftCharacteristics.BOEING_787, new TaskList(tasks),0,0);
    }

    @Test
    public void getGateNumberTest() {
        Assert.assertEquals(1,this.gate.getGateNumber());
    }

    @Test
    public void ParkAircraftTest() {
        Assert.assertFalse(gate.isOccupied());
        Assert.assertNull(gate.getAircraftAtGate());
        try {
            gate.parkAircraft(aircraft1);
            Assert.assertTrue(gate.isOccupied());
            Assert.assertEquals(aircraft1, gate.getAircraftAtGate());
        } catch (NoSpaceException e) {
            e.printStackTrace();
        }
        try {
            gate.parkAircraft(aircraft2);
        } catch (NoSpaceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void aircraftLeavesTest() {
        try {
            gate.parkAircraft(aircraft1);
            Assert.assertTrue(gate.isOccupied());
            Assert.assertEquals(aircraft1, gate.getAircraftAtGate());
        } catch (NoSpaceException e) {
            e.printStackTrace();
        }
        gate.aircraftLeaves();
        Assert.assertFalse(gate.isOccupied());
        Assert.assertNull(gate.getAircraftAtGate());
    }

    @Test
    public void isOccupiedTest() {
        Assert.assertFalse(gate.isOccupied());
    }

    @Test
    public void getAircraftAtGateTest() {
        Assert.assertNull(gate.getAircraftAtGate());
        try {
            gate.parkAircraft(aircraft2);
            Assert.assertEquals(aircraft2, gate.getAircraftAtGate());
        } catch (NoSpaceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void toStringTest() {
        Assert.assertEquals("Gate 1 [empty]",gate.toString());
        try {
            gate.parkAircraft(aircraft1);
            Assert.assertEquals("Gate 1 [" + aircraft1.getCallsign() + "]",gate.toString());
        } catch (NoSpaceException e) {
            e.printStackTrace();
        }
    }
}
