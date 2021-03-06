function getItemsTemplate() {
    return "<h2 class='flow-text'>Товары</h2>" +
        "<div class=\"divider\"></div>" +
        getTableTemplate('item')
}
function displayItemData(url, headers) {
    var display = document.getElementById("mainProgress");
    display.style.visibility='visible';
    $.getJSON(url, headers, function (data) {
        var tableData = $.parseJSON(JSON.stringify(data));
        $('#itemTable').html('')
            .append("<thead>" +
                "<tr>" +
                "<th>Код товара</th>" +
                "<th>Имя товара</th>" +
                "<th>Цена</th>" +
                "<th>акционная цена</th>" +
                "<th>акция</th>" +
                "<th>Время последнего обновления</th>" +
                "<th>Привязка</th>" +
                "<th>Действие</th>" +
                "</tr>" +
                "</thead>")
            .append("<tbody id=\"itemTBody\"></tbody>");
        for (var i = 0; i < tableData.length; i++) {
            $('#itemTBody').append(
                "<tr>" +
                    "<td>" + tableData[i].itemCode + "</td>" +
                    "<td>" + tableData[i].itemName + "</td>" +
                    "<td>" + tableData[i].price + "</td>" +
                    "<td>" + tableData[i].secondPrice + "</td>" +
                    "<td>" + tableData[i].action + "</td>" +
                    "<td>" + tableData[i].lastUpdate + "</td>" +
                    "<td>" + tableData[i].associate + "</td>" +
                    "<td> </td>" +
                "</tr>");
        }
        $('.dropdown-trigger').dropdown();
        display.style.visibility='hidden'
    }).error(function(jqXHR) {
        display.style.visibility='hidden';
        //alert(jqXHR.responseText);
    });
}
function itemActivateActions() {
    $('#itemTableCounter').formSelect().on('change', function () {
        getAllItemsData(pageNum);
    });
    $('#search').on('input', function() {
        getAllItemsData(pageNum);
    });
    $('.dropdown-trigger').dropdown();
    $('#previousBtn').click(function () {
        if (pageNum > 0) {
            pageNum = pageNum - 1;
            document.getElementById("firstBtn").innerHTML = pageNum + 1;
            getAllItemsData(pageNum);
        }
    });
    $('#nextBtn').click(function () {
        pageNum = pageNum + 1;
        document.getElementById("firstBtn").innerHTML = pageNum + 1;
        getAllItemsData(pageNum);
    });
}
function getAllItemsData(pageNum) {
    var headers = {"size": $('#itemTableCounter').val(), "pageNum": pageNum, "searchValue": $('#search').val()};
    displayItemData("/api/getItemTableData", headers);
}