/**
 * Created by Pabel on 16/6/2016.
 */
function get_tema() {
    return 'material';
}
function ejecutar() {

    crear_tabla();

    $("#mostrar_todos").click(function () {
        $.ajax({
            type: "GET",
            url: window.location + '/get_productos',
           // data: {activo:true},
            success: function (data) {

                var datas = '';
                var source;

                datas = data;
                source =
                {
                    datatype: "json",

                    datafields: [
                        {name: 'f_id'},
                        {name: 'f_referencia'},
                        {name: 'f_descripcion'},
                        {name: 'f_version'}
                    ],
                    localdata: datas

                };


                var dataAdapter = new $.jqx.dataAdapter(source);


                $("#tabla_productos").jqxGrid(
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
                            {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_id', width: "10%"},
                            {text: 'Referencia', filtertype: 'textbox', datafield: 'f_referencia', width: "10%"},
                            {text: 'Descripción', filtertype: 'textbox', datafield: 'f_descripcion', width: "45%"},
                            {text: 'Versión', filtertype: 'textbox', datafield: 'f_version', width: "45%"}

                        ]
                    });
                $('#tabla_productos').jqxGrid('hidecolumn', 'f_id');
            }
        });
    });

    $("#solo_activos").click(function () {
      crear_tabla();
    });


    $("#nuevo_cliente").click(function () {
        if ($("#idrecord").val()=='')
        {
            nuevo();
        }

    });

    $("#atras").click(function () {
        nuevo();
        crear_tabla();
    });

    $("#salvar").click(function () {




        if (validaciones() == true) {

            var servicio = 'false';
            var genera_serial = 'false';
            var tiene_itbis = 'false';
            var activo = 'false';
            var cantidad = 0;



            if ($("#es_servicio").is(':checked')) {
                servicio = 'true';
            }
            if ($("#genera_serial").is(':checked')) {
                genera_serial = 'true';
            }
            if ($("#tiene_itebis").is(':checked')) {
                tiene_itbis = 'true';
            }
            if ($("#activo").is(':checked')) {
                activo = 'true';
            }
            if ($("#cantidad").val()!='') {
                cantidad = $("#cantidad").val();
            }



            var vector = {
                idrecord: $("#idrecord").val(),
                referencia: $("#referencia").val(),
                descripcion: $("#descripcion").val(),
                version: $("#version").val(),
                sistema: $("#sistema").val(),
                precio: $("#precio").val(),
                cantidad: cantidad,
                servicio: servicio,
                genera_serial: genera_serial,
                tiene_itbis:tiene_itbis,
                activo:activo
            };

            var v ='';v = v + vector.idrecord+',';
            v = v + vector.referencia+',';
            v = v + vector.descripcion+',';
            v = v + vector.version+',';
            v = v + vector.sistema+',';
            v = v + vector.precio+',';
            v = v + vector.cantidad+',';
            v = v + vector.servicio+',';
            v = v + vector.genera_serial+',';
            v = v + vector.activo+',';
            v = v + vector.tiene_itbis;

          //  alert(v);


            $.ajax({
                type: "POST",
                url: window.location + '/salvar_productos',
                data: vector,
                success: function (data) {
                    var tupla = eval(data);
                    notificacion_si('Datos Guardados Correctamente');
                    nuevo();
                    $("#atras").click();
                }
            });

        }
        else {
            return;
        }

    });

    $('#tabla_productos').on('rowdoubleclick', function (event) {
        var args = event.args;
        var pocision = args.rowindex;
        var data = $('#tabla_productos').jqxGrid('getrowdata', pocision);

        // alert(data.f_id);
        $.ajax({
            type: "POST",
            url: window.location + '/get_productos_by_id',
            data: {idrecord:data.f_id},
            success: function (data) {
                var tupla = eval(data);

                $("#idrecord").val(tupla[0].f_id);
                $("#referencia").val(tupla[0].f_referencia);
                $("#descripcion").val(tupla[0].f_descripcion);
                $("#cantidad").val(tupla[0].f_cantidad);
                $("#precio").val(tupla[0].f_precio);
                $("#sistema").val(tupla[0].f_sistema);
                $("#version").val(tupla[0].f_version);

                if (String(tupla[0].f_servicio)=='true')
                {
                    $("#es_servicio").parent().addClass('checked');
                    $("#es_servicio").prop('checked',true);
                }
                else {
                    $("#es_servicio").parent().removeClass('checked');
                    $("#es_servicio").prop('checked',false);
                }
                if (String(tupla[0].f_genera_serial)=='true')
                {
                    $("#genera_serial").parent().addClass('checked');
                    $("#genera_serial").prop('checked',true);
                }
                else {
                    $("#genera_serial").parent().removeClass('checked');
                    $("#genera_serial").prop('checked',false);
                }
                // $("#retiene_isr").prop('checked', tupla[0].f_retiene_isr);
                if (String(tupla[0].f_itbis)=='true')
                {
                    $("#tiene_itebis").parent().addClass('checked');
                    $("#tiene_itebis").prop('checked',true);
                }
                else {
                    $("#tiene_itebis").parent().removeClass('checked');
                    $("#tiene_itebis").prop('checked',false);
                }

                if (String(tupla[0].f_activo)=='true')
                {
                    $("#activo").parent().addClass('checked');
                    $("#activo").prop('checked',true);
                }
                else {
                    $("#activo").parent().removeClass('checked');
                    $("#activo").prop('checked',false);
                }
                $("#nuevo_producto").click();


            }
        });

    });

}

