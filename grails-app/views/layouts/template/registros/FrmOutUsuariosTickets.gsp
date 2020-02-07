<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Usuario Tickets</title>
</head>

<body>


<div class="page-header">

    <div class="row">
        <!-- Page header, center on small screens -->
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-dashboard page-header-icon"></i>&nbsp;&nbsp;Seriales Formula</h1>

        <div class="col-xs-12 col-sm-8">
            <div class="row">
                <hr class="visible-xs no-grid-gutter-h">
                <!-- "Create project" button, width=auto on desktops -->
                %{--<div class="pull-right col-xs-12 col-sm-auto"><a  class="btn btn-primary btn-labeled" id="nuevo_serial"  data-toggle="modal" data-target="#modal_serial" style="width: 100%;"><span class="btn-label icon fa fa-plus"></span>Nuevo Serial</a></div>--}%

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

            <div class="col-md-4">
                <div id="tabla_datos">
                </div>
            </div>
            <div class="col-md-7">

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

                    <div class="col-md-6 col-sm-12">

                        <div class="form-group has-feedback">
                            <label>E-mail</label>
                            <input type="" id="email" class="form-control" placeholder="E-mail" required>
                            <span class="fa fa-envelope form-control-feedback"></span>
                        </div>
                    </div>


                    <div class="col-md-2 col-sm-2 col-xl-2 col-xs-4 col-lg-2">

                        <div class="form-group has-feedback">
                            <label>Activo</label><br/>
                            <input type="checkbox" class="form-control" id="activo" >&nbsp;&nbsp;

                        </div>
                    </div>



                </div>
                <div class="row">
                    <div class="col-md-12 col-sm-12">

                        <div class="form-group has-feedback">
                            <label>Cliente</label>
                            <select name="cliente" id="cliente" class="form-control">
                                <option value="0">--SELECCIONE UN CLIENTE--</option>
                                <%

                                    for (Map<String,Object> tupla : lista_cliente)
                                    {
                                %>
                                <option value="${tupla.get("f_id")}">${tupla.get("f_id")} - ${tupla.get("f_nombre_empresa")}</option>
                                <%
                                    }


                                %>



                            </select>
                        </div>
                    </div>

                </div>

                <div class="row">
                    <di class="col-md-12">
                        <div class="box-footer">
                            <div class="form-group has-feedback">
                                %{--<a href="" class="btn btn-primary ">--}%
                                %{--<span class="fa fa-arrow-left"></span> Atras--}%
                                %{--</a>--}%
                                <button type="submit" id="nuevo" class="btn btn-danger">Nuevo</button>
                                <button type="submit" id="salvar" class="btn btn-primary">Salvar</button>
                            </div>

                            <br></br>

                        </div>
                    </di>

                </div>

            </div>







        </div>


        <div class="col-md-3">
            %{--Moddal del in clientes--}%



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


<asset:javascript src="js_proyecto/registros/usuarios_tickets.js"/>

<g:javascript>
    $(document).ready(function () {
        ejecutar();
    })

    init.push(function () {
        $('#activo').switcher();
    });

</g:javascript>
</body>
</html>