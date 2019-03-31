package gz.paint.shape;

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
        setXY();
    }

    @Override
    public void rotate(double dAngle) {
        for (Figure groupShape : groupShapes) {
            double dx = groupShape.getX() - x;
            double dy = groupShape.getY() - y;
            double angleS = Math.atan(dy / dx);
            double line = Math.sqrt(dx*dx + dy*dy);
            if ((dx > 0 && dy > 0) || (dx > 0 && dy < 0)) {
                groupShape.setX(x + line * Math.cos(angleS - dAngle));
                groupShape.setY(y + line * Math.sin(angleS - dAngle));
            } else {
                groupShape.setX(x + line * Math.cos(angleS - dAngle - Math.PI));
                groupShape.setY(y + line * Math.sin(angleS - dAngle - Math.PI));
            }
            groupShape.rotate(dAngle);
        }
    }

    @Override
    public void draw(Boolean fill) {
        for (Figure groupShape : groupShapes) groupShape.draw(fill);
        // Drawing the center of mass if group is active
        if (fill) {
            Shape center1 = new Square(gc, x, y, 3, 0);
            Shape center2 = new Ball(gc, x, y, 1, 0);
            center1.draw(true);
            center2.draw(true);
        }
    }

    @Override
    public void zoom(double ds) {
        for (Figure groupShape : groupShapes) {
            groupShape.zoom(ds);
            // Changing position of the shape in the group
            groupShape.setX(x + (groupShape.getX() - x) * ds);
            groupShape.setY(y + (groupShape.getY() - y) * ds);
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
        setXY();
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
    public double getWeight() {
        double weight = 0;
        for (Figure groupShape : groupShapes) {
            weight += groupShape.getWeight();
        }
        return weight;
    }

    /**
     * Calculates the center of mass
     */
    private void setXY() {
        x = 0;
        y = 0;
        for (Figure groupShape : groupShapes) {
            x += groupShape.getX() * groupShape.getWeight();
            y += groupShape.getY() * groupShape.getWeight();
        }
        x /= getWeight();
        y /= getWeight();
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupShapes=" + groupShapes +
                '}';
    }
}
