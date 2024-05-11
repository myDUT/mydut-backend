SET search_path TO mydut;

CREATE TABLE IF NOT EXISTS "role"
(
    role_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    role_name varchar(255) NOT NULL,
    description text
    );

CREATE TABLE IF NOT EXISTS room
(
    room_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name varchar(255) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS "user"
(
    user_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    role_id uuid NOT NULL,
    username varchar(255) UNIQUE NOT NULL,
    fullname varchar(255),
    student_code varchar(255) UNIQUE NOT NULL,
    homeroom_class varchar(255) NOT NULL,
    email varchar(255),
    "password" varchar(255),
    created_at timestamp,
    created_by uuid,
    updated_at timestamp,
    updated_by uuid,

    FOREIGN KEY (role_id) REFERENCES "role"(role_id)
    );

CREATE TABLE IF NOT EXISTS clazz
(
    class_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    room_id uuid NOT NULL,
    name text NOT NULL,
    day_of_week integer NOT NULL,
    time_from timestamp NOT NULL,
    time_to timestamp NOT NULL,
    date_from timestamp NOT NULL,
    date_to timestamp NOT NULL,
    total_student integer NULL,
    class_code varchar(255) UNIQUE NOT NULL,
    created_at timestamp,
    created_by uuid,
    updated_at timestamp,
    updated_by uuid,

    FOREIGN KEY (room_id) REFERENCES "room"(room_id)
    );

CREATE TABLE IF NOT EXISTS enrollment
(
    enrollment_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id uuid NOT NULL,
    class_id uuid NOT NULL,
    status varchar,
    created_at timestamp,
    created_by uuid,
    updated_at timestamp,
    updated_by uuid,

    FOREIGN KEY (user_id) REFERENCES "user"(user_id),
    FOREIGN KEY (class_id) REFERENCES "clazz"(class_id)
    );

CREATE TABLE IF NOT EXISTS coordinate
(
    coordinate_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    longitude varchar(50) NOT NULL,
    latitude varchar(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS lesson
(
    lesson_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    class_id uuid NOT NULL,
    coordinate_id uuid NOT NULL,
    datetime_from timestamp NOT NULL,
    datetime_to timestamp NOT NULL,
    description text,
    present_student integer,
    absent_student integer,

    FOREIGN KEY (class_id) REFERENCES "clazz"(class_id),
    FOREIGN KEY (coordinate_id) REFERENCES "coordinate"(coordinate_id)
    );

CREATE TABLE IF NOT EXISTS attendence_record
(
    attendence_record_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id uuid NOT NULL,
    lesson_id uuid NOT NULL,
    coordinate_id uuid NOT NULL,
    time_in timestamp NOT NULL,
    is_valid_check_in boolean NOT NULL,
    is_facial_recognition boolean NOT NULL,

    FOREIGN KEY (user_id) REFERENCES "user"(user_id),
    FOREIGN KEY (lesson_id) REFERENCES lesson(lesson_id),
    FOREIGN KEY (coordinate_id) REFERENCES coordinate(coordinate_id)
    );

CREATE TABLE IF NOT EXISTS evidence_image
(
    evidence_image_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    lesson_id uuid NOT NULL,
    url text NOT NULL,

    FOREIGN KEY (lesson_id) REFERENCES lesson(lesson_id)
    );