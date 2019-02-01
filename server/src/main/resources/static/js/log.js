function getLogTemplate() {
    return "<h2 class='flow-text'>Логи взаимодействия с товарами и ценниками</h2>" +
        "<div class=\"divider\"/>"+
        getTableTemplate('log')
}
function displayLogData(url, headers) {
    var display = document.getElementById("mainProgress");
    display.style.visibility='visible';
    $('#logTable').html('')
        .append("<thead>" +
            "<tr>" +
            "<th>время события</th>" +
            "<th>имя</th>" +
            "<th>источник</th>" +
            "<th>тип</th>" +
            "<th>событие</th>" +
            "</tr>" +
            "</thead>")
        .append("<tbody id=\"logTBody\"></tbody>");
    $.getJSON(url, headers, function (data) {
        var mapData = $.parseJSON(JSON.stringify(data));
        for (var i = 0; i < mapData.length; i++) {
            $('#logTBody').append("<tr>" +
                "<td>" + mapData[i].time + "</td>" +
                "<td>" + mapData[i].name + "</td>" +
                "<td>" + mapData[i].source + "</td>" +
                "<td>" + mapData[i].type + "</td>" +
                "<td>" + mapData[i].event + "</td>" +
                "<td>" +
                "</li>" +
                "</ul>" +
                "</td>" +
                "</tr>");
        }
        display.style.visibility='hidden'
    }).error(function(jqXHR) {
        alert(jqXHR.responseText);
        display.style.visibility='hidden'
    });
}
function logActivateActions() {
    $('#logTableCounter').formSelect().on('change', function () {
        getAllLogData(pageNum);
    });
    $('#search').on('input', function() {
        getAllLogData(pageNum);
    });
    $('.dropdown-trigger').dropdown();
    $('#previousBtn').click(function () {
        if (pageNum > 0) {
            pageNum = pageNum - 1;
            document.getElementById("firstBtn").innerHTML = pageNum + 1;
            getAllLogData(pageNum);
        }
    });
    $('#nextBtn').click(function () {
        pageNum = pageNum + 1;
        document.getElementById("firstBtn").innerHTML = pageNum + 1;
        getAllLogData(pageNum);
    });
}function getAllLogData(pageNum) {
    var headers = {"size": $('#logTableCounter').val(), "pageNum": pageNum, "searchValue": $('#search').val()};
    displayLogData("/api/getLogTableData", headers);
}