----------------------------------------------------Customer--------------------------------------------------------
INSERT INTO customer (id, full_name, sin_number, address, date_of_registration)
	VALUES ('334455', 'Fayer Zhang', '112233445', '456 Ottawa Rd, Ottawa, ON', '2021-3-18');
INSERT INTO customer (id, full_name, sin_number, address, date_of_registration)
	VALUES ('135790', 'Nancy Wu', '456789012', '333 Toronto Rd, Toronto, ON', '2021-3-17');
INSERT INTO customer (id, full_name, sin_number, address, date_of_registration)
	VALUES ('334456', 'Tom Tompson', '112233446', '457 Ottawa Rd, Ottawa, ON', '2021-02-08');
INSERT INTO customer (id, full_name, sin_number, address, date_of_registration)
	VALUES ('135792', 'David James', '456789013', '444 Toronto Rd, Toronto, ON', '2021-01-12');
INSERT INTO customer (id, full_name, sin_number, address, date_of_registration)
	VALUES ('135793', 'Anthony Alexander', '456789025', '666 Main Rd, Toronto, ON', '2021-01-12');
	
----------------------------------------------------Employee--------------------------------------------------------
INSERT INTO employee (id, full_name, sin_number, address, salary, role)
	VALUES ('335577', 'Feier Zhang', '334455667', '123 Ottawa Rd, Ottawa, ON', '1000', 'front desk employee');
INSERT INTO employee (id, full_name, sin_number, address, salary, role)
	VALUES ('335578', 'John Joseph', '334455668', '123 Quebec Rd, Ottawa, ON', '2000', 'manager');
INSERT INTO employee (id, full_name, sin_number, address, salary, role)
	VALUES ('335579', 'Daniel George', '334455669', '77 Watson Rd, Toronto, ON', '1000', 'front desk employee');
INSERT INTO employee (id, full_name, sin_number, address, salary, role)
	VALUES ('335580', 'Mark Donald', '334455670', '95 Charles Rd, Toronto, ON', '1000', 'front desk employee');

--------------------------------------------------Employment--------------------------------------------------------
INSERT INTO employment (hotel_brand_name, hotel_name, employee_id)
	VALUES ('Hilton', 'Double Tree', '335577');
INSERT INTO employment (hotel_brand_name, hotel_name, employee_id)
	VALUES ('Hilton', 'Hilton Garden Inn', '335578');
INSERT INTO employment (hotel_brand_name, hotel_name, employee_id)
	VALUES ('Marriott', 'Sheraton', '335579');
INSERT INTO employment (hotel_brand_name, hotel_name, employee_id)
	VALUES ('Marriott', 'Delta', '335580');

--------------------------------------------------Hotel_Brand--------------------------------------------------------
INSERT INTO hotel_brand (hotel_brand_name, main_office_location, physical_address, total_phone_number)
	VALUES ('Hilton', 'Virginia', 'Tysons Corner', '333-340-3340');
INSERT INTO hotel_brand (hotel_brand_name, main_office_location, physical_address, total_phone_number)
	VALUES ('Marriott', 'Maryland', 'Bethesda', '111-120-1120');

--------------------------------------------Hotel_Brand_email_address------------------------------------------------
INSERT INTO hotel_brand_email_address(hotel_brand_name, email_address)
	VALUES ('Hilton', 'hiltonmain@hilton.com');
INSERT INTO hotel_brand_email_address(hotel_brand_name, email_address)
	VALUES ('Marriott', 'marriottmain@marriott.com');

--------------------------------------------Hotel_Brand_phone_number-------------------------------------------------
INSERT INTO hotel_brand_phone_number(hotel_brand_name, phone_number)
	VALUES ('Hilton', '333-340-3340');
INSERT INTO hotel_brand_phone_number(hotel_brand_name, phone_number)
	VALUES ('Marriott', '111-120-1120');

