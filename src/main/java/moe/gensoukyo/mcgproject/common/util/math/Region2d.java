package moe.gensoukyo.mcgproject.common.util.math;

public class Region2d implements Cloneable {

    public Vec2d vec1;
    public Vec2d vec2;

    public Region2d(double pos1x, double pos1y, double pos2x, double pos2y) {
        this.vec1 = new Vec2d(pos1x, pos1y);
        this.vec2 = new Vec2d(pos2x, pos2y);
        this.fixPos();
    }

    public Region2d(Vec2d vec1, Vec2d vec2) {
        this.vec1= vec1;
        this.vec2 = vec2;
        this.fixPos();
    }

    public Region2d clone() {
        return new Region2d(vec1, vec2);
    }

    public boolean isVecInRegion(Vec2d vec2d) {
        return vec2d.x > this.vec1.x && vec2d.x < this.vec2.x && vec2d.y > this.vec1.y && vec2d.y < this.vec2.y;
    }

    public boolean isCoincideWith(Region2d region) {
        Vec2d a1 = this.vec1;
        Vec2d a2 = this.vec2;
        Vec2d b1 = region.vec1;
        Vec2d b2 = region.vec2;
        if (a2.x <= b1.x) return false;
        if (a1.x >= b2.x) return false;
        if (a1.y >= b2.y) return false;
        if (a2.y <= b1.y) return false;
        return true;
    }

    private void fixPos() {
        double minX = Math.min(this.vec1.x, this.vec2.x);
        double maxX = Math.max(this.vec1.x, this.vec2.x);
        double minY = Math.min(this.vec1.y, this.vec2.y);
        double maxY = Math.max(this.vec1.y, this.vec2.y);
        this.vec1.x = minX;
        this.vec1.y = minY;
        this.vec2.x = maxX;
        this.vec2.y = maxY;
    }

}
