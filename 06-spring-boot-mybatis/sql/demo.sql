CREATE TABLE T_USER (
                        `id` varchar(50) NOT NULL,
                        `url` varchar(255) NOT NULL,
                        `user_name` varchar(50) NOT NULL,
                        `password` varchar(50) NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO T_USER(`id`, `url`, `user_name`, `password`) VALUES ('db1', 'jdbc:mysql://192.168.10.200:3306/one?useUnicode=true&characterEncoding=utf8&useSSL=false', 'root', '123456');
INSERT INTO T_USER(`id`, `url`, `user_name`, `password`) VALUES ('db2', 'jdbc:mysql://192.168.10.200:3306/two?useUnicode=true&characterEncoding=utf8&useSSL=false', 'root', '123456');
