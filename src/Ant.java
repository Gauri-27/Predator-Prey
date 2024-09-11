class Ant extends Organism {
    public Ant(int x, int y, Ecosystem ecosystem) {
        super(x, y, ecosystem);
    }
    private void breed() {
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
                ecosystem.populateAnt(newX, newY); // Call populateAnt directly on the ecosystem
                return;
            }
        }
    }
    public void act() {
        //move();
        if (isReadyToBreed(3)) {
            breed();
            resetBreedTime();
        }
    }

    @Override
    public void move() {
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
            } else {  // dir = 3
                newY--;
            }

            if (isValidPosition(newX, newY)) {
                ecosystem.move(x, y, newX, newY);
                x = newX;
                y = newY;
                incrementBreedTime();
                setMoved(true);
                return;
            }
        }
        setMoved(false);
    }
}
