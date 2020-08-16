package moe.gensoukyo.mcgproject.common.util.math;

public class Vec2d {

    public double x;
    public double y;

    public Vec2d() { }

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the square of the distance between two points.
     *
     * @param x1 the X coordinate of the first specified point
     * @param y1 the Y coordinate of the first specified point
     * @param x2 the X coordinate of the second specified point
     * @param y2 the Y coordinate of the second specified point
     * @return the square of the distance between the two
     * sets of specified coordinates.
     */
    public static double distanceSq(double x1, double y1, double x2, double y2) {
        x1 -= x2;
        y1 -= y2;
        return (x1 * x1 + y1 * y1);
    }

    /**
     * Returns the distance between two points.
     *
     * @param x1 the X coordinate of the first specified point
     * @param y1 the Y coordinate of the first specified point
     * @param x2 the X coordinate of the second specified point
     * @param y2 the Y coordinate of the second specified point
     * @return the distance between the two sets of specified
     * coordinates.
     */
    public static double distance(double x1, double y1, double x2, double y2) {
        x1 -= x2;
        y1 -= y2;
        return Math.sqrt(x1 * x1 + y1 * y1);
    }

    /**
     * Returns the square of the distance from this
     * <code>Vec2d</code> to a specified point.
     *
     * @param vx the X coordinate of the specified point to be measured
     *           against this <code>Vec2d</code>
     * @param vy the Y coordinate of the specified point to be measured
     *           against this <code>Vec2d</code>
     * @return the square of the distance between this
     * <code>Vec2d</code> and the specified point.
     */
    public double distanceSq(double vx, double vy) {
        vx -= x;
        vy -= y;
        return (vx * vx + vy * vy);
    }

    /**
     * Returns the square of the distance from this
     * <code>Vec2d</code> to a specified <code>Vec2d</code>.
     *
     * @param v the specified point to be measured
     *           against this <code>Vec2d</code>
     * @return the square of the distance between this
     * <code>Vec2d</code> to a specified <code>Vec2d</code>.
     */
    public double distanceSq(com.sun.javafx.geom.Vec2d v) {
        double vx = v.x - this.x;
        double vy = v.y - this.y;
        return (vx * vx + vy * vy);
    }

    /**
     * Returns the distance from this <code>Vec2d</code> to
     * a specified point.
     *
     * @param vx the X coordinate of the specified point to be measured
     *           against this <code>Vec2d</code>
     * @param vy the Y coordinate of the specified point to be measured
     *           against this <code>Vec2d</code>
     * @return the distance between this <code>Vec2d</code>
     * and a specified point.
     */
    public double distance(double vx, double vy) {
        vx -= x;
        vy -= y;
        return Math.sqrt(vx * vx + vy * vy);
    }

    /**
     * Returns the distance from this <code>Vec2d</code> to a
     * specified <code>Vec2d</code>.
     *
     * @param v the specified point to be measured
     *           against this <code>Vec2d</code>
     * @return the distance between this <code>Vec2d</code> and
     * the specified <code>Vec2d</code>.
     */
    public double distance(com.sun.javafx.geom.Vec2d v) {
        double vx = v.x - this.x;
        double vy = v.y - this.y;
        return Math.sqrt(vx * vx + vy * vy);
    }

}
