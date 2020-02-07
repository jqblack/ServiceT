package publico
/**
 * The Tsuplidores entity.
 *
 * @author    
 *
 *
 */
class Tsuplidores {
    static mapping = {
         table 't_suplidores'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_id'
    }
    String fNombre
    String fDireccion
    String fTelefono
    String fRnc

    static constraints = {
        fNombre(size: 0..40)
        fDireccion()
        fTelefono(size: 0..12)
        fRnc(size: 0..12)
    }
    String toString() {
    }
}
