/*
 * Написать программу с использованием JavaFx:
 * 1. (+) Можно добавить фигуры на выбор (не менее 3 видов)
 * 2. (+) Можно переключаться между фигурами - активная фигура выделена цветом, у остальных только контур.
 * 3. (+) Можно удалять фигуры (последняя оставшаяся фигура не должна удаляться).
 * 4. (+) Активную фигуру можно двигать.
 * 5. (+) Агрегация: несколько фигур можно объединить в одну.
 * 6. (+) Фигуры можно клонировать.
 * 7. (+) Можно менять размер фигур.
 * 8. Реализовать сохраниение/загрузку в файл.
 * 9. При движении фигуры если она пересекается с другой фигурой менять у активной фигуры цвет.
 * 10. Движение по траектории. Фигура может запомнить по какой траектории ее двигали и воспроизвести.
 *
 * Обязательные требования:
 * в программе должны использоваться интерфейсы, абстрактные классы и наследование.
 */

package gz.paint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import gz.paint.utills.Logger;

public class Main extends Application {

    private static final double SPEED = 3;
    private static final double GROW = 3;
    private Board board;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Paint");
        primaryStage.setResizable(false);

        Canvas canvas = new Canvas();
        canvas.setWidth(800);
        canvas.setHeight(500);

        BorderPane group = new BorderPane(canvas);
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        primaryStage.show();
        Logger.log("Game started");

        GraphicsContext gc = canvas.getGraphicsContext2D();
        board = new Board(gc);
        board.draw();

        registerOnMousePressListener(scene);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    board.move(-SPEED, 0);
                    break;
                case RIGHT:
                    board.move(SPEED, 0);
                    break;
                case UP:
                    board.move(0, -SPEED);
                    break;
                case DOWN:
                    board.move(0, SPEED);
                    break;
                case W:
                    board.changeSize(GROW);
                    break;
                case Q:
                    board.changeSize(-GROW);
                    break;
                case DIGIT1:
                    board.addBall(gc);
                    break;
                case DIGIT2:
                    board.addSquare(gc);
                    break;
                case DIGIT3:
                    board.addTriangle(gc);
                    break;
                case CONTROL:
                    break;
                case SHIFT:
                    break;
                case TAB:
                    board.nextShape();
                    break;
                case DELETE:
                    board.delete();
                    break;
            }

            board.draw();
        });

    }

    private void registerOnMousePressListener(Scene scene) {
        scene.setOnMousePressed(event -> {
            if (event.isControlDown()) {
                board.merge((int) event.getSceneX(), (int) event.getSceneY(), true);
            }
            if (event.isShiftDown()) {
                board.merge((int) event.getSceneX(), (int) event.getSceneY(), false);
            }
        });
    }
}
