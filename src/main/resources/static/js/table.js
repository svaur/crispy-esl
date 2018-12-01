function getTableTemplate(suffixForTable) {
    var resultTableHtml= "<div id=\"mainSpaceForTable\">"+
        "<div class=\"row\">" +
            "<div class=\"input-field col s1\">" +
                "<select id='" + suffixForTable + "TableCounter'>" +
                    "<option value=\"10\">10</option>" +
                    "<option value=\"25\">25</option>" +
                    "<option value=\"50\">50</option>" +
                    "<option value=\"80\">80</option>" +
                "</select>" +
            "</div>" +
            "<div class=\"input-field col s4 offset-s3\">" +
                "<input id=\"search\" type=\"search\" placeholder=\"Search\">" +
            "</div>";
    if (suffixForTable==="task"){
        resultTableHtml+=
            "<div class=\"input-field col s1 offset-s3\">" +
                "<a id='addTask' class=\"btn\" href=\"#\">Добавить</a>" +
            "</div>";
    }else {
        resultTableHtml+="<div class=\"input-field col s1 offset-s3\">" +
            "<a class=\"dropdown-trigger btn\" href=\"#\" data-target=\"upload\">" +
                "<i class=\"tiny material-icons\">file_upload</i>" +
            "</a>" +
            "<ul id=\"upload\" class=\"dropdown-content\">" +
            "<li>" +
                "<a href=\"#!\">CSV</a>" +
            "</li>" +
            "<li>" +
                "<a href=\"#!\">Excel</a>" +
            "</li>" +
            "</ul>" +
        "</div>";
    }

    resultTableHtml+="</div>" +
        "<table class=\"centered striped z-depth-2\" id=\"" + suffixForTable + "Table\"></table></div>";
    return resultTableHtml;
}