$(document).ready(function () {
    $('.sidenav').sidenav();
    $('.dropdown-trigger').dropdown();
    displayWorkSpace('#welcomeWorkSpace', '');
    $('#welcomeWorkSpace').click(function () {
        setActive('#welcomeWorkSpace');
        displayWorkSpace('#welcomeWorkSpace', '');
    });
    $('#eslsWorkSpace').click(function () {
        setActive('#eslsWorkSpace');
        displayWorkSpace('#eslsWorkSpace', '/api/getTableData');
    });
    $('#itemsWorkSpace').click(function () {
        setActive('#itemsWorkSpace');
        displayWorkSpace('#itemsWorkSpace', '/api/getAnotherTableData');
    });
    $('#associateWorkSpace').click(function () {
        setActive('#associateWorkSpace');
        displayWorkSpace('#associateWorkSpace', '/api/getAnotherTableData');
    });
    $('#schedulerWorkSpace').click(function () {
        setActive('#schedulerWorkSpace');
        $('#workSpace').html('')
            .append("<h5>sdfsdfsdfsdfsdfsdf</h5>")
    });
});

function displayWorkSpace(menu, url) {
    setActive();
    switch(menu) {
        case "#welcomeWorkSpace":
            $('#workSpace').html('')
                .append(getWelcomeTemplate());
            testChart("myChart1");
            testChart("myChart2");
            testChart("myChart3");
            testChart("myChart4");
            activateActions();
            break;
        case "#eslsWorkSpace":
            $('#workSpace').html('')
                .append(getEslsTemplate());
            activateActions();
            displayEslData(url);
            break;
        case "#itemsWorkSpace":
            $('#workSpace').html('')
                .append(getTestTemplate());
            break;
        case "#associateWorkSpace":
            $('#workSpace').html('')
                .append(getAssociateTemplate());
            $('.tabs').tabs();
            activateActions();
            displayEslData(url);
            break;
        default:
            alert("пока не готово");
    }
    $('select').formSelect()//Для отображения селектов
}

function setActive(nameClassToActive) {
    $('#eslsWorkSpace').removeClass("active");
    $('#itemsWorkSpace').removeClass("active");
    $('#associateWorkSpace').removeClass("active");
    $('#schedulerWorkSpace').removeClass("active");
    $(nameClassToActive).addClass("active");
}
function activateActions() {
    $('#eslTableCounter').formSelect().on('change', function () {
        var headers = {"size": $('#eslTableCounter').val()};
        displayEslData("/api/getTableData", headers);
    });
    $('#search').on('input', function() {
        var headers = {"size": $('#eslTableCounter').val(), "searchValue": $('#search').val()};
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
    }).error(function(jqXHR) {
        alert(jqXHR.responseText);
    });
}