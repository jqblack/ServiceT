/**
 * The TpermisosUsuarios entity.
 *
 * @author    
 *
 *
 */
package cfg;
class TpermisosUsuarios {
    static mapping = {
         table 'cfg.t_permisos_usuarios'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    Long fIdusuario
    Long fIdopcion

    static constraints = {
        fIdusuario(nullable: true, max: 9999999999L)
        fIdopcion(nullable: true, max: 9999999999L)
    }
    String toString() {
    }
}
