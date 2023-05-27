import java.util.*;

//ファイル系
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class NumberGame {
    static int point = 0, getpoint = 0;

    //時間を空けるメソッド
	static void sleeptime(int time) {
		try {
			Thread.sleep(time); //〇秒待つ
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//ポイントを付与させるメソッド
	static void pointchanger(int getpoint_dec) {
        int getpoint_cal = (11 - getpoint_dec);
        if (getpoint_cal == 1) { //10回目に当てた場合
			System.out.println("ポイント:" + point + " +" + 8);
			point++;
			sleeptime(1000);
			for (int i = 7; i > 0; i--, point++) {
				System.out.println("ポイント:" + point + " +" + i);
				sleeptime(50);
			}
        }else{
		    getpoint = 4 * getpoint_cal * getpoint_cal;
		    System.out.println("ポイント:" + point + " +" + getpoint);
		    point++;
		    sleeptime(1000);
		    for (int i = getpoint-1; i > 0; i--, point++) {
		    	System.out.println("ポイント:" + point + " +" + i);
		    	sleeptime(50);
		    }
        }
		System.out.println("ポイント:" + point +"\n");
		String a = point + "";

		// 最終的なポイント数を書き込む
		try {
            FileWriter fw = new FileWriter("./txt/data.txt");
            fw.write(a);
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}

       // メイン
       public void run(){
		Random rand = new Random();
		Scanner stdIn = new Scanner(System.in);
        int retry = 0;

        // data.txtに入ってる数値を読み込む(ゲームが始まった時にのみ処理)
		try {
			String str;
			BufferedReader bfReader = new BufferedReader(new InputStreamReader(new FileInputStream("./txt/data.txt"), Charset.forName("UTF-8")));

			//ファイルの一行目を読み取る
			str = bfReader.readLine();
			point = Integer.parseInt(str);
			bfReader.close();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}


        System.out.println("--数当てゲーム--");
        sleeptime(1000);

        do {
		    int no = rand.nextInt(1000) + 1;	// 当てるべき数：1～1000の乱数として生成

		    int number_input = 0; // プレーヤが入力した数
            int count = 0; // 回数のカウント、リセット
		    do {
                count++;
                System.out.println("\n10回以内に1～1000の数を当ててください。");
                try {
                    System.out.print(count + "回目:");
		    	    number_input = stdIn.nextInt();
                }catch(java.util.InputMismatchException e){
                    System.out.println("エラーが発生しました。プログラムを終了します。");
                    System.exit(1);
                }

		    	if (number_input > no)
		    		System.out.println(number_input + "より" + "小さな" +"数だよ。");
		    	else if (number_input < no)
		    		System.out.println(number_input + "より" + "大きな" +"数だよ。");
                
                if (count == 10 && number_input != no) {//10回以内に正解できなかった場合
                    System.out.println("\n正解は" + no + "でした。\n");
                    sleeptime(1000);
                    //ポイントを減らす処理
                    System.out.println("ポイント:" + point +" -" + 5);
                    if (point > 0)
                        point--;
                    sleeptime(800);
                    for (int i = -4; i < 0; i++) {
                        System.out.println("ポイント:" + point + " " + i);
                        sleeptime(50);
                        if (point > 0)
                            point--;
                    }
                    System.out.println("ポイント:" + point +"\n");
                    String a = point + "";
    
                    // 最終的なポイント数を書き込む
                    try {
                        FileWriter fw = new FileWriter("./txt/data.txt");
                        fw.write(a);
                        fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    sleeptime(300);
                    break;
                }
		    } while (number_input != no);

            if (number_input == no) { //10回以内に正解した場合
                System.out.println("\n" + "正解です。\n");
                sleeptime(1000);
                pointchanger(count);
            }

            do {
                System.out.println("もう一度やりますか？");
                try {
                    System.out.print("0|はい 1|いいえ:");
                    retry = stdIn.nextInt();
                }catch(java.util.InputMismatchException e){
                    System.out.println("エラーが発生しました。プログラムを終了します。");
                    System.exit(1);
                }
            } while (retry != 0 && retry != 1);
        } while(retry != 1);
    }
     
}
