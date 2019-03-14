package gz.hw12.shape;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Group extends Figure {

    //public static final int FIGCODE = 0;

    private List<Figure> groupShapes = new ArrayList<>();

    Group(GraphicsContext gc, double x, double y, double size) {
        super(gc, x, y, size);
    }

    @Override
    public void draw(Boolean fill) {
        for (Figure groupShape : groupShapes) groupShape.draw(fill);
    }

    @Override
    public Boolean isPointed(int x, int y) {
        for (Figure groupShape : groupShapes) {
            if (groupShape.isPointed(x, y)) return true;
        }
        return false;
    }
}
