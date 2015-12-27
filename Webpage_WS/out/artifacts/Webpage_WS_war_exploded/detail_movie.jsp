<%@ page import="java.util.List" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<jsp:useBean id="genrelist" class="Beans.Genre_List" scope="page"/>
<jsp:useBean id="medialist" class="Beans.Media_List" scope="page"/>
<jsp:useBean id="personList" class="Beans.Person_List" scope="page"/>
<%
    genrelist.setInitialize();
    medialist.setInitialize();
    personList.setInitialize();

    String movie = request.getParameter("movie");
    genrelist.addLastClicks(movie);
%>

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
                <form class="navbar-form" role="search" action="search.jsp">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search" name="q" id="srch-term">
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
-
            <div class="container-fluid">

                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-clock-o fa-fw"></i> Movie </h3>
                            </div>
                            <div class="panel-body" style="overflow-y: scroll; max-height: 650px">
                            <table class="table" border="0 " style="height: 300px">
                                <tr>
                                    <td>
                                        <img src= <%= medialist.getCover(movie) %> style="width:280px;height:350px;">
                                    </td>   
                                    <td valign="top">
                                        <p style="font-size:150%"><b>Title:</b> <%= medialist.getTitle(movie)+"("+medialist.getDate(movie)+")" %></p>
                                        <p><b>Genre:</b> <%= medialist.getGenre(movie)%> </p>
                                        <p><b>Rating:</b> <%= medialist.getRating(movie) %> / 10</p>
                                        <p><b>Plot:</b> <%= medialist.getPlot(movie) %> </p>

                                    <td width="400">
                                        <p name="list_directors"><b>Director:</b>
                                        <ul>
                                            <%
                                                String temp_celeb;
                                                List<String> director_list = medialist.getPerson("Director", movie);
                                                for(int i=0; i<director_list.size(); i++) {
                                                    temp_celeb = director_list.get(i);
                                            %>
                                            <a href= <%= "detail_celebs.jsp?celeb="+temp_celeb %>><li> <%= medialist.getPersonName(temp_celeb) %></li></a>
                                            <%
                                                }
                                            %>
                                        </ul>
                                        </p>
                                        <p name="list_writers"><b>Writer:</b>
                                        <ul>
                                            <%
                                                List<String> writer_list = medialist.getPerson("Writer", movie);
                                                for(int i=0; i<writer_list.size(); i++) {
                                                    temp_celeb = writer_list.get(i);
                                            %>
                                            <a href= <%= "detail_celebs.jsp?celeb="+temp_celeb %>><li> <%= medialist.getPersonName(temp_celeb) %></li></a>
                                            <%
                                                }
                                            %>
                                        </ul>
                                        </p>
                                        <p name="list_actors"><b>Actor:</b>
                                        <ul>
                                            <%
                                                List<String> actor_list = medialist.getPerson("Actor", movie);
                                                for(int i=0; i<actor_list.size(); i++) {
                                                    temp_celeb = actor_list.get(i);
                                            %>
                                            <a href= <%= "detail_celebs.jsp?celeb="+temp_celeb %>><li> <%= medialist.getPersonName(temp_celeb) %></li></a>
                                            <%
                                                }
                                            %>
                                        </ul>
                                        </p>
                                        
                                    </td>                              
                                </tr>
                            </table>    
                              <div class="row">
                                    <div class="col-md-8"><a href="javascript:javascript:history.go(-1)"><i class="fa fa-arrow-circle-left"></i> Back</a></div>
                                </div>                                              
                                                                                                                         
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-clock-o fa-fw"></i> Recommendation </h3>
                            </div>
                            <div class="panel-body" style="overflow-y: scroll; max-height:300px ">
                                <div class="list-group">
                                    <%
                                        String temp_item;
                                        List<String> recommendation = genrelist.getRecommendation();
                                        for(int i=recommendation.size()-1; i>=0; i--) {
                                            temp_item = recommendation.get(i);

                                            if(temp_item.startsWith("tt")) {
                                    %>
                                    <a href=<%= "detail_movie.jsp?movie="+temp_item %> class="list-group-item">
                                        <b> <%= "MOVIE: "+medialist.getTitle(temp_item)+"("+medialist.getDate(temp_item)+"), " %> </b>
                                        <%= "Genre: "+medialist.getGenre(temp_item)+", " %>
                                        <%= "Runtime: "+medialist.getRuntime(temp_item)+"m, "%>
                                        <%= "Rating: "+medialist.getRating(temp_item)%>
                                    </a>
                                    <%
                                    }
                                    else if(temp_item.startsWith("ts")) {
                                    %>
                                    <a href= <%= "detail_tvshow.jsp?tvshow="+temp_item %> class="list-group-item">
                                        <b> <%= "TV_SHOW: "+medialist.getTitle(temp_item)+"("+medialist.getSeasonDate(temp_item)+"), " %> </b>
                                        <%= "Genre: "+medialist.getGenre(temp_item)+", " %>
                                        <%= "Runtime: "+medialist.getRuntime(temp_item)+"m, "%>
                                        <%= "Rating: "+medialist.getRating(temp_item)%>
                                    </a>
                                    <%
                                    }
                                    else {
                                    %>
                                    <a href=<%= "detail_celebs.jsp?celeb="+temp_item %> class="list-group-item">
                                        <b><%= "CELEB: "+personList.getName(temp_item)+", "%></b>
                                        <%= "Birth date: "+personList.getBirthDate(temp_item)+", " %>
                                        <%= "Jobs: "+personList.getJobs(temp_item) %>
                                    </a>
                                    <%
                                            }
                                        }
                                    %>
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
