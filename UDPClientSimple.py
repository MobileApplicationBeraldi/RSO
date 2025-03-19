import socket

# Configura il client
SERVER_IP = "127.0.0.1"  # Indirizzo IP del server
PORT = 12345             # Porta del server

# Crea il socket UDP
client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

# Messaggio da inviare
message = input("Inserisci il messaggio da inviare: ")
client_socket.sendto(message.encode(), (SERVER_IP, PORT))

print("Messaggio inviato!")
client_socket.close()

