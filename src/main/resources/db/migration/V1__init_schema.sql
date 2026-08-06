
CREATE TABLE users (
                       id       BIGINT       NOT NULL AUTO_INCREMENT,
                       nom      VARCHAR(255) NOT NULL,
                       prenom   VARCHAR(255) NOT NULL,
                       email    VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role     VARCHAR(50)  NOT NULL CHECK (role IN ('ADMIN', 'MANAGER', 'AGENT')),

                       PRIMARY KEY (id),
                       CONSTRAINT uq_users_email UNIQUE (email)
);


CREATE TABLE clients (
                         id        BIGINT       NOT NULL,
                         telephone VARCHAR(255),
                         ville     VARCHAR(255),

                         PRIMARY KEY (id),
                         CONSTRAINT fk_clients_users FOREIGN KEY (id) REFERENCES users (id)
                             ON DELETE CASCADE
);


CREATE TABLE agents (
                        id BIGINT NOT NULL,

                        PRIMARY KEY (id),
                        CONSTRAINT fk_agents_users FOREIGN KEY (id) REFERENCES users (id)
                            ON DELETE CASCADE
);


CREATE TABLE managers (
                          id BIGINT NOT NULL,

                          PRIMARY KEY (id),
                          CONSTRAINT fk_managers_users FOREIGN KEY (id) REFERENCES users (id)
                              ON DELETE CASCADE
);


CREATE TABLE produits (
                          id        BIGINT         NOT NULL AUTO_INCREMENT,
                          nom       VARCHAR(255),
                          categorie VARCHAR(255),
                          prix      DOUBLE         NOT NULL DEFAULT 0,
                          quantite  INT            NOT NULL DEFAULT 0,

                          PRIMARY KEY (id)
);


CREATE TABLE commandes (
                           id            BIGINT       NOT NULL AUTO_INCREMENT,
                           date_commande DATETIME,
                           status        VARCHAR(255),
                           client_id     BIGINT,

                           PRIMARY KEY (id),
                           CONSTRAINT fk_commandes_client FOREIGN KEY (client_id) REFERENCES clients (id)
                               ON DELETE SET NULL
);


CREATE TABLE lignecommandes (
                                id          BIGINT NOT NULL AUTO_INCREMENT,
                                quantite    INT    NOT NULL DEFAULT 0,
                                commande_id BIGINT,
                                produit_id  BIGINT,

                                PRIMARY KEY (id),
                                CONSTRAINT fk_lignecommandes_commande FOREIGN KEY (commande_id) REFERENCES commandes (id)
                                    ON DELETE CASCADE,
                                CONSTRAINT fk_lignecommandes_produit  FOREIGN KEY (produit_id)  REFERENCES produits (id)
                                    ON DELETE SET NULL
);
