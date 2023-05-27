class Variable():
    weapon = yen = effect_time = effectflg = endflg = 0
    effect = 1
    innocent = 5
    item = []

class Jinrou():
    def action():

        #インポート
        import random,time

        #インスタンスの生成
        var = Variable()

        #リセットする関数
        def reset():
            #ゲーム終了フラグ
            var.endflg = 0
            #武器
            var.weapon = 'ボロボロのナイフ'
            #所持金
            var.yen = 0
            #バフ
            var.effect = 1
            #市民の数
            var.innocent = 5
            #所持アイテムリスト
            var.item = []
        #変数のリセット
        reset()

        #ゲーム終了時の選択
        def continues() -> int:
            while True:
                try:
                    retry = int(input('もう一度(0：はい／1：いいえ）：'))
                except ValueError:
                    print('エラーが発生しました。\nプログラムを終了します。')
                    exit()
                if retry == 1:
                    print('人狼ゲームを終了します。\n')
                    return 1
                elif retry == 0:
                    break
        
        #武器発見イベント関連
        def found_weapons(weapon):
            print('村を探索していると', weapon, ' を見つけた', sep='')
            time.sleep(1)
            while True:
                Selection = input('盗んで装備しますか？※ 装備した場合元の装備はなくなります。\n(Y/N)')
                if Selection == 'Y' or Selection == 'y':
                    print(weapon, 'を装備した。', end='')
                    input()
                    var.weapon = weapon
                    break
                elif Selection == 'N' or Selection == 'n':
                    input('何もしなかった。')
                    break

        #自販機イベント関連
        def buy_drinks(drink):
                var.item.append(drink)
                var.yen -= 300
                print('ガチャン！')
                time.sleep(1)
                print(drink, ' を入手した。(現在の所持金', var.yen , '円)', sep='', end='')
                input()

        #ショップイベント関連
        def shop(weapon, price):
            var.weapon = weapon
            var.yen -= price
            print(weapon, 'を購入し、装備した。')
            time.sleep(1)
            print('現在の所持金', var.yen , '円', sep='', end='')
            input()

        #実績の解除とデータのセーブ
        def date_save(line_value, pattern=0, date_file=0) -> None: # 比較する数値、パターン、ファイル名を定義
            with open(date_file) as jinrou_date:
                line = jinrou_date.read()
            if line != '':
                line = int(line)
            else:
                line = 0
            if line <= line_value:
                with open(date_file, mode='w') as jinrou_date:
                    jinrou_date.write(str(line_value + 1))
                with open('./テキストファイル/JIN_achievement.txt', encoding='utf-8') as achieve:
                    all_line = achieve.read()
                    # パターンが１なら
                    if pattern == 1: 
                        with open('./テキストファイル/jinrou_Date.txt') as jinrou_date:
                            f = jinrou_date.read()
                            # 一回すべてに✓をつける
                            all_line = all_line.replace('☐', '☑') # 文字列置換
                            # 解除していない実績のチェックを外す
                            if f == 0 or f == '':
                                all_line = all_line.replace('☑', '☐', 1)
                    else:
                        all_line = all_line.replace('☐', '☑', 1)
                    # 同じファイル名で保存
                    with open('./テキストファイル/JIN_achievement.txt', mode='w', encoding='utf-8') as achieve:
                        achieve.write(all_line)

        def event(randvalue, var):
            #武器の判別
            if var.weapon == 'ボロボロのナイフ':
                kill_probability_up = 1.0
            elif var.weapon == 'クワ✰':
                kill_probability_up = 1.2
            elif var.weapon == '粗悪な斧✰✰':
                kill_probability_up = 1.4
            elif var.weapon == '斧✰✰✰':
                kill_probability_up = 1.7
            elif var.weapon == '良質な斧✰✰✰✰':
                kill_probability_up = 2.1
            elif var.weapon == 'チェーンソー✰✰✰✰✰':
                kill_probability_up = 2.6

            #合計6個のイベント
            #武器発見イベント(15%)
            if randvalue >= 86:
                randweapons = random.randint(1, 100)
                if randweapons >= 46: #55%
                    found_weapons('クワ✰')
                elif randweapons >= 16: #30%
                    found_weapons('粗悪な斧✰✰')
                elif randweapons >= 1: #15%
                    found_weapons('斧✰✰✰')

            #お金拾うイベント(25%)
            elif randvalue >= 61:
                randyen = random.randint(1,5)
                print('道に', randyen, '00円が落ちていたので拾った。',sep='')
                time.sleep(1)
                var.yen += int(str(randyen) + '00')
                print('現在の所持金:', var.yen, '円', sep='', end='')
                input()

            #強奪イベント(15%)
            elif randvalue >= 46:
                print('鍵の開いている家を発見した。')
                time.sleep(1)
                max_value = round(100*var.effect)
                success_probability = random.randint(1, max_value)
                percentage = round(100-((30/max_value)*100))
                
                while True:
                    print('お金を盗む？➣ 成功確率', percentage, '%', sep='', end='')
                    if percentage-70 >= 1:
                        print('(', percentage-70, '%↑)', sep='')
                    Selection = input('(Y/N)')
                    if Selection == 'Y' or Selection == 'y':
                        randyen = random.randint(1,6)
                        if success_probability <= 30:
                            print('ガチャ..')
                            time.sleep(1)
                            print('...誰もいないみたいだ')
                            time.sleep(1.5)
                            input('市民>くらえっ！')
                            input('あなた>グハッ...')
                            input('市民>家を躊躇なく荒らし回ってる、お前が狼だろう？')
                            input('市民>残念だったな！俺ら市民の勝ちだ！')
                            input('➣ 人狼の負けです')
                            var.endflg = 1
                            break
                        elif success_probability >= 31:
                            print('ガチャ..')
                            time.sleep(1)
                            print('...誰もいないみたいだ')
                            time.sleep(1.5)
                            print('家から', randyen, '000円を盗んだ', sep='')
                            time.sleep(1)
                            var.yen += int(str(randyen) + '000')
                            print('現在の所持金:', var.yen, '円', sep='', end='')
                            input()
                            break
                    elif Selection == 'N' or Selection == 'n':
                        input('何もしなかった。')
                        break

            #自販機イベント(20%)
            elif randvalue >= 26:
                print('自動販売機を発見した')
                time.sleep(1)
                while True:
                    print('300円で何か買おうかな？', '(現在の所持金', var.yen , '円)', sep='')
                    Selection = input('(Y/N)')
                    if Selection == 'Y' or Selection == 'y':
                        randitem = random.randint(1,100)
                        if var.yen >= 300:
                            if randitem >= 61:
                                buy_drinks('コーヒー✰')
                            elif randitem >= 31:
                                buy_drinks('サイダー✰✰')
                            elif randitem >= 16:
                                buy_drinks('栄養ドリンク✰✰✰')
                            elif randitem >= 1:
                                buy_drinks('エナジードリンク✰✰✰')
                        else:
                            input('所持金が足りなかった')
                        break
                    elif Selection == 'N' or Selection == 'n':
                        print('何もしなかった。')
                        break
            
            #ショップイベント(15%)
            elif randvalue >= 11:
                print('歩いていたら武器商人と出会った')
                time.sleep(1.5)
                print('武器商人>こんばんはー！')
                time.sleep(1)
                while True:
                    Selection = input('武器商人>良かったら何か買っていかないかい？\n(Y/N)')
                    if Selection == 'Y' or Selection == 'y':
                        print('\n--武器--(現在の所持金', var.yen, '円)', sep='')
                        print('  1:クワ✰       　　　　400円')
                        print('  2:粗悪な斧✰✰   　　　1300円')
                        print('  3:斧✰✰✰      　　　　3000円')
                        print('  4:良質な斧✰✰✰✰　   　6800円')
                        print('  5:チェーンソー✰✰✰✰✰ 12000円')
                        buy_weapon = input('\n購入したい武器の番号を入力してください\n番号:')
                            #武器の番号が存在する場合、int型にする。
                        if buy_weapon == '1' or buy_weapon == '2' or buy_weapon == '3' or buy_weapon == '4' or buy_weapon == '5':
                            buy_weapon = int(buy_weapon)
                            if buy_weapon == 1 and var.yen >= 400:
                                shop('クワ✰', 400)
                                break
                            elif buy_weapon == 2 and var.yen >= 1300:
                                shop('粗悪な斧✰✰', 1300)
                                break
                            elif buy_weapon == 3 and var.yen >= 3000:
                                shop('斧✰✰✰', 3000)
                                break
                            elif buy_weapon == 4 and var.yen >= 6800:
                                shop('良質な斧✰✰✰✰', 6800)
                                break
                            elif buy_weapon == 5 and var.yen >= 12000:
                                shop('チェーンソー✰✰✰✰✰', 12000)
                                date_save(0, 1, './テキストファイル/jinrou_Date2.txt')
                                break
                            else:
                                input('所持金が足りない')
                    
                    elif Selection == 'N' or Selection == 'n':
                        input('武器商人>そうかい、気をつけてな')
                        break

            #市民遭遇イベント(10%)
            elif randvalue >= 1:
                print('あそこにいるのは...市民だ！')
                time.sleep(1.7)
                print('なんとかタイミングをみて殺そう')
                max_value = round(100*(var.effect * kill_probability_up))
                success_probability = random.randint(1, max_value)
                percentage = round(100-((50/max_value)*100))
                print('市民を殺害する➣ 成功確率', percentage, '%',  sep='', end='')
                if percentage-50 >= 1:
                    print('(', percentage-50, '%↑)', sep='')
                input('\nEnterキーを押すと実行します')
                if success_probability <= 40:
                    print('あなた>ばれないように近づこう')
                    time.sleep(2.5)
                    print('あなた>あと少し...!')
                    time.sleep(2.5)
                    input('市民>！？ 近づくな！！')
                    input('市民>お前、人狼だろ！')
                    if var.innocent == 1:
                        input('市民>待ってろよ...俺が皆の敵を打ってやる！')
                        input('\n...逃げられてから30分ほど経った。')
                        if percentage%2 == 1:
                            input('グサッ！')
                            time.sleep(2.0)
                            input('市民が背後から襲ってきた')
                            input('市民>残念だったな！')
                            input('市民>お前はこれで終わりだッ！')
                            input('➣ 人狼の負けです')
                        else:
                            input('あなたは背後からの気配に気付いた。')
                            input('市民>くらえ！！')
                            time.sleep(2.5)
                            input('市民が斧を持って襲って来た。')
                            time.sleep(2.5)
                            input('あなたは攻撃を避けてカウンター攻撃を与えた')
                            input('グサッ')
                            input('あなた>これで全員だ..')
                            input('あなた>ついに..やったぞ！')
                            input('あなた>ハハハハハ！')
                            print('➣ 人狼の勝利です')
                            time.sleep(0.7)
                            date_save(0, 0, './テキストファイル/jinrou_Date.txt')
                            input('➣ おめでとうございます')
                            var.endflg = 1
                    else:
                        input('市民>あいつらに言ってやるからな！ｽﾀｽﾀ..')
                        input('逃げられてしまった。')
                        input('\n...逃げられてから10分ほど経った。')
                        input('グサッ！')
                        input('市民>残念だったな！')
                        input('市民>お前はこれで終わりだッ!')
                        input('➣ 人狼の負けです')
                    var.endflg = 1
                elif success_probability >= 41:
                    print('あなた>ばれないように近づこう')
                    time.sleep(2.5)
                    print('あなた>あと少し...!')
                    time.sleep(2.5)
                    print('グサッ！')
                    time.sleep(1.7)
                    input('市民>ぐっ...')
                    var.innocent -= 1
                    if var.innocent <= 0:
                        input('あなた>これで全員だ..')
                        input('あなた>ついに..やったぞ！')
                        input('あなた>ハハハハハ！')
                        print('➣ 人狼の勝利です')
                        time.sleep(0.7)
                        date_save(0, 0, './テキストファイル/jinrou_Date.txt')
                        input('➣ おめでとうございます')
                        var.endflg = 1
                    else:
                        input('あなた>なんとかやれた...')
                        print('あなた>あと', var.innocent, '人だ', sep='', end='')
                        input()

            #必ずイベントが終わるごとにバフターンを1減らす
            if var.effect_time >= 1:
                var.effect_time -= 1
                if var.effect_time == 0:
                    var.effect -= 1.00
                    var.effectflg = 0
                    print('エナジードリンクの効果が切れた')


        print('--人狼ゲーム--')
        while True:
            Selection = input('ストーリーと遊び方を確認しますか？\n(Y/N)')
            if Selection == 'Y' or Selection == 'y':
                print('--ストーリー--')
                print('市民5人と人狼1人がいる村にあなたはいます')
                print('あなたは人狼です')
                print('市民にばれないように武器やアイテムを調達し、')
                input('市民を全員殺害してください。')
                print('\n--遊び方--')
                print('ゲームが始まるとコマンドの選択ができます。')
                print('1.探索を開始する...探索を開始し、様々なイベントが発生します。')
                print('　　　　　　　　　 お金やアイテムの入手、市民殺害等が可能です。\n')
                print('2.アイテムを使用する...アイテムを選択して使用できます。')
                print('　　　　　　　　　　　 アイテムを使用することでイベントの成功確率が上がります。\n')
                print('3.装備を確認する...現在の装備を確認できます。')
                print('　　　　　　　　　 装備のレア度が高いほど、市民殺害イベントの成功確率が上がります。\n')
                print('9.ゲームを終了する...ゲームを終了します。\n')
                print('探索を軸に、アイテムや装備を集めてクリアを目指そう！！')
            elif Selection == 'N' or Selection == 'n':
                break
        """あらすじ"""
        input('市民1>俺ら6人の中に人間の皮を被った狼が1人いる...')
        input('市民2>今まで何人もそいつに襲われた。')
        input('市民3>この村を平和に戻すにはあいつを倒さないとダメだ')
        input('あなた>逃げるなんて選択肢は俺らの中にはない！')
        input('市民4>いいか？もうすぐ夜になるが、全員別行動で散らばるぞ')
        input('市民5>人狼は毎回夜に襲う習性がある。それを利用してあぶり出すんだ')
        print('\n➣ ゲーム開始')
        while True:
            #ゲームを終了するかどうかの判別(ゲームオーバーならもう一度やるかどうかの選択肢を出す)
            if var.endflg == 1:
                if continues() == 1:
                    break
                else:
                    reset()

            # 行動の選択
            act = input('\n[1: 探索する 2: アイテムを使用する 3: 装備を確認する 9: ゲームを終了] 番号:')
            if act == '1' or act == '2' or act == '3' or act == str(9):
                act = int(act)
                if act == 1:
                    #ランダムでイベント発生
                    event(random.randint(1, 100), var)
                elif act == 2:
                    
                    #所持しているアイテムの表示
                    if len(var.item) != 0:
                        print('\n--所持アイテム一覧--')
                        for i in range(len(var.item)):
                            print('  ', i+1, ':', var.item[i],sep='')

                        use_item = input('\n使用したいアイテムの番号を入力してください(Enterで閉じる)\n番号:')
                        #アイテムの番号が存在する場合、int型にする。
                        for i in range(len(var.item)):
                            if use_item == str(i+1):
                                use_item = int(use_item)

                                #アイテムの判別
                                for i in range(len(var.item)):
                                    if use_item-1 == i:
                                        if var.item[i] == 'コーヒー✰':
                                            var.effect += 0.05
                                            print('コーヒーを飲んだ')
                                            time.sleep(1)
                                            input('全ての成功確率小アップ！')
                                            del var.item[i]
                                        elif var.item[i] == 'サイダー✰✰':
                                            var.effect += 0.10
                                            print('サイダーを飲んだ')
                                            time.sleep(1)
                                            input('全ての成功確率中アップ！')
                                            del var.item[i]
                                        elif var.item[i] == '栄養ドリンク✰✰✰':
                                            var.effect += 0.20
                                            print('栄養ドリンクを飲んだ')
                                            time.sleep(1)
                                            input('全ての成功確率大アップ！')
                                            del var.item[i]
                                        elif var.item[i] == 'エナジードリンク✰✰✰':
                                            if var.effectflg == 0:
                                                var.effect += 1.00
                                            print('エナジードリンクを飲んだ')
                                            time.sleep(1)
                                            input('7ターンの間、全ての成功確率超絶アップ！')
                                            var.effect_time = 7
                                            var.effectflg = 1
                                            del var.item[i]
                    else:
                        print('アイテムを所持していません')
                #装備中の武器の表示
                elif act == 3:
                    if var.weapon != 0:
                        print('装備中:', var.weapon)
                    else:
                        print('装備を所持していません')
                #ホームに戻る
                elif act == 9:
                    print('ゲームを終了しています')
                    time.sleep(0.5)
                    break