package fileIOTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class CopyFileTestMain {
	public static void main(String[] args) {
		
	}
	
	public static boolean cpFile(File source, File target) throws IOException {
		if (source.isDirectory()) {
			File newDir = new File(String.format("%s\\%s", target.getAbsolutePath(), source.getName()));
			if (!newDir.exists()) {
				newDir.mkdirs();
			}
			File[] files = source.listFiles();
			for (int i = 0; i < files.length; i++) {
				cpFile(files[i], newDir);
			}
			return true;
		}

		try {
			Path from = FileSystems.getDefault().getPath(source.getAbsolutePath());
			Path to = FileSystems.getDefault().getPath(target.getAbsolutePath());
			Files.copy(from, to.resolve(from.getFileName()));
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
