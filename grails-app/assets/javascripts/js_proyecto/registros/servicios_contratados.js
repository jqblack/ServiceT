/**
 * Created by Pabel on 16/6/2016.
 */
function get_tema() {
    return 'web';
}
function ejecutar() {


    crear_tabla_clientes();
    crear_tabla_productos();
    crear_tabla_referentes();
    crear_tabla_servicios_contratados();

    $("#activo").parent().addClass('checked');
    $("#activo").prop('checked',true);

    $("#cobrable").parent().addClass('checked');
    $("#cobrable").prop('checked',true);

    $("#variable").parent().removeClass('checked');
    $("#variable").prop('checked',false);

    $("#enviar").parent().addClass('checked');
    $("#enviar").prop('checked',true);

    $("#ok_notifificacion_si").click(function () {
       // $("#atras").click();
    });
    $("#nuevo_servicio").click(function () {
        if ($("#idrecord").val()=='')
        {
            nuevo();
        }

    });

    $("#atras").click(function () {
        nuevo();
        crear_tabla_servicios_contratados();
    });
    // $("#buscar_producto").click(function () {
    //   // crear_tabla_productos();
    // });
    // $("#buscar_cliente").click(function () {
    //   crear_tabla_clientes();
    // });
    // $("#buscar_referente").click(function () {
    //     crear_tabla_referentes();
    // });

   $("#solo_activos").click(function () {
      crear_tabla_servicios_contratados();
    });
    $("#mostrar_todos").click(function () {
        $.ajax({
            type: "GET",
            url: window.location + '/get_servicios_contratados',
            // url: window.location + '/get_servicios_contratados_activos',
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
                        {name: 'f_servicio'},
                        {name: 'f_cliente'},
                        {name: 'f_itbis',type:'number'},
                        {name: 'f_cantidad',type:'number'},
                        {name: 'f_monto',type:'number'},
                        {name: 'f_precio',type:'number'}
                    ],
                    localdata: datas

                };


                var dataAdapter = new $.jqx.dataAdapter(source);


                $("#tabla_servicios_contratados").jqxGrid(
                    {
                        width: '100%',
                        source: dataAdapter,
                        theme: get_tema(),
                        showfilterrow: true,
                        filterable: true,
                        pageable: true,
                        autoheight: false,
                        editable: false,
                        sortable: true,
                        //localization: getLocalization('de'),
                        selectionmode: 'singlerow',
                        columns: [
                            {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_id', width: "10%"},
                            {text: 'Cliente', filtertype: 'textbox', datafield: 'f_cliente', width: "35%"},
                            {text: 'Servicio', filtertype: 'textbox', datafield: 'f_servicio', width: "30%"},
                            {text: 'Cantidad', filtertype: 'textbox', datafield: 'f_cantidad', width: "5%",cellsalign: 'right'},
                            {text: 'Precio', filtertype: 'textbox', datafield: 'f_precio', width: "10%",cellsalign: 'right',cellsformat: 'c2' },
                            {text: 'Itbis', filtertype: 'textbox', datafield: 'f_itbis', width: "5%",cellsalign: 'right',cellsformat: 'c2' },
                            {text: 'Monto', filtertype: 'textbox', datafield: 'f_monto', width: "15%",cellsalign: 'right',cellsformat: 'c2' }

                        ]
                    });
                $('#tabla_servicios_contratados').jqxGrid('hidecolumn', 'f_id');
            }
        });
    });


    $("#cantidad").blur(function () {
       calcular();
    });
    $("#precio").blur(function () {
        calcular();
    });
    $("#precio").keyup(function () {
        calcular();
    });

    $("#salvar").click(function () {


        if (validaciones() == true) {

            var envia = 'false';
            var activo = 'false';
            var variable = 'false';
            var cobrable = 'false';
            var referen = '0';

            var porciento_comision = 0;


            if ($("#porciento_referente").val()!= '')
            {
                porciento_comision =parseFloat($("#porciento_referente").val());
            }



            if ($("#enviar").is(':checked')) {
                envia = 'true';
            }
            if ($("#activo").is(':checked')) {
                activo = 'true';
            }
            if ($("#variable").is(':checked')) {
                variable = 'true';
            }

            if ($("#cobrable").is(':checked')) {
                cobrable = 'true';
            }
            if ($("#id_referente").val() != '') {
                referen =  $("#id_referente").val() ;
            }


            var vector = {
                idrecord: $("#idrecord").val(),
                idprodcuto: $("#id_producto").val(),
                idcliente: $("#id_cliente").val(),
                precio: $("#precio").val(),
                dias: $("#dia_factura").val(),
                activo: activo,
                key: $("#serial").val(),
                enviar: envia,
                idreferente: referen,
                monto_comision: $("#comision").val(),
                cantidad: $("#cantidad").val(),
                itebis: $("#itebis").val(),
                servidor: $("#servidor").val(),
                variable:variable,
                cobrable:cobrable,
                porciento_comision:porciento_comision



            };


            $.ajax({
                type: "POST",
                url: window.location + '/salvar_servicios_contratados',
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

    $('#tabla_servicios_contratados').on('rowdoubleclick', function (event) {
        var pocision = args.rowindex;
        var data = $('#tabla_servicios_contratados').jqxGrid('getrowdata', pocision);

        // alert(data.f_id);
        $.ajax({
            type: "POST",
            url: window.location + '/get_servicios_contratados_by_id',
            data: {idrecord:data.f_id},
            success: function (data) {
                var tupla = eval(data);

                $("#idrecord").val(tupla[0].f_id);
                $("#id_producto").val(tupla[0].f_id_producto);
                $("#producto").val(tupla[0].f_producto);
                $("#tiene_itbis").val(tupla[0].f_tiene_itbis);
                $("#id_cliente").val(tupla[0].f_idcliente);
                $("#cliente").val(tupla[0].f_cliente);

                if (tupla[0].f_id_referente!='0' && tupla[0].f_id_referente!='null')
                {
                    $("#id_referente").val(tupla[0].f_id_referente);
                    $("#referente").val(tupla[0].f_referente);
                    $("#porciento_referente").val(tupla[0].f_porciento_referente);
                }
                else {
                    $("#id_referente").val('');
                    $("#referente").val('');
                    $("#porciento_referente").val('0');
                }

                $("#precio").val(tupla[0].f_precio);
                $("#itebis").val(tupla[0].f_itebis);
                // $("#porcentaje").val(tupla[0].f_porcentaje);
                $("#comision").val(tupla[0].f_comision);
                $("#cantidad").val(tupla[0].f_cantidad);
                $("#dia_factura").val(tupla[0].f_dia_factura);
                $("#serial").val(tupla[0].f_serial);
                $("#servidor").val(tupla[0].f_url_servidor_actualizacion);
                $("#monto").val(ConvertToCurrency(tupla[0].f_cantidad*tupla[0].f_precio));


                if (String(tupla[0].f_enviar)=='true')
                {
                    $("#enviar").parent().addClass('checked');
                    $("#enviar").prop('checked',true);
                }
                else {
                    $("#enviar").parent().removeClass('checked');
                    $("#enviar").prop('checked',false);
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

                if (String(tupla[0].f_cobrable)=='true')
                {
                    $("#cobrable").parent().addClass('checked');
                    $("#cobrable").prop('checked',true);
                }
                else {
                    $("#cobrable").parent().removeClass('checked');
                    $("#cobrable").prop('checked',false);
                }

                if (String(tupla[0].f_cvariable)=='true')
                {
                    $("#variable").parent().addClass('checked');
                    $("#variable").prop('checked',true);
                }
                else {
                    $("#variable").parent().removeClass('checked');
                    $("#variable").prop('checked',false);
                }



                $("#nuevo_servicio").click();


            }
        });

    });

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
                    {name: 'f_itbis'},
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
                    sortable: true,

                    //localization: getLocalization('de'),
                    selectionmode: 'singlerow',
                    columns: [
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_id', width: "10%"},
                        {text: 'Nombre', filtertype: 'textbox', datafield: 'f_nombre_empresa', width: "35%"},
                        {text: 'E-mail', filtertype: 'textbox', datafield: 'f_email', width: "35%"},
                        {text: 'IT',columntype: 'checkbox', filtertype: 'textbox', datafield: 'f_itbis', width: "5%",editable:false},
                        {text: 'Seleccionar', datafield: '', columntype: 'button', cellsrenderer: function () {
                            return 'Seleccionar';
                        },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_clientes").jqxGrid('getrowdata', row);
                                $("#id_cliente").val(dataRecord.f_id);
                                $("#cliente").val(dataRecord.f_nombre_empresa);
                                $("#tiene_itbis").val(dataRecord.f_itbis);
                                $("#atras_clientes").click();
                            }, theme: get_tema(), width: "15%"
                        }

                    ]
                });
            // $('#tabla_clientes').jqxGrid('hidecolumn', 'f_idrecord');
        }
    });
}



