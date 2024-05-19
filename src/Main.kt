import java.lang.Thread.sleep
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val fila = FilaPrioridade(50)
    var opcaoEscolhida = 0

    val enfileirarPacientes = PacientesParaTestes()
    enfileirarPacientes.enfileirarPacientesTestes(fila)

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

                println("\n ✅ Paciente $nomeCompleto cadastrado com sucesso ")
            }

            2 -> {
                println("\n╔════════════════════════════════════════════╗")
                println("║           Chamar Próximo Paciente          ║")
                println("╚════════════════════════════════════════════╝")
                print("⚠️ Digite '0' para selecionar automaticamente ou \n Digite a classificação de risco (5 - Emergência, 4 - Muito Urgente, 3 - Urgente, 2 - Pouco Urgente, 1 - Não Urgente): ")
                val prioridadeEspecifica = scanner.nextInt()

                when(prioridadeEspecifica) {
                    5 -> println(fila.desenfileirar(5))
                    4 -> println(fila.desenfileirar(4))
                    3 -> println(fila.desenfileirar(3))
                    2 -> println(fila.desenfileirar(2))
                    1 -> println(fila.desenfileirar(1))
                    0 -> println(fila.desenfileirar())
                    else -> println("Prioridade inválida!")
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
                println("║     Dados do próximo paciente da Fila      ║")
                println("╚════════════════════════════════════════════╝")
                println(fila.espiar())

            }

            5 -> {
                var opcaoEscolhidaEstatisticas = 0
                println("\n╔════════════════════════════════════════════════════════════════════════════╗")
                println("║                           Consultar Estatísticas                           ║")
                println("╠════════════════════════════════════════════════════════════════════════════╣")
                println("║     1.  Quantidade atual de pacientes na fila por prioridade               ║")
                println("║     2.  Quantidade atual de pacientes na fila por faixa etária             ║")
                println("║     3.  Tempo médio de permanência em fila de atendimento                  ║")
                println("║     4.  Percentual de pacientes, entre aqueles atendidos e em cada fila de ║")
                println("║       atendimento, em tempo inferior ao tempo de espera recomendado        ║")
                println("║       para sua classificação de risco                                      ║")
                println("╚════════════════════════════════════════════════════════════════════════════╝")

                while (true) {
                    try {
                        print("🅾️Opção: ")
                        opcaoEscolhidaEstatisticas = scanner.nextInt()
                        break
                    } catch (e: InputMismatchException) {
                        println("❌ Insira uma opção válida!")
                        scanner.nextLine()
                    }
                }
                scanner.nextLine()
                when (opcaoEscolhidaEstatisticas) {
                    1 -> fila.consultarQuantidadeAtualPacientesPorPrioridade()

                    2 -> fila.consultarQuantidadeAtualPacientesPorFaixaEtaria()
                }
            }

            6 -> {
                println("\n👋 Saindo do sistema...")
                sleep(2000)
                println("Sistema encerrado.")
                break
            }

            else -> {
                println("\n❌ Opção inválida, tente novamente.")
            }
        }
    }


    /*val fila = FilaPrioridade(10)

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