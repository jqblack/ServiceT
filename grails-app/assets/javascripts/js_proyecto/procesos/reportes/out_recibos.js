/**
* Created by Pabel on 12/6/2017.
*/
/**
 * Created by Pabel on 8/9/2016.
 */

function get_tema() {
    return 'material';
}
function ejecutar() {

    var options2 = {
        orientation: $('body').hasClass('right-to-left') ? "auto right" : 'auto auto'
    };
    crear_tabla_clientes();
    $('#fecha').datepicker(options2);


    crear_tabla();

    $("#boton_buscar").click(function () {


        $("#boton_buscar").prop('disabled', true);


        if ($("#fecha_inicio").val() == '' && $("#fecha_inicio").val() == '' && $("#parametro").val() == '') {
            notificacion_no("Seleccione por lo menos un parametro de busqueda");
            return;
        } else {

            crear_tabla_parametros();
        }
    })

    $("#parametro").keypress(function (e) {
        if (e.keyCode == 13) {
            $("#boton_buscar").click();
        }

    })


}

function crear_tabla() {


    var datas = '';
    var source;

    datas = datas;
    source =
        {
            datatype: "array",

            datafields: [
                {name: 'f_numero'},
                {name: 'f_fecha'},
                {name: 'f_cliente'},
                {name: 'f_concepto'},
                {name: 'f_monto'}
            ],
            localdata: datas

        };

    var dataAdapter = new $.jqx.dataAdapter(source);

    $("#tabla_recibos").jqxGrid(
        {
            width: '100%',
            source: dataAdapter,
            theme: get_tema(),
            //showfilterrow: true,
            //filterable: true,
            //  pageable: true,
            columnsresize: true,
            autoheight: false,
            editable: true,
            sortable: true,
            // groupable: true,
            //localization: getLocalization('de'),
            selectionmode: 'singlerow',
            columns: [
                {text: 'NUMERO', columntype: 'textbox', datafield: 'f_numero', width: "10%", editable: false},
                {text: 'FECHA', columntype: 'textbox', datafield: 'f_fecha', width: "10%", editable: false},
                {text: 'CLIENTE', columntype: 'textbox', datafield: 'f_cliente', width: "20%", editable: false},
                {text: 'CONCEPTOO', columntype: 'textbox', datafield: 'f_concepto', width: "50%", editable: false},
                {text: 'MONTO', columntype: 'textbox', datafield: 'f_monto', width: "10%", editable: false,cellsalign: 'right',cellsformat: 'c2' }

            ]/*,
         groups: ['f_cliente']*/
        });

    // sumar_montos_adeudados();

    // calcular_totales();


}

function crear_tabla_parametros() {


    var split_f1 = "";
    var f1 = "";

    if ($("#fecha_inicio").val() != '') {
        split_f1 = $("#fecha_inicio").val().split('/');
        f1 = split_f1[2].replace(' ', '') + '/' + split_f1[0].replace(' ', '') + '/' + split_f1[1].replace(' ', '');

    }

    var split_f2 = "";
    var f2 = "";
    if ($("#fecha_fin").val()) {
        split_f2 = $("#fecha_fin").val().split('/');
        f2 = split_f2[2].replace(' ', '') + '/' + split_f2[0].replace(' ', '') + '/' + split_f2[1].replace(' ', '');
    }


    $.ajax({
        type: "POST",
        url: window.location + '/get_out_recibos',
        data: {parametro: $("#id_cliente").val(), f1: f1, f2: f2},
        success: function (data) {


            $("#boton_buscar").prop('disabled', false);

            var datas = '';
            var source;

            datas = data;
            source =
                {
                    datatype: "json",

                    datafields: [
                        {name: 'f_numero'},
                        {name: 'f_fecha'},
                        {name: 'f_cliente'},
                        {name: 'f_concepto'},
                        {name: 'f_monto'}
                    ],
                    localdata: datas

                };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_recibos").jqxGrid(
                {
                    width: '100%',
                    source: dataAdapter,
                    theme: get_tema(),
                    //showfilterrow: true,
                    //filterable: true,
                    //  pageable: true,
                    autoheight: false,
                    editable: true,
                    sortable: true,
                    // groupable: true,
                    //localization: getLocalization('de'),
                    selectionmode: 'singlerow',
                    columns: [
                        {text: 'NUMERO', columntype: 'textbox', datafield: 'f_numero', width: "10%", editable: false},
                        {text: 'FECHA', columntype: 'textbox', datafield: 'f_fecha', width: "10%", editable: false},
                        {text: 'CLIENTE', columntype: 'textbox', datafield: 'f_cliente', width: "20%", editable: false},
                        {text: 'CONCEPTOO', columntype: 'textbox', datafield: 'f_concepto', width: "50%", editable: false},
                        {text: 'MONTO', columntype: 'textbox', datafield: 'f_monto', width: "10%", editable: false,cellsalign: 'right',cellsformat: 'c2' }

                    ]
                });

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



function crear_tabla_clientes() {
    $.ajax({
        type: "GET",
        url: window.location + '/get_clientes',
        // data: 'parametro='+$("#parametro").val(),
        success: function (data) {

            var datas = '';
            var source;

            datas = data;
            source =
                {
                    datatype: "json",

                    datafields: [
                        {name: 'f_id'},
                        {name: 'f_nombre_empresa'},
                        {name: 'f_email'}
                    ],
                    localdata: datas

                };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_clientes").jqxGrid(
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
                        {text: 'Nombre', filtertype: 'textbox', datafield: 'f_nombre_empresa', width: "40%"},
                        {text: 'E-mail', filtertype: 'textbox', datafield: 'f_email', width: "35%"},
                        {text: 'Seleccionar', datafield: '', columntype: 'button', cellsrenderer: function () {
                            return 'Seleccionar';
                        },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_clientes").jqxGrid('getrowdata', row);
                                $("#cliente").val(dataRecord.f_nombre_empresa);
                                $("#id_cliente").val(dataRecord.f_id);
                                $("#atras_clientes").click();
                            }, theme: get_tema(), width: "15%"
                        }

                    ]
                });
            $('#tabla_clientes').jqxGrid('hidecolumn', 'f_id');
        }
    });
}
