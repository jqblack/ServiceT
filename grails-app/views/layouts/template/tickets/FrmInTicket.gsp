<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="mainTickets">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Crear Ticket</title>
</head>

<body>

%{--<%--}%


    %{--org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest request = org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes();--}%
    %{--org.codehaus.groovy.grails.web.servlet.mvc.GrailsHttpSession session = request.session;--}%
    %{--Locale locale = new Locale(session.getAttribute("idioma"));--}%



%{--%>--}%

<style>
.btn-file {
    position: relative;
    overflow: hidden;
}
.btn-file input[type=file] {
    position: absolute;
    top: 0;
    right: 0;
    min-width: 100%;
    min-height: 100%;
    font-size: 100px;
    text-align: right;
    filter: alpha(opacity=0);
    opacity: 0;
    outline: none;
    background: deepskyblue;
    cursor: inherit;
    display: block;
}
</style>



<div class="page-header">

    <div class="row">
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-ticket page-header-icon"></i>Nuevo Ticket</h1>
    </div>

</div> <!-- / .page-header -->
<div class="panel">

    <div class="panel-body">
        <div class="row">
            <div class="col-md-6 col-sm-12">
                <div class="row form-group">
                    <label class="col-sm-4 control-label">${session.Grails.i18n("proyecto",session.locale)}:</label>
                    <select id="proyecto" class="form-control form-group-margin">
                        <option value="0">--${session.Grails.i18n("seleccionar",session.locale)}--</option>
                        <%

                            for (Map<String,java.lang.Object> map : proyectos){
                        %>
                        <option value="${map.get("f_idproyecto")}">${map.get("f_nombre")} </option>
                        <%
                            }
                        %>
                    </select>
                </div>
                <input type="text" hidden value="${session.tusuario.id}" id="idUsuario">

                <div class="row form-group">
                    <label class="col-sm-4 control-label">${session.Grails.i18n("prioridad",session.locale)}:</label>
                    <select id="prioridad" class="form-control form-group-margin">
                        <option value="0">--${session.Grails.i18n("seleccionar",session.locale)}--</option>
                        %{--<option value="1">Error</option>--}%
                        <optgroup label="Error">

                            <%

                                for (Map<String,java.lang.Object> map : lista_prioridades){

                                    if(map.get("f_tipo_tk").toString().equals("1"))
                                    {



                            %>
                            <option  value="${map.get("f_idrecord")}">E - ${map.get("f_descripcion")} </option>
                            %{--<option <% if (map.get("f_prioridad").toString().equals("1")){%>style="background-color: red;color: white" <%}else if (map.get("f_prioridad").toString().equals("2")){%>style="background-color: orangered;color: white"<%}else if (map.get("f_prioridad").toString().equals("3")){%>style="background-color: #00a0da;color: white"<%}else if (map.get("f_prioridad").toString().equals("4")){%>style="background-color: green;color: white"<%}%> value="${map.get("f_idrecord")}">E - ${map.get("f_descripcion")} </option>--}%
                            <%
                                    }
                                }
                            %>

                        </optgroup>
                        <optgroup label="Peticion">

                            <%

                                for (Map<String,java.lang.Object> map : lista_prioridades){

                                    if(map.get("f_tipo_tk").toString().equals("2"))
                                    {



                            %>
                            <option  value="${map.get("f_idrecord")}">P - ${map.get("f_descripcion")} </option>
                            %{--<option  <% if (map.get("f_prioridad").toString().equals("1")){%>style="background-color: red;color: white" <%}else if (map.get("f_prioridad").toString().equals("2")){%>style="background-color: orangered;color: white"<%}else if (map.get("f_prioridad").toString().equals("3")){%>style="background-color: #00a0da;color: white"<%}else if (map.get("f_prioridad").toString().equals("4")){%>style="background-color: green;color: white"<%}%> value="${map.get("f_idrecord")}">P - ${map.get("f_descripcion")} </option>--}%
                            <%
                                    }
                                }
                            %>

                        </optgroup>

                    </select>
                </div>



                <div class="row form-group">
                    <label class="col-sm-4 control-label">${session.Grails.i18n("titulo",session.locale)}:</label>
                    <input type="text" name="name" id="titulo" class="form-control" maxlength="100">
                </div>

                <div class="col-md-2">
                    <div class="form-group has-feedback">
                        <label>Broadcast</label><br/>
                        <input type="checkbox" class="form-control" id="broadcast">&nbsp;&nbsp;
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group has-feedback">
                        <label>${session.Grails.i18n("tipo_tickets",session.locale)}</label><br/>
                        <select id="tipo_tk" class="form-control form-group-margin">
                            <option value="0">--${session.Grails.i18n("seleciconar",session.locale)}--</option>
                            <%

                                for (Map<String,java.lang.Object> map : lista_tipo_tickets){

                                    if(session.getAttribute("idioma").toString().equals("es"))
                                    {



                            %>
                            <option  value="${map.get("f_idrecord")}">${map.get("f_descripcion")} </option>
                            %{--<option  <% if (map.get("f_prioridad").toString().equals("1")){%>style="background-color: red;color: white" <%}else if (map.get("f_prioridad").toString().equals("2")){%>style="background-color: orangered;color: white"<%}else if (map.get("f_prioridad").toString().equals("3")){%>style="background-color: #00a0da;color: white"<%}else if (map.get("f_prioridad").toString().equals("4")){%>style="background-color: green;color: white"<%}%> value="${map.get("f_idrecord")}">P - ${map.get("f_descripcion")} </option>--}%
                            <%
                                }else {

                            %>
                            <option  value="${map.get("f_idrecord")}">${map.get("f_descripcion_ingles")} </option>
                            %{--<option  <% if (map.get("f_prioridad").toString().equals("1")){%>style="background-color: red;color: white" <%}else if (map.get("f_prioridad").toString().equals("2")){%>style="background-color: orangered;color: white"<%}else if (map.get("f_prioridad").toString().equals("3")){%>style="background-color: #00a0da;color: white"<%}else if (map.get("f_prioridad").toString().equals("4")){%>style="background-color: green;color: white"<%}%> value="${map.get("f_idrecord")}">P - ${map.get("f_descripcion")} </option>--}%
                            <%

                                    }
                                }
                            %>
                        </select>

                    </div>
                </div>

                <div id="jqxLoader"  style="margin-top: 35%;margin-right: 35px;">
                </div>


            </div>


            <div class="col-md-6 col-sm-12">
                <br>
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <span class="panel-title">${session.Grails.i18n("archivos_cargados",session.locale)}.</span>
                        <div class="panel-heading-controls">
                            <div class="panel-heading-icon"><i class="fa fa-cloud-upload"></i></div>
                        </div>
                    </div>
                    <div id="panel_archivos" class="panel-body">
                        <div id="FileUpload"></div>
                    </div>
                </div>


            </div>

            <div class="col-md-12">

                <div class="row form-group">
                    <label class="col-sm-4 control-label">${session.Grails.i18n("descripcion",session.locale)}:</label>
                    <textarea class="form-control" id="descripcion" rows="5"></textarea>
                </div>


                <div class="panel-footer text-right">
                    <div id="output"></div>
                    %{--<span class="btn btn-default btn-file">--}%
                    %{--Cargar archivo <input type="file" id="files" />--}%
                    %{--</span>--}%
                    <button class="btn btn-info btn-flat" id="nuevo">${session.Grails.i18n("nuevo",session.locale)}</button>
                    <button class="btn btn-primary btn-flat" id="salvar">${session.Grails.i18n("salvar",session.locale)}</button>
                </div>
            </div>







        </div>

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

<asset:javascript src="js_proyecto/registros/ticket.js"/>
<g:javascript>
    $(document).ready(function () {
        ejecutar();
        $('#broadcast').switcher();
    })
</g:javascript>
</body>
</html>