package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import daos.Handler;
import daos.impl.UserDAO;
import dtos.UserDTO;
import helper.Password;
import helper.SessionMap;

public class BrugerAuthConnection {
	
	UserDAO userdao;
	Brugeradmin ba;
	SessionMap sessions = Handler.sessions;
	final static String DB_URL = "rmi://javabog.dk/brugeradmin";
	
	public BrugerAuthConnection() {
		userdao = Handler.userdao;
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
		String sessionKey = sessions.generateSessionKey();
		
		if(isUserInLocalList(username)) {
			sessions.addSession(username, sessionKey);
			return userdao.getUser(username); //skal der laves session?
		} else {
			Bruger b;
			String passwordToStore;
			try {
				b = ba.hentBruger(username, password);
				passwordToStore = Password.getSaltedHash(username+password); // password der gemmes er hash af username og password fra jakobs database
				UserDTO user = new UserDTO(b.brugernavn, b.email, b.fornavn,
						b.efternavn, passwordToStore);
				sessions.addSession(username, sessionKey); // session key is put into map with username
				userdao.createUser(user); // user added to servlet user list
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
