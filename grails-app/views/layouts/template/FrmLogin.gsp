
<!DOCTYPE html>
<!--[if IE 8]>         <html class="ie8"> <![endif]-->
<!--[if IE 9]>         <html class="ie9 gt-ie8"> <![endif]-->
<!--[if gt IE 9]><!--> <html class="gt-ie8 gt-ie9 not-ie"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Iniciar Sesión - JQ Servicios</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">

    <!-- Open Sans font from Google CDN -->
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,400,600,700,300&subset=latin" rel="stylesheet" type="text/css">

    <!-- Pixel Admin's stylesheets -->
    <asset:stylesheet href="assets/stylesheets/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/pixel-admin.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/widgets.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/rtl.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/themes.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/pages.min.css" rel="stylesheet" type="text/css"/>

    <!--[if lt IE 9]>-->

		%{--<g:javascript src="bootstrap/assets/javascripts/ie.min.js"></g:javascript>--}%
        %{--<g:javascript src="bootstrap/assets/demo/demo.js"></g:javascript>--}%
        %{--<g:javascript src="bootstrap/assets/javascripts/bootstrap.min.js"></g:javascript>--}%
        %{--<g:javascript src="bootstrap/assets/javascripts/pixel-admin.min.js"></g:javascript>--}%



    <!-- Pixel Admin's javascripts -->
    <asset:javascript src="assets/javascripts/jquery-3.4.1.min.js" />
    <asset:javascript src="assets/javascripts/bootstrap.min.js"/>
    <asset:javascript src="assets/javascripts/pixel-admin.min.js"/>


</head>


<!-- 1. $BODY ======================================================================================

	Body

	Classes:
	* 'theme-{THEME NAME}'
	* 'right-to-left'     - Sets text direction to right-to-left
-->
<body class="theme-default page-signin-alt">




<!-- 2. $MAIN_NAVIGATION ===========================================================================

	Main navigation
-->
<div class="signin-header">
    <a href="" class="logo">
        <div class="demo-logo bg-primary">
            %{--<img src="${resource(dir: "/images", file: "Logo.png")}" class="img-responsive" alt="" style="height: 45px;top: 0px">--}%
            <asset:image src="Logo.png" class="img-responsive" alt="" style="height: 45px;top: 0px"/>

            %{--<img src="assets/demo/logo-big.png" alt="" style="margin-top: -4px;">--}%
        </div>&nbsp;
        %{--<strong>JQ</strong>Servicios--}%
    </a> <!-- / .logo -->
    %{--<a href="pages-signup-alt.html" class="btn btn-primary">Sign Up</a>--}%
</div> <!-- / .header -->

<h1 class="form-header">Iniciar Sesión</h1>


<!-- Form -->
<form action="login/iniciar_sesion"  class="panel" method="post">
    <div class="form-group">
        <input type="text" value="admin" name="user" id="user" class="form-control input-lg" placeholder="Usuario o Email" >
    </div> <!-- / Username -->

    <div class="form-group signin-password">
        <input type="password" name="pass" value="admin" id="password_id" class="form-control input-lg" placeholder="Contraseña" >
        %{--<a href="#" class="forgot">Forgot?</a>--}%
    </div> <!-- / Password -->

    <div class="form-actions">
        <input type="submit" id="entrar" value="Entrar" class="btn btn-primary btn-block btn-lg">
    </div> <!-- / .form-actions -->
</form>
<!-- / Form -->
<br>
<div class="text-center">
    <a href="./logincliente">Acceso a clientes</a>
</div>

<div class="signin-with">
    <div class="header">Redes Sociales</div>
    <a href="https://es-la.facebook.com/jqmicrosistemas/" class="btn btn-lg btn-facebook rounded"><i class="fa fa-facebook"></i></a>&nbsp;&nbsp;
    <a href="https://www.instagram.com/jqmicrosistemas/" class="btn btn-lg btn-info rounded"><i class="fa fa-instagram"></i></a>&nbsp;&nbsp;
    %{--<a href="index.html" class="btn btn-lg btn-danger rounded"><i class="fa fa-google-plus"></i></a>--}%
</div>

<!--[if lte IE 9]>
	<script type="text/javascript"> window.jQuery || document.write('<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js">'+"<"+"/script>"); </script>
<![endif]-->
<!-- Get jQuery from Google CDN -->
<!--[if !IE]> -->
<script type="text/javascript"> window.jQuery || document.write('<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js">'+"<"+"/script>"); </script>
<!-- <![endif]-->



<!-- Pixel Admin's javascripts -->


<script type="text/javascript">
//    window.PixelAdmin.start([
//        function () {
//            $("#signin-form_id").validate({ focusInvalid: true, errorPlacement: function () {} });
//
//            // Validate username
//            $("#username_id").rules("add", {
//                required: true,
//                minlength: 3
//            });
//
//            // Validate password
//            $("#password_id").rules("add", {
//                required: true,
//                minlength: 6
//            });
//        }
//    ]);
</script>

</body>
</html>
