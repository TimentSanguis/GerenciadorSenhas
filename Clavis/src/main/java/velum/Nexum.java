package velum;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Nexum {
    private static final String URL = "jdbc:h2:./Clavis";
    private static final String USUARIO = "sa";
    private static final String SENHA = "";
    public static String sql;
    public static ResultSet rs;

    public static Connection conectar() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
        sql = "create table if not exists contas "
        		+ "(ID IDENTITY PRIMARY KEY, "
        		+ "Descricao VARCHAR(255) NOT NULL,"
        		+ "Senha VARCHAR(255) NOT NULL);";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        return conn;
    }
    
    public static JTable carregarDados(JTable table) {
    	DefaultTableModel model = (DefaultTableModel) table.getModel();
    	model.setRowCount(0);
    	try (Connection conn = conectar()) {
    		sql = "select * from contas";
    		Statement stmt = conn.createStatement();
    		rs = stmt.executeQuery(sql);
    		while (rs.next()) {
    			int id = rs.getInt("ID");
    			String desc = rs.getString("Descricao");
    			String senha = rs.getString("Senha");
    			model.addRow(new Object[] {id,desc,senha,"Copiar","Excluir"});
    		}
    	} catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao acessar banco: " + e.getMessage());
        }
		return table;
    }    
}
