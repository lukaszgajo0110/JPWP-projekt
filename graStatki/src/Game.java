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

    public int pos_x=-100; /** Zmienna x do zczytywania pozycji myszy*/
    public int pos_y=-100; /** Zmienna y do zczytywania pozycji myszy*/

    int AIshipsCounter = 5; /** Liczba statków przeciwnika*/
    int[][] AIships = new int[10][10];  /** Tablica w której generujemy statki komputera*/
    int[][] AIclicks = new int[10][10]; /** Tablica w której przechowywane są kliknięcia komputera*/

    int[][] playerShips = new int[10][10];  /** Tablica w której przechowywane są statki gracza*/
    int[][] playerClicks = new int[10][10]; /** Tablica w której przechowywane są kliknięcia gracza*/

    int sunkenPlayer=0; /** Zmienna pomocnicza do obserwowania ilości zatopionych segmentów statków przez gracza*/
    int sunkenAI=0; /** Zmienna pomocnicza do obserwowania ilości zatopionych segmentów statków przez komputer*/
    int playerWin=0;    /** Znacznik wygranej gracza*/
    int aiWin=0;    /** Znacznik wygranej komputera*/

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
    }   /** Klasa w której rysujemy wszystko co sie dzieje*/

    public class Move implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {}
        @Override
        public void mouseMoved(MouseEvent e) {
            pos_x=e.getX();
            pos_y=e.getY();
            //System.out.println("x - "+mx+" y - "+my);
        }
    }   /** Iplementacja mouse motion listenera do zczytywania pozycji myszy na ekranie*/

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
                AIclick();  /** Wywołanie metody generującej ruch komputera jednocześnie podczas kliknięcia gracza*/
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
    }   /** Implementacja mouse listenera do obsługi kliknięc na ekranie*/

    public void centerWindow(){
        Dimension dimension= Toolkit.getDefaultToolkit().getScreenSize();
        int dimX = (int) ((dimension.getWidth() - 1100)/2);
        int dimY = (int) ((dimension.getHeight() - 600 + Settings.topBar)/2);
        this.setLocation(dimX, dimY);
    }   /** Metoda ustawiająca aplikacje na środek ekranu*/
    public int getPosX(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(pos_x>=Settings.border+i*50+Settings.sideBar/2 && pos_x<Settings.border+i*50+50-Settings.border*2+Settings.sideBar/2 && pos_y>=Settings.border+j*50+Settings.topBar && pos_y<Settings.border+j*50+50-Settings.border*2+Settings.topBar){
                    return i;
                }
            }
        }
        return -1;
    }   /** Metoda wracająca indeks poziomy klikniętego przycisku*/
    public int getPosY(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(pos_x>=Settings.border+i*50+Settings.sideBar/2 && pos_x<Settings.border+i*50+50-Settings.border*2+Settings.sideBar/2 && pos_y>=Settings.border+j*50+Settings.topBar && pos_y<Settings.border+j*50+50-Settings.border*2+Settings.topBar){
                    return j;
                }
            }
        }
        return -1;
    }   /** Metoda zwracająca indeks pionowy klikniętego przycisku*/

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
    }   /** Metoda generująca dla tablicy komputera statki*/
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
    }   /** Metoda generująca ruchy komputera*/
    void getStatkiGracza(int [][]tab){
        playerShips=tab;
    }/** Metoda pobierająca tablice ze statkami ustawionymi przez gracza*/
    void showShotInfo(){
        JOptionPane.showMessageDialog(Game.this,"Trafiłeś statek przeciwnika");
    }   /** Metoda wyświetlająca informacje o trafieniu statku przeciwnika*/
}
