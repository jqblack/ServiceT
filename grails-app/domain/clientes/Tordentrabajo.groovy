/**
 * The Tordentrabajo entity.
 *
 * @author    
 *
 *
 */
package clientes
class Tordentrabajo {
    static mapping = {
         table 'clientes.t_ordentrabajo'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    Long fIdcliente
    Long fNumorden
    String fTipodoc
    Long fStatus
    String fTitulo
    String fComentario
    Date fFecha

    static constraints = {
        fIdcliente(nullable: true, max: 9999999999L)
        fNumorden(nullable: true, max: 9999999999L)
        fTipodoc(nullable: true)
        fStatus(nullable: true, max: 9999999999L)
        fTitulo(nullable: true)
        fComentario(nullable: true)
        fFecha(nullable: true)
    }

}
