/**
 * Created by Pabel on 19/8/2016.
 */
/**
 * Created by Jose Santos on 09/08/2016.
 */

function get_tema() {
    return 'web';
}

function ejecutar() {
    crear_tabla();
    solo_numero("horas_consumidas");



    $("#nueva_track").click(function () {


        var selectedrowindex = $('#tabla_tareas').jqxGrid('selectedrowindex');
        if (selectedrowindex == '-1')
        {
            notificacion_no("Seleccione una tarea..");
            return;
        }

        var dataRecord = $("#tabla_tareas").jqxGrid('getrowdata', selectedrowindex);
        $("#id_tarea_track").val(dataRecord.id);
        $("#tarea_track").val(dataRecord.f_descripcion_tarea);
        // alert(dataRecord.id);
        $("#mostrar_modal_track").click();
    });


    var contextMenu = $("#menu").jqxMenu({ width: 130, height: 29, autoOpenPopup: false, mode: 'popup',theme:get_tema()});
    $("#tabla_tareas").on('contextmenu', function () {
        return false;
    });
    $("#tabla_tareas").on('rowclick', function (event) {
        if (event.args.rightclick) {
            $("#tabla_tareas").jqxGrid('selectrow', event.args.rowindex);
            var scrollTop = $(window).scrollTop();
            var scrollLeft = $(window).scrollLeft();
            contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);
            return false;
        }
    });


    $("#menu").on('itemclick', function (event) {
        var args = event.args;
        var rowindex = $("#tabla_tareas").jqxGrid('getselectedrowindex');
        if ($.trim($(args).text()) == "Nuevo Track") {
            editrow = rowindex;
            var offset = $("#tabla_tareas").offset();
            // get the clicked row's data and initialize the input fields.
            var dataRecord = $("#tabla_tareas").jqxGrid('getrowdata', editrow);
            $("#id_tarea_track").val(dataRecord.id);
            $("#tarea_track").val(dataRecord.f_descripcion_tarea);
            // alert(dataRecord.id);
            $("#mostrar_modal_track").click();
        }
        else {
           return
        }
    });



    $("#salvar").click(function () {
        if(validaciones()==true)
        {
            // alert("Hola");
            var cerrado=false;
            var horas_entrega=0;
            var porciento=0;
            var vector;

            if($("#cerrada").is(":checked"))
            {
                cerrado=true;
            }

            if($("#horas_entrega").val()!='')
            {
                horas_entrega =$("#horas_entrega").val() ;
            }

            if($("#porciento").val()!='')
            {
                porciento =$("#porciento").val() ;
            }


            vector = {
                id: $("#idrecord").val(),
                nombre: $("#nombre").val(),
                fechainicio: $("#fecha_inicio").val(),
                fecha_entrega_programada: $("#fecha_entrega_programada").val(),
                fecha_entrega_real: $("#fecha_entrega_real").val(),
                horas_programadas: $("#horas_programadas").val(),
                horas_entregas: horas_entrega,
                cerrado: cerrado,
                comentario: $("#comentario").val(),
                porciento: porciento,
                proyecto: $("#padre").val(),
                empleado: $("#empleado").val()

            };

            $.ajax({
                type: "Post",
                url: window.location + "/salvar_tareas",
                data: vector,
                success: function (data) {
                    var tupla = eval(data);
                    notificacion_si('Datos Guardados Correctamente');
                    nuevo();
                    $("#atras").click();
                    crear_tabla();
                }
            })
        }


    });

    $("#nueva_tareas").click(function () {
        if ($("#idrecord").val()=='')
        {
            nuevo();
        }

    });
    $("#atras").click(function () {
        nuevo();
    });
    $("#atras_track").click(function () {
        nuevo_track();
    });



    $("#salvar_track").click(function () {
        if(validaciones_track()==true)
        {
            var horas_consumidas=0;
            var vector;

            if($("#horas_consumidas").val()!='')
            {
                horas_consumidas=$("#horas_consumidas").val() ;
            }

            vector = {
                idtarea: $("#id_tarea_track").val(),
                empleado: $("#empleado_track").val(),
                horas_consumidas: horas_consumidas,
                comentario: $("#comentario_track").val(),

            };

            $.ajax({
                type: "Post",
                url: window.location + "/salvar_track",
                data: vector,
                success: function (data) {
                    var tupla = eval(data);
                    notificacion_si('Datos Guardados Correctamente');
                    nuevo_track();
                    $("#atras_track").click();
                    // crear_tabla();
                }
            })
        }


    });



    $('#tabla_tareas').on('rowdoubleclick', function (event) {

        var pocision = args.rowindex;

        var data = $('#tabla_tareas').jqxGrid('getrowdata', pocision);

        $.ajax({
            type: "POST",
            url: window.location + '/get_tareas_by_id',
            data: {idrecord:data.id},
            success: function (data)
            {
                var tupla = eval(data);

                // alert(tupla[0].f_idproyecto_padre);
                $("#idrecord").val(tupla[0].id);

                if (String(tupla[0].f_idproyecto) == 'null' || String(tupla[0].f_idproyecto) == '')
                {
                    $("#padre").val('0');
                }
                else {
                    $("#padre").val(tupla[0].f_idproyecto);
                }
                if (String(tupla[0].f_id_empleado) == 'null' || String(tupla[0].f_id_empleado) == '')
                {
                    $("#empleado").val('0');
                }
                else {
                    $("#empleado").val(tupla[0].f_id_empleado);
                }
                $("#nombre").val(tupla[0].f_descripcion_tarea);


                $("#fecha_inicio").val(tupla[0].f_fecha_inicio);
                $("#fecha_entrega_programada").val(tupla[0].f_fecha_entrega_programada);
                $("#fecha_entrega_real").val(tupla[0].f_fecha_entrega_real);
                $("#porciento").val(tupla[0].f_porciento_cumplimiento);
                $("#comentario").val(tupla[0].f_comentario);
                $("#horas_programadas").val(tupla[0].f_horas_programada);
                $("#horas_entrega").val(tupla[0].f_horas_entrega);
                if (String(tupla[0].f_cerrada)=='true')
                {
                    $("#cerrada").parent().addClass('checked');
                    $("#cerrada").prop('checked',true);
                }
                else {
                    $("#cerrada").parent().removeClass('checked');
                    $("#cerrada").prop('checked',false);
                }


                $("#nueva_tareas").click();


            }
        });

    });
    $("#padre").val('0');
    $("#empleado").val('0');

}

