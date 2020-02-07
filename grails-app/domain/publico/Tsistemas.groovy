package publico
/**
 * The Tsistemas entity.
 *
 * @author    
 *
 *
 */
class Tsistemas {
    static mapping = {
         table 't_sistemas'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_id_record'
    }
    String fDescripcion

    static constraints = {
        fDescripcion(size: 0..100)
    }
    String toString() {
    }
}
