# Informe · Q2 / fila 4

## Tabla de medición

Salida real de `git diff --name-status hoja-1 final`:

| Archivo | Estado | ¿Predicho en ADR-0001? |
|---------|--------|------------------------|
| codigo/src/main/java/com/tienda/Hoja2Sample.java | A | Sí (crear) |
| codigo/src/main/java/com/tienda/billing/Invoice.java | A | Sí (crear) |
| codigo/src/main/java/com/tienda/billing/InvoiceRepository.java | A | Sí (crear) |
| codigo/src/main/java/com/tienda/billing/BillingService.java | A | Sí (crear) |
| codigo/src/main/java/com/tienda/notifications/NotificationService.java | A | Sí (crear) |
| codigo/src/test/java/com/tienda/ModuleIndependenceTest.java | M | Sí (modificar) |
| LEEME.md | M | Sí (modificar) |
| codigo/src/main/java/com/tienda/orders/OrderService.java | — | Predicho NO tocar → no se tocó |
| codigo/src/main/java/com/tienda/inventory/InventoryService.java | — | Predicho NO tocar → no se tocó |
| codigo/src/test/java/com/tienda/ReglasNegocioTest.java | — | Predicho NO tocar → no se tocó |

## Las tres preguntas

1. ¿Se tocó alguna prueba de la Hoja 1?
   No. ReglasNegocioTest quedó intacto; el git diff no lo lista. Las pruebas de RN-1 a RN-3
   pasan sin modificarse (7/7 OK).

2. ¿Ocurrió lo que el ADR-0001 dijo que se perdería?
   Parcialmente. La predicción de que el productor (OrderService) no se tocaría se cumplió:
   las reacciones se absorbieron creando billing/ y notifications/. La pérdida declarada
   (adaptar el punto de partida: ID numérico, clienteId, descuento, estados, RN-5 síncrona)
   ya se había pagado en la Hoja 1, no en la Hoja 2. El bus síncrono en memoria se usó en
   la muestra, tal como se declaró.

3. ¿La predicción acertó?
   Sí, al 100 por ciento. Se crearon las 5 clases predichas y se modificaron exactamente
   ModuleIndependenceTest y LEEME.md. No se tocó OrderService, InventoryService, Order,
   OrderRepository, Hoja1Sample ni ReglasNegocioTest.

## Violación inducida (para la evidencia)

1. Editar codigo/src/main/java/com/tienda/orders/OrderService.java y añadir al inicio:
   import com.tienda.inventory.InventoryService;
2. Ejecutar cd codigo && mvn test → debe FALLAR en ordersNoDependeDeOtros.
3. Revertir con git checkout -- codigo/src/main/java/com/tienda/orders/OrderService.java
4. Ejecutar cd codigo && mvn test → debe PASAR.
5. Pegar las dos salidas en evidencia.txt (NO commitear el cambio inducido).
