package br.edu.mackenzie.gerenciadornomes;

import java.util.List;

public interface GerenciadorProdutos {
    boolean adicionar(Produto produto);

    List<Produto> obter();

    boolean atualizar(long id, Produto produto);

    boolean remover(long id);
}