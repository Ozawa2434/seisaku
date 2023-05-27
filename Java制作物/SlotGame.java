//動作関連
import java.util.Random;
import java.util.Scanner;

//ファイル系
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import java.util.Arrays;

public class SlotGame {
	static int point = 0;
	static int bingo_flg;
	static String[] sign_no = new String[3];
	static String[] sign = new String[9];
	
	//時間を空けるメソッド
	static void sleeptime(int time) {
		try {
			Thread.sleep(time); //〇秒待つ
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// ポイントを付与するメソッド
	static void pointgain(int getpoint) {
		sleeptime(1000);
		System.out.println("ポイント:" + point + " +" + getpoint);
		point++;
		sleeptime(700);
		for (int i = getpoint-1; i > 0; i--, point++) {
			System.out.println("ポイント:" + point + " +" + i);
			sleeptime(50);
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
	//ランダムで図柄を返す
	static String sign_dec(int no) {
		if (no <= 3) {
			return sign[8];
		} else if (no <= 7){
			return sign[7];
		} else if (no <= 13){
			return sign[6];
		} else if (no <= 22){
			return sign[5];
		} else if (no <= 32){
			return sign[4];
		} else if (no <= 45){
			return sign[3];
		} else if (no <= 61){
			return sign[2];
		} else if (no <= 79){
			return sign[1];
		} else
			return sign[0];
	}

	/*与えられた数値(今回はランダムの0～399のみ)が0～277に当てはまる場合、
	  フラグを立て、全てのレーンの図柄を統一する*/
	static void sign_bingo(int no) {
		if (no == 0) {
			bingo_flg = 1;
			Arrays.fill(sign_no, sign[8]);// 配列sign_noの全ての要素に同じ図柄を入れる
		} else if (no <= 4){
			bingo_flg = 1;
			Arrays.fill(sign_no, sign[7]);
		} else if (no <= 8){
			bingo_flg = 1;
			Arrays.fill(sign_no, sign[6]);
		} else if (no <= 19){
			bingo_flg = 1;
			Arrays.fill(sign_no, sign[5]);
		} else if (no <= 43){
			bingo_flg = 1;
			Arrays.fill(sign_no, sign[4]);
		} else if (no <= 77){
			bingo_flg = 1;
			Arrays.fill(sign_no, sign[3]);
		} else if (no <= 129){
			bingo_flg = 1;
			Arrays.fill(sign_no, sign[2]);
		} else if (no <= 197){
			bingo_flg = 1;
			Arrays.fill(sign_no, sign[1]);
		} else if (no <= 277){
			bingo_flg = 1;
			Arrays.fill(sign_no, sign[0]);
		} else {
			bingo_flg = 0;
		}
	}

	// レーンの回転と決定。
	static void spin(int no_spin, int no) {
		Random rand = new Random();
		String sign1 = "";
		if (no == 0) { //1レーン目の場合の処理
			for (int i = 0; i < no_spin; i++ ) {
				sign1 = sign_dec(rand.nextInt(100));
				System.out.println(sign1);
				sleeptime(80);
			}
			if (bingo_flg == 0) {
				sign_no[no] = sign1;
			}
			System.out.println(sign_no[0]);
		} else if(no == 1) { //2レーン目の場合の処理
			for (int i = 0; i < no_spin; i++ ) {
				sign1 = sign_dec(rand.nextInt(100));
				System.out.print(sign_no[0] + sign1 + "\n"); //1レーン目の図柄を表示させながら2レーン目を回転
				sleeptime(80);
			}
			if (bingo_flg == 0) {
				sign_no[no] = sign1;
			}
			System.out.println(sign_no[0] + sign_no[1]);
		} else if(no == 2) { //3レーン目の場合の処理
			for (int i = 0; i < no_spin; i++ ) {
				sign1 = sign_dec(rand.nextInt(100));
				System.out.print(sign_no[0] + sign_no[1] + sign1 + "\n"); //1レーン目と2レーン目の図柄を表示させながら3レーン目を回転
				sleeptime(80);
			}
			if (bingo_flg == 0) {
				sign_no[no] = sign1;
			} else {
				System.out.print(sign_no[0] + sign_no[1] + sign_no[2]);
			}
			System.out.println("");
			
			if (sign_no[0] == sign_no[1] && sign_no[0] == sign_no[2] && sign_no[1] == sign_no[2]) { //3つの値が等しいか
				System.out.println("");
				if (sign_no[0] == sign[0]) {
					System.out.println("◯ 揃い!");
					System.out.print(sign_no[0] + sign_no[1] + sign_no[2] + "\n");
					pointgain(7);// 獲得ポイント
				} else if(sign_no[0] == sign[1]) {
					System.out.println(sign[1] + "揃い!");
					System.out.print(sign_no[0] + sign_no[1] + sign_no[2] + "\n");
					pointgain(10);// 獲得ポイント
				} else if(sign_no[0] == sign[2]) {
					System.out.println(sign[2] + "揃い!");
					System.out.print(sign_no[0] + sign_no[1] + sign_no[2] + "\n");
					pointgain(16);// 獲得ポイント
				} else if(sign_no[0] == sign[3]) {
					System.out.println(sign[3] + "揃い!");
					System.out.print(sign_no[0] + sign_no[1] + sign_no[2] + "\n");
					pointgain(22);// 獲得ポイント
				} else if(sign_no[0] == sign[4]) {
					System.out.println(sign[4] + "揃い!");
					System.out.print(sign_no[0] + sign_no[1] + sign_no[2] + "\n");
					pointgain(44);// 獲得ポイント
				} else if(sign_no[0] == sign[5]) {
					System.out.println(sign[5] + "揃い!");
					System.out.print(sign_no[0] + sign_no[1] + sign_no[2] + "\n");
					pointgain(70);// 獲得ポイント
				} else if(sign_no[0] == sign[6]) {
					System.out.println(sign[6] + "揃い!");
					System.out.print(sign_no[0] + sign_no[1] + sign_no[2] + "\n");
					pointgain(140);// 獲得ポイント
				} else if(sign_no[0] == sign[7]) {
					System.out.println(sign[7] + "揃い!!");
					System.out.print(sign_no[0] + sign_no[1] + sign_no[2] + "\n");
					sleeptime(1500);
					pointgain(200);// 獲得ポイント
				} else if(sign_no[0] == sign[8]) {
					System.out.println(sign[8] + "揃い!!(大当たり)");
					System.out.print(sign_no[0] + sign_no[1] + sign_no[2] + "\n");
					sleeptime(1500);
					pointgain(rand.nextInt(100) + 350);// 獲得ポイント
				}
			} else {
				System.out.println("ハズレ");
				System.out.print(sign_no[0] + sign_no[1] + sign_no[2] + "\n");
				sleeptime(1000);
			}
		}
		sleeptime(100);
	}

       // メイン
       public void run(){
        Random rand = new Random();
		Scanner stdIn = new Scanner(System.in);
		//スロットの図柄
		sign[0] = "◯ ";
		sign[1] = "◎ ";
		sign[2] = "△ ";
		sign[3] = "▼ ";
		sign[4] = "◇ ";
		sign[5] = "◆ ";
		sign[6] = "☆ ";
		sign[7] = "★ ";
		sign[8] = "7";

		int select = 0;
		
		// data.txtに入ってる数値を読み込む処理
		try {
			String str;
			BufferedReader bfReader = new BufferedReader(new InputStreamReader(new FileInputStream("./txt/data.txt"), Charset.forName("UTF-8")));
			str = bfReader.readLine();	//ファイルの一行目を読み取る
			point = Integer.parseInt(str);
			bfReader.close();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
		
        System.out.println("--スロットゲーム--");
		sleeptime(1000);

		// data.txtに入ってる数値を読み込む
		try {
			String str;
			BufferedReader bfReader = new BufferedReader(new InputStreamReader(new FileInputStream("./txt/data.txt"), Charset.forName("UTF-8")));
			str = bfReader.readLine(); //ファイルの一行目を読み取る
			point = Integer.parseInt(str);
			bfReader.close();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}

		do {
			bingo_flg = 0;
			System.out.println("\n10ポイント消費してプレイしますか？");
			try {
				System.out.print("0|はい 1|ホームへ戻る 2|ポイント表:");
				select = stdIn.nextInt();
			}catch(java.util.InputMismatchException e){
				System.out.println("エラーが発生しました。プログラムを終了します。");
				System.exit(1);
			}
			if (select == 0) {
				if (point < 10) {
					System.out.println("ポイントが足りません!");
					continue;
				}
				// プレイの処理
				//ポイントを減らす処理
				System.out.println("ポイント:" + point + " -" + 10);
				if (point > 0)
					point--;
				sleeptime(200);
				for (int i = -9; i < 0; i++) {
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

				System.out.println("現在のポイント:" + point);
				System.out.print("Enterで回す∇ ");
				stdIn.nextLine();
				stdIn.nextLine();

				sign_bingo(rand.nextInt(400));

				int no1_spin = rand.nextInt(17) + 8;	//1つ目のレーンの回転率
				int no2_spin = rand.nextInt(17) + 8;	//2つ目のレーンの回転率
				int no3_spin = rand.nextInt(17) + 8;	//3つ目のレーンの回転率

				spin(no1_spin, 0);
				spin(no2_spin, 1);
				spin(no3_spin, 2);
			} else if (select == 2) {
				System.out.println("");
				System.out.println("--ポイント表--");
				System.out.println("");
				System.out.println("◯ ◯ ◯ ->7p");
				System.out.println("◎ ◎ ◎ ->10p");
				System.out.println("△ △ △ ->16p");
				System.out.println("▼ ▼ ▼ ->22p");
				System.out.println("◇ ◇ ◇ ->44p");
				System.out.println("◆ ◆ ◆ ->70p");
				System.out.println("☆ ☆ ☆ ->140p");
				System.out.println("★ ★ ★ ->200p");
				System.out.println("777->500p~1000p(ランダム)");
			}

		}while(select != 1);
	}
}