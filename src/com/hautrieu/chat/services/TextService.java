package com.hautrieu.chat.services;

import java.math.BigInteger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TextService {
	
	public String hashByMD5(String initial) {
		
		try {			
			
			MessageDigest getMD5Instance = MessageDigest.getInstance("MD5");			
			byte[] messageDigest = getMD5Instance.digest(initial.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);
			String hashText = no.toString(16);
			
			while (hashText.length() < 32) {
				hashText = "0" + hashText;
			}
			return hashText;
			
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
