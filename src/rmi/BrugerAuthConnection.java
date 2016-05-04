package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import daos.impl.UserDAO;
import dtos.UserDTO;
import helper.Password;

public class BrugerAuthConnection {
	
	UserDAO userdao;
	Brugeradmin ba;
	final static String DB_URL = "rmi://javabog.dk/brugeradmin";
	
	public BrugerAuthConnection() {
		userdao = new UserDAO();
		try {
			ba = (Brugeradmin) Naming.lookup(DB_URL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public UserDTO hentBruger(String username, String password) {
		if(!isUserInLocalList(username)) {
			
			return userdao.getUser(username);
		} else {
			Bruger b;
			String passwordToStore;
			try {
				b = ba.hentBruger(username, password);
				passwordToStore = Password.getSaltedHash(username+password);
				// user oprettes hvor password er hash af username+password i jakobs database.
				UserDTO user = new UserDTO(b.brugernavn, b.email, b.fornavn,
						b.efternavn, passwordToStore);
				return user;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				System.err.println("password for bruger "+username+" kunne ikke hashes");
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public boolean isUserInLocalList(String username) {
		return (userdao.getUser(username) != null) ? true : false;
	}
	
}
