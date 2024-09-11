class Doodlebug extends Organism {
    private int starveTime;

    public Doodlebug(int x, int y, Ecosystem ecosystem) {
        super(x, y, ecosystem);
        this.starveTime = 0;
    }

    public void breed() {
        int[] directions = {0, 1, 2, 3}; // 0: up, 1: right, 2: down, 3: left
        Utils.shuffleArray(directions); // Randomize direction

        for (int dir : directions) {
            int newX = x, newY = y;

            if (dir == 0) {
                newX--;
            } else if (dir == 1) {
                newY++;
            } else if (dir == 2) {
                newX++;
            } else {
                newY--;
            }

            if (isValidPosition(newX, newY)) {
                ecosystem.populateDoodlebug(newX, newY);
                return;
            }
        }
    }

    public void act() {
        if (isReadyToBreed(8)) {
            breed();
            resetBreedTime();
        }

        if (ecosystem.isSimulationEnd()) {
            System.out.println("The End: All grids are occupied by Ants or Doodlebugs.");
            System.exit(0);
        }
    }
    @Override
    /*public void move() {
        Organism prey = findPrey();
        if (prey != null) {
            int newX = prey.getX();
            int newY = prey.getY();
            ecosystem.move(x, y, newX, newY);
            x = newX;
            y = newY;
            incrementBreedTime();
            starveTime = 0; // Reset starve time
            return;
        }

        int[] directions = {0, 1, 2, 3}; // 0: up, 1: right, 2: down, 3: left
        Utils.shuffleArray(directions); // Randomize direction

        for (int dir : directions) {
            int newX = x, newY = y;

            if (dir == 0) {
                newX--;
            } else if (dir == 1) {
                newY++;
            } else if (dir == 2) {
                newX++;
            } else {  // dir == 3
                newY--;
            }

            if (isValidPosition(newX, newY)) {
                ecosystem.move(x, y, newX, newY);
                x = newX;
                y = newY;
                incrementBreedTime();
                starveTime++;
                setMoved(true);

                if (starveTime >= 3) {
                    ecosystem.removeDoodlebug(x, y);
                }
                return;
            }
        }
        setMoved(false);
    }*/

    // new
    public void move() {
        // Random movement
        int[] directions = {0, 1, 2, 3}; // 0: up, 1: right, 2: down, 3: left
        Utils.shuffleArray(directions); // Randomize direction

        for (int dir : directions) {
            int newX = x, newY = y;

            if (dir == 0) {
                newX--;
            } else if (dir == 1) {
                newY++;
            } else if (dir == 2) {
                newX++;
            } else {
                newY--;
            }

            if (isValidPosition(newX, newY)) {
                if (ecosystem.getAt(newX, newY) instanceof Ant) {
                    eatAnt(newX, newY);
                } else {
                    ecosystem.move(x, y, newX, newY);
                }
                x = newX;
                y = newY;
                incrementBreedTime();
                starveTime++;
                setMoved(true);

                // Reset starve time if the doodlebug has eaten
                if (ecosystem.getAt(x, y) instanceof Ant) {
                    starveTime = 0;
                }

                // Remove the doodlebug if it has starved for 3 or more steps
                if (starveTime >= 3) {
                    ecosystem.removeDoodlebug(x, y);
                }
                return; // Stop after the first valid move
            }
        }
        setMoved(false); // Set moved to false if no valid move is possible
    }
    // new
    private void eatAnt(int x, int y) {
        Organism prey = ecosystem.getAt(x, y);
        ecosystem.removeDoodlebug(x, y); // Remove the ant from the grid
        ecosystem.move(this.x, this.y, x, y); // Move the doodlebug to the ant's position
        this.x = x;
        this.y = y;
        //incrementBreedTime(); // Increment breed time
        starveTime = 0; // Reset starve time after eating
    }


    private Organism findPrey() {
        if (isAdjacent(x - 1, y, Ant.class)) {
            return ecosystem.getAt(x - 1, y);
        } else if (isAdjacent(x, y + 1, Ant.class)) {
            return ecosystem.getAt(x, y + 1);
        } else if (isAdjacent(x + 1, y, Ant.class)) {
            return ecosystem.getAt(x + 1, y);
        } else if (isAdjacent(x, y - 1, Ant.class)) {
            return ecosystem.getAt(x, y - 1);
        }
        return null;
    }

    @Override
    protected boolean isValidPosition(int newX, int newY) {
        return newX >= 0 && newX < ecosystem.getHeight() && newY >= 0 && newY < ecosystem.getWidth();
    }
}
