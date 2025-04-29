package org.example.javafx_example.server;

public class ServerTarget {
    static double field_height = 346;
    private final int x;
    private int y;
    private final double radius;
    private int speed;
    private final int points;

    ServerTarget(int x, int y, double radius, int target_speed, int points_for_hit) {
        this.y = y;
        this.x = x;
        this.radius = radius;
        speed = target_speed;
        points = points_for_hit;
    }

    public void move() {
        y += speed;

        if (y > field_height - radius || y < radius)
            speed *= -1;

    }

    public boolean check_hit(ServerArrow arrow) {
        return Math.pow(this.x - arrow.get_x(), 2) + Math.pow(this.y - arrow.get_y(), 2) <= Math.pow(radius, 2);
    }

    public int get_points_for_hit() {
        return points;
    }

    public void set_start_coords() {
        y = (int)radius;
    }

    public int get_x() {
        return x;
    }
    public int get_y() {
        return y;
    }

}
