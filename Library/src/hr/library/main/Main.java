package hr.library.main;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import hr.library.GUI.MainGUI.PasswordWindow;
import hr.library.database.Database;
import hr.library.librarians.Librarian;

public class Main {

	public static void main(String[] args) {
		Database.createDatabase();
		Database.createTables();	
		
		if(Database.countLibrarians() > 1 && Database.getLibrarian(0) != null) {
			Database.deleteLibrarian(0);
		}
		if(Database.getLibrarian(0) == null && Database.countLibrarians() == 0) {
			char[] lozinka = {'0'};
			Database.insertLibrarian(new Librarian("0", "0", 0, lozinka));
			lozinka = null;
		}
		
		try {
			SwingUtilities.invokeAndWait(() -> {
				PasswordWindow pass = new PasswordWindow();
				pass.setVisible(true);
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
