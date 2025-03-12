import os

# Creazione della pipe^M
r, w = os.pipe()

pid = os.fork()

if pid == 0:  # Figlio
    os.close(w)  # Chiude il lato scrittura
    message = os.read(r, 50).decode()
    print(f"Figlio ha stringa: {message}")
    os.close(r)
else:  # Padre
    os.close(r)  # Chiude il lato lettura
    message = "Ciao dal padre!"
    os.write(w, message.encode())
    os.close(w)
~                  
