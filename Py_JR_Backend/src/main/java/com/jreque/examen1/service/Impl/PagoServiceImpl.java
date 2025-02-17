package com.jreque.examen1.service.Impl;

import com.jreque.examen1.entity.PagoEntity;
import com.jreque.examen1.entity.AlumnoEntity;
import com.jreque.examen1.repository.PagoRepository;
import com.jreque.examen1.repository.AlumnoRepository;
import com.jreque.examen1.service.PagoService;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PagoServiceImpl implements PagoService {
    private final PagoRepository pagoRepository;
    private final AlumnoRepository alumnoRepository;

    public PagoServiceImpl(PagoRepository pagoRepository, AlumnoRepository alumnoRepository) {
        this.pagoRepository = pagoRepository;
        this.alumnoRepository = alumnoRepository;
    }

    public PagoEntity crearPago(Long alumnoId, PagoEntity pago) {
        AlumnoEntity alumnoExistente = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new NoSuchElementException("Error Persona no existe."));
        pago.setPersona(alumnoExistente);
        return pagoRepository.save(pago);
    }

    // BUSCAR - Debe realizar la búsqueda de todos los pedidos con estado parametrizado
    public List<PagoEntity> buscarTodos() {
        return pagoRepository.findAll();
    }

    // BUSCAR - Debe buscar un pedido por id
    public PagoEntity buscarPagoPorId(Long id) {
        return pagoRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("Pago no encontrado"));
    }

    // CRUD - Actualizar
    public PagoEntity actualizarPago(Long id, Long idAlumno, PagoEntity pago) {
        PagoEntity pagoExistente = buscarPagoPorId(id);
        pagoExistente.setDescripcion(pago.getDescripcion());
        pagoExistente.setCantidad(pago.getCantidad());
        return pagoRepository.save(pagoExistente);
    }

    // CRUD - Eliminar
    public void eliminarPago(Long id) {
        PagoEntity pagoExistente = buscarPagoPorId(id);
        // Setear auditoría
        pagoExistente.setEstado(0);
        pagoExistente.setDelete_by("ADMIN");
        pagoExistente.setDelete_date(new Timestamp(System.currentTimeMillis()));
        // Grabar eliminación lógica
        pagoRepository.save(pagoExistente);
    }

}
