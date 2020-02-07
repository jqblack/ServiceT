/**
 * The TserialFormula entity.
 *
 * @author    
 *
 *
 */
package publico
class TserialFormula {
    static mapping = {
         table 't_serial_formula'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id column:'f_idrecord',generator: "identity"
         fTipoProyecto column:'f_tipo_proyecto'
         fServer column:'f_server'
    }
    String fSerial
    String fCliente
    String fImei
    Date fFecha
    Boolean fActivo
    Long fServer;
    Long fTipoProyecto

    static constraints = {
        fSerial(size: 0..30)
        fCliente(size: 0..30)
        fImei(size: 0..30)
        fFecha(nullable: true)
        fActivo(nullable: true)
        fTipoProyecto(nullable: true)
        fServer(nullable: true);
    }
    String toString() {
    }
}
