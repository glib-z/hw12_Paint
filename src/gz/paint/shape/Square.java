package gz.paint.shape;

import com.google.gson.JsonObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Square extends Figure {

    private static final short FIGCODE = 2;

    public Square(GraphicsContext gc, double x, double y, double size) {
        super(gc, x, y, size);
    }

    @Override
    public void draw(Boolean fill) {
        if (fill) {
            gc.setFill(Color.GREEN);
            gc.fillRect(x - size / 2, y - size / 2, size, size);
        }
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        gc.strokeRect(x - size / 2, y - size / 2, size, size);
    }

    @Override
    public Boolean isPointed(int mx, int my) {
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
    public String getShapeInfo() {
        JsonObject info = new JsonObject();
        info.addProperty("shapeID", FIGCODE);
        info.addProperty("X", x);
        info.addProperty("Y", y);
        info.addProperty("size", size);
        return info.toString();
    }

}