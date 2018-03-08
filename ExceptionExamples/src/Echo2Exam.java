import java.io.IOException;
import java.io.InputStream;

public class Echo2Exam {

	public static void main(String[] args) {
		InputStream is = System.in;
		try {
				while(true) {
				int i = is.read();
				if(i == -1) break;
				System.out.print((char)i);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
			//printStackTrace()는 예외가 발생하기까지의 메소드 호출순서를 보여줌
		}
		
	}
}
