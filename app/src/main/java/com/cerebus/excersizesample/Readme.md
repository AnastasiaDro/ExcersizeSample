
# Приложение баннер
Всем привет!
Это примерная разбивка классов, которую вы можете использовать

В папке **api** - data классы и интерфейсы, от которых желательно наследоваться
В папке **impl** - ui и примеры наследования

По сути вы должны сделать свою  папку **impl** и вставить получившийся Screen в Activity
(да, она не на compose, но мы пишем на Compose, можете в своей ветке поменять активити на Compose Activity)

В каждом классе есть описания и дока
Тут просто все собрано в одном месте

## Описание ТЗ
У нас есть экран, у экрана есть ViewModel, они общаются между собой в стиле MVVM (я написала шаблон с примерами)
Внутри ViewModel у нас есть

### Ссылка на схему
https://drive.google.com/file/d/1kvIB0W0z0dtuLBRdWgiNSMosD_CYo8aa/view?usp=share_link

### ViewModel
1. ExcersizeListener - он обрабатывает всякие события упражнения
2. ExcersizeProvider - он выдает ViewModel-и упражнения

### Data модели
Все то, в чем мы храним/перерабатываем данные
1. ExcersizeData - данные об упражнении, внутри все, что нам нужно для обработки логики и отрисовки
2. ExcersizeType - тип упражнения, внутри будет enum всех-всех упражнений
3. LevelData - лежит внутри ExcersizeData, содержит данные об уровне
4. Parameters - лежит внутри LevelData, будет содержать всё-всё для отрисовки уровня, там мы сами задаете свои параметры

получается матрешка:

ExcersizeData:
- разные параметры
- LevelData
- --Parameters