function crear_tabla() {
    $.ajax({
        type: "POST",
        url: window.location + '/get_productos_activos_no_activos',
        data: {activo:true},
        success: function (data) {

            var datas = '';
            var source;

            datas = data;
            source =
            {
                datatype: "json",

                datafields: [
                    {name: 'f_id'},
                    {name: 'f_referencia'},
                    {name: 'f_descripcion'},
                    {name: 'f_version'}
                ],
                localdata: datas

            };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_productos").jqxGrid(
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
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_id', width: "10%"},
                        {text: 'Referencia', filtertype: 'textbox', datafield: 'f_referencia', width: "10%"},
                        {text: 'Descripción', filtertype: 'textbox', datafield: 'f_descripcion', width: "45%"},
                        {text: 'Versión', filtertype: 'textbox', datafield: 'f_version', width: "45%"}

                    ]
                });
            $('#tabla_productos').jqxGrid('hidecolumn', 'f_id');
        }
    });
}

function notificacion_si(msj) {
    $("#body_notifificacion_si").html(msj);
    $("#notificacion_si").click();
}
function notificacion_no(msj) {

    $("#body_notifificacion_no").html(msj);
    $("#notificacion_no").click();
}

function validaciones() {
    var valor = true;
    if ($("#referencia").val() == '') {
        notificacion_no('Digite la Referencia');
        valor = false;
    }
    else if ($("#descripcion").val() == '') {
        notificacion_no('Digite la descripción');
        valor = false;
    }
    else if ($("#precio").val() == '' || parseInt($("#precio").val())==parseInt(0)) {
        notificacion_no('Digite el Precio');
        valor = false;
    }

    return valor;
}

function nuevo() {
    $("#idrecord").val('');
    $("#referencia").val('');
    $("#description").val('');
    $("#version").val('');
    $("#sistema").val('');
    $("#precio").val('0');
    $("#cantidad").val('0');
    $("#es_servicio").parent().removeClass('checked');
    $("#es_servicio").prop('checked',false);
    $("#genera_serial").parent().removeClass('checked');
    $("#genera_serial").prop('checked',false);
    $("#tiene_itebis").parent().removeClass('checked');
    $("#tiene_itebis").prop('checked',false);
    $("#activo").parent().add('checked');
    $("#activo").prop('checked',true);
}