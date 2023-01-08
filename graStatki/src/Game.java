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

/** Klasa do głównej części gry*/
public class Game extends JFrame {

    /** Zmienna x do zczytywania pozycji myszy*/
    public int pos_x=-100;
    /** Zmienna y do zczytywania pozycji myszy*/
    public int pos_y=-100;

    /** Liczba statków pozsotałych do postawienia przez przeciwnika*/
    int AIshipsCounter = 5;
    /** Tablica w której generujemy statki komputera*/
    int[][] AIships = new int[10][10];
    /** Tablica w której przechowywane są kliknięcia komputera*/
    int[][] AIclicks = new int[10][10];

    /** Tablica w której przechowywane są statki gracza*/
    int[][] playerShips = new int[10][10];
    /** Tablica w której przechowywane są kliknięcia gracza*/
    int[][] playerClicks = new int[10][10];

    /** Zmienna pomocnicza do obserwowania ilości zatopionych segmentów statków przez gracza*/
    int sunkenPlayer=0;
    /** Zmienna pomocnicza do obserwowania ilości zatopionych segmentów statków przez komputer*/
    int sunkenAI=0;
    /** Znacznik wygranej gracza*/
    int playerWin=0;
    /** Znacznik wygranej komputera*/
    int aiWin=0;


    /**Konstuktor - stworzenie okna i ustawienie mu parametrów. Stworzenie obiektów klas wewnętrznych*/
    public Game() {
        centerWindow();
        this.setTitle("Gra w statki");
        this.pack();
        this.setSize(1100+Settings.sideBar, 600+Settings.topBar);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);

        Board board = new Board();
        this.setContentPane(board);

        Move move2 = new Move();
        this.addMouseMotionListener(move2);

