/**
 * Created by Pabel on 16/6/2016.
 */
function get_tema() {
    return 'web';
}
var id_enviar = 0;
function ejecutar() {

    $("#jqxLoader").jqxLoader({ isModal: false, width: 100, height: 60, imagePosition: 'top',rtl: true,text: ""  });

  //  $("#mostrar_factura").click();

    solo_numero("precio");

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


    $("#mostrar_factura").hide();

    // $("#contado").parent().addClass('checked');
    // $("#contado").prop('checked',true);
    //
    // $("#credito").parent().removeClass('checked');
    // $("#credito").prop('checked',false);
    // $("#buscar_cliente").prop('disabled',true);

    $("#contado").parent().removeClass('checked');
    $("#contado").prop('checked', false);

    $("#credito").parent().addClass('checked');
    $("#credito").prop('checked', true);
    $("#buscar_cliente").prop('disabled', false);

    $("#fecha").jqxDateTimeInput({width: '100%', height: '31px', theme: get_tema(), template: 'primary'});
    crear_tabla_clientes();
    crear_tabla();
    crear_tabla_productos();


    $('#contado').change(function () {
        if ($(this).is(":checked")) {
            $("#credito").parent().removeClass('checked');
            $("#credito").prop('checked', false);
            $("#buscar_cliente").prop('disabled', true);
        }
        else {
            $("#credito").parent().addClass('checked');
            $("#credito").prop('checked', true);

            $("#buscar_cliente").prop('disabled', false);
        }

    });


    $('#credito').change(function () {
        if ($(this).is(":checked")) {
            $("#contado").parent().removeClass('checked');
            $("#contado").prop('checked', false);
            $("#buscar_cliente").prop('disabled', false);

        }
        else {
            $("#contado").parent().addClass('checked');
            $("#contado").prop('checked', true);
            $("#buscar_cliente").prop('disabled', true);

        }


    });

    $("#ok_notifificacion_si").click(function () {
        // $("#atras").click();
    });

    $("#nuevo").click(function () {
        // distribuir();
        nuevo();
        // crear_tabla_servicios_contratados();
    });

    $("#cantidad").keydown(function (e) {
        // Allow: backspace, delete, tab, escape, enter and .
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
            // Allow: Ctrl+A
            (e.keyCode == 65 && e.ctrlKey === true) ||
            // Allow: Ctrl+C
            (e.keyCode == 67 && e.ctrlKey === true) ||
            // Allow: Ctrl+X
            (e.keyCode == 88 && e.ctrlKey === true) ||
            // Allow: home, end, left, right
            (e.keyCode >= 35 && e.keyCode <= 39)) {
            // let it happen, don't do anything
            return;
        }
        // Ensure that it is a number and stop the keypress
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });


    $("#salvar").click(function () {

      

        if (validaciones() == true) {

            var json_header = '';
            var json_header_array = {};
            var json_detalles = '';
            var json_detalles_array = {};
            var concepto = '';


            var datainformations = $('#tabla_datos').jqxGrid('getdatainformation');
            var rowscounts = datainformations.rowscount;
            var t = 0;

            var monto = 0;
            var tax = 0;

            var tipo = 'FCO';
            var tipo_de_factura = '1';
            if ($("#credito").is(":checked")) {
                tipo = 'FCR';
                tipo_de_factura = '2'
            }



            for (var i = 0; i < rowscounts; i++) {
                var dataRecord = $("#tabla_datos").jqxGrid('getrowdata', i);

                monto = monto + dataRecord.f_total;
                tax = tax + dataRecord.f_itbis;

                var cant = dataRecord.f_cantidad;

                json_detalles_array.f_id_producto = dataRecord.f_id;
                json_detalles_array.f_tipo_doc = tipo;
                json_detalles_array.f_comentario = dataRecord.f_comentario;
                json_detalles_array.f_precio = parseFloat(dataRecord.f_precio);
                json_detalles_array.f_itbis = parseFloat((dataRecord.f_itbis / cant));
                json_detalles_array.f_cantidad = parseFloat(dataRecord.f_cantidad);


                if (i == 0) {
                    json_detalles = json_detalles + JSON.stringify(json_detalles_array);
                }
                else {
                    json_detalles = json_detalles + ',' + JSON.stringify(json_detalles_array);
                }


            }
            json_detalles = '[' + json_detalles + ']';
            json_detalles = json_detalles.replace('[,', '[');

            var id_cliente = 0;
            if ($("#id_cliente").val()) {
                id_cliente = $("#id_cliente").val();
            }


            var fecha_vencimiento = $('#fecha').jqxDateTimeInput('getText');



            json_header_array.f_tipofactura = tipo;
            json_header_array.f_monto = parseFloat(monto - tax);
            json_header_array.f_tax = parseFloat(tax);
            json_header_array.f_id_cliente = id_cliente;
            json_header_array.f_fecha_vencimiento = fecha_vencimiento;
            json_header_array.f_tipo = tipo_de_factura;
            json_header_array.f_comentario = $("#comentario").val();
            json_header_array.f_moneda = $("#moneda").val();


            json_header = json_header + JSON.stringify(json_header_array);

            var vector = {factura: json_header, detalle: json_detalles};


            // alert(json_detalles);
            // return;

            $("#salvar" ).prop( "disabled", true );
            $.ajax({
                type: "POST",
                url: window.location + '/salvar_facturas',
                data: vector,
                success: function (data) {

                    $("#pdf").html('');
                    if (data != '-1') {


                        var dato = String(data).split("<-*->");

                        $("#pdf").html('<embed src="data:application/pdf;base64,' + dato[1] + '" width="100%" height="600"></embed>');
                        id_enviar = dato[0];

                    }
                    //  var tupla = eval(data);


                    notificacion_si('Datos Guardados Correctamente');
                    nuevo();


                    $("#mostrar_factura").click();
                    // $("#atras").click();
                }
            });

        }
        else {
            return;
        }

    });

    $("#agregar_producto").click(function () {


        var precio_producto = 0;
        var tax = 0;
        var cantidad = 0;
        var total_tax = 0;
        var monto_total = 0;


        var datainformations = $('#tabla_datos').jqxGrid('getdatainformation');
        var rowscounts = datainformations.rowscount;

        if ($("#id_producto").val() == "" && $("#descripcion_producto").val() == "") {
            notificacion_no("Selecicone un prooducto");
            return;
        }
        if (parseFloat($("#cantidad").val()) == parseFloat('0') || $("#cantidad").val() == "") {
            notificacion_no("Digite la Cantidad");
            $("#cantidad").focus();
            return;
        }


        if ($("#precio").val() != '') {
            precio_producto = parseFloat(quitarComas($("#precio").val()));
        }
        if ($("#itbis").val() != '') {
            tax = parseFloat(quitarComas($("#itbis").val()));
        }

        if ($("#cantidad").val() != '') {
            cantidad = parseFloat(quitarComas($("#cantidad").val()));
        }


        total_tax = ( precio_producto * (tax / 100));
        monto_total = monto_total + cantidad * (precio_producto + total_tax);


        var id = hex_md5(new Date());
        $('#tabla_datos').jqxGrid('addrow', rowscounts, {
            f_id: $("#id_producto").val(),
            f_referencia: $("#referencia").val(),
            f_descripcion: $("#descripcion_producto").val(),
            f_cantidad: $("#cantidad").val(),
            f_precio: parseFloat(String($("#precio").val()).replace(',', '')),
            f_itbis: total_tax * cantidad,
            f_total: monto_total,
            f_comentario:$("#comentario_producto").val()
        });


        calcular_totales();
        blanquear_campos();
        $("#cantidad").focus();


    });

    $("#cantidad").keyup(function () {
        calcular();
    })


    $("#cantidad").keyup(function (e) {
        if (e.which == 13) {
            $("#agregar_producto").click();
        }
    });


    $("#a_pagar").keypress(function (e) {
        if (e.which == 13) {
            distribuir();
        }

    });
    $("#a_pagar").focus(function () {
        distribuir();
    });
    $("#a_pagar").blur(function () {
        distribuir();
    });
}


