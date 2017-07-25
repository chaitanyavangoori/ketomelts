/**
 * Created by Chaitanya on 12/7/16.
 */
angular.module('dashboardModule').factory('portfolioData', function ($http, $q, $log){
    var service = {};

    //construct url for each symbol
    service.getBaseUrl  = function (symbol) {
        return "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%22"+symbol+"%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
    };

    //service call to get user requested data
    service.getSymbolData = function (symbol) {
        var url = service.getBaseUrl(symbol);
        return $http.get(url).then(function (response) {
            return response;
        }, function (error) {
            $log.log("Error Occurred "+error);
        })
    };
    
//get saved symbols from db
    service.getSavedPortfolios = function () {
        return $http.get("/user/savedPortfolios").then(function (response) {
            return service.getLatestData(response.data).then(function (data) {
                return data;
            });
        }, function (error) {
            $log.log("Error Occurred "+error);
        })
    };
    
//This is a better way to do using $q instead of manual promise chaining
    service.getLatestData = function (data) {
        //adding all promises into an array
        var promises = data.map(function (stock) {
            var url = service.getBaseUrl(stock.symbol);
            return $http.get(url).then(function (response) {
                (!stock['numberOfShares']) ? response.data.query.results['numberOfShares']=0 : response.data.query.results['numberOfShares'] = stock['numberOfShares'];
                response.data.query.results['id'] = stock.id;
                return response;
            });
        });
        
        //executing all promises using $q lib function
        return $q.all(promises);
    };

    //saves portfolio details
    service.savePortfolio = function (data) {
        var criteria = [{
            "symbol": data.quote.symbol,
            "name": data.quote.Name
        }];

        (!data['numberOfShares']) ? criteria[0]['numberOfShares'] = 0 : criteria[0]['numberOfShares'] = data['numberOfShares'];
        return $http.post("/user/save", criteria).then(function (response) {
            return service.getLatestData(response.data)
        });
    };

    //update portolio service call
    service.updatePortfolio = function (data) {
        var criteria = {
            "id":data.id,
            "symbol": data.quote.symbol,
            "name": data.quote.Name,
            "numberOfShares": data.numberOfShares
        };
        return $http.put("/user//updatePortfolio", criteria);
    };
    
    //delete portfolio service call
    service.deleteStock = function (pf) {
        var url = "/user/delete/"+pf.id;
        return $http.post(url).then(function (response) {
            return service.getLatestData(response.data)
        });
    };
    
    return service;

});