CREATE TABLE genres
(
    id         VARCHAR(36) NOT NULL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    active     BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at datetime(6)  NOT NULL,
    updated_at datetime(6)  NOT NULL,
    deleted_at datetime(6)  NULL
);

CREATE TABLE genres_categories
(
    genre_id    VARCHAR(36) NOT NULL,
    category_id VARCHAR(36) NOT NULL,
    CONSTRAINT idx_genre_category UNIQUE (genre_id, category_id) ,
    CONSTRAINT fk_genre_id FOREIGN KEY (genre_id) references genres (id) ON DELETE CASCADE,
    CONSTRAINT fk_category_id FOREIGN KEY (category_id) references categories (id) ON DELETE CASCADE
);