package br.com.myfood.pedidos.dto;

import br.com.myfood.pedidos.model.ItemDoPedido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemDoPedidoDto(
    Long id,

    @NotNull
    @Positive
    Integer quantidade,

    @NotBlank
    String descricao
) {
    public ItemDoPedidoDto(ItemDoPedido item) {
        this(
            item.getId(),
            item.getQuantidade(),
            item.getDescricao()
        );
    }
}
