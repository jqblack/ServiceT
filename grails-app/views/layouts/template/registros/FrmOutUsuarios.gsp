<!doctype html>
<html lang="en">
<head>
    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Usuarios</title>
</head>

<body>

<div class="page-header">
    %{--<div class="form-group has-feedback">--}%
    %{--<label>Activo</label><br/>--}%
    %{--<input type="checkbox" class="form-control" id="activo" >&nbsp;&nbsp;--}%

    %{--</div>--}%
    <div class="row">
        <!-- Page header, center on small screens -->
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-dashboard page-header-icon"></i>&nbsp;&nbsp;Usuarios</h1>

        <div class="col-xs-12 col-sm-8">
            <div class="row">
                <hr class="visible-xs no-grid-gutter-h">
                <!-- "Create project" button, width=auto on desktops -->
                <div class="pull-right col-xs-12 col-sm-auto"><a  class="btn btn-primary btn-labeled" id="nuevo_usuario"  data-toggle="modal" data-target="#modal_usuario" style="width: 100%;"><span class="btn-label icon fa fa-plus"></span>Nuevo Usuario</a></div>

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
                <div id="tabla_usuarios">
                </div>
            </div>
            <div class="col-md-3">
                %{--Moddal del in clientes--}%


                <div id="modal_usuario" class="modal fade" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                %{--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>--}%
                                <h4 class="modal-title">Registrar Usuario</h4>
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
                                                                <input type="" id="nombre" class="form-control" placeholder="Nombre" required>
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

                                                        <div class="col-md-6 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>E-mail</label>
                                                                <input type="" id="email" class="form-control" placeholder="E-mail" required>
                                                                <span class="fa fa-envelope form-control-feedback"></span>
                                                            </div>
                                                        </div>

                                                        <div class="col-md-6 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Telefono </label>
                                                                <input type="" id="telefono" class="form-control"
                                                                       placeholder="Telefono" required>
                                                                %{--<span class="fa fa-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>

                                                        <div class="col-md-6 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Usuario</label>
                                                                <input type="" id="usuario" class="form-control" placeholder="Usuario" required>
                                                                <span class="fa fa-user form-control-feedback"></span>
                                                            </div>
                                                        </div>

                                                        <div class="col-md-6 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Password</label>
                                                                <input type="password" id="password" class="form-control" placeholder="Password" required>
                                                                <span class="fa fa-unlock form-control-feedback"></span>
                                                                %{--<span class="glyphicon glyphicon-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>

                                                        <div class="col-md-12 col-sm-12">

                                                            <div class="form-group has-feedback">
                                                                <label>Direcicón</label>
                                                                <input type="" id="direccion" class="form-control" placeholder="Dirección" required>
                                                                %{--<span class="glyphicon glyphicon-envelope form-control-feedback"></span>--}%
                                                            </div>
                                                        </div>

                                                        <div class="col-md-4 col-sm-12 col-xl-12 col-xs-12 col-lg-4">

                                                            <div class="form-group has-feedback">
                                                                <label>Fecha de Caducidad</label>
                                                                <div class="input-group date" id="fecha">
                                                                    <input type="text" id="fecha_de_caducidad" class="form-control"><span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-2 col-sm-2 col-xl-2 col-xs-4 col-lg-2">

                                                            <div class="form-group has-feedback">
                                                                <label>Activo</label><br/>
                                                                <input type="checkbox" class="form-control" id="activo" >&nbsp;&nbsp;

                                                            </div>
                                                        </div>

                                                        <div class="col-md-6 col-sm-4 col-xl-4 col-xs-4 col-lg-6">
                                                            <div class="form-group has-feedback">
                                                                <label>Permisos</label><br/>
                                                                <button class="btn btn-dark-gray btn-labeled " id="permisos"  data-toggle="modal" data-target="#modal_permisos"><span class="btn-label icon fa fa-unlock"  ></span></button>
                                                            </div>
                                                        </div>

                                                    </div>




                                                </div>









                                            </div><!-- /.box-body -->
                                            <div class="box-footer">
                                                <div class="form-group has-feedback">
                                                    <div class="row">
                                                        <button type="" id="atras" class="btn btn-primary" data-dismiss="modal"   aria-hidden="true"><span class="fa fa-arrow-left"></span>Atras</button>
                                                        <button type="submit" id="salvar"    class="btn btn-primary">Salvar</button>
                                                    </div>

                                                </div>

                                                <br></br>

                                            </div>
                                            %{--</form>--}%
                                        </div><!-- /.box -->
                                    </div>

                                </div>
                                <div class="col-md-1 col-sm-1 col-xs-1 col-lg-1 col-xl-1 "></div>

                            </div> <!-- / .modal-content -->
                        </div> <!-- / .modal-dialog -->
                    </div> <!-- / .modal -->
                <!-- / Large modal -->
                </div>








                <div id="modal_permisos" class="modal fade" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-sm">
                        <div class="modal-content">
                            <div class="modal-header ">
                                %{--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>--}%
                                <h4 class="modal-title">Registrar Permisos</h4>
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
                                            <br></br>
                                            <div class="row">

                                                <div class="col-md-12 col-sm-12">

                                                    <div class="row">
                                                        <div class="col-md-12 col-sm-12">
                                                            <div id="container_tabla_permisos">
                                                                <div id="tabla_permisos"></div>
                                                            </div>

                                                        </div>

                                                    </div>




                                                </div>

                                            </div><!-- /.box-body -->
                                            <br></br>
                                            <div class="box-footer">
                                                <div class="form-group has-feedback">
                                                    <button type="" id="atras_permisos" class="btn btn-primary" data-dismiss="modal"   aria-hidden="true"><span class="fa fa-arrow-left"></span>Atras</button>
                                                    <button type="submit" id="salvar_permisos"    class="btn btn-primary">Salvar</button>
                                                </div>

                                                %{--<br></br>--}%

                                            </div>
                                            %{--</form>--}%
                                        </div><!-- /.box -->
                                    </div>

                                </div>
                                <div class="col-md-1 col-sm-1 col-xs-1 col-lg-1 col-xl-1 "></div>

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


<asset:javascript src="js_proyecto/registros/usuarios.js"/>

<g:javascript>
    $(document).ready(function () {
        ejecutar();
    })

    init.push(function () {
        $('#activo').switcher();
        $('#fecha').datepicker();
    });

</g:javascript>
</body>
</html>