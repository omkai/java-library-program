package hr.library.gui;

import hr.library.book.Book;
import hr.library.borrow.Borrow;
import hr.library.database.Database;
import hr.library.users.User;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BorrowTab extends JPanel {

	private static final long serialVersionUID = 1L;


	private JLabel bId = new JLabel("Book ID:");
	private JTextField tBId = new JTextField();

	private JLabel bTime = new JLabel("Borrow time: ");
	private JTextField tBTime = new JTextField();

	private JLabel uId = new JLabel("User ID: ");
	private JTextField tUId = new JTextField();

	private JPanel btnPanel = new JPanel();
	private JButton uAddBtn = new JButton("Borrow a book");

	JPanel mPanel = new JPanel(new BorderLayout());
	private JPanel tPanel = new JPanel(new GridLayout(5, 0, 15, 15));
	private JPanel lPanel = new JPanel(new GridLayout(5, 0, 15, 15));

	private JLabel borrowLabel = new JLabel("       BORROW");
	private JLabel filler1 = new JLabel();

	private JLabel returnLabel = new JLabel("       RETURN");
	private JLabel filler2 = new JLabel();

	private JLabel rBId = new JLabel("Book ID: ");
	private JLabel rUId = new JLabel("User ID:");

	private JTextField rTBId = new JTextField();
	private JTextField rTUId = new JTextField();

	private JPanel rBtnPanel = new JPanel();
	private JButton returnBtn = new JButton("Return book");

	private JPanel rLPanel = new JPanel(new GridLayout(5, 0, 15, 15));
	private JPanel rTPanel = new JPanel(new GridLayout(5, 0, 15, 15));

	private JPanel rMPanel = new JPanel(new BorderLayout());

	JPanel bottomLevelGridPanel = new JPanel(new GridLayout(2, 0, 15, 15));
	JPanel topLevelGridPanel = new JPanel(new GridLayout(2, 0, 15, 15));

	private static String[] columns = {"Return date", "User ID", "User name", "User surname", "Book title", "Book author", "Book edition"};

	private static JTable table = new JTable(Database.fetchBorrowings(), columns);

	private JLabel filler3 = new JLabel();
	private JLabel filler4 = new JLabel();

	private Border borrowBorder = BorderFactory.
			createCompoundBorder(new EtchedBorder(), new EmptyBorder(10, 20, 10, 20));
	private Border returnBorder = BorderFactory.
			createCompoundBorder(new EtchedBorder(), new EmptyBorder(10, 20, 10, 20));

	public BorrowTab() {
		setLayout(new BorderLayout());

		lPanel.add(borrowLabel);
		lPanel.add(uId);
		lPanel.add(bId);
		lPanel.add(bTime);

		tPanel.add(filler1);
		tPanel.add(tUId);
		tPanel.add(tBId);
		tPanel.add(tBTime);

		mPanel.add(lPanel, BorderLayout.WEST);
		mPanel.add(tPanel, BorderLayout.CENTER);
		btnPanel.add(uAddBtn);
		mPanel.add(btnPanel, BorderLayout.SOUTH);
		mPanel.setBorder(borrowBorder);

		bottomLevelGridPanel.add(mPanel);

		rLPanel.add(returnLabel);
		rLPanel.add(rUId);
		rLPanel.add(rBId);
		rLPanel.add(filler3);

		rTPanel.add(filler2);
		rTPanel.add(rTUId);
		rTPanel.add(rTBId);
		rTPanel.add(filler4);

		rBtnPanel.add(returnBtn);

		rMPanel.add(rLPanel, BorderLayout.WEST);
		rMPanel.add(rTPanel, BorderLayout.CENTER);
		rMPanel.add(rBtnPanel, BorderLayout.SOUTH);
		rMPanel.setBorder(returnBorder);

		bottomLevelGridPanel.add(rMPanel);

		topLevelGridPanel.add(bottomLevelGridPanel);
		topLevelGridPanel.add(new JScrollPane(table));

		add(new JScrollPane(topLevelGridPanel));


		uAddBtn.addActionListener((event) -> {

			Runnable r = new Runnable() {

				@Override
				public void run() {
					try {
						if(tBId.getText().length() != 0 && tUId.getText().length() != 0 && tBTime.getText().length() != 0) {

							int borrowTime = Integer.parseInt(tBTime.getText().toString());
							User user = Database.getUser(Integer.parseInt(tUId.getText().toString()));
							Book book = Database.getBook(Integer.parseInt(tBId.getText().toString()));

							if(book == null) {
								JOptionPane.showMessageDialog(BorrowTab.this, "Book doesn't exist");
							} else if (book.isBorrowed() == true) {
								JOptionPane.showMessageDialog(BorrowTab.this, "Book already borrowed");
							} else if (user == null) {
								JOptionPane.showMessageDialog(BorrowTab.this, "User is not registered");
							} else if (user.getNofBooks() >= 3) {
								JOptionPane.showMessageDialog(BorrowTab.this, "User has too many books");
							} else if (borrowTime < 15) {
								JOptionPane.showMessageDialog(BorrowTab.this,  "Borrow time cannot be shorter than 15 days");
							} else {
								Database.insertBorrow(new Borrow(user, book, Integer.parseInt(tBTime.getText().toString())));
								updateTable();
								BookTab.changeStatus(book, "true");
								UserTab.changeStatus(user, true);
								Database.bookStatusChange(book.getId(), !book.isBorrowed());
								JOptionPane.showMessageDialog(BorrowTab.this, "Borrow entered");
							}
						} else JOptionPane.showMessageDialog(BorrowTab.this, "No field can stay empty");

					} catch(Exception e) {
						System.out.println(e);
						e.printStackTrace();
						JOptionPane.showMessageDialog(BorrowTab.this, "Invalid input");
					}	
				}
			};

			new Thread(r).run();

		});


		returnBtn.addActionListener(a -> {

			Runnable r = new Runnable() {

				@Override
				public void run() {
					try {
						Borrow borrow = Database.getBorrow(Database.getUser(Integer.parseInt(rTUId.getText().toString())),
								Database.getBook(Integer.parseInt(rTBId.getText().toString())));
						Book book = borrow.getBook();
						User user = borrow.getUser();

						if(rTBId.getText().length() == 0 && rTUId.getText().length() == 0) {
							JOptionPane.showMessageDialog(BorrowTab.this, "All fields have to be filled");
						} else {
							Database.returnBorrow(book.getId(), user.getId());
							Database.bookStatusChange(borrow.getBook().getId(), !borrow.getBook().isBorrowed());
							updateTable();
							BookTab.changeStatus(borrow.getBook(), "false");
							UserTab.changeStatus(borrow.getUser(), false);
							JOptionPane.showMessageDialog(BorrowTab.this, "Book returned");
						}
					} catch(NullPointerException e) {
						System.out.println("No such borrow");
						JOptionPane.showMessageDialog(BorrowTab.this, "No such borrow");
					} catch(Exception e) {
						System.out.println(e);
						e.printStackTrace();
						JOptionPane.showMessageDialog(BorrowTab.this, "Invalid entry");
					}
				}
			};
			
			new Thread(r).run();
			
		});

	}

	public void updateTable() {
		DefaultTableModel model = new DefaultTableModel(Database.fetchBorrowings(), columns);
		table.setModel(model);
	}
}
