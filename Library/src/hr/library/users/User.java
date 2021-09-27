package hr.library.users;

public class User {

	private String name;
	private String surname;
	private int noOfBooks;
	private int id;
	
	public User(String name, String surname, int id) {
		this.name = name;
		this.surname = surname;
		this.noOfBooks = 0;
		this.id = id;
	}
	
	public User(String name, String surname, int noOfBooks, int id) {
		this.name = name;
		this.surname = surname; 
		this.noOfBooks = noOfBooks;
		this.id = id;
	}
 	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getNofBooks() {
		return noOfBooks;
	}

	public void setNofBooks(int noOfBooks) {
		this.noOfBooks = noOfBooks;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", surname=" + surname + ", noOfBooks=" + noOfBooks + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + noOfBooks;
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (noOfBooks != other.noOfBooks)
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}
}
