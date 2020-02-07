/**
 * Created by Jose Santos on 09/08/2016.
 */

function get_tema() {
    return 'web';
}

function ejecutar() {
    crear_tabla();
    
    $("#salvar").click(function () {
        if(validaciones()==true)
        {
            // alert("Hola");
            var congelado=false;
            var vector;
            
            if($("#congelado").is(":checked"))
            {
                congelado=true;
            }


            vector = {
                id: $("#idrecord").val(),
                nombre: $("#nombre").val(),
                fechaInicio: $("#fecha_inicio_propuesta").val(),
                fechaFinal: $("#fecha_fin_propuesta").val(),
                fechaRealInicio: $("#fecha_inicio_real").val(),
                fechaRealFinal: $("#fecha_fin_real").val(),
                empleado: $("#empleado").val(),
                congelado: congelado,
                comentario: $("#comentario").val(),
                porcentaje: $("#porciento").val(),
                padre: $("#padre").val()

            };

            $("#salvar" ).prop( "disabled", true );
            $.ajax({
                type: "Post",
                url: window.location + "/salvarProyectos",
                data: vector,
                success: function (data) {
                    var tupla = eval(data);
                    notificacion_si('Datos Guardados Correctamente');
                    nuevo();
                    $("#atras").click();
                    crear_tabla();

                    $("#salvar" ).prop( "disabled", false );
                }
            })
        }


    });

    $("#nuevo_proyecto").click(function () {
        if ($("#idrecord").val()=='')
        {
            nuevo();
        }

    });
    $("#atras").click(function () {
       nuevo();
    });



    $('#tabla_proyectos').on('rowdoubleclick', function (event) {

        var args = event.args;
        var pocision = args.rowindex;
        
        var data = $('#tabla_proyectos').jqxGrid('getrowdata', pocision);

        $.ajax({
            type: "POST",
            url: window.location + '/get_proyectos_by_id',
            data: {idrecord:data.f_idproyecto},
            success: function (data)
            {
                var tupla = eval(data);

                // alert(tupla[0].f_idproyecto_padre);
                $("#idrecord").val(tupla[0].f_idproyecto);

                if (String(tupla[0].f_idproyecto_padre) == 'null' || String(tupla[0].f_idproyecto_padre) == '')
                {
                    $("#padre").val('0');
                }
                else {
                    $("#padre").val(tupla[0].f_idproyecto_padre);
                }
                if (String(tupla[0].f_idempleado) == 'null' || String(tupla[0].f_idempleado) == '')
                {
                    $("#empleado").val('0');
                }
                else {
                    $("#empleado").val(tupla[0].f_idempleado);
                }
                $("#nombre").val(tupla[0].f_nombre);


                $("#fecha_inicio_propuesta").val(tupla[0].f_fecha_inicio);
                $("#fecha_fin_propuesta").val(tupla[0].f_fecha_final);
                $("#fecha_inicio_real").val(tupla[0].f_fecha_real_inicio);
                $("#fecha_fin_real").val(tupla[0].f_fecha_real_final);
                $("#porciento").val(tupla[0].f_porcentaje);
                $("#comentario").val(tupla[0].f_comentario);



                if (String(tupla[0].f_congelado)=='true')
                {
                    $("#congelado").parent().addClass('checked');
                    $("#congelado").prop('checked',true);
                }
                else {
                    $("#congelado").parent().removeClass('checked');
                    $("#congelado").prop('checked',false);
                }

             
                $("#nuevo_proyecto").click();


            }
        });

    });
    $("#padre").val('0');
    $("#empleado").val('0');

}

function crear_tabla() {

    $.ajax({
        type: "post",
        url: window.location +"/getProyectos",
        success: function (data) {

            var datas= data;
            var source;

            source=
            {
                datatype: "json",
                dataFields: [
                    {name: "f_idproyecto"},
                    {name: "f_nombre"},
                    {name: "f_fecha_inicio"},
                    {name: "f_comentario"}
                ],
                localdata: datas
            };

            var dataAdapter = new $.jqx.dataAdapter(source);

            $("#tabla_proyectos").jqxGrid(
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
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_idproyecto', width: "10%"},
                        {text: 'Nombre', filtertype: 'textbox', datafield: 'f_nombre', width: "50%"},
                        {text: 'fecha', filtertype: 'textbox', datafield: 'f_fecha_inicio', width: "20%"},
                        {text: 'comentario', filtertype: 'textbox', datafield: 'f_comentario', width: "20%"}

                    ]
                }
            )
        }

    })

}

function validaciones() {
    var valor = true;
    if ($("#nombre").val() == '') {
        notificacion_no('Digite el Nombre del Proyecto');
        valor = false;
    }

    else if ($("#empleado").val()=='0') {

        notificacion_no('Selecione el empleado');
        valor = false;
    }
    else if ($("#fecha_inicio_propuesta").val()=='') {

        notificacion_no('Selecione la fecha inicial propuesta');
        valor = false;
    }
    else if ($("#fecha_fin_propuesta").val()=='') {

        notificacion_no('Selecione la fecha final propuesta');
        valor = false;
    }

    // else if ($("#fecha_inicio_real").val()=='') {
    //
    //     notificacion_no('Selecione la fecha inicial real');
    //     valor = false;
    // }
    // else if ($("#fecha_fin_real").val()=='') {
    //
    //     notificacion_no('Selecione la fecha final real');
    //     valor = false;
    // }

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
    $("#fecha_inicio_propuesta").val('');
    $("#fecha_fin_propuesta").val('');
    $("#fecha_inicio_real").val('');
    $("#fecha_fin_real").val('');
    $("#porciento").val('');
    $("#comentario").val('');

    
    $("#congelado").parent().removeClass('checked');
    $("#congelado").prop('checked',false);
  

}
