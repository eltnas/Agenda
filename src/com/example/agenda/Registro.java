import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Registro extends JFrame {
    private JTextField campoNome;
    private JPasswordField campoSenha;
    private JTextField campoEmail;

    public Registro() {
        super("Registro de Usuário");

        // Criação dos componentes da tela
        JLabel labelNome = new JLabel("Nome:");
        JLabel labelSenha = new JLabel("Senha:");
        JLabel labelEmail = new JLabel("Email:");

        campoNome = new JTextField(20);
        campoSenha = new JPasswordField(20);
        campoEmail = new JTextField(20);

        JButton botaoRegistrar = new JButton("Registrar");

        // Configuração do layout
        setLayout(new GridLayout(4, 2, 10, 10));
        add(labelNome);
        add(campoNome);
        add(labelSenha);
        add(campoSenha);
        add(labelEmail);
        add(campoEmail);
        add(new JLabel()); // Espaço vazio para alinhar o botão à direita
        add(botaoRegistrar);

        // Configuração do comportamento do botão Registrar
        botaoRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });

        // Configuração da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setVisible(true);
    }

    private void registrarUsuario() {
        String nome = campoNome.getText();
        String senha = new String(campoSenha.getPassword());
        String email = campoEmail.getText();

        // Realiza o hash SHA-1 da senha
        String senhaHash = hashSHA1(senha);

        // Conexão com o banco de dados
        String url = "jdbc:mysql://localhost/agenda";
        String usuario = "agenda";
        String senhaBD = "123456789";

        try {
            Connection conn = DriverManager.getConnection(url, usuario, senhaBD);

            // Insere o novo usuário no banco de dados
            String sql = "INSERT INTO usuario (nome, senha, email) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, senhaHash);
            stmt.setString(3, email);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

            JOptionPane.showMessageDialog(this, "Registro efetuado com sucesso!");

            // Limpa os campos após o registro
            campoNome.setText("");
            campoSenha.setText("");
            campoEmail.setText("");

            // Após o registro bem-sucedido, exibe a tela de login
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    Login.exibir();
                }
            });

            // Fecha a janela de registro
            dispose();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar usuário: " + ex.getMessage());
        }
    }

    private String hashSHA1(String input) {
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

    public static void exibir() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Registro();
            }
        });
    }

    public static void main(String[] args) {
        exibir();
    }
}
