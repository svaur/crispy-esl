Запуск в следующем порядке:
1) ставим постгре (ща в принципе от версии не зависим. Ставим последнюю, 11.1)
2) запускаем в нем скрипт создания объектов бд (скрипт newDB.sql)
3) испортируем мавен проекты из исходников. В исходниках 2 папки - server- содержит основное ядро для функционирования АРМ и AccessPoint - ядро для точки доступа
4) запускаем мавен проект. В папках eslDir и itemDir содержатся данные для первоначальной инициализации данных бд. Можно поменять или удалить. Импортнутся автоматом при запуске приложения (там есть баг. Импорт происходит каждый раз при новом запуске идеи)
