package gz.paint.shape;

import javafx.scene.canvas.GraphicsContext;

public abstract class Figure implements Shape {

    GraphicsContext gc;
    double x;
    double y;
    double size;

    Figure(GraphicsContext gc, double x, double y, double size) {
        this.gc = gc;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    @Override
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    @Override
    public abstract void draw(Boolean fill);

    @Override
    public void zoom(double ds) {
        size += ds;
        if (size < 1) {
            size = 1;
        }
    }

    @Override
    public abstract Boolean isPointed(int x, int y);

    @Override
    public abstract int getShapeID();

    @Override
    public abstract Boolean add(Figure figure);

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getSize() {
        return size;
    }

    @Override
    public abstract int getPullSize();

    @Override
    public abstract Figure getShape(int index);

    @Override
    public abstract String getShapeInfo();

}