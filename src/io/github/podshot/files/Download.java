package io.github.podshot.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class Download {
	
	public static void downloadFile(String link, String dir) throws IOException {
		URL url = new URL(link);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(dir);
		
		byte[] b = new byte[2048];
		int length;
		
		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}
		
		is.close();
		os.close();
	}

}
