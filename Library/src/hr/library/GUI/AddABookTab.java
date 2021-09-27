package hr.library.GUI;

import hr.library.book.Book;
import hr.library.book.Genre;
import hr.library.database.Database;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
//import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


public class AddABookTab extends JPanel {

	private static final long serialVersionUID = 1L;

	// fillers for the grid panel so that titles of the panels can be used ("ADD, Edit")
	private JLabel filler1 = new JLabel();
	private JLabel filler2 = new JLabel();
	private JLabel filler3 = new JLabel();
	private JLabel filler4 = new JLabel();

	// text fields for the "add" panel
	private JTextField Tid = new JTextField();
	private JTextField Tname = new JTextField();
	private JTextField Tauthor = new JTextField();
	private JTextField Tedition = new JTextField();
	private JComboBox<hr.library.book.Genre> types = new JComboBox<>(hr.library.book.Genre.values());
	//sets values of the combo box to the enumeration values

	//panel for the "add" and "Edit" panels
	private JPanel gridPanelBottomLevel = new JPanel(new GridLayout(2, 0, 15, 15));

	//panel for the bottom level grid panel and the table
	private JPanel gridPanelTopLevel = new JPanel(new GridLayout(2, 0, 30, 30));

	// labels for the "add" panel	
	private JLabel add = new JLabel("       ADD");

	private JLabel id = new JLabel("Book ID: ");
	private JLabel name = new JLabel("Book title: ");
	private JLabel author = new JLabel("Author: ");
	private JLabel edition = new JLabel("Edition year: ");
	private JLabel addGenre = new JLabel("Book genre: ");

	//panels for the "add" labels + fields
	private JPanel addMainPanel = new JPanel(new BorderLayout());
	private JPanel addLabelPanel = new JPanel(new GridLayout(7, 0, 15, 15));
	private JPanel addTextPanel = new JPanel(new GridLayout(7, 0, 15, 15));
	private JPanel addBtnPanel = new JPanel();

	private JButton addBtn = new JButton("Add");


	//panels for the "Edit" label + text fields
	private JPanel updateMainPanel = new JPanel(new BorderLayout());
	private JPanel updateLabelPanel = new JPanel(new GridLayout(6, 0, 15, 15));
	private JPanel updateTextPanel = new JPanel(new GridLayout(6, 0, 15, 15));
	private JPanel updateBtnPanel = new JPanel();

	private JButton upBtn = new JButton("Edit");

	//labels for the "Edit" panel
	private JLabel Edit = new JLabel("       EDIT");

	private JLabel updateName = new JLabel("Book title: ");
	private JLabel updateAuthor = new JLabel("Author: ");
	private JLabel updateEdition = new JLabel("Edition year: ");
	private JLabel updateGenre = new JLabel("Book genre: ");

	//"Edit" text fields
	private JTextField updateTName = new JTextField();
	private JTextField updateTAuthor = new JTextField();
	private JTextField updateTEdition = new JTextField();
	private JComboBox<hr.library.book.Genre> updateTypes = new JComboBox<>(hr.library.book.Genre.values());

	//table modifications and setup
	private static String[] columns = {"Book ID", "Book title", "Book author", "Book edition", "Book genre", "Borrowed"};

	private static DefaultTableModel model = new DefaultTableModel(columns, 0);

	private static JTable updateTable = new JTable(model);

	//esthetics
	Border updateBorder = BorderFactory.createLineBorder(Color.gray);
	Border addBorder = BorderFactory.createLineBorder(Color.gray);

	String designatedId = null;

