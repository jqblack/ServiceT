/**
 * Created by Pabel on 16/6/2016.
 */
function get_tema() {
    return 'material';
}

var tipo_moneda_recibo=1;
function ejecutar() {


    crear_tabla();


    $("#ok_notifificacion_si").click(function () {
       // $("#atras").click();
    });
    //  $("#pagar_todo").click(function () {
    //      var datainformations = $('#tabla_pagos').jqxGrid('getdatainformation');
    //      var rowscounts = datainformations.rowscount;
    //      var t =0;
    //      for (var i = 0 ;i < rowscounts;i++) {
    //           var dataRecord = $("#tabla_pagos").jqxGrid('getrowdata', i);
    //          $("#tabla_pagos").jqxGrid('setcellvalue',i, "f_pagar", true);
    //           t = t+ parseFloat(dataRecord.f_balance);
    //
    //      }
    //      $("#texto_monto").html('$<strong>'+ConvertToCurrency(t)+'</strong>');
    //
    //
    // });

    $("#marcar_rd").click(function () {
        tipo_moneda_recibo =1;

        var datainformations = $('#tabla_pagos').jqxGrid('getdatainformation');
        var rowscounts = datainformations.rowscount;
        var t =0;
        $("#desmarcar_todo").click();

        for (var i = 0 ;i < rowscounts;i++) {
            var dataRecord = $("#tabla_pagos").jqxGrid('getrowdata', i);
            if (String(dataRecord.f_idmoneda)=='1')
            {
                $("#tabla_pagos").jqxGrid('setcellvalue',i, "f_pagar", true);
                t = t+ parseFloat(dataRecord.f_balance);
            }
        }
        $("#texto_monto").html('$<strong>'+ConvertToCurrency(t)+'</strong>');
        $("#texto_monto_pagar_us").html('$<strong>'+ConvertToCurrency(0)+'</strong>');



    });

    $("#marcar_us").click(function () {
        tipo_moneda_recibo =2;

        var datainformations = $('#tabla_pagos').jqxGrid('getdatainformation');
        var rowscounts = datainformations.rowscount;
        var t =0;
        $("#desmarcar_todo").click();

        for (var i = 0 ;i < rowscounts;i++) {
            var dataRecord = $("#tabla_pagos").jqxGrid('getrowdata', i);
            if (String(dataRecord.f_idmoneda)=='2')
            {
                $("#tabla_pagos").jqxGrid('setcellvalue',i, "f_pagar", true);
                t = t+ parseFloat(dataRecord.f_balance);
            }
        }
        $("#texto_monto_pagar_us").html('$<strong>'+ConvertToCurrency(t)+'</strong>');
        $("#texto_monto").html('$<strong>'+ConvertToCurrency(0)+'</strong>');


    });

    $("#desmarcar_todo").click(function () {
        var datainformations = $('#tabla_pagos').jqxGrid('getdatainformation');
        var rowscounts = datainformations.rowscount;
        var t =0;
        for (var i = 0 ;i < rowscounts;i++) {
            var dataRecord = $("#tabla_pagos").jqxGrid('getrowdata', i);
            $("#tabla_pagos").jqxGrid('setcellvalue',i, "f_pagar", false);


        }
        $("#texto_monto").html('$<strong>'+ConvertToCurrency(t)+'</strong>');
        $("#texto_monto_pagar_us").html('$<strong>'+ConvertToCurrency(t)+'</strong>');


    });



    $("#recargar").click(function () {
        crear_tabla();
    });

    $("#salvar").click(function () {




        if (validaciones() == true) {


            var json_detalles='';
            var json_detalles_array={};

            var datainformations = $('#tabla_pagos').jqxGrid('getdatainformation');
            var rowscounts = datainformations.rowscount;
            var t = 0;




            for (var i = 0 ;i < rowscounts;i++) {
                var dataRecord = $("#tabla_pagos").jqxGrid('getrowdata', i);
                if (dataRecord.f_pagar == true)
                {

                    json_detalles_array.f_id = dataRecord.f_id;
                    json_detalles_array.f_cliente = dataRecord.f_cliente;
                    json_detalles_array.f_vencimiento = dataRecord.f_vencimiento;
                    json_detalles_array.f_factura = dataRecord.f_factura;
                    json_detalles_array.f_balance = dataRecord.f_balance;
                    json_detalles_array.f_pagar = dataRecord.f_pagar;


                    if (i==0)
                    {
                        json_detalles=json_detalles+JSON.stringify(json_detalles_array);
                    }
                    else
                    {
                        json_detalles=json_detalles+','+JSON.stringify(json_detalles_array);
                    }
                }

            }
            json_detalles = '['+json_detalles+']';
            json_detalles = json_detalles.replace('[,','[');


            // var data = $("#tabla_pagos").jqxGrid('exportdata', 'json');

            $.ajax({
                type: "POST",
                url: window.location + '/salvar_pagos_generales',
                data: {json:json_detalles,tipo_moneda_recibo:tipo_moneda_recibo},
                success: function (data) {
                    var tupla = eval(data);
                    notificacion_si('Datos Guardados Correctamente');
                    nuevo();
                    // $("#atras").click();
                }
            });

        }

    });




}


