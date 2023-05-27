//動作関連
import java.util.Scanner;
import java.util.Random;

//ファイル関連
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class JankenGame {
	static int point = 0, getpoint = 0;

	/* https://www.javalife.jp/2018/05/04/java-%E5%87%A6%E7%90%86%E4%B8%AD%E3%81%AB10%E7%A7%92%E9%96%93%E5%BE%85%E3%81%A4%EF%BC%88%E3%82%B9%E3%83%AA%E3%83%BC%E3%83%97%E3%81%99%E3%82%8B%EF%BC%89/ 
		↑のサイトを参考にしました*/
	//時間を空けるメソッド
	static void sleeptime(int time) {
		try {
			Thread.sleep(time); //〇秒待つ
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//勝敗、連勝数を取得し、ポイントを付与するメソッド
	static void pointchanger(int winningstreak) {
		if (winningstreak == 0) { //じゃんけんに負けた場合(-5ポイント)
			System.out.println("ポイント:" + point + " -" + 5);
			if (point > 0)
				point--;
			sleeptime(1000);
			for (int i = -4; i < 0; i++) {
				System.out.println("ポイント:" + point + " " + i);
				sleeptime(50);
				if (point > 0)
					point--;
			}
		} else if (winningstreak == 1) { //じゃんけんに１回勝った場合(+5ポイント)
			System.out.println("ポイント:" + point + " +" + 5);
			point++;
			sleeptime(1000);
			for (int i = 4; i > 0; i--, point++) {
				System.out.println("ポイント:" + point + " +" + i);
				sleeptime(50);
			}
		} else { //じゃんけんに連勝した場合の処理(2×勝った数の2乗)
			getpoint = 2 * winningstreak * winningstreak;
			System.out.println("ポイント:" + point + " +" + getpoint +" (" + winningstreak + "連勝ボーナス！)");
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

    //メイン
    public void run() {
        Scanner stdIn = new Scanner(System.in);
		Random rand = new Random();
		int aiko_flg = 0, winningstreak = 0;

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
		
        System.out.println("--じゃんけんゲーム--");
		sleeptime(1000);
		String[] hands = {"グー", "チョキ", "パー"};
		int retry = 0;						// もう一度行うか？

		do {
			do {
				int comp = rand.nextInt(3);	// コンピュータの手（0, 1, 2の乱数）
				int user = 0;										// プレーヤの手（0, 1, 2で読み込む）

				do {
					if (aiko_flg == 1) {
						System.out.print("あいこで...");
					} else {
						System.out.print("じゃんけん... ");
					}
					for (int i = 0; i < 3; i++)
						System.out.printf("%d|%s ", i, hands[i]);
					try {
						System.out.print("：");
						user = stdIn.nextInt();
					}catch(java.util.InputMismatchException e){
						System.out.println("エラーが発生しました。プログラムを終了します。");
						System.exit(1);
					}
				} while (user < 0 || user > 2);

				// 両者の手を表示
				System.out.printf("あなた>%s!\n", hands[user]);
				sleeptime(1000);
				System.out.printf("相手>%s!\n", hands[comp]);
				sleeptime(700);
				int judge = (user - comp + 3) % 3;			// 判定
				switch (judge) {
					case 0: aiko_flg = 1; break; //あいこの場合の処理
					case 1: System.out.println("\nYOU LOSE"); // 負けた場合の処理
						aiko_flg = 0;
						winningstreak = 0;
						pointchanger(winningstreak);
						break;
					case 2: System.out.println("\nYOU WIN!"); //勝った場合の処理
						aiko_flg = 0;
						winningstreak += 1;
						pointchanger(winningstreak);
						break;
				}
			} while (aiko_flg == 1);

			do {			// もう一度行うかどうかを確認
				if (winningstreak >= 1) {
					System.out.println("現在" + winningstreak + "連勝中");
				}
				try {
				System.out.print("もう一度？ 0|はい 1|いいえ：");
				retry = stdIn.nextInt();
				}catch(java.util.InputMismatchException e){
					System.out.println("エラーが発生しました。プログラムを終了します。");
					System.exit(1);
				}
			} while (retry != 0 && retry != 1);
		} while (retry == 0);
    }
}
