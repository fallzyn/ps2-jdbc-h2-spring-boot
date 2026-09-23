package br.edu.mackenzie.gerenciadornomes;

import java.time.LocalDate;

public class Produto {
    private long id;
    private String nome;
    private int quantidade;
    private double preco;
    private boolean ativo;
    private LocalDate dataCadastro;

    public Produto(String nome, int quantidade, double preco, boolean ativo, LocalDate dataCadastro) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.ativo = ativo;
        this.dataCadastro = dataCadastro;
    }

    public Produto(long id, String nome, int quantidade, double preco, boolean ativo, LocalDate dataCadastro) {
        this(nome, quantidade, preco, ativo, dataCadastro);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                ", ativo=" + ativo +
                ", dataCadastro=" + dataCadastro +
                '}';
    }
}
