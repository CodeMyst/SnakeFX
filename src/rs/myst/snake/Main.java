package rs.myst.snake;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.InputReceiver;
import mars.drawingx.drawing.View;
import mars.geometry.Box;
import mars.geometry.Vector;
import mars.input.InputEvent;
import mars.input.InputState;

public class Main implements Drawing, InputReceiver {
    private static final Vector gridSize = new Vector(600, 600);
    private final int tileSize = 20;

    private Snake snake;

    private Vector foodPos;

    private int updateCounter = 0;

    public static void main(String[] args) {
        DrawingApplication.launch(gridSize.x, gridSize.y);
    }

    private Vector randomFoodPos() {
        double randX = Math.floor(Math.random() * (gridSize.x / tileSize));
        double randY = Math.floor(Math.random() * (gridSize.y / tileSize));

        return new Vector((randX * tileSize) - 340, (randY * tileSize) - 300);
    }

    @Override
    public void init(View view) {
        snake = new Snake(gridSize, tileSize);
        foodPos = randomFoodPos();
    }

    @Override
    public void receiveEvent(View view, InputEvent event, InputState state, Vector pointerWorld, Vector pointerViewBase) {
        if (event.isKeyPress(KeyCode.UP)) {
            snake.setDirection(Direction.UP);
        } else if (event.isKeyPress(KeyCode.DOWN)) {
            snake.setDirection(Direction.DOWN);
        } else if (event.isKeyPress(KeyCode.LEFT)) {
            snake.setDirection(Direction.LEFT);
        } else if (event.isKeyPress(KeyCode.RIGHT)) {
            snake.setDirection(Direction.RIGHT);
        }
    }

    @Override
    public void draw(View view) {
        // simulate lower fps with the updateCounter
        if (updateCounter >= 6) {
            if (snake.shouldEat(foodPos)) {
                foodPos = randomFoodPos();
            }

            snake.update();


            updateCounter = 0;
        }

        DrawingUtils.clear(view, new Color(0.11f, 0.11f, 0.11f, 1f));


        view.setFill(new Color(0.3f, 0.8, 0.3, 1.0f));
        view.fillRect(new Box(foodPos, new Vector(tileSize, tileSize)));

        snake.draw(view);

        updateCounter++;
    }
}
