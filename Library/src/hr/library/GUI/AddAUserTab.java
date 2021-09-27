package hr.library.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import hr.library.database.Database;
import hr.library.users.User;

public class AddAUserTab extends JPanel{

	private static final long serialVersionUID = 1L;

	private JPanel gridPanelBottomLevel = new JPanel(new GridLayout(2, 0, 15, 15));
	private JPanel gridPanelTopLevel = new JPanel(new GridLayout(2, 0, 15, 15));

	private JLabel filler1 = new JLabel();
	private JLabel filler2 = new JLabel();
	private JLabel filler3 = new JLabel();
	private JLabel filler5 = new JLabel();
	private JLabel filler6 = new JLabel();
	private JLabel filler7 = new JLabel();
	private JLabel filler8 = new JLabel();
	private JLabel filler9 = new JLabel();

	private JLabel addLabel = new JLabel("       ADD");
	private JLabel updateLabel = new JLabel("       EDIT");

	private JLabel addId = new JLabel("User ID: ");
	private JLabel addName = new JLabel("User name: ");
	private JLabel addSurname = new JLabel("User surname: ");
	private JTextField tAddId = new JTextField();
	private  JTextField tAddName = new JTextField();
	private JTextField tAddSurname = new JTextField();

	private JPanel addLPanel = new JPanel(new GridLayout(5, 0, 15, 15));
	private JPanel addTPanel = new JPanel(new GridLayout(5, 0, 15, 15));
	private JPanel addMPanel = new JPanel(new BorderLayout());
	private JPanel addBtnPanel = new JPanel();

	private JLabel updateName = new JLabel("User name: ");
	private JLabel updateSurname = new JLabel("User surname: ");
	private JTextField tUpdateName = new JTextField();
	private JTextField tUpdateSurname = new JTextField();

	private JPanel updateLPanel = new JPanel(new GridLayout(5, 0, 15, 15));
	private JPanel updateTPanel = new JPanel(new GridLayout(5, 0, 15, 15));
	private JPanel updateMPanel = new JPanel(new BorderLayout());
	private JPanel updateBtnPanel = new JPanel();

	private JButton addBtn = new JButton("Add user");
	private JButton updateBtn = new JButton("Edit user");

	private static String[] columns = {"User ID", "User name", "User surname", "Books borrowed"};

	private static DefaultTableModel model = new DefaultTableModel(Database.fetchUsers(), columns);

	private static JTable table = new JTable(model);

	String designatedId = null;

	Border addBorder = BorderFactory.createLineBorder(Color.gray);
	Border updateBorder = BorderFactory.createLineBorder(Color.gray);

