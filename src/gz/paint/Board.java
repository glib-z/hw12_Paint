package gz.paint;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import gz.paint.shape.*;
import javafx.scene.canvas.GraphicsContext;

import java.io.*;
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
    }

    private void clean() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    /*
     * Mergin or cloning shapes.
     */
    void merge(int x, int y, boolean mrg) {
        int pointedIndex = -1;
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).isPointed(x, y)) {
                pointedIndex = i;                       // An upper figure is always has a bigger index
            }
        }
        if (pointedIndex == -1) return;                 // No one figure is pointed
        /* The merging section */
        if ((pointedIndex != activeIndex) && mrg) {
            int activID = shapes.get(activeIndex).getShapeID();
            int pointedID = shapes.get(pointedIndex).getShapeID();

            if ((activID != 0) && (pointedID != 0)) {
                shapes.add(new Group(gc, shapes.get(activeIndex)));             // Creating a new Group with active figure
                shapes.get(shapes.size() - 1).add(shapes.get(pointedIndex));    // Adding pointed figure
                if (activeIndex > pointedIndex) {                               // Detecting a bigger index
                    shapes.remove(activeIndex);                                 // Removing the link with a bigger index
                    shapes.remove(pointedIndex);                                // Removing the link with a less index
                } else {
                    shapes.remove(pointedIndex);
                    shapes.remove(activeIndex);
                }
                activeIndex = shapes.size() - 1;                                // New activeIndex
            } else {
                if ((activID == 0) && (pointedID == 0)) {
                    copyGroup(pointedIndex);         // Copying of all figures from the pointed Group to the active Group
                } else {
                    if (activID != 0) {
                        int i = activeIndex;
                        activeIndex = pointedIndex;
                        pointedIndex = i;
                    }
                    shapes.get(activeIndex).add(shapes.get(pointedIndex));      // Adding simple figure to the Group
                }
                shapes.remove(pointedIndex);                                    // Removing the Group from the board list
                if (activeIndex > pointedIndex) {
                    activeIndex--;                                              // New activeIndex
                }
            }
        }
        /* The cloning section */
        if (!mrg) {
            double cX = shapes.get(pointedIndex).getX();
            double cY = shapes.get(pointedIndex).getY();
            double cSize = shapes.get(pointedIndex).getSize();
            activeIndex = shapes.size();                 // The activeIndex is preset for the new Group (OUT OF BOUND !!!)
            switch (shapes.get(pointedIndex).getShapeID()) {
                case 1: /* Ball */
                    shapes.add(new Ball(gc, cX, cY, cSize));
                    break;
                case 2: /* Square */
                    shapes.add(new Square(gc, cX, cY, cSize));
                    break;
                case 3: /* Triangle */
                    shapes.add(new Triangle(gc, cX, cY, cSize));
                    break;
                case 0: /* Set of shapes */
                    shapes.add(new Group(gc, 0, 0, 0));              // Creating a new empty Group
                    copyGroup(pointedIndex);
                    break;
            }
            shapes.get(activeIndex).move(3, 3);
        }
        draw();
    }

    private void copyGroup(int pIndex) {
        for (int i = 0; i < shapes.get(pIndex).getPullSize(); i++) {
            double cX = shapes.get(pIndex).getShape(i).getX();
            double cY = shapes.get(pIndex).getShape(i).getY();
            double cSize = shapes.get(pIndex).getShape(i).getSize();
            switch (shapes.get(pIndex).getShape(i).getShapeID()) {
                case 1:
                    shapes.get(activeIndex).add(new Ball(gc, cX, cY, cSize));
                    break;
                case 2:
                    shapes.get(activeIndex).add(new Square(gc, cX, cY, cSize));
                    break;
                case 3:
                    shapes.get(activeIndex).add(new Triangle(gc, cX, cY, cSize));
                    break;
            }
        }
    }

    void saveBoardInfo() throws IOException {
        String boardInfo = new String();
        for (Figure shape : shapes) {
            if (shape.getShapeID() != 0) {
                boardInfo += shape.getShapeInfo();
            } else {
                boardInfo += "{GroupSTART}";
                for (int k = 0; k < shape.getPullSize(); k++) {
                    boardInfo += shape.getShape(k).getShapeInfo();
                }
                boardInfo += "{GroupEND}";
            }
        }
        System.out.println(boardInfo);
        FileWriter f = new FileWriter("save.txt");
        f.write(boardInfo);
        f.close();
    }

}