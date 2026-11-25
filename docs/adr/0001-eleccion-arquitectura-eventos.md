# ADR-0001 · Elección de arquitectura de eventos para Q2 / fila 4

Variante: Q2 / fila 4
- Fecha: 2026-11-25 15:00
- Estado: aceptada

## Contexto

Tienda con un solo producto (Audífonos, 70000, stock 50, umbral u=6, descuento d=15 por ciento).
El atributo prioritario es Q2 (extensibilidad): cada pedido CONFIRMADO debe provocar dos
reacciones nuevas (factura con consecutivo F-0001 y aviso al cliente), y el registro del
pedido no puede fallar porque falle la factura. La sección Q2 de la Hoja 2 exige que el
consecutivo de factura solo avance cuando la factura se emite (fallo siempre para C-999).

## Alternativas consideradas

- Capas - descartada porque para Q2 el OrderService tendría que llamar directamente a
  facturación y notificación, acoplando el flujo del pedido a las reacciones y obligando a
  modificar OrderService cada vez que se sume una reacción. Con stock 50 y umbral 6 la
  lógica de pedidos ya es densa; añadirle orquestación de reacciones empeora la
  modificabilidad.
- Hexagonal - descartada porque, aunque aísla el dominio, la extensibilidad de reacciones
  no es su fuerte: las reacciones nuevas se orquestan en application, tocando el caso de
  uso de registro del pedido. Q2 pide justo lo contrario: sumar reacciones sin tocar el
  productor.
- Monolito modular - descartada porque, si bien permite módulos facturación y
  notificaciones, el disparo del nuevo efecto obliga a tocar el módulo de pedidos (o
  añadir un listener interno en él), lo que contradice el "sin tocar al productor" que Q2
  premia. Además el fallo de factura para C-999 debe aislarse del pedido; en monolito
  modular la transacción única del pedido complica ese aislamiento.

## Decisión

Se elige Arquitectura de eventos: el módulo orders publica PedidoConfirmado y los
módulos billing y notifications se suscriben como consumidores. El fallo de factura se
maneja dentro de BillingService sin afectar al pedido.

## Consecuencias

**Se gana:** sumar las dos reacciones nuevas (factura y aviso) sin tocar OrderService:
el productor solo publica PedidoConfirmado. El fallo de factura para C-999 queda aislado
en BillingService y el pedido permanece CONFIRMADO. El consecutivo solo avanza cuando la
factura se emite, tal como pide Q2.

**Se pierde:** la carpeta de eventos no trae Spring ni HTTP, el ID es texto (PED-001) y el
stock es en memoria; hay que adaptar el punto de partida antes de la Hoja 2 (ID numérico,
clienteId, descuento, estados, RN-5 síncrona). Además, el bus en memoria es síncrono para
la muestra, por lo que la consistencia eventual real no se ejercita. La prueba
ModuleIndependenceTest tiene la lista de módulos escrita a mano: hay que añadir billing y
notifications para que queden vigilados.

**Predicción del costo de la Hoja 2:**
- Crear: billing/Invoice.java, billing/InvoiceRepository.java, billing/BillingService.java,
  notifications/NotificationService.java, Hoja2Sample.java.
- Modificar: ModuleIndependenceTest.java, LEEME.md.
- NO modificar: OrderService.java, InventoryService.java, Order.java, OrderRepository.java,
  Hoja1Sample.java, ReglasNegocioTest.java.
