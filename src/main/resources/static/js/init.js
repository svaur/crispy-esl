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
                .append(data)
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
            .append("<table class=\"centered striped\" id=\"eslTable\"></table>");//frame=\"border\"
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
                            "<th>Action</th>" +
                        "</tr>" +
                    "</thead>")
            .append("<tbody id=\"eslTBody\"></tbody>");
        for (var i = 0; i < tableData.length; i++) {
            $('#eslTBody').append("<tr>" +
                                    "<td>" + tableData[i].key1 + "</td>" +
                                    "<td>" + tableData[i].key2 + "</td>" +
                                    "<td>" + tableData[i].key3 + "</td>" +
                                    "<td>" + tableData[i].key4 + "</td>" +
                                    "<td>" + tableData[i].key5 + "</td>" +
                                    "<td>" + tableData[i].key6 + "</td>" +
                                    "<td>" + tableData[i].key7 + "</td>" +
                                    "<td>" + tableData[i].key8 + "</td>" +
                                    "<td>" + tableData[i].key9 + "</td>" +
                                    "<td>экшн кнопка</td>" +
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
function showImage(data) {
    var w = window.open();
    $(w.document.body).html("<img id='esl-image' src='" + data + "'>");
}