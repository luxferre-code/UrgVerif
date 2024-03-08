DROP TABLE IF EXISTS centre CASCADE;
DROP TABLE IF EXISTS vehicule CASCADE;
DROP TABLE IF EXISTS compartiment CASCADE;
DROP TABLE IF EXISTS materiel CASCADE;

CREATE TABLE centre
(
    id SERIAL,
    nom TEXT,
    adresse TEXT,
    chef_centre TEXT,
    telephone TEXT,
    CONSTRAINT pkey_centre PRIMARY KEY (id)
);

CREATE TABLE vehicule
(
    immatriculation VARCHAR(7),
    type_engin TEXT,
    id_centre INT,
    CONSTRAINT pkey_vehicule PRIMARY KEY (immatriculation),
    CONSTRAINT fkey_vehicule_centre FOREIGN KEY (id_centre) REFERENCES centre(id) ON DELETE CASCADE
);

CREATE TABLE compartiment
(
    id SERIAL,
    nom TEXT,
    CONSTRAINT pkey_compartiment PRIMARY KEY (id)
);

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