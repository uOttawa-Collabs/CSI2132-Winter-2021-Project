-- 1. Give the details of all currently rented rooms in a specific hotel. 
--    Please display the columns as customer name, room type, room price, rental start date, room view, hotel chain. 
--    Sort by the room price in ascending order and rental start date in descending order.
SELECT c.full_name, rt.room_type, ro.price, rt.check_in_date, rov.view, rt.hotel_brand_name
FROM rent rt 
JOIN customer c
ON c.id = rt.customer_id
JOIN room ro
ON ro.hotel_brand_name = rt.hotel_brand_name AND ro.hotel_name = rt.hotel_name AND ro.room_id = rt.room_id
JOIN room_view rov
ON rov.hotel_brand_name = ro.hotel_brand_name AND ro.hotel_name = rov.hotel_name AND ro.room_id = rov.room_id
WHERE rt.hotel_name = 'Double Tree'
ORDER BY ro.price, rt.check_in_date DESC;


-- 2. Create a view named CustomerListView that gives the details of all the customers. 
--    Please, sort the customers by hotel chain.



-- 3. Display the details of the cheapest hotel room of all hotel chains.
SELECT *
FROM room ro
WHERE price = (SELECT MIN(price) FROM room);


-- 4.List all the rooms in all hotels in Ottawa and sort them based on the hotel stars and price.
SELECT ro.hotel_brand_name, ro.hotel_name, ro.room_id, ro.price, h.star_category, h.address  
FROM room ro
JOIN hotel h
ON h.hotel_brand_name = ro.hotel_brand_name AND h.hotel_name = ro.hotel_name
WHERE h.address LIKE '%Ottawa%'
ORDER BY h.star_category, ro.price;


-- 5. List all the details of all rooms rented on the 10th day of a month of your choice. 
--    Ensure to insert dates in your table that correspond to that month in order to run your query.
SELECT rt.customer_id, ro.hotel_brand_name, ro.hotel_name, ro.room_id, rt.check_in_date, ro.price, roa.amenity, rov.view, roe.extensibility 
FROM room ro
JOIN rent rt
ON ro.hotel_brand_name = rt.hotel_brand_name AND ro.hotel_name = rt.hotel_name AND ro.room_id = rt.room_id
JOIN room_amenity roa
ON roa.hotel_brand_name = rt.hotel_brand_name AND roa.hotel_name = rt.hotel_name AND roa.room_id = rt.room_id
JOIN room_view rov
ON rov.hotel_brand_name = rt.hotel_brand_name AND rov.hotel_name = rt.hotel_name AND rov.room_id = rt.room_id
JOIN room_extensibility roe
ON roe.hotel_brand_name = rt.hotel_brand_name AND roe.hotel_name = rt.hotel_name AND roe.room_id = rt.room_id
WHERE rt.check_in_date = '2021-01-10';


-- 6. Update the phone number of a customer.
UPDATE customer
SET phone_number = '333-123-5555'
WHERE id = 100005;

SELECT id, phone_number
FROM customer
WHERE id = 100005;

-- 7. Which category hotels (1 star to 5 star) are most preferred by the customers?


-- 8. Find the second highest salary from the employee table.
SELECT id, full_name, salary, role
FROM employee
WHERE salary =(SELECT MAX(salary)
			   FROM employee
			   WHERE salary < (SELECT MAX(salary) 
							   FROM employee));
