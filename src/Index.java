import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class Index extends JFrame {

    private JPanel Index;
    private JButton showNetworkButton;
    private JButton DISPLAYFriendsButton;
    private JButton findFriendOfFriendsButton;
    private JButton findTopUsersButton;
    private JButton Posts;
    private JButton logOut;
    private JButton makeConnection;

    String name;

    Index(String s){
        setContentPane(Index);
        setTitle("Index");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(310, 310);
        setLocationRelativeTo(null);
        setVisible(false);
        name = s;
        ADSProject ntw = new ADSProject();
        showNetworkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String network = ntw.printSocialNetwork();
                JOptionPane.showMessageDialog(Index.this,  "network \n\n" + network);
            }
        });
        DISPLAYFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<User> set = ntw.socialNetwork.keySet();
                for(User u : set){
                    if(u.name.equals(name)){
                        String friends = ntw.displayFriends(u);
                        JOptionPane.showMessageDialog(Index.this,  "friends \n\n" + friends);
                        break;
                    }
                }

            }
        });
        findFriendOfFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<User> set = ntw.socialNetwork.keySet();
                for(User u : set){
                    if(u.name.equals(name)){
                        String friends = ntw.findFriendsOfFriends(u);
                        JOptionPane.showMessageDialog(Index.this,  "friends of friends \n\n" + friends);
                        break;
                    }
                }
            }
        });
        findTopUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String topUsers = ntw.findTopUsers();

                JOptionPane.showMessageDialog(Index.this,  "top users \n\n" + topUsers);
            }
        });
        Posts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Index.this,  "opening your feed");
                dispose();
                Feed f = new Feed(name);
                f.setVisible(true);
            }
        });
        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Index.this,  "logging out");
                dispose();
                new Login();
            }
        });
        makeConnection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection c = new Connection(ntw, name);
                c.setVisible(true);


            }
        });
    }



}
