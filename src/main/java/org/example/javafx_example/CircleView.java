package org.example.javafx_example;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class CircleView {
    @FXML
    Circle bullet;

    @FXML
    Circle target1;

    @FXML
    Circle target2;

    @FXML
    Label score;

    @FXML
    Label shootCount;

    @FXML
    AnchorPane mainPane;

    Thread t = null;
    boolean play = false;
    boolean fpause = false;
    boolean shooting = false;
    double speed1 = 1;
    double speed2 = 2;

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

    @FXML
    void shoot(){
        shooting = true;
        shootCount.setText(Integer.toString(Integer.parseInt(shootCount.getText()) + 1));
    }

    @FXML
    void start(){
//        circle.setLayoutX(0);
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
    }

    @FXML
    void stop()
    {
        if(t != null)
        {
            play = false;
            shooting = false;
            score.setText("0");
            shootCount.setText("0");
            t.interrupt();
        }
    }

    @FXML
    void pause(){
        if(t != null)
            fpause = true;
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
        shooting = true;
        shootCount.setText(Integer.toString(Integer.parseInt(shootCount.getText()) + 1));
    }

}
