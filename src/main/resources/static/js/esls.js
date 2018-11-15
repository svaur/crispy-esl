function getEslsTemplate() {
    return "<span class='flow-text'>Ценники</span>" +
            "<div class=\"divider\"></div>"+
            "<div class=\"row\">" +
                "<div class=\"input-field col s12 m6 l3\">" +
                    "<select id='eslTableCounter'>" +
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
        "<table class=\"centered striped\" id=\"esl-table\"></table>"
}
function displayEslData(url, headers) {
    $.getJSON(url, headers, function (data) {
        var tableData = $.parseJSON(JSON.stringify(data));
        $('#esl-table').html('')
            .append("<thead>" +
                "<tr>" +
                "<th>ESL code</th>" +
                "<th>ESL type</th>" +
                "<th>Item code</th>" +
                "<th>Item name</th>" +
                "<th>Price</th>" +
                "<th>Updated date</th>" +
                "<th>Connectivity</th>" +
                "<th>Battery level</th>" +
                "<th>Status</th>" +
                "<th>action</th>" +
                "</tr>" +
                "</thead>")
            .append("<tbody id=\"eslTBody\"></tbody>");
        for (var i = 0; i < tableData.length; i++) {
            $('#eslTBody').append("<tr>" +
                "<td>" + tableData[i].elsCode + "</td>" +
                "<td>" + tableData[i].elsType + "</td>" +
                "<td>" + tableData[i].itemCode + "</td>" +
                "<td>" + tableData[i].itemName + "</td>" +
                "<td>" + tableData[i].price + "</td>" +
                "<td>" + tableData[i].lastUpdate + "</td>" +
                "<td>" + tableData[i].connectivity + "</td>" +
                "<td>" + tableData[i].batteryLevel + "</td>" +
                "<td>" + tableData[i].status + "</td>" +
                "<td>" +
                "<a class=\"dropdown-trigger btn-small\" href=\"#\" data-autoTrigger='false' data-target=\"dropdown" + i + "\">" +
                "<i class=\"material-icons\">menu</i>" +
                "</a>" +
                "<ul id=\"dropdown" + i + "\" class=\"dropdown-content\">" +
                "<li>" +
                "<a class=\"waves-effect waves-light\" onclick='showImage(" + tableData[i].elsCode + ")'>" +
                "<i class=\"material-icons\">photo</i>" +
                "</a>" +
                "</li>" +
                "<li>" +
                "<a class=\"waves-effect waves-light\">" +
                "<i class=\"material-icons\">edit</i>" +
                "</a>" +
                "</li>" +
                "<li>" +
                "<a class=\"waves-effect waves-light\">" +
                "<i class=\"material-icons Tiny\">update</i>" +
                "</a>" +
                "</li>" +
                "<li>" +
                "<a class=\"waves-effect waves-light\">" +
                "<i class=\"material-icons Small\">delete</i>" +
                "</a>" +
                "</li>" +
                "</ul>" +
                "</td>" +
                "</tr>");
        }
        $('.dropdown-trigger').dropdown();
    }).error(function(jqXHR) {
        alert(jqXHR.responseText);
    });
}