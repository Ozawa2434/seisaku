#クラスを作成
class Date_clear():

    def action():

        #データの削除
        def clear(file) -> None:
            with open(file, encoding='utf-8') as achieve:
                # 全文を抽出
                all_line = achieve.read()
                # 文字列置換
                all_line = all_line.replace('☑', '☐')
                # 置換し終わったファイルを保存
                with open(file, mode='w', encoding='utf-8') as achieve:
                    achieve.write(all_line)
        #実績の削除
        clear('./テキストファイル/J_achievement.txt')
        clear('./テキストファイル/N_achievement.txt')
        clear('./テキストファイル/JIN_achievement.txt')

        #データファイルを空白にする
        file = open('./テキストファイル/janken_Date.txt', 'w')
        file.write('')
        file.close()                       # クローズ

        file = open('./テキストファイル/number_Date.txt', 'w')
        file.write('')
        file.close()

        file = open('./テキストファイル/jinrou_Date.txt', 'w')
        file.write('')
        file.close()

        file = open('./テキストファイル/jinrou_Date2.txt', 'w')
        file.write('')
        file.close()