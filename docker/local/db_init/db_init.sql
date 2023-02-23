create USER server_user WITH PASSWORD 'server_user';
CREATE DATABASE traveldb;
GRANT ALL PRIVILEGES ON DATABASE traveldb TO server_user;
ALTER DATABASE traveldb OWNER TO server_user;