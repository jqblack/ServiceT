<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="mainClientes">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Reporte Ordenes Clientes</title>
</head>

<body>


<div class="page-header">

    <div class="row">
        <!-- Page header, center on small screens -->
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-dashboard page-header-icon"></i>&nbsp;&nbsp;Ver Ordenes</h1>

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
                            <g:select class="form-control" name="miselect" id="miselect" from="${listsatus}" noSelection="['0':'Seleccione un status']" optionKey="id" optionValue="fDescripcion"></g:select>

                        </div>
                        <div class="col-md-3">
                            <div class="form-group ">
                                <label>&nbsp;&nbsp;&nbsp;</label><br/>
                                <button id="boton_buscar"  class="btn btn-info"><i id="icono_cargando" style="display: none" class="fa fa-spinner fa-spin"></i> Buscar</button>
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
</div>
</div>

<div class="modal fade" tabindex="-1" role="dialog" id="modalArchivos">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4>Archivos Ordenes</h4>
            </div>
            <div class="modal-body">

                <table class="table">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">Nombre</th>
                        <th scope="col">Acción</th>
                    </tr>
                    </thead>

                    <tbody id="bodytable">

                    </tbody>

                </table>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-danger" data-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>

<asset:javascript src="js_proyecto/reporteclientes/reportesOrdenes.js"/>
<g:javascript>
    $(document).ready(function () {
        ejecutar();

    });

</g:javascript>

</body>
</html>