--------------------------------------------Hotel_phone_number-------------------------------------------------
INSERT INTO hotel_phone_number(hotel_brand_name, hotel_name, phone_number)
	VALUES ('Hilton', 'Double Tree', '333-340-3341');
INSERT INTO hotel_phone_number(hotel_brand_name, hotel_name, phone_number)
	VALUES ('Hilton', 'Hilton Garden Inn', '333-340-3342');
INSERT INTO hotel_phone_number(hotel_brand_name, hotel_name, phone_number)
	VALUES ('Marriott', 'Sheraton','111-120-1121');
INSERT INTO hotel_phone_number(hotel_brand_name, hotel_name, phone_number)
	VALUES ('Marriott', 'Delta','111-120-1122');

-----------------------------------------------------Hotel-----------------------------------------------------------
INSERT INTO hotel (hotel_brand_name, hotel_name, star_category, number_of_rooms, address, email_address)
	VALUES ('Hilton', 'Double Tree', 5, 100, '108 Chestnut St, Toronto, ON', 'doubletreetrt@hilton.com');
INSERT INTO hotel (hotel_brand_name, hotel_name, star_category, number_of_rooms, address, email_address)
	VALUES ('Hilton', 'Hilton Garden Inn', 4, 200, '92 Peter St, Toronto, ON', 'gardeninntrt@hilton.com');

INSERT INTO hotel (hotel_brand_name, hotel_name, star_category, number_of_rooms, address, email_address)
	VALUES ('Marriott', 'Sheraton', 4, 50, '123 Queen St W, Toronto, ON', 'sheraton@marriott.com');
INSERT INTO hotel (hotel_brand_name, hotel_name, star_category, number_of_rooms, address, email_address)
	VALUES ('Marriott', 'Delta', 4, 80, '75 Lower Simcoe Street, Toronto, ON', 'delta@marriott.com');

-----------------------------------------------------Room-----------------------------------------------------------
INSERT INTO room (hotel_brand_name, hotel_name, room_id, price, room_capacity)
	VALUES ('Hilton', 'Double Tree', 101, 188.00, 'double');
INSERT INTO room (hotel_brand_name, hotel_name, room_id, price, room_capacity)
	VALUES ('Hilton', 'Double Tree', 102, 128.00, 'double');
INSERT INTO room (hotel_brand_name, hotel_name, room_id, price, room_capacity)
	VALUES ('Hilton', 'Double Tree', 103, 88.00, 'single');

INSERT INTO room (hotel_brand_name, hotel_name, room_id, price, room_capacity)
	VALUES ('Hilton', 'Hilton Garden Inn', 101, 108.00, 'single');
INSERT INTO room (hotel_brand_name, hotel_name, room_id, price, room_capacity)
	VALUES ('Hilton', 'Hilton Garden Inn', 102, 118.00, 'double');
INSERT INTO room (hotel_brand_name, hotel_name, room_id, price, room_capacity)
	VALUES ('Hilton', 'Hilton Garden Inn', 103, 158.00, 'double');

INSERT INTO room (hotel_brand_name, hotel_name, room_id, price, room_capacity)
	VALUES ('Marriott', 'Sheraton', 101, 208.00, 'single');
INSERT INTO room (hotel_brand_name, hotel_name, room_id, price, room_capacity)
	VALUES ('Marriott', 'Sheraton', 102, 218.00, 'double');
INSERT INTO room (hotel_brand_name, hotel_name, room_id, price, room_capacity)
	VALUES ('Marriott', 'Sheraton', 103, 258.00, 'double');

INSERT INTO room (hotel_brand_name, hotel_name, room_id, price, room_capacity)
	VALUES ('Marriott', 'Delta', 101, 236.00, 'double');
INSERT INTO room (hotel_brand_name, hotel_name, room_id, price, room_capacity)
	VALUES ('Marriott', 'Delta', 102, 232.00, 'double');
INSERT INTO room (hotel_brand_name, hotel_name, room_id, price, room_capacity)
	VALUES ('Marriott', 'Delta', 103, 180.00, 'single');

