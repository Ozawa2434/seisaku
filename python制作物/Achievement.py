#クラスを作成
class Achievement():

    def view():       # 〇〇_achievement.txtを全てオープン（テキスト＋読込みモード）
        with open('./テキストファイル/J_achievement.txt', encoding='utf-8', mode='r') as J_achieve, open('./テキストファイル/N_achievement.txt', encoding='utf-8', mode='r') as N_achieve, open('./テキストファイル/JIN_achievement.txt', encoding='utf-8', mode='r') as JIN_achieve:
            #全文の表示
            for line in J_achieve:
                print(line, end='')
            print('\n')
            for line in N_achieve:
                print(line, end='')
            print('\n')
            for line in JIN_achieve:
                print(line, end='')

            print()
            input('\nEnterキーを押してください')