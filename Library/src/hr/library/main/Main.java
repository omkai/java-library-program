package hr.library.main;

import hr.library.database.Database;
import hr.library.gui.MainGUI.PasswordWindow;
import hr.library.librarians.Librarian;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class  Main {

	/* TOD0
	* MAKE THE PREPARED STATEMENT SECURE WITH PARAMETERIZATION
	* https://www.javatpoint.com/PreparedStatement-interface
	* CLOSE THE CONNECTIONS ON THE DATABASE methods
	* */


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
