set search_path to mydut;
alter table mydut.lesson add is_enable_check_in boolean;
alter table mydut.clazz add facial_data_url text;
alter table mydut.attendance_record add distance double precision;
alter table mydut.enrollment alter column status type integer using (status::integer);