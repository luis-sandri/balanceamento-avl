import java.util.Scanner;

public class ArvoreAVL {

    static class No {
        int valor;
        No esquerda;
        No direita;

        No(int valor) {
            this.valor = valor;
        }
    }

    private No raiz;

    public void inserirManualmente(int valor, Scanner scanner) {

        if (raiz == null) {
            raiz = new No(valor);

            System.out.println(
                    "A árvore estava vazia. " +
                            valor + " foi inserido como raiz."
            );

            return;
        }

        No atual = raiz;

        while (true) {

            System.out.println("\nVocê está no nó: " + atual.valor);

            System.out.println(
                    "Filho esquerdo: "
                            + (atual.esquerda == null
                            ? "VAZIO"
                            : atual.esquerda.valor)
            );

            System.out.println(
                    "Filho direito: "
                            + (atual.direita == null
                            ? "VAZIO"
                            : atual.direita.valor)
            );

            System.out.println("\nO que deseja fazer?");
            System.out.println("1 - Ir para a esquerda");
            System.out.println("2 - Ir para a direita");
            System.out.println("3 - Cancelar inserção");

            System.out.print("Escolha: ");

            int opcao = scanner.nextInt();

            // =================================================
            // ESQUERDA
            // =================================================

            if (opcao == 1) {

                // Se estiver vazio, podemos inserir
                if (atual.esquerda == null) {

                    System.out.println(
                            "\nA esquerda do nó "
                                    + atual.valor
                                    + " está vazia."
                    );

                    System.out.print(
                            "Deseja inserir "
                                    + valor
                                    + " aqui? (1 - Sim / 2 - Não): "
                    );

                    int resposta = scanner.nextInt();

                    if (resposta == 1) {

                        atual.esquerda = new No(valor);

                        System.out.println(
                                valor
                                        + " inserido à esquerda de "
                                        + atual.valor
                        );

                        return;
                    }

                } else {

                    // Continua navegando
                    atual = atual.esquerda;
                }
            }

            // =================================================
            // DIREITA
            // =================================================

            else if (opcao == 2) {

                if (atual.direita == null) {

                    System.out.println(
                            "\nA direita do nó "
                                    + atual.valor
                                    + " está vazia."
                    );

                    System.out.print(
                            "Deseja inserir "
                                    + valor
                                    + " aqui? (1 - Sim / 2 - Não): "
                    );

                    int resposta = scanner.nextInt();

                    if (resposta == 1) {

                        atual.direita = new No(valor);

                        System.out.println(
                                valor
                                        + " inserido à direita de "
                                        + atual.valor
                        );

                        return;
                    }

                } else {

                    atual = atual.direita;
                }
            }

            // =================================================
            // CANCELAR
            // =================================================

            else if (opcao == 3) {

                System.out.println("Inserção cancelada.");
                return;
            }

            else {
                System.out.println("Opção inválida.");
            }
        }
    }


    // =========================================================
    // ALTURA
    // =========================================================

    private int altura(No no) {

        if (no == null) {
            return 0;
        }

        return 1 + Math.max(
                altura(no.esquerda),
                altura(no.direita)
        );
    }


    // =========================================================
    // FATOR DE BALANCEAMENTO
    // =========================================================

    private int fatorBalanceamento(No no) {

        if (no == null) {
            return 0;
        }

        return altura(no.esquerda)
                - altura(no.direita);
    }


    // =========================================================
    // VERIFICAR SE ESTÁ EQUILIBRADA
    // =========================================================

    public boolean estaEquilibrada() {
        return estaEquilibrada(raiz);
    }

    private boolean estaEquilibrada(No no) {

        if (no == null) {
            return true;
        }

        int fb = fatorBalanceamento(no);

        if (Math.abs(fb) > 1) {
            return false;
        }

        return estaEquilibrada(no.esquerda)
                && estaEquilibrada(no.direita);
    }


    // =========================================================
    // ROTAÇÃO PARA DIREITA
    // =========================================================

    private No rotacaoDireita(No y) {

        No x = y.esquerda;
        No temporario = x.direita;

        x.direita = y;
        y.esquerda = temporario;

        return x;
    }


    // =========================================================
    // ROTAÇÃO PARA ESQUERDA
    // =========================================================

    private No rotacaoEsquerda(No x) {

        No y = x.direita;
        No temporario = y.esquerda;

        y.esquerda = x;
        x.direita = temporario;

        return y;
    }


    // =========================================================
    // BALANCEAMENTO
    // =========================================================

    public void balancear() {

        if (estaEquilibrada()) {

            System.out.println(
                    "\nA árvore já está equilibrada."
            );

            return;
        }

        System.out.println("\n=================================");
        System.out.println("       ÁRVORE DESBALANCEADA");
        System.out.println("=================================");

        System.out.println("\nANTES:");

        mostrar();

        raiz = balancearRecursivo(raiz);

        System.out.println("\n=================================");
        System.out.println("           DEPOIS");
        System.out.println("=================================");

        mostrar();

        if (estaEquilibrada()) {
            System.out.println("\nA árvore agora está equilibrada.");
        } else {
            System.out.println(
                    "\nAinda existem pontos desbalanceados."
            );
        }
    }


