package com.hautrieu.chat.domains;

import java.awt.Desktop;
import java.io.*;
import java.nio.file.*;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.data.InMemoryDataStorage;

public class InMemoryFile extends BaseEntity {

	private String extension;
	
	public InMemoryFile(String extension) {
		this.extension = extension;
	}
	
	public InMemoryFile upload(String extension, Path source) {
		DataStorage storage = InMemoryDataStorage.getInstance();
        InMemoryFile targetFile = new InMemoryFile(extension);

		try {
			
            File folder = new File("src/files");
            
            if (!folder.exists()) {
                folder.mkdirs();
            }          
            
            File file = new File(folder, targetFile.getFullFileName());
            file.createNewFile();
            
            Files.copy(source, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                      
            storage.getFiles().add(targetFile);
            System.out.println("File uploaded successfully to: " + file.getAbsolutePath()); 
            return targetFile;
        
		} catch (IOException e) {
            e.printStackTrace();
        }
		
		return null;
	}
	
	public void open() {
		try {
			
            File file = new File("src/files" + "/" + getFullFileName());
            Path path = file.toPath();
            String absolutePath = path.toAbsolutePath().toString();
            Desktop.getDesktop().open(new File(absolutePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public boolean delete() {
		DataStorage storage = InMemoryDataStorage.getInstance();
		File file = new File("src/files" + "/" + getFullFileName());
		
		if (!file.exists()) {
            System.out.println("File does not exist!");
            return false;
        }

        if (!file.isFile()) {
            System.out.println("Not a valid file!");
            return false;
        }

        if(file.delete()) {
        	storage.getFiles().removeFirst(temporaryFile -> temporaryFile.getFullFileName() == getFullFileName());
        	return true;
        }
        
        return false;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public String getFullFileName() {
		return getId() + "." + extension;
	}
}
