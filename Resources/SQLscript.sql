
CREATE TABLE user (
    id int NOT NULL AUTO_INCREMENT,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    mail varchar(255) NOT NULL,
    secret varchar(255) NOT NULL,
    firstime boolean,
    digits int,
    alghorithm varc(255)
    PRIMARY KEY (id)
);
