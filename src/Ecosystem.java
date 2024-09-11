import java.util.Random;
import java.util.Scanner;
class Ecosystem {
    private Organism[][] grid;
    private int height;
    private int width;

    public Ecosystem(int height, int width) {
        this.height = height;
        this.width = width;
        grid = new Organism[height][width];
    }

    public void populate(int numAnts, int numDoodlebugs) {
        Random rand = new Random();
        int count = 0;
        while (count < numAnts) {
            int x = rand.nextInt(height);
            int y = rand.nextInt(width);
            if (grid[x][y] == null) {
                grid[x][y] = new Ant(x, y, this);
                count++;
            }
        }
        count = 0;
        while (count < numDoodlebugs) {
            int x = rand.nextInt(height);
            int y = rand.nextInt(width);
            if (grid[x][y] == null) {
                grid[x][y] = new Doodlebug(x, y, this);
                count++;
            }
        }
    }

    public void runSimulation() {
        Scanner scanner = new Scanner(System.in);
        boolean continueSimulation = true;

        while (continueSimulation) {
            printGrid();
            System.out.println("Do you want to continue simulation? (Y/N)");
            String input = scanner.nextLine().toUpperCase();

            if (input.equals("N")) {
                continueSimulation = false;
            } else if (input.equals("Y")) {
                step();
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }
    }

    private void step() {
        boolean antsExist = false;
        boolean doodlebugsExist = false;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Organism organism = grid[i][j];
                if (organism != null && !organism.hasMoved()) {
                    organism.move();
                    if (organism instanceof Ant) {
                        antsExist = true;
                    } else if (organism instanceof Doodlebug) {
                        doodlebugsExist = true;
                    }
                }
            }
        }

        // Reset hasMoved flag after each step
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Organism organism = grid[i][j];
                if (organism != null) {
                    organism.setMoved(false);
                }
            }
        }

        if (!antsExist || !doodlebugsExist) {
            if (!antsExist) {
                System.out.println("The End: All grids are occupied by Ants.");
            } else {
                System.out.println("The End: All grids are occupied by Doodlebugs.");
            }
            System.exit(0); // Exit the program
        }
    }

    public void printGrid() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] != null) {
                    if (grid[i][j] instanceof Ant) {
                        System.out.print("O ");
                    } else if (grid[i][j] instanceof Doodlebug) {
                        System.out.print("X ");
                    }
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean antsExist() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] instanceof Ant) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean doodlebugsExist() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] instanceof Doodlebug) {
                    return true;
                }
            }
        }
        return false;
    }
}
