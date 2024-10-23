package com.jreque.examen1.controller;

import com.jreque.examen1.entity.PedidoEntity;
import com.jreque.examen1.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/persona/{personaId}")
    public ResponseEntity<PedidoEntity> crearPedido(@PathVariable Long personaId,
                                                    @RequestBody PedidoEntity pedido) {
        PedidoEntity nuevoPedido = pedidoService.crearPedido(personaId, pedido);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoEntity> buscarPedido(@PathVariable Long id) {
        PedidoEntity pedido = pedidoService.buscarPedidoPorId(id);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @GetMapping
    public List<PedidoEntity> buscarTodosPedidos() {
        return pedidoService.buscarTodos();
    }

    @PutMapping("/{id}/persona/{idPersona}")
    public ResponseEntity<PedidoEntity> actualizarPedido(@PathVariable Long id,
                                                         @PathVariable Long idCLiente,
                                                         @RequestBody PedidoEntity pedido) {
        PedidoEntity pedidoActualizado = pedidoService.actualizarPedido(id,idCLiente ,pedido);
        return new ResponseEntity<>(pedidoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
