package cfg;
class TvideosAyuda {
    static mapping = {
         table 'cfg.t_videos_ayuda'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    String fUrl
    String fTitulo
    String fDescripcion
    Boolean fActivo
    Long fProyecto

    static constraints = {
        fUrl(size: 0..100)
        fTitulo(size: 0..50)
        fDescripcion(size: 0..250)
        fActivo(nullable: true)
        fProyecto(nullable: true, max: 9999999999L)
    }
    String toString() {
    }
}
