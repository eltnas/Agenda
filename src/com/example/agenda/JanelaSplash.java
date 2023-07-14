import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

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
        int y = ((screen.height - height) / 2); // para centralizar
        setBounds(x, y, width, height);

        JLabel label = new JLabel(new ImageIcon("src/recursos/phone.jpeg"));
        content.add(label);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setString("Carregando...");
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(300, 20));
        content.add(progressBar, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (verificarRegistros()) {
                            Login.exibir();
                        } else {
                            new Registro().setVisible(true);
                        }
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
        dispose();
    }

    private boolean verificarRegistros() {
        // Configuração da conexão com o banco de dados
        String url = "jdbc:mysql://localhost/agenda";
        String usuario = "agenda";
        String senhaBD = "123456789";

        try {
            Connection conn = DriverManager.getConnection(url, usuario, senhaBD);

            // Verifica se existem registros na tabela de usuários
            String sql = "SELECT COUNT(*) FROM usuario";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            rs.close();
            stmt.close();
            conn.close();

            return count > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        int tempoSplash = 3000;
        JanelaSplash splash = new JanelaSplash(tempoSplash);
        splash.showSplash();
    }
}