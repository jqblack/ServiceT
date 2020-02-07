package publico
/**
 * The Trecibos entity.
 *
 * @author    
 *
 *
 */
class Trecibos {
    static mapping = {
         table 't_recibos'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
        fIdcliente column:'f_idcliente'
    }
    Long fNoRecibo
    Date fFecha
    Long fHechoPor
    String fConcepto
    java.math.BigDecimal fMonto
    String fWhole
    String fTipoRecibo
    Long fMoneda
    Long fIdcliente

    static constraints = {
        fNoRecibo(nullable: true, max: 9999999999L)
        fFecha(nullable: true)
        fHechoPor(nullable: true, max: 9999999999L)
        fConcepto()
        fMonto(nullable: true)
        fMoneda(nullable: true)
        fIdcliente(nullable: true)
        fWhole(size: 0..15)
        fTipoRecibo(size: 0..5)
    }
    String toString() {
    }
}
