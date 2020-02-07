package publico
/**
 * The Tversiones entity.
 *
 * @author    
 *
 *
 */
class Tversiones {
    static mapping = {
         table 't_versiones'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_id_record'
    }
    String fDescripcion
    Long fIdSistema

    static constraints = {
        fDescripcion(size: 0..100)
        fIdSistema(nullable: true, max: 9999999999L)
    }
    String toString() {
    }
}
