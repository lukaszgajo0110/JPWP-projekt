import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Random;

public class setShips extends JFrame {
    public setShips() {
        this.setTitle("Gra w statki");
        this.pack();
        this.setSize(500, 500 + 28);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }
}