function crear_tabla_servicios_contratados() {
    $.ajax({
        type: "POST",
        // url: window.location + '/get_servicios_contratados',
        url: window.location + '/get_servicios_contratados_activos',
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
                    {name: 'f_servicio'},
                    {name: 'f_cliente'},
                    {name: 'f_itbis',type:'number'},
                    {name: 'f_cantidad',type:'number'},
                    {name: 'f_monto',type:'number'},
                    {name: 'f_precio',type:'number'}
                ],
                localdata: datas

            };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_servicios_contratados").jqxGrid(
                {
                    width: '100%',
                    source: dataAdapter,
                    theme: get_tema(),
                    showfilterrow: true,
                    filterable: true,
                    pageable: true,
                    autoheight: false,
                    editable: false,
                    sortable: true,

                    //localization: getLocalization('de'),
                    selectionmode: 'singlerow',
                    columns: [
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_id', width: "10%"},
                        {text: 'Cliente', filtertype: 'textbox', datafield: 'f_cliente', width: "25%"},
                        {text: 'Servicio', filtertype: 'textbox', datafield: 'f_servicio', width: "30%"},
                        {text: 'Cantidad', filtertype: 'textbox', datafield: 'f_cantidad', width: "10%",cellsalign: 'right' },
                        {text: 'Precio', filtertype: 'textbox', datafield: 'f_precio', width: "10%",cellsalign: 'right',cellsformat: 'c2' },

                        {text: 'Itbis', filtertype: 'textbox', datafield: 'f_itbis', width: "10%",cellsalign: 'right',cellsformat: 'c2' },
                        {text: 'Monto', filtertype: 'textbox', datafield: 'f_monto', width: "15%",cellsalign: 'right',cellsformat: 'c2' }

                    ]
                });
            $('#tabla_servicios_contratados').jqxGrid('hidecolumn', 'f_id');
        }
    });
}



