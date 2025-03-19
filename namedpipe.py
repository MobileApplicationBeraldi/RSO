import os
import time

def reader():
    fifo = 'mypipe'  # Nome della named pipe
    with open(fifo, 'r') as pipe:
        while True:
            message = pipe.readline().strip()  # Legge una riga dalla pipe
            if message:
                print(f"Ricevuto: {message}")

if __name__ == "__main__":
    reader()


def writer():
    fifo = 'mypipe'  # Nome della named pipe
    with open(fifo, 'w') as pipe:
        for i in range(5):
            message = f"Messaggio {i+1}"
            pipe.write(message + '\n')
            print(f"Inviato: {message}")
            time.sleep(1)  # Simula un delay tra i messaggi

if __name__ == "__main__":
    # Crea la named pipe se non esiste
    if not os.path.exists('mypipe'):
        os.mkfifo('mypipe')

    writer()
