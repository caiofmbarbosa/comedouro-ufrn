CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    email VARCHAR(60) NOT NULL UNIQUE,
    password VARCHAR(150) NOT NULL,
    registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE pets (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    breed VARCHAR(50) NOT NULL,
    pet_size SMALLINT NOT NULL,
    weight NUMERIC(6,2) NOT NULL,
    birth_date DATE NOT NULL,
    gender SMALLINT NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    tutor_id UUID NOT NULL,

    CONSTRAINT fk_pets_tutor
        FOREIGN KEY (tutor_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_pets_size
        CHECK (pet_size IN (1, 2, 3)),

    CONSTRAINT chk_pets_gender
        CHECK (gender IN (1, 2)),

    CONSTRAINT chk_pets_weight
        CHECK (weight > 0)
);

CREATE TABLE feeding_logs (
    id UUID PRIMARY KEY,
    fed_at TIMESTAMP NOT NULL,
    grams_released INTEGER NOT NULL,
    pet_id UUID NOT NULL,

    CONSTRAINT fk_feeding_logs_pet
        FOREIGN KEY (pet_id)
        REFERENCES pets(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_feeding_logs_grams
        CHECK (grams_released > 0)
);

CREATE TABLE reservoir (
    id BIGINT PRIMARY KEY,
    total_capacity INTEGER NOT NULL,
    remaining_grams INTEGER NOT NULL,
    last_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_reservoir_singleton
        CHECK (id = 1),

    CONSTRAINT chk_reservoir_capacity
        CHECK (total_capacity > 0),

    CONSTRAINT chk_reservoir_remaining
        CHECK (remaining_grams >= 0),

    CONSTRAINT chk_reservoir_remaining_capacity
        CHECK (remaining_grams <= total_capacity)
);

INSERT INTO reservoir (
    id,
    total_capacity,
    remaining_grams,
    last_updated_at
) VALUES (
    1,
    5000,
    5000,
    CURRENT_TIMESTAMP
);
