package gz.paint.shape;

import com.google.gson.JsonObject;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Group extends Figure {

    private static final short FIGCODE = 0;

    private List<Figure> groupShapes = new ArrayList<>();

    public Group(GraphicsContext gc, double x, double y, double size, double angle) {
        super(gc, x, y, size, angle);
    }

    public Group(GraphicsContext gc, Figure figure) {
        super(gc, figure.x, figure.y, figure.size, figure.angle);
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
    public boolean isPointed(int x, int y) {
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

    @Override
    public int getPullSize() {
        return groupShapes.size();
    }

    @Override
    public Figure getShape(int index) {
        if (groupShapes.isEmpty() || (index >= groupShapes.size())) return null;
        return groupShapes.get(index);
    }

    @Override
    public java.lang.String getShapeInfo() {
        JsonObject info = new JsonObject();
        info.addProperty("shapeID", FIGCODE);
        info.addProperty("X", x);
        info.addProperty("Y", y);
        info.addProperty("size", size);
        return info.toString();
    }

}