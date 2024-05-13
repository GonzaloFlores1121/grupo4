
-- Insertar valores en la tabla Usuario
INSERT INTO Usuario (email, nombre, password, rol, activo, genero, nivelDeActividad, peso, altura, edad, ingestaCalorica)
VALUES ('usuario1@example.com', 'Federico', '123', 'Usuario', 1, 'Masculino', 'activo', 70.5, 175.0, 30, 2500),
       ('usuario2@example.com', 'Liam', '123', 'Usuario', 1, 'Femenino', 'sedentario', 65.2, 160.0, 35, 2000);


-- Insertar valores en la tabla Ejercicio
INSERT INTO Ejercicio (id,nombre, caloriasQuemadas, intensidad) VALUES
                                                             (1,'Caminata', 300, 'baja'),    -- Intensidad Moderada
                                                                    (2,'Correr', 600, 'alta');       -- Intensidad Alta

-- Insertar valores en la tabla Ejercicio_usuario
INSERT INTO Ejercicio_usuario (id,nombre, minutos, dia, mes, anio, intensidad, id_ejercicio, id_usuario)
VALUES (1,'Caminata', '23', 30, 4, 2024, 'baja', 1,1),  -- Usuario 1, Intensidad Moderada
       (2,'Correr', '80', 30, 4, 2024, 'alta', 2,2);   -- Usuario 2, Intensidad Alta


