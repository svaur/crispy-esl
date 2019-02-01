function getReportTemplate() {
    return "<h2 class='flow-text'>Отчеты</h2>" +
        "<div class=\"divider\"/>"+
        getTableTemplate('report')
}
function displayReportData(url, headers) {
    var display = document.getElementById("mainProgress");
    display.style.visibility='visible';
    $('#reportTable').html('')
        .append("<thead>" +
            "<tr>" +
            "<th>время события</th>" +
            "<th>событие</th>" +
            "</tr>" +
            "</thead>")
        .append("<tbody id=\"reportTBody\"></tbody>");
    $.getJSON(url, headers, function (data) {
        var mapData = $.parseJSON(JSON.stringify(data));
        for (var i = 0; i < mapData.length; i++) {
            $('#reportTBody').append("<tr>" +
                "<td>" + mapData[i].time + "</td>" +
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
function reportActivateActions() {
    $('#reportTableCounter').formSelect().on('change', function () {
        getAllReportData(pageNum);
    });
    $('#search').on('input', function() {
        getAllReportData(pageNum);
    });
    $('.dropdown-trigger').dropdown();
    $('#previousBtn').click(function () {
        if (pageNum > 0) {
            pageNum = pageNum - 1;
            document.getElementById("firstBtn").innerHTML = pageNum + 1;
            getAllReportData(pageNum);
        }
    });
    $('#nextBtn').click(function () {
        pageNum = pageNum + 1;
        document.getElementById("firstBtn").innerHTML = pageNum + 1;
        getAllReportData(pageNum);
    });
}function getAllReportData(pageNum) {
    var headers = {"size": $('#reportTableCounter').val(), "pageNum": pageNum, "searchValue": $('#search').val()};
    displayReportData("/api/getReportTableData", headers);
}