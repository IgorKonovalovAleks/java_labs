package org.example.javafx_example;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import org.example.javafx_example.PlayerInfo;

public class PlayerInfoControlPanel extends GridPane {
    PlayerInfoVBox[] players;
    public PlayerInfoControlPanel() {
        super();
        this.addColumn(0);
        this.getRowConstraints().add(new RowConstraints(346. / 4));
        this.getRowConstraints().add(new RowConstraints(346. / 4));
        this.setAlignment(Pos.CENTER);
        players = new PlayerInfoVBox[2];
    }

    public void add_new_player(PlayerInfo info) {
        if (players[info.get_num_on_field()] == null) {
            players[info.get_num_on_field()] = new PlayerInfoVBox(info);
            this.add(players[info.get_num_on_field()], 0, info.get_num_on_field());
        }
    }

    public void update_data(PlayerInfo[] infos) {
        for (PlayerInfo info: infos) {
            int id = info.get_num_on_field();
            players[id].update_score_val(info.get_score());
            players[id].update_shots_val(info.get_shots());
        }
    }

    public void update_num_wins(PlayerInfo info) {
        players[info.get_num_on_field()].update_wins_val(info.get_stat().getNum_wins());
    }
}