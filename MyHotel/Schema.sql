CREATE TABLE customer
(
    id                   INT,
    full_name            VARCHAR(64)  NOT NULL,
    sin_number           VARCHAR(9)   NOT NULL,
    address              VARCHAR(128) NOT NULL,
    date_of_registration DATE         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE employee
(
    id         INT,
    full_name  VARCHAR(64)  NOT NULL,
    sin_number VARCHAR(9)   NOT NULL,
    address    VARCHAR(128) NOT NULL,
    salary     NUMERIC(10, 2) DEFAULT 0.00,
    role       VARCHAR(64)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE hotel_brand
(
    hotel_brand_name     VARCHAR(64),
    main_office_location VARCHAR(128) NOT NULL,
    physical_address     VARCHAR(128) NOT NULL,
    --email_address
    --phone_number
    total_phone_number   VARCHAR(16)  NOT NULL,
    PRIMARY KEY (hotel_brand_name)
);

CREATE TABLE hotel_brand_email_address
(
    hotel_brand_name VARCHAR(64) NOT NULL,
    email_address    VARCHAR(32) NOT NULL,
    FOREIGN KEY (hotel_brand_name) REFERENCES hotel_brand (hotel_brand_name) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE hotel_brand_phone_number
(
    hotel_brand_name VARCHAR(64) NOT NULL,
    phone_number     VARCHAR(16) NOT NULL,
    FOREIGN KEY (hotel_brand_name) REFERENCES hotel_brand (hotel_brand_name) ON UPDATE CASCADE ON DELETE CASCADE
);

-- TODO: May tighten the constraint of the "email" column
CREATE TABLE hotel
(
    hotel_brand_name VARCHAR(64),
    hotel_name       VARCHAR(64),
    star_category    INT DEFAULT 0,
    number_of_rooms  INT DEFAULT 0,
    address          VARCHAR(128) NOT NULL,
    email_address    VARCHAR(32)  NOT NULL,
    --phone_number
    PRIMARY KEY (hotel_brand_name, hotel_name),
    FOREIGN KEY (hotel_brand_name) REFERENCES hotel_brand (hotel_brand_name) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT range_star_category CHECK (star_category BETWEEN 0 AND 5),
    CONSTRAINT range_number_of_rooms CHECK (number_of_rooms >= 0),
    CONSTRAINT verification_email CHECK (email_address LIKE '%@%')
);

CREATE TABLE hotel_phone_number
(
    hotel_brand_name VARCHAR(64) NOT NULL,
    hotel_name       VARCHAR(64) NOT NULL,
    phone_number     VARCHAR(16) NOT NULL,
    FOREIGN KEY (hotel_brand_name, hotel_name) REFERENCES hotel (hotel_brand_name, hotel_name) ON UPDATE CASCADE ON DELETE CASCADE
);

-- TODO: May create more room capacity
CREATE TABLE room
(
    hotel_brand_name VARCHAR(64),
    hotel_name       VARCHAR(64),
    room_id          INT,
    price            NUMERIC(8, 2) DEFAULT 0.00,
    --amenity
    room_capacity    VARCHAR(16) NOT NULL,
    --view
    --extensibility
    PRIMARY KEY (hotel_brand_name, hotel_name, room_id),
    FOREIGN KEY (hotel_brand_name, hotel_name) REFERENCES hotel (hotel_brand_name, hotel_name) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT range_price CHECK (price >= 0.00),
    CONSTRAINT range_room_capacity CHECK (room_capacity IN ('single', 'double'))
);

CREATE TABLE room_amenity
(
    hotel_brand_name VARCHAR(64) NOT NULL,
    hotel_name       VARCHAR(64) NOT NULL,
    room_id          INT         NOT NULL,
    amenity          VARCHAR(32) NOT NULL,
    PRIMARY KEY (hotel_brand_name, hotel_name, room_id),
    FOREIGN KEY (hotel_brand_name, hotel_name, room_id) REFERENCES room (hotel_brand_name, hotel_name, room_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE room_view
(
    hotel_brand_name VARCHAR(64) NOT NULL,
    hotel_name       VARCHAR(64) NOT NULL,
    room_id          INT         NOT NULL,
    view             VARCHAR(32) NOT NULL,
    FOREIGN KEY (hotel_brand_name, hotel_name, room_id) REFERENCES room (hotel_brand_name, hotel_name, room_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE room_extensibility
(
    hotel_brand_name VARCHAR(64) NOT NULL,
    hotel_name       VARCHAR(64) NOT NULL,
    room_id          INT         NOT NULL,
    extensibility    VARCHAR(32) NOT NULL,
    PRIMARY KEY (hotel_brand_name, hotel_name, room_id),
    FOREIGN KEY (hotel_brand_name, hotel_name, room_id) REFERENCES room (hotel_brand_name, hotel_name, room_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE book
(
    customer_id            INT,
    hotel_brand_name       VARCHAR(64),
    hotel_name             VARCHAR(64),
    room_id                INT,
    check_in_date          DATE        NOT NULL,
    room_type              VARCHAR(16) NOT NULL,
    total_number_occupants INT DEFAULT 1,
    PRIMARY KEY (customer_id, hotel_brand_name, hotel_name, room_id),
    FOREIGN KEY (customer_id) REFERENCES customer (id) ON UPDATE CASCADE,
    FOREIGN KEY (hotel_brand_name, hotel_name, room_id) REFERENCES room (hotel_brand_name, hotel_name, room_id) ON UPDATE CASCADE
);

CREATE TABLE rent
(
    customer_id            INT,
    hotel_brand_name       VARCHAR(64),
    hotel_name             VARCHAR(64),
    room_id                INT,
    check_in_employee_id   INT         NOT NULL,
    check_in_date          DATE        NOT NULL,
    room_type              VARCHAR(16) NOT NULL,
    total_number_occupants INT            DEFAULT 1,
    bill_amount            NUMERIC(10, 2) DEFAULT 0.0,
    duration               INTERVAL    NOT NULL,
    PRIMARY KEY (customer_id, hotel_brand_name, hotel_name, room_id),
    FOREIGN KEY (customer_id) REFERENCES customer (id) ON UPDATE CASCADE,
    FOREIGN KEY (hotel_brand_name, hotel_name, room_id) REFERENCES room (hotel_brand_name, hotel_name, room_id) ON UPDATE CASCADE,
    FOREIGN KEY (check_in_employee_id) REFERENCES employee (id) ON UPDATE CASCADE
);

CREATE TABLE rent_history
(
    customer_id            INT,
    hotel_brand_name       VARCHAR(64),
    hotel_name             VARCHAR(64),
    room_id                INT,
    check_in_employee_id   INT         NOT NULL,
    check_in_date          DATE        NOT NULL,
    room_type              VARCHAR(16) NOT NULL,
    total_number_occupants INT            DEFAULT 1,
    bill_amount            NUMERIC(10, 2) DEFAULT 0.0,
    duration               INTERVAL    NOT NULL,
    check_out_employee_id  INT         NOT NULL,
    check_out_date         DATE        NOT NULL,
    PRIMARY KEY (customer_id, hotel_brand_name, hotel_name, room_id),
    FOREIGN KEY (customer_id) REFERENCES customer (id) ON UPDATE CASCADE,
    FOREIGN KEY (hotel_brand_name, hotel_name, room_id) REFERENCES room (hotel_brand_name, hotel_name, room_id) ON UPDATE CASCADE,
    FOREIGN KEY (check_in_employee_id) REFERENCES employee (id) ON UPDATE CASCADE,
    FOREIGN KEY (check_out_employee_id) REFERENCES employee (id) ON UPDATE CASCADE
);

CREATE TABLE employment
(
    hotel_brand_name VARCHAR(64),
    hotel_name       VARCHAR(64),
    employee_id      INT,
    PRIMARY KEY (hotel_brand_name, hotel_name, employee_id),
    FOREIGN KEY (hotel_brand_name, hotel_name) REFERENCES hotel (hotel_brand_name, hotel_name) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employee (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE FUNCTION update_hotel_number_of_rooms()
    RETURNS TRIGGER AS
    $body$
        DECLARE
            room_count INT;
            hotel_identifier RECORD;
        BEGIN
            FOR hotel_identifier IN (SELECT hotel_brand_name, hotel_name FROM hotel)
                LOOP
                    SELECT COUNT(room_id)
                        FROM room
                        WHERE hotel_brand_name = hotel_identifier.hotel_brand_name AND hotel_name = hotel_identifier.hotel_name
                        INTO room_count;
                    UPDATE hotel
                        SET number_of_rooms = room_count
                        WHERE hotel_brand_name = hotel_identifier.hotel_brand_name AND hotel_name = hotel_identifier.hotel_name;
                END LOOP;
            RETURN NEW;
        END;
    $body$
    LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_hotel_number_of_rooms AFTER INSERT OR UPDATE OR DELETE ON room
    FOR EACH ROW
    EXECUTE FUNCTION update_hotel_number_of_rooms();


-- Administrator
GRANT ALL PRIVILEGES ON ALL
TABLES IN SCHEMA PUBLIC TO xwang532;

-- All employees connected to the database uses this account
GRANT ALL PRIVILEGES ON ALL
TABLES IN SCHEMA PUBLIC TO fzhan081;

-- All customers connected to the database uses this account
GRANT ALL PRIVILEGES ON ALL
TABLES IN SCHEMA PUBLIC TO jguo108;
