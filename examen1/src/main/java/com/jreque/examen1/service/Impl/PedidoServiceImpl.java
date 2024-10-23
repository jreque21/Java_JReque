package com.jreque.examen1.service.Impl;

import com.jreque.examen1.entity.PedidoEntity;
import com.jreque.examen1.entity.PersonaEntity;
import com.jreque.examen1.repository.PedidoRepository;
import com.jreque.examen1.repository.PersonaRepository;
import com.jreque.examen1.service.PedidoService;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository pedidoRepository;
    private final PersonaRepository personaRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, PersonaRepository personaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.personaRepository = personaRepository;
    }

    public PedidoEntity crearPedido(Long personaId, PedidoEntity pedido) {
        PersonaEntity personaExistente = personaRepository.findById(personaId)
                .orElseThrow(() -> new NoSuchElementException("Error Persona no existe."));
        pedido.setPersona(personaExistente);
        return pedidoRepository.save(pedido);
    }

    // BUSCAR - Debe realizar la búsqueda de todos los pedidos con estado parametrizado
    public List<PedidoEntity> buscarTodos() {
        return pedidoRepository.findAll();
    }

    // BUSCAR - Debe buscar un pedido por id
    public PedidoEntity buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("Pedido no encontrado"));
    }

    // CRUD - Actualizar
    public PedidoEntity actualizarPedido(Long id, Long idCliente, PedidoEntity pedido) {
        PedidoEntity pedidoExistente = buscarPedidoPorId(id);
        pedidoExistente.setDescripcion(pedido.getDescripcion());
        pedidoExistente.setCantidad(pedido.getCantidad());
        return pedidoRepository.save(pedidoExistente);
    }

    // CRUD - Eliminar
    public void eliminarPedido(Long id) {
        PedidoEntity pedidoExistente = buscarPedidoPorId(id);
        // Setear auditoría
        pedidoExistente.setEstado(0);
        pedidoExistente.setDelete_by("ADMIN");
        pedidoExistente.setDelete_date(new Timestamp(System.currentTimeMillis()));
        // Grabar eliminación lógica
        pedidoRepository.save(pedidoExistente);
    }

}
