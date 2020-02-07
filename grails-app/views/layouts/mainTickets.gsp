<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>

    %{--TEMPLATE--}%
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,400,600,700,300&subset=latin" rel="stylesheet" type="text/css">

    <!-- Pixel Admin's stylesheets -->
    <asset:stylesheet href="assets/stylesheets/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/widgets.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/pixel-admin.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/rtl.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/themes.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/pages.min.css" rel="stylesheet" type="text/css"/>

    <asset:stylesheet src="jqwidgets/jqwidgets/styles/jqx.base.css"/>
    <asset:stylesheet src="jqwidgets/jqwidgets/styles/jqx.material.css"/>


    <asset:javascript src="assets/javascripts/jquery-3.4.1.min.js" />
    <asset:javascript src="assets/javascripts/bootstrap.min.js"/>



</head>

<body class="theme-default main-menu-animated main-navbar-fixed page-search">
<script>var init = [];</script>


<div id="main-wrapper">


    <!-- 2. $MAIN_NAVIGATION =========================================================================== Main navigation-->
    <input type="hidden" id="itebis_preferencia" class="form-control" placeholder="Nombre" value="${session.tpreferencia.fTax}">
    <input type="hidden" id="idioma" class="form-control" placeholder="idioma" value="${session.locale.getLanguage()}">
    <div id="main-navbar" class="navbar navbar-inverse" role="navigation">
        <!-- Main menu toggle -->
        <button type="button" id="main-menu-toggle"><i class="navbar-icon fa fa-bars icon"></i><span class="hide-menu-text">HIDE MENU</span></button>

        <div class="navbar-inner">
            <!-- Main navbar header -->
            <div class="navbar-header">

                %{--<!-- Logo -->--}%
                %{--<a href="${getRequest().getContextPath()}/index" class="navbar-brand">--}%
                %{--<div> <img src="${resource(dir: "/images", file: "avatar.png")}" class="message-avatar" alt=""></div>--}%
                %{--JQ Servicios--}%
                %{--</a>--}%

                <a href="index_tickets" class="user-menu navbar-brand">
                    <img src="${resource(dir: "/images", file: "avatar.png")}" class="message-avatar" alt="">
                    <span>JQ Servicios</span>
                </a>

                <!-- Main navbar toggle -->
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#main-navbar-collapse"><i class="navbar-icon fa fa-bars"></i></button>

            </div> <!-- / .navbar-header -->

            <div id="main-navbar-collapse" class="collapse navbar-collapse main-navbar-collapse">
                <div>

                    <ul class="nav navbar-nav">
                        <li>
                            <div class="row">
                                <div class="col-md-12">
                                    <select style="margin-top: 5px" class="form-control" name="" id="idioma_combo">
                                        <option value="es" <%if(session.locale.getLanguage()=='es'){%>selected="selected"<%}%>>Español</option>
                                        <option value="en" <%if(session.locale.getLanguage()=='en'){%>selected="selected"<%}%>>English</option>

                                    </select>
                                </div>
                            </div>
                            %{--<a href="#">Inicio</a>--}%
                        </li>

                    </ul> <!-- / .navbar-nav -->

                    <div class="right clearfix">
                        <ul class="nav navbar-nav pull-right right-navbar-nav">
                            <li class="dropdown">
                                <a href="" class="dropdown-toggle user-menu" data-toggle="dropdown">
                                    <img src="${resource(dir: "/images", file: "avatar.png")}" class="message-avatar" alt="">
                                    <span>${session.tusuario.fNombre}</span>
                                </a>
                                <ul class="dropdown-menu">
                                    %{--<li><a href="#">Perfil</a></li>--}%
                                    %{--<li><a href="#">Cuenta</a></li>--}%
                                    <input type="text" hidden id="url_cambio_contrasena" value="jq/">
                                    <li><a style="cursor: pointer" data-toggle="modal" data-target="#modal_cambiar_contrasena"><i class="dropdown-icon fa fa-cog"></i>&nbsp;&nbsp;${session.Grails.i18n("cambiar_contrasena",session.locale)}</a></li>
                                    <li class="divider"></li>
                                    <li><a href="logout"><i class="dropdown-icon fa fa-power-off"></i>&nbsp;&nbsp;${session.Grails.i18n("cerrar_sesion",session.locale)}</a></li>
                                </ul>
                            </li>
                        </ul> <!-- / .navbar-nav -->
                    </div> <!-- / .right -->
                </div>
            </div> <!-- / #main-navbar-collapse -->
        </div> <!-- / .navbar-inner -->
    </div> <!-- / #main-navbar -->
