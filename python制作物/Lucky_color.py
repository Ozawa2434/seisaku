class Lucky_color():
    
    def action():

        import random, time

        # 色のリスト
        color_list = [
            '赤',
            'オレンジ',
            '黄',
            '黄緑',
            '緑',
            '青',
            '空',
            '紫'
        ]
        while True:
            print('明日のラッキーカラーを占いますか？')
            try:
                select = input('(Y/N)')
            except ValueError: # エラーの表示
                print('エラーが発生しました。\nプログラムを終了します。')
                exit()
            if select == 'Y' or select == 'y':
                #ランダムでラッキーカラーを表示
                print('明日のラッキーカラーは、',color_list[random.randint(0, 7)], '色です', sep='', end='')
                print('\n')
                time.sleep(0.5) # 表示間隔開ける
            elif select == 'N' or select == 'n':
                break