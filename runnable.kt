class MioRunnable : Runnable {
    override fun run() {
        for (i in 1..5) {
            println("Thread ${Thread.currentThread().name}: $i")
            Thread.sleep(500) // Simula un'attività
        }
    }
}

fun main() {
    val thread1 = Thread(MioRunnable())
    val thread2 = Thread(MioRunnable())

    thread1.start()
    thread2.start()
}
