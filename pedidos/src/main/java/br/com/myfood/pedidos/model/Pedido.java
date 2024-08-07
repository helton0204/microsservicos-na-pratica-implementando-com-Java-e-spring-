package br.com.myfood.pedidos.model;

import br.com.myfood.pedidos.dto.PedidoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", orphanRemoval = true)
    private List<ItemDoPedido> itens = new ArrayList<>();

    public Pedido(PedidoDto dto) {
        this.dataHora = dto.dataHora();
        this.status = dto.status();
        this.itens = dto.itens().stream()
                .map(ItemDoPedido::new)  // Presumindo que há um construtor apropriado
                .collect(Collectors.toList());
    }
}