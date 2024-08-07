package br.com.myfood.pedidos.dto;

import br.com.myfood.pedidos.model.Pedido;
import br.com.myfood.pedidos.model.Status;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PedidoDto(
    Long id,

    LocalDateTime dataHora,

    Status status,

    List<ItemDoPedidoDto> itens
) {
    public PedidoDto(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getDataHora(),
                pedido.getStatus(),
                pedido.getItens().stream().map(ItemDoPedidoDto::new)
                    .collect(Collectors.toList())
        );
    }
}
