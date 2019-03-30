package gz.paint.shape;

import com.google.gson.JsonObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Square extends Figure {

    private static final short FIGCODE = 2;

    public Square(GraphicsContext gc, double x, double y, double size, double angle) {
        super(gc, x, y, size, angle);
    }

    @Override
    public void draw(Boolean fill) {

        double x1 = x + size * Math.cos(angle + Math.PI / 4) / Math.sqrt(2);
        double y1 = y - size * Math.sin(angle + Math.PI / 4) / Math.sqrt(2);
        double x2 = x + size * Math.cos(angle + Math.PI * 3 / 4) / Math.sqrt(2);
        double y2 = y - size * Math.sin(angle + Math.PI * 3 / 4) / Math.sqrt(2);
        double x3 = x + size * Math.cos(angle + Math.PI * 5 / 4) / Math.sqrt(2);
        double y3 = y - size * Math.sin(angle + Math.PI * 5 / 4) / Math.sqrt(2);
        double x4 = x + size * Math.cos(angle + Math.PI * 7 / 4) / Math.sqrt(2);
        double y4 = y - size * Math.sin(angle + Math.PI * 7 / 4) / Math.sqrt(2);

        if (fill) {
            gc.setFill(Color.GREEN);
            gc.fillPolygon(new double[]{x1, x2, x3, x4}, new double[]{y1, y2, y3, y4}, 4);
        } else {
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(2);
            gc.strokePolygon(new double[]{x1, x2, x3, x4}, new double[]{y1, y2, y3, y4}, 4);
        }
    }

    @Override
    public boolean isPointed(int mx, int my) {
        return (Math.abs((int)(x - mx)) <= size / 2) && (Math.abs((int)(y - my)) <= size / 2);
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
    public java.lang.String getShapeInfo() {
        JsonObject info = new JsonObject();
        info.addProperty("shapeID", FIGCODE);
        info.addProperty("X", x);
        info.addProperty("Y", y);
        info.addProperty("size", size);
        return info.toString();
    }

    @Override
    public double getWeight() {
        return size * size;
    }

    @Override
    public String toString() {
        return "Square{" +
                "x=" + x +
                ", y=" + y +
                ", size=" + size +
                ", angle=" + angle +
                '}';
    }
}