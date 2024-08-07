package br.com.myfood.pedidos.model;

import br.com.myfood.pedidos.dto.ItemDoPedidoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_do_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDoPedido {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    private String descricao;

    @ManyToOne(optional=false)
    private Pedido pedido;

    public ItemDoPedido(ItemDoPedidoDto item) {
        this.quantidade = item.quantidade();
        this.descricao = item.descricao();
    }
}
