import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterForm extends JFrame{
    private JPanel registerPanel;
    private JTextField tID;
    private JTextField tName;
    private JTextField tCA;
    private JTextField tPractical;
    private JTextField tTheory;
    private JButton btnView;
    private JButton btnSave;
    private JLabel ID;
    private JLabel Name;
    private JLabel CA;
    private JLabel PR;
    private JLabel TH;
    private JCheckBox check;

    public RegisterForm(){
        setTitle(" ");
        setVisible(true);
        setSize(450,474);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(registerPanel);
        setResizable(true);


        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!check.isSelected())
                {
                    JOptionPane.showMessageDialog(registerPanel,"Check");
                }
                else {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mark1", "root", "1234");
                        String sql = "INSERT INTO student(Student_ID,Name,CA,PR,TH) VALUES(?,?,?,?,?)";
                        PreparedStatement st = con.prepareStatement(sql);
                        st.setString(1, tID.getText());
                        st.setString(2, tName.getText());
                        st.setInt(3, Integer.parseInt(tCA.getText()));
                        st.setInt(4, Integer.parseInt(tPractical.getText()));
                        st.setInt(5, Integer.parseInt(tTheory.getText()));
                        st.executeUpdate();
                        st.close();
                        con.close();
                        JOptionPane.showMessageDialog(registerPanel, "Data saved successfully", "success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(registerPanel, "JDBC not connected", "Error", JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(registerPanel, "Database not connected", "Error", JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                    tID.setText("");
                    tName.setText("");
                    tCA.setText("");
                    tPractical.setText("");
                    tTheory.setText("");
                    check.setSelected(false);
                }
            }
        });

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MarkDisplay();
            }
        });
    }

    public static void main(String[] args) {
        RegisterForm myform = new RegisterForm();
    }


}

