
-- Insertar valores en la tabla Ejercicio
INSERT INTO Ejercicio (nombre, caloriasQuemadas, intensidad) VALUES
                                                                 ('Caminata', 300, 'baja'),    -- Intensidad Moderada
                                                                 ('Correr', 600, 'alta');       -- Intensidad Alta

use fatloss;
select * from usuario;

select * from EjercicioUsuario;

Insert into Receta (nombre,imagen,descripcion,ingredientes,instrucciones,tamanoPorcion,energia,carbohidratos,
                    azucar,proteina,grasa,grasaSaturada,grasaPoliinsaturada,grasaMonoinsaturada,
                    colesterol,fibra,sodio,potasio)
Values('Ensalada de Quinoa con Vegetales Asados','receta-quinoa.jpg','Una receta saludable y deliciosa para disfrutar en cualquier momento del día.',
       '<ul>
           <li>1 taza de quinoa</li>
           <li>2 tazas de caldo de verduras o agua</li>
           <li>1 pimiento rojo, cortado en trozos</li>
           <li>1 pimiento amarillo, cortado en trozos</li>
           <li>1 calabacín, cortado en rodajas</li>
           <li>1 berenjena, cortada en rodajas</li>
           <li>1 cebolla roja, cortada en trozos</li>
           <li>2 cucharadas de aceite de oliva</li>
           <li>Sal y pimienta al gusto</li>
           <li>2 cucharadas de jugo de limón</li>
           <li>2 cucharadas de hojas de cilantro fresco (opcional)</li>
           <li>1 aguacate, cortado en cubos</li>
         </ul>',
       '<ol>
         <li>Precalienta el horno a 200°C (390°F).</li>
         <li>Enjuaga bien la quinoa bajo agua fría para eliminar el sabor amargo. Luego, colócala en una olla con el caldo de verduras o agua. Lleva a ebullición, luego reduce el fuego, tapa y cocina a fuego lento durante 15-20 minutos, o hasta que la quinoa esté cocida y el líquido se haya absorbido. Retira del fuego y deja reposar durante unos minutos.</li>
         <li>Mientras la quinoa se cocina, prepara las verduras. Coloca los pimientos, el calabacín, la berenjena y la cebolla en una bandeja para hornear. Rocía con aceite de oliva, sal y pimienta al gusto. Mezcla bien para asegurarte de que todas las verduras estén cubiertas.</li>
         <li>Hornea las verduras en el horno precalentado durante unos 20-25 minutos, o hasta que estén tiernas y ligeramente doradas, revolviendo ocasionalmente.</li>
         <li>Una vez que la quinoa y las verduras estén listas, mezcla la quinoa cocida con las verduras asadas en un tazón grande.</li>
         <li>Adereza la ensalada con jugo de limón y hojas de cilantro fresco (si lo deseas). Mezcla bien.</li>
         <li>Sirve la ensalada en platos individuales y decora con cubos de aguacate.</li>
         <li>¡Disfruta de tu deliciosa ensalada de quinoa con vegetales asados!</li>
       </ol>',
       'por porción','160 kj / 38 kcal','3,3g','0,64g','2,69g','1,58g','0,422g','0,32g','0,552g','53mg','0,6g','143mg','59mg'),

      ('Ensalada de Quinoa con Vegetales Asados','receta-quinoa.jpg','Una receta saludable y deliciosa para disfrutar en cualquier momento del día.',
       '<ul>
           <li>1 taza de quinoa</li>
           <li>2 tazas de caldo de verduras o agua</li>
           <li>1 pimiento rojo, cortado en trozos</li>
           <li>1 pimiento amarillo, cortado en trozos</li>
           <li>1 calabacín, cortado en rodajas</li>
           <li>1 berenjena, cortada en rodajas</li>
           <li>1 cebolla roja, cortada en trozos</li>
           <li>2 cucharadas de aceite de oliva</li>
           <li>Sal y pimienta al gusto</li>
           <li>2 cucharadas de jugo de limón</li>
           <li>2 cucharadas de hojas de cilantro fresco (opcional)</li>
           <li>1 aguacate, cortado en cubos</li>
         </ul>',
       '<ol>
         <li>Precalienta el horno a 200°C (390°F).</li>
         <li>Enjuaga bien la quinoa bajo agua fría para eliminar el sabor amargo. Luego, colócala en una olla con el caldo de verduras o agua. Lleva a ebullición, luego reduce el fuego, tapa y cocina a fuego lento durante 15-20 minutos, o hasta que la quinoa esté cocida y el líquido se haya absorbido. Retira del fuego y deja reposar durante unos minutos.</li>
         <li>Mientras la quinoa se cocina, prepara las verduras. Coloca los pimientos, el calabacín, la berenjena y la cebolla en una bandeja para hornear. Rocía con aceite de oliva, sal y pimienta al gusto. Mezcla bien para asegurarte de que todas las verduras estén cubiertas.</li>
         <li>Hornea las verduras en el horno precalentado durante unos 20-25 minutos, o hasta que estén tiernas y ligeramente doradas, revolviendo ocasionalmente.</li>
         <li>Una vez que la quinoa y las verduras estén listas, mezcla la quinoa cocida con las verduras asadas en un tazón grande.</li>
         <li>Adereza la ensalada con jugo de limón y hojas de cilantro fresco (si lo deseas). Mezcla bien.</li>
         <li>Sirve la ensalada en platos individuales y decora con cubos de aguacate.</li>
         <li>¡Disfruta de tu deliciosa ensalada de quinoa con vegetales asados!</li>
       </ol>',
       'por porción','160 kj / 38 kcal','3,3g','0,64g','2,69g','1,58g','0,422g','0,32g','0,552g','53mg','0,6g','143mg','59mg'),

      ('Ensalada de Quinoa con Vegetales Asados','receta-quinoa.jpg','Una receta saludable y deliciosa para disfrutar en cualquier momento del día.',
       '<ul>
           <li>1 taza de quinoa</li>
           <li>2 tazas de caldo de verduras o agua</li>
           <li>1 pimiento rojo, cortado en trozos</li>
           <li>1 pimiento amarillo, cortado en trozos</li>
           <li>1 calabacín, cortado en rodajas</li>
           <li>1 berenjena, cortada en rodajas</li>
           <li>1 cebolla roja, cortada en trozos</li>
           <li>2 cucharadas de aceite de oliva</li>
           <li>Sal y pimienta al gusto</li>
           <li>2 cucharadas de jugo de limón</li>
           <li>2 cucharadas de hojas de cilantro fresco (opcional)</li>
           <li>1 aguacate, cortado en cubos</li>
         </ul>',
       '<ol>
         <li>Precalienta el horno a 200°C (390°F).</li>
         <li>Enjuaga bien la quinoa bajo agua fría para eliminar el sabor amargo. Luego, colócala en una olla con el caldo de verduras o agua. Lleva a ebullición, luego reduce el fuego, tapa y cocina a fuego lento durante 15-20 minutos, o hasta que la quinoa esté cocida y el líquido se haya absorbido. Retira del fuego y deja reposar durante unos minutos.</li>
         <li>Mientras la quinoa se cocina, prepara las verduras. Coloca los pimientos, el calabacín, la berenjena y la cebolla en una bandeja para hornear. Rocía con aceite de oliva, sal y pimienta al gusto. Mezcla bien para asegurarte de que todas las verduras estén cubiertas.</li>
         <li>Hornea las verduras en el horno precalentado durante unos 20-25 minutos, o hasta que estén tiernas y ligeramente doradas, revolviendo ocasionalmente.</li>
         <li>Una vez que la quinoa y las verduras estén listas, mezcla la quinoa cocida con las verduras asadas en un tazón grande.</li>
         <li>Adereza la ensalada con jugo de limón y hojas de cilantro fresco (si lo deseas). Mezcla bien.</li>
         <li>Sirve la ensalada en platos individuales y decora con cubos de aguacate.</li>
         <li>¡Disfruta de tu deliciosa ensalada de quinoa con vegetales asados!</li>
       </ol>',
       'por porción','160 kj / 38 kcal','3,3g','0,64g','2,69g','1,58g','0,422g','0,32g','0,552g','53mg','0,6g','143mg','59mg'),

      ('Ensalada de Quinoa con Vegetales Asados','receta-quinoa.jpg','Una receta saludable y deliciosa para disfrutar en cualquier momento del día.',
       '<ul>
           <li>1 taza de quinoa</li>
           <li>2 tazas de caldo de verduras o agua</li>
           <li>1 pimiento rojo, cortado en trozos</li>
           <li>1 pimiento amarillo, cortado en trozos</li>
           <li>1 calabacín, cortado en rodajas</li>
           <li>1 berenjena, cortada en rodajas</li>
           <li>1 cebolla roja, cortada en trozos</li>
           <li>2 cucharadas de aceite de oliva</li>
           <li>Sal y pimienta al gusto</li>
           <li>2 cucharadas de jugo de limón</li>
           <li>2 cucharadas de hojas de cilantro fresco (opcional)</li>
           <li>1 aguacate, cortado en cubos</li>
         </ul>',
       '<ol>
         <li>Precalienta el horno a 200°C (390°F).</li>
         <li>Enjuaga bien la quinoa bajo agua fría para eliminar el sabor amargo. Luego, colócala en una olla con el caldo de verduras o agua. Lleva a ebullición, luego reduce el fuego, tapa y cocina a fuego lento durante 15-20 minutos, o hasta que la quinoa esté cocida y el líquido se haya absorbido. Retira del fuego y deja reposar durante unos minutos.</li>
         <li>Mientras la quinoa se cocina, prepara las verduras. Coloca los pimientos, el calabacín, la berenjena y la cebolla en una bandeja para hornear. Rocía con aceite de oliva, sal y pimienta al gusto. Mezcla bien para asegurarte de que todas las verduras estén cubiertas.</li>
         <li>Hornea las verduras en el horno precalentado durante unos 20-25 minutos, o hasta que estén tiernas y ligeramente doradas, revolviendo ocasionalmente.</li>
         <li>Una vez que la quinoa y las verduras estén listas, mezcla la quinoa cocida con las verduras asadas en un tazón grande.</li>
         <li>Adereza la ensalada con jugo de limón y hojas de cilantro fresco (si lo deseas). Mezcla bien.</li>
         <li>Sirve la ensalada en platos individuales y decora con cubos de aguacate.</li>
         <li>¡Disfruta de tu deliciosa ensalada de quinoa con vegetales asados!</li>
       </ol>',
       'por porción','160 kj / 38 kcal','3,3g','0,64g','2,69g','1,58g','0,422g','0,32g','0,552g','53mg','0,6g','143mg','59mg'),

       ('Ensalada de Quinoa con Vegetales Asados','receta-quinoa.jpg','Una receta saludable y deliciosa para disfrutar en cualquier momento del día.',
  '<ul>
      <li>1 taza de quinoa</li>
      <li>2 tazas de caldo de verduras o agua</li>
      <li>1 pimiento rojo, cortado en trozos</li>
      <li>1 pimiento amarillo, cortado en trozos</li>
      <li>1 calabacín, cortado en rodajas</li>
      <li>1 berenjena, cortada en rodajas</li>
      <li>1 cebolla roja, cortada en trozos</li>
      <li>2 cucharadas de aceite de oliva</li>
      <li>Sal y pimienta al gusto</li>
      <li>2 cucharadas de jugo de limón</li>
      <li>2 cucharadas de hojas de cilantro fresco (opcional)</li>
      <li>1 aguacate, cortado en cubos</li>
    </ul>',
  '<ol>
    <li>Precalienta el horno a 200°C (390°F).</li>
    <li>Enjuaga bien la quinoa bajo agua fría para eliminar el sabor amargo. Luego, colócala en una olla con el caldo de verduras o agua. Lleva a ebullición, luego reduce el fuego, tapa y cocina a fuego lento durante 15-20 minutos, o hasta que la quinoa esté cocida y el líquido se haya absorbido. Retira del fuego y deja reposar durante unos minutos.</li>
    <li>Mientras la quinoa se cocina, prepara las verduras. Coloca los pimientos, el calabacín, la berenjena y la cebolla en una bandeja para hornear. Rocía con aceite de oliva, sal y pimienta al gusto. Mezcla bien para asegurarte de que todas las verduras estén cubiertas.</li>
    <li>Hornea las verduras en el horno precalentado durante unos 20-25 minutos, o hasta que estén tiernas y ligeramente doradas, revolviendo ocasionalmente.</li>
    <li>Una vez que la quinoa y las verduras estén listas, mezcla la quinoa cocida con las verduras asadas en un tazón grande.</li>
    <li>Adereza la ensalada con jugo de limón y hojas de cilantro fresco (si lo deseas). Mezcla bien.</li>
    <li>Sirve la ensalada en platos individuales y decora con cubos de aguacate.</li>
    <li>¡Disfruta de tu deliciosa ensalada de quinoa con vegetales asados!</li>
  </ol>',
  'por porción','160 kj / 38 kcal','3,3g','0,64g','2,69g','1,58g','0,422g','0,32g','0,552g','53mg','0,6g','143mg','59mg'),

    ('Ensalada de Quinoa con Vegetales Asados','receta-quinoa.jpg','Una receta saludable y deliciosa para disfrutar en cualquier momento del día.',
  '<ul>
      <li>1 taza de quinoa</li>
      <li>2 tazas de caldo de verduras o agua</li>
      <li>1 pimiento rojo, cortado en trozos</li>
      <li>1 pimiento amarillo, cortado en trozos</li>
      <li>1 calabacín, cortado en rodajas</li>
      <li>1 berenjena, cortada en rodajas</li>
      <li>1 cebolla roja, cortada en trozos</li>
      <li>2 cucharadas de aceite de oliva</li>
      <li>Sal y pimienta al gusto</li>
      <li>2 cucharadas de jugo de limón</li>
      <li>2 cucharadas de hojas de cilantro fresco (opcional)</li>
      <li>1 aguacate, cortado en cubos</li>
    </ul>',
  '<ol>
    <li>Precalienta el horno a 200°C (390°F).</li>
    <li>Enjuaga bien la quinoa bajo agua fría para eliminar el sabor amargo. Luego, colócala en una olla con el caldo de verduras o agua. Lleva a ebullición, luego reduce el fuego, tapa y cocina a fuego lento durante 15-20 minutos, o hasta que la quinoa esté cocida y el líquido se haya absorbido. Retira del fuego y deja reposar durante unos minutos.</li>
    <li>Mientras la quinoa se cocina, prepara las verduras. Coloca los pimientos, el calabacín, la berenjena y la cebolla en una bandeja para hornear. Rocía con aceite de oliva, sal y pimienta al gusto. Mezcla bien para asegurarte de que todas las verduras estén cubiertas.</li>
    <li>Hornea las verduras en el horno precalentado durante unos 20-25 minutos, o hasta que estén tiernas y ligeramente doradas, revolviendo ocasionalmente.</li>
    <li>Una vez que la quinoa y las verduras estén listas, mezcla la quinoa cocida con las verduras asadas en un tazón grande.</li>
    <li>Adereza la ensalada con jugo de limón y hojas de cilantro fresco (si lo deseas). Mezcla bien.</li>
    <li>Sirve la ensalada en platos individuales y decora con cubos de aguacate.</li>
    <li>¡Disfruta de tu deliciosa ensalada de quinoa con vegetales asados!</li>
  </ol>',
  'por porción','160 kj / 38 kcal','3,3g','0,64g','2,69g','1,58g','0,422g','0,32g','0,552g','53mg','0,6g','143mg','59mg');

