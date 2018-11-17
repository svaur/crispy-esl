function getTableTemplate(suffixForTable) {
    return "<div class=\"divider\"></div>"+
        "<div class=\"row\">" +
            "<div class=\"input-field col s12 m6 l3\">" +
                "<select id='" + suffixForTable + "TableCounter'>" +
                    "<option value=\"10\">10</option>" +
                    "<option value=\"25\">25</option>" +
                    "<option value=\"50\">50</option>" +
                    "<option value=\"80\">80</option>" +
                "</select>" +
            "</div>" +
            "<div class=\"input-field col s12 m6 l3\">" +
                "<input id=\"search\" type=\"search\" placeholder=\"Search\">" +
            "</div>" +
            "<div id=\"uploadBtn\" class=\"col s12 m6 l1\">" +
                "<a class=\"dropdown-trigger btn\" href=\"#\" data-target=\"upload\">" +
                    "<i class=\"tiny material-icons\">file_upload</i>" +
                "</a>" +
                "<ul id=\"upload\" class=\"dropdown-content\">" +
                    "<li>" +
                        "<a href=\"#!\">CSV</a>" +
                    "</li>"+
                    "<li>" +
                        "<a href=\"#!\">Excel</a>" +
                    "</li>" +
                "</ul>" +
            "</div>" +
        "</div>" +
        "<div class=\"divider\"></div>" +
        "<table class=\"centered striped\" id=\"" + suffixForTable + "Table\"></table>";
}