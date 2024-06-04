package ch05;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NateOn extends JFrame{
	
	private JButton button1;
	private JButton button2;
	private JPanel jp;
	private JTextField textfield;
	private JTextArea textarea;
	private JLabel background;
	private MyThreadServer myThreadServer;
	private BufferedReader keyboardReader;
	private NateOn nate = this;
	private boolean server;
	

	public JTextArea getTextarea() {
		return textarea;
	}

	public NateOn() {
		getInitData();
		getLayoutData();
		myThreadServer = new MyThreadServer(nate);
		myThreadServer.run();
	}
	
	public String getText() {
		return textfield.getText();
	}
	public void setText(String text) {
		this.textfield.setText(text);
	}
	public void getInitData() {
		keyboardReader = new BufferedReader(new InputStreamReader(System.in));
		background = new JLabel(new ImageIcon("images/kakao.png"));
		setContentPane(background);
		setSize(500,840);
		setTitle("카카오톡");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		textfield = new JTextField();
		textfield.setBorder(null);
		textarea = new JTextArea();
		jp = new JPanel();
		textfield.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textarea.append("나 : " + textfield.getText() + "\n");
				myThreadServer.createWriteThread();
			}
		});
	}
	
	public void getLayoutData() {
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
		add(textfield);
		add(textarea);
		textarea.setEditable(false);
		textarea.setSize(400,620);
		textarea.setLocation(40, 100);
		textfield.setSize(400,40);
		textfield.setLocation(40, 750);
	}
	
	public static void main(String[] args) {
		new NateOn();
	}
	
}
