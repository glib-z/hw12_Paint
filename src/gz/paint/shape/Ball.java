package gz.paint.shape;

import com.google.gson.JsonObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class Ball extends Figure {

    private static final short FIGCODE = 1;

    public Ball(GraphicsContext gc, double x, double y, double size, double angle) {
        super(gc, x, y, size, angle);
    }

    @Override
    public void draw(Boolean fill) {
        if (fill) {
            gc.setFill(Color.RED);
            gc.fillOval(x - size / 2, y - size / 2, size, size);
        } else {
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.strokeOval(x - size / 2, y - size / 2, size, size);
        }
    }

    @Override
    public boolean isPointed(int mx, int my) {
        return Math.sqrt(Math.pow(x - mx, 2) + Math.pow(y - my, 2)) <= size / 2;
    }

    @Override
    public int getShapeID() {
        return FIGCODE;
    }

    @Override
    public Boolean add(Figure figure) {
        return false;
    }

    @Override
    public int getPullSize() {
        return 0;
    }

    @Override
    public Figure getShape(int index) {
        return null;
    }

    @Override
    public double getWeight() {
        return Math.PI * size *size / 4;
    }


    @Override
    public String toString() {
        return "Ball{" +
                ", x=" + x +
                ", y=" + y +
                ", size=" + size +
                ", angle=" + angle +
                '}';
    }
}