CREATE DATABASE "video-service-db";
CREATE DATABASE "notification-service-db";
CREATE DATABASE "statistics-db";

GRANT ALL PRIVILEGES ON DATABASE "video-service-db" TO postgres;
GRANT ALL PRIVILEGES ON DATABASE "notification-service-db" TO postgres;
GRANT ALL PRIVILEGES ON DATABASE "statistics-db" TO postgres;