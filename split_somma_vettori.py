import os

def somma_parziale(vettore, pipe_write):
    os.close(pipe_write[0])  # Chiude il lato di lettura della pipe
    conn = os.fdopen(pipe_write[1], 'w')  # Apre il lato di scrittura
    print(sum(vettore), file=conn, flush=True)  # Scrive e forza l'invio
    conn.close()  # Chiude il flusso
    os._exit(0)

if __name__ == "__main__":
    vettore = list(range(1, 11))  # Esempio da 1 a 10
    metà = len(vettore) // 2

    # Creazione delle pipe
    padre_figlio1 = os.pipe()
    padre_figlio2 = os.pipe()

    pid1 = os.fork()
    if pid1 == 0:  # Primo figlio
        somma_parziale(vettore[:metà], padre_figlio1)

    pid2 = os.fork()
    if pid2 == 0:  # Secondo figlio
        somma_parziale(vettore[metà:], padre_figlio2)

    # Processo padre
    os.close(padre_figlio1[1])  # Chiude il lato di scrittura
    os.close(padre_figlio2[1])

    conn1 = os.fdopen(padre_figlio1[0], 'r')
    conn2 = os.fdopen(padre_figlio2[0], 'r')

    somma1 = int(conn1.read().strip())  # Rimuove eventuali spazi vuoti
    somma2 = int(conn2.read().strip())

    conn1.close()
    conn2.close()

    os.waitpid(pid1, 0)  # Attende i figli
    os.waitpid(pid2, 0)

    print(f"Somma totale: {somma1 + somma2}")

