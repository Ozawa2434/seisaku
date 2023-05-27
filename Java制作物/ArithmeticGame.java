// -*- coding: windows-31j -*-
import java.util.Random;
import java.util.Scanner;

//ファイル系
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class ArithmeticGame {

    static Scanner stdIn = new Scanner(System.in);
	static int retry_flg = 0, point = 0;

    //時間を空けるメソッド
	static void sleeptime(int time) {
		try {
			Thread.sleep(time); //〇秒待つ
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 続行の確認
	static boolean confirmRetry() {
		int retry = 0;
		do {
			retry_flg = 1;
			System.out.println("もう一度やりますか？");
			try {
				System.out.print("0|はい 1|いいえ:");
				retry = stdIn.nextInt();
			}catch(java.util.InputMismatchException e){
				System.out.println("エラーが発生しました。プログラムを終了します。");
				System.exit(1);
			}
		} while (retry != 0 && retry != 1);
		return retry == 0; 
	}

	//ポイントを付与させるメソッド
	static void pointchanger(double time) {
		if (time > 21.0) {// 21秒以上なら
			System.out.println("ポイント:" + point  + " +" + 1);
			point++;
			sleeptime(1000);
			for (int i = 0; i > 0; i--, point++) {
				System.out.println("ポイント:" + point  + " +" + i);
				sleeptime(50);
			}
		}else{// 20秒以内なら
			int time_dec = (int)Math.floor(time);
			double getpoint_cal = 24 - time_dec;
			getpoint_cal = 0.07 * getpoint_cal * getpoint_cal;
			int getpoint = (int)Math.round(getpoint_cal);
			if (getpoint == 1)
				getpoint++;
			System.out.println("ポイント:" + point  + " +" + getpoint + " (時間ボーナス)");
			point++;
			sleeptime(1000);
			for (int i = getpoint-1; i > 0; i--, point++) {
				System.out.println("ポイント:" + point  + " +" + i);
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
        System.out.println("--暗算ゲーム--");
		sleeptime(1000);

		Random rand = new Random();
		long response = 0;
		long startTime = 0, endTime = 0;
		double response_time = 0;
		int k = 0;

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

		System.out.println("\n暗算トレーニング!!");
		do {
			System.out.print("Enterで開始∇ ");
			if (retry_flg == 1)
				stdIn.nextLine();
			stdIn.nextLine();
			System.out.println("\n暗算トレーニング!!");
			startTime = System.currentTimeMillis(); // 問題出題時の時刻を取得

			int x = rand.nextInt(170) + 30;		// 3桁の数
			int y = rand.nextInt(170) + 30;		// 3桁の数
			int z = rand.nextInt(170) + 30;		// 3桁の数

			while (true) {
			System.out.print(x + " + " + y + " + " + z + " = ");
				try {
					k = stdIn.nextInt();		// 読み込んだ値
				}catch(java.util.InputMismatchException e){
                    System.out.println("エラーが発生しました。プログラムを終了します。");
                    System.exit(1);
                }
				endTime = System.currentTimeMillis(); // 解答後の時刻を取得
				response = (endTime - startTime);
				response_time = response;
				response_time /= 1000;
				response_time = Math.floor(response_time * 10) / 10;
				System.out.println("解答時間:"  + response_time + "秒");
				if (k == x + y + z)	{			// 正解
					System.out.println("正解!" + "\n");
					sleeptime(1000);
					pointchanger(response_time);
					break;
				} else { // 不正解
					System.out.println("不正解");
					sleeptime(1000);
					System.out.println();
					//ポイントを減らす処理
					System.out.println("ポイント:" + point + " -" + 5);
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
			}
		} while (confirmRetry());
    }
}
