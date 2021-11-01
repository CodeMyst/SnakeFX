package rs.myst.snake;

import javafx.scene.paint.Color;
import mars.drawingx.drawing.View;
import mars.geometry.Box;
import mars.geometry.Vector;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private final Vector gridSize;
    private final int size;

    private Vector position = new Vector(0, 0);
    private Direction direction = Direction.NONE;

    private int tailSize = 0;
    private final List<Vector> tail = new ArrayList<>();

    private boolean alive = true;

    public Snake(Vector gridSize, int size) {
        this.gridSize = gridSize;
        this.size = size;
    }

    public void update() {
        // check if the snake's head collides with the tail
        for (int i = 0; i < tailSize - 1; i++) {
            if (position.distanceTo(tail.get(i)) < 5) {
                alive = false;
            }
        }

        // don't update the snake if it's not alive
        if (!alive) return;

        if (tailSize == tail.size()) {
            // shift the tail list by one
            for (int i = 0; i < tailSize - 1; i++) {
                tail.set(i, tail.get(i + 1));
            }
        } else if (tailSize > tail.size()) {
            // tailSize was incremented, add a new tail to the list
            tail.add(new Vector(-5000, -5000));
        }

        // set the last tail to the current position
        if (tailSize > 0) tail.set(tailSize - 1, position);

        // increment position by speed
        position = position.add(direction.getVec().mul(size));

        // check if out of bounds
        if (position.x < -(gridSize.x / 2) - size * 2) alive = false; // left
        if (position.x > gridSize.x / 2 + size) alive = false; // right
        if (position.y > gridSize.y / 2 - size) alive = false; // up
        if (position.y < -(gridSize.y / 2)) alive = false; // down
    }

    public void draw(View view) {
        // draw tail
        for (int i = 0; i < tailSize; i++) {
            // calculate the tail colour gradient
            view.setFill(new Color((float) (i+1) / tailSize, 0.6f, 0.9f, 1.0f));
            view.fillRect(new Box(tail.get(i), new Vector(size, size)));
        }
        
        view.setFill(new Color(1.0f, 0.6f, 0.9f, 1.0f));

        view.fillRect(new Box(position, new Vector(size, size)));

        view.setFill(Color.WHITE);

        view.fillText("score: " + tailSize, new Vector(-335, 280));

        if (!alive) {
            view.fillText("game over", new Vector(-200, 280));
        }
    }

    public void setDirection(Direction dir) {
        // make sure the new direction is a different axis
        // e.g. the snake can't move left if its already going right
        if (dir.getVec().x != 0 && direction.getVec().x != 0) return;
        if (dir.getVec().y != 0 && direction.getVec().y != 0) return;
       
        direction = dir;
    }

    public boolean shouldEat(Vector foodPos) {
        double dist = position.distanceTo(foodPos);

        if (dist < 5) {
            tailSize++;
            return true;
        }

        return false;
    }
}
