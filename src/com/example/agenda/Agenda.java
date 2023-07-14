import javax.swing.*;
import java.awt.*;

public class Agenda {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame agenda = new JFrame("Agenda");
                agenda.setExtendedState(JFrame.MAXIMIZED_BOTH);
                agenda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                agenda.setLayout(new BorderLayout());

                // Componentes da Agenda
                JLabel lbl = new JLabel("Agenda");
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setVerticalAlignment(SwingConstants.CENTER);
                agenda.add(lbl, BorderLayout.CENTER);

                agenda.setVisible(true);
            }
        });
    }
}
