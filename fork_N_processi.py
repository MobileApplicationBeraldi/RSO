import os
import time

# Funzione che esegue una certa logica nel processo figlio
def child_process(i):
    print(f"Processo {i} in esecuzione, PID: {os.getpid()}")
    # Simula un lavoro, come un'attesa

    time.sleep(2)
    print(f"Processo {i} terminato.")

# Numero di processi da creare
N = 5

# Lista per tenere traccia dei PID dei processi figli
pids = []

# Crea N processi
for i in range(N):
    pid = os.fork()
    if pid == 0:
        # Se siamo nel processo figlio, esegui la funzione
        child_process(i)
        os._exit(0)  # Termina il processo figlio
    else:
        # Se siamo nel processo padre, aggiungiamo il PID alla lista
        pids.append(pid)

# Il processo padre attende che tutti i figli terminino
for pid in pids:
    os.waitpid(pid, 0)

print("Tutti i processi figli sono terminati.")
