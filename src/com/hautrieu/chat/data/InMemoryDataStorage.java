package com.hautrieu.chat.data;

public class InMemoryDataStorage extends DataStorage {
	private static InMemoryDataStorage storage;

    private InMemoryDataStorage() {
    	super();
    }

    public static InMemoryDataStorage getInstance() {
        if (storage == null) {
            storage = new InMemoryDataStorage();
        }
        return storage;
    }

}