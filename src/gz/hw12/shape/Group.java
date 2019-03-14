package gz.hw12.shape;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Group extends Figure {

    //public static final int FIGCODE = 0;

    private List<Figure> shapes = new ArrayList<>();

    Group(GraphicsContext gc, double x, double y, double size) {
        super(gc, x, y, size);
    }

    @Override
    public void draw(Boolean fill) {

    }

    @Override
    public Boolean isPointed(int x, int y) {
        return false;
    }
}
