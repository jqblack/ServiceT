<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Referentes</title>
</head>

<body>


<div class="page-header">

    <div class="row">
        <!-- Page header, center on small screens -->
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-dashboard page-header-icon"></i>&nbsp;&nbsp;Referentes</h1>

        <div class="col-xs-12 col-sm-8">
            <div class="row">
                <hr class="visible-xs no-grid-gutter-h">
                <!-- "Create project" button, width=auto on desktops -->
                <div class="pull-right col-xs-12 col-sm-auto"><a  class="btn btn-primary btn-labeled" id="nuevo_referente"  data-toggle="modal" data-target="#modal_referente" style="width: 100%;"><span class="btn-label icon fa fa-plus"></span>Nuevo Referente</a></div>

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
            <div class="col-md-1"> </div>
            <div class="col-md-10">
                <div id="tabla_referentes">
                </div>
            </div>
            <div class="col-md-3">
                %{--Moddal del in clientes--}%


                <div id="modal_referente" class="modal fade" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                %{--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>--}%
                                <h4 class="modal-title">Registrar Referente</h4>
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
                                                        <div class="col-md-6 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Nombre</label>
                                                                <input type="" id="nombre" class="form-control"
                                                                       placeholder="Nombre" required>
                                                                %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>

                                                        <div class="col-md-6 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Apellidos</label>
                                                                <input type="" id="apellido" class="form-control" placeholder="Apellidos" required>
                                                                %{--<span class="glyphicon glyphicon-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>

                                                    </div>

                                                    <div class="row">
                                                        <div class="col-md-6 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>E-mail</label>
                                                                <input type="" id="email" class="form-control"
                                                                       placeholder="E-mail" required>
                                                                <span class="fa fa-envelope form-control-feedback"></span>
                                                            </div>
                                                        </div>

                                                        <div class="col-md-6 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Cuenta Deposito</label>
                                                                <input type="" id="cuenta_deposiito" class="form-control" placeholder="Cuenta Deposito" required>
                                                                %{--<span class="glyphicon glyphicon-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>
                                                        <div class="col-md-4 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Banco</label>
                                                                <input type="" id="banco" class="form-control" placeholder="Banco" required>
                                                                %{--<span class="glyphicon glyphicon-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>
                                                        <div class="col-md-8 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Direcicón</label>
                                                                <input type="" id="direccion" class="form-control" placeholder="Dirección" required>
                                                                %{--<span class="glyphicon glyphicon-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>

                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-6 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Ciudad</label>
                                                                <input type="" id="ciudad" class="form-control"
                                                                       placeholder="Ciudad" required>
                                                                %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>

                                                        <div class="col-md-6 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Pais</label>
                                                                <input type="" id="pais" class="form-control" placeholder="Pais" required>
                                                                %{--<span class="glyphicon glyphicon-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>

                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Telefono 1</label>
                                                                <input type="" id="telefono1" class="form-control"
                                                                       placeholder="Telefono" required>
                                                                %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>

                                                        <div class="col-md-4 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Telefono 2</label>
                                                                <input type="" id="telefono2" class="form-control"
                                                                       placeholder="Telefono" required>
                                                                %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>

                                                        <div class="col-md-4 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Porcentaje</label>
                                                                <input type="" id="porcentaje" class="form-control"
                                                                       placeholder="Porcentaje" required>
                                                                %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>

                                                    </div>

                                                    %{--<div class="row">--}%

                                                    %{--<div class="col-md-2 col-sm-4 col-xs-4 col-xl-4 col-lg-2">--}%
                                                    %{--<div class="form-group has-feedback">--}%
                                                    %{--<label>Precio</label>--}%
                                                    %{--<input type="" id="precio"  class="form-control" placeholder="Precio" required style="text-align: right">--}%

                                                    %{--</div>--}%
                                                    %{--</div>--}%
                                                    %{--<div class="col-md-2 col-sm-4 col-xs-4 col-xl-4 col-lg-2">--}%
                                                    %{--<div class="form-group has-feedback">--}%
                                                    %{--<label>Cantidad</label>--}%
                                                    %{--<input type="" id="cantidad"  class="form-control" placeholder="Cantidad" required style="text-align: right">--}%

                                                    %{--</div>--}%
                                                    %{--</div>--}%
                                                    %{--<div class="col-md-2 col-sm-4 col-xs-4 col-xl-4 col-lg-2">--}%
                                                    %{--<div class="form-group has-feedback">--}%
                                                    %{--<label>Servicio</label><br/>--}%
                                                    %{--<input type="checkbox" class="form-control" id="es_servicio" >&nbsp;&nbsp;--}%

                                                    %{--</div>--}%
                                                    %{--</div>--}%
                                                    %{--<div class="col-md-2 col-sm-4 col-xs-4 col-xl-4 col-lg-2">--}%
                                                    %{--<div class="form-group has-feedback">--}%
                                                    %{--<label>Serial</label><br/>--}%
                                                    %{--<input type="checkbox"  class="form-control" id="genera_serial">&nbsp;&nbsp;--}%

                                                    %{--</div>--}%
                                                    %{--</div>--}%
                                                    %{--<div class="col-md-2 col-sm-4 col-xs-4 col-xl-4 col-lg-2">--}%
                                                    %{--<div class="form-group has-feedback">--}%
                                                    %{--<label>Tiene Itbis</label><br/>--}%
                                                    %{--<input type="checkbox"  class="form-control" id="tiene_itebis">&nbsp;&nbsp;--}%

                                                    %{--</div>--}%
                                                    %{--</div>--}%
                                                    %{--<div class="col-md-2 col-sm-4 col-xs-4 col-xl-4 col-lg-2">--}%
                                                    %{--<div class="form-group has-feedback">--}%


                                                    %{--</div>--}%
                                                    %{--</div>--}%

                                                    %{--</div>--}%






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


<asset:javascript src="js_proyecto/registros/referentes.js"/>

<g:javascript>
    $(document).ready(function () {
        ejecutar();
    })

</g:javascript>

</body>
</html>