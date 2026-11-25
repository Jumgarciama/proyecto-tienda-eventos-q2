package com.tienda.notifications;

import com.tienda.event.PedidoConfirmado;

public class NotificationService {
    public void onPedidoConfirmado(PedidoConfirmado evento) {
        System.out.println("AVISO cliente=" + evento.getClienteId());
    }
}
