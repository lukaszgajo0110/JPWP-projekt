import javax.management.monitor.MonitorSettingException;
import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/** Klasa w której gracz ustawia swoje statki*/
public class setShips extends JFrame {
    /** Zmienna x do zczytywania pozycji myszy*/
    public int pos_x=-100;
    /** Zmienna y do zczytywania pozycji myszy*/
    public int pos_y=-100;
    /** Licznik statków pozostałych do postawienia*/
    int AIshipsNum = 5;
    /** Tablica do której zapisywane są statki gracza*/
    private int[][] statkiGracza = new int[10][10];

    /**Konstuktor - stworzenie okna i ustawienie mu parametrów. Stworzenie obiektów klas wewnętrznych*/
    public setShips()  {
        centerWindow();
        this.setTitle("Gra w statki");
        this.pack();
        this.setSize(500+Settings.sideBar,600+Settings.topBar);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        Board board = new Board();
        this.setContentPane(board);

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click();
        this.addMouseListener(click);

    }
    /** Klasa w której rysujemy siatke planszy i stawianie statków przez gracza*/
    public class Board extends JPanel{
        /** Metoda rysująca kafelki planszy w pętli i jednocześnie metoda zmieniająca kolor kafelków na których gracz postawił statek*/
        public void paintComponent(Graphics g){
            g.setColor(Settings.tloPlanszy);
            g.fillRect(0,0,500,600);
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    g.setColor(Settings.polePlanszy);
                    if(statkiGracza[i][j]==1){
                        g.setColor(Settings.kolorStatku);
                    }
                    g.fillRect(Settings.border+i*50,Settings.border+j*50,50-Settings.border*2,50-Settings.border*2);
                }
            }
            g.setColor(Settings.tloPlanszy);
            g.fillRect(0,505,500,100);
            g.setColor(Color.white);
            g.setFont(new Font("Tahoma",Font.PLAIN,19));
            g.drawString("Ustaw statki o długości kolejno 5,4,3,2,1. LPM - ustawienie",4,525);
            g.drawString("poziome, PPM - ustawienie pionowe. Ustawienie ostatniego",4,550);
            g.drawString("statku, automatycznie przenosi do dalszej gry",4,575);

        }
    }

    /** Klasa będąca implementacją MouseMotionListenera w celu obsługi ruchu myszki*/
    public class Move implements MouseMotionListener{
        @Override
        public void mouseDragged(MouseEvent e) {}
        /** Metoda służąca do przypisania pozycji myszy do zmiennych*/
        @Override
        public void mouseMoved(MouseEvent e) {
            pos_x=e.getX();
            pos_y=e.getY();
        }
    }

    /** Klasa będąca implementacją MouseListenera do obsługi kliknięć*/
    public class Click implements MouseListener{
        /**
         * Metoda która obsługuje kliknięcie LPM i PMP.
         *
         * Po kliknięciu sprawdzane są koordynaty myszy i są sprawdzane czy nie wykraczają poza zakres pola.
         * Jeśli jest okej to statek jest ustawiony poprzez wpisanie jedynek do tablicy. Licznik AIshipNum jest zmniejszany.
         * Działamy tak, aż licznik będzie 0 i wtedy kończymy ustawianie staków wyświetlając komunikat.
         * Na koniec licznik zmieniamy na -1 co pozwala na sprawdzenie późniejszej funkcji czy już skończono ustawianie statków.
         * */
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getButton()==MouseEvent.BUTTON1){
                if(getPosX()!=-1 && getPosY()!=-1 && AIshipsNum==5){
                    if(getPosX()>1 && getPosX()<8 && statkiGracza[getPosX()+2][getPosY()]==0 && statkiGracza[getPosX()+1][getPosY()]==0 && statkiGracza[getPosX()][getPosY()]==0 && statkiGracza[getPosX()-1][getPosY()]==0 && statkiGracza[getPosX()-2][getPosY()]==0){
                        statkiGracza[getPosX()-2][getPosY()]=1;
                        statkiGracza[getPosX()-1][getPosY()]=1;
                        statkiGracza[getPosX()][getPosY()]=1;
                        statkiGracza[getPosX()+1][getPosY()]=1;
                        statkiGracza[getPosX()+2][getPosY()]=1;
                        AIshipsNum=4;
                    }
                }

                if(getPosX()!=-1 && getPosY()!=-1 && AIshipsNum==4){
                    if(getPosX()>0 && getPosX()<8 && statkiGracza[getPosX()+2][getPosY()]==0 && statkiGracza[getPosX()+1][getPosY()]==0 && statkiGracza[getPosX()][getPosY()]==0 && statkiGracza[getPosX()-1][getPosY()]==0){
                        statkiGracza[getPosX()-1][getPosY()]=1;
                        statkiGracza[getPosX()][getPosY()]=1;
                        statkiGracza[getPosX()+1][getPosY()]=1;
                        statkiGracza[getPosX()+2][getPosY()]=1;
                        AIshipsNum=3;
                    }
                }

                if(getPosX()!=-1 && getPosY()!=-1 && AIshipsNum==3){
                    if(getPosX()>0 && getPosX()<9 && statkiGracza[getPosX()+1][getPosY()]==0 && statkiGracza[getPosX()][getPosY()]==0 && statkiGracza[getPosX()-1][getPosY()]==0){
                        statkiGracza[getPosX()-1][getPosY()]=1;
                        statkiGracza[getPosX()][getPosY()]=1;
                        statkiGracza[getPosX()+1][getPosY()]=1;
                        AIshipsNum=2;
                    }
                }

                if(getPosX()!=-1 && getPosY()!=-1 && AIshipsNum==2){
                    if(getPosX()<9 && statkiGracza[getPosX()+1][getPosY()]==0 && statkiGracza[getPosX()][getPosY()]==0){
                        statkiGracza[getPosX()][getPosY()]=1;
                        statkiGracza[getPosX()+1][getPosY()]=1;
                        AIshipsNum=1;
                    }
                }

                if(getPosX()!=-1 && getPosY()!=-1 && AIshipsNum==1){
                    if(statkiGracza[getPosX()][getPosY()]==0){
                        statkiGracza[getPosX()][getPosY()]=1;
                        AIshipsNum=0;
                    }
                }
                if(getPosX()!=-1 && getPosY()!=-1 && AIshipsNum==0){
                    JOptionPane.showMessageDialog(setShips.this,"Ustawiłeś wszystkie swoje statki, kliknij OK aby przejść do gry");
                    AIshipsNum=-1;
                }

            }
            if(e.getButton()==MouseEvent.BUTTON3){
                if(getPosX()!=-1 && getPosY()!=-1 && AIshipsNum==5){
                    if(getPosY()>1 && getPosY()<8 && statkiGracza[getPosX()][getPosY()+2]==0 && statkiGracza[getPosX()][getPosY()+1]==0 && statkiGracza[getPosX()][getPosY()]==0 && statkiGracza[getPosX()][getPosY()-1]==0 && statkiGracza[getPosX()][getPosY()-2]==0){
                        statkiGracza[getPosX()][getPosY()-2]=1;
                        statkiGracza[getPosX()][getPosY()-1]=1;
                        statkiGracza[getPosX()][getPosY()]=1;
                        statkiGracza[getPosX()][getPosY()+1]=1;
                        statkiGracza[getPosX()][getPosY()+2]=1;
                        AIshipsNum=4;
                    }
                }

                if(getPosX()!=-1 && getPosY()!=-1 && AIshipsNum==4){
                    if(getPosY()>0 && getPosY()<8 && statkiGracza[getPosX()][getPosY()+2]==0 && statkiGracza[getPosX()][getPosY()+1]==0 && statkiGracza[getPosX()][getPosY()]==0 && statkiGracza[getPosX()][getPosY()-1]==0){
                        statkiGracza[getPosX()][getPosY()-1]=1;
                        statkiGracza[getPosX()][getPosY()]=1;
                        statkiGracza[getPosX()][getPosY()+1]=1;
                        statkiGracza[getPosX()][getPosY()+2]=1;
                        AIshipsNum=3;
                    }
                }

                if(getPosX()!=-1 && getPosY()!=-1 && AIshipsNum==3){
                    if(getPosY()>0 && getPosY()<9 && statkiGracza[getPosX()][getPosY()+1]==0 && statkiGracza[getPosX()][getPosY()]==0 && statkiGracza[getPosX()][getPosY()-1]==0){
                        statkiGracza[getPosX()][getPosY()-1]=1;
                        statkiGracza[getPosX()][getPosY()]=1;
                        statkiGracza[getPosX()][getPosY()+1]=1;
                        AIshipsNum=2;
                    }
                }

                if(getPosX()!=-1 && getPosY()!=-1 && AIshipsNum==2){
                    if(getPosY()<9 && statkiGracza[getPosX()][getPosY()+1]==0 && statkiGracza[getPosX()][getPosY()]==0){
                        statkiGracza[getPosX()][getPosY()]=1;
                        statkiGracza[getPosX()][getPosY()+1]=1;
                        AIshipsNum=1;
                    }
                }

                if(getPosX()!=-1 && getPosY()!=-1 && AIshipsNum==1){
                    if(statkiGracza[getPosX()][getPosY()]==0){
                        statkiGracza[getPosX()][getPosY()]=1;
                        AIshipsNum=0;
                    }
                }
                if(getPosX()!=-1 && getPosY()!=-1 && AIshipsNum==0){
                    JOptionPane.showMessageDialog(setShips.this,"Ustawiłeś wszystkie swoje statki, kliknij OK aby przejść do gry");
                    AIshipsNum=-1;
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
        int dimX = (int) ((dimension.getWidth() - 500+Settings.sideBar)/2);
        int dimY = (int) ((dimension.getHeight() - 600+Settings.topBar)/2);
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


    /** Metoda zwarająca wypełnioną tabele ze statkami gracza*/
    public int [][] tabGracza(){
        return statkiGracza;
    }
    /** Metoda zwracająca licznik do decyzji o zakończeniu tego etapu gry */
    public int isEndOfSetting(){
        return AIshipsNum;
    }
}
