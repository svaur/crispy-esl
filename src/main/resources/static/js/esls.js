function getEslsTemplate() {
    return "<span class='flow-text'>Ценники</span>" + getTableTemplate('esl')
}
function displayEslData(url, headers) {
    var display = document.getElementById("mainProgress");
    display.style.visibility='visible';
    $.getJSON(url, headers, function (data) {
        var tableData = $.parseJSON(JSON.stringify(data));
        $('#eslTable').html('')
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
                "<td>" + tableData[i].eslCode + "</td>" +
                "<td>" + tableData[i].eslType + "</td>" +
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
                "<a class=\"waves-effect waves-light\" onclick='showImage(" + tableData[i].eslCode + ")'>" +
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
        display.style.visibility='hidden'
    }).error(function(jqXHR) {
        alert(jqXHR.responseText);
        display.style.visibility='hidden'
    });
}
function eslActivateActions() {
    $('#eslTableCounter').formSelect().on('change', function () {
        var headers = {"size": $('#eslTableCounter').val()};
        displayEslData("/api/getEslTableData", headers);
    });
    $('#search').on('input', function() {
        var headers = {"size": $('#eslTableCounter').val(), "searchValue": $('#search').val()};
        displayEslData("/api/searchEslData", headers);
    });
    $('.dropdown-trigger').dropdown();
}