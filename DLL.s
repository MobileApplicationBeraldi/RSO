.set noreorder

.data

got_entry_printf:   .word func_stub  # Simula una voce nella GOT che inizialmente punta a un "stub"
got_entry_read:   .word func_stub  # Simula una voce nella GOT che inizialmente punta a un "stub"


.text
.globl _start

_start:
    # Simula una chiamata a una funzione dinamica (es. printf)
    li   $t0, got_entry_printf   # Carica l'indirizzo della voce GOT
    lw   $t1, 0($t0)      # Legge l'indirizzo della funzione dalla GOT
    jalr $t1              # Salta alla funzione (indiretto)

    li   $t0, got_entry   # Carica l'indirizzo della voce GOT
    lw   $t1, 0($t0)      # Legge l'indirizzo della funzione dalla GOT
    jalr $t1             # Salta alla funzione (indiretto)

    li   $v0, 10          # Exit syscall
    syscall

# Simula la PLT che inizialmente punta a uno stub
func_stub:
    # Prima esecuzione: risolve l'indirizzo e aggiorna la GOT
    la   $t0, got_entry
    la   $t1, real_function  # Supponiamo che il dynamic linker risolva l'indirizzo reale
    sw   $t1, 0($t0)         # Aggiorna la GOT con l'indirizzo reale

    # Ora saltiamo alla funzione reale
    jr   $t1

# Questa è la funzione "reale" che viene risolta dinamicamente
real_function:
    li   $v0, 4
    la   $a0, msg
    syscall
    jr   $ra

.data
msg: .asciiz "Funzione chiamata dinamicamente!\n"
