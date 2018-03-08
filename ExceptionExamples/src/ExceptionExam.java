
public class ExceptionExam {

	public static void main(String[] args) {
		try {
			int data[] = new int[] {10,20,30,40,50};
			for(int i=0; i<= data.length; i++)
				System.out.println("data[" + i + "] = " + data[i]);
		} catch(NullPointerException e) {
			//Exception이긴 하지만 NullPointerException은 아님
			System.out.println("e.toString() : " + e.toString());
		} catch (Exception e) {
			//Exception은 모든 Exception클래스의 상위 클래스
			//ArrayIndexOutOfBoundsException
			System.out.println("위 경우 이외의 에러 처리");
		}
	}
}
