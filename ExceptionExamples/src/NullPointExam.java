
public class NullPointExam {

	public static void main(String[] args) {
		String str = null;
		try {
			//str.length()가 null이라고 해서 0은 아님
			System.out.println("문자열: " + str.length());
		}
		catch(Exception e) {
			//NullPointException
			System.out.println("e.toString() : " + e.toString());
		}
		finally {
			System.out.println("무조건 실행");	
		}
	}

}
