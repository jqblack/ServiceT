/**
 * Created by Pabel on 16/6/2016.
 */
function get_tema() {
    return 'web';
}
function ejecutar() {


    crear_tabla_clientes();
    crear_mensajes();

    $("#activo").parent().addClass('checked');
    $("#activo").prop('checked',true);

    $("#enviar").parent().addClass('checked');
    $("#enviar").prop('checked',true);

    $("#ok_notifificacion_si").click(function () {
       // $("#atras").click();
    });
    $("#nuevo_mensajes").click(function () {
        if ($("#idrecord").val()=='')
        {
            nuevo();
        }

    });

    $("#desactivar_todos").click(function () {
      if (confirm("Esta seguro que desea desactvar los mensaje ?")){
          $.ajax({
              type: "POST",
              url: window.location + '/desactivar_todo_los_mensajes',
              // data: vector,
              success: function (data) {
                  if (data){
                      notificacion_si('Datos Guardados Correctamente');
                      crear_mensajes();
                  }else {
                      notificacion_no("Ocurrio un erro");
                  }
              }
          });
      }
    });

    $("#atras").click(function () {
        nuevo();
        crear_mensajes();
    });



    $("#salvar").click(function () {


        if (validaciones() == true) {


            var activo = 'false';

            if ($("#activo").is(':checked')) {
                activo = 'true';
            }


            var vector = {
                idrecord: $("#idrecord").val(),
                idcliente: $("#id_cliente").val(),
                mensaje: $("#mensaje").val(),
                activo: activo,
                tipo: $("#tipo").val()
            };


            $.ajax({
                type: "POST",
                url: window.location + '/salvar_mensajes',
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

    $('#tabla_mensajes').on('rowdoubleclick', function (event) {
        var args = event.args;
        var pocision = args.rowindex;
        var data = $('#tabla_mensajes').jqxGrid('getrowdata', pocision);

        // alert(data.f_id);
        $.ajax({
            type: "POST",
            url: window.location + '/get_mensajes_by_id',
            data: {idrecord:data.f_id},
            success: function (data) {
                var tupla = eval(data);

                $("#idrecord").val(tupla[0].f_id);
                $("#id_cliente").val(tupla[0].f_idcliente);
                $("#cliente").val(tupla[0].f_cliente);
                $("#mensaje").val(tupla[0].f_mensaje);



                if (String(tupla[0].f_tipo)=='Aviso') {

                    $("#tipo").val('1');
                }
                else
                {
                    $("#tipo").val('2');
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


                $("#nuevo_mensajes").click();


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
                        {text: 'Nombre', filtertype: 'textbox', datafield: 'f_nombre_empresa', width: "40%"},
                        {text: 'E-mail', filtertype: 'textbox', datafield: 'f_email', width: "35%"},
                        {text: 'Seleccionar', datafield: '', columntype: 'button', cellsrenderer: function () {
                            return 'Seleccionar';
                        },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_clientes").jqxGrid('getrowdata', row);
                                $("#id_cliente").val(dataRecord.f_id);
                                $("#cliente").val(dataRecord.f_nombre_empresa);
                                $("#atras_clientes").click();
                            }, theme: get_tema(), width: "15%"
                        }

                    ]
                });
            // $('#tabla_clientes').jqxGrid('hidecolumn', 'f_idrecord');
        }
    });
}



function crear_mensajes() {
    $.ajax({
        type: "GET",
        url: window.location + '/get_mensajes',
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
                    {name: 'f_idcliente'},
                    {name: 'f_cliente'},
                    {name: 'f_mensaje'},
                    {name: 'f_tipo'},
                    {name: 'f_activo'}
                ],
                localdata: datas

            };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_mensajes").jqxGrid(
                {
                    width: '100%',
                    source: dataAdapter,
                    theme: get_tema(),
                    showfilterrow: true,
                    filterable: true,
                   // pageable: true,
                    autoheight: true,
                    editable: false,
                    groupable: true,
                    sortable: true,

                    //localization: getLocalization('de'),
                    selectionmode: 'singlerow',
                    columns: [
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_id', width: "10%"},
                        {text: 'Codigo', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_idcliente', width: "10%"},
                        {text: 'Cliente', filtertype: 'textbox', datafield: 'f_cliente', width: "35%"},
                        {text: 'Mensaje', filtertype: 'textbox', datafield: 'f_mensaje', width: "35%"},
                        {text: 'Tipo', filtertype: 'textbox', datafield: 'f_tipo', width: "10%"},
                        {text: 'Activo',columntype: 'checkbox',datafield: 'f_activo', width: "10%" }

                    ],
                    groups: ['f_cliente']
                });
            $('#tabla_mensajes').jqxGrid('hidecolumn', 'f_id');
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
    if ($("#id_cliente").val() == '') {
        notificacion_no('Seleccione el Cliente');
        valor = false;
    }
    else if ($("#tipo").val() == '') {
        notificacion_no('Seleccione el Tipo de Mensaje');
        valor = false;
    }
    else if ($("#mensaje").val() == '') {
        notificacion_no('Digite el Mensaje');
        valor = false;
    }
    return valor;
}

function nuevo() {
    $("#idrecord").val('');
    $("#id_cliente").val('');
    $("#cliente").val('');
    $("#tipo").val('');
    $("#mensaje").val('');
    $("#activo").parent().addClass('checked');
    $("#activo").prop('checked',true);
}


function crear_tabla_productos() {
    $.ajax({
        type: "GET",
        url: window.location + '/get_productos',
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
                    {name: 'f_referencia'},
                    {name: 'f_descripcion'},
                    {name: 'f_version'},
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
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_id', width: "5%"},
                        {text: 'Referencia', filtertype: 'textbox', datafield: 'f_referencia', width: "15%"},
                        {text: 'Descripción', filtertype: 'textbox', datafield: 'f_descripcion', width: "55%"},
                        {text: 'Precio', filtertype: 'textbox', datafield: 'f_precio', width: "10%",cellsalign: 'right',cellsformat: 'c2' },
                        {text: 'Seleccionar', datafield: '', columntype: 'button', cellsrenderer: function () {
                            return 'Seleccionar';
                        },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_productos").jqxGrid('getrowdata', row);
                                $("#id_producto").val(dataRecord.f_id);
                                $("#producto").val(dataRecord.f_referencia+' '+dataRecord.f_descripcion);
                                $("#precio").val(dataRecord.f_precio);
                                $("#atras_producto").click();

                                calcular();
                            }, theme: get_tema(), width: "15%"
                        }

                    ]
                });
            $('#tabla_productos').jqxGrid('hidecolumn', 'f_id');
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

    $("#itebis").val(aux);
    $("#comision").val(precio_producto * (porciento_comision/100) );
    $("#monto").val(ConvertToCurrency(precio_producto * cantidad));

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
