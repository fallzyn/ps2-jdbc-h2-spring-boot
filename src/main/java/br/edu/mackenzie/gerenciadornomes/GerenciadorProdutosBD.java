package br.edu.mackenzie.gerenciadornomes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorProdutosBD implements GerenciadorProdutos {

    private final Connection connection;

    public GerenciadorProdutosBD(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean adicionar(Produto produto) {
        String sql = "INSERT INTO produtos (nome, quantidade, preco, ativo, data_cadastro) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            preencherProduto(statement, produto);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Produto> obter() {
        String sql = "SELECT id, nome, quantidade, preco, ativo, data_cadastro " +
                "FROM produtos ORDER BY id";
        List<Produto> produtos = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                produtos.add(new Produto(
                        resultSet.getLong("id"),
                        resultSet.getString("nome"),
                        resultSet.getInt("quantidade"),
                        resultSet.getDouble("preco"),
                        resultSet.getBoolean("ativo"),
                        resultSet.getDate("data_cadastro").toLocalDate()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtos;
    }

    @Override
    public boolean atualizar(long id, Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, quantidade = ?, preco = ?, " +
                "ativo = ?, data_cadastro = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            preencherProduto(statement, produto);
            statement.setLong(6, id);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean remover(long id) {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    private void preencherProduto(PreparedStatement statement, Produto produto) throws Exception {
        statement.setString(1, produto.getNome());
        statement.setInt(2, produto.getQuantidade());
        statement.setDouble(3, produto.getPreco());
        statement.setBoolean(4, produto.isAtivo());
        statement.setDate(5, Date.valueOf(produto.getDataCadastro()));
    }
}