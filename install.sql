DROP TABLE IF EXISTS centre CASCADE;
DROP TABLE IF EXISTS vehicule CASCADE;
DROP TABLE IF EXISTS compartiment CASCADE;
DROP TABLE IF EXISTS materiel CASCADE;
DROP TABLE IF EXISTS verif_history CASCADE;

CREATE TABLE centre
(
    id SERIAL,
    nom TEXT,
    adresse TEXT,
    chef_centre TEXT,
    telephone TEXT,
    CONSTRAINT pkey_centre PRIMARY KEY (id)
);

INSERT INTO centre(nom, adresse, chef_centre, telephone)
VALUES ('CIS Oignies', '105 Rue des 80 Fusillés, 62590 Oignies', 'Lieutenant Fengler', '321692618');

CREATE TABLE vehicule
(
    immatriculation VARCHAR(7),
    type_engin TEXT,
    id_centre INT,
    CONSTRAINT pkey_vehicule PRIMARY KEY (immatriculation),
    CONSTRAINT fkey_vehicule_centre FOREIGN KEY (id_centre) REFERENCES centre(id) ON DELETE CASCADE
);

INSERT INTO vehicule(immatriculation, type_engin, id_centre)
VALUES ('FR941YQ', 'VSAV', 1);

CREATE TABLE compartiment
(
    id SERIAL,
    nom TEXT,
    CONSTRAINT pkey_compartiment PRIMARY KEY (id)
);

INSERT INTO compartiment(nom)
VALUES ('Coffre haut "Capucine"');

CREATE TABLE materiel
(
    id SERIAL,
    id_compartiment INT,
    id_vehicule VARCHAR(7),
    nom TEXT,
    quantite INT,
    CONSTRAINT pkey_materiel PRIMARY KEY (id),
    CONSTRAINT fkey_materiel_compartiment FOREIGN KEY (id_compartiment) REFERENCES compartiment(id) ON DELETE CASCADE,
    CONSTRAINT fkey_materiel_vehicule FOREIGN KEY (id_vehicule) REFERENCES vehicule(immatriculation) ON DELETE CASCADE
);

INSERT INTO materiel(id_compartiment, id_vehicule, nom, quantite)
VALUES (1, 'FR941YQ', 'Draps UU', 6);

CREATE TABLE verif_history
(
    id SERIAL,
    id_materiel INT,
    date_verif DATE DEFAULT CURRENT_DATE,
    resultat TEXT,
    matricule TEXT,
    CONSTRAINT pkey_verif_history PRIMARY KEY (id),
    CONSTRAINT fkey_verif_history_materiel FOREIGN KEY (id_materiel) REFERENCES materiel(id) ON DELETE CASCADE
);

INSERT INTO verif_history(id_materiel, resultat)
VALUES (1, 'OK', '00019459');

-- Création d'une view qui permet de voir quand est-ce que chaque véhicule a été vérifié pour la dernière fois
CREATE OR REPLACE VIEW last_verif AS
SELECT vehicule.immatriculation, MAX(verif_history.date_verif) AS last_verif, verif_history.matricule
FROM vehicule
LEFT JOIN materiel ON vehicule.immatriculation = materiel.id_vehicule
LEFT JOIN verif_history ON materiel.id = verif_history.id_materiel
GROUP BY vehicule.immatriculation, verif_history.matricule;
