import java.time.LocalDate
import java.time.LocalDateTime

data class PacienteEnfileirado(var paciente: Paciente, var prioridade: Char, var dataHoraEnfileiramento: LocalDateTime,
    var dataHoraDesenfileiramento: LocalDateTime, var senha: String) {
}