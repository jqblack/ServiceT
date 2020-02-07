<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Conocimientos</title>
</head>

<body>


<div class="page-header">

    <div class="row">
        <!-- Page header, center on small screens -->
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i
                class="fa fa-dashboard page-header-icon"></i>&nbsp;&nbsp;Conocimiento</h1>

        <div class="col-xs-12 col-sm-8">
            <div class="row">
                <hr class="visible-xs no-grid-gutter-h">
                <!-- "Create project" button, width=auto on desktops -->
                <div class="pull-right col-xs-12 col-sm-auto"><a class="btn btn-primary btn-labeled"
                                                                 id="nuevo_conocimiento" data-toggle="modal"
                                                                 data-target="#modal_conocimiento"
                                                                 style="width: 100%;"><span
                            class="btn-label icon fa fa-plus"></span>Nuevo Conocimiento</a></div>

                <!-- Margin -->
                <div class="visible-xs clearfix form-group-margin"></div>

            </div>
        </div>
    </div>
</div> <!-- / .page-header -->
<div class="panel">
    %{--<a href="${getRequest().getContextPath()}/conocimiento/ver_solucion">ver</a>--}%
    <div class="panel-body">

        <div class="row">
            <div class="col-md-1"></div>

            <div class="col-md-10">
                <div id="tabla_conocimiento">
                </div>

            </div>

            <div class="col-md-3">
                %{--Moddal del in clientes--}%


                <div id="modal_conocimiento" class="modal fade" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">Registrar Conocimiento</h4>
                            </div>

                            <div class="row">
                                <input type="hidden" id="idrecord" class="form-control" placeholder="idrecord  ">

                                <div class="col-md-2 col-sm-1 col-xs-1 col-lg-1 col-xl-1 "></div>

                                <div class="col-md-8 col-sm-10 col-xs-10 col-lg-10 col-xl-10 ">
                                    <div class="box box-primary">
                                        <div class="box-header with-border">
                                        </div><!-- /.box-header -->

                                        <div class="box-body">
                                            <div class="row">
                                                <div class="col-md-12 col-sm-12">

                                                    <div class="row">
                                                        <div class="col-md-8 col-sm-12">

                                                            <div class="form-group ">
                                                                <label>Titulo</label>
                                                                <input type="" id="titulo" class="form-control"
                                                                       placeholder="Titulo" required>

                                                            </div>
                                                        </div>

                                                        <div class="col-md-4 col-sm-12">

                                                            <div class="form-group ">
                                                                <label>Categoria</label>
                                                                <select class="form-control" id="categoria">
                                                                    <option value="">-SELECCIONAR-</option>
                                                                    <g:each in="${categorias}">
                                                                        <option value="${it.id}">${it.fNombre}</option>
                                                                    </g:each>
                                                                </select>
                                                            </div>
                                                        </div>

                                                    </div>

                                                    <div class="row">
                                                        <div class="col-md-12 col-sm-12 col-xl-12 col-lg-12">

                                                            <textarea id="solucion">

                                                            </textarea>
                                                        </div>

                                                    </div>

                                                    <div class="row">

                                                        <div class="col-md-4 col-sm-12 col-xl-12 col-lg-4">

                                                            <div class="form-group has-feedback">
                                                                <label>Cargar Documento</label>

                                                                <div id="documento">
                                                                </div>

                                                            </div>
                                                        </div>

                                                        <div class="col-md-8 col-sm-12 col-xl-12 col-lg-8">

                                                            <div class="form-group has-feedback">
                                                                <label>Tags</label><br/>
                                                                <a id="tags" data-type="select2" data-pk="1"
                                                                   data-title="Tags">JQServicios</a>
                                                            </div>
                                                        </div>

                                                    </div>

                                                    <div class="box-footer">
                                                        <div class="form-group has-feedback">
                                                            %{--<a href="" class="btn btn-primary ">--}%
                                                            %{--<span class="fa fa-arrow-left"></span> Atras--}%
                                                            %{--</a>--}%
                                                            <button type="" id="atras" class="btn btn-primary"
                                                                    data-dismiss="modal" aria-hidden="true"><span
                                                                    class="fa fa-arrow-left"></span>Atras</button>
                                                            <button type="submit" id="salvar"
                                                                    class="btn btn-primary">Salvar</button>
                                                        </div>

                                                        <br></br>

                                                    </div>
                                                </div><!-- /.box-body -->
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
                                <button id="ok_notifificacion_no" type="button" class="btn btn-danger"
                                        data-dismiss="modal" aria-hidden="true">OK</button>
                            </div>
                        </div> <!-- / .modal-content -->
                    </div> <!-- / .modal-dialog -->
                </div> <!-- / .modal -->
            <!-- / Danger -->

                <button id="notificacion_si" style="display:none" class="btn btn-success" data-toggle="modal"
                        data-target="#uidemo-modals-alerts-success">Success</button>&nbsp;&nbsp;&nbsp;
                <button id="notificacion_no" style="display:none" class="btn btn-danger" data-toggle="modal"
                        data-target="#uidemo-modals-alerts-danger">Danger</button>&nbsp;&nbsp;&nbsp;

            </div>
        </div>


        <div class="row">

            <div class="col-md-1 col-lg-1"></div>

            <div class="col-md-10 col-lg-10">
                %{--data-toggle="modal" data-target="#modal_documento"--}%
                <button id="tag_documento" style="display: none;"
                        class="label label-primary label-tag">Documento</button>
            </div>

        </div>
        <br/>


        <div class="row">

            <div class="col-md-1 col-lg-1"></div>

            <div class="col-md-10 col-lg-10">
                <div class="panel panel-primary" hidden id="panel_solucion">
                    <div class="panel-heading ">
                        <span class="panel-title" id="titulo_solucion" style="color:white;">SOLUCIÓN</span>
                    </div>

                    <div class="panel-body">
                        <span id="texto_solucion">NO HAY DATOS</span>

                    </div>
                </div>
            </div>

            <div class="col-md-1 col-lg-1"></div>
        </div>
</div>
</div>

<asset:javascript src="js_proyecto/conocimiento/conocimiento.js"/>
<g:javascript>
    $(document).ready(function () {
        ejecutar();
    })
    init.push(function () {
        $('#enviar').switcher();
        $('#activo').switcher();


        $('#tags').editable({
            select2: {
                defaultValue: ['Example'],
                tags: ['Programacion'],
                tokenSeparators: [",", " "]
            }
        });
    });
</g:javascript>

</body>
</html>