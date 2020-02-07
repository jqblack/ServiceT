/**
 * The Tusuario entity.
 *
 * @author    
 *
 *
 */
package clientes
class Tusuarioclientes {
    static mapping = {
         table 'clientes.t_usuarioclientes'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    Long fUsername
    String fContrasena
    Boolean fFirstlogin

    static constraints = {
        fUsername(nullable: true, max: 9999999999L)
        fContrasena(size: 0..32)
        fFirstlogin(nullable: true)
    }

}
