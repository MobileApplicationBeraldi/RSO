import os
import time

pid = os.fork()

if pid == 0:  # Figlio
    print("Figlio: sto eseguendo...")
    time.sleep(2)
    print("Figlio: terminato")
else:  # Padre
    print("Padre: in attesa del figlio...")
    os.wait()
    print("Padre: il figlio è terminato, posso uscire.")
