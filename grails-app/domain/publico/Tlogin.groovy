package publico
/**
 * The Tlogin entity.
 *
 * @author    
 *
 *
 */
class Tlogin {
    static mapping = {
         table 't_login'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    Date fFecha
    String fToken
    String fUsuario
    Boolean fActivo

    static constraints = {
        fFecha(nullable: true)
        fToken(size: 0..33)
        fUsuario(size: 0..32)
        fActivo(nullable: true)
    }
    String toString() {
    }
}
