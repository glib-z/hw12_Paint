package gz.paint;

import gz.paint.shape.*;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

class Board {

    private int activeIndex = -1;
    private GraphicsContext gc;

    private List<Figure> shapes = new ArrayList<>();

    Board(GraphicsContext gc) {
        this.gc = gc;
    }

    void addBall(GraphicsContext gc) {
        this.gc = gc;
        shapes.add(new Ball(gc, 15, 15, 30));
        activeIndex = shapes.size() - 1;
    }

    void addSquare(GraphicsContext gc) {
        this.gc = gc;
        shapes.add(new Square(gc, 15, 15, 30));
        activeIndex = shapes.size() - 1;
    }

    void addTriangle(GraphicsContext gc) {
        this.gc = gc;
        shapes.add(new Triangle(gc, 15, 15, 30));
        activeIndex = shapes.size() - 1;
    }

    void delete() {
        if (shapes.size() > 1) {                               // Check if board has more then one figures
            shapes.remove(activeIndex);
            activeIndex = shapes.size() - 1;
        }
    }

    void nextShape() {
        if (!shapes.isEmpty()) {                               // Check if some figure exist
            if (activeIndex < shapes.size() - 1) {
                activeIndex++;
            } else {
                activeIndex = 0;
            }
        }
    }

    void move(double dx, double dy) {
        if (!shapes.isEmpty()) {                                // Check if some figure exist
            shapes.get(activeIndex).move(dx, dy);
        }
    }

    void changeSize(double ds) {
        if (!shapes.isEmpty()) {                                // Check if some gz.paint.shape exist
            shapes.get(activeIndex).zoom(ds);
        }
    }

    void draw() {
        clean();
        for (int i = 0; i < shapes.size(); i++) {
            shapes.get(i).draw(i == activeIndex);
        }

        // Temporary code
        System.out.printf("Board status: activeIndex=%d\n", activeIndex);
        for (int i = 0; i < shapes.size(); i++) {
            System.out.printf("shape[%d]: getShapeID=%d\n", i, shapes.get(i).getShapeID());
        }

    }

    private void clean() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }


    void merge(int x, int y, boolean mrg) {

        int pointedIndex = -1;
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).isPointed(x, y)) {
                pointedIndex = i;                       // An upper figure is always has a bigger index
            }
        }
        if (pointedIndex == -1) return;                 // No one figure is pointed

        System.out.printf("activeIndex: %d, pointedIndex: %d.\n", activeIndex, pointedIndex);

        /* The merging section */
        if ((pointedIndex != activeIndex) && mrg) {
            if (shapes.get(activeIndex).add(shapes.get(pointedIndex))) {        // Adding selected figure into existing Group
                shapes.remove(pointedIndex);                                    // Removing the link from the board list
                if (activeIndex > pointedIndex) {
                    activeIndex--;                                              // Correcting the activeIndex
                }
            } else {
                shapes.add(new Group(gc, shapes.get(activeIndex)));             // Creating a new Group with active figure
                shapes.get(shapes.size() - 1).add(shapes.get(pointedIndex));    // Adding pointed figure
                if (activeIndex > pointedIndex) {                               // Detecting a bigger index
                    shapes.remove(activeIndex);                                 // Removing the link with a bigger index
                    shapes.remove(pointedIndex);                                // Removing the link with a less index
                } else {
                    shapes.remove(pointedIndex);
                    shapes.remove(activeIndex);
                }
                activeIndex = shapes.size() - 1;                                // Correcting the activeIndex
            }
            draw();
        }

        /* The cloning section */
        if ((pointedIndex == activeIndex) && !mrg) {


        }

    }
}