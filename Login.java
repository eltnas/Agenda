import javax.swing.*;
import java.awt.*;

public class Login {

    public static void exibir() {
        JFrame janela = new JFrame("Agenda");
        janela.setSize(600, 300);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centralizar a janela no meio da tela
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - janela.getWidth()) / 2;
        int y = (screenSize.height - janela.getHeight()) / 2;
        janela.setLocation(x, y);

        JPanel painel = new JPanel(new GridBagLayout());

        janela.getContentPane().add(painel);
        janela.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                exibir();
            }
        });
    }
}
