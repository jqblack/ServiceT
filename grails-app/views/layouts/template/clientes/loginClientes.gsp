<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login Clientes</title>

        <!--===============================================================================================-->
        <asset:stylesheet  href="assets/vendor/bootstrap/css/bootstrap.min.css"/>
        <asset:stylesheet href="assets/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>
    <!--===============================================================================================-->
        <asset:stylesheet  href="assets/fonts/Linearicons-Free-v1.0.0/icon-font.min.css"/>
        <!--===============================================================================================-->
        <asset:stylesheet  href="assets/vendor/animate/animate.css"/>
        <!--===============================================================================================-->
        <asset:stylesheet href="assets/vendor/css-hamburgers/hamburgers.min.css"/>
        <!--===============================================================================================-->
        <asset:stylesheet  href="assets/vendor/animsition/css/animsition.min.css"/>
        <!--===============================================================================================-->
        <asset:stylesheet  href="assets/vendor/select2/select2.min.css"/>
        <!--===============================================================================================-->
        <asset:stylesheet  href="assets/vendor/daterangepicker/daterangepicker.css"/>
        <!--===============================================================================================-->
        <asset:stylesheet  href="assets/css/util.css"/>
        <asset:stylesheet  href="assets/css/main.css"/>

</head>

<body >
<div class="text-center">
    <asset:image src="Logo.png" class="img-responsive" alt="" style="height: 45px;top: 0px"/>
</div>

<div class="limiter">
    <div class="container-login100">

        <div class="wrap-login100">
            %{--<div class="login100-form-title" style="background-image: url(images/bg-01.jpg);">--}%
            <div class="login100-form-title" >
                <span class="login100-form-title-1">
                    Inciar Sesión Clientes
                </span>
            </div>

            <form class="login100-form validate-form" method="post" action="logincliente/iniciar">
                <div class="wrap-input100 validate-input m-b-26" data-validate="Username is required">
                    <span class="label-input100">Username</span>
                    <input class="input100" type="number" name="username" placeholder="Enter username">
                    <span class="focus-input100"></span>
                </div>

                <div class="wrap-input100 validate-input m-b-18" data-validate = "Password is required">
                    <span class="label-input100">Password</span>
                    <input class="input100" type="password" name="pass" placeholder="Enter password">
                    <span class="focus-input100"></span>
                </div>


                <div class="container-login100-form-btn" >
                    <button class="login100-form-btn btn-block" type="submit">
                        Login
                    </button>
                </div>
            </form>
        </div>

    </div>
    <div class="text-center">
        <a class="text-info" href="./login">Inicio JQ Microsistemas</a>
    </div>

</div>

	%{--<script type="text/javascript"> window.jQuery || document.write('<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js">'+"<"+"/script>"); </script>--}%

%{--<script type="text/javascript"> window.jQuery || document.write('<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js">'+"<"+"/script>"); </script>--}%

%{--<script type="text/javascript">--}%

%{--</script>--}%

<asset:javascript  src="assets/vendor/jquery/jquery-3.2.1.min.js"/>
<!--===============================================================================================-->
<asset:javascript  src="assets/vendor/animsition/js/animsition.min.js"/>
<!--===============================================================================================-->
<asset:javascript  src="assets/vendor/bootstrap/js/popper.js"/>
<asset:javascript  src="assets/vendor/bootstrap/js/bootstrap.min.js"/>
<!--===============================================================================================-->
<asset:javascript  src="assets/vendor/select2/select2.min.js"/>
<!--===============================================================================================-->
<asset:javascript  src="assets/vendor/daterangepicker/moment.min.js"/>
<asset:javascript  src="assets/vendor/daterangepicker/daterangepicker.js"/>
<!--===============================================================================================-->
<asset:javascript  src="assets/vendor/countdowntime/countdowntime.js"/>
<!--===============================================================================================-->
<asset:javascript  src="assets/js/main.js"/>


</body>
</html>