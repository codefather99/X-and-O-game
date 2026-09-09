import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;

public class RoundedButton extends JButton {

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // shadow, drawn slightly offset down and right, to fake a "raised" look
        g2.setColor(new Color(0, 0, 0, 80));
        g2.fillRoundRect(3, 4, getWidth() - 6, getHeight() - 6, 20, 20);

        // main button face
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 20, 20);

        g2.dispose();
        super.paintComponent(g);
    }
}