INSERT INTO CategoriaAlimento (nombre, imagen) VALUES ('Bebida', 'imagen_agua.jpg');
INSERT INTO CategoriaAlimento (nombre, imagen) VALUES ('Carne', 'imagen_carne.jpg');
INSERT INTO CategoriaAlimento (nombre, imagen) VALUES ('Verdura', 'imagen_verdura.jpg');
INSERT INTO CategoriaAlimento (nombre, imagen) VALUES ('Fruta', 'imagen_fruta.jpg');



INSERT INTO Alimento (nombre, imagen, tamanoPorcion, energia, carbohidratos, azucar, proteina, grasa, grasaSaturada, grasaPoliinsaturada, grasaMonoinsaturada, colesterol, fibra, sodio, potasio, categoria_id)
VALUES ('Cafe', 'imagen_cafe.png', '1 vaso', '50 kcal', '10g', '5g', '0g', '0g', '0g', '0g', '0g', '0mg', '0g', '10mg', '0mg', 1),
       ('Té', 'imagen_te.jpg', '1 vaso', '50 kcal', '10g', '5g', '0g', '0g', '0g', '0g', '0g', '0mg', '0g', '10mg', '0mg', 1),
        ('Agua', 'imagen_agua.jpg', '1 vaso', '50 kcal', '10g', '5g', '0g', '0g', '0g', '0g', '0g', '0mg', '0g', '10mg', '0mg', 1),
        ('Coca cola', 'imagen_cocacola.jpg', '1 vaso', '50 kcal', '10g', '5g', '0g', '0g', '0g', '0g', '0g', '0mg', '0g', '10mg', '0mg', 1);

