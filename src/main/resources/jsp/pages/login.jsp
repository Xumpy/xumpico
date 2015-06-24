<html xmlns:th="http://www.thymeleaf.org" xmlns:tiles="http://www.thymeleaf.org">
  <head>
    <script src="resources/angular.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/bootstrap/css/datepicker3.css" rel="stylesheet">
    <script src="resources/bootstrap/js/bootstrap.min.js"></script>
    <script src="resources/bootstrap/js/bootstrap-datepicker.js"></script>
    <title tiles:fragment="title">Login ThuisAdmin</title>
  </head>
  <body>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3>Please Login</h3>
            </div>
        </div>
        <form class="form-horizontal col-lg-12" name="loginForm" action="j_spring_security_check" method="post">               
            <fieldset>
                <div th:if="${param.logout}" class="alert alert-warning"> 
                    You have been logged out.
                </div>
                <div class="form-group col-lg-12">
                    <label class="col-lg-1 control-label" for="username">Username</label>
                    <div class="col-lg-2">
                        <input class="form-control" placeholder="Username"type="text" id="username" name="username"/>
                    </div>
                </div>
                <div class="form-group col-lg-12">
                    <label class="col-lg-1 control-label" for="password">Password</label></td>
                    <div class="col-lg-2">
                        <input class="form-control" placeholder="Password" type="password" id="password" name="password"/>
                    </div>
                </div>
                <div class="form-group col-lg-12">
                    <div class="col-lg-offset-1 col-lg-1">
                        <button type="submit" class="btn-primary btn">Log in</button>
                    </div>
                    <div class="col-lg-1">
                        <a class="btn-primary btn" href="register/register_user">Register</a>
                    </div>
                </div>
            </fieldset>
        </form>
  </body>
</html>