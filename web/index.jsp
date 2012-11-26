<%-- 
    Document   : index
    Created on : 5-nov-2012, 18.17.55
    Author     : simone
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>		
        <!-- meta -->
        <meta charset='utf-8'>
        <meta name='viewport' content='width=device-width, initial-scale=1.0'>
        <meta name='description' content=''>
        <meta name='author' content=''>	

        <!-- stili -->
        <link href='css/bootstrap.css' rel='stylesheet' media='screen'>
        <link href='css/bootstrap.min.css' rel='stylesheet' media='screen'>
        <link href='css/bootstrap-responsive.css' rel='stylesheet' media='screen'>
        <link href='css/bootstrap-responsive.min.css' rel='stylesheet' media='screen'>		
        <link href='css/style.css' rel='stylesheet' media='screen'>		

        <title>Login</title>
    </head>
    <body>
        <div class='container-fluid' id='login'>
            <div class='row-fluid'>
                <div class='span4 offset4'>
                    <h1>
                        ProxiFarmer
                    </h1>
                </div>
            </div>			
            <!-- end of row-fluid -->
            <div class='row-fluid'>
                <div class='span4 offset4'>
                    <form action="Login" method="POST">
                        <label>Login
                        <%
                            String message = (String) request.getAttribute("message");
                            String type = (String) request.getAttribute("message_type");

                            if (message != null) {
                                out.print("<span class=\"label label-"+type+"\"> "+message+"</span>");
                            }
                            
                        %>
                        </label>
                        <input type='text' placeholder='username' name='username'>
                        <input type='password' placeholder='password' name='password'>
                        <label class='checkbox'>
                            <input type='checkbox'> Remember me
                        </label>
                        <button type='submit' class='btn'>Sign in</button>
                    </form>
                </div>
            </div>
            <!-- end of row-fluid -->
        </div>
        <!-- end of container-fluid -->
    </body>
</html>
