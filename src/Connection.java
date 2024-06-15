import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class Connection extends JFrame {
    private JTextField textField1;
    private JButton connectButton;
    private JPanel Connect;

    Connection(ADSProject ntw, String name){
        ADSProject n = ntw;
        String userName = name;
        setContentPane(Connect);
        setTitle("Connect");
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(200, 130);
        setLocationRelativeTo(null);
        setVisible(false);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<User> s = n.socialNetwork.keySet();
                User u1 = null, u2 = null;
                for(User ur : s){
                    if(userName.equals(ur.name)){
                        u1 = ur;
                    }
                }
                for(User ur : s){
                    if(textField1.getText().equals(ur.name)){
                        u2 = ur;
                    }
                }
                n.addConnection(u1, u2);
                JOptionPane.showMessageDialog(Connection.this,  u1.name + " and" + u2.name + " are now friends!");

            }
        });
    }
}
