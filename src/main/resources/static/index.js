angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8090/api/v1';

    $scope.compareExchangeRate = function () {
        let titleCurrency = $("#codes_select").val();

        $.ajax({
            url: contextPath + '/currencies/compare_rate/' + titleCurrency,
            method: 'GET',
            dataType: "json",
            complete: function (data) {
                let content = JSON.parse(data.responseText);
                let img = document.createElement("img");
                let gifName = document.createElement("p");
                gifName.textContent = content.data.title;
                let gifKey = document.createElement("p");
                gifKey.textContent = content.compareResult;
                img.src = content.data.images.original.url;
                let out = document.querySelector("#out");
                out.innerHTML = '';
                out.insertAdjacentElement("afterbegin", img);
                out.insertAdjacentElement("afterbegin", gifName);
                out.insertAdjacentElement("afterbegin", gifKey);
            }
        })
    }

    $scope.loadSelect = function () {
        $.ajax({
            url:  contextPath + '/currencies',
            method: 'GET',
            complete: function (data) {
                let codesList = JSON.parse(data.responseText);
                let select = document.querySelector("#codes_select");
                select.innerHTML = '';
                for (let i = 0; i < codesList.length; i++) {
                    let option = document.createElement("option");
                    option.value = codesList[i];
                    option.text = codesList[i];
                    select.insertAdjacentElement("beforeend", option);
                }
            }
        })
    }
    $scope.loadSelect();
});