import os
import json

# Creazione della pipe
r, w = os.pipe()

pid = os.fork()

if pid == 0:  # Processo figli
    print(f"Sono il figlio PID:,{os.getpid()}")
    os.close(w)  # Chiude il lato scrittura
    data = os.read(r, 100).decode()  # Legge i dati e li decodifica
    os.close(r)

    # Decodifica il JSON
    messaggio_ricevuto = json.loads(data)
    print(f"Figlio ha ricevuto: {messaggio_ricevuto}")
    x = messaggio_ricevuto['numero']
    print(x)

else:  # Processo padre
    os.close(r)  # Chiude il lato lettura
    messaggio = {"testo": "Ciao dal padre", "numero": 42}  # Dizionario con string
a e numero
    dati = json.dumps(messaggio)  # Converte in JSON string
    os.write(w, dati.encode())  # Scrive i dati nella pipe
    os.close(w)
