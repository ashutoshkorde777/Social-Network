import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Login extends JFrame {
    private JPanel demoPanel;
    private JTextField password;
    private JButton Login;
    private JTextField Name;
    private JLabel loginLogo;

    private HashMap<String, String> users = new HashMap<>();




    public Login(){
        setContentPane(demoPanel);
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 170);
        setLocationRelativeTo(null);
        setVisible(true);


        users.put("Shreyash", "123");
        users.put("Sudhanshu", "456");
        users.put("Sharvari", "789");
        users.put("Ashutosh", "789");
        users.put("Aditya", "789");
        users.put("Ayush", "789");
        users.put("Aniket", "789");



        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = Name.getText();
                String pass = password.getText();

                Set<String> set = users.keySet();
                if(set.contains(name)){
                    if(Objects.equals(users.get(name), pass)){
                        JOptionPane.showMessageDialog(Login.this,  name + " you have successfully logged in!");
                        dispose();

                        Index index = new Index(name);
                        index.setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(Login.this,  "wrong password!");
                    }

                }
                else{
                    JOptionPane.showMessageDialog(Login.this,  "user does not exist");
                }

            }
        });
    }

    public static void main(String[] args) {

        Login l1 = new Login();
    }



}
