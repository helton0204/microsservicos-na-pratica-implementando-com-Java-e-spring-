package br.com.myfood.pedidos.service;

import br.com.myfood.pedidos.dto.PedidoDto;
import br.com.myfood.pedidos.dto.StatusDto;
import br.com.myfood.pedidos.model.Pedido;
import br.com.myfood.pedidos.model.Status;
import br.com.myfood.pedidos.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<PedidoDto> obterTodos() {
        return pedidoRepository.findAll().stream()
                .map(PedidoDto::new)
                .collect(Collectors.toList());
    }

    public PedidoDto obterPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return new PedidoDto(pedido);
    }

    public PedidoDto criarPedido(PedidoDto dto) {
        Pedido pedido = new Pedido(dto);

        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(Status.REALIZADO);
        pedido.getItens().forEach(item -> item.setPedido(pedido));
        Pedido salvo = pedidoRepository.save(pedido);

        return new PedidoDto(pedido);
    }

    public PedidoDto atualizaStatus(Long id, StatusDto dto) {

        Pedido pedido = pedidoRepository.porIdComItens(id);

        if (pedido == null) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(dto.status());
        pedidoRepository.atualizaStatus(dto.status(), pedido);
        return new PedidoDto(pedido);
    }

    public void aprovaPagamentoPedido(Long id) {

        Pedido pedido = pedidoRepository.porIdComItens(id);

        if (pedido == null) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(Status.PAGO);
        pedidoRepository.atualizaStatus(Status.PAGO, pedido);
    }

    public void excluirPedido(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            pedidoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Pedido não encontrado com o ID: " + id);
        }
    }
}
