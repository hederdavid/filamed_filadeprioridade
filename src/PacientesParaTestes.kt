import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random

class PacientesParaTestes {

    fun enfileirarPacientesTestes(fila: FilaPrioridade) {
        val nomes = listOf("Ana Silva", "Bruno Santos", "Carlos Souza", "Daniela Lima", "Eduardo Almeida",
            "Fernanda Costa", "Gustavo Pereira", "Helena Rocha", "Igor Oliveira", "Julia Ribeiro",
            "Lucas Mendes", "Mariana Gomes", "Nicolas Ferreira", "Olivia Martins", "Paulo Silva",
            "Quirino Santos", "Renata Lima", "Sandro Almeida", "Tereza Costa", "Ursula Pereira",
            "Vinicius Rocha", "Wesley Ribeiro", "Xavier Mendes", "Yara Gomes", "Zeca Ferreira",
            "Amanda Martins", "Beto Silva", "Camila Santos", "Diego Lima", "Eva Almeida",
            "Fabio Costa", "Gabriela Pereira", "Henrique Rocha", "Isabela Oliveira", "João Ribeiro",
            "Karen Mendes", "Luan Gomes", "Monica Ferreira", "Natan Martins", "Olga Silva",
            "Pedro Santos", "Quintino Lima", "Roberta Almeida", "Sofia Costa", "Thiago Pereira",
            "Ubiratan Rocha", "Veronica Ribeiro", "Wagner Mendes", "Xuxa Gomes", "Yasmin Ferreira")

        val sintomas = listOf("Dor de cabeça", "Febre alta", "Dor abdominal", "Tontura", "Dor no peito",
            "Fadiga", "Náusea", "Tosse", "Dificuldade para respirar", "Dor nas costas")

        val random = Random.Default
        val startDate = LocalDate.of(1950, 1, 1)
        val endDate = LocalDate.of(2024, 5, 19)

        repeat(50) { i ->
            val nome = nomes[i % nomes.size]
            val cpf = "${random.nextInt(100, 999)}.${random.nextInt(100, 999)}.${random.nextInt(100, 999)}-${random.nextInt(10, 99)}"
            val sexo = if (random.nextBoolean()) 'M' else 'F'
            val dataNascimento = LocalDate.ofEpochDay(startDate.toEpochDay() + random.nextLong(0, endDate.toEpochDay() - startDate.toEpochDay()))
            val relatoQueixasSintomas = sintomas[random.nextInt(sintomas.size)]
            val prioridade = random.nextInt(1, 6)
            val paciente = Paciente(nome, cpf, sexo, dataNascimento, relatoQueixasSintomas, prioridade, LocalDateTime.now())

            fila.enfileirar(paciente)
            println("${i + 1} pacientes enfileirados...")

            // Simulando o tempo de espera entre as enfileirações
            Thread.sleep(100)
        }
    }
}