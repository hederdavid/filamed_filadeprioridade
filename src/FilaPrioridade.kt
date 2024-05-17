import java.time.LocalDateTime

class FilaPrioridade(private val tamanho: Int = 10): Enfileiravel {

    private var pacientes: Array<Paciente?> = arrayOfNulls(tamanho)
    private var ponteiroFim = -1
    private var quantidade = 0
    private var qtdPrioridadeEmergencia = 0
    private var qtdPrioridadeMuitaUrgencia = 0
    private var qtdPrioridadeUrgencia = 0
    private var qtdPrioridadePoucaUrgencia = 0
    private var qtdPrioridadeNaoUrgente = 0

    override fun enfileirar(paciente: Paciente) {
        if (!estaCheia()) {
            if (!isCpfCadastrado(paciente.cpf)) {
                ponteiroFim++
                paciente.senha = gerarSenhaDoPaciente(paciente.prioridade)
                pacientes[ponteiroFim] = paciente
                paciente.dataHoraEnfileiramento = LocalDateTime.now()
                ajustarAcima(ponteiroFim)
                quantidade++
            } else {
                println("Paciente já cadastrado!")
            }
        } else {
            println("Fila de Prioridades Cheia!")
        }
    }

    override fun desenfileirar(): Paciente? {
        var dadoRaiz: Paciente? = null
        if (!estaVazia()) {
            dadoRaiz = pacientes[0]
            pacientes[0] = pacientes[ponteiroFim]
            ponteiroFim--
            ajustarAbaixo(0)
            quantidade--
        } else {
            println("Fila de Prioridades Vazia!")
        }
        return dadoRaiz
    }

    fun desenfileirar(prioridade: Int): Paciente? {
        var pacienteDesenfileirado: Paciente? = null
        var indiceRemocao: Int = 0
        if (!estaVazia()) {
            pacienteDesenfileirado = chamarPrimeiroPrioridade(prioridade)
            for (i in 0 ..< quantidade) {
                if (pacientes[i]!! == pacienteDesenfileirado) {
                    indiceRemocao = i
                    break
                }
            }
            pacientes[indiceRemocao] = pacientes[ponteiroFim]
            ponteiroFim--
            ajustarAbaixo(0)
            quantidade--
        } else {
            println("Fila de Prioridades Vazia!")
        }
        return pacienteDesenfileirado
    }

    override fun atualizar(paciente: Paciente){
        if (!estaVazia()) {
            pacientes[0] = paciente
            ajustarAbaixo(0)
        } else {
            print("Fila de Prioridades Vazia!")
        }
    }

    override fun espiar(): Paciente? {
        var dadoRaiz: Paciente? = null
        if (!estaVazia())
            dadoRaiz = pacientes[0]
        else
            println("Fila de Prioridades Cheia!")

        return dadoRaiz
    }

    override fun estaCheia(): Boolean {
        return ponteiroFim == pacientes.size - 1
    }

    override fun estaVazia(): Boolean {
        return ponteiroFim == -1
    }

    override fun imprimir(): String {
        var resultado = "\n["
        for (i in 0..ponteiroFim) {
            resultado += "${pacientes[i]}"
            if (i != ponteiroFim)
                resultado += ","
        }
        return "$resultado\n]"
    }

    private fun ajustarAcima(indice: Int) {
        var indiceAtual = indice
        while (indiceAtual != 0) {
            val indicePai = indicePai(indiceAtual)
            if (pacientes[indicePai]!!.prioridade < pacientes[indiceAtual]!!.prioridade) {
                trocar(indiceAtual, indicePai)
                indiceAtual = indicePai
            } else if (pacientes[indicePai]!!.prioridade == pacientes[indiceAtual]!!.prioridade) {
                if (pacientes[indicePai]!!.dataHoraEnfileiramento.isAfter(pacientes[indiceAtual]!!.dataHoraEnfileiramento)) {
                    trocar(indiceAtual, indicePai)
                    indiceAtual = indicePai
                } else {
                    break;
                }
            } else {
                break
            }
        }
    }

    private fun ajustarAbaixo(pai: Int) {
        val filhoEsquerdo = indiceFilhoEsquerda(pai)
        val filhoDireito = indiceFilhoDireita(pai)
        var maior = pai;

        if (filhoEsquerdo <= ponteiroFim) { //está dentro dos valores válidos do array
            if (pacientes[maior]!!.prioridade < pacientes[filhoEsquerdo]!!.prioridade) {
                maior = filhoEsquerdo
            } else if (pacientes[maior]!!.prioridade == pacientes[filhoEsquerdo]!!.prioridade) {
                if (pacientes[maior]!!.dataHoraEnfileiramento.isAfter(pacientes[filhoEsquerdo]!!.dataHoraEnfileiramento)) {
                    maior = filhoEsquerdo
                }
            }
        }

        if (filhoDireito <= ponteiroFim) { //está dentro dos valores válidos do array
            if (pacientes[maior]!!.prioridade < pacientes[filhoDireito]!!.prioridade) {
                maior = filhoDireito
            } else if (pacientes[maior]!!.prioridade == pacientes[filhoDireito]!!.prioridade) {
                if (pacientes[maior]!!.dataHoraEnfileiramento.isAfter(pacientes[filhoDireito]!!.dataHoraEnfileiramento)) {
                    maior = filhoDireito
                }
            }
        }

        if (maior != pai) {
            trocar(pai, maior)
            ajustarAbaixo(maior)
        }
    }

    private fun trocar(i: Int, j: Int) {
        val temp = pacientes[i]
        pacientes[i] = pacientes[j]
        pacientes[j] = temp
    }

    private fun indicePai(indiceFilho: Int): Int {
        return (indiceFilho-1)/2
    }

    private fun indiceFilhoEsquerda(indicePai: Int): Int {
        return 2 * indicePai + 1
    }

    private fun indiceFilhoDireita(indicePai: Int): Int	{
        return (2 * indicePai + 1) + 1
    }

    private fun isCpfCadastrado(cpf: String): Boolean {
        val cpfFormatado = cpf.replace("[.\\-\\s]".toRegex(), "")
        var resultado = false

        for (i in 0..<quantidade) {
            val paciente = pacientes[i]
            val cpfPacienteJaCadastrado = paciente?.cpf?.replace("[.\\-\\s]".toRegex(), "")
            if (cpfPacienteJaCadastrado == cpfFormatado) {
                resultado = true
                break  // Interrompe o loop pois o CPF já foi encontrado
            }
        }

        return resultado
    }

    private fun chamarPrimeiroPrioridade(prioridade: Int): Paciente? {
        var pacienteDesenfileirado: Paciente? = null
        var menorDataHora: LocalDateTime? = null

        for (i in 0..<quantidade) {
            val paciente = pacientes[i]
            if (paciente?.prioridade == prioridade) {
                if (pacienteDesenfileirado == null || paciente.dataHoraEnfileiramento.isBefore(menorDataHora)) {
                    pacienteDesenfileirado = paciente
                    menorDataHora = paciente.dataHoraEnfileiramento
                }
            }
        }

        return pacienteDesenfileirado
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

    override fun toString(): String {
        return "FilaPrioridade(tamanho=$tamanho, pacientes=${pacientes.contentToString()}, ponteiroFim=$ponteiroFim)"
    }

    fun size(): Int {
        return quantidade
    }
}