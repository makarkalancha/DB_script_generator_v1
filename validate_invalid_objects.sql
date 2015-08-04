Prompt FILL OWNER Variable
  DEFINE OWNER = &OWNER

set verify off

DECLARE

BEGIN
  FOR I IN 1..5
  LOOP
    FOR cur_rec IN
        (SELECT owner,
                object_name,
                object_type,
                DECODE(object_type, 'TYPE',         1,
                                    'FUNCTION',     2,
                                    'PROCEDURE',    3,
                                    'PACKAGE',      4,
                                    'PACKAGE BODY', 5,
                                    'TRIGGER',      6,
                                    'VIEW',         7,
                                    'TYPE BODY',    8) AS recompile_order
          FROM  all_objects
         WHERE  object_type IN ('TYPE', 'FUNCTION', 'VIEW',
                                'PROCEDURE','TRIGGER','PACKAGE', 'PACKAGE BODY',
                                'TYPE BODY')
           AND  status != 'VALID'
           and owner = UPPER('&OWNER')
         ORDER BY 4)
    LOOP
      BEGIN
        IF    cur_rec.object_type = 'PACKAGE BODY'
        then  EXECUTE IMMEDIATE 'ALTER PACKAGE ' || ' "' || cur_rec.owner ||
                 '"."' || cur_rec.object_name || '" COMPILE BODY';
        elsif cur_rec.object_type = 'TYPE BODY'
         then EXECUTE IMMEDIATE 'ALTER TYPE ' || ' "' || cur_rec.owner ||
                 '"."' || cur_rec.object_name || '" COMPILE BODY';
         else 
           EXECUTE IMMEDIATE 'ALTER ' || cur_rec.object_type ||
            ' "' || cur_rec.owner || '"."' || cur_rec.object_name || '" COMPILE';
        END IF;
      EXCEPTION
        WHEN OTHERS THEN
    --    DBMS_OUTPUT.put_line(cur_rec.object_type || ' : ' || cur_rec.owner ||
    --                         ' : ' || cur_rec.object_name);
          NULL;
      END;
    END LOOP;
  END LOOP;
END;
/

Prompt Validate INVALID OBJECTS

set echo off heading on verify off feedback on termout on
set define on linesize 2000 trimspool on
column object_name format a30
select object_type, object_name
  from all_objects
 where owner  = UPPER('&&OWNER')
   and status = 'INVALID';