function crear_tabla_productos() {
    $.ajax({
        type: "POST",
        // url: window.location + '/get_productos',
        url: window.location + '/get_productos_activos_no_activos',
        data: {activo: true},
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
                    {name: 'f_version'},
                    {name: 'f_itbis', type: 'bool'},
                    {name: 'f_precio'}
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
                        {
                            text: 'ID',
                            columntype: 'textbox',
                            filtertype: 'textbox',
                            datafield: 'f_id',
                            width: "0%",
                            editable: false
                        },
                        {text: 'Referencia', filtertype: 'textbox', datafield: 'f_referencia', width: "15%"},
                        {text: 'Descripción', filtertype: 'textbox', datafield: 'f_descripcion', width: "52%"},
                        {
                            text: 'IT',
                            columntype: 'checkbox',
                            filtertype: 'textbox',
                            datafield: 'f_itbis',
                            width: "5%",
                            editable: false
                        },
                        {
                            text: 'Precio',
                            filtertype: 'textbox',
                            datafield: 'f_precio',
                            width: "10%",
                            cellsalign: 'right',
                            cellsformat: 'c2'
                        },
                        {
                            text: 'Seleccionar', datafield: '', columntype: 'button', cellsrenderer: function () {
                            return 'Seleccionar';
                        },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_productos").jqxGrid('getrowdata', row);
                                $.ajax({
                                    type: "POST",
                                    url: window.location + '/get_productos_by_id',
                                    data: {idrecord: dataRecord.f_id},
                                    success: function (data) {
                                        var tupla = eval(data);

                                        $("#id_producto").val(tupla[0].f_id);
                                        $("#referencia").val(tupla[0].f_referencia);
                                        $("#descripcion_producto").val(tupla[0].f_descripcion);


                                        if (tupla[0].f_itbis == true) {
                                            $("#itbis").val(ConvertToCurrency($("#itebis_preferencia").val()));
                                        }
                                        else {
                                            $("#itbis").val(ConvertToCurrency('0'));
                                        }

                                        $("#cantidad").val('1');
                                        $("#precio").val(ConvertToCurrency(tupla[0].f_precio));

                                        $("#atras_producto").click();
                                        $("#cantidad").focus();
                                        calcular();

                                    }
                                });
                            }, theme: get_tema(), width: "15%"
                        }

                    ]
                });
            $("#tabla_productos").jqxGrid('hidecolumn', 'f_id');
            $("#tabla_productos").jqxGrid('hidecolumn', 'f_itbis');
        }
    });
}

