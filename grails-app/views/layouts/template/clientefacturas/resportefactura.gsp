<!doctype html>
<html lang="en">
<head>
    <meta name="layout" content="mainClientes">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Facturas Clientes</title>
</head>

<body>

<div class="page-header">

    <div class="row">
        <!-- Page header, center on small screens -->
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-dashboard page-header-icon"></i>&nbsp;&nbsp;Ver Facturas</h1>

        <div class="col-xs-12 col-sm-1">
            <div class="row">
                <hr class="visible-xs no-grid-gutter-h">
                <!-- "Create project" button, width=auto on desktops -->
                %{--<div class="pull-right col-xs-12 col-sm-auto"><a  class="btn btn-primary btn-labeled" id="nuevo_producto"  data-toggle="modal" data-target="#modal_clientes" style="width: 100%;"><span class="btn-label icon fa fa-plus"></span>Nuevo Producto</a></div>--}%

                <!-- Margin -->
                <div class="visible-xs clearfix form-group-margin"></div>

                <!-- Search field -->
                %{--<form action="" class="pull-right col-xs-12 col-sm-6">--}%
                %{--<div class="input-group no-margin">--}%
                %{--<span class="input-group-addon" style="border:none;background: #fff;background: rgba(0,0,0,.05);"><i class="fa fa-search"></i></span>--}%
                %{--<input type="text" placeholder="Search..." class="form-control no-padding-hr" style="border:none;background: #fff;background: rgba(0,0,0,.05);">--}%
                %{--</div>--}%
                %{--</form>--}%
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
            <div class="col-md-0"> </div>
            <div class="col-md-12">
                <div class="box-body">
                    <div class="row">
                        <div class="col-md-5 col-sm-12">

                            %{--<h6 class="text-muted text-semibold text-xs" style="margin:20px 0 10px 0;">Fechas Propuestas</h6>--}%
                            <label>Fechas</label>
                            <div class="input-daterange input-group " id="fecha">
                                <input type="text" class="input-sm form-control" name="fecha_inicio" id="fecha_inicio" placeholder="Fecha Inicio">
                                <span class="input-group-addon">Hasta</span>
                                <input type="text" class="input-sm form-control" name="fecha_fin" id="fecha_fin"   placeholder="Fecha Final">
                            </div>
                            %{--<hr class="panel-wide">--}%
                        </div>

                        <div class="col-md-4">
                            <label>Tipo de Búsqueda</label><br>
                            <input type="radio" value="0" id="tipobusqueda"  name="tipobusqueda" checked>Todas &nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="radio" value="1" id="tipobusqueda" name="tipobusqueda" >Pendientes &nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="radio" value="2" id="tipobusqueda"  name="tipobusqueda" >Pagadas &nbsp;&nbsp;&nbsp;&nbsp;
                        </div>
                        <div class="col-md-3">
                            <div class="form-group ">
                                <label>&nbsp;&nbsp;&nbsp;</label><br/>
                                <button id="boton_buscar"  class="btn btn-info"><i id="icono_cargando" style="display: none" class="fa fa-spinner fa-spin"></i> Buscar</button>
                                <button id="ver_estado_cuenta" class="btn btn-success"><i class="fa fa-eye"></i> Estado de cuenta</button>
                            </div>
                            %{--<div class="form-group ">--}%
                            %{--<label>&nbsp;&nbsp;&nbsp;</label><br/>--}%

                            %{--</div>--}%
                        </div>

                    </div>
                    <div class="row">


                        <div class="col-md-12 col-xl-12">
                            <div id="tabla_facturas">
                            </div>
                        </div>
                        <br/>
                        <div class="col-md-12 col-xl-12 " style="text-align: center">
                            <a  class="btn btn-primary" id="cerrar_reporte" style="display: none;cursor: hand;  margin-top: 4px;"><span class=" fa fa-angle-double-up"></span> Cerra Reporte</a>
                        </div>
                        <br/>
                        <div class="col-md-12 col-xl-12 " style=" margin-top: 4px;">

                            <div id="jqxLoader">
                            </div>

                        </div>

                    </div><!-- /.box -->

                </div>

            </div>
            <div class="col-md-0">

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

    </div>
</div>

<asset:javascript src="js_proyecto/reporteclientes/reportefacturas.js"/>
<g:javascript>
    $(document).ready(function () {
        ejecutar();

    });

</g:javascript>
</body>
</html>