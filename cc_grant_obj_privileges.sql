Prompt Executing cc_grant_obj_privileges.sql ...

declare
c_dev_instance             CONSTANT VARCHAR2(35) := 'devorcl';
c_etl_instance             CONSTANT VARCHAR2(35) := 'devoetl';
v_instance                 VARCHAR2(35);

begin
  v_instance := SYS_CONTEXT('USERENV', 'INSTANCE_NAME');

  if   v_instance = c_dev_instance
  or   v_instance = c_etl_instance
  then NULL;
  else ACQETL.ETL_APPS_MISC_TASKS.SET_OBJECT_PRIVILEGES('ACQBBMROLE');
  end if;
end;
/
