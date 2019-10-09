
CREATE TABLE IF NOT EXISTS students (
    id INTEGER NOT NULL PRIMARY KEY,
    lname TEXT NOT NULL,
    fname TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS class (
    id INTEGER NOT NULL PRIMARY KEY,
    class_name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS regcourse (
    id INTEGER NOT NULL PRIMARY KEY,
    stud_id INTEGER NOT NULL,
    class_id1 INTEGER NOT NULL REFERENCES class(id)
)

-- \COPY students FROM './MOCK_DATA.csv' WITH (FORMAT csv);
-- \COPY class FROM './MOCK_DATA2.csv' WITH (FORMAT csv);
