# 数当てゲーム
class Number():

    # コンストラクタ
    def __init__(self):
        pass

    def action():
        
        # インポート
        import random

        #実績の解除とデータのセーブ
        def date_save(line_value) -> None:
            with open('./テキストファイル/number_Date.txt') as number_date:
                line = number_date.read()
            if line != '':
                line = int(line)
            else:
                line = 0
            if line <= line_value:
                with open('./テキストファイル/number_Date.txt', mode='w') as number_date:
                    number_date.write(str(line_value + 1))
                with open('./テキストファイル/N_achievement.txt', encoding='utf-8') as achieve:
                    all_line = achieve.read()
                    # 文字列置換
                    all_line = all_line.replace('☐', '☑', 1)
                    # 同じファイル名で保存
                    with open('./テキストファイル/N_achievement.txt', mode='w', encoding='utf-8') as achieve:
                        achieve.write(all_line)

        # ゲーム終了時にもう一度プレイするかのセンタ丑を表示する関数
        def continues() -> int:
            while True:
                try:
                    retry = int(input('もう一度(0：はい／1：いいえ）：'))
                except ValueError:
                    print('エラーが発生しました。\nプログラムを終了します。')
                    exit()
                if retry == 1:
                    print('数当てチャレンジを終了します。\n')
                    return 1
                elif retry == 0:
                    break
                else:
                    print('正しく入力してください')
        MAX = 1000                  # 当てさせる最大値
        MAX_STAGE = 10              # 入力できる最大回数
        while True:
            print('1～{}の数を{}回以内で当ててください。'.format(MAX, MAX_STAGE))
            stage = 1
            answer = random.randint(1, 1000) # 正解の数字をランダムで決める
            while stage <= MAX_STAGE: # MAX_STAGE分繰り返す
                print(stage, '回目 いくつかな：', end='')
                try:
                    n = int(input())
                except ValueError:# エラーの表示
                    print('エラーが発生しました。\nプログラムを終了します。')
                    exit()
                if n < 1 or n > MAX:    # 範囲外の値はやり直し
                    print('1～1000の整数値を入力してください。')
                    continue
                if n == answer:         # 正解
                    print('正解。', stage, '回で当たりました。')
                    for i in range(8):
                        if stage <= 8 - i:
                            date_save(i)
                    break
                elif n > answer:
                    #ヒントの表示
                    print('もっと小さな数です。')
                else:
                    print('もっと大きな数です。')
                stage += 1
            else:
                print('残念。正解は', answer, 'でした。')
            # もう一度プレイするかどうか
            if continues() == 1:
                break