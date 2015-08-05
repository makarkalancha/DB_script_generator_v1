DEFINE APPUSER        = DEVACQUISIO3
DEFINE TBL_TABLESPACE = DEVACQUISIO3NONSTAT
DEFINE IDX_TABLESPACE = DEVACQUISIO3NONSTATIDX
DEFINE OWNER          = DEVACQUISIO3NONSTAT
set verify off

SPOOL &OWNER._run_all.log

alter SESSION SET DDL_LOCK_TIMEOUT = 3600;
ALTER SESSION SET CURRENT_SCHEMA = &OWNER;

column START_DATE HEADING ''
select 'START_DATE ' || to_char(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') AS START_DATE from dual;

@01_insert_duplicates.sql
@02_create_TMP_YJADGTARGCH_YJADGBIDM.sql
@03_create_TMP_YJADGROUPBIDMULTIPLIER.sql

@validate_invalid_objects.sql
@cc_grant_obj_privileges.sql

set termout on
column END_DATE HEADING ''
select 'END_DATE   ' || to_char(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') AS END_DATE from dual;
SPOOL OFF
exit
