set search_path to mydut;

alter table "user"
    alter column password set not null;

alter table lesson
    alter column coordinate_id drop not null;

alter table attendence_record rename to attendance_record;

alter table attendance_record drop constraint attendence_record_pkey;

alter table attendance_record rename column attendence_record_id to attendance_record_id;

alter table attendance_record add constraint attendance_record_pkey primary key (attendance_record_id);