/*INSERT INTO Alimento (nombre, imagen, tamanoPorcion, energia, carbohidratos, azucar, proteina, grasa, grasaSaturada, grasaPoliinsaturada, grasaMonoinsaturada, colesterol, fibra, sodio, potasio, categoria_id)
VALUES ('Carne1', 'imagen_carne1.jpg', '100g', '250 kcal', '0g', '0g', '20g', '17g', '6g', '0g', '0g', '70mg', '0g', '70mg', '300mg', 2),
       ('Carne1', 'imagen_carne1.jpg', '100g', '250 kcal', '0g', '0g', '20g', '17g', '6g', '0g', '0g', '70mg', '0g', '70mg', '300mg', 2),
       ('Carne1', 'imagen_carne1.jpg', '100g', '250 kcal', '0g', '0g', '20g', '17g', '6g', '0g', '0g', '70mg', '0g', '70mg', '300mg', 2),
       ('Carne1', 'imagen_carne1.jpg', '100g', '250 kcal', '0g', '0g', '20g', '17g', '6g', '0g', '0g', '70mg', '0g', '70mg', '300mg', 2),;

INSERT INTO Alimento (nombre, imagen, tamanoPorcion, energia, carbohidratos, azucar, proteina, grasa, grasaSaturada, grasaPoliinsaturada, grasaMonoinsaturada, colesterol, fibra, sodio, potasio, categoria_id)
VALUES ('Verdura1', 'imagen_verdura1.jpg', '100g', '25 kcal', '5g', '2g', '1g', '0g', '0g', '0g', '0g', '0mg', '3g', '10mg', '200mg', 3),
    ('Verdura1', 'imagen_verdura1.jpg', '100g', '25 kcal', '5g', '2g', '1g', '0g', '0g', '0g', '0g', '0mg', '3g', '10mg', '200mg', 3),
('Verdura1', 'imagen_verdura1.jpg', '100g', '25 kcal', '5g', '2g', '1g', '0g', '0g', '0g', '0g', '0mg', '3g', '10mg', '200mg', 3),
('Verdura1', 'imagen_verdura1.jpg', '100g', '25 kcal', '5g', '2g', '1g', '0g', '0g', '0g', '0g', '0mg', '3g', '10mg', '200mg', 3);

INSERT INTO Alimento (nombre, imagen, tamanoPorcion, energia, carbohidratos, azucar, proteina, grasa, grasaSaturada, grasaPoliinsaturada, grasaMonoinsaturada, colesterol, fibra, sodio, potasio, categoria_id)
VALUES ('Fruta1', 'imagen_fruta1.jpg', '100g', '60 kcal', '15g', '10g', '1g', '0g', '0g', '0g', '0g', '0mg', '2g', '0mg', '200mg', 4),
       ('Fruta1', 'imagen_fruta1.jpg', '100g', '60 kcal', '15g', '10g', '1g', '0g', '0g', '0g', '0g', '0mg', '2g', '0mg', '200mg', 4),
       ('Fruta1', 'imagen_fruta1.jpg', '100g', '60 kcal', '15g', '10g', '1g', '0g', '0g', '0g', '0g', '0mg', '2g', '0mg', '200mg', 4),
       ('Fruta1', 'imagen_fruta1.jpg', '100g', '60 kcal', '15g', '10g', '1g', '0g', '0g', '0g', '0g', '0mg', '2g', '0mg', '200mg', 4);

 */


