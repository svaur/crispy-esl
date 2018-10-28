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
        $.getJSON('/api/getImage', {eslId:1}, function (data) {
            $('#workSpace').html('')
                .append("<img id='esl-image' src='" + data + "'>")
        });
    });
    $('#menu4').click(function () {
        setActive('#menu4');
        $('#workSpace').html('')
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
            .append("<span class='flow-text'>ESLs list</span>" +
                    "<div class=\"divider\"></div>")
            .append("<div class=\"row\">" +
                        "<div class=\"col s1\">" +
                            "<span>Show by</span>" + //todo надо выровнять
                        "</div>" +
                        "<div class=\"input-field col s1\">" +
                            "<select class='browser-default'>" +
                                "<option value=\"\" disabled selected>10</option>" +
                                "<option value=\"1\">25</option>" +
                                "<option value=\"2\">50</option>" +
                                "<option value=\"3\">80</option>" +
                            "</select>" +
                        "</div>" +
                    "</div>")
            .append("<table class=\"centered striped\" id=\"esl-table\"></table>");//frame=\"border\"
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
                                    "<a class=\"waves-effect waves-light btn-small\" onclick='showImage("+tableData[i].elsCode+")'><i class=\"material-icons\">photo</i></a>" +
                                    "<a class=\"waves-effect waves-light btn-small\"><i class=\"material-icons\">edit</i></a>" +
                                    "<a class=\"waves-effect waves-light btn-small\"><i class=\"material-icons\">update</i></a>" +
                                    "</td>" +
                                  "</tr>");
        }
        $('select').formSelect();
    });
}
function setActive(nameClassToActive) {
    $('#menu1').removeClass("active");
    $('#menu2').removeClass("active");
    $('#menu3').removeClass("active");
    $('#menu4').removeClass("active");
    $(nameClassToActive).addClass("active");
}
function showImage(elsCode) {
    var w = window.open();
    $.getJSON("/api/getImage", {elsCode:elsCode}, function (data) {
        $(w.document.body).html(
            "<style>.shadow {" +
            "box-shadow: 0 0 10px rgba(0,0,0,0.5);" +
            "padding: 0px;}</style>" +
            "<img class=\"shadow\" id='esl-image' src='" + data + "'>");
    });
}