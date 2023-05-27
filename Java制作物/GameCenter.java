import java.util.*;

import javax.management.loading.PrivateClassLoader;

//ファイル系
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class GameCenter {
    static int point;

        //時間を空けるメソッド
	static void sleeptime(int time) {
		try {
			Thread.sleep(time); //〇秒待つ
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

    //ポイントを使用する場合のメソッド
    static void pointuse(int usepoint) {
		System.out.println("ポイント:" + point + usepoint );
		if (point > 0)
			point--;
		sleeptime(800);
		for (int i = usepoint+1; i < 0; i++) {
			System.out.println("ポイント:" + point + " " + i );
			sleeptime(50);
			if (point > 0)
				point--;
		}
		System.out.println("ポイント:" + point +"\n");
        sleeptime(500);
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
    }

    public static void main(String[] args) {

        // 画面出力
        System.out.println("ゲームセンターへようこそ！");

        // インスタンス化
        Scanner stdIn = new Scanner(System.in);
        PrivateCode privateCode = new PrivateCode(3923);
        int select = 0, point = 0, code = 0, unlock_flg = 0;

        while(true) {

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

            // unlock.txtに入ってる数値を読み込む
            try {
                String str;
                BufferedReader bfReader = new BufferedReader(new InputStreamReader(new FileInputStream("./txt/unlockdata.txt"), Charset.forName("UTF-8")));
                str = bfReader.readLine(); //ファイルの一行目を読み取る
                unlock_flg = Integer.parseInt(str);
                bfReader.close();
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }

            System.out.println();
            System.out.println("--実行したいゲームを選んでください--");
            System.out.println("1|じゃんけんゲーム");
            System.out.println("2|数当てゲーム");
            System.out.println("3|暗算ゲーム");
            if (unlock_flg == 1) {
                System.out.println("4|スロットゲーム");
            } else {
                System.out.println("4|？？？" + "[230ポイントで解放可能]" );
            }
            System.out.println("5|秘密のコード");
            System.out.println("6|データの初期化");
            System.out.println("9|終了                   所持ポイント：" + point);
            
            // 入力チェック
            try {
                System.out.print("番号：");
                select = stdIn.nextInt();
            } catch(InputMismatchException e) {
                System.out.println("エラーが発生しました。プログラムを終了します。");
                System.exit(1);
            }
            if(select == 1) {
                JankenGame janken = new JankenGame();
                System.out.println();
                janken.run();
            } else if(select == 2) {
                NumberGame number = new NumberGame();
                System.out.println();
                number.run();
            } else if(select == 3) {
                ArithmeticGame arithmetic = new ArithmeticGame();
                System.out.println();
                arithmetic.run();
            } else if(select == 4) {
                if (unlock_flg == 1) {
                    SlotGame slotGame = new SlotGame();
                    System.out.println();
                    slotGame.run();
                } else {
                    do{
                        System.out.println("\n230ポイントで解放しますか？");
                        System.out.print("0|はい 1|いいえ:");
                        select = stdIn.nextInt();
                        if (select == 0) {
                            if (point < 230) {
                                System.out.println("ポイントが足りません!" );
                                continue;
                            }
                                System.out.println("ポイント:" + point + " -" + 230 );
                                if (point > 0)
                                    point--;
                                sleeptime(800);
                                for (int i = -229; i < 0; i++) {
                                    System.out.println("ポイント:" + point + " " + i );
                                    sleeptime(20);
                                    if (point > 0)
                                        point--;
                                }
                                System.out.println("ポイント:" + point +"\n");
                                sleeptime(500);
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
                        
                            // unlockdataにデータを保存
                            a = 1 + "";
                            try {
                                FileWriter fw = new FileWriter("./txt/unlockdata.txt");
                                fw.write(a);
                                fw.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            System.out.println("スロットゲームが解放されました。");
                        }
                    } while (select != 0 && select != 1);
                }
            } else if(select == 5) {
                System.out.println("\n--秘密のコード--");
                sleeptime(800);
                try {
                    System.out.print("4桁のコードを入力して下さい:");
                    code = stdIn.nextInt();
                }catch(java.util.InputMismatchException e){
                    System.out.println("エラーが発生しました。プログラムを終了します。");
                    System.exit(1);
                }
                if (code == privateCode.getCode()) {
                    point += 10000;
                    System.out.println("コードの一致を確認!");
                    sleeptime(800);
                    System.out.print("10000ポイントを獲得しました∇ ");
                    stdIn.nextLine();
                    stdIn.nextLine();
                    // 最終的なポイント数を書き込む
                    String a = point + "";
                    try {
                        FileWriter fw = new FileWriter("./txt/data.txt");
                        fw.write(a);
                        fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("コードが違います。");
                    sleeptime(1000);
                }
            } else if(select == 6) {
                Dataclear dataclear = new Dataclear();
                System.out.println();
                dataclear.run();
            } else if(select == 9) {
                System.out.println("またのお越しを！");
                break;
            } else {
                System.out.println("正しい番号を入力してください。");
            }
        }

        stdIn.close();

    }
}

// 秘密のコードをカプセル化
class PrivateCode{
	private int code;
    
    PrivateCode(int code) {
    	this.code = code;
    }
	
	public int getCode() {
		return code;
	}
}