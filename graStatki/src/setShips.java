import javax.management.monitor.MonitorSettingException;
import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class setShips extends JFrame {
    public int pos_x=-100; /** Zmienna x do zczytywania pozycji myszy*/
    public int pos_y=-100; /** Zmienna y do zczytywania pozycji myszy*/
    int AIshipsNum = 5; /** Liczba statków przeciwnika*/
    private int[][] statkiGracza = new int[10][10]; /** Tablica do której zapisywane są statki gracza*/

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
    public class Board extends JPanel{
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
    }   /** Klasa w której rysujemy wszystko co sie dzieje*/

    public class Move implements MouseMotionListener{
        @Override
        public void mouseDragged(MouseEvent e) {}
        @Override
        public void mouseMoved(MouseEvent e) {
            pos_x=e.getX();
            pos_y=e.getY();
        }
    }   /** Iplementacja mouse motion listenera do zczytywania pozycji myszy na ekranie*/

    public class Click implements MouseListener{
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

            }   /** Ustawianie statku w pozycji poziomej LPM */
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
            }   /** Ustawianie statku w pozycji pionowej PPM*/
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
        int dimX = (int) ((dimension.getWidth() - 500+Settings.sideBar)/2);
        int dimY = (int) ((dimension.getHeight() - 600+Settings.topBar)/2);
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

    public int [][] tabGracza(){    /** Metoda zwarająca wypełnioną tabele ze statkami gracza*/
        return statkiGracza;
    }   /** Metoda zwracająca wypełnioną tablice ze statkami gracza*/
    public int isEndOfSetting(){
        return AIshipsNum;
    }   /** Metoda służąca do decyzji o zakończeniu pobierania statków od gracza */
}
