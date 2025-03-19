fun main() {
    // Creiamo un pool di thread con 2 thread
    val executorService = Executors.newFixedThreadPool(2)

    // Task che stampa il nome del thread corrente
    val task1 = Runnable {
        println("Task 1 in esecuzione su ${Thread.currentThread().name}")
    }

    val task2 = Runnable {
        println("Task 2 in esecuzione su ${Thread.currentThread().name}")
    }

    // Submit dei task al pool
    executorService.submit(task1)
    executorService.submit(task2)

    // Fermiamo l'ExecutorService dopo che tutti i task sono stati completati
    executorService.shutdown()
}