function crear_tabla_referentes() {
    $.ajax({
        type: "GET",
        url: window.location + '/get_referentes',
        // data: 'parametro='+$("#parametro").val(),
        success: function (data) {

            var datas = '';
            var source;

            datas = data;
            source =
            {
                datatype: "json",

                datafields: [
                    {name: 'f_idrecord'},
                    {name: 'f_nombre'},
                    {name: 'f_email'},
                    {name: 'f_telefono1'},
                    {name: 'f_porciento'}

                ],
                localdata: datas

            };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_referentes").jqxGrid(
                {
                    width: '100%',
                    source: dataAdapter,
                    theme: get_tema(),
                    showfilterrow: true,
                    filterable: true,
                    pageable: true,
                    autoheight: false,
                    editable: false,
                    sortable: true,

                    //localization: getLocalization('de'),
                    selectionmode: 'singlerow',
                    columns: [
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_idrecord', width: "5%"},
                        {text: 'Nombre', filtertype: 'textbox', datafield: 'f_nombre', width: "35%"},
                        {text: 'E-Mail', filtertype: 'textbox', datafield: 'f_email', width: "35%"},
                        {text: 'Porciento', filtertype: 'textbox', datafield: 'f_porciento', width: "10%"},
                        {text: 'Seleccionar', datafield: '', columntype: 'button', cellsrenderer: function () {
                            return 'Seleccionar';
                        },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_referentes").jqxGrid('getrowdata', row);
                                $("#id_referente").val(dataRecord.f_idrecord);
                                $("#referente").val(dataRecord.f_nombre);
                                $("#porciento_referente").val(dataRecord.f_porciento);
                                calcular();
                                $("#atras_referentes").click();
                            }, theme: get_tema(), width: "15%"
                        }

                    ]
                });
            $('#tabla_referentes').jqxGrid('hidecolumn', 'f_idrecord');
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
    if ($("#id_producto").val() == '') {
        notificacion_no('Seleccione el Prodcuto');
        valor = false;
    }
    else if ($("#id_cliente").val() == '') {
        notificacion_no('Seleccione el Cliente');
        valor = false;
    }
    // else if ($("#id_referente").val() == '') {
    //     notificacion_no('Seleccione el Referente');
    //     valor = false;
    // }
    else if ($("#cantidad").val() == '' ||parseFloat($("#cantidad").val())<=0) {
        notificacion_no('Digite la Cantidad');
        valor = false;
    }
    else if ($("#dia_factura").val() == '' ||parseFloat($("#dia_factura").val())<0) {
        notificacion_no('Digite el Dia de para Facturar');
        valor = false;
    }
    else if (parseInt($("#dia_factura").val())<=0 || parseInt($("#dia_factura").val())>31) {
        notificacion_no('El dia de la Factura no puede ser Menor que 0 o Mayor que 31');
        valor = false;
    }
    return valor;
}

function nuevo() {
    $("#idrecord").val('');
    $("#id_producto").val('');
    $("#producto").val('');
    $("#id_cliente").val('');
    $("#cliente").val('');
    $("#id_referente").val('');
    $("#referente").val('');
    $("#precio").val('');
    $("#itebis").val('');
    $("#monto").val('');
    $("#tiene_itbis").val('');
    // $("#porcentaje").val('');
    $("#comision").val('');
    $("#cantidad").val('');
    $("#dia_factura").val('');
    $("#serial").val('');
    $("#servidor").val('0');
    $("#enviar").parent().addClass('checked');
    $("#enviar").prop('checked',true);

    $("#cobrable").parent().addClass('checked');
    $("#cobrable").prop('checked',true);
    
    $("#activo").parent().addClass('checked');
    $("#activo").prop('checked',true);

    $("#variable").parent().removeClass('checked');
    $("#variable").prop('checked',false);
}


function crear_tabla_productos() {
    $.ajax({
        type: "POST",
        // url: window.location + '/get_productos',
        url: window.location + '/get_productos_activos_no_activos',
        data: {activo:true} ,
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
                    {name: 'f_itbis',type: 'bool'},
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
                    sortable: true,

                    //localization: getLocalization('de'),
                    selectionmode: 'singlerow',
                    columns: [
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_id', width: "0%",editable:false},
                        {text: 'Referencia', filtertype: 'textbox', datafield: 'f_referencia', width: "15%"},
                        {text: 'Descripción', filtertype: 'textbox', datafield: 'f_descripcion', width: "55%"},
                        {text: 'IT',columntype: 'checkbox', filtertype: 'textbox', datafield: 'f_itbis', width: "5%",editable:false},
                        {text: 'Precio', filtertype: 'textbox', datafield: 'f_precio', width: "10%",cellsalign: 'right',cellsformat: 'c2' },
                        {text: 'Seleccionar', datafield: '', columntype: 'button', cellsrenderer: function () {
                            return 'Seleccionar';
                        },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_productos").jqxGrid('getrowdata', row);
                                $("#id_producto").val(dataRecord.f_id);
                                $("#producto").val(dataRecord.f_referencia+' '+dataRecord.f_descripcion);
                                $("#precio").val(dataRecord.f_precio);
                                // $("#tiene_itbis").val(dataRecord.f_itbis);
                                $("#atras_producto").click();

                                calcular();
                            }, theme: get_tema(), width: "15%"
                        }

                    ]
                });
            $("#tabla_productos").jqxGrid('hidecolumn', 'f_id');
            $("#tabla_productos").jqxGrid('hidecolumn', 'f_itbis');
        }
    });
}

function calcular() {

    var precio_producto = 0 ;
    var porciento_comision = 0;
    var cantidad = 0;

    if ($("#precio").val()!= '')
    {
        precio_producto =parseFloat($("#precio").val());
    }
    if ($("#porciento_referente").val()!= '')
    {
        porciento_comision =parseFloat($("#porciento_referente").val());
    }

    if ($("#cantidad").val()!= '')
    {
        cantidad =parseFloat($("#cantidad").val());
    }



    var itebis_preferencia =parseFloat($("#itebis_preferencia").val());
    var aux =parseFloat(precio_producto) * (itebis_preferencia/100);

    $("#comision").val(precio_producto * (porciento_comision/100) );
    if ($("#tiene_itbis").val()=='true')
    {
        $("#itebis").val(aux);

        $("#monto").val(ConvertToCurrency((precio_producto * cantidad)+(aux*cantidad)));

    }
    else {
        $("#itebis").val(0);
        $("#monto").val(ConvertToCurrency((precio_producto * cantidad)));

    }

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
