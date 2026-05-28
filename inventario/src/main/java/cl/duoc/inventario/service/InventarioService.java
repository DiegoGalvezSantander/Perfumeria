package cl.duoc.inventario.service;

import cl.duoc.inventario.client.ProductoClient; // Importamos el nuevo cliente
import cl.duoc.inventario.dto.MovimientoRequestDTO;
import cl.duoc.inventario.model.Inventario;
import cl.duoc.inventario.model.MovimientoStock;
import cl.duoc.inventario.repository.InventarioRepository;
import cl.duoc.inventario.repository.MovimientoStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository inventarioRepo;
    private final MovimientoStockRepository movimientoRepo;
    private final ProductoClient productoClient; // Lo inyectamos aquí

    public String actualizarStock(MovimientoRequestDTO dto) {

        // 1. BARRERA DE SEGURIDAD: Validar que el producto exista en el microservicio de Productos
        productoClient.validarProducto(dto.getIdProducto());

        // 2. Lógica normal de inventario
        Inventario inv = inventarioRepo.findByIdProducto(dto.getIdProducto())
                .orElseGet(() -> {
                    Inventario nuevo = new Inventario();
                    nuevo.setIdProducto(dto.getIdProducto());
                    nuevo.setStockProducto(0);
                    return nuevo;
                });

        int nuevoStock = inv.getStockProducto() + dto.getCantidad();

        if (nuevoStock < 0) {
            throw new IllegalArgumentException(
                "Stock insuficiente para el producto ID: " + dto.getIdProducto() +
                ". Stock actual: " + inv.getStockProducto()
            );
        }

        inv.setStockProducto(nuevoStock);
        inventarioRepo.save(inv);

        MovimientoStock mov = new MovimientoStock();
        mov.setIdProducto(dto.getIdProducto());
        mov.setCantidad(dto.getCantidad());
        movimientoRepo.save(mov);

        return "Stock actualizado. Saldo actual: " + inv.getStockProducto();
    }
}