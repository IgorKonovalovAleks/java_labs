package org.example.javafx_example;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.example.javafx_example.server.PlayerStatistic;

import java.util.List;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class CircleView {

    @FXML
    Circle bullet;

    @FXML
    Circle bullet1;

    @FXML
    Circle target1;

    @FXML
    Circle target2;

    @FXML
    AnchorPane mainPane;

    @FXML
    Button connectButton;

    @FXML
    TextField nick;

    @FXML
    VBox right;

    Thread t = null;
    boolean play = false;
    boolean fpause = false;
    boolean shooting = false;
    double speed1 = 1;
    double speed2 = 2;
    SocketClient cc;
    ClientModel model = BClientModel.get_model();
    int port = 5588;
    InetAddress ip = null;
    Socket cs;
    private int[] arrows;
    PlayerInfoControlPanel controlPanel;

    @FXML
    public void initialize(){
        model.mc = this;
        arrows = new int[2];

        for (int i = 0; i < 2; i++) {
            arrows[i] = 30;
        }

        controlPanel = new PlayerInfoControlPanel();


    }

    @FXML
    void get_leaderboard() {
        model.cls.send_signal(new SignalMsg(MsgAction.GET_LEADERBOARD, true));
    }

    public void update_data(PlayerInfo[] infos) {
        Platform.runLater(() -> {
            int[] new_target_coords = model.get_target_coords();
            target1.setLayoutY(new_target_coords[0]);

            target2.setLayoutY(new_target_coords[1]);

            ArrowData[] arrows_data = model.get_arrows();
            bullet.setLayoutX(arrows_data[0].get_x());
            bullet1.setLayoutX(arrows_data[1].get_x());
            controlPanel.update_data(infos);
        });
    }

    public void add_new_player(PlayerInfo info) {
        Platform.runLater( () -> {
            //iconsField.add_new_icon(info.num_on_field, info.num_on_field == model.player_id);
            controlPanel.add_new_player(info);
        });
    }

    public void show_leaderboard(List<PlayerStatistic> leaderboard) {
        Platform.runLater( () -> {
            LeaderboardWindow leaderboard_window = new LeaderboardWindow();
            leaderboard_window.show(leaderboard);
        });
    }

    @FXML
    public void connect(){
        if (model.cls != null) {
            return;
        }
        try {
            System.out.println("asd");
            Socket cs;
            System.out.println("asd");
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println("asd");
            cs = new Socket(ip, port);
            System.out.println("asd");
            model.cls = new SocketClient(cs);
            System.out.println("asd");
            model.cls.send_auth_data(nick.getText());
            System.out.println("asd");

            AuthResponse resp = model.cls.get_auth_response();
            if (resp.is_connected()) {
                model.cls.listen_msg();
            }
            else {
                model.cls.close();
                model.cls = null;
            }

        }
        catch (IOException e) {
            System.out.println(e.toString());
        }
    }
    /*
        void next(){
            double width = mainPane.getWidth();
            double height = mainPane.getHeight();
            double y = bullet.getLayoutY();
            double x1 = target1.getLayoutX();
            double x2 = target2.getLayoutX();
            double x = bullet.getLayoutX();
            double y1 = target1.getLayoutY();
            double y2 = target2.getLayoutY();
            if (shooting) x += 4;
            y1 += speed1;
            y2 += speed2;

            if (x > width - 15) {
                x = 30;
                shooting = false;
            }
            if (y1 > height - 35 || y1 < 35)
                speed1 *= -1;
            if (y2 > height - 20 || y2 < 20)
                speed2 *= -1;

            if (shooting) {
                if ((x - x1) * (x - x1) + (y - y1) * (y - y1) < (42 * 42)){
                    shooting = false;
                    x = 30;
                    score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1));
                }
                if ((x - x2) * (x - x2) + (y - y2) * (y - y2) < (26 * 26)){
                    shooting = false;
                    x = 30;
                    score.setText(Integer.toString(Integer.parseInt(score.getText()) + 2));
                }
            }

            bullet.setLayoutX(x);
            target1.setLayoutY(y1);
            target2.setLayoutY(y2);
        }
    */
    @FXML
    void start(){
//        circle.setLayoutX(0);
        model.cls.send_signal(new SignalMsg(MsgAction.SET_READY, true));
        /*
        if(t == null)
        {
          t = new Thread(
              ()->
              {
                play = true;
                fpause = false;
                while (play)
                {
                    Platform.runLater(this::next);


                    try {
                        if(fpause)
                        {
                            synchronized (this)
                            {
                                this.wait();
                            }
                            fpause = false;
                        }

                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        play = false;
                        t = null;
                    }
                }
              }
          );
          t.start();
        }
        */
    }

    @FXML
    void stop()
    {
        if(t != null)
        {
            play = false;
            shooting = false;
            t.interrupt();
        }
    }

    public void show_winner(PlayerInfo info) {
        Platform.runLater( () -> {
            controlPanel.update_num_wins(info);
            WinnerWindow.show(info);
        });
    }

    @FXML
    void pause(){
        if (model.cls != null) {
            model.cls.send_signal(new SignalMsg(MsgAction.PAUSE, true));
        }
    }


    @FXML
    void cont(){
        synchronized (this)
        {
            if(t != null)
                this.notifyAll();
        }
    }


    @FXML
    void click(MouseEvent evn){
        if (model.cls != null) {
            model.cls.send_signal(new SignalMsg(MsgAction.SHOT, true));
        }
    }

}
