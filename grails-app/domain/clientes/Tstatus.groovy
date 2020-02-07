/**
 * The Tstatus entity.
 *
 * @author    
 *
 *
 */
package clientes
class Tstatus {
    static mapping = {
         table 'clientes.t_status'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    String fDescripcion

    static constraints = {
        fDescripcion(size: 0..20)
    }
}
