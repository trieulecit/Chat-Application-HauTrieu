package com.hautrieu.chat.domains;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class File {
	
	private String name;	
	private String extension;
	
	public void upload() {
		
	}
	public void open() {
		
	}
	public void delete() {
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public File(String name, String extension) {
		this.name = name;
		this.extension = extension;
	}
}
