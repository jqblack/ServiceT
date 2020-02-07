package jq_servicios

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }


        "/"(redirect: "/login")

        //SESION

        '/login'(controller: 'ws'){
            action = [GET:'login']
        }
        '/logout'(controller: 'ws'){
            action = [GET:'logout']
        }
        '/index'(controller: 'ws'){
            action = [GET:'index']
        }
        '/login/iniciar_sesion'(controller: 'ws'){
            action = [POST:'iniciar_sesion']
        }

        //TICKETS

        '/index_tickets'(controller: 'tickets'){
            action = [GET:'index_tickets']
        }


        //CLIENTES

        '/clientes'(controller: 'ws'){
            action = [GET:'clientes']
        }

        '/clientes/get_clientes'(controller: 'ws'){
            action = [GET:'get_clientes']
        }

        '/clientes/generar_factura_clientes'(controller: 'ws'){
            action = [POST:'generar_factura_clientes']
        }

        '/clientes/get_clientes_activos_no_activos'(controller: 'ws'){
            action = [POST:'get_clientes_activos_no_activos']
        }

        '/clientes/get_clientes_by_id'(controller: 'ws'){
            action = [POST:'get_clientes_by_id']
        }

        '/clientes/generar_factura_clientes'(controller: 'ws'){
            action = [POST:'generar_factura_clientes']
        }

        '/clientes/salvar_cliente'(controller: 'ws'){
            action = [POST:'salvar_cliente']
        }

        //PRODUCTOS

        '/productos'(controller: 'ws'){
            action = [GET:'productos']
        }

        '/productos/get_productos_by_id'(controller: 'ws'){
            action = [POST:'get_productos_by_id']
        }
        '/productos/get_productos'(controller: 'ws'){
            action = [GET:'get_productos']
        }

        '/productos/get_productos_activos_no_activos'(controller: 'ws'){
            action = [POST:'get_productos_activos_no_activos']
        }

        '/productos/salvar_productos'(controller: 'ws'){
            action = [POST:'salvar_productos']
        }


        //REFERENTES

        '/referentes'(controller: 'ws'){
            action = [GET:'referentes']
        }
        '/referentes/get_referentes'(controller: 'ws'){
            action = [GET:'get_referentes']
        }
        '/referentes/get_referentes_by_id'(controller: 'ws'){
            action = [POST:'get_referentes_by_id']
        }
        '/referentes/salvar_referentes'(controller: 'ws'){
            action = [POST:'salvar_referentes']
        }

        //USUARIOS

        '/usuarios'(controller: 'ws'){
            action = [GET:'usuarios']
        }

        '/usuarios/get_usuarios'(controller: 'ws'){
            action = [GET:'get_usuarios']
        }
        '/usuarios/get_validar_usuarios'(controller: 'ws'){
            action = [GET:'get_validar_usuarios']
        }

        '/usuarios/get_usuarios_by_id'(controller: 'ws'){
            action = [POST:'get_usuarios_by_id']
        }

        '/usuarios/salvar_usuarios'(controller: 'ws'){
            action = [POST:'salvar_usuarios']
        }

        '/usuarios/get_permisos_usuarios'(controller: 'ws'){
            action = [POST:'get_permisos_usuarios']
        }


        '/usuarios/salvar_permisos_usuarios'(controller: 'ws'){
            action = [POST:'salvar_permisos_usuarios']
        }

        //SERIALES FORMULA

        "/seriales_formula"(controller: 'ws'){
            action = [GET:'seriales_formula']
        }

        "/seriales_formula/get_seriales_formulas"(controller: 'ws'){
            action = [POST:'get_seriales_formulas']
        }

        "/seriales_formula/get_seriales_formulas_by_id"(controller: 'ws'){
            action = [POST:'get_seriales_formulas_by_id']
        }


        "/seriales_formula/salvar_serial_formula"(controller: 'ws'){
            action = [POST:'salvar_serial_formula']
        }

        //Raro
        "/get_serial_formula/$dato"(controller: 'ws'){
            action = [GET:'get_serial_formula']
        }

        //DISTRIBUCION DE CORREO

        "/distribucion_correos"(controller: 'ws'){
            action = [GET:'distribucion_correos']
        }

        '/distribucion_correos/get_clientes_activos_no_activos'(controller: 'ws'){
            action = [POST:'get_clientes_activos_no_activos']
        }
        '/distribucion_correos/get_distribucion_correos_by_cliente'(controller: 'ws'){
            action = [POST:'get_distribucion_correos_by_cliente']
        }
        '/distribucion_correos/salvar_distribucion_correos'(controller: 'ws'){
            action = [POST:'salvar_distribucion_correos']
        }

        //USUARIO TICKETS
        '/usuarios_tickets'(controller: 'ws'){
            action = [GET:'usuarios_tickets']
        }

        '/usuarios_tickets/get_usuarios_tickets'(controller: 'ws'){
            action = [POST:'get_usuarios_tickets']
        }

        '/usuarios_tickets/get_usuarios_tickets_by_id'(controller: 'ws'){
            action = [POST:'get_usuarios_tickets_by_id']
        }

        '/usuarios_tickets/salvar_usuario_tickets'(controller: 'ws'){
            action = [POST:'salvar_usuario_tickets']
        }

        '/usuarios_tickets/get_validar_usuarios'(controller: 'ws'){
            action = [POST:'get_validar_usuarios']
        }

        //PROCESOS

        //RECIBOS
        '/recibos/get_enviar_estado_cuenta_con_correo'(controller: 'ws'){
            action = [POST:'get_enviar_estado_cuenta_con_correo']
        }

        '/recibos/'(controller: 'ws'){
            action = [GET:'recibos']
        }

        '/recibos/get_clientes'(controller: 'ws'){
            action = [GET:'get_clientes']
        }

        '/recibos/get_facturas_cxc_by_id'(controller: 'ws'){
            action = [POST:'get_facturas_cxc_by_id']
        }

        '/recibos/salvar_recibo'(controller: 'ws'){
            action = [POST:'salvar_recibo']
        }

        '/recibos/get_reporte_estado_cuenta_by_id'(controller: 'ws'){
            action = [POST:'get_reporte_estado_cuenta_by_id']
        }

        //PAGOS GENERALES

        '/pagos_generales'(controller: 'ws'){
            action = [GET:'pagos_generales']
        }
        '/pagos_generales/get_facturas_pagos_generales'(controller: 'ws'){
            action = [GET:'get_facturas_pagos_generales']
        }

        '/pagos_generales/salvar_pagos_generales'(controller: 'ws'){
            action = [POST:'salvar_pagos_generales']
        }
        '/pagos_generales/get_enviar_factura_cliente'(controller: 'ws'){
            action = [POST:'get_enviar_factura_cliente']
        }

        //NOTAS DE CREDITO

        '/nota_credito'(controller: 'ws'){
            action = [GET:'nota_credito']
        }

        '/nota_credito/get_clientes'(controller: 'ws'){
            action = [GET:'get_clientes']
        }

        '/nota_credito/get_facturas_cxc_by_id'(controller: 'ws'){
            action = [POST:'get_facturas_cxc_by_id']
        }

        '/nota_credito/salvar_nota_credito'(controller: 'ws'){
            action = [POST:'salvar_nota_credito']
        }

        //NOTA DEBITO

        '/nota_debito'(controller: 'ws'){
            action = [GET:'nota_debito']
        }

        '/nota_debito/get_clientes'(controller: 'ws'){
            action = [GET:'get_clientes']
        }

        '/nota_debito/salvar_nota_debito'(controller: 'ws'){
            action = [POST:'salvar_nota_debito']
        }

        //SERVICIOS CONTRATADOS

        '/servicios_contratados'(controller: 'ws'){
            action = [GET:'servicios_contratados']
        }
        '/servicios_contratados/get_servicios_contratados'(controller: 'ws'){
            action = [GET:'get_servicios_contratados']
        }
        '/servicios_contratados/get_servicios_contratados_activos'(controller: 'ws'){
            action = [POST:'get_servicios_contratados_activos']
        }

        '/servicios_contratados/get_servicios_contratados_by_id'(controller: 'ws'){
            action = [POST:'get_servicios_contratados_by_id']
        }
        '/servicios_contratados/get_productos'(controller: 'ws'){
            action = [GET:'get_productos']
        }
        '/servicios_contratados/get_productos_activos_no_activos'(controller: 'ws'){
            action = [POST:'get_productos_activos_no_activos']
        }
        '/servicios_contratados/get_clientes'(controller: 'ws'){
            action = [GET:'get_clientes']
        }
        '/servicios_contratados/get_referentes'(controller: 'ws'){
            action = [GET:'get_referentes']
        }

        '/servicios_contratados/salvar_servicios_contratados'(controller: 'ws'){
            action = [POST:'salvar_servicios_contratados']
        }

        //MENSAJES

        '/mensajes'(controller: 'ws'){
            action = [GET:'mensajes']
        }

        '/mensajes/get_mensajes'(controller: 'ws'){
            action = [GET:'get_mensajes']
        }
        '/mensajes/get_mensajes_by_id'(controller: 'ws'){
            action = [POST:'get_mensajes_by_id']
        }
        '/mensajes/salvar_mensajes'(controller: 'ws'){
            action = [POST:'salvar_mensajes']
        }
        '/mensajes/get_clientes'(controller: 'ws'){
            action = [GET:'get_clientes']
        }
        '/mensajes/desactivar_todo_los_mensajes'(controller: 'ws'){
            action = [POST:'desactivar_todo_los_mensajes']
        }

        //FACTURAS

        '/facturas'(controller: 'ws'){
            action = [GET:'facturas']
        }


        '/facturas/get_clientes_activos_no_activos'(controller: 'ws'){
            action = [POST:'get_clientes_activos_no_activos']
        }

        '/facturas/get_enviar_facturas_con_correo'(controller: 'ws'){
            action = [POST:'get_enviar_facturas_con_correo']
        }

        '/facturas/get_productos_activos_no_activos'(controller: 'ws'){
            action = [POST:'get_productos_activos_no_activos']
        }

        '/facturas/get_productos_by_id'(controller: 'ws'){
            action = [POST:'get_productos_by_id']
        }

        '/facturas/salvar_facturas'(controller: 'ws'){
            action = [POST:'salvar_facturas']
        }

        //VER FACTURAS

        '/ver_facturas'(controller: 'ws'){
            action = [GET:'ver_facturas']
        }

        '/ver_facturas/get_ver_facturas'(controller: 'ws'){
            action = [GET:'get_ver_facturas']
        }
        '/ver_facturas/duplicar_factura'(controller: 'ws'){
            action = [POST:'duplicar_factura']
        }
        '/ver_facturas/get_enviar_facturas_con_correo'(controller: 'ws'){
            action = [POST:'get_enviar_facturas_con_correo']
        }
        '/ver_facturas/get_documento_by_id'(controller: 'conocimientos'){
            action = [POST:'get_documento_by_id']
        }

        '/ver_facturas/get_reporte_factura_by_id'(controller: 'ws'){
            action = [POST:'get_reporte_factura_by_id']
        }

        '/ver_facturas/get_enviar_factura_cliente'(controller: 'ws'){
            action = [POST:'get_enviar_factura_cliente']
        }

        //VER RECIBOS

        '/out_reporte_recibos/'(controller: 'ws'){
            action = [GET:'out_reporte_recibos']
        }

        '/out_reporte_recibos/get_out_recibos'(controller: 'ws'){
            action = [POST:'get_out_recibos']
        }

        '/out_reporte_recibos/get_clientes'(controller: 'ws'){
            action = [GET:'get_clientes']
        }

        //DASHBOARD

        '/dashboard'(controller: 'ws'){
            action = [GET:'dashboard']
        }

        '/dashboard/generar_factura_clientes'(controller: 'ws'){
            action = [POST:'generar_factura_clientes']
        }
        '/dashboard/get_utima_factura'(controller: 'ws'){
            action = [POST:'get_utima_factura']
        }
        '/dashboard/set_desactivar_servicio_contratado'(controller: 'ws'){
            action = [POST:'set_desactivar_servicio_contratado']
        }
        '/dashboard/set_mensajes_clientes'(controller: 'ws'){
            action = [POST:'set_mensajes_clientes']
        }

        //CANCELAR FACTURAS

        "/cancelar_factura"(controller: "ws"){
            action=[GET:"cancelar_factura"];
        }

        "/cancelar_factura/get_factura_cancelar"(controller: "ws"){
            action=[POST:"get_factura_cancelar"];
        }

        "/cancelar_factura/get_cancelar_factura"(controller: "ws"){
            action=[POST:"get_cancelar_factura"];
        }

        //CONOCIMIENTOS

        '/conocimiento'(controller: 'conocimientos'){
            action = [GET:'conocimiento']
        }

        '/conocimiento/get_conocimiento'(controller: 'conocimientos'){
            action = [GET:'get_conocimiento']
        }
        '/conocimiento/get_conocimiento_by_id'(controller: 'conocimientos'){
            action = [POST:'get_conocimiento_by_id']
        }
        '/conocimiento/get_documento_by_id'(controller: 'conocimientos'){
            action = [POST:'get_documento_by_id']
        }
        '/conocimiento/ver_solucion/get_documento_by_id'(controller: 'conocimientos'){
            action = [POST: 'get_documento_by_id']
        }
        '/conocimiento/ver_solucion'(controller: 'conocimientos'){
            action = [GET:'ver_solucion']
        }

        '/conocimiento/ver_solucion/descargar_documento'(controller: 'conocimientos'){
            action = [POST:'descargar_documento']
        }

        '/conocimiento/crear_id_solucion'(controller: 'conocimientos'){
            action = [POST:'crear_id_solucion']
        }
        '/conocimiento/salvar_conocimiento'(controller: 'conocimientos'){
            action = [POST:'salvar_conocimiento']
        }
        '/conocimiento/salvar_documento'(controller: 'conocimientos'){
            action = [POST:'salvar_documento']
        }

        //EMPLEADO

        '/empleados'(controller: 'ws'){
            action = [GET:'empleados']
        }

        '/empleados/getEmpleado'(controller: 'proyecto'){
            action = [POST:'getEmpleados']
        }

        '/empleados/salvar_empleado'(controller: 'proyecto'){
            action = [POST:'salvar_empleado']
        }
        '/empleados/get_empleado_by_id'(controller: 'proyecto'){
            action = [POST:'get_empleado_by_id']
        }

        //PROYECTOS

        '/proyectos'(controller: 'proyecto'){
            action = [GET:'proyectos']
        }

        '/proyectos/getProyectos'(controller: 'proyecto'){
            action = [POST:'getProyectos']
        }

        '/proyectos/salvarProyectos'(controller: 'proyecto'){
            action = [POST:'salvarProyectos']
        }
        '/proyectos/get_proyectos_by_id'(controller: 'proyecto'){
            action = [POST:'get_proyectos_by_id']
        }


        //TAREAS

        '/tareas/'(controller: 'proyecto'){
            action = [GET:'tareas']
        }
        '/tareas/get_tareas'(controller: 'proyecto'){
            action = [POST:'get_tareas']
        }

        '/tareas/get_tareas_by_id'(controller: 'proyecto'){
            action = [POST:'get_tareas_by_id']
        }
        '/tareas/salvar_tareas'(controller: 'proyecto'){
            action = [POST:'salvar_tareas']
        }

        '/tareas/salvar_track'(controller: 'proyecto'){
            action = [POST:'salvar_track']
        }

        //VER TRACK

        '/ver_track/'(controller: 'proyecto'){
            action = [GET:'ver_track']
        }

        '/ver_track/get_tareas'(controller: 'proyecto'){
            action = [POST:'get_tareas']
        }

        '/ver_track/get_tracks'(controller: 'proyecto'){
            action = [POST:'get_tracks']
        }


        "/ticket/"(controller: 'tickets'){
            action = [GET:'ticket']
        }
        //ASIGNAR TICKTES

        '/asignar_tickets'(controller: 'tickets'){
            action = [GET:'asignar_tickets']
        }

        '/asignar_tickets/salvar_asignacion_tickets'(controller: 'tickets'){
            action = [POST:'salvar_asignacion_tickets']
        }

        '/asignar_tickets/salvar_cancelacion_tickets'(controller: 'tickets'){
            action = [POST:'salvar_cancelacion_tickets']
        }

        //TICKETS SIN ASIGNAR

        '/tickets_sin_asignar'(controller: 'tickets'){
            action = [GET:'tickets_sin_asignar',POST:'tickets_sin_asignar']
        }

        //REASIGNAR TICKETS

        '/reasignar_tickets'(controller: 'tickets'){
            action = [GET:'reasignar_tickets']
        }

        '/reasignar_tickets/salvar_reasignacion_tickets'(controller: 'tickets'){
            action = [POST:'salvar_reasignacion_tickets']
        }
        '/reasignar_tickets/salvar_cancelacion_tickets'(controller: 'tickets'){
            action = [POST:'salvar_cancelacion_tickets']
        }

        '/tickets_asignados'(controller: 'tickets'){
            action = [GET:'tickets_asignados',POST:'tickets_asignados']
        }

