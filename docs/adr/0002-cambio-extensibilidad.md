# ADR-0002 · Absorción del cambio Q2 (factura + aviso)

Variante: Q2 / fila 4
- Fecha: 2026-11-25 16:20
- Estado: aceptada

## Contexto

La Hoja 2 (Q2) exige que cada pedido CONFIRMADO emita una factura con consecutivo F-0001,
F-0002, y un aviso al cliente. La factura falla siempre para clienteId C-999, y el pedido
debe permanecer CONFIRMADO sin consumir consecutivo. Los pedidos RECHAZADO no emiten nada.

## Alternativas consideradas

- Modificar OrderService para llamar a facturación y notificación - descartada porque
  acopla el productor a las reacciones y contradice Q2 (sumar reacciones sin reabrir el
  flujo de registro del pedido).
- Añadir las reacciones dentro de InventoryService - descartada porque mezcla
  responsabilidades y hace que un fallo de factura pueda afectar al stock.

## Decisión

Se crean dos módulos consumidores: billing (escucha PedidoConfirmado, emite factura o
imprime FACTURA FALLO para C-999, y solo avanza el consecutivo si emite) y notifications
(escucha PedidoConfirmado e imprime AVISO cliente=). OrderService no se modifica.

## Consecuencias

**Se gana:** el productor queda intacto; sumar una tercera reacción sería añadir otro
consumidor sin tocar OrderService. El fallo de factura queda aislado en BillingService.

**Se pierde:** hay que registrar los nuevos módulos en ModuleIndependenceTest para que
queden vigilados (la lista es manual). El bus en memoria es síncrono en la muestra, así
que la consistencia eventual real no se ejercita.

**Veredicto sobre ADR-0001:** la predicción acertó. El git diff hoja-1..final muestra
exactamente los 5 archivos nuevos y los 2 modificados predichos; OrderService,
InventoryService y ReglasNegocioTest no se tocaron. La pérdida declarada (adaptar el punto
de partida) ya se había pagado en la Hoja 1. El ADR-0001 se mantiene vigente.
