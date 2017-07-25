/**
 * Created by Chaitanya on 12/7/16.
 */
angular.module('portfolio', ['ui.router', 'dashboardModule'])
    .config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider

    //states and views
        .state('home', {
            url: '/home',
            templateUrl: 'webapp/js/partials/home.html',
            controller:'dashboardController'
        })
        //saved portfolio
        .state('clay', {
            url: '/clay',
            templateUrl: 'webapp/js/partials/clay.html',
            controller:'dashboardController'
        })
        .state('checkout', {
            url: '/checkout',
            templateUrl: 'webapp/js/partials/checkout.html',
            controller:'dashboardController'
        });

    })