#! /bin/bash

dbname=naruto

# This may fail if the database does not currently exist
echo "drop database $dbname;" \
	| mysql --user=root --pass=root

(echo "create database $dbname; use $dbname; set autocommit = 0; " && bunzip2 -c naruto.sql.bz2 && echo "commit;") \
	| mysql --user=root --pass=root
