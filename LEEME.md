# LEEME

## Muestra Hoja 1 (arranque limpio)
cd codigo && mvn -q compile && java -cp target/classes com.tienda.Hoja1Sample

## Muestra Hoja 2 (arranque limpio, corrida aparte)
cd codigo && mvn -q compile && java -cp target/classes com.tienda.Hoja2Sample

## Pruebas y regla de arquitectura
cd codigo && mvn test
