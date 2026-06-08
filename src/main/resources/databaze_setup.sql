-- Vytvoření a použití databáze (pokud ještě neexistuje)
CREATE DATABASE IF NOT EXISTS arete_db;
USE arete_db;

-- Tabulka pro uživatele
CREATE TABLE IF NOT EXISTS uzivatel (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(150) UNIQUE,
    age INT,
    gender BOOLEAN,
    weight INT,
    height INT,
    physical_activity VARCHAR(50),
    personal_goal VARCHAR(50),
    recommended_kcal INT,
    recommended_proteins INT,
    recommended_fats INT,
    recommended_carbs INT,
    recommended_sugars INT,
    actual_kcal INT,
    actual_protein INT,
    actual_fats INT,
    actual_carb INT,
    actual_sugars INT,
    preferences_tags JSON
);

-- Tabulka pro recepty
CREATE TABLE IF NOT EXISTS recept (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    difficulty VARCHAR(50),
    kcal INT,
    proteins INT,
    fats INT,
    carbs INT,
    sugars INT,
    prepare_time INT,
    type_food VARCHAR(50),
    rich_in VARCHAR(50),
    instructions TEXT,
    tags TEXT,
    diets TEXT
);

-- Tabulka pro ingredience (napojená na recept)
CREATE TABLE IF NOT EXISTS ingredience (
    id INT AUTO_INCREMENT PRIMARY KEY,
    recept_id INT,
    name VARCHAR(100) NOT NULL,
    amount VARCHAR(50), -- ZMĚNĚNO Z INT NA VARCHAR(50) KVŮLI TEXTŮM JAKO "pinch" NEBO "1/2"
    unit VARCHAR(50),
    FOREIGN KEY (recept_id) REFERENCES recept(id) ON DELETE CASCADE
);

-- NOVÉ: Propojovací tabulka pro oblíbené recepty
CREATE TABLE IF NOT EXISTS oblibeny_recept (
    uzivatel_email VARCHAR(150),
    recept_name VARCHAR(255),
    PRIMARY KEY (uzivatel_email, recept_name)
);

-- Vložení dvou výchozích uživatelů (1 = muž, 0 = žena)
INSERT INTO uzivatel (name, password, email, age, gender, weight, height, physical_activity, personal_goal)
VALUES
('Vojta Admin', 'admin123', 'admin@arete.cz', 25, 1, 80, 185, 'MODERATE', 'MUSCLE_GAIN'),
('Ema Uživatelská', 'heslo123', 'ema@arete.cz', 22, 0, 60, 168, 'LIGHT', 'WEIGHT_LOSS')
ON DUPLICATE KEY UPDATE email=email; -- Zabrání pádu, pokud uživatelé už existují