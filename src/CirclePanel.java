import javax.swing.*;
import java.awt.*;

public class CirclePanel extends JPanel {
    private COFDraw drawer = new COFDraw();

    COFKeyFile.Key key;
    COFMMFile.COFMM mm;

    public CirclePanel (COFKeyFile.Key key, COFMMFile.COFMM mm) {
        this.key = key;
        this.mm = mm;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawer.drawCircleOfFifths((Graphics2D) g, getWidth() / 2, getHeight() / 2, Math.min(getWidth(), getHeight()) / 2 - 40, this.key, this.mm);
    }
}