    private No balancearRecursivo(No no) {

        if (no == null) {
            return null;
        }

        // Primeiro corrigimos os filhos
        no.esquerda = balancearRecursivo(no.esquerda);
        no.direita = balancearRecursivo(no.direita);

        int fb = fatorBalanceamento(no);


        // =====================================================
        // PESO PARA ESQUERDA
        // =====================================================

        if (fb > 1) {

            int fbFilho =
                    fatorBalanceamento(no.esquerda);

            // LL
            if (fbFilho >= 0) {

                System.out.println(
                        "\nDesbalanceamento no nó "
                                + no.valor
                );

                System.out.println(
                        "Fator de balanceamento: " + fb
                );

                System.out.println(
                        "Caso identificado: LL"
                );

                System.out.println(
                        "Rotação necessária: DIREITA"
                );

                return rotacaoDireita(no);
            }

            // LR
            else {

                System.out.println(
                        "\nDesbalanceamento no nó "
                                + no.valor
                );

                System.out.println(
                        "Fator de balanceamento: " + fb
                );

                System.out.println(
                        "Caso identificado: LR"
                );

                System.out.println(
                        "Rotação necessária:"
                );

                System.out.println(
                        "1 - Rotação para ESQUERDA no filho"
                );

                System.out.println(
                        "2 - Rotação para DIREITA no nó"
                );

                no.esquerda =
                        rotacaoEsquerda(no.esquerda);

                return rotacaoDireita(no);
            }
        }


        // =====================================================
        // PESO PARA DIREITA
        // =====================================================

        if (fb < -1) {

            int fbFilho =
                    fatorBalanceamento(no.direita);

            // RR
            if (fbFilho <= 0) {

                System.out.println(
                        "\nDesbalanceamento no nó "
                                + no.valor
                );

                System.out.println(
                        "Fator de balanceamento: " + fb
                );

                System.out.println(
                        "Caso identificado: RR"
                );

                System.out.println(
                        "Rotação necessária: ESQUERDA"
                );

                return rotacaoEsquerda(no);
            }

            // RL
            else {

                System.out.println(
                        "\nDesbalanceamento no nó "
                                + no.valor
                );

                System.out.println(
                        "Fator de balanceamento: " + fb
                );

                System.out.println(
                        "Caso identificado: RL"
                );

                System.out.println(
                        "Rotação necessária:"
                );

                System.out.println(
                        "1 - Rotação para DIREITA no filho"
                );

                System.out.println(
                        "2 - Rotação para ESQUERDA no nó"
                );

                no.direita =
                        rotacaoDireita(no.direita);

                return rotacaoEsquerda(no);
            }
        }

        return no;
    }


    // =========================================================
    // MOSTRAR ÁRVORE
    // =========================================================

    public void mostrar() {

        if (raiz == null) {
            System.out.println("Árvore vazia.");
            return;
        }

        mostrarRecursivo(
                raiz,
                "",
                true
        );
    }


    private void mostrarRecursivo(
            No no,
            String prefixo,
            boolean ultimo
    ) {

        if (no == null) {
            return;
        }

        System.out.println(
                prefixo
                        + (ultimo ? "└── " : "├── ")
                        + no.valor
                        + " [FB: "
                        + fatorBalanceamento(no)
                        + "]"
        );

        if (no.esquerda != null
                || no.direita != null) {

            if (no.esquerda != null) {

                mostrarRecursivo(
                        no.esquerda,
                        prefixo
                                + (ultimo
                                ? "    "
                                : "│   "),
                        no.direita == null
                );
            }

            if (no.direita != null) {

                mostrarRecursivo(
                        no.direita,
                        prefixo
                                + (ultimo
                                ? "    "
                                : "│   "),
                        true
                );
            }
        }
    }


    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArvoreAVL arvore = new ArvoreAVL();

        int opcao;

        do {

            System.out.println("\n=================================");
            System.out.println("       CONSTRUTOR DE ÁRVORE");
            System.out.println("=================================");

            System.out.println("1 - Inserir nó");
            System.out.println("2 - Mostrar árvore");
            System.out.println("3 - Verificar balanceamento");
            System.out.println("4 - Corrigir árvore");
            System.out.println("0 - Sair");

            System.out.print("\nEscolha: ");

            opcao = scanner.nextInt();


            switch (opcao) {

                case 1:

                    System.out.print(
                            "\nDigite o valor do novo nó: "
                    );

                    int valor = scanner.nextInt();

                    System.out.println(
                            "\nÁrvore atual:"
                    );

                    arvore.mostrar();

                    arvore.inserirManualmente(
                            valor,
                            scanner
                    );

                    System.out.println(
                            "\nÁrvore após inserção:"
                    );

                    arvore.mostrar();

                    break;


                case 2:

                    System.out.println(
                            "\nÁrvore atual:"
                    );

                    arvore.mostrar();

                    break;


                case 3:

                    System.out.println(
                            "\nÁrvore atual:"
                    );

                    arvore.mostrar();

                    if (arvore.estaEquilibrada()) {

                        System.out.println(
                                "\nA árvore está EQUILIBRADA."
                        );

                    } else {

                        System.out.println(
                                "\nA árvore está DESBALANCEADA."
                        );
                    }

                    break;


                case 4:

                    arvore.balancear();

                    break;


                case 0:

                    System.out.println(
                            "\nPrograma encerrado."
                    );

                    break;


                default:

                    System.out.println(
                            "Opção inválida."
                    );
            }

        } while (opcao != 0);

        scanner.close();
    }
}