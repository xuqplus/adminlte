CREATE TABLE IF NOT EXISTS user_register (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255),
    expires_at BIGINT,
    name VARCHAR(255),
    password VARCHAR(255),
    verify_code VARCHAR(255),
    verify_url VARCHAR(255),
    verify_count INTEGER,
    PRIMARY KEY (id)
)  ENGINE=InnoDB;
ALTER TABLE user_register ADD CONSTRAINT UK_73qid98u3773n8i1ctaxd0uon UNIQUE (email);
ALTER TABLE user_register ADD CONSTRAINT UK_7xrk61huffd71v60h3d2970l4 UNIQUE (name);