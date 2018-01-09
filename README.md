**Графическое оконное приложение поиска кратчайшего пути в лабиринте**

**Стэк технологий:** Java 7, Java Swing, Apache Maven

### Поддерживаемые возможности:
------------
- Задание числа строк и столбцов таблицы лабиринта. В случае размерностей больше 10x10 клеток возможна прокрутка.
- Построение лабиринта: расстановка/удаление препятсвий, обозначение начала/конца пути
- Загрузка готового лабиринта из файла
- Автоматическое масштабирование (только пропорционально) изображения иконок на клетках лабиринта в зависимости от размера клеток
- Поиск кратчайшего пути. Используется алгоритм A*.

### Требования
------------
JVM 1.7+ для запуска exe-файла

Maven, JDK 1.7+ для генерации exe-файла

### Развертывание приложения
------------

1.  **Клонируйте репозиторий**

    ```
    $ git clone https://github.com/Njuton/LabyrinthSwingApplication.git c:\myapp
    ```
2.  **Запустите исполняемый файл**
     ```
     $ c:\myapp\target\labyrinth.exe
     ```
     Приложение будет запущено. Если Вы хотите самостоятельно сгенерировать exe файл, выполните
     ```
     $ cd c:\myapp
     $ mvn clean package
     ```
     Исполняемый файл будет в каталоге myapp\target.
