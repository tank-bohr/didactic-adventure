(ns didactic-adventure.brain)

(defn kagbe []
  (rand-nth [
              "Кагбэ, хочется, чтобы живой вышел"
              "Ну кагбэ, мы и там абсолютно не при чем"
              "я кагбэ не шучу"
              "кагбэ это позоже на наеб чистой воды"
              "как бы не обостриться"
              "ну кагбэ удачи"
              "и кагбэ это не конец"
              "как бы сказал Леша хренюгге"
              "хуёся, как бы сказал Леха!"
              "я какбэ не последний человек"
              ]))

(defn pasha []
  (rand-nth [
              "паш у нас на зоне так не отвечали"
              "паш покажи шашличинг"
              "Паш покажи достать чужое мясо, стоять и жарить"
              "паш, покажи букмейт"
              "паш чо такой борзый?"
              "-паш кто красавчик? - Andrew Baew"
              "Паш, получается все херней страдают, ты это хочешь сказать?"
              ]))

(defn food []
  (rand-nth [
              "Только давайте без плова"
              "ПОТОМУ ЧТО ОТ МАКАРОН НЕ ЖИРЕЕШЬ"
              "так - просто обжиралово"
              "МАКАРОН или морковки?"
              "попкорн есть?"
              "Спасибо, не увлекайся там чизбургерами по 450 рублей"
              "жружин"
              ":fu: тому, кто ест чужую еду, давайте так не делать и уважать друг друга"
              "вишня с курицей внутри"
              "Попробую все три)))"
              "некогда пожарить свое мясо, но можно попробовать успеть пожарить чужое мясо!"
              "А в чем особенность котлетосов из Волгограда, немного побаиваюсь спросить"
              "а я в макдачку успею как раз)"
              "заказывать в макдке?))))"
              "Моя еда тоже свободна"
              "мой пирожок"
              "две ключевые состовляющие ДНК москвича - блины и шава"
              ]))

(defn i-said []
  (rand-nth [
              "я сказал я"
              "я прав или я прав"
              "(можно зачеркнуть)"
              "сам себя лайкнул))))"
              "Так, я сказал все что хотел."
              "я готов попозже сегодня выиграть"
              "Вспомните, что я вам говорил и что вы мне ответили."
              "И наказать"
              "ваш звонок очень важен для вас"
              "Я категорически не согласен."
              "Так, не понял, у кого-то кроме меня есть кнопка бога?"
              "Нет и нет"
              "НЕТ, ЭТО Я САМ ЗНАЮ"
              "Пиздец я добренький"
              "А ты что думаешь?"
              "по русски я гендир, Саймон основатель"
              "напомни в пнд"
              "Гггг, я согласен)"
              "А ты что думаешь?"
              ]))

(defn money []
  (rand-nth [
            "разориться можно!"
            "Норм, можно брать"
            "Понятно, мы не вылечим людей от жадности и глупости, пробовать не стоит"
            "не расплатимся потом"
            "поставь плз 1000 от Самера на Хиллари"
            "Ну, это зависит от того, сколько пятитысячных у меня останется afterall"
            "а мы им не заплатили? ну тогда тебе в премию"
            "Сколько там? Дам"
            "почем?"
    ]))


(defn kitty []
  (rand-nth [
            "для флеш-рояля достаточно 5 котов)))"
            "Карин, тут твой выход с котом должен быть))))"
            "мяу"
            "прикошачили"
            "промо-кот"
            ]))


(defn drink []
  (rand-nth [
            "кое-чего надо бахнуть и идем"
            "ГГГ) За покером точно бухнем)"
            "надо еще в вискокурней и пивнывм заводом запартнериться)))"
            "только с пивом и сушеные рыбки плиз"
            "нет уж. давай приходи, напоим тебя водочкой. нагрею спец для тебя!"
            "ирландский напиток, 5 букв/ изолента!"
            "тебе бы только попраздновать)))"
            "5 литров чего???"
            "кто бежит?)))"
            "какая разница чем закидываться, конечно"
            "Саймоногон"
            ]))


(defn i-can []
  (rand-nth [
              "если я так делаю, значит можно"
              ]))


(defn prohibition []
  (rand-nth [
             "Запретить запрещать прекращать троллинг!"
             "запретить парковаться у нас всяким непонятным чувакам"
             "Вот! Запретить такие выражения про котов"
             "в стоп-лист выражение стоп-лист"
             "запретить слово запретить"
             "никогда - азартные игры запрещены законом!"
             "Кто решил? Подпись под решением стоит?))"
             "правильно - астрафуй, насяльникэ!"
             "нельзя."
             "заблокирован за спам"
             "Безусловно перестать"
             "прекращай уже! соседи жалуются"
             "гондонапрув"
             "АПРУВ"
             "АПРУВ ВСЕМ"
              ]))

