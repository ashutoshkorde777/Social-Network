import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

public class Test {

    private JTextField userName;
    private JTextField passWord;
    private JButton saveButton;

    Test(){

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args)  {
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/mysql","root","");
            System.out.println(con);
        }catch(Exception e){

        }

    }


}
