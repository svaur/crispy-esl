$(document).ready(function () {
    $('.sidenav').sidenav();
    $('.dropdown-trigger').dropdown();
    downloadTable('#menu1', '/api/getTableData');
    $('#menu1').click(function () {
        downloadTable('#menu1', '/api/getTableData');
    });
    $('#menu2').click(function () {
        setActive('#menu2');
        downloadTable('#menu2', '/api/getAnotherTableData');
    });
    $('#menu3').click(function () {
        setActive('#menu3');
        $.getJSON('/api/getRandStr', {}, function (data) {
            $('#workSpace').html('')//todo поменять т.к поменял айди
                .append("<h5>" + data + "</h5>")

        });
    });
    $('#menu4').click(function () {
        setActive('#menu4');
        $('#workSpace').html('')//todo поменять т.к поменял айди
            .append("<h5>sdfsdfsdfsdfsdfsdf</h5>")
    })
});
function downloadTable(menu, url) {
    setActive();
    if(menu === "#menu1"){
        getEslList(url)
    }
    else {
        alert("пока не готово")
    }
}
function getEslList(url) {
    $.getJSON(url, {}, function (data) {
        var tableData = $.parseJSON(JSON.stringify(data));
        $('#workSpace').html('')
            .append("<table class=\"bordered striped centered highlight responsive-table\">")//frame=\"border\"
            .append("<thead><tr><th>ESL code</th><th>ESL type</th><th>Item code</th><th>Item name</th><th>Price</th>" +
                "<th>Updated date</th><th>Connectivity</th><th>Battery level</th><th>Status</th><th>Action</th></tr></thead>")
            .append("<tbody>");
        for (var i = 0; i < tableData.length; i++) {
            $('#workSpace').append("<tr><td>" + tableData[i].key1 + "</td><td>" + tableData[i].key2 + "</td><td>"
                + tableData[i].key3 + "</td><td>" + tableData[i].key4 + "</td><td>"
                + tableData[i].key5 + "</td><td>" + tableData[i].key6 + "</td><td>"
                + tableData[i].key7 + "</td><td>" + tableData[i].key8 + "</td><td>"
                + tableData[i].key9 + "</td><td>экшн кнопка</td>"
                + "</tr>");

        }
        $('#workSpace')
            .append("</tbody>")
            .append("</table>")
    });
}
function setActive(nameClassToActive) {
    $('#menu1').removeClass("active");
    $('#menu2').removeClass("active");
    $('#menu3').removeClass("active");
    $('#menu4').removeClass("active");
    $(nameClassToActive).addClass("active");
}