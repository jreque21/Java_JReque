package com.jreque.examen1.controller;

import com.jreque.examen1.entity.PagoEntity;
import com.jreque.examen1.service.PagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping("/alumno/{alumnoId}")
    public ResponseEntity<PagoEntity> crearPago(@PathVariable Long alumnoId,
                                                  @RequestBody PagoEntity pago) {
        PagoEntity nuevoPago = pagoService.crearPago(alumnoId, pago);
        return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoEntity> buscarPago(@PathVariable Long id) {
        PagoEntity pago = pagoService.buscarPagoPorId(id);
        return new ResponseEntity<>(pago, HttpStatus.OK);
    }

    @GetMapping
    public List<PagoEntity> buscarTodosPagos() {
        return pagoService.buscarTodos();
    }

    @PutMapping("/{id}/alumno/{idAlumno}")
    public ResponseEntity<PagoEntity> actualizarPago(@PathVariable Long id,
                                                       @PathVariable Long idAlumno,
                                                       @RequestBody PagoEntity pago) {
        PagoEntity pagoActualizado = pagoService.actualizarPago(id,idAlumno ,pago);
        return new ResponseEntity<>(pagoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.eliminarPago(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
