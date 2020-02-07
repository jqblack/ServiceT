<!doctype html>
<html lang="en">
<head>
    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Facturas</title>
</head>

<body>


<div class="page-header">

    <div class="row">
        <!-- Page header, center on small screens -->
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-money page-header-icon"></i>&nbsp;&nbsp;Facturas</h1>

        <div class="col-xs-12 col-sm-8">
            <div class="row">
                <hr class="visible-xs no-grid-gutter-h">
                <!-- "Create project" button, width=auto on desktops -->
                %{--<div class="pull-right col-xs-12 col-sm-auto"><a  class="btn btn-primary btn-labeled" id="nuevo_servicio"  data-toggle="modal" data-target="#modal_servicios_contratados" style="width: 100%;"><span class="btn-label icon fa fa-plus"></span>Nuevo Servicio</a></div>--}%

                <!-- Margin -->
                <div class="visible-xs clearfix form-group-margin"></div>

            </div>
        </div>
    </div>
</div> <!-- / .page-header -->
<div class="panel">
    %{--<div class="panel-heading">--}%
    %{--<span class="panel-title">Morris.js Graph</span>--}%
    %{--</div>--}%
    <div class="panel-body">

        %{--<div class="graph-container">--}%
        %{----}%
        %{--</div>--}%


        <div class="row">
            <div class="col-md-1"> </div>
            <div class="col-md-10">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        %{--<h3 class="box-title">Clientes</h3>--}%
                    </div><!-- /.box-header -->
                <!-- form start -->
                %{--<form role="form" method="post" action="salvar_perfil">--}%
                    <div class="box-body">
                        <div class="row">
                            <div class="col-md-12 col-sm-12">


                                <div class="row">
                                    <div class="col-md-7 col-sm-12">

                                        <div class="form-group ">
                                            <label>Cliente</label>
                                            <input type="hidden" id="id_cliente" class="form-control" placeholder="Nombre">
                                            <input type="hidden" id="email_cliente" class="form-control" placeholder="Email Cliente">

                                            <div class="input-group ">
                                                <input type="" id="cliente"  readonly class="form-control" placeholder="Cliente" required>
                                                <span class="input-group-btn">
                                                    <button class="btn btn-primary" type="submit" id="buscar_cliente"  data-toggle="modal" data-target="#modal_clientes">
                                                        <span class="fa fa-search"></span>
                                                    </button>
                                                </span>
                                            </div>
                                            %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                        </div>
                                    </div>

                                    <div class="col-md-2 col-sm-12">
                                        <div class="form-group has-feedback">
                                            <label>Moneda</label>
                                            <select class="form-control "  id="moneda">
                                                <optgroup label="Monedas Locales" >
                                                    <option value="1">PESO</option>
                                                </optgroup>

                                                <optgroup label="Monedas Extrajeras" >
                                                    <option value="2">DOLLAR</option>
                                                    <option value="3">EURO</option>
                                                </optgroup>


                                            </select>

                                        </div>
                                    </div>
                                    <div class="col-md-3 col-sm-12">

                                        <div class="form-group ">
                                            <label>Fecha Vencimiento</label>
                                            %{--<div class="input-group date" id="piker_fecha">--}%
                                            %{--<input id="fecha" type="text" class="form-control"><span class="input-group-addon"><i class="fa fa-calendar"></i></span>--}%
                                            %{--</div>--}%
                                            <div id="fecha">
                                            </div>
                                            %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                        </div>
                                    </div>

                                </div>


                                <div class="row">
                                    <div class="col-md-9 col-sm-9 col-xl-12">



                                        <div class="row">
                                            <div class="col-md-3 col-sm-12 col-xl-6 col-lg-3 col-xs-12">
                                                <div class="form-group ">
                                                    <label>Referencia</label>
                                                    <input type="hidden" id="id_producto" class="form-control" placeholder="id_producto">

                                                    <div class="input-group ">
                                                        <input type="" id="referencia"  readonly class="form-control" placeholder="Referencia" required>
                                                        <span class="input-group-btn">
                                                            <button class="btn btn-primary" type="submit" id="buscar_productos" data-toggle="modal" data-target="#modal_productos">
                                                                <span class="fa fa-search"></span>
                                                            </button>
                                                        </span>
                                                    </div>
                                                    %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                                </div>
                                            </div>
                                            <div class="col-md-9 col-sm-12 col-xl-6 col-lg-9 col-xs-12">
                                                <div class="form-group ">
                                                    <label>Descripcion</label>
                                                    <input type="" id="descripcion_producto" readonly   class="form-control" placeholder="Descripción">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-md-4 col-sm-12 col-xl-12 col-lg-3">

                                                <div class="form-group has-feedback">
                                                    <label>Precio</label>
                                                    <input type=""  id="precio" class="form-control" placeholder="Precio" required>
                                                    %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                                </div>
                                            </div>

                                            <div class="col-md-4 col-sm-12 col-xl-12 col-lg-3">

                                                <div class="form-group has-feedback">
                                                    <label>Itbis </label>
                                                    <input type="" readonly id="itbis" class="form-control" placeholder="Itbis" required>
                                                    %{--<span class="glyphicon glyphicon-envelope form-control-feedback"></span>--}%
                                                </div>
                                            </div>
                                            <div class="col-md-4 col-sm-12 col-xl-12 col-lg-3">

                                                <div class="form-group has-feedback">
                                                    <label>Cantidad</label>
                                                    <div class="input-group ">
                                                        <input type="" id="cantidad" class="form-control" placeholder="Cantidad" required>
                                                        <span class="input-group-btn">
                                                            <button class="btn btn-primary" type="submit" id="agregar_producto">
                                                                <span class="fa fa-plus"></span>
                                                            </button>
                                                            %{--<button class="btn btn-primary" type="submit" id="eli" data-toggle="modal" data-target="#modal_productos">--}%
                                                            %{--<span class="fa fa-search"></span>--}%
                                                            %{--</button>--}%
                                                        </span>
                                                    </div>
                                                    %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                                </div>
                                            </div>
                                            <div class="col-md-4 col-sm-0 col-xl-0 col-lg-3">
                                                <div class="form-group has-feedback">
                                                    <label>Monto</label>
                                                    <input type="" id="monto" readonly class="form-control" placeholder="Monto" required>
                                                    %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                                </div>
                                            </div>

                                        </div>





                                    </div>
                                    <div class="col-md-3 col-sm-3 col-xl-12">

                                        <div class="row">
                                            <div class="col-md-6 col-xl-6 col-sm-12 col-lg-6 col-xs-6">
                                                <div class="form-group has-feedback">
                                                    <label>Contado</label><br/>
                                                    <input type="checkbox"  class="form-control" id="contado">&nbsp;&nbsp;

                                                </div>
                                            </div>
                                            <div class="col-md-6 col-xl-6 col-sm-12 col-lg-6 col-xs-6">
                                                <div class="form-group has-feedback">
                                                    <label>Credito</label><br/>
                                                    <input type="checkbox"  class="form-control" id="credito">&nbsp;&nbsp;

                                                </div>
                                            </div>
                                            %{--<div class="col-md-12 col-sm-12 ">--}%
                                            %{--<div class="form-group has-feedback pull-left" >--}%
                                            %{--<button type="" style="margin-top: 26px" id="mostrar_todos"  class="btn btn-primary "  data-original-title="Mostrar Todos" data-dismiss="modal" aria-hidden="true">  <span class="fa fa-square-o"></span></button>--}%
                                            %{--<button type="" style="margin-top: 26px" id="solo_activos"  class="btn btn-success  " data-original-title="Solo Activos" data-dismiss="modal" aria-hidden="true">  <span class="fa fa-check-square-o"></span></button>--}%
                                            %{--<button type="submit" id="salvar" class="btn btn-primary"><span class="fa fa-save"></span>&nbsp;&nbsp;Salvar</button>--}%
                                            %{--</div>--}%
                                            %{--</div>--}%
                                        </div>
                                        %{--codigo para si es a credito o a contado--}%


                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group has-feedback">
                                            <label>Comentario Producto</label>
                                            <textarea type=""  rows="1" id="comentario_producto"  class="form-control" placeholder="Comentario Producto" ></textarea>
                                            %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                        </div>
                                    </div>

                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group has-feedback">
                                            <label>Comentario Factura</label>
                                            <textarea type="" rows="2" id="comentario"  class="form-control" placeholder="Comentario" ></textarea>
                                            %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                        </div>
                                    </div>

                                </div>

                                <div class="row">
                                    <div class="col-md-12">
                                        <div id="tabla_datos">

                                        </div>
                                    </div>
                                </div>



                                <br/>

                                <div class="box-footer">
                                    <div class="row">
                                        <div class="col-md-12 col-lg-6 col-sm-12 col-xl-12 col-xs-12">
                                            <div style="background-color: #f0ffc5;color: red;font-size: 25px">
                                                <label id="label_tax"> TAX : $0</label>
                                            </div>
                                        </div>
                                        <div class="col-md-12 col-lg-6 col-sm-12 col-xl-12 col-xs-12">
                                            <div style="background-color: #f0ffc5;color: green;font-size: 25px">
                                                <label id="label_total">Monto : $0</label>
                                            </div>
                                        </div>
                                    </div>


                                    <div class="form-group has-feedback">
                                        %{--<a href="" class="btn btn-primary ">--}%
                                        %{--<span class="fa fa-arrow-left"></span> Atras--}%
                                        %{--</a>--}%
                                        <button type="" id="nuevo"  class="btn btn-primary" >  <span class="fa fa-edit"></span>&nbsp;Nuevo</button>
                                        <button type="submit" id="salvar" class="btn btn-primary"><span class="fa fa-save"></span>&nbsp;Salvar</button>
                                    </div>
                                </div>
                            </div><!-- /.box-body -->
                        %{--</form>--}%
                        </div><!-- /.box -->
                    </div>
                    <div class="col-md-1 col-sm-1 col-xs-1 col-lg-1 col-xl-1 "></div>
                </div>
            </div>
            <div class="col-md-1">

                <div id="modal_productos" class="modal fade" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                %{--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>--}%
                                <h4 class="modal-title">Buscar Productos</h4>
                            </div>
                            <div class="row">
                                <div class="col-md-2 col-sm-1 col-xs-1 col-lg-1 col-xl-1 "></div>
                                <div class="col-md-8 col-sm-10 col-xs-10 col-lg-10 col-xl-10 ">
                                    <div class="box box-primary">
                                        <div class="box-header with-border">
                                            %{--<h3 class="box-title">Clientes</h3>--}%
                                        </div><!-- /.box-header -->
                                    <!-- form start -->
                                    %{--<form role="form" method="post" action="salvar_perfil">--}%
                                        <div class="box-body">
                                            <div class="row">
                                                <div class="col-md-12 col-sm-12">
                                                    <div class="row">
                                                        <div class="col-md-12 col-sm-12">
                                                            <div id="tabla_productos"></div>
                                                        </div>
                                                    </div>
                                                    <div class="box-footer">
                                                        <div class="form-group has-feedback">
                                                            %{--<a href="" class="btn btn-primary ">--}%
                                                            %{--<span class="fa fa-arrow-left"></span> Atras--}%
                                                            %{--</a>--}%
                                                            <br/>
                                                            <button type="" id="atras_producto"  class="btn btn-primary" data-dismiss="modal" aria-hidden="true">  <span class="fa fa-arrow-left"></span>Atras</button>
                                                            %{--<button type="submit" id="salvar" class="btn btn-primary">Salvar</button>--}%
                                                        </div>



                                                    </div>
                                                </div><!-- /.box-body -->
                                            %{--</form>--}%
                                            </div><!-- /.box -->
                                        </div>
                                        <div class="col-md-1 col-sm-1 col-xs-1 col-lg-1 col-xl-1 "></div>
                                    </div>

                                </div> <!-- / .modal-content -->
                            </div> <!-- / .modal-dialog -->
                        </div> <!-- / .modal -->
                    <!-- / Large modal -->
                    </div>
                </div>


                <a class=""  type="submit" id="mostrar_factura" data-toggle="modal" data-target="#modal_ver_factura"></a>
                <div id="modal_ver_factura" class="modal fade" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" id="close_modal_actura" aria-hidden="true">×</button>
                                <h4 class="modal-title">Factura</h4>
                            </div>
                            <div class="row">
                                <input type="hidden" id="idrecord"  class="form-control" placeholder="idrecord  ">
                                <div class="col-md-2 col-sm-1 col-xs-1 col-lg-1 col-xl-1 "></div>
                                <div class="col-md-8 col-sm-10 col-xs-10 col-lg-10 col-xl-10 ">
                                    <div class="box box-primary">
                                        <div class="box-header with-border">
                                            %{--<h3 class="box-title">Clientes</h3>--}%
                                        </div><!-- /.box-header -->
                                    <!-- form start -->
                                    %{--<form role="form" method="post" action="salvar_perfil">--}%
                                        <div class="box-body">
                                            <div class="row">
                                                <div class="col-md-12 col-sm-12">
                                                    <div id="pdf">

                                                    </div>

                                                    <div id="jqxLoader">
                                                    </div>

                                                </div><!-- /.box-body -->
                                            %{--</form>--}%
                                            </div><!-- /.box -->
                                            <div class="row">
                                                <div class="col-md-3 col-sm-0">
                                                </div>
                                                <div class="col-md-6 col-sm-12">

                                                    <div class="panel panel-info panel-dark">
                                                        <div class="panel-heading">
                                                            <span class="panel-title">Datos de Envio</span>
                                                            <div class="panel-heading-controls">
                                                                <div class="panel-heading-icon"><i class="fa fa-inbox"></i></div>
                                                            </div>
                                                        </div>
                                                        <div class="panel-body" style="height: 63px;">
                                                            <div class="row" style=" margin-top: -19px;">
                                                                <div class="col-md-9 col-xs-8">
                                                                    <div class="form-group ">
                                                                        <label>Correo</label>
                                                                        <input type="email" id="correo_alternativo"    class="form-control" placeholder="Correo">
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-3 col-xs-3">
                                                                    <div class="form-group ">
                                                                        <label>&nbsp;&nbsp;&nbsp;</label><br/>
                                                                        <button type="" id="boton_enviar"  class="btn btn-primary"><span class="fa"></span>Enviar</button>

                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div><!-- /.box-body -->
                                                <div class="col-md-3 col-sm-0">
                                                </div>
                                                %{--</form>--}%
                                            </div><!-- /.box -->
                                        </div>
                                        <div class="col-md-1 col-sm-1 col-xs-1 col-lg-1 col-xl-1 "></div>
                                    </div>

                                </div> <!-- / .modal-content -->
                            </div> <!-- / .modal-dialog -->
                        </div> <!-- / .modal -->
                    <!-- / Large modal -->
                    </div>
                </div>


                <div id="modal_clientes" class="modal fade" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                %{--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>--}%
                                <h4 class="modal-title">Buscar Clientes</h4>
                            </div>
                            <div class="row">
                                <div class="col-md-2 col-sm-1 col-xs-1 col-lg-1 col-xl-1 "></div>
                                <div class="col-md-8 col-sm-10 col-xs-10 col-lg-10 col-xl-10 ">
                                    <div class="box box-primary">
                                        <div class="box-header with-border">
                                            %{--<h3 class="box-title">Clientes</h3>--}%
                                        </div><!-- /.box-header -->
                                    <!-- form start -->
                                    %{--<form role="form" method="post" action="salvar_perfil">--}%
                                        <div class="box-body">
                                            <div class="row">
                                                <div class="col-md-12 col-sm-12">
                                                    <div class="row">
                                                        <div class="col-md-12 col-sm-12">
                                                            <div id="tabla_clientes"></div>
                                                        </div>
                                                    </div>
                                                    <div class="box-footer">
                                                        <div class="form-group has-feedback">
                                                            %{--<a href="" class="btn btn-primary ">--}%
                                                            %{--<span class="fa fa-arrow-left"></span> Atras--}%
                                                            %{--</a>--}%
                                                            <br/>
                                                            <button type="" id="atras_clientes"  class="btn btn-primary" data-dismiss="modal" aria-hidden="true">  <span class="fa fa-arrow-left"></span>Atras</button>
                                                            %{--<button type="submit" id="salvar" class="btn btn-primary">Salvar</button>--}%
                                                        </div>



                                                    </div>
                                                </div><!-- /.box-body -->
                                            %{--</form>--}%
                                            </div><!-- /.box -->
                                        </div>
                                        <div class="col-md-1 col-sm-1 col-xs-1 col-lg-1 col-xl-1 "></div>
                                    </div>

                                </div> <!-- / .modal-content -->
                            </div> <!-- / .modal-dialog -->
                        </div> <!-- / .modal -->
                    <!-- / Large modal -->
                    </div>
                </div>






                %{--modal clientes--}%



                <div id="uidemo-modals-alerts-success" class="modal modal-alert modal-success fade">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <i class="fa fa-check-circle"></i>
                            </div>
                            <div id="titulo_notifificacion_si" class="modal-title">Alerta</div>
                            <div id="body_notifificacion_si" class="modal-body">Some alert text</div>
                            <div class="modal-footer">
                                <button id="ok_notifificacion_si" type="button" class="btn btn-success" data-dismiss="modal">OK</button>
                            </div>
                        </div> <!-- / .modal-content -->
                    </div> <!-- / .modal-dialog -->
                </div> <!-- / .modal -->
            <!-- / Success -->

            <!-- Danger -->
                <div id="uidemo-modals-alerts-danger" class="modal modal-alert modal-danger fade">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <i class="fa fa-times-circle"></i>
                            </div>
                            <div id="titulo_notifificacion_no" class="modal-title">Alerta</div>
                            <div id="body_notifificacion_no" class="modal-body">Some alert text</div>
                            <div class="modal-footer">
                                <button id="ok_notifificacion_no" type="button" class="btn btn-danger" data-dismiss="modal"  aria-hidden="true">OK</button>
                            </div>
                        </div> <!-- / .modal-content -->
                    </div> <!-- / .modal-dialog -->
                </div> <!-- / .modal -->
            <!-- / Danger -->
                <button  id="notificacion_si" style="display:none"  class="btn btn-success" data-toggle="modal" data-target="#uidemo-modals-alerts-success">Success</button>&nbsp;&nbsp;&nbsp;
                <button id="notificacion_no" style="display:none" class="btn btn-danger" data-toggle="modal" data-target="#uidemo-modals-alerts-danger">Danger</button>&nbsp;&nbsp;&nbsp;


            </div>
        </div>
</div>
</div>

<asset:javascript src="js_proyecto/funciones.js"/>
<asset:javascript src="js_proyecto/md5.js"/>
<asset:javascript src="js_proyecto/procesos/facturas.js"/>
<g:javascript>
    $(document).ready(function () {
        ejecutar();
        $('#piker_fecha').datepicker();
        $('#contado').switcher();
        $('#credito').switcher();
    });

</g:javascript>
</body>
</html>