<!-- /2. $END_MAIN_NAVIGATION -->


<!-- 4. $MAIN_MENU =================================================================================

		Main menu

		Notes:
		* to make the menu item active, add a class 'active' to the <li>
		  example: <li class="active">...</li>
		* multilevel submenu example:
			<li class="mm-dropdown">
			  <a href="#"><span class="mm-text">Submenu item text 1</span></a>
			  <ul>
				<li>...</li>
				<li class="mm-dropdown">
				  <a href="#"><span class="mm-text">Submenu item text 2</span></a>
				  <ul>
					<li>...</li>
					...
				  </ul>
				</li>
				...
			  </ul>
			</li>
-->
    <div id="main-menu" role="navigation">
        <div id="main-menu-inner">
            <div class="menu-content top" id="menu-content-demo">
                <!-- Menu custom content demo
					 CSS:        styles/pixel-admin-less/demo.less or styles/pixel-admin-scss/_demo.scss
					 Javascript: html/assets/demo/demo.js
				 -->
                <div>
                    <div class="text-bg"><span class="text-slim">${session.Grails.i18n("bienvenido",session.locale)}</span><br/><span class="text-semibold">${session.tusuario.fNombre}</span></div>

                    %{--<img src="assets/demo/avatars/1.jpg" alt="" class="">--}%
                    <img src="${resource(dir: "/images", file: "avatar.png")}" class="message-avatar" alt="">
                    <div class="btn-group">

                        <a  data-toggle="modal" data-target="#modal_help" class="btn btn-xs btn-primary btn-outline dark"><i class="fa fa-question"></i></a>
                        <a href="logout" class="btn btn-xs btn-danger btn-outline dark"><i class="fa fa-power-off"></i></a>
                    </div>


                    %{--<a href="#" class="close">&times;</a>--}%
                </div>
            </div>
            <ul class="navigation">
                <li>
                    <a href="index_tickets"><i class="menu-icon fa fa-dashboard"></i><span class="mm-text">${session.Grails.i18n("principal",session.locale)}</span></a>
                </li>
                <li class="mm-dropdown">
                    <a href="#"><i class="menu-icon fa fa-edit"></i><span class="mm-text">${session.Grails.i18n("opciones",session.locale)}</span></a>
                    <ul>
                        <li>
                            <a tabindex="-1" href="ticket"><i class="menu-icon fa fa-user"></i><span class="mm-text">${session.Grails.i18n("crear_tickets",session.locale)}</span></a>
                        </li>
                        <li>
                            <a tabindex="-1" href="ver_tickets"><i class="menu-icon fa fa-shopping-cart"></i><span class="mm-text">${session.Grails.i18n("ver_tickets",session.locale)}</span></a>

                        </li>

                        <li>
                            <a tabindex="-1" href="modificar_ticket"><i class="menu-icon fa fa-shopping-cart"></i><span class="mm-text">${session.Grails.i18n("comentar_o_cerrar_tickets",session.locale)}</span></a>

                        </li>

                    </ul>
                </li>
                <li class="mm-dropdown">
                    <a href="#"><i class="menu-icon fa fa-edit"></i><span class="mm-text">${session.Grails.i18n("reportes",session.locale)}</span></a>
                    <ul>
                        <li>
                            <a tabindex="-1" href="ver_tickets_abiertos_cerrado"><i class="menu-icon fa fa-user"></i><span class="mm-text">${session.Grails.i18n("tickets_abiertos_cerrados",session.locale)}</span></a>
                        </li>
                        %{--<li>--}%
                        %{--<a tabindex="-1" href="${getRequest().getContextPath()}/modificar_ticket"><i class="menu-icon fa fa-shopping-cart"></i><span class="mm-text">Modificar Tickets</span></a>--}%

                        %{--</li>--}%
                    </ul>
                </li>

            </ul> <!-- / .navigation -->
            <div class="menu-content">
                %{--<a href="${getRequest().getContextPath()}/servicios_contratados" class="btn btn-primary btn-block btn-outline dark">Crear Servicios Contratados</a>--}%
            </div>
        </div> <!-- / #main-menu-inner -->
    </div> <!-- / #main-menu -->
