class Gerenciador {
    private var fila: FilaPrioridade = FilaPrioridade()
    private var pacientesEnfileirados: List<Paciente> = ArrayList()
    private var qtdPrioridadeEmergencia = 0
    private var qtdPrioridadeMuitaUrgencia = 0
    private var qtdPrioridadeUrgencia = 0
    private var qtdPrioridadePoucaUrgencia = 0
    private var qtdPrioridadeNaoUrgente = 0

    fun ingressarNaFila(paciente: Paciente): String {
        fila.enfileirar(paciente)
        pacientesEnfileirados.addFirst(paciente)
        return gerarSenhaDoPaciente(paciente.prioridade)
    }

    private fun gerarSenhaDoPaciente(prioridade: Int): String {
        return when (prioridade) {
            5 -> {
                qtdPrioridadeEmergencia++
                "R$qtdPrioridadeEmergencia"
            }
            4 -> {
                qtdPrioridadeMuitaUrgencia++
                "O$qtdPrioridadeMuitaUrgencia"
            }
            3 -> {
                qtdPrioridadeUrgencia++
                "Y$qtdPrioridadeUrgencia"
            }
            2 -> {
                qtdPrioridadePoucaUrgencia++
                "G$qtdPrioridadePoucaUrgencia"
            }
            1 -> {
                qtdPrioridadeNaoUrgente++
                "B$qtdPrioridadeNaoUrgente"
            }
            else -> "SENHA NÃO DEFINIDA"
        }

    }
}