function sumar_montos_adeudados() {

    var datainformations = $('#tabla_pagos').jqxGrid('getdatainformation');
    var rowscounts = datainformations.rowscount;
    var t = 0;
    var t_us = 0;

    for (var i = 0 ;i < rowscounts;i++) {
        var dataRecord = $("#tabla_pagos").jqxGrid('getrowdata', i);

        if (String(dataRecord.f_idmoneda)=='1')
        {
            t = t+ parseFloat(dataRecord.f_balance);
        }else if (String(dataRecord.f_idmoneda)=='2'){
            t_us = t_us+ parseFloat(dataRecord.f_balance);

        }else {

        }

    }
    $("#texto_monto_adeudado").html('$<strong>'+ConvertToCurrency(t)+'</strong>');
    $("#texto_monto_adeudado_us").html('$<strong>'+ConvertToCurrency(t_us)+'</strong>');

}

function crear_tabla() {
    $.ajax({
        type: "GET",
        url: window.location + '/get_facturas_pagos_generales',
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
                    {name: 'f_id_cliente'},
                    {name: 'f_idfactura'},
                    {name: 'f_cliente'},
                    {name: 'f_factura'},
                    {name: 'f_fecha'},
                    {name: 'f_vencimiento'},
                    {name: 'f_comentario'},
                    {name: 'f_idmoneda'},
                    {name: 'f_monto'},
                    {name: 'f_moneda'},
                    {name: 'f_balance'},
                    {name: 'f_pagar',type: 'bool'},
                    {name: 'f_enviar'}
                ],
                localdata: datas

            };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_pagos").jqxGrid({
                    width: '100%',
                    source: dataAdapter,
                    theme: get_tema(),
                  //showfilterrow: true,
                    // filterable: true,
                  //  pageable: true,
                    columnsresize: true,
                    autoheight: true,
                    editable: true,
                    groupable: true,
                    sortable: true,

                    //localization: getLocalization('de'),
                    selectionmode: 'singlerow',
                    columns: [
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_id', width: "0%",editable:false},
                        {text: 'Pagar', filtertype: 'checkbox', datafield: 'f_pagar', width: "5%",columntype: 'checkbox',cellvaluechanging:function (row,datafield, columntype, oldvalue, newvalue) {
                            // var dataRecord = $("#tabla_pagos").jqxGrid('getrowdata',row);
                            var t = $("#texto_monto").text();
                            var t_us = $("#texto_monto_pagar_us").text();
                            if(newvalue==true)
                            {
                                var dataRecord = $("#tabla_pagos").jqxGrid('getrowdata',row);
                                var idmonerda = dataRecord.f_idmoneda;
                                if (String(idmonerda) == '1')
                                {
                                    tipo_moneda_recibo =1;
                                    t = parseFloat(t.substr(1,t.length).replace(',',''))+parseFloat(dataRecord.f_balance);
                                    $("#texto_monto").html('$<strong>'+ConvertToCurrency(t)+'</strong>');
                                    cambiar_tipo_moneda(idmonerda);
                                }else  if (String(idmonerda) == '2')
                                {
                                    tipo_moneda_recibo =2;
                                    t_us = parseFloat(t_us.substr(1,t_us.length).replace(',',''))+parseFloat(dataRecord.f_balance);
                                    $("#texto_monto_pagar_us").html('$<strong>'+ConvertToCurrency(t_us)+'</strong>');
                                    cambiar_tipo_moneda(idmonerda);
                                }else  if (String(idmonerda) == '3')
                                {

                                }
                            }
                            else {
                                var dataRecord = $("#tabla_pagos").jqxGrid('getrowdata',row);
                                var idmonerda = dataRecord.f_idmoneda;

                                if (String(idmonerda) == '1')
                                {
                                    t = parseFloat(t.substr(1,t.length).replace(',',''))-parseFloat(dataRecord.f_balance);
                                    $("#texto_monto").html('$<strong>'+ConvertToCurrency(t)+'</strong>');
                                    cambiar_tipo_moneda(idmonerda);
                                }else  if (String(idmonerda) == '2')
                                {
                                    t_us = parseFloat(t_us.substr(1,t_us.length).replace(',',''))-parseFloat(dataRecord.f_balance);
                                    $("#texto_monto_pagar_us").html('$<strong>'+ConvertToCurrency(t_us)+'</strong>');
                                    cambiar_tipo_moneda(idmonerda);
                                }else  if (String(idmonerda) == '3')
                                {

                                }
                            }

                        }},
                        {text: '#Factura', filtertype: 'textbox', datafield: 'f_factura', width: "10%",editable:false},
                        {text: 'Cliente', filtertype: 'textbox', datafield: 'f_cliente', width: "20%",editable:false},
                        {text: 'Fecha', filtertype: 'textbox', datafield: 'f_fecha', width: "10%",editable:false},
                        {text: 'Vencimiento', filtertype: 'textbox', datafield: 'f_vencimiento', width: "10%",editable:false},
                        {text: 'f_idmoneda', filtertype: 'textbox', datafield: 'f_idmoneda', width: "10%",editable:false},
                        {text: 'Modena', filtertype: 'textbox', datafield: 'f_moneda', width: "5%",editable:false},
                        {text: 'Monto', filtertype: 'textbox', datafield: 'f_monto', width: "10%",cellsalign: 'right',cellsformat: 'c2',editable:false},
                        {text: 'Balance', filtertype: 'textbox', datafield: 'f_balance', width: "10%",cellsalign: 'right',cellsformat: 'c2',editable:false},
                        {text: 'Comentario', filtertype: 'textbox', datafield: 'f_comentario', width: "10%",editable:false},
                        {
                            text: 'Enviar', datafield: 'f_enviar', columntype: 'button', cellsrenderer: function () {
                            return 'Enviar';
                        },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_pagos").jqxGrid('getrowdata', row);

                                var id = dataRecord.f_idfactura;
                                var idcliente = dataRecord.f_id_cliente;


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
                    ],
                    groups: ['f_moneda']
                });
             $('#tabla_pagos').jqxGrid('hidecolumn', 'f_id');
             $('#tabla_pagos').jqxGrid('hidecolumn', 'f_idmoneda');
            sumar_montos_adeudados();

            // calcular_totales();
        }
    });



}
function ConvertToCurrency(number) {
    var number = number.toString(),
        dollars = number.split('.')[0],
        cents = (number.split('.')[1] || '') +'00';
    dollars = dollars.split('').reverse().join('')
        .replace(/(\d{3}(?!$))/g, '$1,')
        .split('').reverse().join('');
    return dollars + '.' + cents.slice(0, 2);
    // return '$' +dollars + '.' + cents.slice(0, 2);
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
    var valor = false;

        var datainformations = $('#tabla_pagos').jqxGrid('getdatainformation');
        var rowscounts = datainformations.rowscount;

        for (var i = 0 ;i < rowscounts;i++) {
            var dataRecord = $("#tabla_pagos").jqxGrid('getrowdata', i);
            if (dataRecord.f_pagar==true)
            {
                valor = true;
            }
        }

    if (valor==false)
    {
        notificacion_no('No se a Seleccionado Ninguna Factura Para Pagar')
    }
    return valor;
}


