package gz.hw12;

import javafx.scene.canvas.GraphicsContext;
import gz.hw12.shape.Ball;
import gz.hw12.shape.Figure;
import gz.hw12.shape.Square;
import gz.hw12.shape.Triangle;

import java.util.ArrayList;
import java.util.List;

class Board {

    private int currentShape;
    private GraphicsContext gc;

    private List<Figure> shapes = new ArrayList<>();

    Board(GraphicsContext gc) {
        this.gc = gc;
    }

    void addBall(GraphicsContext gc) {
        this.gc = gc;
        shapes.add(new Ball(gc,15,15,30));
        currentShape = shapes.size() - 1;
    }

    void addSquare(GraphicsContext gc) {
        this.gc = gc;
        shapes.add(new Square(gc,15,15,30));
        currentShape = shapes.size() - 1;
    }

    void addTriangle(GraphicsContext gc) {
        this.gc = gc;
        shapes.add(new Triangle(gc,15,15,30));
        currentShape = shapes.size() - 1;
    }

    void delete() {
        if (shapes.size() > 1) {                               // Check if board has more then one figures
            shapes.remove(currentShape);
            currentShape = shapes.size() - 1;
        }
    }

    void nextShape() {
        if (shapes.size() != 0) {                               // Check if some figure exist
            if (currentShape < shapes.size() - 1) {
                currentShape++;
            } else {
                currentShape = 0;
            }
        }
    }

    void move(double dx, double dy) {
        if (shapes.size() != 0) {                                // Check if some figure exist
            shapes.get(currentShape).move(dx, dy);
        }
    }

    void changeSize(double ds) {
        if (shapes.size() != 0) {                                // Check if some gz.hw12.shape exist
            shapes.get(currentShape).zoom(ds);
        }
    }

    void draw() {
        clean();
        for (int i = 0; i < shapes.size(); i++) {
            shapes.get(i).draw(i == currentShape);
        }
    }

    private void clean() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }


    void merge(int x, int y, boolean mrg) {

        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).isPointed(x, y)) {
                System.out.printf("gz.hw12.shape.Figure #%d is founded\n", i);
            }
        }

/*        if (!mrg) {

        }
*/

    }

}
