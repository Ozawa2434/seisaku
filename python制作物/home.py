#ファイルのインポート
import Janken, Number, Achievement, Jinrou, Date_clear, Lucky_color
import time, random

#インスタンスの生成
janken = Janken.Janken
number = Number.Number
achievement = Achievement.Achievement
lucky_color = Lucky_color.Lucky_color
jinrou = Jinrou.Jinrou
date_clear = Date_clear.Date_clear


"""
https://qiita.com/BlueSilverCat/items/4111c17e59bdbfab5078
ここの関数は↑のサイトを参考にしました。 """
#ロード(Now loading)の表示ができる関数
def load(list, interval=0.25, loop=1): # リスト、表示間隔、ループ回数を定義
    for _ in range(loop): # 表示回数
        for i in list: # リストの中を順番に表示
            print(i, end="")
            time.sleep(interval)
            print("\r", end="") # 結合
    time.sleep(interval)
    print() #改行

load_list = [
    "  Nowloading     ",
    "  Nowloading.    ",
    "  Nowloading..   ",
    "  Nowloading...  "
]

clear_list = [
    "  初期化中     ",
    "  初期化中.    ",
    "  初期化中..   ",
    "  初期化中...  "
]

# ロードの表示
load(load_list, 0.25, random.randint(0,1))

while True:
    
    print('\n何をしますか？')
    print('0: 実績を確認する')
    print('1: じゃんけんする')
    print('2: 数当てチャレンジ')
    print('3: 明日のラッキーカラー')
    print('4: 人狼ゲーム')
    print('9: 終了する')
    print('10:データの初期化')
    
    act = input('整数で番号を入力:')
    #項目にある数値ならば整数型に
    if act == '0' or act == '1' or act == '2' or act == '3' or act == '4' or act == '9' or act == '10':
        act = int(act)

        # 数値ごとにプログラムを実行
        if act == 0:
            print()
            achievement.view()

        elif act == 1:
            janken.action()

        elif act == 2:
            number.action()

        elif act == 3:
            lucky_color.action()

        elif act == 4:
            jinrou.action()

        elif act == 9:
            print('また来てね')
            break

        elif act == 10:
            while True:
                Selection = input('データを初期化しますか?\n(Y/N)')
                if Selection == str:
                    Selection = str(Selection)
                if Selection == 'Y' or Selection == 'y':
                    date_clear.action()
                    load(clear_list, 0.25, random.randint(1,2))
                    input('データを初期化しました。')
                    break
                elif Selection == 'N' or Selection == 'n':
                    print('ホームに戻ります')
                    time.sleep(0.5)
                    break
    else:
        print('正しく入力してください')