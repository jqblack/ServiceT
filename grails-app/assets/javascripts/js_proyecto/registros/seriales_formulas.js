/**
 * Created by Pabel on 16/6/2016.
 */
function get_tema() {
    return 'material';
}
function ejecutar() {


    crear_tabla();

    $("#activo").parent().addClass('checked');
    $("#activo").prop('checked',true);


    $("#nuevo_producto").click(function () {
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
                serial: $("#serial").val(),
                cliente: $("#cliente").val(),
                imei: $("#imei").val(),
                server: $("#server").val(),
                tipo: $("#tipo").val(),
                activo:activo
            };



            $.ajax({
                type: "POST",
                url: window.location + '/salvar_serial_formula',
                data: vector,
                success: function (data) {
                    var tupla = eval(data);
                    notificacion_si('Datos Guardados Correctamente');
                    nuevo();

                }
            });

        }
        else {
            return;
        }

    });

    $('#tabla_seriales').on('rowclick', function (event) {
        var args = event.args;
        var pocision = args.rowindex;
        var data = $('#tabla_seriales').jqxGrid('getrowdata', pocision);

        // alert(data.f_id);
        $.ajax({
            type: "POST",
            url: window.location + '/get_seriales_formulas_by_id',
            data: {idrecord:data.f_idrecord},
            success: function (data) {
                var tupla = eval(data);

                $("#idrecord").val(tupla[0].f_idrecord);
                $("#serial").val(tupla[0].f_serial);
                $("#cliente").val(tupla[0].f_cliente);
                $("#imei").val(tupla[0].f_imei);
                $("#server").val(tupla[0].f_server);
                $("#tipo").val(tupla[0].f_tipo_proyecto);
                if (String(tupla[0].f_activo)=='true')
                {
                    $("#activo").parent().addClass('checked');
                    $("#activo").prop('checked',true);
                }
                else {
                    $("#activo").parent().removeClass('checked');
                    $("#activo").prop('checked',false);
                }



            }
        });

    });

}

function crear_tabla() {
    $.ajax({
        type: "POST",
        url: window.location + '/get_seriales_formulas',
        //data: ,
        success: function (data) {

            var datas = '';
            var source;

            datas = data;
            source =
            {
                datatype: "json",

                datafields: [
                    {name: 'f_idrecord'},
                    {name: 'f_serial'},
                    {name: 'f_cliente'},
                    {name: 'f_imei'},
                    {name: 'f_tipo'},
                    {name: 'f_activo',type: 'bool'}
                ],
                localdata: datas

            };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_seriales").jqxGrid(
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
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_idrecord', width: "10%"},
                        {text: 'SERIAL', filtertype: 'textbox', datafield: 'f_serial', width: "25%"},
                        {text: 'CLIENTE', filtertype: 'textbox', datafield: 'f_cliente', width: "25%"},
                        {text: 'EMEI', filtertype: 'textbox', datafield: 'f_imei', width: "25%"},
                        {text: 'TIPO', filtertype: 'textbox', datafield: 'f_tipo', width: "15%"},
                        {text: 'ACTIVO', filtertype: 'textbox', datafield: 'f_activo', width: "10%",columntype: 'checkbox'}

                    ]
                });
            $('#tabla_seriales').jqxGrid('hidecolumn', 'f_idrecord');
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
    if ($("#serial").val() == '') {
        notificacion_no('Digite el Serial');
        valor = false;
    }
    else if ($("#cliente").val() == '') {
        notificacion_no('Digite el cliente');
        valor = false;
    }else if ($("#imei").val() == '') {
        notificacion_no('Digite el Imei');
        valor = false;
    }else if ($("#server").val() == '') {
        notificacion_no('Digite el Server');
        valor = false;
    }
    return valor;
}

function nuevo() {

    $("#idrecord").val('');
    $("#serial").val('');
    $("#cliente").val('');
    $("#imei").val('');
    $("#server").val('');
    $("#tipo").val('1');
    $("#activo").parent().add('checked');
    $("#activo").prop('checked',true);

    crear_tabla();
}
