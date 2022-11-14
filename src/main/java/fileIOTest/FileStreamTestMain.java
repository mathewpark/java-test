package fileIOTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStreamTestMain {
	public static void main(String[] args) throws IOException {
		// create a new output stream
		OutputStream os = new FileOutputStream("file/test.txt");

		// craete a new input stream
		InputStream is = new FileInputStream("file/test.txt");

		// write something
		os.write('A');

		// flush the stream but it does nothing
		os.flush();

		// write something else
		os.write('B');

		// read what we wrote
		System.out.println("" + is.available());
	}
}