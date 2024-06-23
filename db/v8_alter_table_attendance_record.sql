set search_path to mydut;

alter table mydut.attendance_record
    alter column coordinate_id drop not null;
alter table mydut.attendance_record
    alter column time_in drop not null;
alter table mydut.attendance_record
    alter column is_valid_check_in drop not null;
alter table mydut.attendance_record
    alter column is_facial_recognition drop not null;
