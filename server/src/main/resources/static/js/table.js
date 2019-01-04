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
                "<input id=\"search\" type=\"search\" placeholder=\"Поиск\">" +
            "</div>";
    if (suffixForTable==="task"){
        resultTableHtml+=
            "<div class=\"input-field col s1 offset-s3\">" +
                "<a id='addTask' class=\"btn\" href=\"#\">+</a>" +
            "</div>";
    }else {
        resultTableHtml+="<div class=\"input-field col s1 offset-s3\">" +
            "<a class=\"dropdown-trigger btn\" href=\"#\" data-target=\"upload\">" +
                "<i class=\"white-text tiny material-icons\">file_upload</i>" +
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
        "<table class=\"centered striped\" id=\"" + suffixForTable + "Table\"></table>" +
        "</div>"+
        "  <ul class=\"pagination col offset-s8\">\n" +
        "    <li class=\"waves-effect\"><a id =\"previousBtn\"  href=\"#!\"><i class=\"material-icons\">chevron_left</i></a></li>\n" +
        "    <li class=\"active waves-effect\"><a id =\"firstBtn\"  href=\"#!\">1</a></li>\n" +
        // "    <li class=\"waves-effect\"><a id =\"secondBtn\" href=\"#!\">2</a></li>\n" +
        // "    <li class=\"waves-effect\"><a id =\"thirdBtn\"  href=\"#!\">3</a></li>\n" +
        // "    <li class=\"waves-effect\"><a id =\"fourthBtn\" href=\"#!\">4</a></li>\n" +
        "    <li class=\"waves-effect\"><a id =\"nextBtn\" href=\"#!\"><i class=\"material-icons\">chevron_right</i></a></li>\n" +
        "  </ul>";
    return resultTableHtml;
}