(defn decree []
  (rand-nth [
             "уволить н*хуй"
             "нахер пускай идут"
             "А че меня бояться? Но вообще надо."
             "на работе надо работать, при этом домой ходить, конечно же, не обязательно ))))"
             "а то не уйдете в отпуск :lash:"
             "Какое пиво, вход по карточке, работа 10 часов, обед строго 45 минут в 12.00"
             "в 6 - самый разгар работы, так-то"
             "как я посмотрю, работа кипит"
             ]))

(defn appeal []
  (rand-nth [
             "Хехехеех))"
             "Гггг"
             "мазафака!"
             "гого!"
             "ЙОУ!"
             "+"
             "Камон"
             "рил ток"
             "Погодите"
             "Ненене"
             "СЕЙЧАС"
             "Куку"
             ")))))))))"
             "чесногря"
             "Эм"
             "емае"
             "гогого"
             "Ыыыыы"
             "ёма"
             "я буду завтра"
             "плиз"
             "хохо"
             "Не"
             "ок, ща иду"
             "а можешь спутиться?"
             ]))

(defn asap []
  (rand-nth [
              "Давайте асап"
              "СЕЙЧАС"
              "Господа, дождитесь меня, плиз"
              "Вот это скорость!"
              "Погодите минуточку"
              "гого!"
              "Че вы так резво)"
    ]))

(defn joke []
  (rand-nth [
              "))))) тролль!"
              "очень остроумно и смешно"
              "durazki shutke detected"
              "Ггггг"
    ]))


(defn hat []
  (rand-nth [
              "Шляпа — это шляпа"
              "вообще, это пичалька"
    ]))

(defn idiots []
  (rand-nth [
              "Какие-то раздолбаи"
]))

(defn facts []
  (rand-nth [
              "простой фактчекинг надо делать, дебилы"
              "Козлы, элементарный фактчекинг сделать не могут"
  ]))

(defn pain []
  (rand-nth [
              "это не боль, а хейтеры"
              "Как-то боль всегда"
              "что за тоска и боль на ровном месте?"
  ]))

(defn litres []
  (rand-nth [
              "Литрес вон облажался"
              "Голосуем за БМ, против Эксмо-АСТ"
  ]))


(defn lemonass []
  (rand-nth [
              "обращение к тем, кто сиcтематически режет попки лимонов"
  ]))


(defn bday []
  (rand-nth [
              "С ДНЕМ РОЖДЕНИЯ МЕНЯ!"
  ]))

(defn react [text]
  (cond
    (re-find #"(?ui)ка[кг\s]*б[ыеэ]"                        text)
    (kagbe)
    (re-find #"(?ui)паш"                                    text)
    (pasha)
    (re-find #"(?ui)(прав\s|сказа(ть|л|жи))"                text)
    (i-said)
    (re-find #"(?ui)(запре[тщ]|\bможно)"                      text)
    (prohibition)
    (re-find #"(?ui)(нельзя|невозможно)"                    text)
    (i-can)
    (re-find #"(?ui)(указ|работ[ауо])"                      text)
    (decree)
    (re-find #"(?ui)(андр[ею]|andrew|директор|вождь|лидер)" text)
    (appeal)
    (re-find #"(?ui)(\bед[аы]|поесть|жир|поедим|плов|пицца|жрун|жрать|худеть|голод)" text)
    (food)
    (re-find #"(?ui)буха(ть|ем)"                            text)
    (drink)
    (re-find #"(?ui)(баб[лк]|деньги|дорого)"                text)
    (money)
    (re-find #"(?ui)(\bкот[\sаы]\b)"                        text)
    (kitty)
    (re-find #"(?ui)(\bасап|asap)"                          text)
    (asap)
    (re-find #"(?ui)(пошутил|шутка|шучу)"                   text)
    (joke)
    (re-find #"(?ui)(шляпа)"                                text)
    (hat)
    (re-find #"(?ui)(фактчекинг)"                           text)
    (facts)
    (re-find #"(?ui)(\bболь\b)"                             text)
    (pain)
    (re-find #"(?ui)(праздник)"                             text)
    (bday)
    (re-find #"(?ui)(литрес|эксмо)"                             text)
    (litres)
    (re-find #"(?ui)(попки|лимон)"                             text)
    (lemonass)
    ))
