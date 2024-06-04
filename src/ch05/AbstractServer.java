package ch05;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

// 상속에 활용
public abstract class AbstractServer {
	
	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader readerStream;
	private PrintWriter writerStream;
	private FileReader chatStream;
	private BufferedReader chatReader;
	public String message;
	protected NateOn nate;
	private Scanner scanner;
	
	// set 메서드
	// 메서드 의존 주입(멤버 변수에 참조 변수 할당)
	protected void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	// 메서드 의존 주입(멤버 변수에 참조 변수 할당)
	protected void setSocket(Socket socket) {
		this.socket = socket;
	}
	// get 메서드
	protected ServerSocket getServerSocket() {
		return serverSocket;
	}
	public Socket getSocket () {
		return socket;
	}
	// 실행에 흐름이 필요하다.(순서가 중요)
	public final void run() {
		// 1. 서버 셋팅 - 포트 번호 할당
		try {
			setupServer();
			connection();
			setupStream();
			startService(); // 내부적으로 while 동작
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("cleanup()");
			cleanup();
		}
	}
	
	// 1. 포트 번호 할당(구현 클래스에서 직접 설계)
	protected abstract void setupServer() throws IOException;
	
	// 2. 클라이언트 연결 대기 실행 ( 구현 클래스)
	protected abstract void connection() throws IOException;
	
	// 3. 스트림 초기화 (연결된 소켓에서 스트림을 뽑아야 함) - 여기서 함(private)
	private void setupStream() throws IOException {
		readerStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writerStream = new PrintWriter(socket.getOutputStream(), true);
		chatStream = new FileReader("chat.txt");
		chatReader = new BufferedReader(chatStream);
	}
	// 4. 서비스 시작
	private void startService() {
		Thread readThread = createReadThread();
		
				
		readThread.start();
		
		try {
			readThread.join();
			// main 스레드 잠깐 기다려
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// 캡슐화
	private Thread createReadThread() {
		return new Thread(() -> {
			try {
				String msg;
				// scanner.nextLine(); <--- 무한 대기(사용자가 콘솔에 값 입력 까지 대기)
				// 코드 ...
				while( (msg = readerStream.readLine()) != null ) {
					// 서버측 콘솔에 출력
					System.out.println("client 측 msg : " + msg);
					message = msg;
					nate.getTextarea().append("상대방 : " + msg + "\n");
					nate.repaint();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		});
	}
	
	// 텍스트 에디에 글작성하고 옆에 보내기 버튼을 누러면 --> 클라이언트로 보내자 
	// 메서드 호출 write.println(); 
	
	public void createWriteThread() {
				writerStream.println(nate.getText());
				nate.setText("");
	}
	
	// 캡슐화 - 소켓 자원 종료
	private void cleanup() {
		try {
			if(socket != null) {
				socket.close();
			}
			
			if(serverSocket != null) {
				serverSocket.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
