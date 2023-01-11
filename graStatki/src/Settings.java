import javax.swing.*;
import java.awt.*;
/** klasa kolorów, ustawień i stałych zmiennych używanych w projekcie*/
public class Settings extends JFrame {
    //Ze względu na różny rozmiar okna na Windowsie i MacOS (pasek górny i boczne okna) należy odpowiednio ustawić poniższe zmienne
    /**pole określające w pixelach wysokość paska górnego okna*/
    static int topBar=29;   //28 - macOS, 29 - Windows
    /**pole określające w pixelach szerokość pasków bocznych okna*/
    static int sideBar=12;   //0 - macOS, 12 - Windows

    /** Kolory używane w projekcie*/
    static Color polePlanszy = new Color(182,182,180);
    static Color tloPlanszy = new Color(152,152,150);
    static Color kolorStatku = new Color(255,0,127,50);
    static Color kolorPudla = new Color(194,24,7);
    static Color kolorTrafu1 = new Color(199,234,70);
    static Color kolorTrafu2 = new Color(169,204,80);
    static Color kolorTrafu3 = new Color(139,174,90);
    static Color kolorTrafu4 = new Color(109,144,100);
    static Color kolorTrafu5 = new Color(79,114,110);

    /** Liczba px pomiędzy komórkami siatki*/
    static int border=3;

}
