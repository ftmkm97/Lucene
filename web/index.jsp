<%@page import="Lucene.StopWords"%>
<%@page import="Lucene.IndexDocuments"%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="stp" uri="/WEB-INF/custom.tld" %>
<html>
<head>
    <title>Lucene Project</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="assets/css/bootstrap.css">
    <link rel="stylesheet" href="assets/css/bootstrap-rtl.css">
    <link rel="stylesheet" href="assets/css/styles.css" >
    <link rel="stylesheet" href="assets/css/pagination.css">
    <link rel="stylesheet" href="assets/css/sweetalert.css">
    <link rel="stylesheet" href="assets/css/loader.css">

    <script src="assets/js/jquery.js"></script>
    <script src="assets/js/bootstrap.js"></script>
    <script src="assets/js/pagination.js"></script>
    <script src="assets/js/bootbox.js"></script>
    <script src="assets/js/sweetalert.min.js"></script>
    <script src="assets/js/common.js"></script>
</head>
<body>
    <div class="container">

        <div class="row">
            <div class="col-lg-12">
                <h1 class="project-title">پروژه درس  مبانی بازیابی اطلاعات و جستجو وب</h1>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-8 col-lg-offset-2" id="searchPanle">

                <div class="panel panel-default">
                    <div class="panel-heading">موتور جستجو ماه گل</div>
                        <div class="panel-body">
                            
                            <!-- Stop Words Button And Create Button Index -->
                            <div class="row">
                                <div class="col-lg-12">
                                    <button class="btn btn-info" id="createStopWordBtn"><span>ایجاد فایل</span> StopWords</button>
                                    <button class="btn btn-success" id="createIndex">ساخت ایندکس</button>
                                </div>
                            </div>
                            
                            <!-- Search Button -->
                            <div class="row">
                                
                                <div class="col-lg-10 search-input" >
                                    <%
                        if(IndexDocuments.isIndexExists() && StopWords.isStopWordFileExists()) {
                            out.print("<input class=\"form-control\" placeholder=\"متن خود را وارد کنید...\" id=\"query\" >");
                        }
                        else {
                            out.print("<input class=\"form-control\" placeholder=\"فایل StopWords یا ایندکس وجود ندارد.\" disabled id=\"query\">");
                        }
                    %>
                                    
                                </div>
                                
                                <div class="col-lg-2 search-btn">
                                    <%
                        if(IndexDocuments.isIndexExists() && StopWords.isStopWordFileExists()) {
                            out.print("<button class=\"btn btn-block btn-default\" id=\"searchBtn\">جستجو</button>");
                        }
                        else {
                            out.print("<button class=\"btn btn-block btn-default\" id=\"searchBtn\" disabled>جستجو</button>");
                        }
                    %>
                                    
                                </div>
                            </div>
                            
                            
                            <!-- Results -->
                            <div class="row">
                                <div class="col-lg-12 search-results">
                                    
                                    <div class="panel panel-success">
                                        
                                        <div class="panel-heading"><b>نتایج جستجو</b></div>
                                            
                                        <ul class="list-group">
                                            <li class="list-group-item" style="text-align: center">برای نمایش نتیجه جستجو کنید.</li>
                                        </ul>

                                    </div>
                                
                                </div>
                            </div>
                            
                            
                            <!-- Pages -->
                            <div class="row">
                                <div class="col-lg-12" id="results-pages">
                                    
                                </div>
                            </div>
                            
                    </div>
               </div>

            </div>
        </div>
    </div>
    
    <div class="spinner">
        
      <div class="double-bounce1"></div>
      <div class="double-bounce2"></div>
    </div>
    <div class="mask"></div>
 <script src="assets/js/js.js"></script>
</body>
</html>