//        ACTUALIZAR TICKETS

        '/actualizar_tickets'(controller: 'tickets'){
            action = [GET:'actualizar_tickets',POST: 'actualizar_tickets']
        }
        '/actualizar_tickets/descargarUpdate'(controller: 'tickets'){
            action = [GET:'descargarUpdate',POST: 'descargarUpdate']
        }

        '/actualizar_tickets/salvar_comentario'(controller: 'tickets'){
            action = [POST:'salvar_comentario']
        }

        '/actualizar_tickets/cerrar_ticket'(controller: 'tickets'){
            action = [POST:'cerrar_ticket']
        }

        '/actualizar_tickets/salvar_archivo_update'(controller: 'tickets'){
            action = [POST:'salvar_archivo_comentario']
        }

        '/actualizar_tickets/get_update_by_id'(controller: 'tickets'){
            action = [POST:'get_update_by_id']
        }

        //CREAR TICKETS

        "/crear_ticket_admin/"(controller: 'tickets'){
            action = [GET:'crear_ticket_admin']
        }

        "/crear_ticket_admin/salvar_ticket_admin"(controller: 'tickets'){
            action = [POST: 'salvar_ticket_admin']
        }
        "/crear_ticket_admin/salvar_imagen_ticket_admin"(controller: 'tickets'){
            action = [POST: 'salvar_imagen_ticket_admin']
        }

        "/crear_ticket_admin/enviar_archivos_tickets_admin"(controller: 'tickets'){
            action = [POST:'enviar_archivos_tickets_admin']
        }

