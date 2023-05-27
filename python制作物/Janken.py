# じゃんけんゲーム

#クラスを作成
class Janken():

    def action():

        #インポート
        import random
        import time

        print('じゃんけんゲーム')
        time.sleep(0.7)

        #連勝数、最大連勝数、あいこの判定
        winningstreak = max_winningstreak = tieflg = 0

        #ゲーム終了時の選択肢
        def continues() -> int:
            while True:
                #空白等を入力した場合エラーを表示
                try:
                    retry = int(input('もう一度(0：はい／1：いいえ）：'))
                except ValueError:
                    print('エラーが発生しました。\nプログラムを終了します。')
                    exit()
                if retry == 1:
                    print('最大連勝数：', max_winningstreak, '連勝')
                    print('じゃんけんゲームを終了します。\n')
                    #1を返す
                    return 1
                elif retry == 0:
                    break
                else:
                    print('正しく入力してください')

        #実績の解除とデータのセーブ
        def date_save(line_value) -> None:
            with open('./テキストファイル/janken_Date.txt') as janken_date:
                line = janken_date.read()
            if line != '':
                line = int(line)
            else:
                line = 0
            if line <= line_value:
                with open('./テキストファイル/janken_Date.txt', mode='w') as janken_date:
                    janken_date.write(str(line_value + 1))
                with open('./テキストファイル/J_achievement.txt', encoding='utf-8') as achieve:
                    all_line = achieve.read()
                    # 文字列置換
                    all_line = all_line.replace('☐', '☑', 1)
                    # 同じファイル名で保存
                    with open('./テキストファイル/J_achievement.txt', mode='w', encoding='utf-8') as achieve:
                        achieve.write(all_line)

        while True:
            #0~2の値をランダムで生成
            comp = random.randint(0, 2)

            while True:
                try:
                    #あいこの判別
                    if tieflg == 1:
                        print('あいこで...', end='')
                    else:
                        print('じゃんけん...', end='')
                    human = int(input('(0：グー／1：チョキ／2:パー）：'))
                except ValueError:
                    print('エラーが発生しました。\nプログラムを終了します。')
                    exit()
                #自分側の出す手を判別
                if human == 0:
                    print('あなた>グー✊')
                    break
                elif human == 1:
                    print('あなた>チョキ✌')
                    break
                elif human == 2:
                    print('あなた>パー🖐')
                    break
                else:
                    print('正しく入力してください')
                    time.sleep(0.3) #表示間隔をあける

            time.sleep(0.7)
            print('相手>私は', end='')
            time.sleep(1)
            #相手側の出す手を判別
            if comp == 0:
                print('グー✊ ', end='')
            elif comp == 1:
                print('チョキ✌ ', end='')
            else:
                print('パー🖐 ', end='')
            print('を出しました。')

            # 勝敗の判定
            judge = (human - comp + 3) % 3
            time.sleep(0.7)
            if judge == 0:
                print('引き分けです。')
                tieflg = 1
            elif judge == 1:
                print('あなたの負けです。')
                #連勝数とあいこフラグをリセット
                winningstreak = 0
                tieflg = 0
                if continues() == 1:
                    break
            else:
                print('あなたの勝ちです。')
                #連勝数に１足してあいこフラグをリセット
                winningstreak += 1
                tieflg = 0
                #最大連勝数の更新
                if winningstreak >= max_winningstreak:
                    max_winningstreak = winningstreak
                #実績の解除
                if winningstreak >= 1:
                    print('現在', winningstreak, '連勝中', sep='')
                    if winningstreak == 2:
                        date_save(0)
                    elif winningstreak == 3:
                        date_save(1)
                    elif winningstreak == 5:
                        date_save(2)
                    elif winningstreak == 10:
                        date_save(3)
                if continues() == 1:
                    break
