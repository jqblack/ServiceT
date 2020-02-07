/**
 * The Tusuarios entity.
 *
 * @author    
 *
 *
 */
package tk
class Tusuarios {
    static mapping = {
         table 'tk.t_usuarios'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
        fNombre column:'f_nombre'
         fApellido column:'f_apellido'
    }

    String fUsuario
    String fNombre
    String fApellido
    String fPass
    Long fIdcliente
    Boolean fActivo
    String fEmail

    static constraints = {

        fUsuario(size: 0..60)
        fPass(size: 0..35)
        fIdcliente(nullable: true, max: 9999999999L)
        fActivo(nullable: true)
        fNombre(nullable: true)
        fApellido(nullable: true)
        fEmail()
    }
    String toString() {

    }
}
