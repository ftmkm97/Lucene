showLoader = function() {
    $(".spinner").fadeIn(300);
    $(".mask").fadeIn(300);
};

hideLoaders = function() {
    $(".spinner").fadeOut(300);
    $(".mask").fadeOut(300);
};



String.prototype.replaceAll = function(search, replacement) {
    
    var target = this;
    return target.split(search).join(replacement);
};

removeStpWords = function (vall, arr) {
    
    vall = vall.split(",");
    
    for(i in vall) {
        
        var val = vall[i];
        
        for (var i = 0; i < arr.length; i++) {
            var c = arr[i];
            if (c === val || (val.equals && val.equals(c))) {
               arr.splice(i, 1);
            }
        }
    }
    
 };

boldKeyWords = function(text) {
        
    var queryVal = $("#query").val().split(" ");
        
    //console.log(queryVal);
    //removeStpWords("و,از,با,به,در,را,است,این" , queryVal);
    
    for(i in queryVal) {
        queryVal[i] = " " + queryVal[i] + " "; 
        
    }
    
    for(i in queryVal) {
        
        if(queryVal[i] != "") {
            text = text.replaceAll(queryVal[i], "<b><i><u>" + queryVal[i] + "</u></i></b>");
        }
    }
        
    return text;
        
 };