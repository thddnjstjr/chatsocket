package project;

import java.awt.Button;
import java.awt.TextArea;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JLabel;

import lombok.Data;

@Data
public class Kakao extends JFrame {

	private JLabel background1;
	private TextField chat;
	private TextField search;
	private TextArea textarea;
	private Button send;
	private Button join;
	private int port;

	private void initData() {
		port = 5000;
	}

	public void creatServer() {
		Server server = new Server(this, port);
		port++;
	}
	

}
