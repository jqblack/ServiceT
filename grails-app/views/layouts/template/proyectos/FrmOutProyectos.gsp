<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Proyectos</title>
</head>

<body>


<div class="page-header">

    <div class="row">
        <!-- Page header, center on small screens -->
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-dashboard page-header-icon"></i>&nbsp;&nbsp;Proyecto</h1>

        <div class="col-xs-12 col-sm-8">
            <div class="row">
                <hr class="visible-xs no-grid-gutter-h">
                <!-- "Create project" button, width=auto on desktops -->
                <div class="pull-right col-xs-12 col-sm-auto"><a  class="btn btn-primary btn-labeled" id="nuevo_proyecto"  data-toggle="modal" data-target="#modal_proyectos" style="width: 100%;"><span class="btn-label icon fa fa-plus"></span>Nuevo Proyecto</a></div>

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

        <div class="row">
            <div class="col-md-1"> </div>
            <div class="col-md-10">
                <div id="tabla_proyectos">
                </div>
            </div>
            <div class="col-md-3">
                %{--Moddal del in clientes--}%


                <div id="modal_proyectos" class="modal fade" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                %{--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>--}%
                                <h4 class="modal-title">Registrar Proyecto</h4>
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

                                                    <div class="form-group has-feedback">
                                                        <label>Nombre Proyecto*</label>
                                                        <input type="" id="nombre" name="nombre" class="form-control" placeholder="Proyecto" required>
                                                        %{--<span class="glyphicon glyphicon-envelope form-control-feedback"></span>--}%
                                                    </div>
                                                </div><!-- /.box-body -->

                                                <div class="col-md-6 col-sm-12">

                                                    <div class="form-group has-feedback">
                                                        <label>Proyecto Padre</label>
                                                        <select class="form-control" id="padre" name="padre">
                                                            %{--<option value="0">--SELECCIONE--</option>--}%
                                                            <option value="0">--NINGUNO--</option>
                                                            <g:each in="${proyectos}">
                                                                <option value="${it.getId()}">${it.getfNombre()}</option>
                                                            </g:each>
                                                        </select>
                                                    </div>

                                                </div><!-- /.box-body -->

                                                <div class="col-md-6 col-sm-12">

                                                    <div class="form-group  has-feedback">
                                                        <label >Empleados</label>
                                                        <select class="form-control" id="empleado" name="empleado">
                                                            %{--<option value="0">--SELECCIONE--</option>--}%
                                                            <option value="0">--NINGUNO--</option>
                                                            <g:each in="${empleados}">
                                                                <option value="${it.getId()}">${it.getfDescripcion()}</option>
                                                            </g:each>
                                                        </select>
                                                    </div>
                                                </div>


                                                <div class="col-md-12 col-sm-12">

                                                    %{--<h6 class="text-muted text-semibold text-xs" style="margin:20px 0 10px 0;">Fechas Propuestas</h6>--}%
                                                    <label>Fechas Propuestas </label>
                                                    <div class="input-daterange input-group datepicker" id="fecha">
                                                        <input type="text" class="input-sm form-control" name="fecha_inicio_propuesta" id="fecha_inicio_propuesta" placeholder="Fecha Inicio">
                                                        <span class="input-group-addon">Hasta</span>
                                                        <input type="text" class="input-sm form-control" name="fecha_fin_propuesta" id="fecha_fin_propuesta"   placeholder="Fecha Final">
                                                    </div>
                                                    %{--<hr class="panel-wide">--}%
                                                </div>


                                                <div class="col-md-12 col-sm-12">

                                                    %{--<h6 class="text-muted text-semibold text-xs"  style="margin:20px 0 10px 0;">Fechas Reales</h6>--}%
                                                    <label>Fechas Reales </label>
                                                    <div class="input-daterange input-group datepicker" id="fecha_real">
                                                        <input type="text" class="input-sm form-control" name="fecha_inicio_real" id="fecha_inicio_real" placeholder="Fecha Inicio ">
                                                        <span class="input-group-addon">Hasta</span>
                                                        <input type="text" class="input-sm form-control" name="fecha_fin_real" id="fecha_fin_real" placeholder="Fecha Final">
                                                    </div>
                                                    %{--<hr class="panel-wide">--}%

                                                </div>


                                                <div class="col-sm-2 col-md-2">
                                                    <div class="form-group has-feedback">
                                                        <label>Congelado</label><br/>
                                                        <input type="checkbox" class="form-control" id="congelado" name="congelado"  value="true" >&nbsp;&nbsp;

                                                    </div>
                                                </div>
                                                <div class="col-sm-4 col-md-4">
                                                    <div class="form-group has-feedback">
                                                        <label>Porciento Realizado</label>
                                                        <input type="" id="porciento" name="porciento" class="form-control" placeholder="Proyecto" required>
                                                        %{--<span class="glyphicon glyphicon-envelope form-control-feedback"></span>--}%
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-6">

                                                </div>
                                                <div class="col-sm-12 col-md-12">
                                                    <div class="form-group  has-feedback">
                                                        <label for="comentario" class=" control-label">Comentario</label>
                                                        <textarea class="form-control" name="comentario" id="comentario"></textarea>
                                                        <p class="help-block">Comentario Acerca del Proyecto</p>
                                                    </div>
                                                </div>

                                                %{--</form>--}%
                                            </div><!-- /.box -->
                                        </div>


                                        <div class="box-footer">
                                            <div class="form-group has-feedback">
                                                %{--<a href="" class="btn btn-primary ">--}%
                                                %{--<span class="fa fa-arrow-left"></span> Atras--}%
                                                %{--</a>--}%
                                                <button type="" id="atras"  class="btn btn-primary" data-dismiss="modal" aria-hidden="true">  <span class="fa fa-arrow-left"></span>Atras</button>
                                                <button type="submit" id="salvar" class="btn btn-primary">Salvar</button>
                                            </div>

                                            <br>
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

<asset:javascript src="js_proyecto/proyectos/proyectos.js"/>
<script type="text/javascript" xmlns="http://www.w3.org/1999/html">

    $(document).ready(function () {
        ejecutar();
    });

    init.push(function () {
        $('#congelado').switcher();
        var options2 = {
            orientation: $('body').hasClass('right-to-left') ? "auto right" : 'auto auto'
        };
        $('#fecha').datepicker(options2);
        $('#fecha_real').datepicker(options2);
        $('#fecha_congelado').datepicker();
    })
</script>
</body>
</html>