package br.com.myfood.pagamentos.model;

import br.com.myfood.pagamentos.dto.PagamentoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Long pedidoId;
    private Long formaDePagamentoId;

    public Pagamento(PagamentoDto dto) {
        this.valor = dto.valor();
        this.nome = dto.nome();
        this.numero = dto.numero();
        this.expiracao = dto.expiracao();
        this.codigo = dto.codigo();
        this.status = dto.status();
        this.pedidoId = dto.pedidoId();
        this.formaDePagamentoId = dto.formaDePagamentoId();
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "id=" + id +
                ", valor=" + valor +
                ", nome='" + nome + '\'' +
                ", numero='" + numero + '\'' +
                ", expiracao='" + expiracao + '\'' +
                ", codigo='" + codigo + '\'' +
                ", status=" + status +
                ", pedidoId=" + pedidoId +
                ", formaDePagamentoId=" + formaDePagamentoId +
                '}';
    }
}