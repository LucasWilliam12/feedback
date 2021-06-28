CREATE KEYSPACE feedbackkeyspace
WITH replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};

use feedbackkeyspace;

CREATE TABLE feedbacks(
    id uuid PRIMARY KEY,
    titulo text,
    descricao text,
    createdAt timestamp
);