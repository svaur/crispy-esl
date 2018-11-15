$(document).ready(function () {
    $('.sidenav').sidenav();
    $('.dropdown-trigger').dropdown();
    displayWorkSpace('#menu1', '/api/getTableData');
    $('#menu1').click(function () {
        displayWorkSpace('#menu1', '/api/getTableData');
    });
    $('#menu2').click(function () {
        setActive('#menu2');
        displayWorkSpace('#menu2', '/api/getAnotherTableData');
    });
    $('#menu3').click(function () {
        setActive('#menu3');
        displayWorkSpace('#menu3', '/api/getAnotherTableData');
    });
    $('#menu4').click(function () {
        setActive('#menu4');
        $('#workSpace').html('')
            .append("<h5>sdfsdfsdfsdfsdfsdf</h5>")
    });
});

function displayWorkSpace(menu, url) {
    setActive();
    switch(menu) {
        case "#menu1":
            $('#workSpace').html('')
                .append(getEslsTemplate());
            activateActions();
            displayEslData(url);
            break;
        case "#menu2":
            $('#workSpace').html('')
                .append(getTestTemplate());
            break;
        case "#menu3":
            $('#workSpace').html('')
                .append(getAssertTemplate());
            $('.tabs').tabs();
            activateActions();
            displayEslData(url);
            break;
        default:
            alert("пока не готово");
    }
}

function setActive(nameClassToActive) {
    $('#menu1').removeClass("active");
    $('#menu2').removeClass("active");
    $('#menu3').removeClass("active");
    $('#menu4').removeClass("active");
    $(nameClassToActive).addClass("active");
}
function activateActions() {
    $('select').formSelect().on('change', function () {
        var headers = {"size": $('select').val()};
        displayEslData("/api/getTableData", headers);
    });
    $('#search').on('input', function() {
        var headers = {"size": $('select').val(), "searchValue": $('#search').val()};
        displayEslData("/api/searchData", headers);
    });
    $('.dropdown-trigger').dropdown();
}
function showImage(code) {
    var w = window.open();
    $.getJSON("/api/getImage", {elsCode:code}, function (data) {
        $(w.document.body).html(
            "<style>.shadow {" +
            "box-shadow: 0 0 10px rgba(0,0,0,0.5);" +
            "padding: 0px;}</style>" +
            "<img class=\"shadow\" id='esl-image' src='" + data + "'>");
    });
}