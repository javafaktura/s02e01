ALTER USER 'user'@'%' IDENTIFIED WITH mysql_native_password BY 'admin123';
FLUSH PRIVILEGES;

CREATE TABLE springcore.tab(
    id INT PRIMARY KEY,
    val VARCHAR(32)
);

INSERT INTO springcore.tab(id, val) VALUES(1, 'FOO');
INSERT INTO springcore.tab(id, val) VALUES(2, 'BAR');
INSERT INTO springcore.tab(id, val) VALUES(3, 'FOO');
