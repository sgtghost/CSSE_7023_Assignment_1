package towersim.ground;

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
import towersim.util.NoSuitableGateException;

import java.util.ArrayList;
import java.util.List;

public class TerminalTest {
    private HelicopterTerminal terminal;
    private List<Gate> gates;
    private Aircraft aircraft1;
    private Aircraft aircraft2;

    @Before
    public void setup() {
        this.terminal = new HelicopterTerminal(1);

        this.gates = new ArrayList<>();

        this.gates.add(new Gate(11));
        this.gates.add(new Gate(12));
        this.gates.add(new Gate(13));
        this.gates.add(new Gate(14));
        this.gates.add(new Gate(15));
        this.gates.add(new Gate(16));
        this.gates.add(new Gate(17));

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
    public void getTerminalNumberTest() {
        Assert.assertEquals(1,this.terminal.getTerminalNumber());
    }

    @Test
    public void addGateTest() {
        try {
            this.terminal.addGate(gates.get(0));
            this.terminal.addGate(gates.get(1));
            this.terminal.addGate(gates.get(2));
            this.terminal.addGate(gates.get(3));
            this.terminal.addGate(gates.get(4));
            this.terminal.addGate(gates.get(5));
            this.terminal.addGate(gates.get(6));
        } catch (NoSpaceException e) {
            e.printStackTrace();
        }
        try {
            this.terminal.addGate(gates.get(0));
            this.terminal.addGate(gates.get(1));
            this.terminal.addGate(gates.get(2));
            this.terminal.addGate(gates.get(3));
            this.terminal.addGate(gates.get(4));
            this.terminal.addGate(gates.get(5));
            this.terminal.addGate(gates.get(6));
            List<Gate> getGates = new ArrayList<>(this.terminal.getGates());
            List<Gate> testGates = new ArrayList<>(this.gates);
            testGates.remove(6);
            Assert.assertArrayEquals(testGates.toArray(),getGates.toArray());
        } catch (NoSpaceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findUnoccupiedGateMixedTest() {
        try {
            this.terminal.addGate(gates.get(0));
            this.terminal.addGate(gates.get(1));
            this.terminal.addGate(gates.get(2));
            this.terminal.addGate(gates.get(3));
            Gate occupied1 = gates.get(4);
            occupied1.parkAircraft(aircraft1);
            this.terminal.addGate(occupied1);
            Gate occupied2 = gates.get(5);
            occupied2.parkAircraft(aircraft2);
            this.terminal.addGate(occupied2);
            List<Gate> testGates = new ArrayList<>(this.gates);
            testGates.remove(6);
            List<Gate> getGates = new ArrayList<>(this.terminal.getGates());
            Assert.assertArrayEquals(testGates.toArray(),getGates.toArray());
            Assert.assertEquals(gates.get(0),this.terminal.findUnoccupiedGate());
        } catch (NoSpaceException | NoSuitableGateException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void findUnoccupiedGateOccupiedOnlyTest() {
        try {
            Gate occupied1 = gates.get(0);
            occupied1.parkAircraft(aircraft1);
            this.terminal.addGate(occupied1);
            Gate occupied2 = gates.get(1);
            occupied2.parkAircraft(aircraft2);
            this.terminal.addGate(occupied2);
            Gate unoccupied = this.terminal.findUnoccupiedGate();
        } catch (NoSpaceException | NoSuitableGateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void emergencyTest(){
        Assert.assertFalse(this.terminal.hasEmergency());
        this.terminal.declareEmergency();
        Assert.assertTrue(this.terminal.hasEmergency());
        this.terminal.clearEmergency();
        Assert.assertFalse(this.terminal.hasEmergency());
    }

    @Test
    public void calculateOccupancyLevelTest() {
        try {
            this.terminal.addGate(gates.get(0));
            this.terminal.addGate(gates.get(1));
            Gate occupied1 = gates.get(2);
            occupied1.parkAircraft(aircraft1);
            this.terminal.addGate(occupied1);
            Gate occupied2 = gates.get(3);
            occupied2.parkAircraft(aircraft2);
            this.terminal.addGate(occupied2);
            List<Gate> testGates = new ArrayList<>(this.gates);
            testGates.remove(6);
            testGates.remove(5);
            testGates.remove(4);
            List<Gate> getGates = new ArrayList<>(this.terminal.getGates());
            Assert.assertArrayEquals(testGates.toArray(),getGates.toArray());
            Assert.assertEquals(gates.get(0),this.terminal.findUnoccupiedGate());
            float occupancyLevel = this.terminal.calculateOccupancyLevel();
            System.out.println(occupancyLevel);
            Assert.assertTrue(Math.abs(occupancyLevel - 50) < 0.001);
        } catch (NoSpaceException | NoSuitableGateException e) {
            e.printStackTrace();
        }
    }

}
