package publico
/**
 * The TmsgClientes entity.
 *
 * @author    
 *
 *
 */
class TmsgClientes {
    static mapping = {
         table 't_msg_clientes'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_id'
    }
    String fMsg
    Boolean fActivo
    Date fFecha
    Long fIdcliente
    Long fTipo

    static constraints = {
        fMsg(nullable: true)
        fActivo(nullable: true)
        fFecha(nullable: true)
        fIdcliente(nullable: true, max: 9999999999L)
        fTipo(nullable: true, max: 9999999999L)
    }
    String toString() {
    }
}
