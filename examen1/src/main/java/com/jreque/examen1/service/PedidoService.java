package com.jreque.examen1.service;
import com.jreque.examen1.entity.PedidoEntity;
import java.util.List;

public interface PedidoService {
    public PedidoEntity crearPedido(Long personaId, PedidoEntity pedido);
    public List<PedidoEntity> buscarTodos();
    public PedidoEntity buscarPedidoPorId(Long id);
    public PedidoEntity actualizarPedido(Long id, Long idCliente, PedidoEntity pedido);
    public void eliminarPedido(Long id);
}
