
package daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import daos.interfaces.IUserDAO;
import dtos.UserDTO;
import helper.DataInit;
import helper.Password;

public class UserDAO implements IUserDAO {

	private List<UserDTO> users;

	private static final String DIR = "data/users";

	public UserDAO() {
		try {
			FileInputStream fin = new FileInputStream(new File(DIR + "/users.ser"));
			ObjectInputStream oin = new ObjectInputStream(fin);
			users = (ArrayList<UserDTO>) oin.readObject();
			oin.close();
			fin.close();
			System.out.println("users list loaded from file.");
		} catch (IOException | ClassNotFoundException e) {
			users = new ArrayList<>();
			System.out.println("No User list found. A new list has been created.");
			//e.printStackTrace();

			// creates the directory
			File dir = new File(DIR);
			dir.mkdirs();
		}
		
	}

	@Override
	public boolean updateUser(UserDTO oldUser, UserDTO newUser) {
		
		UserDTO user = getUser(oldUser.getUsername());
		if(user != null){
			int userNr = users.indexOf(user);
			users.remove(userNr);
			users.add(userNr, newUser);
		
			if(getUser(newUser.getUsername()) != null){
				updateUserFile();
				return true;
				
			}
		}
		return false;
	}

	@Override
	public boolean createUser(UserDTO user) {
		if (getUser(user.getUsername()) != null)
			return false;
		try {
			String hashedPassword = Password.getSaltedHash(user.getPassword());
			user.setPassword(hashedPassword);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		users.add(user);
		System.out.println("User added to list");
		updateUserFile();
		return true;
	}

	@Override
	public boolean deleteUser(UserDTO user) {
		if(users.remove(user)){
			updateUserFile();
			return true;
		}
		return false;
	}

	@Override
	public UserDTO getUser(UserDTO user) {
		for(UserDTO u : users){
			if(u.getUsername().equals(user.getUsername())){
				return u;
				
			}
			
		}
		return null;
	}

	private void updateUserFile() {
		try {
			FileOutputStream fout = new FileOutputStream(DIR + "/users.ser");
			ObjectOutputStream oout = new ObjectOutputStream(fout);
			oout.writeObject(users);
			oout.close();
			fout.close();
			System.out.println("UserList was saved to disk.");
		} catch (IOException e) {
			System.out.println("UserList could not be saved to disk.");
			e.printStackTrace();

		}

	}

	@Override
	public boolean authUser(String username, String password) throws Exception {
		for (UserDTO u : users) {
			if (u.getUsername().equals(username)) {
				System.out.println("User " + username + " found.");
				if (Password.check(password,u.getPassword())) {
					try {
						System.out.println("User " + username + " found. And pasword matches");
						return Password.check(password, u.getPassword());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}

	@Override
	public UserDTO getUser(String name) {
		for (UserDTO u : users) {
			if (u.getUsername().equals(name))
				return u;
		}
		return null;
	}

}
