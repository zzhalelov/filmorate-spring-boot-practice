Задача

Фильмов много — и с каждым годом становится всё больше. Чем их больше, тем больше разных оценок. Чем больше оценок, тем сложнее сделать выбор. Однако не время сдаваться! Пишем бэкенд для сервиса, который будет работать с фильмами и оценками пользователей, а также возвращать топ-5 фильмов, рекомендованных к просмотру. Теперь ни мне, ни вашим друзьям не придётся долго размышлять, что посмотреть вечером.

Cоздадим каркас Spring Boot приложения Filmorate (от англ. film — «фильм» и rate — «оценивать»). В дальнейшем сервис будет обогащаться новым функционалом и с каждой пройденной темой становиться лучше.

Модели данных

Создадим пакет model. Добавьте в него два класса — Film и User. Это классы — модели данных приложения.

У model.Film должны быть следующие свойства:

целочисленный идентификатор — id;
название — name;
описание — description;
дата релиза — releaseDate;
продолжительность фильма — duration.
Свойства model.User:

целочисленный идентификатор — id;
электронная почта — email;
логин пользователя — login;
имя для отображения — name;
дата рождения — birthday.
Хранение данных

Сейчас данные можно хранить в памяти приложения — так же, как вы поступили в случае с менеджером задач. Для этого используем контроллер.

REST-контроллеры
Создадим два класса-контроллера. FilmController будет обслуживать фильмы, а UserController — пользователей. Убедимся, что созданные контроллеры соответствуют правилам REST.

Добавим в классы-контроллеры эндпоинты с подходящим типом запроса для каждого из случаев.

Для FilmController:

добавление фильма;
обновление фильма;
получение всех фильмов.
Для UserController:

создание пользователя;
обновление пользователя;
получение списка всех пользователей.
Эндпоинты для создания и обновления данных должны также вернуть созданную или изменённую сущность.

Валидация

Проверим данные, которые приходят в запросе на добавление нового фильма или пользователя. Эти данные должны соответствовать определённым критериям. Для Film:

название не может быть пустым;
максимальная длина описания — 200 символов;
дата релиза — не раньше 28 декабря 1895 года;
продолжительность фильма должна быть положительной.
Для User:

электронная почта не может быть пустой и должна содержать символ @;
логин не может быть пустым и содержать пробелы;
имя для отображения может быть пустым — в таком случае будет использован логин;
дата рождения не может быть в будущем.
Подсказка: как обработать ошибки

Логирование
Добавим логирование для операций, которые изменяют сущности — добавляют и обновляют их. Также логируем причины ошибок — например, если валидация не пройдена. Это считается хорошей практикой.
Воспользуемся библиотекой slf4j для логирования и объявляйте логер для каждого класса — так будет сразу видно, где в коде выводится та или иная строка.
