package publico
/**
 * The Tncnd entity.
 *
 * @author    
 *
 *
 */
class Tncnd {
    static mapping = {
         table 't_ncnd'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    String fTipo
    Long fNumero
    Date fFecha
    Long fHechoPor
    String fConcepto
    java.math.BigDecimal fMonto

    static constraints = {
        fTipo(size: 0..5)
        fNumero(nullable: true, max: 9999999999L)
        fFecha(nullable: true)
        fHechoPor(nullable: true, max: 9999999999L)
        fConcepto()
        fMonto(nullable: true)
    }
    String toString() {
    }
}
