import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Random;

/** Klasa główna w której tworzone są obiekty klas Game i setShips*/
public class Main implements Runnable{

    /** Obiekt klasy setShips*/
    setShips setships = new setShips();
    /** Obiekt klasy Game*/
    Game game = new Game();


    public static void main(String[] args) {
        new Thread(new Main()).start();
    }

    @Override
    /** Metoda w której jest przebieg gry.
     *
     * Ustawiane są tu widoczności okien.
     * Dwa ify sprawdzające etap gry i podejmujące właściwe działania.
     * Decyzja o końcu gry i zakończenia działania programu*/
    public void run() {
        game.setVisible(false); // okno gry na starcie niepotrzebne
        while(true){
            setships.repaint();
            game.genAIships();
            game.repaint();
            /** Koniec ustawiania statków przez gracza
             *
             * Okno do ustawiania statków zostaje ukryte.
             * Dla obiektu klasy Game zostaje przekazana wypełniona tablica z obiektu klasy setShips
             * Widoczność okna Game jest włączana*/
            if(setships.isEndOfSetting()==-1){
                game.getStatkiGracza(setships.tabGracza());
                setships.dispose();
                game.setVisible(true);
            }
            /** Sprawdzenia warunku wygrania gry przez gracza i wyłączenie aplikacji*/
            if(game.playerWin==1){
                game.dispose();
                break;
            }
            /** Sprawdzenia warunku wygrania gry przez komputer i wyłączenie aplikacji*/
            if(game.aiWin==1){
                game.dispose();
                break;
            }
        }
    }
}

