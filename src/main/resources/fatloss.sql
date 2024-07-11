
use fatloss;

/*


INSERT INTO Ejercicio (nombre, intensidad, caloriasQuemadasPorHora) VALUES
                                                                        ('Caminata', 'baja', 200),
                                                                        ('Correr', 'alta', 600),
                                                                        ('Bicicleta', 'moderada', 400),
                                                                        ('Nadar', 'alta', 700),
                                                                        ('Yoga', 'baja', 150),
                                                                        ('Levantamiento de pesas', 'alta', 500),
                                                                        ('Baile', 'moderada', 350);


select * from usuario;

select * from EjercicioUsuario;

Insert into Receta (nombre,imagen,descripcion,ingredientes,instrucciones,energia, fibra, calorias, colesterol, sodio, potasio,
                    grasa, grasaSaturada, grasaPoliinsaturada, grasaMonoinsaturada, carbohidratos, azucar,proteina)
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
       600, 12, 450, 0, 150, 1500, 30, 4, 2, 22, 80, 10, 15),

      (
          'Arroz Con Pollo','arroz_conpollo.jpg',
          'Un plato de pollo con arroz saciante y delicioso para el almuerzo o la cena.',
          '<ul>
              <li>130 g pechuga de pollo</li>
              <li>160 g arroz blanco</li>
              <li>1 grande huevo duro</li>
              <li>25 g cebollas</li>
              <li>2 cucharadas de sopa aceite puro de girasol</li>
              <li>25 g morrón</li>
          </ul>',
          '<ol>
              <li>Hervir el huevo y cocinar el arroz.</li>
              <li>Corta la cebolla, el pimiento morrón y el pollo en cuadritos o tiras.</li>
              <li>Agrega un poco de agua a la olla y agrega la cebolla y el pimiento morrón.</li>
              <li>Una vez consumida el agua de la cebolla y el pimiento morrón, agrega el pollo y sal al gusto.</li>
              <li>Cuando se consuma el agua de la pechuga, agrega el aceite y sazona al gusto.</li>
              <li>Freír hasta que esté bien cocido.</li>
              <li>Luego agrega el pollo cocido encima del arroz blanco con el huevo en rodajas.</li>
          </ol>',
          700, 4, 600, 150, 500, 800, 25, 5, 3, 15, 80, 5, 40
      ),

      (
          'Trufas De Bananas Y Chocolate','trufas_banana_chocolate.jpeg',
          'Delicioso, bajo en carbohidratos y fácil de hacer.',
          '<ul>
              <li>60 g coco rallado</li>
              <li>200 g banana</li>
              <li>20 g mantequilla de maní</li>
              <li>10 g cacao amargo</li>
          </ul>',
          '<ol>
              <li>Triturar los plátanos.</li>
              <li>Agrega el cacao.</li>
              <li>Agrega la mantequilla de maní.</li>
              <li>Darles forma de bolitas, rebozarlas con coco rallado y meterlas en el frigorífico.</li>
          </ol>',
          400, 6, 350, 0, 5, 600, 20, 10, 2, 6, 60, 30, 6
      ),

      (
          'Mezclar Ensalada Con Huevo','ensalada_huevo.jpg',
          'Receta sana y fácil.',
          '<ul>
              <li>1 la mitad de tomate</li>
              <li>1 grande huevo duro</li>
              <li>1/2 cucharada aceite de oliva</li>
              <li>6 hojas lechuga de hoja verde</li>
              <li>1/2 mediana zanahorias pequeñas</li>
          </ul>',
          '<ol>
              <li>Rallar las zanahorias.</li>
              <li>Cortar el huevo, la lechuga y los tomates.</li>
              <li>Mezclar todo y sazonar al gusto.</li>
          </ol>',
          200, 4, 150, 200, 50, 300, 10, 2, 1, 2, 12, 6, 10
      ),
      (
          'Waffles De Avena','waffles_avena.jpeg',
          'Desayuno o merienda baja en calorías.',
          '<ul>
              <li>1 grande huevo</li>
              <li>60 g avena instantánea</li>
              <li>1 cucharadita polvo de hornear (sulfato de aluminio de sodio, de doble efecto)</li>
          </ul>',
          '<ol>
              <li>Combine todos los ingredientes hasta que quede suave. Agregue un poco de agua si es necesario.</li>
              <li>Caliente una plancha para gofres ligeramente engrasada.</li>
              <li>Cocine la masa hasta que esté dorada.</li>
          </ol>',
          300, 5, 200, 150, 100, 300, 10, 2, 1, 2, 30, 5, 10
      ),
      (
          'Panqueques','panqueques.jpg',
          'Desayuno fácil y rápido bajo en calorías.',
          '<ul>
              <li>150 ml leche entera</li>
              <li>4 mediano huevos</li>
              <li>1 cucharadita extracto de vainilla</li>
              <li>50 g avena</li>
              <li>2 ml edulcorante de mesa</li>
              <li>1 mediana banana</li>
              <li>30 g pasta de maní</li>
              <li>1/2 taza de te harina integral leudante</li>
          </ul>',
          '<ol>
              <li>Mezcle los huevos, el edulcorante y el extracto de vainilla en un bol.</li>
              <li>Agregar los demás ingredientes y seguir mezclando.</li>
              <li>Cocine individualmente en forma de panqueques en una sartén hasta que estén dorados.</li>
          </ol>',
          250, 4, 150, 100, 50, 200, 8, 2, 1, 1, 30, 10, 8

      ),
      (
          'Alfajorcitos De Coco','alfajores_coco.jpg',
          'Sin harinas y gluten free receta.',
          '<ul>
              <li>1 grande huevo</li>
              <li>40 g dulce de leche</li>
              <li>40 g coco rallado</li>
              <li>1 sobre sweet</li>
          </ul>',
          '<ol>
              <li>En un bowl, mezclar el huevo con el coco rallado y el edulcorante.</li>
              <li>Distribuir la masa que se formó en una placa para horno con forma de galletitas (redondas).</li>
              <li>Llevar a un horno a 210°c por 10 mins.</li>
              <li>Sacar las galletitas del horno y esperar a que enfríen.</li>
              <li>Por último, rellenamos las tapitas con dulce de leche y formamos un alfajor.</li>
              <li>Con estas cantidades, nos van a salir 2 alfajores grandes aprox.</li>
          </ol>',
          300, 5, 250, 100, 50, 300, 15, 10, 2, 8, 40, 25, 8
      ),
      (
          'Helado Proteico De Frutilla','helado_frutilla_proteico.jpeg',
          'Rico en proteína de helado de fresa.',
          '<ul>
              <li>150 g frutillas</li>
              <li>2 grande clara de huevos</li>
              <li>30 ml agua</li>
              <li>50 g azucar light</li>
              <li>5 g gelatina sin sabor</li>
          </ul>',
          '<ol>
              <li>Lavar y cortar las fresas en dados, dejar reposar con una cucharada de azúcar para que suelte su jugo. Reserva algunas de las fresas cortadas en cubitos para que tengan otra textura. Luego licúa el resto y reserva (se puede calentar al microondas por 2 minutos antes de licuar para que quede como una salsa).</li>
              <li>Preparar un almíbar ligero mezclando el resto del azúcar y el agua, llevar a fuego mínimo y cuando empiece a burbujear, en un bol aparte, comenzar con el batido de las claras a nieve. Asegúrate de que el almíbar no se queme ni se convierta en caramelo mientras se baten las claras si es necesario apartar unos momentos del fuego.</li>
              <li>Cuando el almíbar llegue a "bolígrafo", retirar del fuego y añadir a las claras en forma de hilo muy fino en los bordes del bol, batir continuamente hasta que el merengue alcance la temperatura ambiente.</li>
              <li>Una vez tibio el merengue, hidratar la gelatina sin sabor con 2 cucharadas de agua hirviendo, remover para que se disuelva por completo, y agregar de la misma forma que el almíbar a la preparación anterior, poco a poco y batir continuamente.</li>
              <li>Cuando toda la gelatina esté integrada, utiliza una espátula para incorporar la salsa de fresa desde el primer paso con movimientos envolventes. Ahora agregue los trozos de fresas.</li>
              <li>Colocar en moldes y llevar al freezer por al menos 2 horas antes de consumir. Con estas cantidades salen 2 porciones abundantes (1 taza) o 4 pequeñas.</li>
          </ol>',
          150, 3, 100, 0, 10, 200, 2, 0, 0, 0, 25, 10, 8
      );


-- Inserción de categorías
INSERT INTO CategoriaAlimento (nombre, imagen) VALUES ('Bebidas', 'imagen_agua.jpg');
INSERT INTO CategoriaAlimento (nombre, imagen) VALUES ('Carnes', 'imagen_carne.jpg');
INSERT INTO CategoriaAlimento (nombre, imagen) VALUES ('Verduras', 'imagen_verdura.jpg');
INSERT INTO CategoriaAlimento (nombre, imagen) VALUES ('Frutas', 'imagen_fruta.jpg');
INSERT INTO CategoriaAlimento (nombre, imagen) VALUES ('Harinas', 'harinas.jpeg');
INSERT INTO CategoriaAlimento (nombre, imagen) VALUES ('Comida rápida', 'comida_rapida.jpg');

-- Inserción de alimentos
INSERT INTO Alimento (nombre, imagen, baseEnergia,baseFibra ,baseCalorias, baseColesterol, baseSodio, basePotasio,
                      baseGrasa, baseGrasaSaturada, baseGrasaPoliinsaturada, baseGrasaMonoinsaturada, baseCarbohidratos, baseAzucar, baseProteina, categoria_id,esPersonalizado)
VALUES
    ('Cafe', 'imagen_cafe.png', 2, 0, 2, 0, 5, 116, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 1,false),
    ('Té', 'imagen_te.jpg', 1, 0, 1, 0, 2, 88, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1,false),
    ('Agua', 'imagen_agua.jpg', 0, 0, 0, 0, 0, 0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1,false),
    ('Coca cola', 'imagen_cocacola.jpg',320 , 0, 120, 34, 50, 5, 0, 0, 0, 0, 21, 100, 0, 1,false),
    ('Bife asado', 'bife_asado.jpeg', 250, 0, 250, 90, 72, 320, 18.0, 7.0, 0.5, 8.0, 0.0, 0.0, 26.0, 2,false),
    ('Pescado frito', 'pescado_frito.jpg', 200, 0, 200, 50, 300, 450, 12.0, 2.5, 1.0, 5.0, 10.0, 0.0, 18.0, 2,false),
    ('Pollo cocido', 'pollo_cocido.jpeg', 165, 0, 165, 85, 70, 250, 6.0, 2.0, 0.5, 2.5, 0.0, 0.0, 31.0, 2,false),
    ('Milanesa de carne', 'milanesa _carne.jpg', 320, 1, 320, 70, 350, 400, 20.0, 5.0, 2.0, 9.0, 15.0, 0.0, 20.0, 2,false),
    ('Banana', 'banana.jpg', 89, 2.6, 89, 0, 1, 358, 0.3, 0.1, 0.1, 0.0, 22.8, 12.2, 1.1, 4,false),
    ('Manzana', 'manzana.jpeg', 52, 2.4, 52, 0, 1, 107, 0.2, 0.0, 0.1, 0.0, 13.8, 10.4, 0.3, 4,false),
    ('Naranja', 'naranja.jpg', 47, 2.4, 47, 0, 0, 181, 0.1, 0.0, 0.0, 0.0, 11.8, 9.4, 0.9, 4,false),
    ('Frutilla', 'frutilla.jpeg', 32, 2.0, 32, 0, 1, 153, 0.3, 0.0, 0.1, 0.0, 7.7, 4.9, 0.7, 4,false),
    ('Lechuga', 'lechuga.jpeg', 15, 1.3, 15, 0, 28, 194, 0.2, 0.0, 0.1, 0.0, 2.9, 0.8, 1.4, 3,false),
    ('Papa cocida', 'papa_cocida.jpeg', 77, 2.2, 77, 0, 6, 421, 0.1, 0.0, 0.0, 0.0, 17.6, 0.8, 2.0, 3,false),
    ('Zapallo', 'zapallo.jpeg', 26, 0.5, 26, 0, 1, 340, 0.1, 0.0, 0.0, 0.0, 6.5, 2.8, 1.0, 3,false),
    ('Tomate', 'tomate.jpeg', 18, 1.2, 18, 0, 5, 237, 0.2, 0.0, 0.1, 0.0, 3.9, 2.6, 0.9, 3,false),
    ('Medialunas', 'medialunas.jpeg', 407, 1.2, 407, 40, 370, 100, 22.0, 9.0, 1.0, 8.0, 45.0, 20.0, 8.0, 5,false),
    ('Pan criollo', 'pan_criollo.jpg', 279, 2.6, 279, 0, 523, 128, 6.3, 2.2, 1.6, 1.8, 50.6, 2.2, 8.6, 5,false),
    ('Pan lactal', 'pan_lactal.jpg', 265, 2.5, 265, 0, 490, 115, 3.5, 1.0, 0.9, 1.4, 49.0, 4.0, 8.0, 5,false),
    ('Churros', 'churros.jpg', 404, 1.3, 404, 0, 210, 93, 22.1, 5.6, 8.2, 6.8, 46.0, 16.5, 4.1, 5,false),
    ('Pancho', 'pancho.jpeg', 290, 1.5, 290, 40, 980, 100, 15.0, 6.0, 0.5, 7.0, 25.0, 5.0, 10.0, 6,false),
    ('Papas fritas', 'papas_fritas.jpeg', 312, 3.0, 312, 0, 210, 600, 15.0, 2.3, 3.7, 6.1, 41.0, 0.3, 3.4, 6,false),
    ('Pizza', 'pizza.jpeg', 266, 2.3, 266, 17, 598, 172, 10.0, 4.0, 1.2, 3.8, 33.0, 6.0, 11.0, 6,false),
    ('Hamburguesa', 'hamburguesa.jpg', 295, 2.5, 295, 50, 500, 300, 14.0, 5.0, 0.5, 7.5, 30.0, 7.0, 17.0, 6,false);

*/
