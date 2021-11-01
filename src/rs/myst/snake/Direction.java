package rs.myst.snake;

import mars.geometry.Vector;

public enum Direction {
    NONE(Vector.ZERO),
    UP(new Vector(0, 1)),
    DOWN(new Vector(0, -1)),
    LEFT(new Vector(-1, 0)),
    RIGHT(new Vector(1, 0));

    private final Vector vec;

    Direction(Vector vec) {
        this.vec = vec;
    }

    public Vector getVec() {
        return vec;
    }
}
