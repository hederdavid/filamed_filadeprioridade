import java.time.LocalDate
import java.time.LocalDateTime

data class Paciente(var nomeCompleto: String, var cpf: String, var sexo: Char, var dataNascimento: LocalDate,
    var relatoQueixasSintomas: String, var prioridade: Int, var dataHoraEnfileiramento: LocalDateTime) {

    var senha: String = ""
    override fun toString(): String {
        return "Paciente(nomeCompleto='$nomeCompleto', cpf='$cpf', sexo=$sexo, dataNascimento=$dataNascimento," +
                " relatoQueixasSintomas='$relatoQueixasSintomas', prioridade=$prioridade," +
                " dataHoraEnfileiramento=$dataHoraEnfileiramento, senha=$senha)"
    }
}

