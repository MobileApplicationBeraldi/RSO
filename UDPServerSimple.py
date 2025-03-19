import socket

# Configura il server
HOST = "0.0.0.0"  # Ascolta su tutte le interfacce
PORT = 12345      # Porta su cui ascoltare

# Crea il socket UDP
server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
server_socket.bind((HOST, PORT))

print(f"Server in ascolto su {HOST}:{PORT}")

while True:
    data, addr = server_socket.recvfrom(1024)  # Riceve fino a 1024 byte
    print(f"Messaggio ricevuto da {addr}: {data.decode()}")
