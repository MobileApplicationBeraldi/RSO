.data
ivt: .word timer_isr   # Indirizzo della ISR per Timer (IVT[0])
     .word keyboard_isr # Indirizzo della ISR per Keyboard (IVT[1])

msg_timer:   .asciiz "Timer Interrupt Handled!\n"
msg_keyboard: .asciiz "Keyboard Interrupt Handled!\n"

.text
.globl main, exception_handler

main:
    li $t0, 0  # Tipo di interrupt da simulare (0=Timer, 1=Keyboard)
    
    # Simula un interrupt software
    li $v0, 10  # Codice syscall non valido per generare un'eccezione
    syscall     # Questo salterà all'handler degli interrupt

# ============================================
# HANDLER GENERICO DEGLI INTERRUPT
# ============================================
exception_handler:
    # Salva i registri importanti
    addiu $sp, $sp, -8
    sw $ra, 4($sp)
    sw $t0, 0($sp)

    # Simula lettura del codice di interrupt (in realtà andrebbe letto da registro Cause)
    move $a0, $t0  # Supponiamo che $t0 abbia l'ID dell'interrupt
    
    # Carica l'indirizzo della IVT
    la $t1, ivt        # Base della IVT
    sll $a0, $a0, 2    # Offset = ID * 4
    add $t1, $t1, $a0  # Calcola l'indirizzo IVT[ID]
    lw $t2, 0($t1)     # Carica l'indirizzo della ISR
    
    # Salta alla ISR selezionata
    jalr $t2
    
    # Ripristina i registri e ritorna
    lw $t0, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    eret  # Torna al programma principale

# ============================================
# ROUTINE DI GESTIONE DEGLI INTERRUPT
# ============================================
timer_isr:
    li $v0, 4
    la $a0, msg_timer
    syscall
    jr $ra

keyboard_isr:
    li $v0, 4
    la $a0, msg_keyboard
    syscall
    jr $ra
