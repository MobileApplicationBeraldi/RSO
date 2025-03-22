import socket

HOST = "localhost"  # Cambia con l'IP del server se necessario
PORT = 8080         # Deve corrispondere alla porta del server

def main():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((HOST, PORT))
        while True:
            msg = input("Inserisci una stringa (o 'exit' per uscire): ")
            if msg.lower() == "exit":
                break
            a = s.sendall((msg+"\n").encode())  # Invia il messaggio al server
            print('sent',a)
            data = s.recv(1024)      # Riceve la risposta
            print("Risposta dal server:", data.decode())

if __name__ == "__main__":
    main()
