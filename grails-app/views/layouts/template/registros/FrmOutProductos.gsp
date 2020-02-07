<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Productos</title>
</head>

<body>

<div class="page-header">

    <div class="row">
        <!-- Page header, center on small screens -->
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-dashboard page-header-icon"></i>&nbsp;&nbsp;Productos</h1>

        <div class="col-xs-12 col-sm-8">
            <div class="row">
                <hr class="visible-xs no-grid-gutter-h">
                <!-- "Create project" button, width=auto on desktops -->
                <div class="pull-right col-xs-12 col-sm-auto"><a  class="btn btn-primary btn-labeled" id="nuevo_producto"  data-toggle="modal" data-target="#modal_clientes" style="width: 100%;"><span class="btn-label icon fa fa-plus"></span>Nuevo Producto</a></div>

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
            <div class="col-md-10 col-sm-12 ">
                <div class="form-group has-feedback pull-right" >
                    <button type="" id="mostrar_todos"  class="btn btn-primary "  data-original-title="Mostrar Todos" data-dismiss="modal" aria-hidden="true">  <span class="fa fa-square-o"></span></button>
                    <button type="" id="solo_activos"  class="btn btn-success  " data-original-title="Solo Activos" data-dismiss="modal" aria-hidden="true">  <span class="fa fa-check-square-o"></span></button>
                    %{--<button type="submit" id="salvar" class="btn btn-primary"><span class="fa fa-save"></span>&nbsp;&nbsp;Salvar</button>--}%
                </div>
            </div>
            <div class="col-md-1"> </div>
        </div>
        <div class="row">
            <div class="col-md-1"> </div>
            <div class="col-md-10">
                <div id="tabla_productos">
                </div>
            </div>
            <div class="col-md-3">
                %{--Moddal del in clientes--}%


                <div id="modal_clientes" class="modal fade" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                %{--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>--}%
                                <h4 class="modal-title">Registrar Produtos</h4>
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



                                                    <div class="row">
                                                        <div class="col-md-4 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Referencia</label>
                                                                <input type="" id="referencia" class="form-control"
                                                                       placeholder="Referencia" required>
                                                                %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>

                                                        <div class="col-md-8 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Descripción</label>
                                                                <input type="" id="descripcion" class="form-control" placeholder="Descripción" required>
                                                                %{--<span class="glyphicon glyphicon-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>

                                                    </div>

                                                    <div class="row">
                                                        <div class="col-md-4 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Versión</label>
                                                                <input type="" id="version" class="form-control"
                                                                       placeholder="Versión" required>
                                                                %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>

                                                        <div class="col-md-8 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Sistema</label>
                                                                <input type="" id="sistema" class="form-control" placeholder="Sistema" required>
                                                                %{--<span class="glyphicon glyphicon-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>

                                                    </div>

                                                    <div class="row">

                                                        <div class="col-md-2 col-sm-6 col-xs-6 col-xl-4 col-lg-2">
                                                            <div class="form-group has-feedback">
                                                                <label>Precio</label>
                                                                <input type="" id="precio"  class="form-control" placeholder="Precio" required style="text-align: right">

                                                            </div>
                                                        </div>
                                                        <div class="col-md-2 col-sm-6 col-xs-6 col-xl-4 col-lg-2">
                                                            <div class="form-group has-feedback">
                                                                <label>Cantidad</label>
                                                                <input type="" id="cantidad"  class="form-control" placeholder="Cantidad" required style="text-align: right">

                                                            </div>
                                                        </div>
                                                        <div class="col-md-2 col-sm-3 col-xs-6 col-xl-4 col-lg-2">
                                                            <div class="form-group has-feedback">
                                                                <label>Servicio</label><br/>
                                                                <input type="checkbox" class="form-control" id="es_servicio" >&nbsp;&nbsp;

                                                            </div>
                                                        </div>
                                                        <div class="col-md-2 col-sm-3 col-xs-6 col-xl-4 col-lg-2">
                                                            <div class="form-group has-feedback">
                                                                <label>Serial</label><br/>
                                                                <input type="checkbox"  class="form-control" id="genera_serial">&nbsp;&nbsp;

                                                            </div>
                                                        </div>
                                                        <div class="col-md-2 col-sm-3 col-xs-6 col-xl-4 col-lg-2">
                                                            <div class="form-group has-feedback">
                                                                <label>Tiene Itbis</label><br/>
                                                                <input type="checkbox"  class="form-control" id="tiene_itebis">&nbsp;&nbsp;

                                                            </div>
                                                        </div>
                                                        <div class="col-md-2 col-sm-3 col-xs-3 col-xl-4 col-lg-2">
                                                            <div class="form-group has-feedback">
                                                                <label>Activo</label><br/>
                                                                <input type="checkbox"  class="form-control" id="activo">&nbsp;&nbsp;

                                                            </div>
                                                        </div>

                                                    </div>






                                                    <div class="box-footer">
                                                        <div class="form-group has-feedback">
                                                            %{--<a href="" class="btn btn-primary ">--}%
                                                            %{--<span class="fa fa-arrow-left"></span> Atras--}%
                                                            %{--</a>--}%
                                                            <button type="" id="atras"  class="btn btn-primary" data-dismiss="modal" aria-hidden="true">  <span class="fa fa-arrow-left"></span>Atras</button>
                                                            <button type="submit" id="salvar" class="btn btn-primary">Salvar</button>
                                                        </div>

                                                        <br></br>

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

<asset:javascript src="js_proyecto/registros/productos.js"/>

<g:javascript>
    $(document).ready(function () {
        ejecutar();
    })


    init.push(function () {
        $('#es_servicio').switcher();
        $('#genera_serial').switcher();
        $('#tiene_itebis').switcher();
        $('#activo').switcher();

        $('#mostrar_todos').tooltip();
        $('#solo_activos').tooltip();

    });


</g:javascript>

</body>
</html>