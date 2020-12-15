# Reverse Market Android

В данной части курсового проекта необходимо было разработать приложение для Android на языке программирования Kotlin, которое предоставляло бы пользователю клиентскую часть
логики всего проекта.

## Участники
Проект выполнили студенты группы 3530904/80102:
- Нуртдинов А.А.
- Гусаров И.Е.
- Зыбкин В.Ю.
- Рахматуллин А.Р.

# Этапы проекта 

## Поставнока проблемы 

Проект решает проблему обмена вещами между людьми. Периодически каждому человеку нужно продавать ненужные вещи или покупать какие-то вещи. Проект позволяет найти определенные товары у людей, которые хотят их продать, и наоборот, найти человека, который готов купить ваш товар.

## Требования 

![image](https://user-images.githubusercontent.com/17166741/102255490-36bdb000-3f1b-11eb-936b-31d6bef31d2c.png)

## Диаграммы 

- System Context diagram

![image](https://user-images.githubusercontent.com/17166741/102256213-25c16e80-3f1c-11eb-9ad8-81aeac934c52.png)
- Container diagram

![image](https://user-images.githubusercontent.com/17166741/102256218-278b3200-3f1c-11eb-903c-de51f2b6242e.png)
- Component diagram (Mobile App)

![image](https://user-images.githubusercontent.com/17166741/102256225-2954f580-3f1c-11eb-87ab-e53301e47329.png)

# Кодирование и отладка

Клиентская часть реализована с помощью Android SDK. Были реализованы различные фрагменты и графические элементы для общения с пользователем. Клиентская часть позволяет авторизироваться пользователям через их google аккаунт, создавать заказы, предложения и тд.

Проект написан на языке Kotlin, для запросов в сеть используем библиоткеу `retrofit`, библиотека `dagger 2` для инъекции зависимостей, для загрузки фотографий используем `glide`, `lottie` для красивых анимаций и `navigation component` для навигации. Для сборки используем gradle.

# Тестирование
Для тестирования были использованы библиотеки `junit` и `espresso`

# Сборка

Для начала необходимо склонить проект из github:

    git clone https://github.com/reverse-market/android.git

Далее из командной строки в папке проекте нужно выполнить следующую команду для сборки:

    ./gradlew build

Для того чтобы успешно прошли все тесты необходимо правильно настроить android устройство:
    Отключаем анимацию на вашем устройстве, для этого включаем параметры разработчика:
1.	Открываем приложение «Настройки»
2.	Выбираем «О телефоне» в самом низу страницы
3.	Нажимаем на пункт «номер сборки» 7 раз
4.	Возвращаемся на предыдущий экран и находим параметры разработчика внизу 
Теперь переходим в параметры разработчика в приложении «Настройки» и в разделе «Отрисовка» выключаем следующие параметры:
1.	Window animation scale (Анимация окон)
2.	Transition animation scale (Анимация переходов)
3.	Animator duration scale (Длительность анимации)

Чтобы запустить Android тесты необходимо подключить android устройство к компьютеру или использовать Android Virtual Device, предварительно настроив его по инструкции выше. Далее открываем командную строку в папке проекта и выполняем следующую команду:

    ./gradlew connectedAndroidTest
# Примеры работы

Основные экраны приложения

![image](https://user-images.githubusercontent.com/17166741/102265192-d5500e00-3f27-11eb-8fe7-5f0db8aa36d2.png)
![image](https://user-images.githubusercontent.com/17166741/102265203-da14c200-3f27-11eb-9cac-c6b5298d6d05.png)
![image](https://user-images.githubusercontent.com/17166741/102265277-f44ea000-3f27-11eb-9d12-cbb1e125b3cc.png)
