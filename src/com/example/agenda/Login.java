import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Login {

    public static void exibir() {
        JFrame janela = new JFrame("Agenda");
        janela.setSize(300, 400);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centralizar a janela no meio da tela
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - janela.getWidth()) / 2;
        int y = (screenSize.height - janela.getHeight()) / 2;
        janela.setLocation(x, y);

        JPanel painel = new JPanel(new GridBagLayout());
        janela.getContentPane().add(painel);

        // formulario de Login
        GridBagConstraints gbcLogin = new GridBagConstraints();
        gbcLogin.gridx = 1;
        gbcLogin.gridy = 0;
        gbcLogin.weightx = 1.0;
        gbcLogin.fill = GridBagConstraints.BOTH;

        // Define os campos do formulário
        JPanel frmPainel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcComponents = new GridBagConstraints();
        gbcComponents.gridx = 0;
        gbcComponents.gridy = GridBagConstraints.RELATIVE;
        gbcComponents.insets = new Insets(5, 5, 5, 5);

        JLabel lblUsuario = new JLabel("Usuário");
        JTextField txtUsuario = new JTextField(20);
        frmPainel.add(lblUsuario, gbcComponents);
        frmPainel.add(txtUsuario, gbcComponents);

        JLabel lblSenha = new JLabel("Senha");
        JPasswordField txtSenha = new JPasswordField(20);
        frmPainel.add(lblSenha, gbcComponents);
        frmPainel.add(txtSenha, gbcComponents);

        JButton btnLogin = new JButton("Entrar");

        gbcComponents.anchor = GridBagConstraints.CENTER;
        frmPainel.add(btnLogin, gbcComponents);

        JButton btnExit = new JButton("Cancelar");
        gbcComponents.anchor = GridBagConstraints.CENTER;
        frmPainel.add(btnExit, gbcComponents);

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String senha = new String(txtSenha.getPassword());

                if (verificarCredenciais(usuario, senha)) {
                    // Abre a tela Agenda
                    Agenda.main(new String[]{});

                    // Fecha a tela de login
                    janela.dispose();
                } else {
                    JOptionPane.showMessageDialog(janela, "Usuário ou senha invalido(s)!");
                }
            }
        });

        painel.add(frmPainel, gbcLogin);

        janela.setVisible(true);
    }

    private static boolean verificarCredenciais(String usuario, String senha) {
        // Realiza o hash SHA-1 da senha
        String senhaHash = hashSHA1(senha);

        // Conexão com o banco de dados
        String url = "jdbc:mysql://localhost/agenda";
        String usuarioBD = "agenda";
        String senhaBD = "123456789";

        try {
            Connection conn = DriverManager.getConnection(url, usuarioBD, senhaBD);

            // Verifica as credenciais no banco de dados
            String sql = "SELECT COUNT(*) FROM usuario WHERE usuario = ? AND senha = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, senhaHash);
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

    private static String hashSHA1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                exibir();
            }
        });
    }
}