--------------------------------------------------Room_amenity------------------------------------------------------
INSERT INTO room_amenity(hotel_brand_name, hotel_name, room_id, amenity)
	VALUES ('Hilton', 'Double Tree', 101, 'TV, air condition, fridge');
INSERT INTO room_amenity(hotel_brand_name, hotel_name, room_id, amenity)
	VALUES ('Hilton', 'Double Tree', 102, 'TV, air condition, fridge');
INSERT INTO room_amenity(hotel_brand_name, hotel_name, room_id, amenity)
	VALUES ('Hilton', 'Double Tree', 103, 'TV, air condition, fridge');

INSERT INTO room_amenity(hotel_brand_name, hotel_name, room_id, amenity)
	VALUES ('Hilton', 'Hilton Garden Inn', 101, 'TV, air condition, fridge');
INSERT INTO room_amenity(hotel_brand_name, hotel_name, room_id, amenity)
	VALUES ('Hilton', 'Hilton Garden Inn', 102, 'TV, air condition, fridge');
INSERT INTO room_amenity(hotel_brand_name, hotel_name, room_id, amenity)
	VALUES ('Hilton', 'Hilton Garden Inn', 103, 'TV, air condition, fridge');

INSERT INTO room_amenity(hotel_brand_name, hotel_name, room_id, amenity)
	VALUES ('Marriott', 'Sheraton', 101, 'TV, air condition, fridge');
INSERT INTO room_amenity(hotel_brand_name, hotel_name, room_id, amenity)
	VALUES ('Marriott', 'Sheraton', 102, 'TV, air condition, fridge');
INSERT INTO room_amenity(hotel_brand_name, hotel_name, room_id, amenity)
	VALUES ('Marriott', 'Sheraton', 103, 'TV, air condition, fridge');	

INSERT INTO room_amenity(hotel_brand_name, hotel_name, room_id, amenity)
	VALUES ('Marriott', 'Delta', 101, 'TV, air condition, fridge');
INSERT INTO room_amenity(hotel_brand_name, hotel_name, room_id, amenity)
	VALUES ('Marriott', 'Delta', 102, 'TV, air condition, fridge');
INSERT INTO room_amenity(hotel_brand_name, hotel_name, room_id, amenity)
	VALUES ('Marriott', 'Delta', 103, 'TV, air condition, fridge');

----------------------------------------------Room_extensibility----------------------------------------------------
INSERT INTO room_extensibility(hotel_brand_name, hotel_name, room_id, extensibility)
	VALUES ('Hilton', 'Double Tree', 101, 'Add one more bed');
INSERT INTO room_extensibility(hotel_brand_name, hotel_name, room_id, extensibility)
	VALUES ('Hilton', 'Double Tree', 102, 'Add more pillows');
INSERT INTO room_extensibility(hotel_brand_name, hotel_name, room_id, extensibility)
	VALUES ('Hilton', 'Double Tree', 103, 'Add more pillows');

INSERT INTO room_extensibility(hotel_brand_name, hotel_name, room_id, extensibility)
	VALUES ('Hilton', 'Hilton Garden Inn', 101, 'Add one more bed');
INSERT INTO room_extensibility(hotel_brand_name, hotel_name, room_id, extensibility)
	VALUES ('Hilton', 'Hilton Garden Inn', 102, 'Add more pillows');
INSERT INTO room_extensibility(hotel_brand_name, hotel_name, room_id, extensibility)
	VALUES ('Hilton', 'Hilton Garden Inn', 103, 'Add more pillows');	

INSERT INTO room_extensibility(hotel_brand_name, hotel_name, room_id, extensibility)
	VALUES ('Marriott', 'Sheraton', 101, 'Add extra bed quilt');
INSERT INTO room_extensibility(hotel_brand_name, hotel_name, room_id, extensibility)
	VALUES ('Marriott', 'Sheraton', 102, 'Add one more bed');
