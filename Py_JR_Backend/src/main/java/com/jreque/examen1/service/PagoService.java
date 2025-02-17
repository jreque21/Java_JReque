package com.jreque.examen1.service;
import com.jreque.examen1.entity.PagoEntity;
import java.util.List;

public interface PagoService {
    public PagoEntity crearPago(Long alumnoId, PagoEntity pago);
    public List<PagoEntity> buscarTodos();
    public PagoEntity buscarPagoPorId(Long id);
    public PagoEntity actualizarPago(Long id, Long idAlumno, PagoEntity pago);
    public void eliminarPago(Long id);
}
