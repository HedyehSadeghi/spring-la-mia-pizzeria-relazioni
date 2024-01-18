-- Gli statement non devono andare a capo
INSERT INTO db_pizzeria.pizzas (id, description, name, photo, price) VALUES(1, 'mozarella, pomodoro', 'Margherita', 'https://upload.wikimedia.org/wikipedia/commons/c/c8/Pizza_Margherita_stu_spivack.jpg', 5);
INSERT INTO db_pizzeria.pizzas (id, description, name, photo, price) VALUES(2, 'pomodoro, origano', 'Marinara', 'https://assets.tmecosys.com/image/upload/t_web767x639/img/recipe/ras/Assets/97C2C718-5015-437F-8355-316EE000F57C/Derivates/556409BB-02E0-4BA3-BC16-F409F609FCED.jpg', 4);
INSERT INTO db_pizzeria.pizzas (id, description, name, photo, price) VALUES(3, 'mozarella, pomodoro, funghi', 'Funghi', 'https://www.italianstylecooking.net/wp-content/uploads/2022/01/Pizza-Funghi.jpg', 6);

-- insert discount
INSERT INTO db_pizzeria.discounts (id, expire_date, start_date, title, pizza_id) VALUES(1, '2024-02-10', '2024-01-15', 'half price', 1);
INSERT INTO db_pizzeria.discounts (id, expire_date, start_date, title, pizza_id)VALUES(2, '2024-03-14', '2024-02-01', '10% discount', 1);

-- insert ingredient
INSERT INTO db_pizzeria.ingredients(id, name)VALUES(1, 'pomodoro');
INSERT INTO db_pizzeria.ingredients(id, name)VALUES(2, 'olio');
INSERT INTO db_pizzeria.ingredients(id, name)VALUES(3, 'mozzarella');
INSERT INTO db_pizzeria.ingredients(id, name)VALUES(4, 'funghi');
INSERT INTO db_pizzeria.ingredients(id, name)VALUES(5, 'origano');