package publico
/**
 * The Tsecuencias entity.
 *
 * @author    
 *
 *
 */
class Tsecuencias {
    static mapping = {
         table 't_secuencias'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    String fTipoDocumento
    Long fSecuencia

    static constraints = {
        fTipoDocumento(size: 0..10)
        fSecuencia(nullable: true, max: 9999999999L)
    }
    String toString() {
    }
}
