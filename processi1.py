import os

pid = os.fork()

if pid == 0:
    print(f"Sono il figlio, PID: {os.getpid()}")
else:
    print(f"Sono il padre, PID: {os.getpid()}, mio figlio ha PID: {pid}")
