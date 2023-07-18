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
    course_id uuid,
    student_id uuid,
    degree double precision NOT NULL,
    CONSTRAINT student_course_course_fk FOREIGN KEY (course_id) REFERENCES course(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT student_course_student_fk FOREIGN KEY (student_id) REFERENCES student(id) ON UPDATE CASCADE ON DELETE CASCADE
);
