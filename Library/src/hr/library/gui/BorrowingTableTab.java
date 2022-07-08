package hr.library.gui;

import hr.library.database.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BorrowingTableTab extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private static String[] columns = {"Return date", "User ID", "User name", "User surname", "Book title", "Book author", "Book edition"};
	
	private static JTable table = new JTable(Database.fetchBorrow(), columns);
	
	public BorrowingTableTab() {
		setLayout(new BorderLayout());
		JPanel mPanel = new JPanel(new BorderLayout());
		
		mPanel.add(new JScrollPane(table), BorderLayout.NORTH);
		add(mPanel, BorderLayout.CENTER);	
	}
	
	public static void updateTable() {
		DefaultTableModel model = new DefaultTableModel(Database.fetchBorrow(), columns);
		table.setModel(model);
	}
}
