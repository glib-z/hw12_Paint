package gz.paint.shape;

public interface Shape {

    void move(double dx, double dy);

    void draw(Boolean fill);

    void zoom(double ds);

    int getShapeID();

    Boolean isPointed(int mx, int my);

    Boolean add(Figure figure);

}