function blanquear_campos() {
    $("#id_producto").val('');
    $("#referencia").val('');
    $("#descripcion_producto").val('');
    $("#itbis").val('');
    $("#cantidad").val('');
    $("#monto").val('');
    $("#precio").val('');
    $("#comentario_producto").val('');
}
function calcular_totales() {
    var datainformations = $('#tabla_datos').jqxGrid('getdatainformation');
    var rowscounts = datainformations.rowscount;
    var tax = 0;
    var monto = 0;


    for (var i = 0; i < rowscounts; i++) {
        var dataRecord = $("#tabla_datos").jqxGrid('getrowdata', i);

        // tax = tax + dataRecord.f_cantidad * dataRecord.f_itbis;
        tax = tax + dataRecord.f_itbis;
        monto = monto + dataRecord.f_total;
    }

    $("#label_tax").text(" TAX : $" + ConvertToCurrency(tax));
    $("#label_total").text(" TOTAL : $" + ConvertToCurrency(monto));

}


function crear_tabla_clientes() {
    $.ajax({
        type: "POST",
        url: window.location + '/get_clientes_activos_no_activos',
        data: {activo: true},
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
                    {name: 'f_moneda'},
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
                        {
                            text: 'Seleccionar', datafield: '', columntype: 'button', cellsrenderer: function () {
                            return 'Seleccionar';
                        },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_clientes").jqxGrid('getrowdata', row);
                                $("#cliente").val(dataRecord.f_nombre_empresa)
                                $("#id_cliente").val(dataRecord.f_id)
                                $("#email_cliente").val(dataRecord.f_email);
                                $("#correo_alternativo").val(dataRecord.f_email);
                                $("#moneda").val(dataRecord.f_moneda);

                                $("#atras_clientes").click();
                                $("#a_pagar").focus();
                            }, theme: get_tema(), width: "15%"
                        }

                    ]
                });
            $('#tabla_clientes').jqxGrid('hidecolumn', 'f_id');
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
    var datainformations = $('#tabla_datos').jqxGrid('getdatainformation');
    var rowscounts = datainformations.rowscount;

    if ($("#credito").is(":checked")) {
        if ($("#id_cliente").val() == '') {
            notificacion_no('Seleccione un cliente');
            valor = false;
        }
    }
    if (parseInt(rowscounts) == parseInt('0')) {
        notificacion_no('No existen articulos o servicios agregados');
        valor = false;
    }


    return valor;
}

