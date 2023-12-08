/*
 * Create Stop Word Files
 */
/* global stpwrd, res, token, i */

var StopWords = (function() {
   
   $("#createStopWordBtn").click(function() {
       
       showLoader(); 
       
       $.ajax({
          url: 'createStopWords',
          type: 'post',
          success: function(res) {
              
              hideLoaders();
              
              //Stop Word File Exists
              if(res.FileExists) {
                    
                  fileExists(res);
              }
              else {
                  
                  stopWordsCreated(res);
              }
              
          }
       });
       
   }); 
   
   var fileExists = function(res) {
       
       stopWords = "";
       for(stpwrd in res.stopWords ) {
           stopWords += (res.stopWords[stpwrd] + ", ");
       }
       
       stopWords = stopWords.substr(0,stopWords.length - 2);
       
       swal({
           title: "فایل قبلا ساخته شده است.",
           confirmButtonText: "باشه!"
        },
        function() {
          setTimeout(function(){
            
            swal({
                title: "StopWords",
                text: stopWords,
                confirmButtonText: "باشه!"
            });
            
          }, 200); 
        });
        
   };
   
   var stopWordsCreated = function(res) {
       
       stopWords = "";
       
       for(stpwrd in res.stopWords) {
           
           stopWords += (res.stopWords[stpwrd] + ", ");
       }
       stopWords = stopWords.substr(0,stopWords.length - 2);
       
       swal({
           title: "StopWords",
           text: "فایل با موفقیت ایجاد شد.",
           type: "success",
           confirmButtonText: "حله!"
        },
        function() {
          
            setTimeout(function(){
           
                swal({
                    title: "StopWords",
                    text: stopWords,
                    confirmButtonText: "باشه!"
                },
                function() {
                    setTimeout(function() {window.location.reload(true)},200);
                });
            
          }, 200); 
        });
   };
   
   
}());










var createIndex = (function() {
    
    $("#createIndex").click(function() {
        
        showLoader();
        
        $.ajax({
            url: "createIndex",
            type: "post",
            success: function(res) {
                
                hideLoaders();
                
                if(res.isStopWordsFileExists != null && !res.isStopWordsFileExists) {
                    
                    noStopWordFile();
                }
                else if(res.isIndexExists) {
                    
                    indexExists();
                }else {
                    
                    indexCreated();
                }
            }
        });
    });
    
    
    var indexCreated = function() {
        
        swal({
           title: "شاخص",
           text: "ساخت شاخص ها با موفقیت انجام شد.",
           type: "success",
           confirmButtonText: "حله!"
        },
        function() {
            setTimeout(function() {window.location.reload(true)},200);
        });
    };
    
    var indexExists = function() {
        
        swal({
           title: "ایندکس ها  قبلا ساخته شده اند.",
           imageUrl: "assets/img/thumbs-up.jpg",
           confirmButtonText: "حله!"
        });
    };
    
    var noStopWordFile = function() {
        
        sweetAlert("Oops...StopWords", "فایل" + " StopWords " + "یافت نشد!" , "error");
    };

}());


var queryParse = (function() {
    
    var query             = $("#query");
    var searchResultsList = $(".search-results .list-group");
    
    $(".search-input input").keyup(function(e) {
        
        e.which = e.which || e.keyCode;
        
        if(e.which == 13) {
            queryVal = query.val();
            sendQuery(queryVal);
        }
    });
    
    
    
    $("#searchBtn").click(function() {
       
        queryVal = query.val().trim();
        sendQuery(queryVal);
    });
    
    
    var sendQuery = function(queryVal) {
        
        $.ajax({
            url: "queryParse",
            type: "post",
            data: {
                query: queryVal,
                page: 1
            },
            success: createPagination
        });
    };
    
    createPagination = function(res) {

        $("#results-pages").pagination({

            dataSource: "queryParse",
            totalNumber: res.totalNumer,
            pageSize: 6,
            locator: "result",
            callback: function(data, pagination) {  
                // template method of yourself
                var html = template(data);
                searchResultsList.html(html);
            }
        });  
    };
    
    template = function(data) {
        
        var row, docSummary, boldedTitle;
        
        searchResultsList.empty();
        
        $.each(data, function(i,item) {
           
           row = $("<li>").addClass("list-group-item").html($("<a>"));
           docSummary = $("<span>").html(boldKeyWords(item.summary));
           
           boldedTitle = boldKeyWords(item.title);
           
           row.find('a').attr("docID", item.id).attr("href" ,"").html(boldedTitle);
           
           row.find('a').after(docSummary);
           searchResultsList.append(row.clone());
           
        });
    };
    
}());




var getDocument = (function() {
    
    $(".search-results").on('click', "li a", function(e) {
        
        e.preventDefault();
        var docId = $(this).attr("docid");
        
        sendDocId(docId);
    });
    
    
    sendDocId = function(docId) {
        
        $.ajax({
            url: "getDocument",
            type: "post",
            data: {
                docId: docId
            },
            success: showDocument
        });
    };
    
    
    showDocument = function(res) {
        bootbox.alert({
            title: boldKeyWords(res.title),
            message: boldKeyWords(res.content),
            size: 'large',
            buttons: {
                ok: {
                    label: 'بستن',
                    className: 'btn-success'
                }
            }
        });
    };
    
    
}());