#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

int main() {
    int fd[2]; // File descriptors for the pipe

    if (pipe(fd) == -1) {
        perror("Pipe creation failed");
        exit(EXIT_FAILURE);
    }

    pid_t pid = fork();

    if (pid == -1) {
        perror("Fork failed");
        exit(EXIT_FAILURE);
    }
    if (pid > 0) { // Parent process
        close(fd[0]); // Close the read end of the pipe
        int data[] = {1, 2, 3, 4, 5};
        const char *msg = "dati spediti";
        write(fd[1],data,sizeof(data));
        close(fd[1]); // Close the write end of the pipe after writing

        wait(NULL); // Wait for the child process to finish
    }

    else { // Child process
        close(fd[1]); // Close the write end of the pipe

        int received_data[sizeof(int) * 5];
        read(fd[0], received_data, sizeof(received_data));

        close(fd[0]); // Close the read end of the pipe after reading

        write(STDOUT_FILENO, "Child processed data: ", 23);
        for (int i = 0; i < 5; i++) {
                printf("%d ", received_data[i]);
        }
        printf("\n");
    }

    return 0;
}
