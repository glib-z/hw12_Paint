package gz.paint.shape;

import com.google.gson.JsonObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends Figure {

    private static final short FIGCODE = 3;

    public Triangle(GraphicsContext gc, double x, double y, double size, double angle) {
        super(gc, x, y, size, angle);
    }

    @Override
    public void draw(Boolean fill) {
        double x1 = x + size * Math.cos(angle) * 2 / 3;
        double y1 = y - size * Math.sin(angle) * 2 / 3;
        double x2 = x + size * Math.cos(angle + Math.PI * 2 / 3) * 2 / 3;
        double y2 = y - size * Math.sin(angle + Math.PI * 2 / 3) * 2 / 3;
        double x3 = x + size * Math.cos(angle + Math.PI * 4 / 3) * 2 / 3;
        double y3 = y - size * Math.sin(angle + Math.PI * 4 / 3) * 2 / 3;

        if (fill) {
            gc.setFill(Color.BLACK);
            gc.fillPolygon(new double[]{x1, x2, x3}, new double[]{y1, y2, y3}, 3);
        } else {
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokePolygon(new double[]{x1, x2, x3}, new double[]{y1, y2, y3}, 3);
        }
    }

    @Override
    public boolean isPointed(int mx, int my) {
        int aX = (int) x;
        int aY = (int) (y - size / 2);
        int bX = (int) (x - size / 2);
        int bY = (int) (y + size / 2);
        int cX = (int) (x + size / 2);
        int cY = (int) (y + size / 2);

        int n1 = (bY - aY)*(mx - aX) - (bX - aX)*(my - aY);
        int n2 = (cY - bY)*(mx - bX) - (cX - bX)*(my - bY);
        int n3 = (aY - cY)*(mx - cX) - (aX - cX)*(my - cY);

        return ((n1>0) && (n2>0) && (n3>0)) || ((n1<0) && (n2<0) && (n3<0));
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
        return size * size * Math.sin(Math.PI / 6);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "x=" + x +
                ", y=" + y +
                ", size=" + size +
                ", angle=" + angle +
                '}';
    }
}