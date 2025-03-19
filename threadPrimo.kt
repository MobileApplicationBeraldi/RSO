class MioThread : Thread() {
    override fun run() {
        for (i in 1..5) {
            println("Thread ${Thread.currentThread().name}: $i")
            Thread.sleep(500) // Simula un'attività
        }
    }
}

fun main() {
    val thread1 = MioThread()
    val thread2 = MioThread()

    thread1.start()  // Avvia il primo thread
    thr
