package org.example.javafx_example;

import org.example.javafx_example.server.PlayerStatistic;

import java.util.List;

public class ClientModel {

    public SocketClient cls = null;
    public CircleView mc;
    int[] target_coords;
    ArrowData[] arrows;

    int player_id = -1;

    public void set_target_coords(int[] target_coords) {
        this.target_coords = target_coords;
    }

    public void update_data(PlayerInfo[] infos) {
        mc.update_data(infos);
    }

    public int[] get_target_coords() {
        return target_coords;
    }

    public ArrowData[] get_arrows() {
        return arrows;
    }

    public void set_arrows(ArrowData[] arrows) {
        this.arrows = arrows;
    }

    public void add_new_player(PlayerInfo[] infos, int new_player_id) {
        if (player_id == - 1) {
            player_id = new_player_id;
        }
        for (PlayerInfo info : infos) {
            mc.add_new_player(info);
        }
    }

    public void show_leaderboard(List<PlayerStatistic> leaderboard) {
        mc.show_leaderboard(leaderboard);
    }
}