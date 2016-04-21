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

public class UserDAO implements IUserDAO {

	private List<UserDTO> users;

	public UserDAO() {
		try {
			FileInputStream fin = new FileInputStream(new File("WebContent/test/users.ser"));
			ObjectInputStream oin = new ObjectInputStream(fin);
			users = (ArrayList<UserDTO>) oin.readObject();
			oin.close();
			fin.close();
			System.out.println("users list loaded from file.");
		} catch (IOException | ClassNotFoundException e) {
			users = new ArrayList<>();
			System.out.println("No list found. A new list has been created.");
			e.printStackTrace();
		}
	}

	@Override
	public boolean updateUser(UserDTO user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createUser(UserDTO user) {
		if(getUser(user.getUsername()) != null) return false;
		users.add(user);
		System.out.println("User added to list");
		try {
			FileOutputStream fout = new FileOutputStream("WebContent/test/users.ser");
			ObjectOutputStream oout = new ObjectOutputStream(fout);
			oout.writeObject(users);
			oout.close();
			fout.close();
			System.out.println("List was saved to disk.");
		} catch (IOException e) {
			System.out.println("List could not be saved to disk.");
			e.printStackTrace();

		}
		return true;
	}

	@Override
	public boolean deleteUser(UserDTO user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getUser(UserDTO user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean authUser(String username, String password) {
		for(UserDTO u : users) {
			if(u.getUsername().equals(username)) {
				System.out.println("User "+username+" found.");
				if(u.getPassword().equals(password)){
					System.out.println("Password matches.");
					return true;
				} else return false;
			}
		} return false;
	}


	@Override
	public UserDTO getUser(String name) {
		for(UserDTO u : users) {
			if(u.getUsername().equals(name)) return u;
		}
		return null;
	}


}
