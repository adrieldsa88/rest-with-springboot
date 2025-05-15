package https.github.com.Adrieldsa88;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Scanner;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class ApiMenuInterativo implements CommandLineRunner {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private final String BASE_URL = "http://localhost:8080/person";
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        exibirMenu();
    }

    private void exibirMenu() {
        while (true) {
            System.out.println("\n=== MENU PERSON API ===");
            System.out.println("1. Listar todas as pessoas");
            System.out.println("2. Buscar pessoa por ID");
            System.out.println("3. Criar nova pessoa");
            System.out.println("4. Atualizar pessoa");
            System.out.println("5. Deletar pessoa");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            try {
                switch (opcao) {
                    case 1:
                        listarPessoas();
                        break;
                    case 2:
                        buscarPessoaPorId();
                        break;
                    case 3:
                        criarPessoa();
                        break;
                    case 4:
                        atualizarPessoa();
                        break;
                    case 5:
                        deletarPessoa();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        System.exit(0);
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private void listarPessoas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/all"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        try {
            JsonNode jsonNode = objectMapper.readTree(response.body());
            System.out.println("\nLista de Pessoas:");
            System.out.println("[");

            if (jsonNode.isArray()) {
                for (JsonNode node : jsonNode) {
                    String prettyPerson = objectMapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(node);
                    System.out.println(prettyPerson);
                    System.out.println(","); // Separador entre pessoas
                }
            }

            System.out.println("]");
        } catch (JsonProcessingException e) {
            System.out.println(response.body()); // Fallback se não for JSON
        }
    }

    private void buscarPessoaPorId() throws Exception {
        System.out.print("Digite o ID da pessoa: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        try {
            JsonNode personNode = objectMapper.readTree(response.body());
            String prettyPerson = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(personNode);
            System.out.println("\nPessoa encontrada:");
            System.out.println(prettyPerson);
        } catch (JsonProcessingException e) {
            System.out.println(response.body()); // Fallback se não for JSON
        }
    }

    private void criarPessoa() {
        try {
            System.out.println("\n=== Criar Nova Pessoa ===");

            // Coletar dados
            System.out.print("Nome: ");
            String firstName = scanner.nextLine().trim();

            System.out.print("Sobrenome: ");
            String lastName = scanner.nextLine().trim();

            System.out.print("Endereço: ");
            String address = scanner.nextLine().trim();

            System.out.print("Gênero (male/female/other): ");
            String gender = scanner.nextLine().trim().toLowerCase();

            // Validações básicas
            if (firstName.isEmpty() || lastName.isEmpty()) {
                System.out.println("Erro: Nome e sobrenome são obrigatórios!");
                return;
            }

            // Construir o JSON
            String requestBody = String.format(
                    "{\"firstName\":\"%s\",\"lastName\":\"%s\",\"address\":\"%s\",\"gender\":\"%s\"}",
                    escapeJson(firstName), escapeJson(lastName), escapeJson(address), gender
            );

            System.out.println("\nEnviando dados para a API...");

            // Configurar a requisição
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Enviar e processar resposta
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                JsonNode personNode = objectMapper.readTree(response.body());
                String prettyPerson = objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(personNode);
                System.out.println("\n✅ Pessoa criada com sucesso:");
                System.out.println(prettyPerson);
            } else {
                System.out.println("\n❌ Erro ao criar pessoa:");
                System.out.println("Status Code: " + response.statusCode());
                System.out.println("Resposta: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("\n⚠️ Erro durante a criação:");
            System.out.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private String escapeJson(String input) {
        return input.replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private void atualizarPessoa() throws Exception {
        System.out.print("Digite o ID da pessoa a ser atualizada: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Digite os novos dados:");
        System.out.print("Nome: ");
        String firstName = scanner.nextLine();
        System.out.print("Sobrenome: ");
        String lastName = scanner.nextLine();
        System.out.print("Endereço: ");
        String address = scanner.nextLine();
        System.out.print("Gênero (male/female/other): ");
        String gender = scanner.nextLine();

        String requestBody = String.format(
                "{\"id\":%d,\"firstName\":\"%s\",\"lastName\":\"%s\",\"address\":\"%s\",\"gender\":\"%s\"}",
                id, firstName, lastName, address, gender
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Pessoa atualizada:\n" + response.body());
    }

    private void deletarPessoa() throws Exception {
        System.out.print("Digite o ID da pessoa a ser deletada: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 204) {
            System.out.println("Pessoa deletada com sucesso!");
        } else {
            System.out.println("Erro ao deletar pessoa: " + response.body());
        }
    }
}