function crear_tabla() {

    $.ajax({
        type: "post",
        url: window.location +"/get_tareas",
        success: function (data) {

            var datas= data;
            var source;

            source=
            {
                datatype: "json",
                dataFields: [
                    {name: "id"},
                    {name: "f_descripcion_tarea"},
                    {name: "f_proyecto"},
                    {name: "f_ultima_actualizacion"},
                    {name: "f_empleado"}
                ],
                localdata: datas
            };

            var dataAdapter = new $.jqx.dataAdapter(source);

            $("#tabla_tareas").jqxGrid(
                {
                    width: '100%',
                    source: dataAdapter,
                    theme: get_tema(),
                    showfilterrow: true,
                    filterable: true,
                    pageable: true,
                    autoheight: false,
                    editable: false,

                    //localization: getLocalization('de'),
                    selectionmode: 'singlerow',
                    columns: [
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'id', width: "10%"},
                        {text: 'Nombre', filtertype: 'textbox', datafield: 'f_descripcion_tarea', width: "30%"},
                        {text: 'Proyecto', filtertype: 'textbox', datafield: 'f_proyecto', width: "30%"},
                        {text: 'Empleado', filtertype: 'textbox', datafield: 'f_empleado', width: "30%"},
                        {text: 'Actualización', filtertype: 'textbox', datafield: 'f_ultima_actualizacion', width: "10%"}

                    ]
                }
            );

             $('#tabla_tareas').jqxGrid('hidecolumn', 'id');
        }

    })


}

function validaciones() {
    var valor = true;
    if ($("#nombre").val() == '') {
        notificacion_no('Digite el Nombre de la Tarea');
        valor = false;
    }
    else if ($("#padre").val()=='0') {

        notificacion_no('Selecione el Proyecto');
        valor = false;
    }

    else if ($("#empleado").val()=='0') {

        notificacion_no('Selecione el Empleado');
        valor = false;
    }
    else if ($("#fecha_inicio").val()=='') {

        notificacion_no('Selecione la Fecha Inicio');
        valor = false;
    }
    else if ($("#fecha_entrega_programada").val()=='') {

        notificacion_no('Selecione la Fecha de Entrega Programada');
        valor = false;
    }

    // else if ($("#fecha_entrega_real").val()=='') {
    //
    //     notificacion_no('Selecione la fecha ');
    //     valor = false;
    // }
    else if ($("#horas_programadas").val()=='' || $("#horas_programadas").val()=='0') {

        notificacion_no('Digite las Horas Programadas');
        valor = false;
    }

    return valor;
}


function validaciones_track() {
    var valor = true;
    if ($("#empleado_track").val() == '0') {
        notificacion_no('Seleccione el Empleado');
        valor = false;
    }
    else if ($("#horas_consumidas").val()=='0' || $("#horas_consumidas").val()=='') {

        notificacion_no('Digite las Horas Consumidas');
        valor = false;
    }

    else if ($("#comentario_track").val()=='') {

        notificacion_no('Digite un Comentario');
        valor = false;
    }

    return valor;
}

function notificacion_si(msj) {
    $("#body_notifificacion_si").html(msj);
    $("#notificacion_si").click();
}
function notificacion_no(msj) {

    $("#body_notifificacion_no").html(msj);
    $("#notificacion_no").click();
}

function nuevo() {

    $("#idrecord").val('');
    $("#nombre").val('');
    $("#padre").val('0');
    $("#empleado").val('0');
    $("#fecha_inicio").val('');
    $("#fecha_entrega_programada").val('');
    $("#fecha_entrega_real").val('');
    $("#horas_programadas").val('');
    $("#porciento").val('');
    $("#horas_entrega").val('');
    $("#comentario").val('');


    $("#cerrada").parent().removeClass('checked');
    $("#cerrada").prop('checked',false);


}
function nuevo_track() {

    $("#id_tarea_track").val('');
    $("#empleado_track").val('0');
    $("#horas_consumidas").val('0');
    $("#comentario_track").val('');

}
