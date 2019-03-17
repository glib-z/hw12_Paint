package gz.paint.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends Figure {

    private static final short FIGCODE = 3;

    public Triangle(GraphicsContext gc, double x, double y, double size) {
        super(gc, x, y, size);
    }

    @Override
    public void draw(Boolean fill) {
        if (fill) {
            gc.setFill(Color.BLACK);
            gc.fillPolygon(new double[]{x, (x - size / 2), (x + size / 2)},
                    new double[]{(y - size / 2), (y + size / 2), (y + size / 2)}, 3);
        }
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokePolygon(new double[]{x, (x - size / 2), (x + size / 2)},
                new double[]{(y - size / 2), (y + size / 2), (y + size / 2)}, 3);
    }

    @Override
    public Boolean isPointed(int mx, int my) {
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

}