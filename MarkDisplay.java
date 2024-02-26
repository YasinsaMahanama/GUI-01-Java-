import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class MarkDisplay extends JFrame{
    private JPanel markDisplay;
    private JTable mark;

    private DefaultTableModel tableModel;

    public MarkDisplay() {
        setTitle("");
        setVisible(true);
        setContentPane(markDisplay);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450,474);
        setLocation(450,100);
        setResizable(true);
        creatable();
    }

    public void creatable(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mark1","root","1234");
            String sql = "SELECT * FROM student";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            tableModel = new DefaultTableModel();
            mark.setModel(tableModel);

            tableModel.setColumnIdentifiers(new String[]{"Student ID", "Name", "CA", "Practical", "Theory", "Final"});
            while(rs.next()){
                int ca = rs.getInt("CA");
                int pr = rs.getInt("PR");
                int th = rs.getInt("TH");
                int total = ca + pr + th;
                float average = (float)total/3;

                String averageFormatted = String.format("%.1f", average);

                Object[] row = {
                        rs.getInt("Student_ID"),
                        rs.getString("Name"),
                        ca,
                        pr,
                        th,
                        averageFormatted
                };
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

}
