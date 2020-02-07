package publico
/**
 * The Tfacturas entity.
 *
 * @author    
 *
 *
 */
class Tfacturas {
    static mapping = {
         table 't_facturas'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
         fHechoPor column:'f_hecho_por'
         fMoneda column:'f_moneda'
    }
    Long fNofactura
    String fTipofactura
    Date fFecha
    Date fFechaVencimiento
    java.math.BigDecimal fMonto
    Long fIdCliente
    Boolean fPagada
    java.math.BigDecimal fTax
    java.math.BigDecimal fMontoIsr
    String fWhole
    Boolean fEnviada
    java.math.BigDecimal fBalance
    java.math.BigDecimal fEnvio
    String fComentario
    Boolean fCancelada
    Long fHechoPor
    Long fMoneda

    static constraints = {
        fNofactura(max: 9999999999L)
        fTipofactura(size: 0..10)
        fFecha(nullable: true)
        fFechaVencimiento(nullable: true)
        fMonto(nullable: true)
        fIdCliente(nullable: true, max: 9999999999L)
        fPagada(nullable: true)
        fTax(nullable: true)
        fMontoIsr(nullable: true)
        fWhole(size: 0..20)
        fEnviada(nullable: true)
        fBalance(nullable: true)
        fEnvio(nullable: true)
        fComentario(nullable: true);
        fWhole(nullable: true);
        fCancelada(nullable: true)
        fHechoPor(nullable: true)
        fMoneda(nullable: true)
        //fComentario(nullable: true)
    }
    String toString() {
    }
}
