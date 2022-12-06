import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Random;

public class Main implements Runnable{
    setShips setships = new setShips();
    Game game = new Game();


    public static void main(String[] args) {
        new Thread(new Main()).start();
    }

    @Override
    public void run() {
        game.setVisible(false);
        while(true){
            setships.repaint();
            game.repaint();
            if(setships.isEndOfSetting()==-1){
                setships.dispose();
                game.setVisible(true);
            }   /** Koniec ustawiania statków przez gracza*/
        }
    }
}