import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Random;

public class Main implements Runnable{
    setShips setships = new setShips();


    public static void main(String[] args) {
        new Thread(new Main()).start();
    }

    @Override
    public void run() {
        while(true){
            setships.repaint();
        }
    }
}