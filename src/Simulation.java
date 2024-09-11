public class Simulation {
    public static void main(String[] args) {
        Ecosystem ecosystem = new Ecosystem(4, 4);
        ecosystem.populate(8, 1); // 100 ants and 5 doodlebugs
        while (!ecosystem.isSimulationEnd()) {
            ecosystem.runSimulation();
        }
    }
}