	public AddABookTab() {
		setLayout(new BorderLayout());

		//fills the table upon startup
		fillTable();

		//esthetics
		updateMainPanel.setBorder(updateBorder);
		addMainPanel.setBorder(addBorder);

		//adds all components to the "add" label panel
		addLabelPanel.add(add);
		addLabelPanel.add(id);
		addLabelPanel.add(name);
		addLabelPanel.add(author);
		addLabelPanel.add(edition);
		addLabelPanel.add(addGenre);

		//adds all components to the "add" text field panel
		addTextPanel.add(filler1);
		addTextPanel.add(Tid);
		addTextPanel.add(Tname);
		addTextPanel.add(Tauthor);
		addTextPanel.add(Tedition);
		addTextPanel.add(types);

		addBtnPanel.add(addBtn);

		//adds the layout to the text fields and labels
		addMainPanel.add(addLabelPanel, BorderLayout.WEST);
		addMainPanel.add(addTextPanel, BorderLayout.CENTER);
		addMainPanel.add(addBtnPanel, BorderLayout.SOUTH);

		gridPanelBottomLevel.add(addMainPanel);

		//adds all components to the "Edit" label panel
		updateLabelPanel.add(Edit);
		updateLabelPanel.add(updateName);
		updateLabelPanel.add(updateAuthor);
		updateLabelPanel.add(updateEdition);
		updateLabelPanel.add(updateGenre);
		updateLabelPanel.add(filler3);

		//adds all components to the "Edit" text field panel
		updateTextPanel.add(filler2);
		updateTextPanel.add(updateTName);
		updateTextPanel.add(updateTAuthor);
		updateTextPanel.add(updateTEdition);
		updateTextPanel.add(updateTypes);
		updateTextPanel.add(filler4);

		updateBtnPanel.add(upBtn);


		//adds the layout to the text fields and labels
		updateMainPanel.add(updateLabelPanel, BorderLayout.WEST);
		updateMainPanel.add(updateTextPanel, BorderLayout.CENTER);
		updateMainPanel.add(updateBtnPanel, BorderLayout.SOUTH);

		gridPanelBottomLevel.add(updateMainPanel);

		//adds the bottom level grid panel to the top level one and also adds
		//the table to the top level grid panel
		gridPanelTopLevel.add(gridPanelBottomLevel);

		//when adding a table it must be in the JScrollPane otherwise it wont work
		gridPanelTopLevel.add(new JScrollPane(updateTable));

		//adding the whole thing to the final panel plus adding the JScrollPanel
		//because everything is to big vertically to be fit into a single panel
		add(new JScrollPane(gridPanelTopLevel));


		//creates a listener that fills the Edit text fields when a row is selected
		updateTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int i = updateTable.getSelectedRow();
				//i is the row number
				//number 0 to 4 is the column 
				designatedId = (String) model.getValueAt(i, 0);
				updateTName.setText((String)model.getValueAt(i, 1));
				updateTAuthor.setText((String)model.getValueAt(i, 2));
				updateTEdition.setText((String)model.getValueAt(i, 3));
				updateTypes.setSelectedItem(model.getValueAt(i, 4));

			}
		});


		//updates the table locally when button Edit is pressed
		//locally - does not query the database to fill the table,
		//          instead it just fills in the cells with the text from the fields
		upBtn.addActionListener((event) -> {

			Runnable r = new Runnable() {

				@Override
				public void run() {
					try {
						Book book = new Book(
								updateTName.getText().toString(),
								updateTAuthor.getText().toString(),
								(Genre)updateTypes.getSelectedItem(),
								Integer.parseInt(updateTEdition.getText().toString()),
								Integer.parseInt(designatedId));

						Database.updateBook(book.getId(),
								book.getName(),
								book.getAuthor(),
								book.getEdition(),
								book.getGenre());

						updateTable(book);
						JOptionPane.showMessageDialog(AddABookTab.this, "Book sucessfully updated");

					} catch(Exception e) {
						e.printStackTrace();
						System.out.println("Book could not be updated");
						JOptionPane.showMessageDialog(AddABookTab.this, "Error has occured");
					}
				}
			};

			new Thread(r).run();

		});



		//adds the book to the database and to the table
		addBtn.addActionListener((event) -> {

			Runnable r = new Runnable() {

				@Override
				public void run() {
					try {
						if(Tname.getText().toString().length() == 0 ||
								Tauthor.toString().length() == 0 || 
								Tedition.toString().length() == 0) {
							JOptionPane.showMessageDialog(AddABookTab.this, "All fields have to be filled");
						} else if(Database.getBook(Integer.parseInt(Tid.getText().toString())) != null) {
							JOptionPane.showMessageDialog(AddABookTab.this,  "Book already exists");
						} else if(Database.getBook(Integer.parseInt(Tid.getText().toString())) == null) {
							Database.insertBook(new Book(Tname.getText().toString(), Tauthor.getText().toString(), Genre.valueOf(types.getSelectedItem().toString()),
									Integer.parseInt(Tedition.getText().toString()), Integer.parseInt(Tid.getText().toString())));
							addRow();
							JOptionPane.showMessageDialog(AddABookTab.this, "Book added");
						}
					} catch(Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(AddABookTab.this, "Invalid input");
					}				
				}
			};

			new Thread(r).run();



		});
	}

	//OPTIMIZATION METHODS

	//fills the table each time the book is added and each time when the program starts
	public void fillTable() {
		model = new DefaultTableModel(Database.fetchBooks(), columns);
		updateTable.setModel(model);
	}

	//adds the row locally without querying the database
	public void addRow() {
		DefaultTableModel addModel = (DefaultTableModel)updateTable.getModel();
		String[] data = new String[6];

		for(int i = 0; i < 6; i++) {
			switch(i) {
			case 0:
				data[i] = Tid.getText().toString();
				break;

			case 1:
				data[i] = Tname.getText().toString();
				break;

			case 2:
				data[i] = Tauthor.getText().toString();
				break;

			case 3:
				data[i] = Tedition.getText().toString();
				break;

			case 4:
				data[i] = types.getSelectedItem().toString();
				break;

			case 5:
				data[i] = "false";
				break;
			}
		}	
		addModel.addRow(data);
	}


	//updates the table values when a book is updated by querying the database
	public void updateTable(Book book) {		
		//local iteration of the method, changes the values in the table without
		//querying the database...is more efficient but has problems when a book
		//is accessed through the borrowing terminal because it cannot react to
		//changes in "Borrowed" status
		int i = updateTable.getSelectedRow();
		model.setValueAt(book.getId(), i, 0);
		model.setValueAt(book.getName(), i, 1);
		model.setValueAt(book.getAuthor(), i, 2);
		model.setValueAt(book.getEdition(), i, 3);
		model.setValueAt(book.getGenre(), i, 4);
		model.setValueAt(book.isBorrowed(), i, 5);
	}	

	public static void changeStatus(Book book, String bool) {
		DefaultTableModel change = (DefaultTableModel)updateTable.getModel();

		for(int i = 0; i < change.getRowCount(); i++) {
			Book toChange = new Book((String)model.getValueAt(i, 1),
					(String)model.getValueAt(i, 2),
					Genre.valueOf((String)model.getValueAt(i, 4)),
					Integer.parseInt((String)model.getValueAt(i, 3)),
					Integer.parseInt((String)model.getValueAt(i, 0)),
					(boolean)Boolean.getBoolean((String)model.getValueAt(i, 5)));
			if(book.equals(toChange)) {
				model.setValueAt(bool , i, 5);
				break;
			}
		}
	}
}
