CREATE TABLE `todos` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` varchar(128) NOT NULL, 
    `description` varchar(1024) NOT NULL,
    `user_id` varchar(256) NOT NULL
);
