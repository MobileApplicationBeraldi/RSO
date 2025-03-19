import java.util.concurrent.*

fun main() {
    // Creiamo un thread pool con 2 thread
    val executorService = Executors.newFixedThreadPool(2)

    // Definiamo due task Callable
    val task1 = Callable<Int> {
        println("Task 1 in esecuzione su ${Thread.currentThread().name}")
        Thread.sleep(1000) // Simula un lavoro
        42 // Risultato restituito dal task
    }

    val task2 = Callable<Int> {
        println("Task 2 in esecuzione su ${Thread.currentThread().name}")
        Thread.sleep(1500) // Simula un lavoro
        84 // Risultato restituito dal task
    }

    // Submit dei task e otteniamo i Future
    val future1: Future<Int> = executorService.submit(task1)
    val future2: Future<Int> = executorService.submit(task2)

    // Otteniamo i risultati dai Future (blocca finché non sono pronti)
    try {
        println("Risultato Task 1: ${future1.get()}")  // Questo è bloccante
        println("Risultato Task 2: ${future2.get()}")  // Questo è bloccante
    } catch (e: InterruptedException) {
        println("Il task è stato interrotto: ${e.message}")
    } catch (e: ExecutionException) {
        println("Si è verificato un errore durante l'esecuzione del task: ${e.message}")
    }

    // Fermiamo l'ExecutorService
    executorService.shutdown()
}
