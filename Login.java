import javax.swing.*;
import java.awt.*;

public class Agenda {

    public static void main(String[] args)
    {
        JFrame janela = new JFrame("Agenda");
        janela.setSize(600,300);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new GridBagLayout());
        

        janela.getContentPane().add(painel);
        janela.setVisible(true);
    }
}