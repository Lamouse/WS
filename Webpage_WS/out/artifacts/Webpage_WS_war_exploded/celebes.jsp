<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<jsp:useBean id="genrelist" class="Beans.Genre_List" scope="page"/>
<jsp:useBean id="personList" class="Beans.Person_List" scope="page"/>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SemanticIMDB</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/sb-admin.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="css/plugins/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.jsp">SemanticIMDB</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <form class="navbar-form" role="search">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search" name="srch-term" id="srch-term">
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                        </div>
                    </div>
                </form>
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li class="active">
                        <a href="index.jsp"><i class="fa fa-fw fa-desktop"></i> Homepage </a>
                    </li>
                    <li>
                        <a href="javascript:;" data-toggle="collapse" data-target="#movies"><i class="fa fa-fw fa-arrows-v"></i> Movies <i class="fa fa-fw fa-caret-down"></i></a>
                        <ul id="movies" class="collapse">
                            <li>
                                <a href="movie.jsp?genre=All&page=0"> All </a>
                            </li>

                            <%
                                List<String> temp1 = genrelist.getGenrelist("Movie");
                                for (int i=0; i<temp1.size() ;i++){
                            %>
                                <li>
                                    <a href=<%= "movie.jsp?genre="+temp1.get(i)+"&page=0"%>> <%= temp1.get(i)%> </a>
                                </li>
                            <%
                                }
                            %>
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:;" data-toggle="collapse" data-target="#tv_shows"><i class="fa fa-fw fa-arrows-v"></i> TV-Shows <i class="fa fa-fw fa-caret-down"></i></a>
                        <ul id="tv_shows" class="collapse">
                            <li>
                                <a href="tv_show.jsp?genre=All&page=0"> All </a>
                            </li>
                            <%
                                List<String> temp2 = genrelist.getGenrelist("TV_Show");
                                for (int i=0; i<temp2.size() ;i++){
                            %>
                            <li>
                                <a href=<%= "tv_show.jsp?genre="+temp2.get(i)+"&page=0"%>> <%= temp2.get(i)%> </a>
                            </li>
                            <%
                                }
                            %>
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:;" data-toggle="collapse" data-target="#celebs"><i class="fa fa-fw fa-arrows-v"></i> Celebs <i class="fa fa-fw fa-caret-down"></i></a>
                        <ul id="celebs" class="collapse">
                            <li>
                                <a href="celebes.jsp?kind=All&prefix=A&page=0">All</a>
                            </li>
                            <li>
                                <a href="celebes.jsp?kind=Actor&prefix=A&page=0">Actor</a>
                            </li>
                            <li>
                                <a href="celebes.jsp?kind=Director&prefix=A&page=0">Director</a>
                            </li>
                            <li>
                                <a href="celebes.jsp?kind=Writer&prefix=A&page=0">Writer</a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>

        <div id="page-wrapper">

            <div class="container-fluid">

                <div class="row">
                    <%
                        String kind = request.getParameter("kind");
                        String page1 = request.getParameter("page");
                        String prefix = request.getParameter("prefix");
                    %>
                    <div class="col-lg-12">
                        <center>
                            <ul class="pagination">
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=A&page=0" %>>A</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=B&page=0" %>>B</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=C&page=0" %>>C</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=D&page=0" %>>D</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=E&page=0" %>>E</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=F&page=0" %>>F</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=G&page=0" %>>G</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=H&page=0" %>>H</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=I&page=0" %>>I</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=J&page=0" %>>J</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=K&page=0" %>>K</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=L&page=0" %>>L</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=M&page=0" %>>M</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=N&page=0" %>>N</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=O&page=0" %>>O</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=P&page=0" %>>P</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=Q&page=0" %>>Q</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=R&page=0" %>>R</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=S&page=0" %>>S</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=T&page=0" %>>T</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=U&page=0" %>>U</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=V&page=0" %>>V</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=W&page=0" %>>W</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=X&page=0" %>>X</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=Y&page=0" %>>Y</a></li>
                                    <li><a href=<%= "celebes.jsp?kind="+kind+"&prefix=Z&page=0" %>>Z</a></li>
                                </ul>
                        </center>
                        <div class="panel panel-default">
                        
                            <div class="panel-heading">
                                <h3 class="panel-title"> <%= "Celebs - "+kind%> </h3>
                            </div>

                            <div class="panel-body">
                                <div class="list-group">
                                    <%
                                    String tempPerson;
                                    List<String> temp_personList = personList.getPersonlist(kind, page1, prefix);
                                    for (int i=0; i<temp_personList.size() ;i++){
                                        tempPerson = temp_personList.get(i);
                                    %>

                                        <a href= <%= "detail_celebs.jsp?celeb="+tempPerson %> class="list-group-item">
                                            <b><%= personList.getName(tempPerson)+", "%></b>
                                            <%= "Birth date: "+personList.getBirthDate(tempPerson)+", " %>
                                            <%= "Jobs: "+personList.getJobs(tempPerson) %>
                                        </a>

                                    <%
                                        }
                                    %>
                                </div> 

                                <!--PREVIOUS PAGE ///// NEXT PAGE -->
                                <div class="row">
                                  <div class="col-md-8"><a href= <%= "celebes.jsp?kind="+kind+"&prefix="+prefix+"&page="+personList.decPage(page1) %>><i class="fa fa-arrow-circle-left"></i> Previous page</a></div>
                                  <div class="col-md-4"><a href= <%= "celebes.jsp?kind="+kind+"&prefix="+prefix+"&page="+personList.incPage(page1) %>>Next page <i class="fa fa-arrow-circle-right"></i></a></div>
                                </div>                                                            
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="js/plugins/morris/raphael.min.js"></script>
    <script src="js/plugins/morris/morris.min.js"></script>
    <script src="js/plugins/morris/morris-data.js"></script>

</body>

</html>
