package cl.duoc.venta.service;

import cl.duoc.venta.client.*;
import cl.duoc.venta.dto.*;
import cl.duoc.venta.model.*;
import cl.duoc.venta.repository.VentaRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoClient productoClient;
    private final InventarioClient inventarioClient;
    private final PagoClient pagoClient;

    @Transactional 
    public Venta crearVenta(Ventarequestdto dto, String token) {
        Venta venta = new Venta();
        venta.setIdUsuario(dto.getIdUsuario());
        venta.setFecha(LocalDate.now());
        venta.setMetodoPago(dto.getMetodoPago());
        
        double totalCalculado = 0;
        List<DetalleVenta> detalles = new ArrayList<>();

        for (Detalleventarequestdto detDto : dto.getDetalles()) {

            ProductoDTO prod = productoClient.obtenerProducto(detDto.getIdProducto(), token);
            
            inventarioClient.descontarStock(detDto.getIdProducto(), detDto.getCantidad(), token);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setIdProducto(detDto.getIdProducto());
            detalle.setCantidad(detDto.getCantidad());
            detalle.setPrecioUnitario(prod.getPrecio());
            detalle.setSubtotal(prod.getPrecio() * detDto.getCantidad());
            detalle.setVenta(venta);
            detalles.add(detalle);
            
            totalCalculado += detalle.getSubtotal();
        }

        venta.setTotal(totalCalculado);
        venta.setDetalles(detalles);

        PagoResponseDTO pago = pagoClient.procesarPago(dto.getMetodoPago(), (int)totalCalculado, token);
        
        venta.setIdPago(pago.getIdPago());
        venta.setEstado("PAGADO");

        return ventaRepository.save(venta);
    }

    public List<Venta> listarTodas() { 
        return ventaRepository.findAll(); 
    }

    public List<Venta> obtenerVentasPorUsuario(Long idUsuario) {
        return ventaRepository.findByIdUsuario(idUsuario);
    }

    public Venta obtenerVentaPorId(@NonNull Long idVenta) {
        return ventaRepository.findById(idVenta).orElse(null);
    }
    
}