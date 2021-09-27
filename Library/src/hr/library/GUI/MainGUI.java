package hr.library.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import hr.library.database.Database;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName().toString());
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Look and feel error");
		}

		setTitle("MyLibrary");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.3), (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.3));
		setLocationRelativeTo(null); //sets the default window position to the middle of the screen
		//		setIconImage(); TO-DO set window icon to something desirable

		JTabbedPane t = new JTabbedPane();
		t.add("Borrows", new BorrowABookTab());
		t.add("Books", new AddABookTab());
		t.add("Users", new AddAUserTab());
		t.add("Librarians", new AddALibrarianTab()); 
		//		t.add("Active borrow terms", new BorrowingTableTab());
		add(t);

	}

	public static class PasswordWindow extends JFrame {

		private static final long serialVersionUID = 1L;

		private JLabel name = new JLabel("Name: ");
		private JLabel surname = new JLabel("Surname: ");
		private JLabel id = new JLabel("ID: ");
		private JLabel password = new JLabel("Password: ");

		private JTextField Tname = new JTextField();
		private JTextField Tsurname = new JTextField();
		private JTextField Tid = new JTextField();
		private JPasswordField Tpassword = new JPasswordField();

		private JButton btnSignIn = new JButton("Sign in");


		public PasswordWindow() {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName().toString());
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("Look and feel error");
			}
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle("MyLibrary");
			setSize(230, 154);
			setResizable(false);
			setLocationRelativeTo(null);

			JPanel center = new JPanel(new GridLayout(4, 0, 3, 3));
			JPanel west = new JPanel(new GridLayout(4, 0, 3, 3));
			JPanel north = new JPanel(new BorderLayout());
			west.add(name);
			center.add(Tname);
			west.add(surname);
			center.add(Tsurname);
			west.add(id);
			center.add(Tid);
			west.add(password);
			center.add(Tpassword);

			JPanel south = new JPanel(new GridLayout());
			south.add(btnSignIn);

			north.add(center, BorderLayout.CENTER);
			north.add(west, BorderLayout.WEST);

			add(north, BorderLayout.NORTH);
			add(south, BorderLayout.SOUTH);

			btnSignIn.addActionListener((event) -> {

				Runnable r = new Runnable() {

					@Override
					public void run() {
						if(Tname.getText().length() == 0 || Tsurname.getText().length() == 0 || Tid.getText().length() == 0 || Tpassword.getPassword().length == 0) {
							JOptionPane.showMessageDialog(PasswordWindow.this, "All fields must be filled");
						} else if(Database.isLibrarian(Tname.getText().toString(), Tsurname.getText().toString(), Integer.parseInt(Tid.getText().toString()), Tpassword.getPassword()) == true) {
							MainGUI window = new MainGUI();
							window.setVisible(true);
							PasswordWindow.this.dispose();;
						} else
							JOptionPane.showMessageDialog(PasswordWindow.this, "Wrong user credential(s) or password");
					}
				};

				new Thread(r).run();
				
			});

		};
	}
}
