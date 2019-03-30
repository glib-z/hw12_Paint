package gz.paint.utills;

import gz.paint.shape.Figure;

import java.util.ArrayList;
import java.util.List;

public class Save {

    private int activeIndex;
    private List<Figure> shapes = new ArrayList<>();

    public Save() {
    }

    public Save(int activeIndex, List<Figure> shapes) {
        this.activeIndex = activeIndex;
        this.shapes = shapes;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public List<Figure> getShapes() {
        return shapes;
    }

    @Override
    public String toString() {
        return "Save{" +
                "activeIndex=" + activeIndex +
                ", shapes=" + shapes +
                '}';
    }
}
