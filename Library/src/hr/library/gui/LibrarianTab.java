package hr.library.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import hr.library.database.Database;
import hr.library.librarians.Librarian;

public class LibrarianTab extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel filler1 = new JLabel();
	
	private JLabel addLabel = new JLabel("       ADD");

	private JLabel id = new JLabel("Librarian ID: ");
	private JLabel name = new JLabel("Librarian name: ");
	private JLabel surname = new JLabel("Librarian surname: ");
	private JLabel password = new JLabel("Librarian password: ");
	private JTextField tId = new JTextField();
	private JTextField tName = new JTextField();
	private JTextField tSurname = new JTextField();
	private JPasswordField tPassword = new JPasswordField();

	private JPanel lPanel = new JPanel(new GridLayout(5, 0, 15, 15));
	private JPanel tPanel = new JPanel(new GridLayout(5, 0, 15, 15));
	private JPanel mPanel = new JPanel(new BorderLayout());
	private JPanel btnPanel = new JPanel();

	private JButton add = new JButton("Add new librarian");

	private Border border = BorderFactory.
			createCompoundBorder(new EtchedBorder(), new EmptyBorder(10, 20, 10, 20));

	public LibrarianTab() {
		setLayout(new BorderLayout());
		lPanel.add(addLabel);
		lPanel.add(id);
		lPanel.add(name);
		lPanel.add(surname);
		lPanel.add(password);

		tPanel.add(filler1);
		tPanel.add(tId);
		tPanel.add(tName);
		tPanel.add(tSurname);
		tPanel.add(tPassword);

		mPanel.setBorder(border);
		mPanel.add(lPanel, BorderLayout.WEST);
		mPanel.add(tPanel, BorderLayout.CENTER);

		btnPanel.add(add);

		add(mPanel, BorderLayout.NORTH);
		add(btnPanel, BorderLayout.SOUTH);


		add.addActionListener(a -> {
			try {
				if(tName.getText().length() == 0 || tSurname.getText().length() == 0 || tId.getText().length() == 0 || tPassword.getPassword().length == 0) {
					JOptionPane.showMessageDialog(LibrarianTab.this, "All fields have to be filled");
				} else if (Database.getLibrarian(Integer.parseInt(tId.getText().toString())) != null) {
					JOptionPane.showMessageDialog(LibrarianTab.this, "Librarian already exists");
				} else {
					Database.insertLibrarian(new Librarian(tName.getText().toString(),tSurname.getText().toString(), Integer.parseInt(tId.getText().toString()), tPassword.getPassword()));
					JOptionPane.showMessageDialog(LibrarianTab.this, "Librarian added");
				}
			} catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(LibrarianTab.this, "Invalid input");
			}
		});
	}
}
