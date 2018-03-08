
public class ThrowExam {
	
	public static void arrayMethod() throws ArrayIndexOutOfBoundsException {
		String[] irum = new String[3];
		irum[3] = "홍길동"; //
	}	//배열 예외가 발생해서 에러를 메소드 실행한곳으로 넘겨줌
	
	public static void main(String[] args) {
		try {
			ThrowExam ts = new ThrowExam();
			arrayMethod(); //
	
		}
		catch(Exception e) {
			//ArrayIndexOutOfBoundsException인데  Exception은 상위호환임
			System.out.println("배열 예외 발생");
		}
	}

}
