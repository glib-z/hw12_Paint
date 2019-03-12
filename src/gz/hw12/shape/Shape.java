package gz.hw12.shape;

public interface Shape {

    void move(double dx, double dy);
    void draw(Boolean fill);
    void zoom(double ds);
    Boolean isPointed(int mx, int my);

}
