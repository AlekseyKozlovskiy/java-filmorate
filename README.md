# java-filmorate
Template repository for Filmorate project.
My bd (/bd/bd.png)
Попробую написать пояснение небольшое, хотя на схеме и так вроде все понятно.
Начнем с таблицы User.
Поле UserId является первичным ключем и связь по ключу к таблицам Friends и Likes
В Friends 2 поля UserId и FriendId и поле, которое указывает, что дружба подтверждена
Film связана по FilmId с таблицей Genre, 
будут создаваться уникальные пары(Фильм1 - жанр1, Фильм1 - жанр2 и т.д.)
Как-то так =)

Select f.name
FROM film AS f
JOIN Likes AS l ON f.filmid = l.filmid
JOIN User AS u ON l.filmid = u.filmid
WHERE l.userid = '0';

SELECT * FROM genreType;

SELECT f.name
FROM film AS f
JOIN rating AS r ON f.rating = r.ratingid
WHERE r.description LIKE '%G%'