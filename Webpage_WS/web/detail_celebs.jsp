<%@ page import="java.util.List" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<jsp:useBean id="genrelist" class="Beans.Genre_List" scope="page"/>
<jsp:useBean id="personDetails" class="Beans.Person_List" scope="page"/>
<%
    String person = request.getParameter("celeb");
    genrelist.addLastClicks(person);
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
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-clock-o fa-fw"></i> Celebs </h3>
                            </div>
                            <div class="panel-body">
                            <table class="table" border="0">
                                <tr>
                                    <td>
                                        <img src= <%= personDetails.getPhoto(person) %> style="width:280px;height:350px;">
                                    </td>   
                                    <td valign="top">
                                        <p style="font-size:150%"><b>Name: </b> <%= personDetails.getName(person) %> </p>
                                        <p style="font-size:100%"> <%= personDetails.getJobs(person) %> </p>

                                        <p><b>Born: </b> <%= personDetails.getBirthDate(person) %> </p>
                                        <p><b>Biography:</b> <%= personDetails.getBiography(person) %></p>
                                        <p name="list_of_media_director"><b> Director of: </b>
                                            <ul>
                                                <%
                                                    String temp_media;
                                                    List<String> director_list = personDetails.getMedia("Director", person);
                                                    for(int i=0; i<director_list.size(); i++) {
                                                        temp_media = director_list.get(i);
                                                %>p
                                                    <a href= <%= genrelist.getPageLink(temp_media)+".jsp?celeb="+temp_media %>><li> <%= personDetails.getMediaName(temp_media) %></li></a>
                                                <%
                                                    }
                                                %>
                                            </ul>
                                        </p>
                                        <p name="list_of_media_writer"><b> Writer of: </b>
                                            <ul>
                                            <%
                                                List<String> writer_list = personDetails.getMedia("Writer", person);
                                                for(int i=0; i<writer_list.size(); i++) {
                                                    temp_media = writer_list.get(i);
                                            %>
                                            <a href= <%= genrelist.getPageLink(temp_media)+".jsp?celeb="+temp_media %>><li> <%= personDetails.getMediaName(temp_media) %></li></a>
                                            <%
                                                }
                                            %>
                                            </ul>
                                        </p>
                                        <p name="list_of_media_actor"><b> Actor in: </b>
                                            <ul>
                                            <%
                                                List<String> actor_list = personDetails.getMedia("Actor", person);
                                                for(int i=0; i<actor_list.size(); i++) {
                                                    temp_media = actor_list.get(i);
                                            %>
                                            <a href= <%= genrelist.getPageLink(temp_media)+temp_media %>><li> <%= personDetails.getMediaName(temp_media) %></li></a>
                                            <%
                                                }
                                            %>
                                            </ul>
                                        </p>
                                    </td>   
                                    <td width="400">
                                        
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
