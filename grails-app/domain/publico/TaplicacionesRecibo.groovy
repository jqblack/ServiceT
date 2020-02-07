package publico
/**
 * The TaplicacionesRecibo entity.
 *
 * @author    
 *
 *
 */
class TaplicacionesRecibo {
    static mapping = {
         table 't_aplicaciones_recibo'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_id'
    }

    String fFactura
    String fDocumento
    java.math.BigDecimal fMonto
    Date fFecha
    Long fHechoPor
    Long fNoRecibo
    String fTipoRecibo

    static constraints = {

        fFactura(size: 0..20)
        fDocumento(size: 0..20)
        fMonto(nullable: true)
        fFecha(nullable: true)
        fHechoPor(nullable: true, max: 9999999999L)
        fNoRecibo(nullable: true, max: 9999999999L)
        fTipoRecibo(size: 0..5)
    }
    String toString() {

    }
}