//        VER ARCHIVOS TICKETS

        '/ver_archivos_tickets/get_update_by_id'(controller: 'tickets'){
            action = [POST:'get_update_by_id']
        }

        '/ver_archivos_tickets/descargarUpdate'(controller: 'tickets'){
            action = [POST:'descargarUpdate']
        }

        "/ver_archivos_tickets/"(controller: 'tickets'){
            action = [POST: 'ver_archivos_tickets',GET: "ver_archivos_tickets"]
        }

        "/ver_archivos_tickets/get_archivos_by_idtickets"(controller: 'tickets'){
            action = [POST: 'get_archivos_by_idtickets',GET: "get_archivos_by_idtickets"]
        }

        //tickets asignados

        "/tickets_asignados_report"(controller: 'tickets'){
            action = [GET:'tickets_asignados_report']
        }

        "/tickets_asignados_report/get_reporte_tickes_asignados"(controller: 'tickets'){
            action = [POST:'get_reporte_tickes_asignados']
        }

        //TICKETS PERFORMANCE

        "/tickets_performace_report"(controller: 'tickets'){
            action = [GET:'tickets_performace_report']
        }

        "/tickets_performace_report/get_reporte_tickes_performace"(controller: 'tickets'){
            action = [POST:'get_reporte_tickes_performace']
        }


        //TICKETS APARTE

        "/cambiar_idioma"(controller: 'tickets'){
            action = [POST:'cambiar_idioma']
        }


        //TICKETS

        "/ticket/crear_ticket"(controller: 'tickets'){
            action = [POST: 'crear_ticket']
        }
        "/ticket/salvar_imagen_ticket"(controller: 'tickets'){
            action = [POST: 'salvar_imagen_ticket']
        }

        "/ticket/enviar_archivos_tickets_clientes"(controller: 'tickets'){
            action = [POST:'enviar_archivos_tickets_clientes']
        }

        //VER TICKETS

        '/ver_tickets'(controller: 'tickets'){
            action = [GET:'ver_tickets',POST:'ver_tickets']
        }

        '/ver_tickets/get_update_by_id'(controller: 'tickets'){
            action = [POST:'get_update_by_id']
        }

        //MODIFICAR TICKETS COMENTAR O CERRAR

        "/modificar_ticket"(controller: 'tickets'){
            action = [GET: 'modificar_ticket',POST:'modificar_ticket']
        }
        '/modificar_ticket/get_update_by_id'(controller: 'tickets'){
            action = [POST:'get_update_by_id']
        }

        '/modificar_ticket/salvar_comentario_clientes'(controller: 'tickets'){
            action = [POST:'salvar_comentario_clientes']
        }


        '/modificar_ticket/salvar_archivo_update_clientes'(controller: 'tickets'){
            action = [POST:'salvar_archivo_update_clientes']
        }

        '/modificar_ticket/cerrar_ticket_clientes'(controller: 'tickets'){
            action = [POST:'cerrar_ticket_clientes']
        }


        "/ver_tickets_abiertos_cerrado"(controller: 'tickets'){
            action = [POST: 'ver_tickets_abiertos_cerrado',GET:'ver_tickets_abiertos_cerrado']
        }


        "/ver_tickets_abiertos_cerrado/get_tickets_clientes_abiertos_cerrados"(controller: 'tickets'){
            action = [POST: 'get_tickets_clientes_abiertos_cerrados']
        }

        //CAMBIAR CONTRASE;A

        "/cambiar_contrasena"(controller: 'tickets'){
            action = [POST:'cambiar_contrasena']
        }

        //CLIENTES   MIO

        "/logincliente"(controller: 'clientes'){
            action = [GET:'logincliente']
        }

        "/logincliente/iniciar"(controller: 'clientes'){
            action = [POST: 'iniciar']
        }

        "/clientejq"(controller: 'clientes'){
            action = [GET: 'clientejq']
        }

        "/clientejq/cambiarfirstpass"(controller: 'clientes'){
            action = [GET: 'cambiarfirstpass']
        }

        //ORDEN DE TRABAJO


        "/ordentrabajo"(controller: 'ordencliente'){
            action = [GET:'ordentrabajo']
        }

        "/ordentrabajo/salvar_archivo_orden"(controller: 'ordencliente'){
            action = [POST:'salvar_archivo_orden']
        }

        "/ordentrabajo/GuardarOrden"(controller: 'ordencliente'){
            action = [POST:'GuardarOrden']
        }

        "/facturasclientes"(controller: 'reporteclientes'){
            action = [GET:'facturasclientes']
        }

        //REPORTE FACTURA

        '/facturasclientes/get_ver_facturas'(controller: 'reporteclientes'){
            action = [POST:'get_ver_facturas']
        }

        '/facturasclientes/get_reporte_factura_by_id'(controller: 'reporteclientes'){
            action = [POST:'get_reporte_factura_by_id']
        }

        '/facturasclientes/get_enviar_factura_cliente'(controller: 'reporteclientes'){
            action = [POST:'get_enviar_factura_cliente']
        }

        '/facturasclientes/get_reporte_estado_cuenta_by_id'(controller: 'reporteclientes'){
            action = [POST:'get_reporte_estado_cuenta_by_id']
        }

        '/facturasclientes/get_enviar_estado_cuenta_con_correo'(controller: 'reporteclientes'){
            action = [POST:'get_enviar_estado_cuenta_con_correo']
        }

        //ORDENES CLIENTES

        '/ordenesclientes'(controller: 'reporteclientes'){
            action = [GET:'ordenesclientes']
        }

        '/ordenesclientes/get_ver_ordenesclientes'(controller: 'reporteclientes'){
            action = [POST:'get_ver_ordenesclientes']
        }

        '/ordenesclientes/get_archivos_ordenes'(controller: 'reporteclientes'){
            action = [POST:'get_archivos_ordenes']
        }

        "/ordenesclientes/getDescargarArchivo"(controller: 'reporteclientes'){
            action = [GET:'getDescargarArchivo']
        }

//        CMABIAR PASSWORD

        "/cambiarpass"(controller: 'clientes'){
            action = [GET:'cambiarpass']
        }

        "/cambiarpass/cambiarpassword"(controller: 'clientes'){
            action = [POST: 'cambiarpassword']
        }


        "/logoutclientes"(controller: 'clientes'){
            action = [GET: 'logoutclientes']
        }




//        "/"(view:"/index")
//        "500"(view:'/error')
//        "404"(view:'/notFound')
    }
}
