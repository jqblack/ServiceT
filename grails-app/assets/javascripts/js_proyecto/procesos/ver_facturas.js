/**
 * Created by Pabel on 8/9/2016.
 */

function get_tema() {
    return 'web';
}
var id_enviar = 0;
function ejecutar() {


    $("#jqxLoader").jqxLoader({rtl: true, width: 100, height: 60, imagePosition: 'top'});


    var options2 = {
        orientation: $('body').hasClass('right-to-left') ? "auto right" : 'auto auto'
    };
    $('#fecha').datepicker(options2);


    $("#boton_enviar").click(function () {
        // Expresion regular para validar el correo
        var regex = /[\w-\.]{2,}@([\w-]{2,}\.)*([\w-]{2,}\.)[\w-]{2,4}/;

        // Se utiliza la funcion test() nativa de JavaScript
        if (regex.test($('#correo_alternativo').val().trim())) {

            $("#boton_enviar").prop('disabled', true);
            $('#jqxLoader').jqxLoader('open');
            $.ajax({
                type: "POST",
                url: window.location + '/get_enviar_facturas_con_correo',
                data: {idrecord: id_enviar, email: $('#correo_alternativo').val().trim()},
                success: function (data) {
                    var dato = data;
                    // $("#pdf").html('');
                    $('#jqxLoader').jqxLoader('close');
                    if (dato == '1') {
                        notificacion_si('Factura Enviada Con Exito');
                        $("#close_modal_actura").click();
                        $('#correo_alternativo').val("");

                    } else if (dato == '-3') {
                        notificacion_no("Esta factura no posee cliente no puede ser enviada");
                    } else if (dato == '-4') {
                        notificacion_no("Error al enviar Factura");
                    }
                    else if (dato == '0') {
                        notificacion_no('Esta Factura ya Fue Enviada');
                    }

                    $("#boton_enviar").prop('disabled', false);
                }
            });
        } else {
            notificacion_no('La direccón de correo no es valida');
            $("#boton_enviar").prop('disabled', false);
            return;
        }


    })













    crear_tabla();

    $('#tabla_facturas').on('rowclick', function (event) {
        // var args = event.args;
        // // row's bound index.
        // var boundIndex = args.rowindex;
        // // row's visible index.
        // var visibleIndex = args.visibleindex;
        // // right click.
        // var rightclick = args.rightclick;
        // // original event.
        // var ev = args.originalEvent;
        //
        // var data = $('#tabla_facturas').jqxGrid('getrowdata', boundIndex);
        //
        // var id = data.f_id;
        //
        //
        // $.ajax({
        //     type: "POST",
        //     url: window.location + '/get_reporte_factura_by_id',
        //     data: {idrecord:data.f_id},
        //     success: function (data) {
        //        var dato = data;
        //        $("#pdf").html('');
        //         if (dato!='-1')
        //         {
        //             $("#pdf").html('<embed src="data:application/pdf;base64,'+dato+'" width="100%" height="600"></embed>')
        //
        //         }
        //
        //     }
        // });

    });

    $("#cerrar_reporte").click(function () {
        $("#pdf").html("");
        $("#cerrar_reporte").hide();
    })

    $("#boton_buscar").click(function () {


        $("#boton_buscar").prop('disabled', true);
        $("#icono_cargando").show();

        if ($("#fecha_inicio").val() == '' && $("#fecha_fin").val() == '' && $("#parametro").val() == '') {
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
            {name: 'f_id'},
            {name: 'f_idcliente'},
            {name: 'f_cliente'},
            {name: 'f_fecha', type: 'date'},
            {name: 'f_vencimiento', type: 'date'},
            {name: 'f_factura'},
            {name: 'f_balance'},
            {name: 'f_pagar', type: 'bool'},
            {name: 'f_ver'},
            {name: 'f_enviar'}
        ],
        localdata: datas

    };

    var dataAdapter = new $.jqx.dataAdapter(source);

    $("#tabla_facturas").jqxGrid(
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
            selectionmode: 'multiplerowsextended',
            columns: [
                {text: 'ID', columntype: 'textbox', datafield: 'f_id', width: "0%", editable: false},
                {text: 'IDcliente', columntype: 'textbox', datafield: 'f_idcliente', width: "0%", editable: false},
                {
                    text: 'Cliente',
                    filtertype: 'textbox',
                    datafield: 'f_cliente',
                    width: "30%",
                    editable: false,
                    filtrable: false
                },
                {text: '#Factura', datafield: 'f_factura', width: "15%", editable: false},
                {
                    text: 'Fecha',
                    filtertype: 'date',
                    cellsalign: 'right',
                    datafield: 'f_fecha',
                    width: "15%",
                    editable: false,
                    cellsformat: 'd'
                },
                {
                    text: 'Balance',
                    filtertype: 'textbox',
                    datafield: 'f_balance',
                    width: "20%",
                    cellsalign: 'right',
                    cellsformat: 'c2',
                    editable: false
                },
                {text: 'Ver', datafield: 'f_ver', columntype: 'button', theme: get_tema(), width: "10%"},
                {
                    text: 'Enviar', datafield: 'f_enviar', columntype: 'button', theme: get_tema(), width: "10%"
                }
            ]/*,
         groups: ['f_cliente']*/
        });
    $('#tabla_facturas').jqxGrid('hidecolumn', 'f_id');
    $('#tabla_facturas').jqxGrid('hidecolumn', 'f_idcliente');

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
        type: "GET",
        url: window.location + '/get_ver_facturas',
        data: {parametro: $("#parametro").val(), f1: f1, f2: f2},
        success: function (data) {


            $("#boton_buscar").prop('disabled', false);
            $("#icono_cargando").hide();


            var datas = '';
            var source;

            datas = data;
            source =
            {
                datatype: "json",

                datafields: [
                    {name: 'f_id'},
                    {name: 'f_idcliente'},
                    {name: 'f_cliente'},
                    {name: 'f_fecha', type: 'date'},
                    {name: 'f_vencimiento', type: 'date'},
                    {name: 'f_comentario'},
                    {name: 'f_factura'},
                    {name: 'f_idmoneda'},
                    {name: 'f_moneda'},
                    {name: 'f_factura'},
                    {name: 'f_balance'},
                    {name: 'f_monto'},
                    {name: 'f_pagar', type: 'bool'},
                    {name: 'f_ver'},
                    {name: 'f_enviar'}
                ],
                localdata: datas

            };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_facturas").jqxGrid(
                {
                    width: '100%',
                    source: dataAdapter,
                    theme: get_tema(),
                    showfilterrow: true,
                    filterable: true,
                    //  pageable: true,
                    autoheight: false,
                    editable: true,
                    sortable: true,
                    // groupable: true,
                    //localization: getLocalization('de'),
                    selectionmode: 'multiplerowsextended',
                    columns: [
                        {
                            text: 'Ver', datafield: 'f_ver', columntype: 'button', cellsrenderer: function () {
                                return 'Ver';
                            },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_facturas").jqxGrid('getrowdata', row);

                                var id = dataRecord.f_id;
                                $('#jqxLoader').jqxLoader('open');
                                $.ajax({
                                    type: "POST",
                                    url: window.location + '/get_reporte_factura_by_id',
                                    data: {idrecord: id},
                                    success: function (data) {
                                        var dato = data;
                                        $("#pdf").html('');
                                        if (dato != '-1') {
                                            $("#pdf").html('<embed src="data:application/pdf;base64,' + dato + '" width="100%" height="600"></embed>')
                                            $('#jqxLoader').jqxLoader('close');
                                            $("#cerrar_reporte").show();

                                        }

                                    }
                                });

                            }, theme: get_tema(), width: "10%"
                        },
                        {text: 'ID', columntype: 'textbox', datafield: 'f_id', width: "0%", editable: false},
                        {
                            text: 'IDcliente',
                            columntype: 'textbox',
                            datafield: 'f_idcliente',
                            width: "0%",
                            editable: false
                        },
                        {text: '#Factura', datafield: 'f_factura', width: "15%", editable: false},


                        {
                            text: 'Cliente',
                            filtertype: 'textbox',
                            datafield: 'f_cliente',
                            width: "30%",
                            editable: false,
                            filtrable: false
                        },
                        {
                            text: 'Fecha',
                            filtertype: 'date',
                            cellsalign: 'right',
                            datafield: 'f_fecha',
                            width: "15%",
                            editable: false,
                            cellsformat: 'dd/MM/yyyy'
                        },
                        {text: 'Comentario', filtertype: 'textbox', datafield: 'f_comentario', width: "30%",editable:false},

                        {text: 'f_idmoneda', filtertype: 'textbox', datafield: 'f_idmoneda', width: "10%",editable:false},
                        {text: 'Modena', filtertype: 'checkedlist', datafield: 'f_moneda', width: "5%",editable:false},
                        {
                            text: 'Monto',
                            filtertype: 'textbox',
                            datafield: 'f_monto',
                            width: "10%",
                            cellsalign: 'right',
                            cellsformat: 'c2',
                            editable: false
                        },{
                            text: 'Balance',
                            filtertype: 'textbox',
                            datafield: 'f_balance',
                            width: "10%",
                            cellsalign: 'right',
                            cellsformat: 'c2',
                            editable: false
                        },
                        {
                            text: 'Duplicar', datafield: 'f_duplicar', columntype: 'button', cellsrenderer: function () {
                            return 'Duplicar';
                        },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_facturas").jqxGrid('getrowdata', row);


                                if (confirm("Desea Duplicar la factura")==false){

                                    return
                                }else{

                                    var idrecord = dataRecord.f_id;
                                    $('#jqxLoader').jqxLoader('open');
                                    $.ajax({
                                        type: "POST",
                                        url: window.location + '/duplicar_factura',
                                        data: {idrecord: idrecord},
                                        success: function (data1) {
                                            // $("#pdf").html('');

                                            var split = String(data1).split("<-*->");
                                            if (data1 !="-1") {
                                                notificacion_si('Factura Diplicada Correctramente');
                                                $.ajax({
                                                    type: "POST",
                                                    url: window.location + '/get_reporte_factura_by_id',
                                                    data: {idrecord: split[0]},
                                                    success: function (data2) {
                                                        var dato2 = data2;
                                                        $("#pdf_factura_enviar").html('');
                                                        if (dato2 != '-1') {
                                                            $("#pdf_factura_enviar").html('<embed src="data:application/pdf;base64,' + dato2 + '" width="100%" height="600"></embed>')
                                                            $('#jqxLoader').jqxLoader('close');
                                                            $("#mostrar_factura").click();
                                                            $("#correo_alternativo").val(split[1]);
                                                            id_enviar = split[0];
                                                        }

                                                    }
                                                });
                                            } else {
                                                notificacion_no("Ocurrio un error al Duplicar la Factura");
                                            }
                                        }
                                    });
                                }
                            }, theme: get_tema(), width: "10%"
                        },
                        {
                            text: 'Enviar', datafield: 'f_enviar', columntype: 'button', cellsrenderer: function () {
                            return 'Enviar';
                        },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_facturas").jqxGrid('getrowdata', row);

                                var id = dataRecord.f_id;
                                var idcliente = dataRecord.f_idcliente;
                                $.ajax({
                                    type: "POST",
                                    url: window.location + '/get_enviar_factura_cliente',
                                    data: {idrecord: id, idcliente: idcliente},
                                    success: function (data) {
                                        var dato = data;
                                        // $("#pdf").html('');
                                        if (dato == '1') {
                                            notificacion_si('Factura Enviada Con Exito');
                                        } else if (dato == '-3') {
                                            notificacion_no("Esta factura no posee cliente no puede ser enviada");
                                        } else if (dato == '-4') {
                                            notificacion_no("Error al enviar Factura");
                                        }
                                        else if (dato == '0') {
                                            notificacion_no('Esta Factura ya Fue Enviada');
                                        }
                                    }
                                });

                            }, theme: get_tema(), width: "10%"
                        }
                    ]
                });
            $('#tabla_facturas').jqxGrid('hidecolumn', 'f_id');
            $('#tabla_facturas').jqxGrid('hidecolumn', 'f_idmoneda');
            $('#tabla_facturas').jqxGrid('hidecolumn', 'f_idcliente');
            // sumar_montos_adeudados();

            // calcular_totales();
        }
    });


    $("#boton_imprimir").click(function () {


        var gridContent = $("#tabla_facturas").jqxGrid('exportdata', 'html');
        var newWindow = window.open('', '', 'width="100%", height="100%"'),
            document = newWindow.document.open(),
            pageContent =
                '<!DOCTYPE html>\n' +
                '<html>\n' +
                '<head>\n' +
                '<meta charset="utf-8" />\n' +
                '<title>Estado Cuentas</title>\n' +
                '</head>\n' +
                '<body>\n' + gridContent + '\n</body>\n</html>';
        document.write(pageContent);
        document.close();
        newWindow.print();
    })

}

function notificacion_si(msj) {
    $("#body_notifificacion_si").html(msj);
    $("#notificacion_si").click();
}
function notificacion_no(msj) {

    $("#body_notifificacion_no").html(msj);
    $("#notificacion_no").click();
}
