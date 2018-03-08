import java.io.FileReader;
import java.io.FileWriter;

public class FileExam {

	public static void main(String[] args) {
		try {//8,9라인 : datar.txt, dataw.txt 파일이 없음
			FileReader fr = new FileReader("datar.txt");
			FileWriter fw = new FileWriter("dataw.txt");
			int c;
			while((c = fr.read())!= -1) {
				fw.write(c);
			}
		}
		
		catch(Exception e) {
			System.out.println(e.toString());
		}

	}

}

