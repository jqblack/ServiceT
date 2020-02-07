/**
 * The Tcomentarioorden entity.
 *
 * @author    
 *
 *
 */
package clientes
class Tcomentarioorden {
    static mapping = {
         table 'clientes.t_comentarioorden'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    Long fIdempleado
    Long fIdorden
    String fDescripcion
    Date fFecha

    static constraints = {
        fIdempleado(nullable: true, max: 9999999999L)
        fIdorden(nullable: true, max: 9999999999L)
        fDescripcion(nullable: true)
        fFecha(nullable: true)
    }

}
