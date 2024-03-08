DROP TABLE IF EXISTS centre CASCADE;
DROP TABLE IF EXISTS vehicule CASCADE;

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