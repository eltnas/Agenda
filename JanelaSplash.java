import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JanelaSplash extends JWindow {
    private int duracao;

    public JanelaSplash(int duracao) {
        this.duracao = duracao;
    }

    public void showSplash() {
        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.RED);

        int width = 400;
        int height = 350;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = ((screen.height - height) / 2) - 175; // para centralizar
        setBounds(x, y, width, height);

        JLabel label = new JLabel(new ImageIcon("./Imagem/books.jpg"));
        content.add(label);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setString("Carregando...");
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(300, 20));
        content.add(progressBar, BorderLayout.SOUTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        janelaLogin();
                    }
                });
            }
        });

        setVisible(true);
        try {
            Thread.sleep(duracao);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setVisible(false);
    }

    private void janelaLogin() {
        JFrame janelaLogin = new JFrame("Janela de Login");
        janelaLogin.setSize(400, 300);
        janelaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Bem-vindo!");
        janelaLogin.add(label);

        janelaLogin.setVisible(true);
    }

    public static void main(String[] args) {
        int tempoSplash = 3000;
        JanelaSplash splash = new JanelaSplash(tempoSplash);
        splash.showSplash();
    }
}
