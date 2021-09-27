package hr.library.GUI;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import hr.library.database.Database;

public class BorrowingTableTab extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private static String[] columns = {"Return date", "User ID", "User name", "User surname", "Book title", "Book author", "Book edition"};
	
	private static JTable table = new JTable(Database.fetchBorrowings(), columns);
	
	public BorrowingTableTab() {
		setLayout(new BorderLayout());
		JPanel mPanel = new JPanel(new BorderLayout());
		
		mPanel.add(new JScrollPane(table), BorderLayout.NORTH);
		add(mPanel, BorderLayout.CENTER);	
	}
	
	public static void updateTable() {
		DefaultTableModel model = new DefaultTableModel(Database.fetchBorrowings(), columns);
		table.setModel(model);
	}
}
