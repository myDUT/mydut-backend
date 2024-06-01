conn=`cat db_connection.txt`
psql "$conn" -f  v1_init_db.sql
psql "$conn" -f  v2_revise_table_user.sql
psql "$conn" -f  v3_add_data_table_role.sql
psql "$conn" -f  v4_add_data_table_room.sql
psql "$conn" -f  v5_revise_table_user.sql
psql "$conn" -f  v6_revise_table_lesson_class_enrollment.sql
psql "$conn" -f  v7_alter_table_class.sql