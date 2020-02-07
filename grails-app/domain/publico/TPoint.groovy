package publico


//import com.vividsolutions.jts.geom.Point

/**
 * The Tgastos entity.
 *
 * @author    
 *
 *
 */
class TPoint {
    static mapping = {
         table 'point'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
         point column:'point'
    }


    Object point;

    static constraints = {

    }
    String toString() {
    }
}
