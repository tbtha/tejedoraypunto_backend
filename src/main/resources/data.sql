-- Usuario Administrador (password: admin123)
INSERT IGNORE INTO usuario (id, rut, nombre, apellidos, email, direccion, region, comuna, rol, password, activo, fecha_creacion) VALUES
(1, '11111111-1', 'Admin', 'Sistema', 'tejedoraypunto@gmail.com', '', 'Región Metropolitan', 'Maipú', 'ADMIN', '$2a$10$OO1EosoTiwIpIXga0ixPbu2bjevJuxCIK0ozcLXPhnP/mRKbqrGZa', true, NOW()),
(2, '22222222-2', 'Cliente', 'Sistema', 'cliente@gmail.com', '', 'Región Metropolitan', 'Maipú', 'CLIENTE', '$2a$10$7vLBKDwS2H/6ufeaO05dZ.DvcCrkvVuKVSl399pVgr419RZVrC/zu', true, NOW());

-- Categorías
INSERT IGNORE INTO categoria (id, nombre) VALUES 
(1, 'Chaleco Invierno'),
(2, 'Chaleco Verano'),
(3, 'Bikini'),
(4, 'Gorro Invierno'),
(5, 'Gorro Verano');

-- Productos
-- Categoría: Chaleco Invierno
INSERT IGNORE INTO producto (id, nombre, descripcion, precio, stock, imagen, categoria_id, activo, fecha_creacion) VALUES
(1, 'Chaleco Bosque', 'Inspirado en texturas naturales y tonos tierra', 55000, 1, 'img/winter/chaleco1.jpeg', 1, true, NOW()),
(2, 'Chaleco Cobre', 'Diseño clásico con detalles artesanales', 58000, 2, 'img/winter/chaleco2.jpeg', 1, true, NOW()),
(3, 'Chaleco Niebla', 'Tejido grueso, ideal para días fríos', 60000, 2, 'img/winter/chaleco4.jpeg', 1, true, NOW()),
-- Categoría: Chaleco Verano
(4, 'Chaleco Brisa', 'Tejido ligero perfecto para las tardes de verano', 45000, 2, 'img/winter/chaleco3.jpeg', 2, true, NOW()),
(5, 'Chaleco Marino', 'Diseño fresco con punto calado', 42000, 3, 'img/winter/chaleco5.jpeg', 2, true, NOW()),
(6, 'Chaleco Sunset', 'Tejido en algodón con detalles románticos', 48000, 1, 'img/winter/chaleco6.jpeg', 2, true, NOW()),
-- Categoría: Bikini
(7, 'Bikini Océano', 'Tejido en algodón con detalles de conchas', 32000, 2, 'img/summer/summer3.jpeg', 3, true, NOW()),
(8, 'Bikini Coral', 'Diseño bohemio con punto calado', 35000, 3, 'img/summer/summer4.jpeg', 3, true, NOW()),
(9, 'Bikini Sirena', 'Tejido delicado con detalles brillantes', 38000, 2, 'img/summer/summer5.jpeg', 3, true, NOW()),
-- Categoría: Gorro Invierno
(10, 'Gorro Montaña', 'Tejido grueso con diseño trenzado', 22000, 3, 'img/winter/gorro1.jpeg', 4, true, NOW()),
(11, 'Gorro Nieve', 'Diseño acogedor con pompón', 25000, 4, 'img/winter/gorro2.jpeg', 4, true, NOW()),
(12, 'Gorro Bosque', 'Tejido en punto musgo con doblez', 20000, 5, 'img/winter/gorro3.jpeg', 4, true, NOW()),
-- Categoría: Gorro Verano
(13, 'Gorro Playa', 'Diseño fresco y elegante para el sol', 18000, 4, 'img/summer/gorro_v1.jpeg', 5, true, NOW()),
(14, 'Gorro Sol', 'Tejido calado para días soleados', 15000, 5, 'img/summer/gorro_v2.jpeg', 5, true, NOW()),
(15, 'Gorro Brisa', 'Ligero y transpirable con ala ancha', 20000, 3, 'img/summer/gorro_v3.jpeg', 5, true, NOW());

-- Boletas
INSERT IGNORE INTO boleta (id, usuario_id, fecha_compra, total, neto, iva, estado, metodo_pago, direccion_envio, comuna_envio, region_envio) VALUES
(1, 2, '2025-11-23 20:46:50.000000', 216000, 181512, 34487, 'PENDIENTE', 'TRANSFERENCIA', 'capitan avalos 2383', 'Concepción', 'Región del Biobío'),
(2, 1, '2025-11-23 21:55:44.000000', 120000, 100840, 19159, 'PENDIENTE', 'TRANSFERENCIA', 'capitan avalos 2383', 'Santiago', 'Región Metropolitana');

-- Detalle de Boletas
INSERT IGNORE INTO detalle_boleta (id, boleta_id, producto_id, cantidad, precio_unitario, subtotal, neto, iva) VALUES
(1, 1, 2, 2, 58000, 116000, 97478, 18521),
(2, 1, 4, 1, 45000, 45000, 37815, 7184),
(3, 1, 1, 1, 55000, 55000, 46218, 8781),
(4, 2, 3, 2, 60000, 120000, 100840, 19159);