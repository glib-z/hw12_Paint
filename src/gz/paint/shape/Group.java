package gz.paint.shape;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Group extends Figure {

    private static final short FIGCODE = 100;

    private List<Figure> groupShapes = new ArrayList<>();

    public Group(GraphicsContext gc, double x, double y, double size) {
        super(gc, x, y, size);
    }

    public Group(GraphicsContext gc, Figure figure) {
        super(gc, figure.x, figure.y, figure.size);
        groupShapes.add(figure);
    }


    @Override
    public void move(double dx, double dy) {
        for (Figure groupShape : groupShapes) {
            groupShape.move(dx, dy);
        }
    }

    @Override
    public void draw(Boolean fill) {
        for (Figure groupShape : groupShapes) groupShape.draw(fill);
    }

    /* This method must be changed */
    @Override
    public void zoom(double ds) {
        for (Figure groupShape : groupShapes) {
            groupShape.zoom(ds);
        }
    }

    @Override
    public Boolean isPointed(int x, int y) {
        for (Figure groupShape : groupShapes) {
            if (groupShape.isPointed(x, y)) return true;
        }
        return false;
    }

    @Override
    public int getShapeID() {
        return FIGCODE;
    }

    @Override
    public Boolean add(Figure figure) {
        groupShapes.add(figure);
        return true;
    }

}