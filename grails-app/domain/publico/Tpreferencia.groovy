package publico
/**
 * The Tpreferencia entity.
 *
 * @author    
 *
 *
 */
class Tpreferencia {
    static mapping = {
         table 't_preferencia'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord';
         fLogo column:'f_logo';
    }
    String fNombre
    String fDireccion
    String telefono
    String fEmail
    String fWeb
    java.math.BigDecimal fTax
    java.math.BigDecimal fIsr
    String fMensajeFactura
    String fMensajeCotizacion
    byte [] fLogo;

    static constraints = {
        fNombre(size: 0..30)
        fDireccion()
        telefono(size: 0..15)
        fEmail(size: 0..30)
        fWeb(size: 0..50)
        fTax(nullable: true)
        fIsr(nullable: true)
        fMensajeFactura()
        fMensajeCotizacion()
    }
    String toString() {
    }
}