<!-- /4. $MAIN_MENU -->
    <div id="content-wrapper">
        <div id="modal_cambiar_contrasena" rel="namespace:animal" class="modal  fade modal-blur in" tabindex="-1" role="dialog"  aria-hidden="false">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        %{--<button type="button" class="close" data-dismiss="modal">×</button>--}%
                        <h4 class="modal-title">${session.Grails.i18n("cambiar_contrasena",session.locale).toUpperCase()}</h4>
                    </div>
                    %{--body--}%
                    <div  class="modal-body">

                        <div class="row" >
                            <div class="col-md-12" >
                                <div class="form-group">
                                    <label class="control-label">${session.Grails.i18n("contrasena_anterior",session.locale)}:</label>
                                    <input type="password" name="name" id="contrasena_anterior" class="form-control">
                                </div>
                            </div>
                            <div class="col-md-12" >
                                <div class="form-group">
                                    <label class="control-label">${session.Grails.i18n("nueva_contrasena",session.locale)}:</label>
                                    <input type="password" name="name" id="nueva_contrasena" class="form-control">
                                </div>
                            </div>
                            <div class="col-md-12" >
                                <div class="form-group">
                                    <label class="control-label">${session.Grails.i18n("repetir_contrasena",session.locale)}:</label>
                                    <input type="password" name="name" id="repetir_contrasena" class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12" style="text-align: center">
                                <span >
                                    %{--<button data-type="warning" data-text="<strong>Warning!</strong> Best check yo self, you're not looking too good." id="pabel" class="btn btn-small btn-primary auto-close-alert">Auto closing alert</button>--}%
                                    <button class="btn btn-success" id="procesar_modal_cambio_contrasena" type="button" >${session.Grails.i18n("procesar",session.locale)}</button>
                                    <button class="btn btn-danger" type="button" id="atras_cambio_contrasena" data-dismiss="modal">${session.Grails.i18n("atras",session.locale)}</button>
                                </span>
                            </div>
                        </div>
                    </div>
                    %{--body--}%
                </div> <!-- / .modal-content -->
            </div> <!-- / .modal-dialog -->



        </div>
        <div id="modal_help" rel="namespace:animal" class="modal  fade modal-blur in" tabindex="-1" role="dialog"  aria-hidden="false">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h4 class="modal-title">AYUDA</h4>
                    </div>
                    %{--body--}%
                    <div  class="modal-body">

                        <div class="row" >
                            <div class="col-md-12" >


                                <ul id="uidemo-tabs-default-demo" class="nav nav-tabs">
                                    <li class="active">
                                        <a href="#uidemo-tabs-default-demo-home" data-toggle="tab">Español</a>
                                    </li>
                                    <li class="">
                                        <a href="#uidemo-tabs-default-demo-profile" data-toggle="tab">English</a>
                                    </li>

                                </ul>
                                <div class="tab-content tab-content-bordered">
                                    <div class="tab-pane fade active in" id="uidemo-tabs-default-demo-home">
                                        <p>
                                        <div>Elsistema de
                                        tickets esta dividido en dos formas de poner un ticket <b>ERROR y REQUEST</b>.</div>

                                        <p class="MsoNormal"><span lang="ES">&nbsp;</span></p>

                                        <p class="MsoNormal"><span lang="ES">Los errores son
                                        situaciones que se producen y los que deben de ser reanudados por el equipo de
                                        TI<o:p></o:p></span></p>

                                        <p class="MsoNormal"><span lang="ES">Un error es algo
                                        que sucede ya en las partes en funcionamiento y no debe ser confundido con una
                                        solicitud<o:p></o:p></span></p>

                                        <p class="MsoNormal"><span lang="ES">&nbsp;</span></p>

                                        <p class="MsoNormal"><span lang="ES">Error-Critico:
                                        cuando el sistema / plataforma no puede ser utilizada.<o:p></o:p></span></p>

                                        <p class="MsoNormal"><span lang="ES">Todo el sistema
                                        esta en el suelo y no hay horas de producción. Afecta a todos.<o:p></o:p></span></p>

                                        <p class="MsoNormal"><b><span lang="ES">Tiempo Máximo
                                        de resolución: 2 horas<o:p></o:p></span></b></p>

                                        <p class="MsoNormal"><span lang="ES">&nbsp;</span></p>

                                        <p class="MsoNormal"><span lang="ES">Error-Alta: el
                                        sistema / plataforma puede ser utilizado pero con muchos errores en el
                                        transcurso.<o:p></o:p></span></p>

                                        <p class="MsoNormal"><span lang="ES">A pesar de que
                                        puede ser usado se hace difícil mantener la producción, no afecta a todos.<o:p></o:p></span></p>

                                        <p class="MsoNormal"><b><span lang="ES">Tiempo Máximo
                                        de resolución: 4 horas<o:p></o:p></span></b></p>

                                        <p class="MsoNormal"><span lang="ES">&nbsp;</span></p>

                                        <p class="MsoNormal"><span lang="ES">Error-Medio:
                                        cuando el sistema / plataforma puede ser utilizado pero se detecta un error en
                                        una parte<o:p></o:p></span></p>

                                        <p class="MsoNormal"><span lang="ES">De este que
                                        impide ejecutar dicha acción. Este error solo afecta una parte del sistema que
                                        no es vital<o:p></o:p></span></p>

                                        <p class="MsoNormal"><span lang="ES">Para todo el
                                        funcionamiento, solo afecta un 30% del sistema<o:p></o:p></span></p>

                                        <p class="MsoNormal"><b><span lang="ES">Tiempo Máximo
                                        de resolución: 6 horas<o:p></o:p></span></b></p>

                                        <p class="MsoNormal"><span lang="ES">&nbsp;</span></p>

                                        <p class="MsoNormal"><span lang="ES">Error-Bajo:
                                        cuando el sistema / plataforma puede ser utilizado pero se detecta un error en
                                        una parte<o:p></o:p></span></p>

                                        <p class="MsoNormal"><span lang="ES">De este que
                                        impide ejecutar dicha acción. Este error solo afecta una opción del sistema que
                                        no es<o:p></o:p></span></p>

                                        <p class="MsoNormal"><span lang="ES">Vital, solo
                                        afecta un 10% del sistema<o:p></o:p></span></p>

                                        <p class="MsoNormal"><b><span lang="ES">Tiempo Máximo
                                        de resolución: 8 horas<o:p></o:p></span></b></p>

                                        <p class="MsoNormal"><span lang="ES">&nbsp;</span></p>

                                        <p class="MsoNormal"><span lang="ES">Solicitud:<o:p></o:p></span></p>

                                        <p class="MsoNormal"><span lang="ES">Los solicitudes
                                        son peticiones hechas en el departamento que no se encuentran en las partes
                                        programadas como<o:p></o:p></span></p>

                                        <p class="MsoNormal"><span lang="ES">Por ejemplo,
                                        crear usuarios, nuevo reporte, pedido de información nueva, etc. Estos pedidos
                                        no siempre<o:p></o:p></span></p>

                                        <p class="MsoNormal"><span lang="ES">Puede ser
                                        realizado si se necesita una aprobación superior del cliente o la gerencia para
                                        que el departamento pueda<o:p></o:p></span></p>

                                        <p class="MsoNormal"><span lang="ES">Darle continuidad
                                        a este ticket.<o:p></o:p></span></p>

                                        <p class="MsoNormal"><span lang="ES">&nbsp;</span></p>

                                        <p class="MsoNormal"><span lang="ES">Request-Alto:<o:p></o:p></span></p>

                                        <p class="MsoNormal"><b><span lang="ES">Tiempo Máximo
                                        de resolución: 8 horas<o:p></o:p></span></b></p>

                                        <p class="MsoNormal"><span lang="ES">&nbsp;</span></p>

                                        <p class="MsoNormal"><span lang="ES">Solicitud-Medio:<o:p></o:p></span></p>

                                        <p class="MsoNormal"><b><span lang="ES">Tiempo Máximo
                                        de resolución: 16 horas<o:p></o:p></span></b></p>

                                        <p class="MsoNormal"><span lang="ES">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <o:p></o:p></span></p>

                                        <p class="MsoNormal"><span lang="EN-US">Request-Bajo:<o:p></o:p></span></p>

                                        <p class="MsoNormal"><b><span lang="ES">Tiempo Máximo
                                        de resolución: 24 horas<o:p></o:p></span></b></p>

                                    </p>
                                    </div> <!-- / .tab-pane -->
                                    <div class="tab-pane fade" id="uidemo-tabs-default-demo-profile">
                                        <p>
                                        <div><spanstyle="color: rgb(33, 33, 33); font-family: inherit; font-size: 10pt;">The ticket
                                        system is divided into two ways to put a ticket </span><b style="color: rgb(33, 33, 33); font-family: inherit; font-size: 10pt;">ERROR</b><span style="color: rgb(33, 33, 33); font-family: inherit; font-size: 10pt;"> and </span><b style="color: rgb(33, 33, 33); font-family: inherit; font-size: 10pt;">REQUEST</b><span style="color: rgb(33, 33, 33); font-family: inherit; font-size: 10pt;">.</span></div>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">&nbsp;</span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Errors are
                                        situations that occur and those that must be resumed by the IT team<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">An error is
                                        something that happens already in the operating parts and should not be
                                        confused with a request<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">&nbsp;</span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Error-Critical:
                                        when the system / platform can not be used.<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">The whole system
                                        is on the ground and there are no hours of production. It affects everyone.<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><b><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Maximum
                                        resolution time: 2 hours<o:p></o:p></span></b></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">&nbsp;</span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Error-High: the
                                        system / platform can be used but with many errors in the course.<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Although it can
                                        be used makes it difficult to maintain production, it does not affect everyone.<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><b><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Maximum
                                        resolution time: 4 hours<o:p></o:p></span></b></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">&nbsp;</span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Error-Medium:
                                        when the system / platform can be used but an error is detected in a part<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Of this that
                                        prevents to execute this action. This error only affects a part of the system
                                        that is not vital<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">For the whole
                                        operation, only affects 30% of the system<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><b><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Maximum
                                        resolution time: 6 hours<o:p></o:p></span></b></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">&nbsp;</span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Error-Low: when
                                        the system / platform can be used but an error is detected in a part<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Of this that
                                        prevents to execute this action. This error only affects a system option that
                                        is not<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Vital, only
                                        affects 10% of the system<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><b><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Maximum resolution
                                        time: 8 hours<o:p></o:p></span></b></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">&nbsp;</span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><b><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Request</span></b><span lang="EN" style="font-size:10.0pt;font-family:inherit;color:#212121;mso-ansi-language:
                                        EN">:<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">The requests are
                                        requests made in the department that are not in the parts programmed as<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">For example,
                                        create users, new report, new information request, etc. These orders are not
                                        always<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">It can be done
                                        if superior customer or management approval is needed for the department to<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Give continuity
                                        to this ticket.<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">&nbsp;</span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Request-High:<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><b><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Maximum
                                        resolution time: 8 hours<o:p></o:p></span></b></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">&nbsp;</span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Request-Medium:<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><b><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Maximum
                                        resolution time: 16 hours<o:p></o:p></span></b></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">&nbsp;</span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Request-Low:<o:p></o:p></span></p>

                                        <p class="MsoNormal" style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;"><b><span lang="EN" style="font-size:
                                        10.0pt;font-family:inherit;color:#212121;mso-ansi-language:EN">Maximum
                                        resolution time: 24 hours<o:p></o:p></span></b></p>

                                    </p>
                                    </div> <!-- / .tab-pane -->

                                </div>


                            </div>
                        </div>

                    </div>
                    %{--body--}%
                </div> <!-- / .modal-content -->
            </div> <!-- / .modal-dialog -->

        </div>

        <g:layoutBody/>
    </div>
    <div id="main-menu-bg"></div>
</div>
<!-- Pixel Admin's javascripts -->
<asset:javascript src="assets/javascripts/pixel-admin.min.js"/>

    <asset:javascript src="jqwidgets/jqwidgets/jqxcore.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxdata.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxbuttons.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxscrollbar.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxmenu.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxcheckbox.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxlistbox.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxdropdownlist.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxgrid.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxgrid.sort.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxgrid.grouping.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxgrid.columnsresize.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxgrid.columnsreorder.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxgrid.pager.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxgrid.selection.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxgrid.edit.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxgrid.filter.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxdatatable.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxtreegrid.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxdatetimeinput.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxcalendar.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxloader.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxeditor.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxdropdownbutton.js"/>
    <asset:javascript src="jqwidgets/jqwidgets/jqxfileupload.js"/>
    <asset:javascript src="jqwigets/jqwidgets/jqxdatetimeinput.js"/>
    <asset:javascript src="js_proyecto/tickets/cambiar_contrasena.js"/>


<script type="text/javascript">
    init.push(function () {
        $('.add-tooltip').tooltip();
    });
    window.PixelAdmin.start(init);
    $(document).ready(function () {
        ejecutar_contrasena();
    })
</script>
</body>

</html>