	public AddAUserTab() {
		setLayout(new BorderLayout());
		addLPanel.add(addLabel);
		addLPanel.add(addId);
		addLPanel.add(addName);
		addLPanel.add(addSurname);
		addLPanel.add(filler1);

		addTPanel.add(filler2);
		addTPanel.add(tAddId);
		addTPanel.add(tAddName);
		addTPanel.add(tAddSurname);
		addTPanel.add(filler3);

		addBtnPanel.add(addBtn);

		addMPanel.add(addLPanel, BorderLayout.WEST);
		addMPanel.add(addTPanel, BorderLayout.CENTER);
		addMPanel.add(addBtnPanel, BorderLayout.SOUTH);
		addMPanel.setBorder(addBorder);

		updateLPanel.add(updateLabel);
		updateLPanel.add(updateName);
		updateLPanel.add(updateSurname);
		updateLPanel.add(filler5);
		updateLPanel.add(filler6);

		updateTPanel.add(filler7);
		updateTPanel.add(tUpdateName);
		updateTPanel.add(tUpdateSurname);
		updateTPanel.add(filler8);
		updateTPanel.add(filler9);

		updateBtnPanel.add(updateBtn);

		updateMPanel.add(updateLPanel, BorderLayout.WEST);
		updateMPanel.add(updateTPanel, BorderLayout.CENTER);
		updateMPanel.add(updateBtnPanel, BorderLayout.SOUTH);
		updateMPanel.setBorder(updateBorder);

		gridPanelBottomLevel.add(addMPanel);
		gridPanelBottomLevel.add(updateMPanel);

		gridPanelTopLevel.add(gridPanelBottomLevel);
		gridPanelTopLevel.add(new JScrollPane(table));

		add(new JScrollPane(gridPanelTopLevel));


		table.getSelectionModel().addListSelectionListener(e -> {
			int i = table.getSelectedRow();
			//i is the row number
			//number 0 to 2 is the column
			designatedId =  (String) model.getValueAt(i, 0);
			tUpdateName.setText((String)model.getValueAt(i, 1));
			tUpdateSurname.setText((String)model.getValueAt(i, 2));
		});


		addBtn.addActionListener(a -> {

			Runnable r = new Runnable() {

				@Override
				public void run() {
					try {
						if(tAddName.getText().length() == 0 || tAddSurname.getText().length() == 0 || tAddId.getText().length() == 0) {
							JOptionPane.showMessageDialog(AddAUserTab.this, "None of the fields can stay empty");
						} else if (Database.getUser(Integer.parseInt(tAddId.getText().toString())) != null) {
							JOptionPane.showMessageDialog(AddAUserTab.this, "User already exists");
						} else if (Database.getUser(Integer.parseInt(tAddId.getText().toString())) == null) { 
							Database.insertUser(new User(tAddName.getText().toString(), tAddSurname.getText().toString(), Integer.parseInt(tAddId.getText().toString())));
							JOptionPane.showMessageDialog(AddAUserTab.this, "User added");
							addRow(new User(tAddName.getText().toString(),
									tAddSurname.getText().toString(),
									Integer.parseInt(tAddId.getText().toString())));
						}
					}	catch(Exception e) {
						e.printStackTrace();
					}
				}
			};
			
			new Thread(r).run();
			
		});

		updateBtn.addActionListener(a -> {
			
			Runnable r = new Runnable() {
				
				@Override
				public void run() {
					try {
						User user = new User(
								tUpdateName.getText().toString(),
								tUpdateSurname.getText().toString(),
								Integer.parseInt(designatedId));

						if(user != null) {
							Database.updateUser(user.getId(),
									user.getName(),
									user.getSurname());

							updateTable(user);
							JOptionPane.showMessageDialog(AddAUserTab.this, "User data updated");
						}
					} catch(Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(AddAUserTab.this, "Data could not be updated");
					}
				}
			};
			
			new Thread(r).run();
			
		});

	}


	public void updateTable(User user) {
		int i = table.getSelectedRow();
		model.setValueAt(user.getId(), i, 0);
		model.setValueAt(user.getName(), i, 1);
		model.setValueAt(user.getSurname(), i, 2);
		model.setValueAt(user.getNofBooks(), i, 3);
	}


	public static void changeStatus(User user, boolean add) {
		DefaultTableModel change = (DefaultTableModel) table.getModel();
		
		for(int i = 0; i < change.getRowCount(); i++) {
			User toChange = new User((String)model.getValueAt(i, 1),
					(String)model.getValueAt(i, 2),
					Integer.parseInt((String)model.getValueAt(i, 3)),
					Integer.parseInt((String)model.getValueAt(i, 0)));
			if(user.equals(toChange) && add == true) {
				model.setValueAt(String.valueOf(toChange.getNofBooks() + 1), i, 3);
				break;
			} else if(user.equals(toChange) && add == false) {
				model.setValueAt(String.valueOf(toChange.getNofBooks() - 1), i, 3);
				break;
			}
		}
	}

	public void addRow(User user) {
		String[] data = new String[model.getColumnCount()];

		for(int i = 0; i < model.getColumnCount(); i++) {
			switch(i) {
			case 0:
				Integer id = user.getId();
				data[i] = id.toString();
				break;
			case 1:
				data[i] = user.getName();
				break;
			case 2:
				data[i] = user.getSurname();
				break;
			case 3:
				Integer noOfBooks = user.getNofBooks();
				data[i] = noOfBooks.toString();
				break;
			}
		}
		model.addRow(data);
	}

}
