/*package br.com.projeto.redesocial.backend;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao ChatApp!");
        System.out.println("---------------------");
        boolean sair = false;
        while (!sair) {
            // Exibir o menu de opções
            System.out.println("\nMenu:");
            System.out.println("1. Fazer cadastro");
            System.out.println("2. Fazer login");
            System.out.println("0. Sair");

            System.out.print("Digite a opção desejada: ");
            int opcao1 = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao1) {
                case 1:
                    System.out.print("Digite o seu nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite o seu email: ");
                    String emailCadastro = scanner.nextLine();
                    System.out.print("Digite a sua senha: ");
                    String senhaCadastro = scanner.nextLine();

                    Usuario novoUsuario = new Usuario();
                    boolean sucessoCadastro = novoUsuario.fazerRegistro(emailCadastro, senhaCadastro, nome);

                    if (sucessoCadastro) {
                        System.out.println("Cadastro realizado com sucesso!");
                    } else {
                        System.out.println("Não foi possível realizar o cadastro.");
                    }
                    break;
                case 2:
                    // Solicitar email e senha ao usuário
                    System.out.print("Digite o seu email: ");
                    String email = scanner.nextLine();
                    System.out.print("Digite a sua senha: ");
                    String senha = scanner.nextLine();

                    // Fazer login
                    Usuario usuario = new Usuario(email, senha);
                    //usuario.fazerLogin(email, senha);

                    boolean loginBemSucedido = usuario.fazerLogin(email, senha);
                    if (loginBemSucedido) {
                        System.out.println("Login realizado com sucesso!");
                        System.out.println("Seu código de amizade é: " + usuario.getCOD_USUARIO());

                        int opcao;
                        do {
                            // Exibir o menu de opções
                            System.out.println("\nMenu:");
                            System.out.println("1. Adicionar amigo");
                            System.out.println("2. Listar amigos");
                            System.out.println("3. Excluir amigo");
                            System.out.println("4. Enviar mensagem");
                            System.out.println("5. Mostrar mensagem");
                            System.out.println("0. Sair");
                            System.out.print("Digite a opção desejada: ");
                            opcao = scanner.nextInt();
                            scanner.nextLine(); // Limpar o buffer do scanner

                            switch (opcao) {
                                case 1:
                                    System.out.println("Digite o Código do seu Amigo: ");
                                    if (scanner.hasNextInt()) {
                                        int codigoAmigo = scanner.nextInt();
                                        // Limpa o buffer do scanner (incluindo a quebra de linha do enter)
                                        scanner.nextLine();
                                        usuario.adicionarAmigo(codigoAmigo);
                                    } else {
                                        System.out.println("Erro: Você deve digitar um número inteiro.");
                                        // Descarta a entrada incorreta e limpa o buffer do scanner
                                        scanner.nextLine();
                                    }
                                    break;

                               case 2:
                                    List<Usuario> amigos = usuario.listarAmigos();
                                    System.out.println("=== AMIGOS ===");
                                    for (Usuario amigo : amigos) {
                                        System.out.println(amigo.getNOM_USUARIO());
                                    }
                                    if (amigos.isEmpty()) {
                                        System.out.println("Você não tem amigos cadastrados.");
                                    }
                                    break;

                                case 3:
                                    List<Usuario> amigosexcluir = usuario.listarAmigos();
                                    System.out.println("Seus amigos:");
                                    for (int i = 0; i < amigosexcluir.size(); i++) {
                                        System.out.println((i + 1) + ". " + amigosexcluir.get(i).getNOM_USUARIO() + " (Código: " + amigosexcluir.get(i).getCOD_USUARIO() + ")");
                                    }

                                    // Pedir ao usuário para escolher um amigo para excluir
                                    System.out.println("Digite o número do amigo que deseja excluir:");
                                    int indiceAmigo = scanner.nextInt();

                                    if (indiceAmigo > 0 && indiceAmigo <= amigosexcluir.size()) {
                                        Usuario amigoSelecionado = amigosexcluir.get(indiceAmigo - 1);
                                        boolean resultado = usuario.excluirAmigo(amigoSelecionado.getCOD_USUARIO());
                                        if (resultado) {
                                            System.out.println("Amigo excluído com sucesso!");
                                        } else {
                                            System.out.println("Não foi possível excluir o amigo.");
                                        }
                                    } else {
                                        System.out.println("Número inválido.");
                                    }

                                    break;

                                case 4:
                                    //LISTA OS USUARIO AMIGOS DA PESSOA E ELA SELECIONA COM QUEM QUER MANDAR MENSAGEM
                                    List<Usuario> amigos2 = usuario.listarAmigos();
                                    if (amigos2.isEmpty()) {
                                        System.out.println("Você não tem amigos para enviar mensagens.");
                                    } else {
                                        System.out.println("Selecione um amigo para enviar uma mensagem:");
                                        for (int i = 0; i < amigos2.size(); i++) {
                                            System.out.println((i + 1) + ". " + amigos2.get(i).getNOM_USUARIO());
                                        }
                                        int escolha = scanner.nextInt(); // Asume que você tem um objeto Scanner chamado scanner
                                        scanner.nextLine(); // Consume the rest of the line

                                        if (escolha < 1 || escolha > amigos2.size()) {
                                            System.out.println("Escolha inválida.");
                                        } else {
                                            Usuario amigoEscolhido = amigos2.get(escolha - 1);

                                            System.out.println("Digite sua mensagem para " + amigoEscolhido.getNOM_USUARIO() + ":");
                                            String conteudoMensagem = scanner.nextLine();

                                            LocalDateTime agora = LocalDateTime.now();
                                            Mensagem novaMensagem = new Mensagem(conteudoMensagem, usuario, amigoEscolhido);
                                            novaMensagem.setDTA_ENVIO(agora); // Definindo a data e hora atual da mensagem
                                            novaMensagem.enviarMensagem();
                                        }
                                    }
                                    break;

                                case 5:
                                    List<Usuario> amigos3 = usuario.listarAmigos();
                                    if (amigos3.isEmpty()) {
                                        System.out.println("Você não tem amigos para ver as mensagens.");
                                    } else {
                                        System.out.println("Selecione um amigo para ver as mensagens:");
                                        for (int i = 0; i < amigos3.size(); i++) {
                                            System.out.println((i + 1) + ". " + amigos3.get(i).getNOM_USUARIO());
                                        }
                                        int escolhaAmigo = scanner.nextInt(); // Assume que você tem um objeto Scanner chamado scanner
                                        scanner.nextLine(); // Consume the rest of the line

                                        if (escolhaAmigo < 1 || escolhaAmigo > amigos3.size()) {
                                            System.out.println("Escolha inválida.");
                                        } else {
                                            Usuario amigoSelecionado = amigos3.get(escolhaAmigo - 1);

                                            List<Mensagem> mensagens = Mensagem.listarMensagens(usuario, amigoSelecionado);
                                            if (mensagens.isEmpty()) {
                                                System.out.println("Não há mensagens entre você e " + amigoSelecionado.getNOM_USUARIO() + ".");
                                            } else {
                                                for (Mensagem mensagem : mensagens) {
                                                    System.out.println("De: " + mensagem.getCOD_REMETENTE().getNOM_USUARIO());
                                                    System.out.println("Para: " + mensagem.getCOD_DESTINATARIO().getNOM_USUARIO());
                                                    System.out.println("Mensagem: " + mensagem.getDES_MENSAGEM());
                                                    System.out.println("Data/Hora: " + mensagem.getDTA_ENVIO());
                                                    System.out.println("-----");
                                                }
                                            }
                                        }
                                    }
                                    break;

                                case 0:
                                    System.out.println("Saindo...");
                                    break;
                                default:
                                    System.out.println("Opção inválida!");
                                    break;
                            }
                        } while (opcao != 0);
                    } else {
                        System.out.println("Email ou senha incorretos. Login falhou.");
                    }
                    break;
                case 0:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }


        scanner.close();
    }


}*/