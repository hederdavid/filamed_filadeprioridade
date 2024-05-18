import java.time.LocalDate
import java.time.LocalDateTime

data class Paciente(var nomeCompleto: String, var cpf: String, var sexo: Char, var dataNascimento: LocalDate,
    var relatoQueixasSintomas: String, var prioridade: Int, var dataHoraEnfileiramento: LocalDateTime) {

    var senha: String = ""
    override fun toString(): String {
        return "Nome Completo: $nomeCompleto, CPF:'$cpf', sexo: $sexo, Data de Nascimento: $dataNascimento," +
                " Relato de queixas e/ou sintomas: $relatoQueixasSintomas, Prioridade=$prioridade," +
                " Horario de enfileiramento: $dataHoraEnfileiramento, Senha: $senha)"
    }
}

