abstract class Organism {
    protected int x;
    protected int y;
    protected int breedTime;
    protected boolean hasMoved;
    protected Ecosystem ecosystem;

    public Organism(int x, int y, Ecosystem ecosystem) {
        this.x = x;
        this.y = y;
        this.ecosystem = ecosystem;
        this.breedTime = 0;
        this.hasMoved = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void move();
    public abstract void act();


    protected boolean isValidPosition(int newX, int newY) {
        return newX >= 0 && newX < ecosystem.getHeight() && newY >= 0 && newY < ecosystem.getWidth() && ecosystem.getAt(newX, newY) == null;
    }

    protected boolean isAdjacent(int newX, int newY, Class<? extends Organism> type) {
        if (isValidPosition(newX, newY)) {
            Organism adjacent = ecosystem.getAt(newX, newY);
            return adjacent != null && adjacent.getClass().equals(type);
        }
        return false;
    }

    protected void incrementBreedTime() {
        breedTime++;
    }

    protected boolean isReadyToBreed(int breedThreshold) {
        return breedTime >= breedThreshold;
    }

    protected void resetBreedTime() {
        breedTime = 0;
    }

    protected boolean hasMoved() {
        return hasMoved;
    }

    protected void setMoved(boolean moved) {
        hasMoved = moved;
    }
}