        Click click2 = new Click();
        this.addMouseListener(click2);

    }

    /** Klasa w której rysujemy plansze i ruchy
     *
     * Kliknięcia gracza są sprawdzane w 6 ifach po to, żeby zastsować kolor pudła i 5 róznych kolorów trafu dla rozróżnienia statków.
     * Sprawdzana jest tu też tablica ruchów komuptera i jego strzały są również rysowane*/
    public class Board extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(Settings.tloPlanszy);
            g.fillRect(0,0,1100,600);

            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    g.setColor(Settings.polePlanszy);

                    if(playerClicks[i][j]==1){
                        g.setColor(Settings.kolorTrafu1);
                    }   /** Jeśli gracz trafi to zmień pole na zielone1*/
                    if(playerClicks[i][j]==2){
                        g.setColor(Settings.kolorTrafu2);
                    }/** Jeśli gracz trafi to zmień pole na zielone2*/
                    if(playerClicks[i][j]==3){
                        g.setColor(Settings.kolorTrafu3);
                    }/** Jeśli gracz trafi to zmień pole na zielone3*/
                    if(playerClicks[i][j]==4){
                        g.setColor(Settings.kolorTrafu4);
                    }/** Jeśli gracz trafi to zmień pole na zielone4*/
                    if(playerClicks[i][j]==5){
                        g.setColor(Settings.kolorTrafu5);
                    }/** Jeśli gracz trafi to zmień pole na zielone5*/

                    if(playerClicks[i][j]==-1){
                        g.setColor(Settings.kolorPudla);
                    }   /** Jeśli gracz nie trafi to zmień pole na czerwone*/

                    g.fillRect(Settings.border+i*50,Settings.border+j*50,50-Settings.border*2,50-Settings.border*2);
                    g.setColor(Settings.polePlanszy);
                    if(playerShips[i][j]==1){
                        g.setColor(Settings.kolorStatku);
                    }
                    if(AIclicks[i][j]==1){
                        g.setColor(Settings.kolorTrafu1);
                    }   /** Jeśli komputer trafi to zmień pole na zielone*/
                    if(AIclicks[i][j]==-1){
                        g.setColor(Settings.kolorPudla);
                    }   /** Jeśli komputer trafi to zmień pole na zielone*/

                    g.fillRect(Settings.border+i*50+600,Settings.border+j*50,50-Settings.border*2,50-Settings.border*2);

                }
            }
            g.setColor(Settings.polePlanszy);
            g.fillRect(0,503,1100,100);
            g.setColor(Color.white);
            g.setFont(new Font("Tahoma",Font.PLAIN,40));
            g.drawString("Plansza komputera",84,545);
            g.drawString("Plansza twoja",730,545);
            g.setFont(new Font("Tahoma",Font.PLAIN,18));
            g.drawString("(Tutaj 'klikasz' ty)",180,580);
            g.drawString("(Tutaj 'klika' komputer)",761,580);

        }
    }

    /** Klasa będąca implementacją MouseMotionListenera w celu obsługi ruchu myszki*/
    public class Move implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {}
        @Override
        public void mouseMoved(MouseEvent e) {
            pos_x=e.getX();
            pos_y=e.getY();
            //System.out.println("x - "+mx+" y - "+my);
        }
    }

    /** Klasa będąca implementacjąMouseListenera która:
     *
     * Zczytuje pozycje klikniętego kafelka przez gracza i porównuje ją tabelą statków komputera.
     * Jeśli jest traf to wywoływana jest funckja showShotInfo i licznik zatopień gracza się zwiększa.
     *
     * Zostaje tu wywołana metoda generująca kliknięcia komputera. Czyli zgodnie z naszym kliknięciem klika też komputer.
     * Zostaje sprawdzony warunek końca gry i zwycięstwa gracza lub komputera*/
    public class Click implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            if(getPosX()!=-1 && getPosY()!=-1 && playerClicks[getPosX()][getPosY()]!=1 && playerClicks[getPosX()][getPosY()]!=-1){
                if(AIships[getPosX()][getPosY()]==1 && playerClicks[getPosX()][getPosY()]!=1){
                    playerClicks[getPosX()][getPosY()]=1;
                    showShotInfo();
                    sunkenPlayer=sunkenPlayer+1;}
                if(AIships[getPosX()][getPosY()]==2 && playerClicks[getPosX()][getPosY()]!=2){
                    playerClicks[getPosX()][getPosY()]=2;
                    showShotInfo();
                    sunkenPlayer=sunkenPlayer+1;}
                if(AIships[getPosX()][getPosY()]==3 && playerClicks[getPosX()][getPosY()]!=3){
                    playerClicks[getPosX()][getPosY()]=3;
                    showShotInfo();
                    sunkenPlayer=sunkenPlayer+1;}
                if(AIships[getPosX()][getPosY()]==4 && playerClicks[getPosX()][getPosY()]!=4){
                    playerClicks[getPosX()][getPosY()]=4;
                    showShotInfo();
                    sunkenPlayer=sunkenPlayer+1;}
                if(AIships[getPosX()][getPosY()]==5 && playerClicks[getPosX()][getPosY()]!=5){
                    playerClicks[getPosX()][getPosY()]=5;
                    showShotInfo();
                    sunkenPlayer=sunkenPlayer+1;}
                if(AIships[getPosX()][getPosY()]==0){
                    playerClicks[getPosX()][getPosY()]=-1;
                }
                AIclick();  //Wywołanie metody generującej ruch komputera jednocześnie podczas kliknięcia gracza
                if(sunkenPlayer==15){
                    JOptionPane.showMessageDialog(Game.this,"Gratulacje, wygrałeś!");
                    playerWin=1;
                }
                if(sunkenAI==15){
                    JOptionPane.showMessageDialog(Game.this,"Niestety ale przegrałeś.");
                    aiWin=1;
                }
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }

    /** Metoda ustawiająca aplikacje na środek ekranu*/
    public void centerWindow(){
        Dimension dimension= Toolkit.getDefaultToolkit().getScreenSize();
        int dimX = (int) ((dimension.getWidth() - 1100)/2);
        int dimY = (int) ((dimension.getHeight() - 600 + Settings.topBar)/2);
        this.setLocation(dimX, dimY);
    }
    /** Metoda wracająca indeks poziomy klikniętego przycisku*/
    public int getPosX(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(pos_x>=Settings.border+i*50+Settings.sideBar/2 && pos_x<Settings.border+i*50+50-Settings.border*2+Settings.sideBar/2 && pos_y>=Settings.border+j*50+Settings.topBar && pos_y<Settings.border+j*50+50-Settings.border*2+Settings.topBar){
                    return i;
                }
            }
        }
        return -1;
    }
    /** Metoda zwracająca indeks pionowy klikniętego przycisku*/
    public int getPosY(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(pos_x>=Settings.border+i*50+Settings.sideBar/2 && pos_x<Settings.border+i*50+50-Settings.border*2+Settings.sideBar/2 && pos_y>=Settings.border+j*50+Settings.topBar && pos_y<Settings.border+j*50+50-Settings.border*2+Settings.topBar){
                    return j;
                }
            }
        }
        return -1;
    }

    /** Metoda generująca dla tablicy komputera statki
     *
     * Losowana jest orientacja statku pionowa lub pozioma i nastepnie sprawdzane są dostępne miejsca dla statków tj. czy nie wykracza poza plansze i czy nie zachodzi na inne statki
     * Zmienna określająca ile statków jeszcze musi zostać postawionych zostaje pomniejszona
     * Pętla generowania tych statków działa dopóki licznik nie osiągnie 0*/
    public void genAIships(){
        Random rand = new Random();
        double dx, dy;
        int x,y;
        double orientation;
        do{
            dx=(Math.random()*(10))+0;
            dy=(Math.random()*(10))+0;
            x=(int) dx;
            y=(int) dy;
            orientation=Math.round(Math.random());; //0-orientacja pozioma 1-orientacja pionowa
            if(AIshipsCounter==5 && orientation==0.0 && AIships[x][y]==0 && x>1 && x<8 && AIships[x+2][y]==0 && AIships[x+1][y]==0 && AIships[x-2][y]==0 && AIships[x-1][y]==0) {
                AIships[x-2][y]=5;
                AIships[x-1][y]=5;
                AIships[x][y]=5;
                AIships[x+1][y]=5;
                AIships[x+2][y]=5;
                AIshipsCounter=4;
            }
            if(AIshipsCounter==4 && orientation==0.0 && AIships[x][y]==0 && x>0 && x<8 && AIships[x+2][y]==0 && AIships[x+1][y]==0 && AIships[x-1][y]==0) {
                AIships[x-1][y]=4;
                AIships[x][y]=4;
                AIships[x+1][y]=4;
                AIships[x+2][y]=4;
                AIshipsCounter=3;
            }
            if(AIshipsCounter==3 && orientation==0.0 && AIships[x][y]==0 && x>0 && x<9 && AIships[x+1][y]==0 && AIships[x-1][y]==0) {
                AIships[x-1][y]=3;
                AIships[x][y]=3;
                AIships[x+1][y]=3;
                AIshipsCounter=2;
            }
            if(AIshipsCounter==2 && orientation==0.0 && AIships[x][y]==0 && x<9 && AIships[x+1][y]==0) {
                AIships[x][y] = 2;
                AIships[x + 1][y] = 2;
                AIshipsCounter = 1;
            }
            if(AIshipsCounter==1 && orientation==0.0 && AIships[x][y]==0) {
                AIships[x][y] = 1;
                AIshipsCounter = 0;
            }
            if(AIshipsCounter==5 && orientation==1.0 && AIships[x][y]==0 && y>1 && y<8 && AIships[x][y+2]==0 && AIships[x][y+1]==0 && AIships[x][y-2]==0 && AIships[x][y-1]==0) {
                AIships[x][y-2]=5;
                AIships[x][y-1]=5;
                AIships[x][y]=5;
                AIships[x][y+1]=5;
                AIships[x][y+2]=5;
                AIshipsCounter=4;
            }
            if(AIshipsCounter==4 && orientation==1.0 && AIships[x][y]==0 && y>0 && y<8 && AIships[x][y+2]==0 && AIships[x][y+1]==0 && AIships[x][y-1]==0) {
                AIships[x][y-1]=4;
                AIships[x][y]=4;
                AIships[x][y+1]=4;
                AIships[x][y+2]=4;
                AIshipsCounter=3;
            }
            if(AIshipsCounter==3 && orientation==1.0 && AIships[x][y]==0 && y>0 && y<9 && AIships[x][y+1]==0 && AIships[x][y-1]==0) {
                AIships[x][y-1]=3;
                AIships[x][y]=3;
                AIships[x][y+1]=3;
                AIshipsCounter=2;
            }
            if(AIshipsCounter==2 && orientation==1.0 && AIships[x][y]==0 && y<9 && AIships[x][y+1]==0) {
                AIships[x][y] = 2;
                AIships[x][y+1] = 2;
                AIshipsCounter = 1;
            }
            if(AIshipsCounter==1 && orientation==1.0 && AIships[x][y]==0) {
                AIships[x][y] = 1;
                AIshipsCounter = 0;
            }
        }while(AIshipsCounter!=0);
    }

    /** Metoda generująca ruchy komputera
     *
     * Ruchy generowane są losowo.
     * Losowane są dwie liczby i sprawdzane jakiś statek jest pod tym indeksem
     * Jeśli jest to ustawiamy pole tablicy kliknięc komputera na 1 i zwiększamy licznik zatopionych statków.
     */
    public void AIclick(){
        int licznik=0;
        do{
            Random rand = new Random();
            double dx, dy;
            int x,y;
            dx=(Math.random()*(10))+0;
            dy=(Math.random()*(10))+0;
            x=(int) dx;
            y=(int) dy;

            if(playerShips[x][y]==1 && AIclicks[x][y]!=1 && AIclicks[x][y]!=-1){
                AIclicks[x][y]=1;
                sunkenAI=sunkenAI+1;
                licznik=1;
            }
            if(playerShips[x][y]==2 && AIclicks[x][y]!=1 && AIclicks[x][y]!=-1){
                AIclicks[x][y]=1;
                sunkenAI=sunkenAI+1;
                licznik=1;
            }
            if(playerShips[x][y]==3 && AIclicks[x][y]!=1 && AIclicks[x][y]!=-1){
                AIclicks[x][y]=1;
                sunkenAI=sunkenAI+1;
                licznik=1;
            }
            if(playerShips[x][y]==4 && AIclicks[x][y]!=1 && AIclicks[x][y]!=-1){
                AIclicks[x][y]=1;
                sunkenAI=sunkenAI+1;
                licznik=1;
            }
            if(playerShips[x][y]==5 && AIclicks[x][y]!=1 && AIclicks[x][y]!=-1){
                AIclicks[x][y]=1;
                sunkenAI=sunkenAI+1;
                licznik=1;
            }
            if(playerShips[x][y]==0 && AIclicks[x][y]!=1 && AIclicks[x][y]!=-1) {
                AIclicks[x][y]=-1;
                licznik=1;
            }
        }while(licznik==0);
    }
    /** Metoda pobierająca tablice ze statkami ustawionymi przez gracza*/
    void getStatkiGracza(int [][]tab){
        playerShips=tab;
    }
    /** Metoda wyświetlająca informacje o trafieniu statku przeciwnika*/
    void showShotInfo(){
        JOptionPane.showMessageDialog(Game.this,"Trafiłeś statek przeciwnika");
    }
}
