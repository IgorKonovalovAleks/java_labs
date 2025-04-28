package org.example.javafx_example;

public class ArrowData {
    int x;
    boolean active_flag;

    public ArrowData(int x, boolean is_active) {
        this.x = x;
        this.active_flag = is_active;
    }

    public int get_x() {
        return this.x;
    }

    public boolean is_active() {
        return active_flag;
    }
}