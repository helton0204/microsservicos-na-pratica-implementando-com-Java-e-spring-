package br.com.myfood.pagamentos.service;

import br.com.myfood.pagamentos.dto.PagamentoDto;
import br.com.myfood.pagamentos.httpClient.PedidoClient;
import br.com.myfood.pagamentos.model.Pagamento;
import br.com.myfood.pagamentos.model.Status;
import br.com.myfood.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoClient pedidoClient;

    public Page<PagamentoDto> obterTodos(Pageable paginacao) {
        return pagamentoRepository
                .findAll(paginacao)
                .map(pagamento -> new PagamentoDto(pagamento));
    }

    public PagamentoDto obterPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return new PagamentoDto(pagamento);
    }

    public PagamentoDto criarPagamento(PagamentoDto dto) {
        Pagamento pagamento = new Pagamento(dto);
        pagamento.setStatus(Status.CRIADO);
        System.out.println();
        pagamentoRepository.save(pagamento);
        return new PagamentoDto(pagamento);
    }

    public PagamentoDto atualizarPagamento(Long id, PagamentoDto dto) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento com ID " + id + " não encontrado"));

        Optional.ofNullable(dto.valor()).ifPresent(pagamento::setValor);
        Optional.ofNullable(dto.nome()).ifPresent(pagamento::setNome);
        Optional.ofNullable(dto.numero()).ifPresent(pagamento::setNumero);
        Optional.ofNullable(dto.expiracao()).ifPresent(pagamento::setExpiracao);
        Optional.ofNullable(dto.codigo()).ifPresent(pagamento::setCodigo);
        Optional.ofNullable(dto.status()).ifPresent(pagamento::setStatus);
        Optional.ofNullable(dto.pedidoId()).ifPresent(pagamento::setPedidoId);
        Optional.ofNullable(dto.formaDePagamentoId()).ifPresent(pagamento::setFormaDePagamentoId);

        pagamento = pagamentoRepository.save(pagamento);
        return new PagamentoDto(pagamento);
    }

    public void confirmarPagamento(Long id){
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);

        if (pagamento.isEmpty()) {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO);
        pagamentoRepository.save(pagamento.get());
        pedidoClient.atualizarPedido(pagamento.get().getPedidoId());
    }

    public void alteraStatus(Long id) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);

        if (pagamento.isEmpty()) {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
        pagamentoRepository.save(pagamento.get());
    }

    public void excluirPagamento(Long id) {
        pagamentoRepository.deleteById(id);
    }

}