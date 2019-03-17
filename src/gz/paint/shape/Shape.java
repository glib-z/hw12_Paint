package gz.paint.shape;

public interface Shape {

    /**
     * Moves shape position.
     * @param dx value for increase/decrease X-position of shape
     * @param dy value for increase/decrease Y-position of shape
     */
    void move(double dx, double dy);

    /**
     * Rendering the shape.
     * @param fill true - shape is filled
     *             false - shapeis not filled
     */
    void draw(Boolean fill);

    /**
     *
     * @param ds
     */
    void zoom(double ds);

    /**
     * Returns shape ID for operations where it need to know.
     * @return shape ID: 0 - Set of shapes (Group)
     *                   1 - Ball
     *                   2 - Square
     *                   3 - Triangle
     */
    int getShapeID();

    /**
     * Returns true if mouse pointer coordinates within the shape.
     * @param mx X-position of mouse pointer
     * @param my Y-position of mouse pointer
     * @return  true if mouse pointer coordinates within the shape
     */
    Boolean isPointed(int mx, int my);

    /**
     * Adds specified shape to Set of shapes.
     * @param figure for adding
     * @return true if the method was invoked for Set of shapes (Group: shape ID = 0)
     *         false if the method was invoked for shape (shape ID = 1, 2, 3)
     */
    Boolean add(Figure figure);

    /**
     * Returns X-position of shape.
     * @return X-position of shape
     */
    double getX();

    /**
     * Returns Y-position of shape.
     * @return Y-position of shape
     */
    double getY();

    /**
     * Returns size of the shape. For Set of shapes value is not actual.
     * @return size of the shape
     */
    double getSize();

    /**
     * Returns quantity of shapes in the Group.
     * @return quantity of shapes in the Group
     */
    int getPullSize();

    /**
     * Returns pointer to the shape with specified index in the Group.
     * @param index
     * @return pointer to the shape with specified index in the Group.
     */
    Figure getShape(int index);

}