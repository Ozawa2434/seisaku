import java.util.*;

//ファイル系
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Dataclear {
    static int point = 0;
    static int select = 0;

    //時間を空けるメソッド
	static void sleeptime(int time) {
		try {
			Thread.sleep(time); //〇秒待つ
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
    public void run() {
        Scanner stdIn = new Scanner(System.in);
        System.out.println("データを初期化しますか？");
        do {
            try {
                System.out.print("0|はい 1|いいえ:");
                select = stdIn.nextInt();
            }catch(java.util.InputMismatchException e){
                System.out.println("エラーが発生しました。プログラムを終了します。");
                System.exit(1);
            }
            if (select == 0) {
                try {
                    FileWriter fw = new FileWriter("./txt/data.txt");//data.txtを読み込む
                    fw.write("0");//0に書き換える
                    fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    FileWriter fw = new FileWriter("./txt/unlockdata.txt");//unlockdata.txtを読み込む
                    fw.write("0");//0を書き込む
                    fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println();
                sleeptime(500);
                System.out.print(".");
                sleeptime(500);
                System.out.print(".");
                sleeptime(500);
                System.out.print(".");
                sleeptime(800);
                System.out.println();
                System.out.println("データを初期化しました");
                sleeptime(2000);
            }
        } while (select != 0 && select != 1);
    }
}
