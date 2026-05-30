package cl.duoc.pago.service;

import cl.duoc.pago.dto.Pagorequestdto;
import cl.duoc.pago.model.Pago;
import cl.duoc.pago.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepo;

    public Pago procesarPago(Pagorequestdto dto) {
        Pago pago = new Pago();
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setMonto(dto.getMonto());
        
        
        return pagoRepo.save(pago);
    }
    
}