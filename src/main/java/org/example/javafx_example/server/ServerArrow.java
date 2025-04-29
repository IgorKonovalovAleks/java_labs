package org.example.javafx_example.server;

public class ServerArrow {
    static double field_width = 481;
    private boolean activity_flag;
    private final int speed;
    private final int start_x;
    private int x;
    private int y;

    ServerArrow(int start_x, int y) {
        activity_flag = false;
        this.start_x = start_x;
        x = start_x;
        speed = 4;
        this.y = y;
    }

    public void set_active(boolean value) {
        activity_flag = value;
    }

    public boolean is_active() {
        return activity_flag;
    }

    public void remove() {
        x = start_x;
        this.set_active(false);
    }

    public void move() {
        x += speed;
        if (x >= field_width) {
            remove();
        }
    }

    public int get_x() {
        return x;
    }
    public int get_y() {
        return y;
    }
}
