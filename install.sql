DROP TABLE IF EXISTS centre CASCADE;
DROP TABLE IF EXISTS type_engin CASCADE;
DROP TABLE IF EXISTS vehicule CASCADE;
DROP TABLE IF EXISTS compartiment CASCADE;
DROP TABLE IF EXISTS materiel CASCADE;
DROP TABLE IF EXISTS verif_history CASCADE;
DROP TABLE IF EXISTS gallon CASCADE;
DROP TABLE IF EXISTS agent CASCADE;
DROP TABLE IF EXISTS chef_centre CASCADE;

CREATE TABLE centre
(
    id SERIAL,
    nom TEXT,
    adresse TEXT,
    telephone TEXT,
    CONSTRAINT pkey_centre PRIMARY KEY (id)
);

INSERT INTO centre(nom, adresse, telephone)
VALUES ('CIS Oignies', '105 Rue des 80 Fusillés, 62590 Oignies', '0321692618');

CREATE TABLE gallon
(
    id SERIAL,
    gallon TEXT,
    CONSTRAINT pkey_gallon PRIMARY KEY (id)
);

INSERT INTO gallon(gallon)
VALUES 
    ('Sapeur 2ème classe'),
    ('Sapeur 1ère classe'),
    ('Caporal'),
    ('Caporal-chef'),
    ('Sergent'),
    ('Sergent-chef'),
    ('Adjudant'),
    ('Adjudant-chef'),
    ('Lieutenant'),
    ('Capitaine'),
    ('Commandant'),
    ('Lieutenant-colonel'),
    ('Colonel'),
    ('Administrateur du site');

CREATE TABLE IF NOT EXISTS agent
(
    matricule TEXT UNIQUE,
    nom TEXT NOT NULL,
    prenom TEXT NOT NULL,
    password TEXT NOT NULL,
    mail TEXT NOT NULL CHECK (mail ~* '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$') UNIQUE,
    id_centre INT,
    id_gallon INT DEFAULT 0,
    CONSTRAINT pkey_agent PRIMARY KEY (matricule),
    CONSTRAINT fkey_agent_centre FOREIGN KEY (id_centre) REFERENCES centre(id) ON DELETE CASCADE,
    CONSTRAINT fkey_agent_gallon FOREIGN KEY (id_gallon) REFERENCES gallon(id) ON DELETE CASCADE
);

CREATE TABLE chef_centre
(
    id_centre INT,
    matricule TEXT,
    CONSTRAINT pkey_chef_centre PRIMARY KEY (id_centre),
    CONSTRAINT fkey_chef_centre_centre FOREIGN KEY (id_centre) REFERENCES centre(id) ON DELETE CASCADE,
    CONSTRAINT fkey_chef_centre_agent FOREIGN KEY (matricule) REFERENCES agent(matricule) ON DELETE CASCADE
);

CREATE TABLE type_engin
(
    nom TEXT,
    CONSTRAINT pkey_type_engin PRIMARY KEY (nom)
);

INSERT INTO type_engin(nom)
VALUES  ('VSAV'),
        ('FPT'),
        ('FSR');

CREATE TABLE vehicule
(
    immatriculation VARCHAR(7),
    type_engin TEXT,
    id_centre INT,
    CONSTRAINT pkey_vehicule PRIMARY KEY (immatriculation),
    CONSTRAINT fkey_vehicule_centre FOREIGN KEY (id_centre) REFERENCES centre(id) ON DELETE CASCADE,
    CONSTRAINT fkey_vehicule_type_engin FOREIGN KEY (type_engin) REFERENCES type_engin(nom) ON DELETE CASCADE
);

CREATE TABLE compartiment
(
    id SERIAL,
    nom TEXT,
    type_engin TEXT,
    CONSTRAINT pkey_compartiment PRIMARY KEY (id),
    CONSTRAINT fkey_compartiment_type_engin FOREIGN KEY (type_engin) REFERENCES type_engin(nom) ON DELETE CASCADE
);

INSERT INTO compartiment(nom, type_engin)
VALUES  ('Coffre haut "Capucine"', 'VSAV'),
        ('Paroi', 'VSAV'),
        ('Placard Kit', 'VSAV'),
        ('Rangements', 'VSAV'),
        ('DSA', 'VSAV'),
        ('Tiroir 1', 'VSAV'),
        ('Tiroir 2', 'VSAV'),
        ('Tiroir Ventilation', 'VSAV'),
        ('Tiroir Insufflateur', 'VSAV'),
        ('Tiroir Accouchement', 'VSAV'),
        ('Les niveaux', 'VSAV'),
        ('Cabine avant conducteur', 'VSAV'),
        ('Porte latérale', 'VSAV'),
        ('Les essais', 'VSAV'),
        ('Carrosserie', 'VSAV'),
        ('1er gauche', 'VSAV'),
        ('2eme gauche', 'VSAV'),
        ('Plateau', 'VSAV'),
        ('Paroi droite (4)', 'VSAV'),
        ('Paroi milieu', 'VSAV');

CREATE TABLE materiel
(
    id SERIAL,
    id_compartiment INT,
    id_vehicule VARCHAR(7),
    nom TEXT,
    quantite INT,
    valide BOOLEAN DEFAULT TRUE,
    CONSTRAINT pkey_materiel PRIMARY KEY (id),
    CONSTRAINT fkey_materiel_compartiment FOREIGN KEY (id_compartiment) REFERENCES compartiment(id) ON DELETE CASCADE,
    CONSTRAINT fkey_materiel_vehicule FOREIGN KEY (id_vehicule) REFERENCES vehicule(immatriculation) ON DELETE CASCADE
);

CREATE TABLE verif_history
(
    id SERIAL,
    immatriculation TEXT,
    date_verif DATE DEFAULT CURRENT_DATE,
    time_verif TIME DEFAULT CURRENT_TIME,
    matricule TEXT,
    CONSTRAINT pkey_verif_history PRIMARY KEY (id),
    CONSTRAINT fkey_verif_history_immatriculation FOREIGN KEY (immatriculation) REFERENCES vehicule(immatriculation) ON DELETE CASCADE 
);

-- Création d'une vue qui permet de voir quand est-ce que chaque véhicule a été vérifié pour la dernière fois
CREATE OR REPLACE VIEW last_verif AS
SELECT immatriculation, date_verif, time_verif, matricule
FROM verif_history
WHERE (immatriculation, date_verif, time_verif) IN
(
    SELECT immatriculation, MAX(date_verif), MAX(time_verif)
    FROM verif_history
    GROUP BY immatriculation
);