function nuevo() {
    $("#recargar").click();
    $("#texto_monto").html('$<strong>'+ConvertToCurrency('0')+'</strong>');

}

function cambiar_tipo_moneda(tipo) {

    var datainformations = $('#tabla_pagos').jqxGrid('getdatainformation');
    var rowscounts = datainformations.rowscount;
    var t = 0;

    if (String(tipo) == '1') {
        tipo_moneda_recibo =1;
        for (var i = 0; i < rowscounts; i++) {

            var dataRecord = $("#tabla_pagos").jqxGrid('getrowdata', i);

            if (String(dataRecord.f_idmoneda) == '2' || String(dataRecord.f_idmoneda) == '3') {
                $("#tabla_pagos").jqxGrid('setcellvalue', i, "f_pagar", false);
            }

        }
        $("#texto_monto_pagar_us").html('$<strong>' + ConvertToCurrency(0) + '</strong>');


    } else if (String(tipo) == '2') {
        tipo_moneda_recibo =2;
        for (var i = 0; i < rowscounts; i++) {

            var dataRecord = $("#tabla_pagos").jqxGrid('getrowdata', i);

            if (String(dataRecord.f_idmoneda) == '1' || String(dataRecord.f_idmoneda) == '3') {
                $("#tabla_pagos").jqxGrid('setcellvalue', i, "f_pagar", false);
            }

        }
        $("#texto_monto").html('$<strong>' + ConvertToCurrency(0) + '</strong>');


    } else if (String(tipo) == '3') {
        tipo_moneda_recibo =3;
        for (var i = 0; i < rowscounts; i++) {

            var dataRecord = $("#tabla_pagos").jqxGrid('getrowdata', i);


            if (String(dataRecord.f_idmoneda) == '1' || String(dataRecord.f_idmoneda) == '2') {
                $("#tabla_pagos").jqxGrid('setcellvalue', i, "f_pagar", false);
            }
        }

    }
    //$("#texto_monto").html('$<strong>' + ConvertToCurrency(t) + '</strong>');


}
