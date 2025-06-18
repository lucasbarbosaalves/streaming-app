create table category (
    id VARCHAR(36) not null PRIMARY KEY,
    name VARCHAR(255) not null,
    description VARCHAR(4000) not null,
    active BOOLEAN not null default TRUE,
    created_at DATETIME(6) not null,
    updated_at DATETIME(6) not null,
    deleted_at DATETIME(6) null
);