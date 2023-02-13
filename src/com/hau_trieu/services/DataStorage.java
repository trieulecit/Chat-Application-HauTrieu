package com.hau_trieu.chat.services;

public class DataStorage {
	private static DataStorage instance = null;
	
	
	
	
	public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

}
