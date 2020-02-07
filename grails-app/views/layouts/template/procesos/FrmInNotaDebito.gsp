<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Nota de Débito</title>
</head>

<body>


<div class="page-header">

    <div class="row">
        <!-- Page header, center on small screens -->
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-money page-header-icon"></i>&nbsp;&nbsp;Nota De Debito</h1>

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
                                    <div class="col-md-12 col-sm-12">

                                        <div class="form-group ">
                                            <label>Cliente</label>
                                            <input type="hidden" id="id_cliente" class="form-control" placeholder="Nombre">

                                            <div class="input-group ">
                                                <input type="" id="cliente"  readonly class="form-control" placeholder="Cliente" required>
                                                <span class="input-group-btn">
                                                    <button class="btn btn-primary" type="submit" id="buscar_cliente" data-toggle="modal" data-target="#modal_clientes">
                                                        <span class="fa fa-search"></span>
                                                    </button>
                                                </span>
                                            </div>
                                            %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                        </div>
                                    </div>
                                    %{--<div class="col-md-2 col-sm-12">--}%

                                    %{--<div class="form-group ">--}%
                                    %{--<label>Fecha</label>--}%
                                    %{--<div class="input-group date" id="piker_fecha">--}%
                                    %{--<input id="fecha" type="text" class="form-control"><span class="input-group-addon"><i class="fa fa-calendar"></i></span>--}%
                                    %{--</div>--}%
                                    %{--<div id="fecha">--}%
                                    %{--</div>--}%
                                    %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                    %{--</div>--}%
                                    %{--</div>--}%

                                    <div class="col-md-12 col-sm-12">

                                        <div class="form-group ">
                                            <label>Direcció</label>
                                            <input type="" readonly id="direccion" class="form-control" placeholder="Dirección" required>
                                            %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                        </div>
                                        %{--</div>--}%
                                    </div>


                                    %{--<div class="form-group ">--}%
                                    %{--<label>Nota</label>--}%
                                    %{--<input type="" id="nota"   class="form-control" placeholder="Nota" required>--}%
                                    %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                    %{--</div>--}%
                                    %{--</div>--}%

                                </div>

                                <div class="row">
                                    <div class="col-md-4 col-sm-12 col-xl-12 col-lg-3">

                                        <div class="form-group ">
                                            <label>Fecha</label>
                                            <div id="fecha">
                                            </div>
                                            %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                        </div>
                                    </div>
                                    <div class="col-md-4 col-sm-12 col-xl-12 col-lg-2">

                                        <div class="form-group has-feedback">
                                            <label>Monto</label>
                                            <input type="" id="monto" class="form-control" placeholder="Monto" required>
                                            %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                        </div>
                                    </div>
                                    <div class="col-md-0 col-sm-0 col-xl-0 col-lg-7">

                                    </div>

                                </div>


                                <div class="row">
                                    <div class="col-md-12 col-sm-12 col-lg-12">

                                        <div class="form-group ">
                                            <label>Comentario</label>
                                            <input type="" id="comentario" class="form-control" placeholder="Comentario" required>
                                            %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                        </div>
                                    </div>

                                </div>



                                %{--<div class="row">--}%
                                %{--<div class="col-md-12">--}%
                                %{--<div id="tabla_datos">--}%

                                %{--</div>--}%
                                %{--</div>--}%
                                %{--</div>--}%

                                <br/>
                                <div class="box-footer">
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

                %{--modal clientes--}%



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

<asset:javascript src="js_proyecto/procesos/nota_debito.js"/>
<g:javascript>
    $(document).ready(function () {
        ejecutar();
        $('#piker_fecha').datepicker();
    })

</g:javascript>
</body>
</html>