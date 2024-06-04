package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.Data;

@Data
public class Server {
	
	private ServerSocket serversocket;
	private Socket socket;
	private Kakao kakao;
	private BufferedReader msgin;
	private PrintWriter msgout;
	
	public Server(Kakao kakao,int port) {
		try {
			serversocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.kakao = kakao;
	}
	
	
}
