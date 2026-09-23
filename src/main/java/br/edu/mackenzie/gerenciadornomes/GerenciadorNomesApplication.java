package br.edu.mackenzie.gerenciadornomes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class GerenciadorNomesApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GerenciadorNomesApplication.class, args);
    }

    @Override
    public void run(String... args) {

        String url = obterVariavel("SUPABASE_JDBC_URL",
            "jdbc:postgresql://aws-0-us-east-2.pooler.supabase.com:5432/postgres?sslmode=require");
        String usuario = obterVariavel("SUPABASE_DB_USER", "postgres.xtviauejoadypynjrpft");
        String senha = obterVariavel("SUPABASE_DB_PASSWORD", System.getenv("DB_PASSWORD"));

        try (Connection connection =
                DriverManager.getConnection(url, usuario, senha);
            ) {
            testarProdutos(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String obterVariavel(String nome, String valorPadrao) {
        String valor = System.getenv(nome);
        if (valor == null || valor.isBlank()) {
            valor = valorPadrao;
        }
        if (valor == null || valor.isBlank()) {
            throw new IllegalStateException("Configure a variável de ambiente " + nome + " antes de executar.");
        }
        return valor;
    }

    private void testarProdutos(Connection connection) {
        GerenciadorProdutos gerenciador = new GerenciadorProdutosBD(connection);
        LocalDate hoje = LocalDate.now();

        Produto teclado = new Produto("Teclado", 10, 149.90, true, hoje);
        Produto mouse = new Produto("Mouse", 20, 79.90, true, hoje);
        Produto monitor = new Produto("Monitor", 5, 899.90, true, hoje);

        gerenciador.adicionar(teclado);
        gerenciador.adicionar(mouse);
        gerenciador.adicionar(monitor);

        System.out.println("Produtos cadastrados:");
        gerenciador.obter().forEach(System.out::println);

        List<Produto> produtos = gerenciador.obter();
        if (!produtos.isEmpty()) {
            Produto primeiro = produtos.get(0);
            System.out.println("\nAtualizando o primeiro produto: " +
                    gerenciador.atualizar(primeiro.getId(),
                            new Produto("Teclado mecânico", 8, 249.90, true, hoje)));
            System.out.println("Removendo o último produto: " +
                    gerenciador.remover(produtos.get(produtos.size() - 1).getId()));
        }

        System.out.println("\nProdutos finais:");
        gerenciador.obter().forEach(System.out::println);
    }
}
