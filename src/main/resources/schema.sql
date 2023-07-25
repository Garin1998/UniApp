CREATE TABLE IF NOT EXISTS student (
    id uuid NOT NULL,
    first_name text NOT NULL,
    last_name text NOT NULL,
    term integer NOT NULL,
    CONSTRAINT student_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS lecturer (
    id uuid NOT NULL,
    first_name text NOT NULL,
    last_name text NOT NULL,
    CONSTRAINT lecturer_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS course (
    id uuid NOT NULL,
    name text NOT NULL,
    ects double precision NOT NULL,
    lecturer uuid,
    CONSTRAINT course_pk PRIMARY KEY (id),
    CONSTRAINT course_lecturer_fk FOREIGN KEY (lecturer) REFERENCES lecturer(id)
);

CREATE TABLE IF NOT EXISTS student_course (
    id uuid NOT NULL,
    course_id uuid,
    student_id uuid,
    degree double precision NOT NULL,
    CONSTRAINT student_course_pk PRIMARY KEY (id),
    CONSTRAINT student_course_course_fk FOREIGN KEY (course_id) REFERENCES course(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT student_course_student_fk FOREIGN KEY (student_id) REFERENCES student(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS role (
    id uuid NOT NULL,
    name TEXT,
    CONSTRAINT role_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_in_db (
    id uuid NOT NULL,
    name TEXT,
    password TEXT,
    user_roles uuid,
    registration_timestamp TIMESTAMP,
    CONSTRAINT user_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_role (
    user_id uuid,
    role_id uuid,
    CONSTRAINT user_role_user_fk FOREIGN KEY (user_id) REFERENCES user_in_db(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT user_role_role_fk FOREIGN KEY (role_id) REFERENCES role(id) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO role ("ID", "NAME") VALUES ('00b94aad-1430-419d-a9d3-ff6cd6bbe6fe', 'ROLE_USER');