INSERT INTO room_extensibility(hotel_brand_name, hotel_name, room_id, extensibility)
	VALUES ('Marriott', 'Sheraton', 103, 'Add more pillows');

INSERT INTO room_extensibility(hotel_brand_name, hotel_name, room_id, extensibility)
	VALUES ('Marriott', 'Delta', 101, 'Add more pillows');
INSERT INTO room_extensibility(hotel_brand_name, hotel_name, room_id, extensibility)
	VALUES ('Marriott', 'Delta', 102, 'Add one more bed');
INSERT INTO room_extensibility(hotel_brand_name, hotel_name, room_id, extensibility)
	VALUES ('Marriott', 'Delta', 103, 'Add extra bed quilt');

--------------------------------------------------Room_view---------------------------------------------------------
INSERT INTO room_view(hotel_brand_name, hotel_name, room_id, view)
	VALUES ('Hilton', 'Double Tree', 101, 'lake view');
INSERT INTO room_view(hotel_brand_name, hotel_name, room_id, view)
	VALUES ('Hilton', 'Double Tree', 102, 'road view');
INSERT INTO room_view(hotel_brand_name, hotel_name, room_id, view)
	VALUES ('Hilton', 'Double Tree', 103, 'lake view');

INSERT INTO room_view(hotel_brand_name, hotel_name, room_id, view)
	VALUES ('Hilton', 'Hilton Garden Inn', 101, 'sea view');
INSERT INTO room_view(hotel_brand_name, hotel_name, room_id, view)
	VALUES ('Hilton', 'Hilton Garden Inn', 102, 'mountain view');
INSERT INTO room_view(hotel_brand_name, hotel_name, room_id, view)
	VALUES ('Hilton', 'Hilton Garden Inn', 103, 'mountain view');

INSERT INTO room_view(hotel_brand_name, hotel_name, room_id, view)
	VALUES ('Marriott', 'Sheraton', 101, 'forest view');
INSERT INTO room_view(hotel_brand_name, hotel_name, room_id, view)
	VALUES ('Marriott', 'Sheraton', 102, 'trail view');
INSERT INTO room_view(hotel_brand_name, hotel_name, room_id, view)
	VALUES ('Marriott', 'Sheraton', 103, 'forest view');

INSERT INTO room_view(hotel_brand_name, hotel_name, room_id, view)
	VALUES ('Marriott', 'Delta', 101, 'lake view');
INSERT INTO room_view(hotel_brand_name, hotel_name, room_id, view)
	VALUES ('Marriott', 'Delta', 102, 'lake view');
INSERT INTO room_view(hotel_brand_name, hotel_name, room_id, view)
	VALUES ('Marriott', 'Delta', 103, 'lake view');

-----------------------------------------------------Book-----------------------------------------------------------
INSERT INTO book (customer_id, hotel_brand_name, hotel_name, room_id, check_in_date, room_type, total_number_occupants)
	VALUES ('135790', 'Hilton', 'Hilton Garden Inn', 101, '2021-03-19', 'Non-smoking', 2);
INSERT INTO book (customer_id, hotel_brand_name, hotel_name, room_id, check_in_date, room_type, total_number_occupants)
	VALUES ('334455', 'Hilton', 'Double Tree', 102, '2021-03-19', 'Non-smoking', 2);
INSERT INTO book (customer_id, hotel_brand_name, hotel_name, room_id, check_in_date, room_type, total_number_occupants)
	VALUES ('334456', 'Marriott', 'Sheraton', 102, '2021-02-19', 'Non-smoking', 2);

-----------------------------------------------------Rent-----------------------------------------------------------
INSERT INTO rent (customer_id, hotel_brand_name, hotel_name, room_id, check_in_employee_id, check_in_date, room_type, total_number_occupants, bill_amount, duration)
	VALUES ('135792', 'Marriott', 'Delta', 103, 335580, '2021-03-18', 'Non-smoking', 1, 360.00, interval'2 day');
