/**
 * Created by Chaitanya on 12/7/16.
 */
angular.module('dashboardModule', ['ui.router'])
.controller('dashboardController', function($scope, $state, $log, portfolioData, $interval, $q) {
    $scope.$state = $state;

    $scope.vm={
        portfolioData: [],
        searchResults: [],
        helperMessage: {
            "show":false,
            "message":''
        }
    };
    
    //init function to get saved portfolios and its live data
    function init() {
        var defer = $q.defer();
       portfolioData.getSavedPortfolios().then(function(response) {
         $scope.vm.portfolioData =  response;
           defer.resolve();
       },function (error) {
           $log.log("error: "+error);
           defer.reject();
       });
        return defer.promise;
    };

    //get data for search input
    $scope.getSymbolData = function (input) {
        if(input && checkDupes($scope.vm.searchResults, input, "flag")){
            portfolioData.getSymbolData(input).then(function (data) {
                if($scope.vm.searchResults.length===3){
                    $scope.vm.searchResults.shift();
                }
                $scope.vm.searchResults.push(data);
                $scope.vm.helperMessage.show = false;
            })
        }
        else {
            $scope.vm.helperMessage.show = true;
            $scope.vm.helperMessage.message='Already added!';
        }
    };


//function which checks if there are any dupes which are already added
    function checkDupes(array, symbol, splice) {
        var index = array.map(function (e) { return e.data.query.results.quote.symbol}).indexOf(symbol);
        if(index>-1){
            if(!splice){
                array.splice(index,1);
            }
            return false;
        }
        return true;
    };


    //Adds selected stock to the portfolio
    $scope.addPortfolio = function (pf) {
      if(pf.quote.Name){
          var noDupes = checkDupes($scope.vm.portfolioData, pf.quote.symbol);
          if($scope.vm.portfolioData.length<5 && noDupes) {
              portfolioData.savePortfolio(pf).then(function (response) {
                  $scope.vm.portfolioData = response;
                  checkDupes($scope.vm.searchResults, pf.quote.symbol);
              }, function (error) {
                  $log.log("Error: " + error);
              });
              $scope.vm.helperMessage.show = false;
          }
          else if(!noDupes){
              $scope.vm.helperMessage.show = true;
              $scope.vm.helperMessage.message='Already added';
          }
          else {
              $scope.vm.helperMessage.show = true;
              $scope.vm.helperMessage.message='You reached your maximum limit! Please remove any portfolio to add one';
          }
      }
        else{
          $scope.vm.helperMessage.show = true;
          $scope.vm.helperMessage.message='Please select a valid portfolio to add';
      }
        
    };
    
    
    //updates portfolio with stock units
    $scope.updatePortfolio = function(pf){
        if(pf.numberOfShares<0){
            $scope.vm.helperMessage.show = true;
            $scope.vm.helperMessage.message='Please select a valid input';
        }else {
            $scope.vm.helperMessage.show = false;
            portfolioData.updatePortfolio(pf);
        }
    };
    
    
    //removes search results
    $scope.deleteFromSearchResults = function(pf){
        checkDupes($scope.vm.searchResults, pf.quote.symbol);
    };

    //removes a stock from the portfolio
    $scope.deleteStock = function (pf) {
        if(pf){
           portfolioData.deleteStock(pf).then(function (response) {
               $scope.vm.portfolioData = response;
           }, function (error) {
               $log.log("Error: " + error);
           });
        }
    };

    //watch expression to get difference between the last and current value
    init().then(function () {
            $scope.$watch('vm.portfolioData', function (newVal, oldVal) {
                angular.forEach(newVal, function (nv,ni) {
                    angular.forEach(oldVal, function (ov, oi) {
                        if(nv && ov){
                            var oq = ov.data.query.results.quote;
                            var nq = nv.data.query.results.quote;
                            if(nq.symbol===oq.symbol){
                                var change = nq.LastTradePriceOnly-oq.LastTradePriceOnly;
                                if(change!=0){
                                    (change>0) ? nq['up'] = (change*100)/oq.LastTradePriceOnly : nq['down'] = (change*100)/oq.LastTradePriceOnly;
                                }
                            }
                        }
                    })
                })
            }, true)
    });

    //updating data for every 5 seconds
    //$interval(init, 5000);
});
