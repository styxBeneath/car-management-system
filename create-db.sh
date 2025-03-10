#!/bin/bash

DB_NAME="car_mgmt_db"
DB_USER="postgres"

DB_EXISTS=$(psql -U $DB_USER -tAc "SELECT 1 FROM pg_database WHERE datname='$DB_NAME'")

if [[ $DB_EXISTS != "1" ]]; then
  echo "Database does not exist. Creating database..."
  psql -U $DB_USER -c "CREATE DATABASE $DB_NAME;"
else
  echo "Database already exists. Skipping creation."
fi
