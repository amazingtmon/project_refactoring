package com.client;

import java.io.File;
import java.io.IOException;

import com.file.ClientAddress;
import com.file.FileSocket;

public class ClientMain {

	public static void main(String[] args) {
		try {
			//클라이언트 소켓 생성.
			ClientAddress chatAddress = new ClientAddress("localhost", 9100);
			ClientAddress fileAddress = new ClientAddress("localhost", 9101);
			FileSocket file = new FileSocket(fileAddress, new File("D://test"));
			ClientSocket client = new ClientSocket(chatAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
