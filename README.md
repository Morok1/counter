
#Отчёт

###Цель работы
Исследование производительности различных имплементаций счетчика

Производительность меряется в операциях в единицу времени(Mode.throupghut)

###Теоретическое введение
####Сихронизация
Первое решение параллельного доступа к объекту  - синхронизация  -  ситуация, при которой существуют несколько конкурирующих
 потоков и только один из них захватывает(блокирует) объект, а другие потоки ждут.
 ***
Здесь есть две проблемы:
 1. Время, затрачиваемое на попытки блокировки объекта разными потоками.
 2. Отсутствует свойство wait-free другие потоки ждут освобождения
3. Еще одна проблема, связанная с механизмами, основанными на блокировании, заключается в том, что если поток, контролирующий блокирование, запаздывает (из-за ошибок на странице, отставания планировщика или других неожиданностей), то ни один поток, нуждающийся в этой блокировке, не продвинется в обработке.
4. Также невозможно прервать поток, который ожидает блокировки, так же как невозможно опрашивать или пытаться получить блокировку, не будучи готовым к долгому ожиданию. Синхронизация также требует, чтобы блокировка была снята в том же стековом фрейме, в котором была начата, это правило верно почти все время (и хорошо взаимодействует с обработкой исключительных ситуаций),
####Volatile
Изменяемые (volatile) переменные тоже можно использовать для более экономного хранения переменных совместного доступа (по сравнению с синхронизацией), но это наложит некоторые ограничения. Если записи в изменяемых переменных гарантированно видны остальным потокам, то передать последовательность читать-модифицировать-читать в операциях атомарных невозможно, а это, например, означает, что изменяемую переменную нельзя использовать для надежной реализации взаимного исключения (mutex - мьютекс) или счетчика.	
####Locks
При использовании Lock:
Класс reentrantLock, который реализует Lock, имеет те же параллелизм и семантику памяти, что и synchronized, но также имеет дополнительные возможности, такие как опрос о блокировании (lock polling), ожидание блокирования заданной длительности и прерываемое ожидание блокировки.
Lock решает проблему конкуренции за блокировку, когда много потоков пытаются получить доступ к ресурсу совместного использования, JVM потребуется меньше времени на установление очередности потоков и больше времени на ее выполнение.


### Соотношения между разными типами синхронизации
Атомарные переменные имеют преимущество перед ReentrantLock, который в свою очередь имеет преимущество в большистве случаев перед синхронизацией

###Полученные результаты и интерпретация
1. * На одном потоке synchronized показал лучшие результаты. Это объясняется тем, 
что  то будет работать первый (biased) тип блокировки, когда монитор привязывается (bias) к потоку с помощью единственной операции CAS 
и все последующие заходы будут проходить без каких либо существенных накладок. Заметим, что операция CAS используется при выставлении типа блокировки
   * Почему все выходят на константу? Так как операции count++;  -  три операции поэтому при n>3 тредов различий не будет, 
   так первые три треда полностью точно выполнят этот кусок кода.
   *
2. Почему volatile всех побеждает? 
Простая схема работы тредов с общей памятью: 

    1. 
    * Тред забирает данные из общей памяти
    * Выполняет действия и заканчивает работу
    *  Сбрасывет результаты в память
    
    2. 
       * Тред забирает данные из общей памяти
       * Выполняет действия и заканчивает работу
       *  Сбрасывет результаты в память
       * Продолжает выполнять действия и заканчивает работу
    и промежуточное сбрасывание в память доставляет большой delay работе программы
    *** 
    в случае с volatile данный код всегда будет выполняться по первому варианту и данные флашатся сразу после выполнения кода
 3. Почему synchronized самый низкий?
    Потому что при заходе нескольких потоков у нас происходит перепривязка и блокировка меняется либо на lightweight, либо на fat.
     В первом случае заход в блок сопровождается CAS, во втором испоьзуются примитивы операционной системы, что является очень дорогой операцией
 4.Про Reentrant:
 сначала пытается сделать CAS, пользуясь стандартными средствами Java, и если удастся, то захватывает блокировку и выполняется дальше,
  если же CAS обламывается, то вызов идет в ОС и поток паркуется (дизейблиться для скедулинга OС). 
  Т.е. получаем, что в отличие от synchronized при не конкурирующих потоках, ReentrantLock будет всегда делать CAS,
  чтобы зайти в критическую секцию и не важно заходит ли туда один поток или много. 
  При наличии конкуренции, потоки, которым повезло с CAS избегут переключения контекста и сразу пойдут на выполнение, другим же придется парковаться
   То есть по сути получаем вариант с volatile c точностью до создания локов

  

###Ссылки на статьи
При подготовке отчета использовались материалы из статей 
<http://www.javaspecialist.ru/2011/11/synchronized-vs-reentrantlock.html>
<https://www.ibm.com/developerworks/ru/library/j-jtp10264/>
<https://www.ibm.com/developerworks/ru/library/j-jtp11234/index.html>
<http://www.javaspecialist.ru/2011/04/java.html>