function nuevo() {
    $("#id_cliente").val('');
    $("#cliente").val('');
    blanquear_campos();
    $("#salvar" ).prop( "disabled", false );

    // $("#contado").parent().addClass('checked');
    // $("#contado").prop('checked',true);
    // $("#credito").parent().removeClass('checked');
    // $("#credito").prop('checked',false);
    // $("#buscar_cliente").prop('disabled',true);


    $("#contado").parent().removeClass('checked');
    $("#contado").prop('checked', false);

    $("#credito").parent().addClass('checked');
    $("#credito").prop('checked', true);
    $("#buscar_cliente").prop('disabled', false);
    $("#label_tax").text(" TAX : $" + ConvertToCurrency('0'));
    $("#label_total").text(" TOTAL : $" + ConvertToCurrency('0'));

    $('#tabla_datos').jqxGrid('clear');
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
            {name: 'f_referencia'},
            {name: 'f_descripcion'},
            {name: 'f_cantidad'},
            {name: 'f_precio'},
            {name: 'f_itbis'},
            {name: 'f_total'},
            {name: 'f_comentario'},
            {name: 'f_eliminar'}
        ],
        localdata: datas

    };
    var dataAdapter = new $.jqx.dataAdapter(source);

    $("#tabla_datos").jqxGrid(
        {
            width: '100%',
            source: dataAdapter,
            theme: get_tema(),
            height: '200px',
            // showfilterrow: true,
            //  filterable: true,
            // pageable: true,
            autoheight: false,
            editable: false,

            //localization: getLocalization('de'),
            selectionmode: 'singlerow',
            columns: [
                {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_id', width: "5%"},
                {text: 'COMENTARIO', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_comentario', width: "5%",hidden:true},
                {text: 'Referencia', filtertype: 'textbox', datafield: 'f_referencia', width: "15%"},
                {text: 'Descripcion', filtertype: 'textbox', datafield: 'f_descripcion', width: "15%"},
                {
                    text: 'Cantidad',
                    filtertype: 'textbox',
                    datafield: 'f_cantidad',
                    width: "10%",
                    cellsalign: 'right',
                    cellsformat: 'c2'
                },
                {
                    text: 'Precio',
                    filtertype: 'textbox',
                    datafield: 'f_precio',
                    width: "20%",
                    cellsalign: 'right',
                    cellsformat: 'c2'
                },
                {
                    text: 'Itbis',
                    filtertype: 'textbox',
                    datafield: 'f_itbis',
                    width: "15%",
                    cellsalign: 'right',
                    cellsformat: 'c2'
                },
                {
                    text: 'Total',
                    filtertype: 'textbox',
                    datafield: 'f_total',
                    width: "20%",
                    cellsalign: 'right',
                    cellsformat: 'c2'
                },
                {
                    text: '-', filtertype: 'textbox', datafield: '', columntype: 'button', cellsrenderer: function () {
                    return '-';
                },
                    buttonclick: function (row) {
                        var id = $('#tabla_datos').jqxGrid('getrowid', row);
                        $('#tabla_datos').jqxGrid('deleterow', id);
                        calcular_totales();
                    }, theme: get_tema(), width: "5%"
                }


            ]
        });
    $('#tabla_datos').jqxGrid('hidecolumn', 'f_id');

}


function quitarComas(valor) {
    var dato = String(valor).replace(',', '');
    return dato;

}

function calcular() {

    var precio_producto = 0;
    var tax = 0;
    var cantidad = 0;
    var total_tax = 0;
    var monto_total = 0;

    if ($("#precio").val() != '') {
        precio_producto = parseFloat(quitarComas($("#precio").val()));
    }
    if ($("#itbis").val() != '') {
        tax = parseFloat(quitarComas($("#itbis").val()));
    }

    if ($("#cantidad").val() != '') {
        cantidad = parseFloat(quitarComas($("#cantidad").val()));
    }


    total_tax = ( precio_producto * (tax / 100));
    monto_total = monto_total + cantidad * (precio_producto + total_tax);

    $("#monto").val(ConvertToCurrency(monto_total));


}

function distribuir() {
    var monto = 0;

    if ($("#a_pagar").val() != '' || parseFloat($("#a_pagar").val()) > 0) {
        monto = parseFloat($("#a_pagar").val());
    }
    var datainformations = $('#tabla_datos').jqxGrid('getdatainformation');
    var rowscounts = datainformations.rowscount;
    var t = 0;


    for (var i = 0; i < rowscounts; i++) {
        var dataRecord = $("#tabla_datos").jqxGrid('getrowdata', i);

        if (monto == 0) {
            $("#tabla_datos").jqxGrid('setcellvalue', i, "f_a_pagar", "0");
            $("#tabla_datos").jqxGrid('setcellvalue', i, "f_concepto", "No Acción");
        }
        else {

            if (monto >= parseFloat(dataRecord.f_balance) && monto >= parseFloat(dataRecord.f_balance) - 1) {
                $("#tabla_datos").jqxGrid('setcellvalue', i, "f_a_pagar", dataRecord.f_balance);
                t = parseFloat(dataRecord.f_balance);
                $("#tabla_datos").jqxGrid('setcellvalue', i, "f_concepto", "Saldado");
                monto = monto - t;


            }
            else if (monto < dataRecord.f_balance && monto > 0) {
                $("#tabla_datos").jqxGrid('setcellvalue', i, "f_a_pagar", monto);
                $("#tabla_datos").jqxGrid('setcellvalue', i, "f_concepto", "Abono");
                monto = 0;
            }
        }

        // $("#tabla_datos").jqxGrid('setcellvalue',i, "f_concepto", "New Value");
    }
    calcular_totales();

}
//
// function calcular_totales() {
//
//     var datainformations = $('#tabla_datos').jqxGrid('getdatainformation');
//     var rowscounts = datainformations.rowscount;
//     var t = 0;
//     var a = 0;
//
//     for (var i = 0 ;i < rowscounts;i++) {
//         var dataRecord = $("#tabla_datos").jqxGrid('getrowdata', i);
//         t = t + dataRecord.f_balance;
//         a = a + dataRecord.f;
//     }
//     $("#sald_deudor").val(t);
//     $("#monto_recibo").val(a);
//
// }

function ConvertToCurrency(number) {
    var number = number.toString(),
        dollars = number.split('.')[0],
        cents = (number.split('.')[1] || '') + '00';
    dollars = dollars.split('').reverse().join('')
        .replace(/(\d{3}(?!$))/g, '$1,')
        .split('').reverse().join('');
    return dollars + '.' + cents.slice(0, 2);
    // return '$' +dollars + '.' + cents.slice(0, 2);
}
