package gz.paint;

import com.google.gson.Gson;
import gz.paint.shape.*;
import gz.paint.utills.Save;
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
        shapes.add(new Ball(gc, 15, 15, 30, 0));
        activeIndex = shapes.size() - 1;
    }

    void addSquare(GraphicsContext gc) {
        this.gc = gc;
        shapes.add(new Square(gc, 15, 15, 30, 0));
        activeIndex = shapes.size() - 1;
    }

    void addTriangle(GraphicsContext gc) {
        this.gc = gc;
        shapes.add(new Triangle(gc, 15, 20, 30, Math.PI / 2));
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

    void rotate(double dAnge) {
        if (!shapes.isEmpty()) {                                // Check if some figure exist
            shapes.get(activeIndex).rotate(dAnge);
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

    /**
     * Merging or cloning shapes.
     */
    void merge(int x, int y, boolean mrg) {

        // Detecting if any figure is pointed
        int pointedIndex = -1;
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).isPointed(x, y)) {
                pointedIndex = i;                       // An upper figure is always has a bigger index
            }
        }
        if (pointedIndex == -1) return;                 // No one figure is pointed

        /* The merging section */
        if ((pointedIndex != activeIndex) && mrg) {
            int activeID = shapes.get(activeIndex).getShapeID();
            int pointedID = shapes.get(pointedIndex).getShapeID();

            if ((activeID != 0) && (pointedID != 0)) {
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
                if ((activeID == 0) && (pointedID == 0)) {
                    copyGroup(shapes.get(pointedIndex), shapes.get(activeIndex));         // Copying of all figures from the pointed Group to the active Group
                } else {
                    if (activeID != 0) {
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
            double cAngle = shapes.get(pointedIndex).getAngle();
            activeIndex = shapes.size();                 // The activeIndex is preset for the new Group (OUT OF BOUND !!!)
            switch (shapes.get(pointedIndex).getShapeID()) {
                case 1: /* Ball */
                    shapes.add(new Ball(gc, cX, cY, cSize, cAngle));
                    break;
                case 2: /* Square */
                    shapes.add(new Square(gc, cX, cY, cSize, cAngle));
                    break;
                case 3: /* Triangle */
                    shapes.add(new Triangle(gc, cX, cY, cSize, cAngle));
                    break;
                case 0: /* Set of shapes */
                    shapes.add(new Group(gc, 0, 0, 0, 0));        // Creating a new empty Group
                    copyGroup(shapes.get(pointedIndex), shapes.get(activeIndex));
                    break;
            }
            shapes.get(activeIndex).move(3, 3);
        }
        draw();
    }

    private void copyGroup(Shape source, Shape target) {
        for (int i = 0; i < source.getPullSize(); i++) {
            double cX = source.getShape(i).getX();
            double cY = source.getShape(i).getY();
            double cSize = source.getShape(i).getSize();
            double cAngle = source.getShape(i).getAngle();
            switch (source.getShape(i).getShapeID()) {
                case 1:
                    target.add(new Ball(gc, cX, cY, cSize, cAngle));
                    break;
                case 2:
                    target.add(new Square(gc, cX, cY, cSize, cAngle));
                    break;
                case 3:
                    target.add(new Triangle(gc, cX, cY, cSize, cAngle));
                    break;
            }
        }
    }

    /**
     * Splitting group of shapes
     */
    void split() {
        if ((shapes.size() > 0) && (shapes.get(activeIndex).getShapeID() == 0)) {
            for (int i = 0; i < shapes.get(activeIndex).getPullSize(); i++) {
                double cX = shapes.get(activeIndex).getShape(i).getX();
                double cY = shapes.get(activeIndex).getShape(i).getY();
                double cSize = shapes.get(activeIndex).getShape(i).getSize();
                double cAngle = shapes.get(activeIndex).getShape(i).getAngle();
                switch (shapes.get(activeIndex).getShape(i).getShapeID()) {
                    case 1:
                        shapes.add(new Ball(gc, cX, cY, cSize, cAngle));
                        break;
                    case 2:
                        shapes.add(new Square(gc, cX, cY, cSize, cAngle));
                        break;
                    case 3:
                        shapes.add(new Triangle(gc, cX, cY, cSize, cAngle));
                        break;
                }
            }
        }
        shapes.remove(activeIndex);
        activeIndex = shapes.size() - 1;
        draw();
    }


    void saveBoardInfo() {
        Save save = new Save(activeIndex, shapes);
        System.out.println(save);
        Gson gson = new Gson();
        //Gson gson = new GsonBuilder().create();
        String json = gson.toJson(save);
        FileWriter f = null;
        try {
            f = new FileWriter("save.txt");
            f.write(json);
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}