/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brugerautorisation;

import brugerautorisation.data.Bruger;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;


/**
 *
 * @author j
 */
public class Diverse {

/**
	* Tager et vilkårligt objekt og laver en streng ud af dets public variabler
	* @param obj Objektet
	* @return En streng med alle dets public variabler
	*/
	public static String toString(Object obj) {
		StringBuilder sb = new StringBuilder();
		Class k = obj.getClass();
		sb.append(k.getSimpleName()).append(':');
		for (Field felt : k.getFields()) try {
			Object værdi = felt.get(obj);
			sb.append(' ').append(felt.getName()).append('=').append('"').append(String.valueOf(værdi)).append('"');
		} catch (Exception e) { e.printStackTrace(); }
		return sb.toString();
	}


}
