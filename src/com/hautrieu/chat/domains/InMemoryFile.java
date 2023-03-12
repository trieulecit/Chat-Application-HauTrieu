package com.hautrieu.chat.domains;

import java.awt.Desktop;
import java.io.*;
import java.nio.file.*;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.data.InMemoryDataStorage;

public class InMemoryFile extends BaseEntity {

	private String extension;
	
	public InMemoryFile(String extensionAsInput) {
		extension = extensionAsInput;
	}
	
	public InMemoryFile upload(String extension, Path source) {
		
		DataStorage storage = InMemoryDataStorage.getInstance();
        InMemoryFile targetFile = new InMemoryFile(extension);

		try {
			
            File folder = new File("src/files");
            
            createFileOrFolderIfNotExist(folder);
            
            File systemFile = new File(folder, targetFile.getFullFileName());
            systemFile.createNewFile();
            
            Files.copy(source, systemFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                      
            storage.getFiles().add(targetFile);
            System.out.println("File uploaded successfully to: " + systemFile.getAbsolutePath()); 
            return targetFile;
        
		} catch (IOException e) {
            e.printStackTrace();
        }		
		return null;
	}
	
	public void open() {
		try {
			
            File file = getSystemFile();
            Path path = file.toPath();
            String absolutePath = path.toAbsolutePath().toString();
            Desktop.getDesktop().open(new File(absolutePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public boolean delete() {
		DataStorage storage = InMemoryDataStorage.getInstance();
		File systemFile = getSystemFile();
		
		checkSystemFileExist(systemFile);
        checkNormalSystemFile(systemFile);

        if(systemFile.delete()) {
        	storage.getFiles().removeFirst(temporaryFile -> temporaryFile.getFullFileName() == getFullFileName());
        	return true;
        }
        return false;
	}
	
	private void createFileOrFolderIfNotExist(File target) {
		if (!target.exists()) {
            target.mkdirs();
        }
	}
	
	private boolean checkSystemFileExist(File file) {
		return file.exists();
	}
	
	private boolean checkNormalSystemFile(File file) {
		return file.isFile();
	}
	
	private File getSystemFile() {
		return new File("src/files/" + getFullFileName());
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extensionAsInput) {
		extension = extensionAsInput;
	}
	
	public String getFullFileName() {
		return getId() + "." + extension;
	}
}
