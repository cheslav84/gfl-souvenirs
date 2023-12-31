
## Сувеніри
У файловому сховищі (набір файлів довільної структури) знаходиться
інформація про сувеніри та їх виробників.
### Для сувенірів необхідно зберігати:
- назву;
- реквізити виробника;
- дату випуску;
- ціну.
### Для виробників необхідно зберігати:
- назву;
- країну.
### Реалізувати такі можливості:
- Додавати, редагувати, переглядати всіх виробників та всі сувеніри.
- Вивести інформацію про сувеніри заданого виробника.
- Вивести інформацію про сувеніри, виготовлені в заданій країні.
- Вивести інформацію про виробників, чиї ціни на сувеніри менше заданої.
- Вивести інформацію по всіх виробниках та, для кожного виробника вивести інформацію
про всі сувеніри, які він виробляє.
- Вивести інформацію про виробників заданого сувеніру, виробленого у заданому року.
- Для кожного року вивести список сувенірів, зроблених цього року.
- Видалити заданого виробника та його сувеніри.


    P.S. Бази даних не використовуємо (тільки файли).

Для зберігання даних у програмі використовуємо колекції. У процесі обробки
використовуємо Streams (або не використовуємо, якщо простіше без них)

    Примітка. У різних виробників може бути сувеніри з однаковими назвами. Наприклад, сувенір “Фірмова чашка” 
    може бути у виробників “Національний університет кораблебудування” та “Приватбанк”.




## Вирішення


### Зберігання об'єктів
Дані зберігаються у файловій системі у форматі *JSON*. Для роботи над *JSON* використовуються біблітека *Jackson*.
За замовчуванням як каталог для зберігання зазначається ***src\main\resources\data***. 
Для зміни каталогу зберігання: в інтерфейсі ***StorageProperties*** що знаходиться за адресою
***src\main\java\gfl\havryliuk\storage*** слід зазначети власний каталог. 

Для додавання нового каталогу реалізувати власний ***FileStorage*** в якому зазначити ім'я файлу з розширенням *.json*.
Основні операції над файлами-документами виконує клас ***Document*** якому делегується робота над створенням файлів, 
зберіганням документів або ж читанням записів. Власне обхід вузлів *JSON* та їх редакування виконується в репозиторії
***Repository***.

Кожен об'єкт зберігається в окремому файлі. Посилання об'єктів один на одного здійснюється через id. Наприклад:
    
    Виробники:
    [
        {
            "id": "ba4f973f-11fe-4b6a-bab0-dd842c6037a1",
            "name": "Producer 01",
            "country": "Country 01",
                "souvenirs": [
                  {
                    "id": "a25babb3-e114-44e1-8987-99b8c876833a"
                  },
                  {
                    "id": "e155e213-6848-4b10-93ef-8f6cbbbb00d4"
                  }
            ]
        }
    ]

    Сувеніри:
    [
        {
        "id": "a25babb3-e114-44e1-8987-99b8c876833a",
        "name": "Tea cup",
        "price": 11.05,
        "productionDate": "2014-12-20T02:30:00",
            "producer": {
                "id": "ba4f973f-11fe-4b6a-bab0-dd842c6037a1", 
                "name": "Producer 01"
            }
        },
        {
        "id": "e155e213-6848-4b10-93ef-8f6cbbbb00d4",
        "name": "Coffee cup",
        "price": 14.00,
        "productionDate": "2014-12-20T02:30:00",
            "producer": {
                "id": "ba4f973f-11fe-4b6a-bab0-dd842c6037a1"
                "name": "Producer 01"
            }
        }
    ]

Додавання нового сувеніра чи виробника: якщо об'єкт в файлах не існує він додається, а якщо він з таким ID вже наявний -
відповідно відбувається його заміна.


#### Представлення об'єктів
При вибірці виробників та сувенірів, репозиторій повертає не повноцінний об'єкт. Наприклад, у виробників
в списку сувенірів будуть зазначатись лише їх ID, а в сувенірів у виробника буде вказаний лише ID.
Більшість запитів з умови задачі це задовільняє, а для умови *"Вивести інформацію по всіх виробниках та, для кожного
виробника вивести інформацію про всі сувеніри, які він виробляє"* репозиторій ініціалізує виробників повноцінним
списком сувенірів.

Об'єкт ***Souvenir*** являється композиційним по відношенню до ***Producer*** і не може існувати без нього.
При видаленні ***Producer*** видаляються і його ***Souvenir***.

