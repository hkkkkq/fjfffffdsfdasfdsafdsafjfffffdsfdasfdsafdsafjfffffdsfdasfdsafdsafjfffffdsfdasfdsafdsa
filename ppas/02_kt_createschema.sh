#!/bin/sh

if [ $# -ne 2 ]; then
    echo "Usage: $0 DATABASE_NAME OWNER"
    exit;
fi;

WORKDB=$1
WORKUSER=$2


WORKDB=`echo ${WORKDB} | tr [A-Z] [a-z]`;
WORKUSER=`echo ${WORKUSER} | tr [A-Z] [a-z]`;
export PGHOST=/tmp

## check database
db_count=`psql -t -c "select count(*) from pg_database where datname =lower('${WORKDB}')" | head -1`

if [ $db_count -eq 0 ]; then
        echo "Database ${WORKDB} is being made implicitly."
        ./01_kt_createdb.sh ${WORKDB}
fi;


##------------------------------------------------------------------------------------------------------------
##-- 2. 스키마명 생성 ${WORKUSER}
##------------------------------------------------------------------------------------------------------------
psql -q  -c "CREATE  SCHEMA  ${WORKUSER};" ${WORKDB}

if [ $? -ne 0 ]; then
      date
      echo "===>[2][Schema ${WORKUSER} exists already!, ignored]"
else
      date
      echo "[2][CREATE SCHEMA Success!]"
fi

##------------------------------------------------------------------------------------------------------------
##-- 3. ROLE 생성 ${WORKUSER}_ADMIN, ${WORKUSER}_DML, ${WORKUSER}_SELECT
##------------------------------------------------------------------------------------------------------------
for i in admin dml select
do 
	psql -q  -c "CREATE  ROLE ${2}_$i"

	if [ $? -ne 0 ]; then
	      date
	      echo "===>[3][ROLE ${2}_$i exists already! ignored]"
	else
	      date
	      echo "[3][CREATE ROLE ${2}_$i Success!]"
	fi
done

##------------------------------------------------------------------------------------------------------------
##-- 4. ROLE 생성 ${WORKUSER} LOGIN
##------------------------------------------------------------------------------------------------------------
psql -q  -c "CREATE  ROLE ${WORKUSER} LOGIN PASSWORD 'new1234!' IN ROLE ${WORKUSER}_ADMIN" ${WORKDB}

if [ $? -ne 0 ]; then
      date
      echo "===>[4][USER ${WORKUSER} exists already! ignored]"
	exists_user=1
	orig_passwd=`psql -t -c "select passwd from pg_shadow where usename = lower('${WORKUSER}')" | head -1 | awk '{print $1}'`
	psql -q  -c "GRANT ${2}_ADMIN to ${WORKUSER}"
	psql -q  -c "alter role ${2} login password 'new1234!'"
else
	exists_user=0
      date
      echo "[4][CREATE ROLE Success!]"
fi


##------------------------------------------------------------------------------------------------------------
##-- 5. ${WORKUSER} 유저에 스키마 매핑 작업.
##------------------------------------------------------------------------------------------------------------
psql -q  -c "GRANT  ALL ON  SCHEMA ${WORKUSER} TO  ${WORKUSER}_ADMIN;GRANT  USAGE  ON  SCHEMA ${WORKUSER} TO  ${WORKUSER}_DML; GRANT  USAGE  ON  SCHEMA ${WORKUSER} TO  ${WORKUSER}_SELECT;" ${WORKDB}

if [ $? -ne 0 ]; then
      date
      echo "===>[5][GRANT Failed!]"
else
      date
      echo "[5][GRANT Success!]"
fi

##------------------------------------------------------------------------------------------------------------
##-- 6. 각 유저의 기본 권한 속성은 대상 (${WORKUSER}_ADMIN)
## 유저의 기본 권한 속성은 초기화 됨 (revoke 뒤 grant)
##------------------------------------------------------------------------------------------------------------
export PGHOST=127.0.0.1
export PGPASSWORD='new1234!'

psql -q -U ${WORKUSER} -c "ALTER DEFAULT PRIVILEGES IN SCHEMA ${WORKUSER} 
                                     revoke all ON TABLES
                                     from ${WORKUSER}_ADMIN, ${WORKUSER}_DML, ${WORKUSER}_SELECT;" ${WORKDB}

psql -q -U ${WORKUSER} -c "ALTER DEFAULT PRIVILEGES IN SCHEMA ${WORKUSER} 
                                     GRANT INSERT, SELECT, UPDATE, DELETE, TRUNCATE, REFERENCES, TRIGGER ON TABLES
                                     TO ${WORKUSER}_ADMIN;" ${WORKDB}

if [ $? -ne 0 ]; then
      date
      echo "===>[6-1][ALTER DEFAULT PRIVILEGES Failed!]"
else
      date
      echo "[6-1][ALTER DEFAULT PRIVILEGES Success!]"
fi

psql -q -U ${WORKUSER} -c "ALTER DEFAULT PRIVILEGES IN SCHEMA ${WORKUSER} 
                                     GRANT  INSERT, SELECT, UPDATE, DELETE ON TABLES
                                     TO ${WORKUSER}_DML;" ${WORKDB}

if [ $? -ne 0 ]; then
      date
      echo "===>[6-2][ALTER DEFAULT PRIVILEGES Failed!]"
else
      date
      echo "[6-2][ALTER DEFAULT PRIVILEGES Success!]"
fi

psql -q -U ${WORKUSER} -c "ALTER DEFAULT PRIVILEGES IN SCHEMA ${WORKUSER} 
                                     GRANT SELECT ON TABLES
                                     TO ${WORKUSER}_SELECT;" ${WORKDB}

if [ $? -ne 0 ]; then
      date
      echo "===>[6-3][ALTER DEFAULT PRIVILEGES Failed!]"
else
      date
      echo "[6-3][ALTER DEFAULT PRIVILEGES Success!]"
fi

psql -q -U ${WORKUSER} -c "ALTER  DEFAULT  PRIVILEGES  IN  SCHEMA ${WORKUSER} REVOKE ALL  ON  SEQUENCES  FROM  ${WORKUSER}_ADMIN, ${WORKUSER}_DML;" ${WORKDB} 

psql -q -U ${WORKUSER} -c "ALTER  DEFAULT  PRIVILEGES  IN  SCHEMA ${WORKUSER} GRANT  SELECT,  UPDATE,  USAGE  ON  SEQUENCES  TO  ${WORKUSER}_ADMIN;" ${WORKDB} 

if [ $? -ne 0 ]; then
      date 
      echo "===>[6-4][ALTER DEFAULT PRIVILEGES Failed!]"
else
      date
      echo "[6-4][ALTER DEFAULT PRIVILEGES Success!]"
fi

psql -q -U ${WORKUSER} -c "ALTER  DEFAULT  PRIVILEGES  IN  SCHEMA ${WORKUSER} GRANT  USAGE ON  SEQUENCES  TO  ${WORKUSER}_DML;" ${WORKDB}

if [ $? -ne 0 ]; then
      date
      echo "===>[6-5][ALTER DEFAULT PRIVILEGES Failed!]"
else
      date
      echo "[6-5][ALTER DEFAULT PRIVILEGES Success!]"
fi

psql -q -U ${WORKUSER} -c "ALTER  DEFAULT  PRIVILEGES  IN  SCHEMA ${WORKUSER} REVOKE  ALL ON  FUNCTIONS    FROM  ${WORKUSER}_ADMIN, ${WORKUSER}_DML;" ${WORKDB}
psql -q -U ${WORKUSER} -c "ALTER  DEFAULT  PRIVILEGES  IN  SCHEMA ${WORKUSER} GRANT  EXECUTE ON  FUNCTIONS    TO  ${WORKUSER}_ADMIN, ${WORKUSER}_DML;" ${WORKDB}

if [ $? -ne 0 ]; then
      date
      echo "===>[6-6][ALTER DEFAULT PRIVILEGES Failed!]"
else
      date
      echo "[6-6][ALTER DEFAULT PRIVILEGES Success!]"
fi

unset PGPASSWORD
export PGHOST=/tmp
if [ $exists_user -eq 1 ]; then
	psql -q  -c "update pg_authid set rolpassword = '$orig_passwd' where rolname = lower('${WORKUSER}')"
fi

psql -q  -c "CREATE USER ${WORKUSER}_user PASSWORD 'new1234!' IN ROLE ${WORKUSER}_DML;" ${WORKDB}
psql -q  -c "CREATE USER ${WORKUSER}_monitor PASSWORD 'new1234!' IN ROLE ${WORKUSER}_SELECT;" ${WORKDB}
