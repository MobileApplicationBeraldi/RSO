#include <stdio.h>
#include <unistd.h>
#include <sys/syscall.h>
#include <sys/time.h>

int main() {
    struct timeval tv;

    // Chiamata di sistema per ottenere l'ora corrente (gettimeofday)
    syscall(SYS_gettimeofday, &tv, NULL);

    printf("Seconds: %ld, Microseconds: %ld\n", tv.tv_sec, tv.tv_usec);

    return 0;
}