#### Consistency
- при створенні: Спочатку необхідно зберегти виробника, а потім сувенір. При збереженні сувеніру, якщо ID виробника 
  в файлі виробників не буде, репозиторій видасть ***IllegalStateException()*** з відповідним повідомленям, та ID не 
  збереженого виробника. Те ж буде якщо намагатись зберегти кілька сувенірів відразу (в повідомленні про відсутність 
  з Id зазначиться лише один). Якщо зберігати виробника - його сувеніри, при наявності, зберігаються автоматично.
- при видаленні: При видаленні ***Souvenir*** (одного або ж кількох відразу), репозиторій забезпечує видалення як 
  сувеніру (сувенірів) з файлу ***Souvenir***, так і видалення відповідних їм ID з файлу ***Producer***. Відповідно,
  при видаленні ***Producer*** видаляються всі сувеніри з обох файлів.


### Взаємодія з користувачем, відображення результатів
Взаємодія з користувачем відбувається через консольне меню і побудована на патерні ***"Command"***. Вивід опцій меню
здійснюється через логер. У файлі *Log4j2.properties* для рівнів логування *debug*, *info*, та *warn* налаштований
спеціальний апендер що дозволяє для різних рівнів виводити інформацію в консоль різним кольором, а також не виводити 
службову інформацію.

В основі роботи з користувачем лежать ***EntityDisplayer*** та ***MenuTemplate***.

Абтрактний клас ***EntityDisplayer***, який імплементує два інтерфейси: 
- ***Action*** - встановлює коркретну реалізацію ***ConsoleLoggingPrinter*** та видовить в консоль результати
запитів *Select options* у вигляді таблиці;
- ***ReturnableAction*** - виводить результати запитів в консоль у вигляді нумерованого списку, де користувач має 
можливість обрати відповідного виробника чи сувеніра для подальшої роботи з ним (оновлення, видалення, або ж вибору 
виробника для створення нового сувеніра). 

Таким чином, ***EntityDisplayer*** відображає результати незалежно від того чи був *select* запит, або *create*, 
*update*, *delete* запити даючи користувачу змогу фільтрувати результати вибірки для відповідних подальших дій.

Абтрактний клас ***MenuTemplate*** призначений для навігації по меню застосунку, детальніше описаний нижче в розділі
***Pattern "Template Method"***

*Приклад результату запиту "Вивести інформацію про виробників заданого сувеніру, виробленого у заданому року"*
![alt text](/src/main/resources/picture/example.png "Приклад таблиці")


При першому запуску програми слід створити файли де будуть зберігатись дані. Дана опція надається в меню, слід лише
вибрати пункт ***Create storages***. Програмою, також, надається можливіть заповнити сховище деякими дефолтними даними
(путнт меню ***Fill storages with initial data***), для перевірки можливості роботи вибірки даних з файлів.
При подальшому перезапуску програми ці пункти слід ігнорувати, так як повторне їх виконання призведе до видалення 
існуючих даних, або відповідно дублювання даних в сховищах. При виборі даних пунктів меню програма видає відповідні 
попередження.


### Патерни
***Pattern "Strategy"***
- в класі ***Document<T>***, в залежності від реалізації інтерфейсу Storage, на запит getStorage()
повертаєтся конкретний файл для зберігання конкретного об'єкту.
- вивід таблиць результатів запитів в консоль, в залежності від коркретної реалізації ***ConsoleLoggingPrinter***


***Pattern "Singleton"*** 
- об'єкт ***Mapper***, який розширює ***ObjectMapper*** бібліотеки *Jackson*. Згідно її 
документації на створення ***ObjectMapper*** виліляються значні ресурси.

***Pattern "Command"*** 
- побудоване консольне меню програми. Відповідно до обраної користувачем дії, виконується певна 
команда. При цьому, командами є як запити користувача що реалізують інтерфейс ***Action***, так і відображення меню
користувачеві - ті що реалізують інтерфейс ***MenuAction***. Останні є ***Enum***, котрі розміщенні всередині
відповідного класу і являють собою перелік команд та їх описів що будуть відображені користувачеві. 

***Pattern "Template Method"*** 
- абстрактний клас ***MenuTemplate*** описує шаблонну дію побудови меню користувача, та мають астрактний метод
***setActionList()***. Наслідники цього класу інкапсулюють меню ***Enum*** описаний вище, а реалізація методу
***setActionList()*** ініціалізує поле класу ***MenuTemplate*** масивом команд ***MenuAction***.


#### Тестування
У проекті виконано рестування репозиторіїв. Для тестів використовуються окремі файлові сховища. Основний "репозиторій" 
замінюється під час тестування за допомогою ***Mockito***. Як наслідок - тестування виконувати безпечно при наявності 
записів у файлах.

#### Багатопоточність. Одночасні запити
Програма не разрахована на роботу з багатопоточністю. Всі Singleton не синхронізовані. 
