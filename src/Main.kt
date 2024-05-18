import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val fila = FilaPrioridade(10)
    var opcaoEscolhida = 0

    while (true) {
        println("\n╔═══════════════════════════════════════════════════════════════╗")
        println("║                                                               ║")
        println("║         FILAMED - GERENCIADOR DE FILAS DE PRIORIDADE          ║")
        println("║                                                               ║")
        println("╠═══════════════════════════════════════════════════════════════╣")
        println("║                                                               ║")
        println("║                       Selecione uma opção:                    ║")
        println("║                                                               ║")
        println("║  1.  Adicionar paciente                                       ║")
        println("║  2.  Chamar próximo paciente                                  ║")
        println("║  3.  Verificar iminência de atendimento                       ║")
        println("║  4.  Consultar próximo paciente de uma fila                   ║")
        println("║  5.  Consultar estatísticas                                   ║")
        println("║  6.  Sair                                                     ║")
        println("║                                                               ║")
        println("╚═══════════════════════════════════════════════════════════════╝")

        while (true) {
            try {
                print("🅾️Opção: ")
                opcaoEscolhida = scanner.nextInt()
                break
            } catch (e: InputMismatchException) {
                println("❌ Insira uma opção válida!")
                scanner.nextLine()
            }
        }
        scanner.nextLine()

        when (opcaoEscolhida) {
            1 -> {

                println("\n╔════════════════════════════════════════════╗")
                println("║             Cadastrar Paciente             ║")
                println("╚════════════════════════════════════════════╝")

                print("👤  Digite o nome completo do paciente: ")
                val nomeCompleto = scanner.nextLine()

                print("🆔  Digite o CPF do paciente: ")
                val cpf = scanner.nextLine().uppercase(Locale.getDefault())

                print("⚧️  Digite o sexo do paciente (Masculino/Feminino/Outros): ")
                val sexo = scanner.nextLine().firstOrNull()?.uppercaseChar() ?: ' '
                var dataNascimento = LocalDate.now()

                while (true) {
                    print("📅  Digite a data de nascimento do paciente (dd/MM/yyyy): ")
                    val dataNascimentoStr = scanner.nextLine()
                    try {
                        dataNascimento =
                            LocalDate.parse(dataNascimentoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        break
                    } catch (e: DateTimeParseException) {
                        println("❌ Digite a data no formato correto! Ex: 05/11/2003")
                    }
                }

                print("🤕  Relato de queixas e sintomas: ")
                val relatoQueixaSintomas = scanner.nextLine()

                var prioridade = 0
                while (true) {
                    try {
                        print("🚨  Digite a prioridade do paciente (1 - NÃO URGENTE, 2 - POUCO URGENTE, 3 - URGENTE, " +
                                "4 - MUITO URGENTE, 5 - EMERGENTE): ")
                        prioridade = scanner.nextInt()
                        break
                    } catch (e: InputMismatchException) {
                        println("❌ Prioridade inválida! Tente novamente.")
                        scanner.nextLine()
                    }
                }

                fila.enfileirar(Paciente(nomeCompleto, cpf, sexo, dataNascimento, relatoQueixaSintomas, prioridade,
                    LocalDateTime.now()))
                println(fila.espiar())
                println("\n ✅ Paciente $nomeCompleto cadastrado com sucesso ")
            }

            2 -> {
                println("\n╔════════════════════════════════════════════╗")
                println("║           Chamar Próximo Paciente          ║")
                println("╚════════════════════════════════════════════╝")
                print("⚠️ Digite 'AUTO' para selecionar automaticamente ou \n Digite a classificação de risco (R - Emergência, O - Muito Urgente, Y - Urgente, G - Pouco Urgente, B - Não Urgente): ")
                val prioridadeEspecifica = scanner.next().uppercase(Locale.getDefault()).firstOrNull()

                if (prioridadeEspecifica != null) {
                    if (prioridadeEspecifica.equals('A', true)) {
                        when(prioridadeEspecifica) {
                            'R' -> println(fila.desenfileirar(5))
                            'O' -> println(fila.desenfileirar(4))
                            'Y' -> println(fila.desenfileirar(3))
                            'G' -> println(fila.desenfileirar(2))
                            'B' -> println(fila.desenfileirar(1))
                            else -> println("Prioridade inválida!")
                        }
                    } else {
                        println(fila.desenfileirar())
                    }
                } else {
                    println(fila.desenfileirar())
                }
            }

            3 -> {
                println("\n╔════════════════════════════════════════════╗")
                println("║   ⏳ Verificar Iminência de Atendimento     ║")
                println("╚════════════════════════════════════════════╝")
                print("🆔  Digite o CPF do paciente: ")
                val cpf = scanner.nextLine()
                if (fila.espiar()?.cpf == cpf) {
                    println("O paciente ${fila.espiar()?.nomeCompleto} de cpf ${fila.espiar()?.cpf }  é o próximo a ser " +
                            "atendido.")
                } else {
                    println("O paciente do cpf $cpf, não é o próximo a ser atendido.")
                }

            }

            4 -> {
                println("\n╔════════════════════════════════════════════╗")
                println("║  📋 Dados do próximo paciente da Fila      ║")
                println("╚════════════════════════════════════════════╝")
                println(fila.espiar())

            }

            5 -> {
                println("\n╔════════════════════════════════════════════╗")
                println("║        📊 Consultar Estatísticas           ║")
                println("╚════════════════════════════════════════════╝")

            }

            6 -> {
                println("\n👋 Saindo do sistema... Até logo!")
                break
            }

            else -> {
                println("\n❌ Opção inválida, tente novamente.")
            }
        }
    }


    /*val fila = FilaPrioridade(10)
    fila.enfileirar(Paciente("Ana Silva", "123.456.789-00", 'F', LocalDate.of(1990, 5, 10),
        "Dor de cabeça", 2, LocalDateTime.now()))
    sleep(1000)
    fila.enfileirar(Paciente("Bruno Santos", "987.654.321-11", 'M', LocalDate.of(1985, 8, 22),
        "Febre alta", 1, LocalDateTime.now()))
    sleep(200)
    fila.enfileirar(Paciente("Carlos Souza", "456.789.123-22", 'M', LocalDate.of(1978, 3, 15),
        "Dor abdominal", 3, LocalDateTime.now()))
    sleep(200)
    fila.enfileirar(Paciente("Daniela Lima", "321.654.987-33", 'F', LocalDate.of(1995, 12, 30),
        "Tontura", 2, LocalDateTime.now()))
    sleep(200)
    fila.enfileirar(Paciente("Eduardo Almeida", "654.321.987-44", 'M', LocalDate.of(1980, 7, 17),
        "Dor no peito", 1, LocalDateTime.now()))
    sleep(200)
    fila.enfileirar(Paciente("Fernanda Costa", "789.123.456-55", 'F', LocalDate.of(2000, 1, 5),
        "Fadiga", 3, LocalDateTime.now()))
    sleep(200)
    fila.enfileirar(Paciente("Gustavo Pereira", "147.258.369-66", 'M', LocalDate.of(1992, 6, 25),
        "Náusea", 2, LocalDateTime.now()))
    sleep(200)
    fila.enfileirar(Paciente("Helena Rocha", "258.369.147-77", 'F', LocalDate.of(1988, 9, 18),
        "Tosse", 3, LocalDateTime.now()))
    sleep(200)
    fila.enfileirar(Paciente("Igor Oliveira", "369.147.258-88", 'M', LocalDate.of(1993, 4, 21),
        "Dificuldade para respirar", 1, LocalDateTime.now()))
    sleep(200)
    fila.enfileirar(Paciente("Julia Ribeiro", "159.753.486-99", 'F', LocalDate.of(1975, 11, 11),
        "Dor nas costas", 2, LocalDateTime.now()))
    println(fila.imprimir())
    println(fila.desenfileirar())
    println(fila.desenfileirar(1))
    println(fila.desenfileirar(2))
    println(fila.desenfileirar())
    println(fila.desenfileirar())
    println(fila.desenfileirar())
    println(fila.desenfileirar())
    println(fila.desenfileirar())
    println(fila.desenfileirar())
    println(fila.desenfileirar())*/


}