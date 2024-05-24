package ch03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFile {

	public static void main(String[] args) {

		// 준비물 
		// 1. 서버 소켓이 필요하다.
		// 2. 포트 번호가 필요하다. (0 ~ 65535 까지 존재)
		// 2.1 잘 알려진 포트 번호 : 주로 시스템 레벨 - 0 ~ 1023까지 사용
		// 2.2 등록 가능한 포트 : 1024 ~ 49151까지 등록가능
		// 2.3 동적/사설 포트번호 - 그 외 임시 사용을 위해 할당 된다.
		
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(5001);
			System.out.println("서버를 시작 합니다 - 포트번호 : 5001 ");
			socket = serverSocket.accept(); // while -->
			System.out.println(">>> 클라이언트가 연결 하였습니다. <<<");
			
			// 대상은 소켓이다. ( input stream ) 작업
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			// 1. 클라이언트에서 먼저 보낸 데이터를 읽는다.
			// 실제 데이터를 읽는 행위가 필요 하다.
			String message = reader.readLine();
			System.out.println("클라이언트 측 메세지 전달 받음 : " + message);
						
			// 2. 클라이언트 측으로 데이터를 보낸다.
			// 대상은 소켓이다. ( output stream) 작업
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true); // auto flush
			writer.println("응~어쩔티비~저쩔티비~안물티비~지금화났죠?죽이고싶죠?내가사는곳모르죠?응못죽이죠?어또빡치죠?아무것도못하죠?냬~알걨섑니댸~아무도안물~물어본사람?응근데어쩔티비죠?"); // 줄바꿈 포함 메서드 안녕 ---> 안녕\n
			
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if(socket != null ) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(serverSocket != null ) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
}
