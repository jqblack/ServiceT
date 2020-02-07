package conocimiento
/**
 * The Ttags entity.
 *
 * @author    
 *
 *
 */
class Ttags {
    static mapping = {
         table 'conocimiento.t_tags'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    String fNombre

    static constraints = {
        fNombre(size: 1..20, blank: false)
    }
    String toString() {
    }
}
