#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

#define BUFFER_SIZE 100

int main() {
    int pipefd1[2], pipefd2[2];
    pid_t pid;
    char buffer[BUFFER_SIZE];

    // Creazione delle due pipe
    if (pipe(pipefd1) == -1 || pipe(pipefd2) == -1) {
        perror("pipe");
        exit(EXIT_FAILURE);
    }

    pid = fork();

    if (pid < 0) {
        perror("fork");
        exit(EXIT_FAILURE);
    }

    if (pid == 0) { // Processo figlio
        close(pipefd1[1]); // Chiude la scrittura della prima pipe
        close(pipefd2[0]); // Chiude la lettura della seconda pipe

        // Legge il messaggio dal padre
        read(pipefd1[0], buffer, BUFFER_SIZE);
        printf("Figlio ha ricevuto: %s\n", buffer);

        // Prepara la risposta
        char response[] = "Messaggio ricevuto, padre!";
        write(pipefd2[1], response, strlen(response) + 1);

        close(pipefd1[0]);
        close(pipefd2[1]);
        exit(EXIT_SUCCESS);
    } else { // Processo padre
        close(pipefd1[0]); // Chiude la lettura della prima pipe
        close(pipefd2[1]); // Chiude la scrittura della seconda pipe

        // Messaggio da inviare al figlio
        char message[] = "Ciao figlio!";
        write(pipefd1[1], message, strlen(message) + 1);

        // Attende la risposta del figlio
        read(pipefd2[0], buffer, BUFFER_SIZE);
        printf("Padre ha ricevuto: %s\n", buffer);

        close(pipefd1[1]);
        close(pipefd2[0]);
        wait(NULL); // Attende la terminazione del figlio
    }
