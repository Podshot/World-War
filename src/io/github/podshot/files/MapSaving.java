package io.github.podshot.files;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class MapSaving {

	public static void save(HashMap<String, String> map, String path) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(map);
			oos.flush();
			oos.close();
			//Handle I/O exceptions
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static HashMap<String, String> load(String path) {
		Object result = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
			result = ois.readObject();
			//you can feel free to cast result to HashMap<String, Integer> if you know there's that HashMap in the file
			//return (HashMap<String, String>)result;
			ois.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return (HashMap<String, String>) result;
	}



}
