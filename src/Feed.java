import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

public class Feed extends JFrame {
    private JPanel feed;
    private JButton yourPosts;
    private JButton backToMain;
    private JButton logOut;
    private JButton friendsPosts;
    private JButton networksTopPosts;
    private JButton friendsTopPosts;
    private JTextArea postArea;
    private JButton nextButton;
    private JButton previousButton;
    private JButton viewLikesButton;
    private JButton viewCommentsButton;

    public int currentPostIndex = -1;

    public ArrayList<String[]> p;

    String name;

    Feed(String n){
        setContentPane(feed);
        setTitle("Feed");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setVisible(false);
        name = n;
        ADSProject ntw = new ADSProject();


        yourPosts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<User> set = ntw.socialNetwork.keySet();
                for(User u : set){
                    if(u.name.equals(name)){
                        p = ntw.seeUserPosts(u);
                        if (!p.isEmpty()) {
                            currentPostIndex = 0; // Start with the first post
                            displayCurrentPost();
                        } else {
                            postArea.setText("No posts found.");
                        }
                        break;
                    }
                }

            }
        });
        friendsPosts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<User> set = ntw.socialNetwork.keySet();
                for(User u : set){
                    if(u.name.equals(name)){
                        p =  ntw.seeFriendsPosts(u);
                        if (!p.isEmpty()) {
                            currentPostIndex = 0; // Start with the first post
                            displayCurrentPost();
                        } else {
                            postArea.setText("No posts found.");
                        }
                        break;
                    }
                }

            }
        });
        friendsTopPosts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<User> set = ntw.socialNetwork.keySet();
                for(User u : set){
                    if(u.name.equals(name)){
                        p = ntw.topFivePostsOfFriends(u);
                        if (!p.isEmpty()) {
                            currentPostIndex = 0; // Start with the first post
                            displayCurrentPost();
                        } else {
                            postArea.setText("No posts found.");
                        }
                        break;
                    }
                }

            }
        });
        networksTopPosts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<User> set = ntw.socialNetwork.keySet();
                for(User u : set){
                    if(u.name.equals(name)){
                        p = ntw.topFivePostsOfNetwork();
                        if (!p.isEmpty()) {
                            currentPostIndex = 0; // Start with the first post
                            displayCurrentPost();
                        } else {
                            postArea.setText("No posts found.");
                        }
                        break;
                    }
                }

            }
        });
        backToMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Feed.this,  "going to main window..");
                dispose();
                Index i = new Index(name);
                i.setVisible(true);

            }
        });
        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Feed.this,  "logging out");
                dispose();
                new Login();
            }
        });
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPostIndex > 0) {
                    currentPostIndex--;
                    displayCurrentPost();
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPostIndex < p.size() - 1) {
                    currentPostIndex++;
                    displayCurrentPost();
                }
            }
        });
        viewCommentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPostIndex >= 0 && currentPostIndex < p.size()) {
                    String post = p.get(currentPostIndex)[2];
                    String[] lines = post.split("\n");
                    String postContent = lines[0];
                    String comments = p.get(currentPostIndex)[2];
                    JOptionPane.showMessageDialog(Feed.this, comments, "Comments", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        viewLikesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPostIndex >= 0 && currentPostIndex < p.size()) {
                    String post = p.get(currentPostIndex)[1];
                    String[] lines = post.split("\n");
                    String postContent = lines[0];
                    // Retrieving likes for the selected post
                    String likes = p.get(currentPostIndex)[1];
                    JOptionPane.showMessageDialog(Feed.this, likes, "Likes", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
    private void displayCurrentPost() {
        if (currentPostIndex >= 0 && currentPostIndex < p.size()) {
            postArea.setText(p.get(currentPostIndex)[0]);
        }
    }
}


