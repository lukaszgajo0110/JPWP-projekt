import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.Set;

public class Game extends JFrame {
    public Game() {
        this.setTitle("Gra w statki");
        this.pack();
        this.setSize(1100 + Settings.sideBar, 600 + Settings.topBar);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);

        Board board = new Board();
        this.setContentPane(board);
    }

    public class Board extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(Settings.tloPlanszy);
            g.fillRect(0, 0, 1100, 600);

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    g.setColor(Settings.polePlanszy);
                    g.fillRect(Settings.border + i * 50, Settings.border + j * 50, 50 - Settings.border * 2, 50 - Settings.border * 2);
                    g.setColor(Settings.polePlanszy);
                    g.fillRect(Settings.border + i * 50 + 600, Settings.border + j * 50, 50 - Settings.border * 2, 50 - Settings.border * 2);
                }
            }
            g.setColor(Settings.polePlanszy);
            g.fillRect(0, 503, 1100, 100);
            g.setColor(Color.white);
            g.setFont(new Font("Tahoma", Font.PLAIN, 40));
            g.drawString("Plansza komputera", 84, 545);
            g.drawString("Plansza twoja", 730, 545);
            g.setFont(new Font("Tahoma", Font.PLAIN, 18));
            g.drawString("(Tutaj 'klikasz' ty)", 180, 580);
            g.drawString("(Tutaj 'klika' komputer)", 761, 580);

        }
    }   /** Klasa w której rysujemy wszystko co sie dzieje*/
}
